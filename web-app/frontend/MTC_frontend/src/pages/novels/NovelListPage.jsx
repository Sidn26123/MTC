import React, { useEffect, useState } from 'react';
import AdvertiseItem from '../../components/global/AdvertiseItem.jsx';
import {
    useNovelList,
    useNovelListData,
    useSetListNovel,
    useSetListNovelPage,
    useSetListNovelPageSize,
} from '../../stores/novelStore.js';
import {API} from '../../configurations/configuration.js';
import { Link, useNavigate } from 'react-router';
import { DefaultNavigator, PageNavigator } from '../../components/global/Navigators.jsx';
import useFilterStore from '../../stores/novelFilterStore.js';
import { usePage, useSetPage, useSetSize, useUpdateFilterItem } from '../../stores/selectors/novelFilterSelector.js';
import { getFilteredNovels } from '../../services/novelService.js';
import { extractFiltersFromStore } from '../../services/novelFilterService.js';

// Custom hooks để thao tác với pagination



const NovelListPage = () => {
    const data = useNovelList();
    const page = usePage() || 0;
    const navigate = useNavigate();

    // Đảm bảo data có giá trị mặc định nếu chưa được tải
    const currentPage = data?.currentPage || 1;
    // const page = data?.page || 1;
    const pageSize = data?.pageSize || 10;
    const totalPages = data?.totalPages || 0;
    const totalElements = data?.totalElements || 0;
    const setListNovel = useSetListNovel();

    const setPage = useSetListNovelPage();

    const setSize = useSetListNovelPageSize();

    const handleChangePageInNavigator = (page) => {
    }
    

    const handleFilter = () => {

        getFilteredNovels(extractFiltersFromStore()).then(r => {
            console.log("NovelListPage -> handleFilter -> r", r);
            setListNovel(r.data.result);

        });

    }

    useEffect(() => {
        handleFilter();
    }, [page, pageSize]);


    return (
        <>
            <div className={'mx-24'}>
                <div className={'mb-5'}>
                    <AdvertiseItem />

                </div>
                {/*Novel list*/}
                <div className={'grid grid-cols-1 md:grid-cols-2 gap-6'}>
                    {data && data.data.map((novel, index) => (
                        <NovelItem key={index} novel={novel} />
                    ))}

                    {/*<NovelItem />*/}
                    {/*<NovelItem />*/}
                </div>
                <div className={'flex justify-center mt-5'}>
                    <PageNavigator
                        page={currentPage}
                        pageSize={pageSize}
                        totalPages={totalPages}
                        totalElements={totalElements}
                        onPageChange={setPage}
                        onPageSizeChange={setSize}
                    />
                </div>

            </div>
        </>
    );
}


const NovelItem = ({ novel }) => {
    const [showPreviewNovel, setShowPreviewNovel] = useState(false);
    const navigate = useNavigate();

    const getNovelUrl = (novel) => {
        return `/truyen/${novel.slug}`;
    }




    const handleNavigateNovel = (novel) => {
        // navigate(`/truyen/${novel.slug}`);
        navigate(getNovelUrl(novel));
    }
    return (
        <>
            <div>
                <div className="flex space-x-3 pb-6 border-b border-auto">
                    <div className="flex-shrink-0">
                        {/*Novel cover image*/}
                        <Link
                            className="text-title font-semibold"
                            // href="https://metruyencv.com/truyen/quy-bi-chi-chu"
                            // href = {`${novel.novelCoverImage}`}
                            // target="_blank"
                            to = {`https://metruyencv.com/truyen/${novel.slug}`}
                        >
                            <img
                                className="h-32 w-24 shadow-xl rounded"
                                loading="lazy"

                                // src="https://static.cdnno.com/poster/quy-bi-chi-chu/150.jpg?1585206121"
                                src = {`${novel.novelCoverImage}`}
                                alt=""
                            />
                        </Link>
                    </div>

                    {/*Navigate tới novel overview page*/}
                    <div className="flex-grow space-y-2">
                        {/*Title: Novel name*/}
                        <div>
                            {/*<a*/}
                            {/*    className="text-title font-semibold"*/}
                            {/*    // href="https://metruyencv.com/truyen/quy-bi-chi-chu"*/}
                            {/*    // href = {getNovelUrl(novel)}*/}
                            {/*    target="_blank"*/}
                            {/*>*/}
                            {/*    {novel.name}*/}
                            {/*</a>*/}
                            <span onClick={() => handleNavigateNovel(novel)} className={"hover:cursor-pointer text-title font-semibold"}>
                                {novel.name}
                            </span>
                        </div>
                        <div className="line-clamp-2 text-gray-500 text-overflow-multiple-lines break-all text-sm">
                            {/*Hơi nước cùng máy móc thủy triều bên trong, ai có thể chạm đến phi phàm? Lịch sử cùng hắc ám*/}
                            {/*trong*/}
                            {/*sương mù, là ai tại thì thầm? Ta theo quỷ bí bên trong tỉnh lại, mở mắt trông thấy cái thế*/}
                            {/*giới này:*/}
                            {novel.description}
                        </div>
                        <div className="flex justify-between space-x-2 pt-1">
                            <div className="flex items-center space-x-1">
                                <svg
                                    className="w-4 h-4 text-gray-500"
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                    aria-hidden="true"
                                >
                                    <path
                                        fillRule="evenodd"
                                        d="M7.5 6a4.5 4.5 0 1 1 9 0 4.5 4.5 0 0 1-9 0ZM3.751 20.105a8.25 8.25 0 0 1 16.498 0 .75.75 0 0 1-.437.695A18.683 18.683 0 0 1 12 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 0 1-.437-.695Z"
                                        clipRule="evenodd"
                                    ></path>
                                </svg>
                                {/*Author name*/}
                                <span className="text-title text-xs">{novel.author?.name}</span>
                            </div>
                            <span className="text-xs">{novel.totalChapters} chương</span>
                        </div>
                        <div className="flex justify-between text-gray-500">
                            <div className="flex items-center space-x-1">
                                <svg
                                    className="w-4 h-4"
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                    aria-hidden="true"
                                >
                                    <path
                                        d="M11.644 1.59a.75.75 0 0 1 .712 0l9.75 5.25a.75.75 0 0 1 0 1.32l-9.75 5.25a.75.75 0 0 1-.712 0l-9.75-5.25a.75.75 0 0 1 0-1.32l9.75-5.25Z"></path>
                                    <path
                                        d="m3.265 10.602 7.668 4.129a2.25 2.25 0 0 0 2.134 0l7.668-4.13 1.37.739a.75.75 0 0 1 0 1.32l-9.75 5.25a.75.75 0 0 1-.71 0l-9.75-5.25a.75.75 0 0 1 0-1.32l1.37-.738Z"></path>
                                    <path
                                        d="m10.933 19.231-7.668-4.13-1.37.739a.75.75 0 0 0 0 1.32l9.75 5.25c.221.12.489.12.71 0l9.75-5.25a.75.75 0 0 0 0-1.32l-1.37-.738-7.668 4.13a2.25 2.25 0 0 1-2.134-.001Z"></path>
                                </svg>
                                <span className="text-xs">{novel.sects[0]?.name}</span>
                            </div>
                            <div>
                                <button className="text-xs background-primary text-white py-1 px-2 rounded-md" onClick={() => setShowPreviewNovel(true)}>Đọc thử</button>
                            </div>
                        </div>
                    </div>

                </div>
                {/* Modal */}
                {showPreviewNovel && (
                    <div>
                        <div
                            className={'bg-gray-500 bg-opacity-75 transition-opacity z-40 fixed top-0 left-0 w-full h-full outline-none overflow-x-hidden'}>
                            <div className={'h-full max-w-screen-lg mx-auto relative w-auto pointer-events-none'}>
                                <div
                                    className={'min-h-screen max-h-full overflow-hidden border-none shadow-lg relative flex flex-col w-full pointer-events-auto bg-clip-padding bg-gray-50 dark:bg-[#272729] dark:text-gray-200 outline-none'}>
                                    <BookModal
                                        book={{
                                            name: novel.name,
                                            synopsis: novel.description,
                                        }}
                                        chapter={{
                                            name: 'Chương 1: ửng đỏ',
                                            content: 'Nội dung chương...',
                                        }}
                                        onClose={() => setShowPreviewNovel(false)}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                )}

            </div>

        </>
    )
}


const BookModal = ({ book, chapter, onClose }) => {
    const [isLoading, setIsLoading] = useState(false);

    return (
        <div className="h-full relative w-auto pointer-events-none">
            <div
                className="min-h-screen overflow-hidden border-none shadow-lg relative flex flex-col w-full pointer-events-auto bg-clip-padding bg-panel dark:text-gray-200 outline-none">
                {/* Header */}
                <div className="flex flex-shrink-0 items-center justify-between p-4 border-b border-auto">
                    <div className="font-medium">
                        <span className="font-bold">{book.name}</span>
                    </div>
                    <button
                        type="button"
                        className="relative rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none"
                        onClick={onClose}
                    >
                        <span className="absolute -inset-2.5"></span>
                        <span className="sr-only">Close panel</span>
                        <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                             strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"></path>
                        </svg>
                    </button>
                </div>

                {/* Body */}
                <div className="flex-auto overflow-y-auto overscroll-none relative px-4 pt-4 pb-12">
                    <div className="space-y-3 text-base break-words">
                        {/* Giới thiệu */}
                        <p><span className="font-medium">Giới thiệu:</span></p>
                        <div>
                            {book.synopsis.split("\n").map((line, index) => (
                                <p key={index}>{line}</p>
                            ))}
                        </div>

                        <p>==============================</p>

                        {/* Loading Indicator */}
                        {isLoading && (
                            <div className="flex justify-center">
                                <svg className="inline animate-spin w-8 h-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"></path>
                                </svg>
                            </div>
                        )}

                        {/* Hiển thị nội dung chương */}
                        {!isLoading && chapter && (
                            <div>
                                <p className="font-medium mb-3">{chapter.name}</p>
                                <div>
                                    {chapter.content ? (
                                        chapter.content.split("\n").map((line, index) => (
                                            <p key={index}>{line}</p>
                                        ))
                                    ) : (
                                        <p>null</p>
                                    )}
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}



export default NovelListPage;

