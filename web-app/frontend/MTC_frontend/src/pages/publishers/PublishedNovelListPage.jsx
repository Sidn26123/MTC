import React, { useEffect } from 'react';
import { faChartLine, faFeather, faListUl, faLocationArrow, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { CategoryDropdown, SimpleDropdown } from '../../common/CommonComponents.jsx';
import { Link } from 'react-router';
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

function PublishedNovelPage() {
    const user = useUser();
    const currentPublishedNovel = usePublishedByPublisher();
    const setCurrentPublishedNovel = useSetPublishedByPublisher();
    // var filter = {
    //     "currentPublisher": user.id
    // }
    useEffect(() => {
        if (!user?.id) return;

        const fetchNovels = async () => {
            try {
                const filter = { currentPublisher: "a3651d8d-f9e0-4a57-930f-5bbf9ff21a6d" };
                const response = await getFilteredNovels(filter);
                setCurrentPublishedNovel(response.data.result);
            } catch (err) {
            }
        };

        fetchNovels().then(r => {console.log(r)});
    }, [user?.id, setCurrentPublishedNovel]);


        return (
        <>
            <div>

                <PublishedNovelTable novels={currentPublishedNovel} />
            </div>
        </>
    );
}

export default PublishedNovelPage;


const PublishedNovelTable = ({novels}) => {
    console.log("novels", novels);
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

    // return (
    //     <>
    //         <div>
    //
    //
    //             <div className="relative overflow-x-auto shadow-md sm:rounded-lg background-color-lighter rounded-md p-3 min-h-[500px]">
    //                 <div className="flex flex-column sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4 ">
    //                     <div className={"flex flex-row gap-x-2"}>
    //                         <SimpleDropdown />
    //                         {/*<SimpleDropdown />*/}
    //                         <CategoryDropdown dropdown ={novelProgressStatus}/>
    //                     </div>
    //
    //
    //                     <label htmlFor="table-search" className="sr-only">Search</label>
    //
    //                     <div className="relative">
    //
    //                         <div
    //                             className="absolute inset-y-0 left-0 rtl:inset-r-0 rtl:right-0 flex items-center ps-3 pointer-events-none">
    //                             <svg className="w-5 h-5 text-gray-500 " aria-hidden="true"
    //                                  fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
    //                                 <path fillRule="evenodd"
    //                                       d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
    //                                       clipRule="evenodd"></path>
    //                             </svg>
    //
    //                         </div>
    //
    //                         <input type="text" id="table-search"
    //                                className="block p-2 ps-10 text-sm text-gray-400 border border-gray-500 rounded-lg w-80 "
    //                                placeholder="Search for items" />
    //
    //                     </div>
    //                     <Link to={"/bookhub/new"}>
    //                         <FontAwesomeIcon icon={faPlus} />
    //                     </Link>
    //
    //                 </div>
    //                 <table className="w-full text-sm text-left">
    //                     <thead
    //                         className="text-sm text-gray-500  border-b">
    //                     <tr>
    //
    //                         <th scope="col" className="px-6 py-3">
    //                             Product name
    //                         </th>
    //                         <th scope="col" className="px-6 py-3">
    //                             Color
    //                         </th>
    //                         <th scope="col" className="px-6 py-3">
    //                             Category
    //                         </th>
    //                         <th scope="col" className="px-6 py-3">
    //
    //                         </th>
    //                     </tr>
    //                     </thead>
    //                     <tbody>
    //                     <tr className=" border-b border-gray-500 hover:bg-gray-500/10 ">
    //
    //                         <th scope="row"
    //                             className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
    //                             Apple MacBook Pro 17"
    //                         </th>
    //                         <td className="px-6 py-4">
    //                             Silver
    //                         </td>
    //                         <td className="px-6 py-4">
    //                             Laptop
    //                         </td>
    //                         <td className="px-6 py-4">
    //                             <SpecItem />
    //                         </td>
    //                     </tr>
    //
    //                     </tbody>
    //                 </table>
    //             </div>
    //
    //         </div>
    //     </>
    // )
    return (
        <div className="relative overflow-x-auto shadow-md sm:rounded-lg bg-white dark:bg-gray-800 rounded-md p-3 min-h-[500px]">
            {/* Header với các dropdown và search */}
            <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
                <div className="flex flex-row gap-x-2">
                    <SimpleDropdown />
                    <CategoryDropdown dropdown ={novelProgressStatus}/>
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
                    <th scope="col" className="px-6 py-3">
                        Tên truyện
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Mô tả
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Slug
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Hành động
                    </th>
                </tr>
                </thead>
                <tbody>
                {novels.data.map((novel) => (
                    <tr key={novel.id} className="border-b hover:bg-gray-50">
                        <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                            {novel.displayName}
                        </th>
                        <td className="px-6 py-4">{novel.description}</td>
                        <td className="px-6 py-4">{novel.slug}</td>
                        <td className="px-6 py-4">
                            <SpecItem novelId={novel.id} />
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {/* Phân trang */}
            <div className="flex justify-end mt-4">
                <div className="flex gap-x-2">
                    {Array.from({ length: novels.totalPages }, (_, i) => i + 1).map((page) => (
                        <button
                            className={`px-3 py-1 rounded-md ${
                                page === novels.currentPage ? 'bg-blue-500 text-white' : 'bg-gray-200'
                            }`}
                        >
                            {page}
                        </button>
                    ))}
                </div>
            </div>
        </div>
    );
}


const SpecItem = () => {
    return (
        <>
            <div>
                <div className={"flex flex-row justify-end items-center gap-x-1"}>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 ml-3 hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={"/bookhub/books/1/upload-chapters"}>
                            <FontAwesomeIcon icon={faPlus} />
                        </Link>
                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={"/bookhub/books/1/chapters"}>
                            <FontAwesomeIcon icon={faListUl} />
                        </Link>

                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={"/bookhub/books/1/update"}>
                            <FontAwesomeIcon icon={faFeather} />
                        </Link>
                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <FontAwesomeIcon icon={faChartLine} />

                    </div>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 hover:cursor-pointer hover:bg-gray-400"}>
                        <FontAwesomeIcon icon={faLocationArrow} />

                    </div>
                </div>
            </div>
        </>
    )
}