import React, { useEffect, useState } from 'react';
import { faChartLine, faFeather, faListUl, faLocationArrow, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { CategoryDropdown, SimpleDropdown } from '../../common/CommonComponents.jsx';
import { Link, useNavigate } from 'react-router';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import { getFilteredNovels } from '../../services/novelService.js';
import { extractFiltersFromStore } from '../../services/novelFilterService.js';
import { useCurrentNovelPublisher, useUser } from '../../stores/userStores.js';
import { usePublishedByPublisher, useSetPublishedByPublisher } from '../../stores/novelStore.js';
import { DefaultNavigator, PageNavigator } from '../../components/global/Navigators.jsx';
import {
    useMyPublishedNovels,
    usePublisherStore,
    useSetCurrentPublishedNovel,
    useSetMyPublishedNovels,
} from '../../stores/publisherStore.js';
import { initPageData } from '../../utils/PageUtils.js';


function PublishedNovelPage() {
    const user = useUser();
    const setMyPublishedNovels = useSetMyPublishedNovels();
    const myPublishedNovels = useMyPublishedNovels();
    const [pageData, setPageData] = useState(initPageData());

    const fetchNovels = async (page = 1, size = pageData.pageSize) => {
        if (!user?.id) return;

        try {
            const filter = {
                currentPublisher: "a3651d8d-f9e0-4a57-930f-5bbf9ff21a6d",
                page,
                size,
            };
            const response = await getFilteredNovels(filter);
            setMyPublishedNovels(response.data.result);
            setPageData({
                currentPage: page,
                pageSize: size,
                totalPages: response.data.result.totalPages,
                totalElements: response.data.result.totalElements,
            });
        } catch (err) {
            console.error("Error fetching novels:", err);
        }
    };

    useEffect(() => {
        fetchNovels(pageData.currentPage, pageData.pageSize).then(r => {});
    }, [user?.id, pageData.currentPage, pageData.pageSize]);


    return (
        <div>
            <PublishedNovelTable
                novels={myPublishedNovels}
                pageData={pageData}
                setPageData={setPageData}
                fetchNovels={fetchNovels}
            />
        </div>
    );
}

export default PublishedNovelPage;



const PublishedNovelTable = ({ novels, pageData, setPageData, fetchNovels }) => {
    const novelProgressStatus = useNovelProgressStatus();
    const handleChangePage = (page) => {
        setPageData((prev) => ({ ...prev, currentPage: page }));
    };

    const handlePageSizeChange = (newSize) => {
        setPageData((prev) => ({ ...prev, pageSize: newSize, currentPage: 1 }));
    };

    return (
        <div className="relative overflow-x-auto shadow-md sm:rounded-lg bg-white dark:bg-gray-800 rounded-md p-3 min-h-[500px]">
            {/* Header với các dropdown và search */}
            <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
                <div className="flex flex-row gap-x-2">
                    <SimpleDropdown />
                    <CategoryDropdown dropdown={novelProgressStatus} />
                </div>
                <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center ps-3 pointer-events-none">
                        <svg
                            className="w-5 h-5 text-gray-500"
                            aria-hidden="true"
                            fill="currentColor"
                            viewBox="0 0 20 20"
                            xmlns="http://www.w3.org/2000/svg"
                        >
                            <path
                                fillRule="evenodd"
                                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                clipRule="evenodd"
                            />
                        </svg>
                    </div>
                    <input
                        type="text"
                        id="table-search"
                        className="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
                        placeholder="Search for novels"
                    />
                </div>
                <Link to="/bookhub/new" className="bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600">
                    <FontAwesomeIcon icon={faPlus} /> Add Novel
                </Link>
            </div>

            {/* Bảng hiển thị novels */}
            <table className="w-full text-sm text-left text-gray-500">
                <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b">
                <tr>
                    <th scope="col" className="px-6 py-3">Tên truyện</th>
                    <th scope="col" className="px-6 py-3">Mô tả</th>
                    <th scope="col" className="px-6 py-3">Slug</th>
                    <th scope="col" className="px-6 py-3">Hành động</th>
                </tr>
                </thead>
                <tbody>
                {novels.data.map((novel) => (
                    <tr key={novel.id} className="border-b hover:bg-gray-50">
                        <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                            <div className="flex flex-col gap-y-2">
                                <div className="flex flex-row gap-x-2">
                                    <img
                                        className="h-10 w-10 shadow-xl rounded"
                                        loading="lazy"
                                        src={novel.novelCoverImage}
                                        alt=""
                                    />
                                    <div className="flex flex-col text-yellow-500">
                                        <span className="text-sm text-gray-400">{novel.displayName}</span>
                                    </div>
                                </div>
                            </div>
                        </th>
                        <td className="px-6 py-4">{novel.description}</td>
                        <td className="px-6 py-4">{novel.slug}</td>
                        <td className="px-6 py-4">
                            <SpecItem novelId={novel.id} novel={novel} />
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {/* Phân trang */}
            <div className="flex justify-end mt-4">
                <div className="mt-3">
                    <PageNavigator
                        page={pageData.currentPage}
                        pageSize={pageData.size}
                        totalPages={pageData.totalPages}
                        totalElements={pageData.totalElements}
                        onPageChange={handleChangePage}
                        onPageSizeChange={handlePageSizeChange}
                    />
                </div>
            </div>
        </div>
    );
};

const SpecItem = ({ data, novel }) => {
    const navigate = useNavigate();
    const setCurrentChosenPublishedNovel = useSetPublishedByPublisher();
    const setCurrentPublishedNovel = useSetCurrentPublishedNovel();

    function handleGotoChapterList() {
        setCurrentChosenPublishedNovel(novel.id);
        setCurrentPublishedNovel(novel);
        navigate(`/bookhub/novels/${novel.slug}/chapters`);
    }

    function handleGotoUploadChapter(){
        setCurrentChosenPublishedNovel(novel.id);
        setCurrentPublishedNovel(novel);
        navigate(`/bookhub/novels/${novel.slug}/upload-chapters`);
    }

    return (
        <>
            <div>
                <div className={"flex flex-row justify-end items-center gap-x-1"}>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 ml-3 hover:cursor-pointer hover:bg-gray-400"}>
                        <span onClick={handleGotoUploadChapter}>
                            <FontAwesomeIcon icon={faPlus} />
                        </span>
                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        {/*<Link to={`/bookhub/novels/${novel.slug}/chapters`}>*/}
                        {/*    <FontAwesomeIcon icon={faListUl} />*/}
                        {/*</Link>*/}
                        <span onClick={handleGotoChapterList}>
                            <FontAwesomeIcon icon={faListUl} />
                        </span>
                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={`/bookhub/novels/${novel.slug}/update`}>
                            <FontAwesomeIcon icon={faFeather} />
                        </Link>
                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={`/bookhub/novels/${novel.slug}/analytics`}>
                        <FontAwesomeIcon icon={faChartLine} />
                        </Link>

                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <FontAwesomeIcon icon={faLocationArrow} />

                    </div>
                </div>
            </div>
        </>
    )
}