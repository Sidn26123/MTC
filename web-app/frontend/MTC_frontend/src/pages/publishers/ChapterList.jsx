import React, { useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDeleteLeft, faPlus } from '@fortawesome/free-solid-svg-icons';
import {faPenToSquare, faTrashCan} from '@fortawesome/free-regular-svg-icons';
import { SimpleDropdown } from '../../common/CommonComponents.jsx';
import { DefaultNavigator } from '../../components/global/Navigators.jsx';
import { Link } from 'react-router';
import { useCurrentNovelPublisher } from '../../stores/userStores.js';
import {
    useCurrentChapterList, useCurrentChapters,
    useCurrentChosenPublishedNovel,
    usePublisherStore,
    useSetCurrentChapterList, useSetCurrentChapters,
} from '../../stores/publisherStore.js';
import { getFilteredNovels } from '../../services/novelService.js';
import { deleteChapter, getFilteredChapters } from '../../services/chapterService.js';
import CommonTable from '../../components/common/CommonTable.jsx';
const ChapterList = () => {
    const currentPublishedNovelChosen = useCurrentChosenPublishedNovel();
    const chapterList = useCurrentChapters();
    const setCurrentChapterList = useSetCurrentChapters();
    useEffect(() => {
        const fetchNovels = async () => {
            try {
                const filter = { novelId: currentPublishedNovelChosen.id };
                const response = await getFilteredChapters(filter);

                setCurrentChapterList(response.data.result);
            } catch (err) {
            }
        };

        fetchNovels().then(r => {});
    }, [currentPublishedNovelChosen]);


    const handleChangePage = (page) => {
        // updateData({ currentPage: page });
    };

    const handlePageSizeChange = (newSize) => {
        // updateData({ pageSize: newSize, currentPage: 1 });
    };

    const handleSearch = (query) => {
        // updateData({ currentPage: 1, search: query });
        // fetchChapters();
    };

    const toolbar = (
        <>
            <div className="flex flex-col">
                <span className="text-lg">Danh sách chương</span>
                <span className="text-gray-500">
                    {currentPublishedNovelChosen.name}
                </span>
            </div>
            <div className="flex flex-row gap-x-2">
                <Link
                    to={`/bookhub/novels/${currentPublishedNovelChosen.id}/update`}
                >
                    <button className="bg-cus-gray text-white rounded-md p-2 hover:bg-yellow-500">
                        Sửa
                    </button>
                </Link>
                <Link
                    to={`/bookhub/novels/${currentPublishedNovelChosen.slug}/upload-chapters`}
                >
                    <button className="bg-cus-gray text-white rounded-md p-2 hover:bg-yellow-500">
                        Thêm
                    </button>
                </Link>
            </div>
            <div className="flex flex-row gap-x-2">
                <Link
                    to={`/bookhub/novels/${currentPublishedNovelChosen.id}/update`}
                >
                    <button className="bg-cus-gray text-white rounded-md p-2 hover:bg-yellow-500">
                        Sửa
                    </button>
                </Link>
                <Link
                    to={`/bookhub/novels/${currentPublishedNovelChosen.slug}/upload-chapters`}
                >
                    <button className="bg-cus-gray text-white rounded-md p-2 hover:bg-yellow-500">
                        Thêm
                    </button>
                </Link>
            </div>
        </>
    );

    const renderRow = (chapter, index) => {
        console.log('Rendering chapter:', chapter);
        const now = new Date();
        let timeColor = "text-gray-300"; // mặc định

        if (!chapter.isPublished && chapter.publishedAt) {
            const publishedDate = new Date(chapter.publishedAt);
            if (now < publishedDate) {
                timeColor = "text-yellow-400"; // chưa tới giờ xuất bản
            } else if (now >= publishedDate) {
                timeColor = "text-red-500"; // đã qua giờ nhưng chưa publish
            }
        }

        return (
            <tr
                key={chapter.id}
                className="border-b border-gray-500 hover:bg-gray-500/10"
            >
                <td className="px-6 py-4 font-medium text-gray-300 whitespace-nowrap">
                    {chapter.chapterIdx}
                </td>

                <td className="px-6 py-4">{chapter.name}</td>
                <td className={`px-6 py-4 ${timeColor}`}>
                    {chapter.publishedAt
                        ? new Date(chapter.publishedAt).toLocaleString()
                        : 'Chưa xuất bản'}
                </td>
                <td className="px-6 py-4">{chapter.wordCount ?? 20}</td>
                <td className="px-6 py-4">{chapter.viewCount ?? 0}</td>
                <td className="px-6 py-4">
                    <SpecItem item={chapter} />
                </td>
            </tr>
        );
    };

    return (
        <>
            <div>
                <div className={'flex flex-col'}>
                    <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                        {/*Head part*/}
                        {/*<div className={"flex flex-row justify-between items-center"}>*/}
                        {/*    <div className={"flex flex-col"}>*/}
                        {/*        <span className={"text-lg"}>Danh sách chương</span>*/}
                        {/*        <span className={"text-gray-500"}>{currentPublishedNovelChosen.name}</span>*/}
                        {/*    </div>*/}
                        {/*    <div className={"flex flex-row gap-x-2"}>*/}
                        {/*        <Link to={'/bookhub/novels/1/update'}>*/}
                        {/*            <button*/}
                        {/*                className={'bg-cus-gray w-full text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}*/}

                        {/*            >*/}
                        {/*                <div className={'flex justify-center items-center gap-x-2'}>*/}
                        {/*                    <span className={'min-w-20'}>Sửa</span>*/}
                        {/*                </div>*/}
                        {/*            </button>*/}
                        {/*        </Link>*/}

                        {/*        <Link to={`/bookhub/novels/${currentPublishedNovelChosen.slug}/upload-chapters`}>*/}
                        {/*            <button*/}
                        {/*                className={'bg-cus-gray w-full text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}*/}

                        {/*            >*/}
                        {/*                <div className={'flex justify-center items-center gap-x-2'}>*/}
                        {/*                    <span className={'min-w-20'}>Thêm</span>*/}
                        {/*                </div>*/}
                        {/*            </button>*/}
                        {/*        </Link>*/}
                        {/*    </div>*/}
                        {/*</div>*/}

                        {/*Search navigator*/}
                        {/*<div className={"mt-4"}>*/}
                        {/*    <div*/}
                        {/*        className="flex flex-column sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4 ">*/}
                        {/*        <div className={"flex flex-row gap-x-2"}>*/}
                        {/*            <SimpleDropdown />*/}
                        {/*        </div>*/}


                        {/*        <label htmlFor="table-search" className="sr-only">Search</label>*/}
                        {/*        <div className="relative">*/}
                        {/*            <div*/}
                        {/*                className="absolute inset-y-0 left-0 rtl:inset-r-0 rtl:right-0 flex items-center ps-3 pointer-events-none">*/}
                        {/*                <svg className="w-5 h-5 text-gray-500 " aria-hidden="true"*/}
                        {/*                     fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">*/}
                        {/*                    <path fillRule="evenodd"*/}
                        {/*                          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"*/}
                        {/*                          clipRule="evenodd"></path>*/}
                        {/*                </svg>*/}
                        {/*            </div>*/}
                        {/*            <input type="text" id="table-search"*/}
                        {/*                   className="block p-2 ps-10 text-sm text-gray-400 border border-gray-500 rounded-lg w-80 "*/}
                        {/*                   placeholder="Search for items" />*/}
                        {/*        </div>*/}
                        {/*    </div>*/}

                        {/*</div>*/}
                        {/*Table part*/}
                        {chapterList && chapterList.data && (
                            <CommonTable
                                data={chapterList.data}
                                headers={[
                                    { label: "STT" },
                                    { label: "TÊN CHƯƠNG" },
                                    { label: "XUẤT BẢN LÚC" },
                                    { label: "SỐ TỪ" },
                                    { label: "LƯỢT ĐỌC" },
                                    { label: "" }
                                ]}
                                renderRow={renderRow}
                                currentPage={chapterList.currentPage}
                                pageSize={chapterList.pageSize}
                                totalPages={chapterList.totalPages}
                                totalElements={chapterList.totalElements}
                                onPageChange={handleChangePage}
                                onPageSizeChange={handlePageSizeChange}
                                toolbar={toolbar}
                                onSearch={handleSearch}
                            />
                        )}

                        {/*<div>*/}
                        {/*    <table className="w-full text-sm text-left ">*/}
                        {/*        <thead*/}
                        {/*            className="text-sm text-gray-500  border-b">*/}
                        {/*        <tr>*/}

                        {/*            <th scope="col" className="px-6 py-3">*/}
                        {/*                STT*/}
                        {/*            </th>*/}
                        {/*            <th scope="col" className="px-6 py-3">*/}
                        {/*                TÊN CHƯƠNG*/}
                        {/*            </th>*/}
                        {/*            <th scope="col" className="px-6 py-3">*/}
                        {/*                XUẤT BẢN LÚC*/}
                        {/*            </th>*/}
                        {/*            <th scope="col" className="px-6 py-3">*/}
                        {/*                SỐ TỪ*/}
                        {/*            </th>*/}
                        {/*            <th scope="col" className="px-6 py-3">*/}
                        {/*                LƯỢT ĐỌC*/}
                        {/*            </th>*/}
                        {/*            <th scope="col" className="px-6 py-3">*/}

                        {/*            </th>*/}
                        {/*        </tr>*/}
                        {/*        </thead>*/}
                        {/*        <tbody>*/}
                        {/*        {chapterList.data && chapterList.data.length > 0 ? (*/}
                        {/*            chapterList.data.map((chapter, index) => (*/}
                        {/*                <tr*/}
                        {/*                    key={chapter.id}*/}
                        {/*                    className="border-b border-gray-500 hover:bg-gray-500/10"*/}
                        {/*                >*/}
                        {/*                    <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">*/}
                        {/*                        {index + 1 + (chapterList.currentPage - 1) * chapterList.pageSize}*/}
                        {/*                    </td>*/}
                        {/*                    <td className="px-6 py-4">{chapter.name}</td>*/}
                        {/*                    <td className="px-6 py-4">*/}
                        {/*                        {chapter.publishedAt*/}
                        {/*                            ? new Date(chapter.publishedAt).toLocaleString()*/}
                        {/*                            : 'Chưa xuất bản'}*/}
                        {/*                    </td>*/}
                        {/*                    <td className="px-6 py-4">*/}
                        {/*                        {chapter.wordCount != null ? chapter.wordCount : '20'}*/}
                        {/*                    </td>*/}
                        {/*                    <td className="px-6 py-4">*/}
                        {/*                        {chapter.viewCount ?? 0}*/}
                        {/*                    </td>*/}
                        {/*                    <td className="px-6 py-4">*/}
                        {/*                        <SpecItem item={chapter}/>*/}
                        {/*                    </td>*/}
                        {/*                </tr>*/}
                        {/*            ))*/}
                        {/*        ) : (*/}
                        {/*            <tr>*/}
                        {/*                <td colSpan={6} className="px-6 py-4 text-center text-gray-500">*/}
                        {/*                    Không có chương nào*/}
                        {/*                </td>*/}
                        {/*            </tr>*/}
                        {/*        )}*/}
                        {/*        </tbody>*/}

                        {/*    </table>*/}
                        {/*    <div className={'mt-3'}>*/}
                        {/*        /!*<DefaultNavigator*!/*/}
                        {/*        /!*    data={*!/*/}

                        {/*        /!*    }*!/*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                    </div>

                </div>
            </div>
        </>
    )
}

export default ChapterList;


const SpecItem = ({item}) => {
    const setCurrentChapterList = useSetCurrentChapterList();
    function handleDelete() {
        deleteChapter(item.id).then((r) => {
            if (r.status === 200) {
                setCurrentChapterList((prevChapters) => {
                    return prevChapters.filter(chapter => chapter.id !== item.id);
                });
            }
        })
    }

    function handleEdit() {

    }

    return (
        <>
            <div>
                <div className={'flex flex-row justify-end items-center gap-x-1'}>
                    <div
                        className={'bg-gray-300 p-1 rounded-md px-2 ml-3 pl-[10px] hover:cursor-pointer hover:bg-gray-400'}>
                        <Link to={`/bookhub/chapters/${item.id}/edit`}>
                            <FontAwesomeIcon icon={faPenToSquare} />
                        </Link>
                    </div>
                    <div className={'bg-red-500 p-1 rounded-md px-2 ml-3 hover:cursor-pointer hover:bg-red-600'} onClick={handleDelete}>
                        <div >
                            <FontAwesomeIcon icon={faTrashCan} />
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}