import React, { useEffect, useState } from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faAngleLeft,
    faAngleRight,
    faAnglesLeft,
    faAnglesRight,
    faBell, faBellSlash,
    faChevronRight,
    faX,
} from '@fortawesome/free-solid-svg-icons';
import { useBookShelf } from '../../stores/novelStore.js';
import {
    deleteBookmarkedNovel,
    deleteBookshelfItem,
    getMyBookmarkedNovels,
    getBookshelfItems,
    getCurrentBookshelf, updateBookmarkItem, updateBookshelfItem,
} from '../../services/bookshelfService.js';
import {
    useBookmarkedNovels,
    useBookshelfItems,
    useCurrentBookshelf, useSetBookmarkedNovels,
    useSetBookshelfItems,
    useSetCurrentBookshelf,
} from '../../stores/bookshelfStore.js';
import { timeAgo } from '../../utils/DatetimeUtil.js';
import { PageNavigator } from '../../components/global/Navigators.jsx';
import { NovelCoverImage_S } from '../../common/CommonComponents.jsx';
import { initPageData } from '../../utils/PageUtils.js';

function MyBookShelfPage() {
    const [tab, setTab] = React.useState(0);
    const setCurrentBookshelf = useSetCurrentBookshelf();
    getCurrentBookshelf().then((response) => {
        if (response.data.result) {
            setCurrentBookshelf(response.data.result);
        }
    });

    return (
        <>
            <div className={"flex flex-col"}>
                <div className={"px-20 mt-5"}>
                    {/*Quang cao*/}
                    <div className={"bg-gray-700 min-h-[120px]"}>

                    </div>
                </div>

                <div className={"flex flex-row justify-between items-center pt-5 px-20"}>
                    <div className={"flex flex-row justify-left items-center"}>
                        <div className={"background-color-lighter mr-1 px-2 rounded-md hover:cursor-pointer select-none"} onClick={(e)=>setTab(0)}>Truyện
                            đang đọc
                        </div>
                        <div className={"background-color-lighter px-2 rounded-md hover:cursor-pointer select-none"} onClick={(e)=>setTab(1)}>Truyện đánh
                            dấu
                        </div>

                    </div>
                </div>
                <div className={"min-h-[500px] px-20"}>
                    {tab === 0 ? <ReadingNovels/> : <BookmarkNovels/>}
                </div>
            </div>

        </>
    )
}

export default MyBookShelfPage;

function ReadingNovels() {
    const bookshelves = useBookShelf();
    const bookShelfItems = useBookshelfItems();
    const currentBookshelf = useCurrentBookshelf();
    const setBookshelfItems = useSetBookshelfItems();
    const [pageData, setPageData] = useState(initPageData());

    useEffect(() => {
        getBookshelfItems("693fdba8-5657-4625-8fd9-1c9f7bbbb5d5", pageData).then((response) => {
            if (response.data.result) {
                setBookshelfItems(response.data.result);
                console.log("Fetched novels:", response.data.result);

            } else {
                console.error("Failed to fetch bookshelf items.");
            }
        })
    }, [pageData]);

    const handleChangePage = (page) => {
        setPageData((prev) => ({
            ...prev,
            page: page,
        }));
    }

    const handlePageSizeChange = (size) => {
        setPageData((prev) => ({
            ...prev,
            size: size,
        }));

    }



    return (
        <>
            <div>
                <div className={""}>
                    <div className={""}>
                        {bookShelfItems.data && bookShelfItems.data.map((item, index) => {
                            return (
                                <div key={index} className={"m-2"}>
                                    <BookShelfNovelCard data={item}/>
                                </div>
                            )
                        })}
                    </div>
                    <div className={"flex justify-center mt-2"}>
                        <PageNavigator
                            page={bookShelfItems.currentPage}
                            pageSize={bookShelfItems.size}
                            totalPages={bookShelfItems.totalPages}
                            totalElements={bookShelfItems.totalElements}
                            onPageChange={handleChangePage}
                            onPageSizeChange={handlePageSizeChange}
                        />

                    </div>
                </div>
            </div>
        </>
    )

}

function NavigationBar() {
    return (
        <>
            <div>
                <div className={"flex w-[120[x] gap-x-2 items-center"}>

                    <FontAwesomeIcon icon={faAnglesLeft} className={"yellow-text-color"}/>
                    <FontAwesomeIcon icon={faAngleLeft} className={"yellow-text-color"}/>
                    <div className={"flex items-center text-sm mx-2 p-1 border-1 hover:border-yellow-500 rounded-md"}>
                        <input type={"number"} defaultValue={1} className={"w-[40px] focus:outline-none px-1"}/>
                        <span className={"text-gray-500 pr-1"}>/3</span>
                    </div>
                    <FontAwesomeIcon icon={faAngleRight} className={"yellow-text-color"}/>
                    <FontAwesomeIcon icon={faAnglesRight} className={"yellow-text-color"}/>
                </div>
            </div>
        </>
    )
}

function BookShelfNovelCard({data}) {
    console.log(data);
    const handleUpdateNotice = (novelId, isNoticed) => {
        const payload = {
            isNoticed: isNoticed
        };
        updateBookshelfItem(novelId, payload).then((response) => {
            if (response.data.result) {
                console.log("Updated notice status successfully");
            } else {
            }
        });
    }

    const handleDeleteItem = (readingNovelId) => {
        deleteBookshelfItem(readingNovelId, data.novel.id).then((response) => {
            if (response.data.result) {
                console.log("Deleted bookshelf item successfully");
                // Optionally, you can refresh the bookshelf items
                setPageData((prev) => ({
                    ...prev,
                    page: 1, // Reset to first page after deletion
                }));
            } else {
                console.error("Failed to delete bookshelf item");
            }
        });
    }

    return (
        <>
            <div
                className={
                    'relative overflow-x-auto shadow-md sm:rounded-lg select-none'
                }
            >
                <div
                    className={
                        'w-full flex flex-1 flex-row border-b border-gray-500 border-dotted items-center'
                    }
                >
                    <div className={'w-1/10'}>
                        <div className={'flex'}>
                            {/*<img*/}
                            {/*    className={*/}
                            {/*        'w-10 h-15 shadow-lg rounded mx-auto'*/}
                            {/*    }*/}
                            {/*    src={data.novel.novelCoverImage}*/}
                            {/*    alt={data.novel.name}*/}
                            {/*></img>*/}
                            <NovelCoverImage_S src={data.novel.novelCoverImage}
                                                    alt={data.novel.name} />
                        </div>
                    </div>
                    <div className={'w-7/10'}>
                        <div className={'flex flex-col'}>
                            <div className={'truncate'}>{data.novel.name}</div>
                            <div className={'text-gray-500 text-sm my-2'}>
                                Đã đọc: {data.currentChapterIdx}/
                                {data.novel.totalChapters}
                            </div>
                        </div>
                    </div>
                    <div className={'w-1/10 text-gray-500 text-xs'}>
                        {timeAgo(data.lastReadAt)}
                    </div>
                    <div className={'w-1/10 justify-end'}>
                        <FontAwesomeIcon
                            icon={faX}
                            onClick={() => handleDeleteItem(data.id)}
                            className={'mr-3 hover:cursor-pointer'}
                        />
                        {data.isNoticed ? (
                            <FontAwesomeIcon icon={faBell}
                                onClick={() => handleUpdateNotice(data.id, false)}
                            />
                        ) : (
                            <FontAwesomeIcon icon={faBellSlash}
                                onClick={() => handleUpdateNotice(data.id, true)}
                            />
                        )}


                    </div>
                </div>
            </div>
        </>
    );
}

function BookmarkNovels() {
    const bookmarkedNovels = useBookmarkedNovels();
    const setBookmarkedNovels = useSetBookmarkedNovels();
    const [pageData, setPageData] = useState(initPageData());

    const handleDeleteBookmark = (novelId) => {};




    const handleChangePage = (page) => {
        console.log("Changing page to:", page);

        setPageData((prev) => ({
            ...prev,
            page: page,
        }));
    }

    const handlePageSizeChange = (size) => {
        setPageData((prev) => ({
            ...prev,
            size: size,
        }));

    }
    useEffect(() => {
        getMyBookmarkedNovels(pageData).then((response) => {
            if (response.data.result) {
                setBookmarkedNovels(response.data.result);
                console.log("Fetched novels:", response.data.result);

            } else {
                console.error('Failed to fetch bookmarked novels.');
            }
        });
    }, [pageData]);

    return (
        <>
            {bookmarkedNovels.totalPages ?
                (
                    <div>
                        <div className={''}>
                            {bookmarkedNovels.data &&
                                bookmarkedNovels.data.map((item, index) => {
                                    return (
                                        <div key={index} className={'m-2 gap-y-2'}>
                                            <BookmarkNovelCard data={item} />
                                        </div>
                                    );
                                })}
                            <div className={'flex justify-center mt-2'}>
                                <PageNavigator
                                    page={bookmarkedNovels.currentPage}
                                    pageSize={bookmarkedNovels.size}
                                    totalPages={bookmarkedNovels.totalPages}
                                    totalElements={bookmarkedNovels.totalElements}
                                    onPageChange={handleChangePage}
                                    onPageSizeChange={handlePageSizeChange}
                                />
                            </div>
                        </div>
                    </div>

                )
                :
                (
                    <div className={'text-center text-gray-500'}>
                        Không có truyện nào được đánh dấu.
                    </div>
                )
            }
        </>
    );
}

function BookmarkNovelCard({ data }) {
    const handleDeleteBookmark = () => {
        deleteBookmarkedNovel(data.id).then((response) => {
            console.log("DE",response.data);

            if (response.data.result) {
                console.log("Deleted bookmark successfully");
            } else {
                console.error("Failed to delete bookmark");
            }
        });
    };


    return (
        <>
            <div>
                <div
                    className={
                        'flex flex-row border-b border-gray-500 border-dotted items-center'
                    }
                >
                    <div className={'w-1/10'}>
                        <div className={'flex'}>
                            {/*<img*/}
                            {/*    className={*/}
                            {/*        'w-10 h-15 shadow-lg rounded mx-auto'*/}
                            {/*    }*/}
                            {/*    src={data.novel.novelCoverImage}*/}
                            {/*    alt={data.novel.name}*/}
                            {/*></img>*/}
                            <NovelCoverImage_S
                                src={data.novel.novelCoverImage}
                                alt={data.novel.name}
                            />
                        </div>
                    </div>
                    <div className={'w-7/10'}>
                        <div className={'flex flex-col'}>
                            <div className={'truncate'}>{data.novel.name}</div>
                            <div className={'text-gray-500 text-sm my-2'}>
                                Đã đọc: {data.markedAtChapter + 1}/
                                {data.novel.totalChapters}
                            </div>
                        </div>
                    </div>
                    <div className={'w-1/10 text-right'} onClick={handleDeleteBookmark}>
                        <FontAwesomeIcon
                            icon={faX}
                            className={'mr-3 ml-auto hover:cursor-pointer'}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}