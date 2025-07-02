import React, { useEffect } from 'react';
import LoadingSpinning from "../global/LoadingSpinning.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import { DefaultNavigator } from '../global/Navigators.jsx';
import { useChapterActions, useListChapter } from '../../stores/chapterStore.js';
import { useCurrentNovel } from '../../stores/novelStore.js';
import { useLocation, useNavigate } from 'react-router';

const ChapterListPanel = ({onClose}) => {
    const [loading, setLoading] = React.useState(true);
    const [mode, setMode] = React.useState("chapter");

    const chapterList = useListChapter();
    const currentNovel = useCurrentNovel();
    const fetchListChapter = useChapterActions().fetchListChapter;

    useEffect(() => {
        if (currentNovel.id !== undefined && currentNovel.id !== null && chapterList.length === 0) {
            setLoading(true);
            fetchListChapter(currentNovel.slug, 1, 20).then(() => {
                setLoading(false);
            });

        }
    },[currentNovel])

    return (
        <>
            <div className={"bg-gray-500 bg-opacity-75 transition-opacity z-40 fixed top-0 left-0 w-full h-full outline-none overflow-x-hidden"}>
                <div className={"h-full max-w-screen-lg mx-auto relative w-auto pointer-events-none"}>
                    <div
                        className={"min-h-screen max-h-full overflow-hidden border-none shadow-lg relative flex flex-col w-full pointer-events-auto bg-clip-padding bg-gray-50 dark:bg-[#272729] dark:text-gray-200 outline-none"}>
                        {/*Header cua ds chuong tab*/}
                        <div className="flex flex-shrink-0 items-center justify-between p-4 border-b border-auto border-gray-500">
                            <div className="font-medium"><span data-x-text="book.name" className="font-bold">Tận Thế Sương Mù: Bắt Đầu Thu Lưu Song Bào Thai Tỷ Muội!</span>
                            </div>
                            <button data-x-bind="CloseModal" type="button"
                                    className="relative rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none"
                                    onClick={onClose}
                            >
                                <span className="absolute -inset-2.5"></span><span
                                className="sr-only">Close panel</span>
                                <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                                     viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true"
                                     data-slot="icon">
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M6 18 18 6M6 6l12 12"></path>
                                </svg>
                            </button>

                        </div>
                        {/*Chapter list*/}
                        <div className={"flex-auto overflow-y-auto overscroll-none relative px-4 pb-24"}>
                            <div className={"relative flex-1"}>
                                {/*Navigator*/}
                                <div className="border-b border-gray-200">
                                    <nav className="-mb-px flex" aria-label="Tabs">
                                        <template data-x-for="tab in data">
                                            <button data-x-bind="TabItem(tab)"></button>
                                        </template>
                                        <button onClick={() => setMode("chapter")}
                                                className="primary-text-color  w-1/2 border-b-2  py-4 px-1 text-center text-sm font-medium">DS
                                            Chương
                                        </button>
                                        <button onClick={() => setMode("reading")}
                                                className="border-transparent text-title hover:border-gray-300 w-1/2 border-b-2 py-4 px-1 text-center text-sm font-medium">Đang
                                            Đọc
                                        </button>
                                        <button onClick={() => setMode("bookmark")}
                                                className="border-transparent text-title hover:border-gray-300 w-1/2 border-b-2 py-4 px-1 text-center text-sm font-medium">Đánh
                                            Dấu
                                        </button>
                                    </nav>
                                </div>
                                {/*Chapter*/}
                                {mode === "chapter" ? (
                                    <div id = "chapters">
                                        <ChapterList chapterList= {chapterList}/>
                                    </div>


                                ): mode === "reading" ? (
                                    <div id = "reading">
                                        <ReadingChapter />
                                    </div>
                                ) :
                                mode === "bookmark" && (
                                    <div id = "bookmark">
                                        <Bookmark />
                                    </div>
                                    )}
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </>
    )
}

export default ChapterListPanel;

const ChapterList = ({chapterList}) => {
    // var data = {
    //     "id": 16845491,
    //     "name": "Chương 1: Mục tiêu là kia đôi song bào thai!",
    //     "published_at": "2023-10-02 09:47:13",
    //     "unlock_price": false,
    //     url: "https://metruyencv.com/truyen/tan-the-suong-mu-bat-dau-thu-luu-song-bao-thai-ty-muoi/chuong-1"
    // }
    // console.log("ChapterList: ", chapterList);
    // const chapterList = useListChapter();
    // useEffect(() => {
    //     if ()
    // })
    // console.log(chapterList);

    return (
        <>
            <div className={"pt-6 px-4 md:px-2"}>
                {/*Sort*/}
                <div className="flex justify-between mb-6">
                    <button data-x-bind="SortToc" className="primary-text-color">
                        <svg data-x-show="tocSort === 'asc'"
                             xmlns="http://www.w3.org/2000/svg"
                             width="24" height="24" viewBox="0 0 24 24" strokeWidth="2"
                             stroke="currentColor" fill="none" strokeLinecap="round"
                             strokeLinejoin="round">
                            <path d="M4 15l3 3l3 -3"></path>
                            <path d="M7 6v12"></path>
                            <path
                                d="M17 3a2 2 0 0 1 2 2v3a2 2 0 1 1 -4 0v-3a2 2 0 0 1 2 -2z"></path>
                            <path d="M17 16m-2 0a2 2 0 1 0 4 0a2 2 0 1 0 -4 0"></path>
                            <path d="M19 16v3a2 2 0 0 1 -2 2h-1.5"></path>
                        </svg>
                        <svg data-x-show="tocSort !== 'asc'"
                             xmlns="http://www.w3.org/2000/svg"
                             width="24" height="24" viewBox="0 0 24 24" strokeWidth="2"
                             stroke="currentColor" fill="none" strokeLinecap="round"
                             strokeLinejoin="round" style={{display: 'none'}}>
                            <path d="M4 15l3 3l3 -3"></path>
                            <path d="M7 6v12"></path>
                            <path
                                d="M17 14a2 2 0 0 1 2 2v3a2 2 0 1 1 -4 0v-3a2 2 0 0 1 2 -2z"></path>
                            <path d="M17 5m-2 0a2 2 0 1 0 4 0a2 2 0 1 0 -4 0"></path>
                            <path d="M19 5v3a2 2 0 0 1 -2 2h-1.5"></path>
                        </svg>
                    </button>
                    <button data-x-bind="ScrollTo"
                            className="flex items-center primary-text-color"
                            style={{display: 'true'}}> Xuống chương hiện tại <svg
                        xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                        viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor"
                        fill="none"
                        strokeLinecap="round" strokeLinejoin="round">
                        <path d="M12 4a4 4 0 1 1 0 8a4 4 0 0 1 0 -8z"></path>
                        <path d="M12 12v8"></path>
                        <path d="M9 17l3 3l3 -3"></path>
                    </svg></button>
                </div>
                {/*    Chapter content*/}
                <div className={"grid grid-cols-1 gap-4 sm:grid-cols-2"}>
                    {/*<a data-x-bind="ChapterItem(index)"*/}
                    {/*   className="space-y-1 border-b border-auto pb-2 primary-text-color visited:text-gray-500 col-span-1"*/}
                    {/*   href="https://metruyencv.com/truyen/tan-the-suong-mu-bat-dau-thu-luu-song-bao-thai-ty-muoi/chuong-1"*/}
                    {/*   id="chapter-16845491">*/}
                    {/*    <div className="text-sm md:text-base font-medium"*/}
                    {/*         >Chương 1: Mục tiêu là kia đôi song*/}
                    {/*        bào thai!*/}
                    {/*    </div>*/}
                    {/*    <div className="flex items-center text-xs text-gray-400"><span*/}
                    {/*        data-x-text="dayjs(chapter.published_at).format('YYYY-MM-DD HH:mm:ss')">2023-10-02 09:47:13</span>*/}
                    {/*        <svg data-x-show="chapter.unlock_price" className="ml-2 w-3 h-3"*/}
                    {/*             xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"*/}
                    {/*             fill="currentColor" aria-hidden="true" data-slot="icon"*/}
                    {/*             style={{display: 'none'}}>*/}
                    {/*            <path fillRule="evenodd"*/}
                    {/*                  d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z"*/}
                    {/*                  clipRule="evenodd"></path>*/}
                    {/*        </svg>*/}
                    {/*    </div>*/}
                    {/*</a>*/}
                    {chapterList.map((chapter, index) => (
                        <ChapterListItem chapter={chapter}/>

                    ))}
                    {/*<Bookmark />*/}
                </div>
            </div>
        </>
    )
}


const ChapterListItem = ({chapter}) => {
    const navigate = useNavigate();
    const location = useLocation();
    const handleClick = (e) => {
        e.preventDefault();
        navigate(`${location.pathname}/chuong-${chapter.chapterIdx}`);
    }

    return (
        <div
            className="space-y-1 border-b border-auto pb-2 primary-text-color visited:text-gray-500 col-span-1 hover:cursor-pointer"
            // href={`${chapter.url}`}
            id={`chapter-${chapter.id}`}
            onClick=    {handleClick}

        >
            <div className="text-sm md:text-base font-medium">
                {chapter.name}
            </div>
            <div className="flex items-center text-xs text-gray-400">
                <span>
                    {new Date(chapter.publishedAt).toLocaleString("vi-VN")}
                </span>
                {chapter.unlock_price && (
                    <svg
                        className="ml-2 w-3 h-3"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        aria-hidden="true"
                    >
                        <path
                            fillRule="evenodd"
                            d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z"
                            clipRule="evenodd"
                        ></path>
                    </svg>
                )}
            </div>
        </div>
    );
};

const ReadingChapter = () => {
    return (
        <>
            <div>
                <div data-x-data="readings($data.book.id)" className="my-6">
                    <div data-x-bind="ReadingNote" className="text-center text-title italic">Bạn chưa đọc truyện này
                    </div>
                    <template data-x-if="list.length > 0">
                        <template data-x-for="(reading, index) in list">
                            <div className="flex items-center justify-between py-2 mx-2 border-b border-auto"><a
                                data-x-bind="ReadingUrl(index)" className="space-y-1 primary-text-color">
                                <div className="text-sm font-medium" data-x-text="reading.chapter.name"></div>
                                <div className="flex items-center text-xs text-gray-400"><span
                                    data-x-text="dayjs(reading.updated_at).fromNow()"></span></div>
                            </a>
                                <button className="outline outline-1 px-2 primary-text-color disabled:bg-gray-500"
                                        data-x-bind="ReadingRemove(reading, 0)"><span className="text-xs">x</span>
                                </button>
                            </div>
                        </template>
                    </template>
                </div>
            </div>
        </>
    )
}


const Bookmark = () => {
    return (
        <>
            <div>
                <div>
                    <div className="my-6">

                        <BookmarkItem />
                    </div>
                </div>
            </div>
        </>
    )
}

const BookmarkItem = ({chapter}) => {
    return (
        <>
            <div>
                <div className="flex items-center justify-between py-2 mx-2 border-b border-gray-500">
                    <a
                        className="space-y-1 primary-text-color"
                        href="https://metruyencv.com/truyen/tan-the-suong-mu-bat-dau-thu-luu-song-bao-thai-ty-muoi/chuong-1">
                        <div className="text-sm font-medium"
                             data-x-text="bookmark.chapter.name ?? bookmark.book.name">Chương 1: Mục tiêu là kia đôi
                            song bào thai!
                        </div>
                        <span className="text-gray-400 text-xs"
                              data-x-text="dayjs(bookmark.created_at).format('YYYY-MM-DD HH:mm:ss')">2025-03-16 00:38:44</span>
                    </a>
                    <button className=" px-2 primary-text-color"
                            data-x-bind="BookmarkRemove(bookmark, index)"><span className="text-xs ">
                                <FontAwesomeIcon icon={faTrashCan}/>
                            </span>
                    </button>
                </div>
            </div>
        </>
    );
};