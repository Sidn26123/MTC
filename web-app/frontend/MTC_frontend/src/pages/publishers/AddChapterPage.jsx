import React, { useEffect, useState } from 'react';
import { CategoryDropdown, CustomDatePicker, SimpleDropdown } from '../../common/CommonComponents.jsx';
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import { formatPublishDateTime } from '../../utils/DatetimeUtil.js';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, redirect, useLocation, useNavigate } from 'react-router';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import { getObjectFromList, getWordCount } from '../../utils/Utils.js';
import { useCurrentNovelPublisher } from '../../stores/userStores.js';
import { usePublishedByPublisher } from '../../stores/novelStore.js';
import {
    useCurrentChosenPublishedNovel,
    useCurrentPublishedNovel,
    usePublisherStore, useSetCurrentChosenPublishedNovel, useSetCurrentPublishedNovel,
} from '../../stores/publisherStore.js';
import { uploadChapter, uploadChapters } from '../../services/chapterService.js';
import { showError, showSuccess } from '../../utils/ToastUtils.js';
import { uploadNovelCover } from '../../services/publisherService.js';
import {faEye, faTimes } from "@fortawesome/free-solid-svg-icons";
import Content from '../../components/common/Content.jsx';
import { getNovelById } from '../../services/novelService.js';


const AddChapterPage = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const initialContent = location.state?.chapterContent || '';
    const [chapterContent, setChapterContent] = useState(initialContent);
    const [chapterList, setChapterList] = useState([]);
    const [chapterContentError, setChapterContentError] = useState("");


    const currentPublishedNovel = useCurrentPublishedNovel();
    const setCurrentPublishedNovel = useSetCurrentPublishedNovel();
    const [showDateTimePicker, setShowDateTimePicker] = React.useState(false);
    const [value, setValue] = useState(undefined);
    const [formattedDate, setFormattedDate] = useState("");
    const [mode, setMode] = useState("writing"); // writing, confirm

    // State để quản lý ảnh
    const [imageCounter, setImageCounter] = useState(0);
    const [uploadedImages, setUploadedImages] = useState({}); // {placeholder: {url: string, uploading: boolean}}
    const [isAnyImageUploading, setIsAnyImageUploading] = useState(false);

    const getFormattedDate = (date) => {
        return formatPublishDateTime(date, "dd/MM/yyyy HH:mm");
    };
    const typeAdd = [{id: "normal", name: "Đăng thường"}, {id: "insert", name: "Chèn chương"}];
    const [currentType, setCurrentType] = useState(typeAdd[0]);

    // Thêm state để lưu file content
    const [chapterFile, setChapterFile] = useState(null);

    function handleFileUpload(e) {
        const file = e.target.files[0];
        if (!file) return;

        // Chỉ cho phép file .txt
        if (file.type !== "text/plain") {
            showError("Chỉ chấp nhận file .txt");
            return;
        }

        const reader = new FileReader();
        reader.onload = (event) => {
            const text = event.target.result;
            let finalText = chapterContent + "\n" + text.trim() + "\n"; // Thêm dòng mới trước và sau nội dung
            if (chapterContent.trim() === "") {
                finalText = "Chương " + (currentPublishedNovel.totalChapters + 1) + ": \n" + finalText; // Thêm tiêu đề chương nếu chưa có
            }

            setChapterContent(finalText); // Gán thẳng vào chapterContent để xử lý chung
        };
        reader.readAsText(file, "UTF-8");
    }

    // Hàm xử lý upload ảnh
    const handleImageUpload = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        // Kiểm tra file ảnh
        if (!file.type.startsWith('image/')) {
            showError("Chỉ chấp nhận file ảnh");
            return;
        }

        // Tạo placeholder cho ảnh
        const placeholder = `[IMG_${imageCounter}]`;
        setImageCounter(prev => prev + 1);

        // Thêm placeholder vào content tại vị trí con trỏ
        const textarea = document.querySelector('textarea');
        const cursorPosition = textarea.selectionStart;
        const textBefore = chapterContent.substring(0, cursorPosition);
        const textAfter = chapterContent.substring(cursorPosition);

        setChapterContent(textBefore + `\n${placeholder}\n` + textAfter);

        // Đánh dấu ảnh đang upload
        setUploadedImages(prev => ({
            ...prev,
            [placeholder]: { url: null, uploading: true }
        }));
        setIsAnyImageUploading(true);

        try {
            // Upload ảnh (giả sử bạn có function uploadImage)
            const uploadRes = await uploadNovelCover("", file);

            if (uploadRes.status === 200) {
                setUploadedImages(prev => ({
                    ...prev,
                    [placeholder]: { url: uploadRes.data.result.url, uploading: false }
                }));
                console.log(`Upload ảnh thành công: ${placeholder} -> ${uploadRes.data.result.url}`);
            } else {
                throw new Error('Upload failed');
            }
        } catch (error) {
            console.error("Lỗi khi upload ảnh:", error);
            showError("Lỗi khi upload ảnh");

            // Xóa placeholder nếu upload thất bại
            setChapterContent(prev => prev.replace(`\n${placeholder}\n`, '\n'));
            setUploadedImages(prev => {
                const newState = { ...prev };
                delete newState[placeholder];
                return newState;
            });
        }

        // Kiểm tra xem còn ảnh nào đang upload không
        const stillUploading = Object.values(uploadedImages).some(img => img.uploading);
        setIsAnyImageUploading(stillUploading);

        // Reset input
        e.target.value = '';
    };

    // Hàm thay thế placeholder bằng URL thực tế
    const replaceImagePlaceholders = (content) => {
        let processedContent = content;

        Object.entries(uploadedImages).forEach(([placeholder, imageData]) => {
            if (imageData.url && !imageData.uploading) {
                // Thay placeholder bằng syntax ảnh (ví dụ: ![alt](url))
                processedContent = processedContent.replace(
                    placeholder,
                    `![Ảnh](${imageData.url})`
                );
            }
        });

        return processedContent;
    };

    const publishChapter = () => {
        // Kiểm tra xem có ảnh nào đang upload không
        if (isAnyImageUploading) {
            showError("Vui lòng chờ upload ảnh hoàn tất");
            return;
        }

        const updatedList = chapterList.map((ch, i) => {
            if (ch.content.trim() === "") {
                setChapterContentError("Nội dung chương không được để trống.");
                return ch; // không thay đổi
            }

            // Thay thế placeholder bằng URL ảnh thực tế
            const processedContent = replaceImagePlaceholders(ch.content);

            return {
                ...ch,
                content: processedContent, // Sử dụng content đã được xử lý
                isInsertMode: currentType.id === "insert",
                chapterIdx: ch.index,
                chapterStatus: ["011ea719-cb1c-46a6-b8c6-0607127dbc6c"],
                novelId: currentPublishedNovel.id,
                name: ch.title,
                // publishedAt: formattedDate ? formattedDate : null,
            };
        });

        // Promise.all(
        //     updatedList.map(ch =>
        //         uploadChapter(ch).then(r => ({ ch, r }))
        //     )
        // ).then(results => {
        //     console.log("Upload results: ", results);
        //     const successChapters = results.filter(res => res.r.status === 200).map(res => res.ch.index);
        //
        //     setChapterList(prev => prev.filter(item => !successChapters.includes(item.index)));
        //
        //     showSuccess("Đăng chương thành công");
        //     navigate("/bookhub/novels/" + currentPublishedNovel.slug +"/chapters");
        // });

        uploadChapters(updatedList).then(async response => {
            if (response.status === 200) {
                showSuccess("Đăng chương thành công");
                setChapterList([]); // Xóa danh sách chương sau khi đăng
                const d = await getNovelById(currentPublishedNovel.id);
                console.log("updateload: ", d);
                // console.log(getNovelById(currentPublishedNovel.id))
                setCurrentPublishedNovel(
                    d.data.result
                )
                navigate("/bookhub/novels/" + currentPublishedNovel.slug + "/chapters");
            } else {
                showError("Đăng chương thất bại: " + response.data.message);
            }
        })

    }

    useEffect(() => {
        if (currentPublishedNovel === null ){

        }
    }, []);

    // Cập nhật giá trị khi component render
    useEffect(() => {
        if (!(value === undefined || value === null)) {
            setFormattedDate(getFormattedDate(value));
        }
    }, [value]);

    // Cập nhật trạng thái upload
    useEffect(() => {
        const stillUploading = Object.values(uploadedImages).some(img => img.uploading);
        setIsAnyImageUploading(stillUploading);
    }, [uploadedImages]);

    function handleSelectField(selected) {
        setCurrentType(getObjectFromList(typeAdd, "id", selected.id));
    }

    function getWordCount(content) {
        if (!content || content.trim() === "") return 0;
        return content.trim().split(/\s+/).length;
    }

    // function splitChapter(content) {
    //     const chapters = []; // {title: "", content: "", index: 0, wordCount: 0}
    //
    //     const lines = content.split('\n');
    //     let currentChapterIndex = -1;
    //     const startIndex = currentPublishedNovel.totalChapters;
    //
    //     for (let i = 0; i < lines.length; i++) {
    //         const line = lines[i].trim();
    //
    //         if (checkLineIsHeader(line)) {
    //             // Tạo chapter mới
    //             currentChapterIndex++;
    //             chapters.push({
    //                 title: line,
    //                 content: "",
    //                 index: startIndex + currentChapterIndex,
    //                 wordCount: 0
    //             });
    //         } else {
    //             if (line.startsWith("#Chương")) {
    //                 chapters[currentChapterIndex].content = line.replace(/^#/, "").trim() + "\n";
    //             } else {
    //                 chapters[currentChapterIndex].content += line + "\n";
    //             }
    //         }
    //     }
    //
    //     // Tính wordCount cho từng chương
    //     return chapters.map(ch => ({
    //         ...ch,
    //         wordCount: getWordCount(ch.content)
    //     }));
    // }


    function splitChapter(content) {
        const chapters = []; // {title: "", content: "", index: 0, wordCount: 0}

        const lines = content.split('\n');
        let currentChapterIndex = -1;
        const startIndex = currentPublishedNovel.totalChapters;

        for (let i = 0; i < lines.length; i++) {
            const line = lines[i].trim();

            if (checkLineIsHeader(line)) {
                // Tạo chapter mới
                currentChapterIndex++;
                chapters.push({
                    title: line,
                    content: "",
                    index: startIndex + currentChapterIndex,
                    wordCount: 0
                });
            } else {
                if (line.startsWith("#Chương")) {
                    chapters[currentChapterIndex].content = line.replace(/^#/, "").trim() + "\n";
                } else {
                    chapters[currentChapterIndex].content += line + "\n";
                }
            }
        }

        // Xử lý tách chương nếu vượt 5000 từ
        const processed = [];
        chapters.forEach((ch, idx) => {
            let words = ch.content.split(/\s+/);
            if (words.length <= 5000) {
                processed.push({
                    ...ch,
                    wordCount: words.length
                });
            } else {
                let start = 0;
                let part = 1;
                while (start < words.length) {
                    let end = start + 5000;
                    if (end >= words.length) end = words.length;
                    else {
                        // tìm dấu "." gần nhất để cắt
                        while (end < words.length && !words[end].endsWith(".")) {
                            end++;
                        }
                        if (end >= words.length) end = words.length;
                    }

                    let chunkWords = words.slice(start, end);
                    let chunkContent = chunkWords.join(" ");

                    processed.push({
                        title: `${ch.title} (Phần ${part})`,
                        content: chunkContent,
                        index: ch.index + part - 1, // giữ index liên tục
                        wordCount: chunkWords.length
                    });

                    start = end;
                    part++;
                }
            }
        });

        return processed;
    }


    function checkLineIsHeader(line) {
        // Loại bỏ khoảng trắng đầu cuối dòng
        const trimmedLine = line.trim();

        // Regex kiểm tra các định dạng tiêu đề chương
        const headerRegex = /^Chương\s+\d+(:|$)/i;

        const isValid = headerRegex.test(trimmedLine);
        return isValid;
    }

    function checkHasValidHeader(content) {
        const lines = content.split('\n');
        if (checkLineIsHeader(lines[0])) {
            return true;
        }
        return false; // Không tìm thấy tiêu đề chương hợp lệ ở line đầu tiên
    }

    function handleSplitChapter() {
        console.log(splitChapter(chapterContent));
    }

    function handleFinishAddContent() {
        if (!checkHasValidHeader(chapterContent)) {
            setChapterContentError('Tên chương không hợp lệ.');
            return;
        }

        // Kiểm tra xem có ảnh nào đang upload không
        if (isAnyImageUploading) {
            setChapterContentError('Vui lòng chờ upload ảnh hoàn tất.');
            return;
        }

        setChapterContentError('');
        var chapters = splitChapter(chapterContent);
        setChapterList(chapters);
        setMode('confirm');
    }

    // Hàm hiển thị trạng thái upload ảnh
    const renderImageUploadStatus = () => {
        const imageEntries = Object.entries(uploadedImages);
        if (imageEntries.length === 0) return null;

        return (
            <div className="mt-3 p-3 bg-gray-50 rounded-md">
                <span className="text-sm font-semibold">Trạng thái upload ảnh:</span>
                {imageEntries.map(([placeholder, imageData]) => (
                    <div key={placeholder} className="flex items-center mt-2">
                        <span className="text-xs text-gray-600 mr-2">{placeholder}:</span>
                        {imageData.uploading ? (
                            <span className="text-xs text-blue-500">Đang upload...</span>
                        ) : (
                            <span className="text-xs text-green-500">✓ Hoàn tất</span>
                        )}
                    </div>
                ))}
            </div>
        );
    };

    return (
        <div>
            {mode === 'writing' ? (
                <div
                    className={
                        'flex flex-col bg-background-light rounded-md p-5'
                    }
                >
                    <div className={''}>
                        <h1>Thêm chương</h1>
                        <span>Truyện đầu tiên</span>
                    </div>
                    <div
                        className={
                            'flex flex-row justify-between items-center mt-10'
                        }
                    >
                        <div className={'flex flex-col gap-y-2 w-1/2 p-2 pl-3'}>
                            <span className={'text-sm'}>Loại</span>
                            <CategoryDropdown
                                dropdown={typeAdd}
                                placeholder={'Chọn thể loại'}
                                onSelect={(selected) =>
                                    handleSelectField(selected)
                                }
                                defaultValue={currentType}
                            />
                        </div>
                        <div className={'flex flex-col gap-y-1 w-1/2'}>
                            <span>STT</span>
                            <input
                                className={
                                    'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'
                                }
                                disabled={currentType.id === 'normal'}
                                value={currentPublishedNovel.totalChapters + 1}
                            />
                        </div>
                    </div>
                    <div className={'flex flex-col mt-10 p-3'}>
                        <span>Nội dung các chương</span>
                        <textarea
                            className="w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                            placeholder="Tên chương phải theo format: Chương [Số chương]: [Tên chương]"
                            onChange={(e) => setChapterContent(e.target.value)}
                            value={chapterContent}
                            onFocus={() => {
                                if (chapterContent.trim() === '') {
                                    setChapterContent(
                                        `Chương ${currentPublishedNovel.totalChapters + 1}: `
                                    );
                                }
                            }}
                        />
                        {chapterContentError && (
                            <span className={'text-xs text-red-500 mt-3'}>
                                {chapterContentError}
                            </span>
                        )}
                        <span className={'text-xs text-gray-500 mt-3'}>
                            Số từ: {chapterContent.length}
                        </span>

                        {/* Hiển thị trạng thái upload ảnh */}
                        {renderImageUploadStatus()}
                    </div>

                    <div className="flex flex-col mt-5">
                        <span>Upload file .txt</span>
                        <input
                            type="file"
                            accept=".txt"
                            onChange={handleFileUpload}
                            className="mt-2"
                        />
                        <span className="text-xs text-gray-500 mt-1">
                            Nếu bạn upload file .txt, nội dung sẽ tự động hiển
                            thị ở ô soạn thảo.
                        </span>
                    </div>

                    {/* Thêm section upload ảnh */}
                    <div className="flex flex-col mt-5">
                        <span>Chèn ảnh vào nội dung</span>
                        <input
                            type="file"
                            accept="image/*"
                            onChange={handleImageUpload}
                            className="mt-2"
                            multiple={false}
                        />
                        <span className="text-xs text-gray-500 mt-1">
                            Chọn vị trí trong textarea rồi upload ảnh. Ảnh sẽ được chèn tại vị trí con trỏ.
                        </span>
                    </div>

                    <div
                        className={
                            'flex flex-row justify-between items-center gap-x-5 mt-10'
                        }
                    >
                        <div
                            className={
                                'flex flex-col gap-y-2 w-1/2 ml-2 relative'
                            }
                        >
                            <span>Hen gio</span>
                            <input
                                className={
                                    'w-full select-none border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 hover:cursor-pointer focus:border-gray-400 focus:outline-none focus:ring-0'
                                }
                                readOnly
                                value={formattedDate}
                                onClick={() =>
                                    setShowDateTimePicker(!showDateTimePicker)
                                }
                            />
                            {showDateTimePicker && (
                                <div className={'absolute text-gray-500'}>
                                    <div className={''}>
                                        <CustomDatePicker
                                            value={value}
                                            onChange={setValue}
                                        />
                                    </div>
                                </div>
                            )}
                        </div>
                        <div className={'flex flex-col gap-y-2 w-1/2'}>
                            <span>Thu phi</span>
                            <input
                                className={
                                    'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'
                                }
                            />
                        </div>
                    </div>
                    <div
                        className={
                            'flex flex-row justify-center items-center gap-x-5 mt-10'
                        }
                    >
                        <button
                            className={`
                                ${isAnyImageUploading
                                ? 'bg-gray-400 cursor-not-allowed'
                                : 'bg-yellow-primary hover:bg-yellow-500 hover:cursor-pointer'
                            }
                                w-48 text-white rounded-md p-2 mt-10 focus:outline-none focus:ring-0
                            `}
                            onClick={handleFinishAddContent}
                            disabled={isAnyImageUploading}
                        >
                            {isAnyImageUploading ? 'Đang upload ảnh...' : 'Thêm'}
                        </button>
                    </div>
                </div>
            ) : (
                <ConfirmAddNovel
                    chapter={splitChapter(chapterContent)}
                    onBack={() => setMode('writing')}
                    onNext={() => publishChapter()}
                />
            )}
        </div>
    );
};


const NormalAddChapterMode = () => {

};

//
// const ConfirmAddNovel = ({ chapter, onBack, onNext }) => {
//
//
//     return (
//         <>
//         <div>
//                 <div className={"flex flex-col"}>
//                     <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
//                         <span className={"text-md"}>
//                             Truyện Mới
//                         </span>
//                         <span>
//                             Đã tách {chapter.length} chương
//                         </span>
//                     </div>
//                     {
//                         chapter.map((item, index) => (
//                             <div className={'bg-background-light rounded-md mt-5 p-5'} key={index}>
//
//                                 <div className={'flex flex-col'}>
//                                     <span className={'text-sm'}>STT</span>
//                                     <input
//                                         className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
//                                         value={item.index + 1}
//                                         readOnly={true}
//                                     />
//
//                                 </div>
//                                 <div className={'flex flex-col mt-8'}>
//                                     <span className={'text-sm'}>Tên chương</span>
//                                     <input
//                                         className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
//                                         value={item.title}
//                                     />
//
//                                 </div>
//                                 <div className={'flex flex-col mt-8'}>
//                                     <span className={'text-sm'}>Nội dung chương</span>
//                                     <textarea
//                                         className={'w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
//                                         value = {item.content}
//                                     />
//
//                                     <span className={'text-xs text-gray-500 mt-3'}>Số từ: {item.content.length}</span>
//                                 </div>
//                             </div>
//                         ))
//                     }
//
//                 </div>
//                 <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
//                     <div className={"w-1/2"}>
//                         <button
//                             className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
//                             onClick={onBack}
//
//                         >
//                             <div className={"flex justify-center items-center gap-x-2"}>
//                                 <span className={"ml-2"}>Nhập Lại</span>
//                                 <FontAwesomeIcon icon={faDeleteLeft} />
//                             </div>
//                         </button>
//                     </div>
//                     <div className={'w-1/2'}>
//                         <button
//                             className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
//                             onClick={onNext}
//
//                         >
//                             <span>
//                                 <div className={"flex justify-center items-center gap-x-2"}>
//                                     <span className={"ml-2"}>Đăng Chương</span>
//                                     <FontAwesomeIcon icon={faArrowUpFromBracket} />
//                                 </div>
//                             </span>
//
//                         </button>
//                     </div>
//                 </div>
//             </div>
//         </>
//     )
// }


const ConfirmAddNovel = ({ chapter, onBack, onNext }) => {
    const [previewIndex, setPreviewIndex] = useState(null);

    return (
        <>
            <div>
                <div className="flex flex-col">
                    <div className="flex flex-col bg-background-light rounded-md p-5 mt-2">
                        <span className="text-md">Truyện Mới</span>
                        <span>Đã tách {chapter.length} chương</span>
                    </div>

                    {chapter.map((item, index) => (
                        <div
                            className="bg-background-light rounded-md mt-5 p-5"
                            key={index}
                        >
                            <div className="flex flex-col">
                                <span className="text-sm">STT</span>
                                <input
                                    className="w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                    value={item.index + 1}
                                    readOnly
                                />
                            </div>

                            <div className="flex flex-col mt-8">
                                <span className="text-sm">Tên chương</span>
                                <input
                                    className="w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                    value={item.title}
                                    readOnly
                                />
                            </div>

                            <div className="flex flex-col mt-8">
                                <span className="text-sm">Nội dung chương</span>
                                <textarea
                                    className="w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                    value={item.content}
                                    readOnly
                                />
                                <span className="text-xs text-gray-500 mt-3">
                                    Số từ: {item.content.length}
                                </span>
                            </div>

                            {/* Nút preview */}
                            <div className="mt-4 flex justify-end">
                                <button
                                    className="bg-blue-500 text-white px-3 py-2 rounded hover:bg-blue-600"
                                    onClick={() => setPreviewIndex(index)}
                                >
                                    <FontAwesomeIcon icon={faEye} className="mr-2" />
                                    Xem trước
                                </button>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="flex flex-row justify-center items-center gap-x-5 w-full">
                    <div className="w-1/2">
                        <button
                            className="bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"
                            onClick={onBack}
                        >
                            <div className="flex justify-center items-center gap-x-2">
                                <span className="ml-2">Nhập Lại</span>
                                <FontAwesomeIcon icon={faDeleteLeft} />
                            </div>
                        </button>
                    </div>
                    <div className="w-1/2">
                        <button
                            className="bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"
                            onClick={onNext}
                        >
                            <div className="flex justify-center items-center gap-x-2">
                                <span className="ml-2">Đăng Chương</span>
                                <FontAwesomeIcon icon={faArrowUpFromBracket} />
                            </div>
                        </button>
                    </div>
                </div>
            </div>

            {/* Fullscreen Preview */}
            {previewIndex !== null && (
                <div className="fixed inset-0 bg-black bg-opacity-90 flex flex-col z-50">
                    {/* Header */}
                    <div className="flex justify-between items-center p-4 bg-gray-900 text-white">
                        <h2 className="text-lg font-semibold">
                            {chapter[previewIndex].title}
                        </h2>
                        <button
                            className="text-white hover:text-red-400"
                            onClick={() => setPreviewIndex(null)}
                        >
                            <FontAwesomeIcon icon={faTimes} size="lg" />
                        </button>
                    </div>

                    {/* Nội dung đọc */}
                    <div className="flex-1 overflow-y-auto p-6 text-white">
                        <Content content={chapter[previewIndex].content} />
                    </div>
                </div>
            )}
        </>
    );
};




export default AddChapterPage;

