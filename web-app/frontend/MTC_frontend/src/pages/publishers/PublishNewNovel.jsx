import React, { useEffect, useState } from 'react';
import { CategoryDropdown, SimpleDropdown } from '../../common/CommonComponents.jsx';
import { Link, useNavigate } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import api from '../../middlewares/axios.js';
import { createNovel } from '../../services/publisherService.js';
import { showSuccess } from '../../utils/ToastUtils.js';
import { useSetMyPublishedNovels } from '../../stores/publisherStore.js';
function PublishNewNovel() {
    const navigate = useNavigate();
    const novelProgressStatus = useNovelProgressStatus();
    const novelAttributes = useNovelAttribute();
    const novelState = useNovelState();
    const novelVisibility = useNovelVisibility();
    const genres = useGenres();
    const mainCharacterTraits = useMainCharacterTrait();
    const sects = useSects();
    const worldScenes = useWorldScene();
    const novelTypes = useNovelType();
    const changePage = useSetPage();
    const setNovelProgressStatus = useSetNovelStatus();
    const [nextPart, setNextPart] = React.useState(false);
    const [novelData, setNovelData] = useState({
        name: "",
        displayName: "",
        slug: "",
        description: "",
        authorId: "00f76b9e-981f-4da8-bc8d-45573a97752d", // tạm hardcode
        genreIds: [],
        mainCharacterTraitIds: [],
        worldSceneIds: [],
        sectIds: [],
        status: ["69a8b5e9-deb2-4e49-86d5-3e214cea26a5"],
        novelTypes: ["TRANS"],
        novelAttributes: ["FREE"],
        novelStates: ["CREATED"],
        progressStatuses: ["IN_PROGRESS"]
    });

    const validateNovelData = (data) => {
        const errors = [];

        if (!data.name) errors.push("Tên truyện không được để trống");
        if (!data.genreIds.length) errors.push("Vui lòng chọn thể loại");
        if (!data.mainCharacterTraitIds.length) errors.push("Vui lòng chọn tính cách nhân vật chính");
        if (!data.worldSceneIds.length) errors.push("Vui lòng chọn bối cảnh thế giới");
        if (!data.sectIds.length) errors.push("Vui lòng chọn lưu phái");

        return errors;
    };

    const handleCreateNovel = async () => {
        // const errors = validateNovelData(novelData);
        // if (errors.length > 0) {
        //     // alert("Lỗi:\n" + errors.join("\n"));
        //     return;
        // }
        try {
            // gọi API ở đây, ví dụ:
            const res = await createNovel(novelData);
            navigate(`/bookhub/published`);
            showSuccess("Đăng truyện thành công!");
        } catch (e) {
            alert("Lỗi khi gửi dữ liệu!");
            console.error(e);
        }
    };

    const convertToSlug = (text) =>
        text
            .toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .replace(/[^a-z0-9 -]/g, "")
            .replace(/\s+/g, "-")
            .replace(/-+/g, "-")
            .replace(/^-+|-+$/g, "");

    const handleFieldChange = (field, value) => {
        setNovelData((prev) => {
            const updatedData = { ...prev, [field]: value };

            // Nếu thay đổi tên truyện thì tự cập nhật displayName và slug
            if (field === "name") {
                updatedData.displayName = value;
                updatedData.slug = convertToSlug(value);
            }

            return updatedData;
        });
    };
    useEffect(() => {
    }, [novelData]);
    const handleSelectField = (field, selected) => {


        setNovelData((prev) => ({
            ...prev,
            [field]: [selected.id], // chỉ lấy 1 giá trị, nếu là dropdown nhiều thì sửa thành [...prev[field], selected.id]
        }));
    };




    return (
        <div>
            <div className="flex flex-row gap-x-5 mt-3">
                <div className="w-1/2">
                        {nextPart ? (
                                <div className={'flex flex-col bg-background-light rounded-md p-5'}>
                                    <div className={'flex flex-col'}>

                                        <div className="flex items-center justify-center w-full">
                                            <label htmlFor="dropzone-file"
                                                   className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                                                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                                                    <svg className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
                                                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                                         viewBox="0 0 20 16">
                                                        <path stroke="currentColor" strokeLinecap="round"
                                                              strokeLinejoin="round"
                                                              strokeWidth="2"
                                                              d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                                                    </svg>
                                                    <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span
                                                        className="font-semibold">Click to upload</span> or drag and drop
                                                    </p>
                                                    <p className="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or
                                                        GIF
                                                        (MAX. 800x400px)</p>
                                                </div>
                                                <input id="dropzone-file" type="file" className="hidden" />
                                            </label>
                                        </div>
                                    </div>
                                    <div className={'mt-5'}>
                                        <div className={'flex flex-row justify-center items-center gap-x-5 w-full'}>
                                            <div className={'w-1/2'}>
                                                <button
                                                    className={'bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
                                                    onClick={() => handleCreateNovel()}
                                                >
                                                    <div className={'flex justify-center items-center gap-x-2'}>
                                                        <span className={'ml-2'}>Nhập Lại</span>
                                                        <FontAwesomeIcon icon={faDeleteLeft} />
                                                    </div>
                                                </button>
                                            </div>
                                            <div className={'w-1/2'}>
                                                <button
                                                    className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}

                                                >
                                                    <span onClick={handleCreateNovel}>
                                                        <div className={'flex justify-center items-center gap-x-2'}>
                                                            <span className={'ml-2'}>Đăng truyện</span>
                                                            <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                        </div>
                                                    </span>

                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ) :
                            (
                                <div className="flex flex-col rounded-md bg-background-light p-5">
                                    <div className="flex flex-col gap-y-4">
                                        <div>
                                            <span className="text-sm">Tên truyện</span>
                                            {/*<input*/}
                                            {/*    className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none"*/}
                                            {/*    value={novelData.name}*/}
                                            {/*    onChange={(e) => {*/}
                                            {/*        const name = e.target.value;*/}
                                            {/*        setNovelData({*/}
                                            {/*            ...novelData,*/}
                                            {/*            name,*/}
                                            {/*            displayName: name,*/}
                                            {/*            slug: convertToSlug(name),*/}
                                            {/*        });*/}
                                            {/*    }}*/}
                                            {/*/>*/}
                                            <input
                                                value={novelData.name}
                                                onChange={(e) => handleFieldChange('name', e.target.value)}
                                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none"
                                            />
                                        </div>

                                        <div>
                                            <span className="text-sm">Giới thiệu</span>
                                            <textarea
                                                value={novelData.description || ''}
                                                onChange={(e) => handleFieldChange('description', e.target.value)}
                                                className="w-full min-h-36 border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none"
                                            />
                                        </div>

                                        <div>
                                            {/*<span className="text-sm">Thể loại</span>*/}
                                            {/*<CategoryDropdown*/}
                                            {/*    dropdown={genres}*/}
                                            {/*    onSelect={(selected) =>*/}
                                            {/*        setNovelData({ ...novelData, genreIds: [selected.id] })*/}
                                            {/*    }*/}
                                            {/*/>*/}
                                            <div className={'flex flex-col mt-5 gap-y-2'}>
                                                <span className={'text-sm'}>Thể loại</span>
                                                <CategoryDropdown
                                                    dropdown={genres}
                                                    placeholder={'Chọn thể loại'}
                                                    onSelect={(selected) => handleSelectField('genreIds', selected)}
                                                />
                                            </div>
                                        </div>

                                        <div>
                                            <span className="text-sm">Tính cách nhân vật chính</span>
                                            <CategoryDropdown
                                                dropdown={mainCharacterTraits}
                                                placeholder={'Chọn tính cách nhân vật chính'}
                                                onSelect={(selected) => handleSelectField('mainCharacterTraitIds', selected)}
                                            />
                                        </div>

                                        <div>
                                            <span className="text-sm">Bối cảnh thế giới</span>
                                            <CategoryDropdown
                                                dropdown={worldScenes}
                                                placeholder={'Chọn bối cảnh thế giới'}
                                                onSelect={(selected) => handleSelectField('worldSceneIds', selected)}
                                            />
                                        </div>

                                        <div>
                                            <span className="text-sm">Lưu phái</span>
                                            <CategoryDropdown
                                                dropdown={sects}
                                                placeholder={'Chọn lưu phái'}
                                                onSelect={(selected) => handleSelectField('sectIds', selected)}
                                            />
                                        </div>
                                    </div>

                                    <div className="mt-5 flex justify-center items-center">
                                        <button
                                            className="bg-yellow-primary text-white rounded-md px-6 py-2 hover:bg-yellow-500 focus:outline-none"
                                            onClick={() => setNextPart(true)}
                                        >
                                            <div className="flex items-center gap-x-2">
                                                <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                <span>Đăng truyện</span>
                                            </div>
                                        </button>
                                    </div>
                                </div>

                            )
                        }

                </div>

                {/* Cột bên phải (tuỳ chọn thêm upload ảnh sau) */}
                <div className="w-1/2">
                    <div className="flex flex-col bg-background-light rounded-md p-5">
                        {/* Có thể chèn preview truyện, ảnh bìa, hoặc tag summary ở đây */}
                    </div>
                </div>
            </div>
        </div>
    );



    // return (
    //     <>
    //         <div>
    //             <div className={'flex flex-row gap-x-5 mt-3'}>
    //                 <div className={'w-1/2'}>
    //                     {nextPart ? (
    //                             <div className={'flex flex-col bg-background-light rounded-md p-5'}>
    //                                 <div className={'flex flex-col'}>
    //
    //                                     <div className="flex items-center justify-center w-full">
    //                                         <label htmlFor="dropzone-file"
    //                                                className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
    //                                             <div className="flex flex-col items-center justify-center pt-5 pb-6">
    //                                                 <svg className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
    //                                                      aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
    //                                                      viewBox="0 0 20 16">
    //                                                     <path stroke="currentColor" strokeLinecap="round"
    //                                                           strokeLinejoin="round"
    //                                                           strokeWidth="2"
    //                                                           d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
    //                                                 </svg>
    //                                                 <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span
    //                                                     className="font-semibold">Click to upload</span> or drag and drop
    //                                                 </p>
    //                                                 <p className="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or
    //                                                     GIF
    //                                                     (MAX. 800x400px)</p>
    //                                             </div>
    //                                             <input id="dropzone-file" type="file" className="hidden" />
    //                                         </label>
    //                                     </div>
    //                                 </div>
    //                                 <div className={'mt-5'}>
    //                                     <div className={'flex flex-row justify-center items-center gap-x-5 w-full'}>
    //                                         <div className={'w-1/2'}>
    //                                             <button
    //                                                 className={'bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
    //                                                 onClick={() => setNextPart(false)}
    //                                             >
    //                                                 <div className={'flex justify-center items-center gap-x-2'}>
    //                                                     <span className={'ml-2'}>Nhập Lại</span>
    //                                                     <FontAwesomeIcon icon={faDeleteLeft} />
    //                                                 </div>
    //                                             </button>
    //                                         </div>
    //                                         <div className={'w-1/2'}>
    //                                             <button
    //                                                 className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
    //
    //                                             >
    //                                                 <span onClick={handleUploadNovel}>
    //                                                     <div className={'flex justify-center items-center gap-x-2'}>
    //                                                         <span className={'ml-2'}>Đăng Truyện</span>
    //                                                         <FontAwesomeIcon icon={faArrowUpFromBracket} />
    //                                                     </div>
    //                                                 </span>
    //
    //                                             </button>
    //                                         </div>
    //                                     </div>
    //                                 </div>
    //                             </div>
    //                         ) :
    //                         (
    //                             <div className={'flex flex-col rounded-md'}>
    //                                 <div
    //                                     className={'flex flex-col gap-y-2 w-full p-2 pl-3 bg-background-light rounded-md'}>
    //                                     <div>
    //                                         <span className={'text-sm'}>Tên truyện</span>
    //                                         <input
    //                                             className={'w-full border border-gray-500 rounded-md p-1 pl-2  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
    //                                     </div>
    //                                     {/*<div className={'flex flex-col mt-5 gap-y-2'}>*/}
    //                                     {/*    <span className={'text-sm'}>Giới tính</span>*/}
    //                                     {/*    /!*<CategoryDropdown dropdown={} />*!/*/}
    //                                     {/*</div>*/}
    //                                     <div className={'flex flex-col mt-5 '}>
    //                                         <span className={'text-sm'}>Giới thiệu</span>
    //                                         <textarea
    //                                             className={'w-full min-h-36 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
    //                                     </div>
    //                                     <div className={'flex flex-col mt-5 gap-y-2'}>
    //                                         <span className={'text-sm'}>Thể loại</span>
    //                                         <CategoryDropdown dropdown={genres} />
    //                                     </div>
    //                                     <div className={'flex flex-col mt-5 gap-y-2'}>
    //                                         <span className={'text-sm'}>Tính cách nhân vật chính</span>
    //                                         <CategoryDropdown dropdown={mainCharacterTraits} />
    //                                     </div>
    //                                     <div className={'flex flex-col mt-5 gap-y-2'}>
    //                                         <span className={'text-sm'}>Bối cảnh thế giới</span>
    //                                         <CategoryDropdown dropdown={worldScenes} />
    //                                     </div>
    //                                     <div className={'flex flex-col mt-5 gap-y-2'}>
    //                                         <span className={'text-sm'}>Lưu phái</span>
    //                                         <CategoryDropdown dropdown={sects} />
    //                                     </div>
    //
    //
    //                                 </div>
    //                                 <div className={'mt-5'}>
    //                                     <div className={'flex flex-row justify-center items-center gap-x-5 w-full'}>
    //                                         <div className={'w-1/2'}>
    //                                             <button
    //                                                 className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
    //                                                 onClick={() => setNextPart(true)}
    //                                             >
    //                                                 <div className={'flex justify-center items-center gap-x-2'}>
    //                                                     <span className={'ml-2'}>Đăng Chương</span>
    //                                                     <FontAwesomeIcon icon={faArrowUpFromBracket} />
    //                                                 </div>
    //
    //                                             </button>
    //                                         </div>
    //                                     </div>
    //                                 </div>
    //                             </div>
    //                         )
    //                     }
    //                 </div>
    //                 <div className={"w-1/2"}>
    //                     <div className={'flex flex-col bg-background-light rounded-md p-5'}>
    //
    //                     </div>
    //                 </div>
    //             </div>
    //         </div>
    //     </>
    // );
}

export default PublishNewNovel;

