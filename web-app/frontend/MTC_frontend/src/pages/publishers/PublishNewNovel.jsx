import React from "react";
import { CategoryDropdown, SimpleDropdown } from '../../common/CommonComponents.jsx';
import { Link } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';

function PublishNewNovel() {
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


    return (
        <>
            <div>
                <div className={"flex flex-row gap-x-5 mt-3"}>
                    <div className={"w-1/2"}>
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
                                                onClick={() => setNextPart(false)}
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
                                                <Link to={'/bookhub/books/1/chapters'}>
                                                    <div className={'flex justify-center items-center gap-x-2'}>
                                                        <span className={'ml-2'}>Đăng Chương</span>
                                                        <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                    </div>
                                                </Link>

                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            ) :
                            (
                                <div className={'flex flex-col rounded-md'}>
                                    <div
                                        className={'flex flex-col gap-y-2 w-full p-2 pl-3 bg-background-light rounded-md'}>
                                        <div>
                                            <span className={'text-sm'}>Tên truyện</span>
                                            <input
                                                className={'w-full border border-gray-500 rounded-md p-1 pl-2  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
                                        </div>
                                        {/*<div className={'flex flex-col mt-5 gap-y-2'}>*/}
                                        {/*    <span className={'text-sm'}>Giới tính</span>*/}
                                        {/*    /!*<CategoryDropdown dropdown={} />*!/*/}
                                        {/*</div>*/}
                                        <div className={'flex flex-col mt-5 '}>
                                            <span className={'text-sm'}>Giới thiệu</span>
                                            <textarea
                                                className={'w-full min-h-36 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
                                        </div>
                                        <div className={'flex flex-col mt-5 gap-y-2'}>
                                            <span className={"text-sm"}>Thể loại</span>
                                            <CategoryDropdown dropdown ={genres}/>
                                        </div>
                                        <div className={'flex flex-col mt-5 gap-y-2'}>
                                            <span className={"text-sm"}>Tính cách nhân vật chính</span>
                                            <CategoryDropdown dropdown ={mainCharacterTraits}/>
                                        </div>
                                        <div className={'flex flex-col mt-5 gap-y-2'}>
                                            <span className={"text-sm"}>Bối cảnh thế giới</span>
                                            <CategoryDropdown dropdown ={worldScenes}/>
                                        </div>
                                        <div className={'flex flex-col mt-5 gap-y-2'}>
                                            <span className={"text-sm"}>Lưu phái</span>
                                            <CategoryDropdown dropdown ={sects}/>
                                        </div>


                                    </div>
                                    <div className={'mt-5'}>
                                        <div className={'flex flex-row justify-center items-center gap-x-5 w-full'}>
                                            <div className={'w-1/2'}>
                                                <button
                                                    className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
                                                    onClick={() => setNextPart(true)}
                                                >
                                                    <div className={"flex justify-center items-center gap-x-2"}>
                                                        <span className={"ml-2"}>Đăng Chương</span>
                                                        <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                    </div>

                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    </div>
                    <div className={"w-1/2"}>
                        <div className={'flex flex-col bg-background-light rounded-md p-5'}>

                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default PublishNewNovel;

