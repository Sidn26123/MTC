import React, { useEffect, useState } from 'react';
import { CategoryDropdown, CustomDatePicker, SimpleDropdown } from '../../common/CommonComponents.jsx';
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import { formatPublishDateTime } from '../../utils/DatetimeUtil.js';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, redirect, useNavigate } from 'react-router';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import { getObjectFromList } from '../../utils/Utils.js';
import { useCurrentNovelPublisher } from '../../stores/userStores.js';
import { usePublishedByPublisher } from '../../stores/novelStore.js';
import {
    useCurrentChosenPublishedNovel,
    useCurrentPublishedNovel,
    usePublisherStore,
} from '../../stores/publisherStore.js';
import { uploadChapter, uploadChapters } from '../../services/chapterService.js';
import { showSuccess } from '../../utils/ToastUtils.js';


const AddChapterPage = () => {
    const navigate = useNavigate();
    const [chapterContent, setChapterContent] = useState("");
    const [chapterList, setChapterList] = useState([]);
    const [chapterContentError, setChapterContentError] = useState("");

    const currentPublishedNovel = useCurrentPublishedNovel();
    // const currentNovel = useNovelState();
    const [showDateTimePicker, setShowDateTimePicker] = React.useState(false);
    const [value, setValue] = useState(new Date());
    const [formattedDate, setFormattedDate] = useState("");
    const [mode, setMode] = useState("writing"); // writing, confirm
    const getFormattedDate = (date) => {
        return formatPublishDateTime(date, "dd/MM/yyyy HH:mm");
    };
    const typeAdd = [{id: "normal", name: "Đăng thường"}, {id: "insert", name: "Chèn chương"}];
    const [currentType, setCurrentType] = useState(typeAdd[0]);

    const publishChapter = () => {
        const updatedList = chapterList.map((ch, i) => {
            if (ch.content.trim() === "") {
                setChapterContentError("Nội dung chương không được để trống.");
                return ch; // không thay đổi
            }
            return {
                ...ch,
                isInsertMode: currentType.id === "insert",
                chapterIdx: ch.index,
                chapterStatus: ["011ea719-cb1c-46a6-b8c6-0607127dbc6c"],
                novelId: currentPublishedNovel.id,
                name: ch.title,
            };
        });
        updatedList.forEach((ch, i) => {
            uploadChapter(ch).then(r => {})
        })
        showSuccess("Đăng chương thành công");
        navigate("/bookhub/published");
    }

    // Cập nhật giá trị khi component render
    useEffect(() => {
        setFormattedDate(getFormattedDate(value));
    }, [value]);


    function handleSelectField(selected) {
        setCurrentType(getObjectFromList(typeAdd, "id", selected.id));
    }


    function splitChapter(content){
        var chapters = []; //{title: "", content: "", index: 0}

        //Lap qua content, neu gap header thi bat dau 1 chap truyen
        const lines = content.split('\n');
        let currentChapterIndex = -1;
        const startIndex= currentPublishedNovel.totalChapters;
        console.log("Start index: ", startIndex);
        var sample = {title: "", content: "", index: 0};
        for (let i = 0; i < lines.length; i++) {
            const line = lines[i].trim();
            if (checkLineIsHeader(line)) {
                // Neu la header, tao chap moi
                currentChapterIndex++;
                chapters.push({title: "", content: "", index: startIndex + currentChapterIndex});
                console.log("CHAPTER ", chapters[currentChapterIndex]);
                chapters[currentChapterIndex].title = line;

            } else {
                if (line.startsWith("#Chương")){
                    //remove the one # character before Chuong
                    chapters[currentChapterIndex].content = line.replace(/^#/, "").trim() + "\n";
                }
                else {
                    // Neu khong phai header, them vao noi dung cua chap hien tai\
                    console.log("Adding line to chapter ", currentChapterIndex, " content: ", chapters[currentChapterIndex]);
                    chapters[currentChapterIndex].content += line + "\n";
                }

            }
        }
        return chapters;
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
        if (!checkHasValidHeader(chapterContent)){
            setChapterContentError("Tên chương không hợp lệ.")
            return;
        }

        setChapterContentError("");
        var chapters = splitChapter(chapterContent);
        console.log("Chapters after split:", chapters);
        setChapterList(chapters);
        setMode("confirm");
    }

    return (
        <div>
            {mode === "writing" ? (
                <div className={"flex flex-col bg-background-light rounded-md p-5"}>
                    <div className={""}>
                        <h1>Thêm chương</h1>
                        <span>Truyện đầu tiên</span>
                    </div>
                    <div className={"flex flex-row justify-between items-center mt-10"}>
                        <div className={"flex flex-col gap-y-2 w-1/2 p-2 pl-3"}>
                            <span className={'text-sm'}>Loại</span>
                            <CategoryDropdown
                                dropdown={typeAdd}
                                placeholder={'Chọn thể loại'}
                                onSelect={(selected) => handleSelectField( selected)}
                                defaultValue={currentType}
                            />

                        </div>
                        <div className={'flex flex-col gap-y-1 w-1/2'}>
                            <span>STT</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                disabled={currentType.id === "normal"}
                                value={currentPublishedNovel.totalChapters + 1}
                            />
                        </div>
                    </div>
                    <div className={'flex flex-col mt-10 p-3'}>
                        <span>Nội dung các chương</span>
                        <textarea
                            className={"w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"}
                            onChange={(e) => setChapterContent(e.target.value)}
                            value={chapterContent}
                        />
                        {chapterContentError && (
                            <span className={'text-xs text-red-500 mt-3'}>{chapterContentError}</span>
                        )}
                        <span className={'text-xs text-gray-500 mt-3'}>Số từ: {chapterContent.length}</span>

                    </div>
                    <div className={'flex flex-row justify-between items-center gap-x-5 mt-10'}>
                        <div className={'flex flex-col gap-y-2 w-1/2 ml-2 relative'}>
                            <span>Hen gio</span>
                            <input
                                className={'w-full select-none border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 hover:cursor-pointer focus:border-gray-400 focus:outline-none focus:ring-0'}
                                readOnly
                                value={formattedDate}
                                onClick={() => setShowDateTimePicker(!showDateTimePicker)}
                            />
                            {showDateTimePicker && (
                                <div
                                    className={'absolute text-gray-500'}>
                                    <div className={''}>
                                        <CustomDatePicker value={value} onChange={setValue} />
                                    </div>

                                </div>
                            )}

                        </div>
                        <div className={'flex flex-col gap-y-2 w-1/2'}>
                            <span>Thu phi</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                            />

                        </div>

                    </div>
                    <div className={'flex flex-row justify-center items-center gap-x-5 mt-10'}>
                        <button
                            className={"bg-yellow-primary w-48 text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
                            onClick={() => {handleFinishAddContent()}}
                        >
                            Thêm
                        </button>
                    </div>
                </div>

            ) :
            (
                <ConfirmAddNovel chapter={splitChapter(chapterContent)} onBack={() => setMode("writing")} onNext={() => publishChapter()}/>
            )}
        </div>
    )
}

const NormalAddChapterMode = () => {

}


const ConfirmAddNovel = ({chapter, onBack, onNext}) => {


    return (
        <>
            <div>
                <div className={"flex flex-col"}>
                    <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                        <span className={"text-md"}>
                            Truyện Mới
                        </span>
                        <span>
                            Đã tách {chapter.length} chương
                        </span>
                    </div>
                    {
                        chapter.map((item, index) => (
                            <div className={'bg-background-light rounded-md mt-5 p-5'} key={index}>

                                <div className={'flex flex-col'}>
                                    <span className={'text-sm'}>STT</span>
                                    <input
                                        className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                        value={item.index + 1}
                                        readOnly={true}
                                    />

                                </div>
                                <div className={'flex flex-col mt-8'}>
                                    <span className={'text-sm'}>Tên chương</span>
                                    <input
                                        className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                        value={item.title}
                                    />

                                </div>
                                <div className={'flex flex-col mt-8'}>
                                    <span className={'text-sm'}>Nội dung chương</span>
                                    <textarea
                                        className={'w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                        value = {item.content}
                                    />

                                    <span className={'text-xs text-gray-500 mt-3'}>Số từ: {item.content.length}</span>
                                </div>
                            </div>
                        ))
                    }

                </div>
                <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
                    <div className={"w-1/2"}>
                        <button
                            className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
                            onClick={onBack}

                        >
                            <div className={"flex justify-center items-center gap-x-2"}>
                                <span className={"ml-2"}>Nhập Lại</span>
                                <FontAwesomeIcon icon={faDeleteLeft} />
                            </div>
                        </button>
                    </div>
                    <div className={'w-1/2'}>
                        <button
                            className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
                            onClick={onNext}

                        >
                            <span onClick={onNext}>
                                <div className={"flex justify-center items-center gap-x-2"}>
                                    <span className={"ml-2"}>Đăng Chương</span>
                                    <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                </div>
                            </span>

                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default AddChapterPage;

