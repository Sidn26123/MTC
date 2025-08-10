import React, { useEffect, useState } from 'react';
import LoadingSpinning from "../global/LoadingSpinning.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import { DefaultNavigator } from '../global/Navigators.jsx';
import { useChapterActions, useListChapter } from '../../stores/chapterStore.js';
import { useCurrentNovel } from '../../stores/novelStore.js';
import { Link, useLocation, useNavigate } from 'react-router';
import {
    getItemOfBookshelfByNovelId,
    getListMarkedChapter,
    getMyBookmarkedNovels,
} from '../../services/bookshelfService.js';
import { useBookmarkedNovels, useCurrentBookshelf, useSetBookmarkedNovels } from '../../stores/bookshelfStore.js';
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import { initPageData } from '../../utils/PageUtils.js';
import { useUser } from '../../stores/userStores.js';
import { getChapterByNovelSlugAndIdx } from '../../services/chapterService.js';
import { formatPublishDateTime, getFormattedTimeForApi, timeAgo } from '../../utils/DatetimeUtil.js';
dayjs.extend(relativeTime);
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
            <div
                className={
                    'bg-gray-500 bg-opacity-75 transition-opacity z-40 fixed top-0 left-0 w-full h-full outline-none overflow-x-hidden'
                }
            >
                <div
                    className={
                        'h-full max-w-screen-lg mx-auto relative w-auto pointer-events-none'
                    }
                >
                    <div
                        className={
                            'min-h-screen max-h-full overflow-hidden border-none shadow-lg relative flex flex-col w-full pointer-events-auto bg-clip-padding bg-gray-50 dark:bg-[#272729] dark:text-gray-200 outline-none'
                        }
                    >
                        {/*Header cua ds chuong tab*/}
                        <div className="flex flex-shrink-0 items-center justify-between p-4 border-b border-auto border-gray-500">
                            <div className="font-medium">
                                <span
                                    data-x-text="book.name"
                                    className="font-bold"
                                >
                                    {currentNovel.name}
                                </span>
                            </div>
                            <button
                                data-x-bind="CloseModal"
                                type="button"
                                className="relative rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none"
                                onClick={onClose}
                            >
                                <span className="absolute -inset-2.5"></span>
                                <span className="sr-only">Close panel</span>
                                <svg
                                    className="w-6 h-6"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth="1.5"
                                    stroke="currentColor"
                                    aria-hidden="true"
                                    data-slot="icon"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        d="M6 18 18 6M6 6l12 12"
                                    ></path>
                                </svg>
                            </button>
                        </div>
                        {/*Chapter list*/}
                        <div
                            className={
                                'flex-auto overflow-y-auto overscroll-none relative px-4 pb-24'
                            }
                        >
                            <div className={'relative flex-1'}>
                                {/*Navigator*/}
                                <div className="border-b border-gray-200">
                                    <nav
                                        className="-mb-px flex"
                                        aria-label="Tabs"
                                    >
                                        <template data-x-for="tab in data">
                                            <button data-x-bind="TabItem(tab)"></button>
                                        </template>
                                        <button
                                            onClick={() => setMode('chapter')}
                                            className={`w-1/3 py-4 px-1 text-center text-sm font-medium border-b-2 
                                            ${mode === 'chapter' ? 'border-primary text-primary font-bold ' : 'border-transparent text-title hover:border-gray-300'}`}
                                        >
                                            DS Chương
                                        </button>

                                        <button
                                            onClick={() => setMode('reading')}
                                            className={`w-1/3 py-4 px-1 text-center text-sm font-medium border-b-2 
                                            ${mode === 'reading' ? 'border-primary text-primary font-bold' : 'border-transparent text-title hover:border-gray-500'}`}
                                        >
                                            Đang Đọc
                                        </button>

                                        <button
                                            onClick={() => setMode('bookmark')}
                                            className={`w-1/3 py-4 px-1 text-center text-sm font-medium border-b-2 
                                            ${mode === 'bookmark' ? 'border-primary text-primary font-bold' : 'border-transparent text-title hover:border-gray-500'}`}
                                        >
                                            Đánh Dấu
                                        </button>
                                    </nav>
                                </div>
                                {/*Chapter*/}
                                {mode === 'chapter' ? (
                                    <div id="chapters">
                                        <ChapterList
                                            chapterList={chapterList}
                                        />
                                    </div>
                                ) : mode === 'reading' ? (
                                    <div id="reading">
                                        <ReadingChapter />
                                    </div>
                                ) : (
                                    mode === 'bookmark' && (
                                        <div id="bookmark">
                                            <Bookmark />
                                        </div>
                                    )
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
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
        // navigate(`${location.pathname}/chuong-${chapter.chapterIdx}`);
        navigate(`chuong-${chapter.chapterIdx}`, {

        })
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
    const currentNovel = useCurrentNovel();
    const currentBookshelf = useCurrentBookshelf();
    const [readingChapter, setReadingChapter] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!currentNovel?.id || !currentBookshelf?.id) return;
        getItemOfBookshelfByNovelId(currentBookshelf.id, currentNovel.id)
            .then((r) => {
                const result = r.data?.result;
                if (result) {
                    setReadingChapter(result);
                } else {
                    setReadingChapter(null);
                }
            })
            .finally(() => setLoading(false));
    }, [currentNovel?.id, currentBookshelf?.id]);

    if (loading) {
        return <div className="text-center my-6">Đang tải dữ liệu chương đã đọc...</div>;
    }

    if (!readingChapter || !readingChapter.novel || readingChapter.currentChapterIdx == null) {
        return (
            <div className="text-center my-6 italic text-title">
                Bạn chưa đọc truyện này.
            </div>
        );
    }

    const chapterName = `Chương ${readingChapter.currentChapterIdx}`;
    const updatedAt = dayjs(readingChapter.updatedAt).fromNow();

    return (
        <div className="my-6">
            <div className="text-sm text-center mb-2 italic text-title">
                Bạn đã đọc tới:
            </div>
            <div className="flex items-center justify-between py-2 mx-2 border-b border-gray-300">
                <Link to={`truyen/${readingChapter.novel.slug}/chuong-${readingChapter.currentChapterIdx}`} className="space-y-1 text-primary">
                    <div className="text-sm font-medium">{chapterName}</div>
                    <div className="flex items-center text-xs text-gray-400">
                        <span>{updatedAt}</span>
                    </div>
                </Link>
                <button
                    className="outline outline-1 px-2 text-primary hover:bg-gray-100"
                    onClick={() => {
                        // TODO: thêm chức năng xóa nếu muốn
                        setReadingChapter(null);
                    }}
                >
                    <span className="text-xs">x</span>
                </button>
            </div>
        </div>
    );
};


const Bookmark = () => {
    const [bookmarkChapter, setBookmarkChapter] = useState([]);
    const [loading, setLoading] = useState(true);

    const user = useUser();
    const currentNovel = useCurrentNovel();
    const [pageData, setPageData] = useState(initPageData());

    useEffect(() => {
        if (!user?.id || !currentNovel?.id) return;

        setLoading(true);
        getListMarkedChapter(user.id, currentNovel.id, pageData)
            .then((response) => {
                if (response.data.result) {
                    console.log("Fetched bookmarked chapters:", response.data.result);
                    setBookmarkChapter(response.data.result);
                } else {
                    setBookmarkChapter([]);
                    console.error("Failed to fetch bookmarked chapters.");
                }
            })
            .catch((error) => {
                setBookmarkChapter([]);
                console.error("API error:", error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [user?.id, currentNovel?.id]);

    return (
        <div className="my-6">
            {loading ? (
                <div className="text-center text-gray-500 animate-pulse py-6">Đang tải đánh dấu...</div>
            ) : bookmarkChapter.length === 0 ? (
                <div className="text-center text-gray-400 py-6">Bạn chưa đánh dấu chương nào.</div>
            ) : (
                bookmarkChapter && bookmarkChapter.data.map((bookmark, index) => (
                    <BookmarkItem chapter={bookmark} key={index} />
                ))
            )}
        </div>
    );
};

const BookmarkItem = ({chapter}) => {

    const [data, setData] = useState({  });
    useEffect(() => {
        getChapterByNovelSlugAndIdx(chapter.novel.slug, chapter.markedAtChapter).then(r => {
            setData(r.data.result);
            console.log("BookmarkItem data: ", r.data.result);
        })
    },[])


    function handleDeleteMarkedItem() {

    }

    return (
        <>
            <div>
                <div className="flex items-center justify-between py-2 mx-2 border-b border-gray-500">
                    <a
                        className="space-y-1 primary-text-color"
                        href="https://metruyencv.com/truyen/tan-the-suong-mu-bat-dau-thu-luu-song-bao-thai-ty-muoi/chuong-1">
                        <div className="text-sm font-medium"
                             data-x-text="bookmark.chapter.name ?? bookmark.book.name">{data.name}
                        </div>
                        <span className="text-gray-400 text-xs"
                              data-x-text="dayjs(bookmark.created_at).format('YYYY-MM-DD HH:mm:ss')">{data &&(data.publishedAt)}</span>
                    </a>
                    <button className=" px-2 primary-text-color"
                            onClick = {handleDeleteMarkedItem}
                            ><span className="text-xs ">
                                <FontAwesomeIcon icon={faTrashCan}/>
                            </span>
                    </button>
                </div>
            </div>
        </>
    );
};

