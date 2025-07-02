import React, { useEffect, useState } from 'react';
import LoadingSpinning from './LoadingSpinning.jsx';
import { FilterItem } from '../../common/NovelFilterComponent.jsx';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus, useNovelState, useNovelType, useNovelVisibility,
    useSearchText, useSects, useSetNovelStatus, useSetPage, useSetSearchText,
    useToggleFilterItem, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import {
    extractFiltersFromStore,
    fetchAllCategories,
    getNovelProgressStatus,
} from '../../services/novelFilterService.js';
import { getFilteredNovels, getNovels } from '../../services/novelService.js';
import { useSetListNovel } from '../../stores/novelStore.js';
import {
    useSetNovelAttribute,
    useSetNovelState,
    useSetNovelVisibility,
    useSetGenres,
    useSetMainCharacterTrait,
    useSetSects,
    useSetWorldScene,
    useSetNovelType,
} from "../../stores/selectors/novelFilterSelector.js";
const menuOptions = [
    { id: 1, name: 'Mới lên chương' },
    { id: 2, name: 'Mới đăng' },
    { id: 3, name: 'Lượt đọc' },
    { id: 4, name: 'Lượt đọc tuần' },
    { id: 5, name: 'Lượt đề cử' },
    { id: 6, name: 'Lượt đề cử tuần' },
    { id: 7, name: 'Lượt bình luận' },
    { id: 8, name: 'Lượt bình luận tuần' },
    { id: 9, name: 'Lượt đánh dấu' },
    { id: 10, name: 'Lượt đánh giá' },
    { id: 11, name: 'Điểm đánh giá' },
    { id: 12, name: 'Số chương' },
    { id: 13, name: 'Lượt mở khóa' },
    { id: 14, name: 'Tên truyện' }
];


export const useInitData = () => {
    const setNovelProgressStatus = useSetNovelStatus();
    const setNovelAttributes = useSetNovelAttribute();
    const setNovelState = useSetNovelState();
    const setNovelVisibility = useSetNovelVisibility();
    const setGenres = useSetGenres();
    const setMainCharacterTraits = useSetMainCharacterTrait();
    const setSects = useSetSects();
    const setWorldScenes = useSetWorldScene();
    const setNovelTypes = useSetNovelType();
    const setListNovel = useSetListNovel();

    useEffect(() => {
        const initData = async () => {
            try {
                const [novel, allCategories] = await Promise.all([
                    getNovels(),
                    fetchAllCategories()
                ]);

                setListNovel(novel.data.result);
                setNovelProgressStatus(allCategories.novelProgressStatus);
                setNovelAttributes(allCategories.novelAttributes);
                setNovelState(allCategories.novelState);
                setNovelVisibility(allCategories.novelVisibility);
                setGenres(allCategories.genres);
                setMainCharacterTraits(allCategories.mainCharacterTraits);
                setSects(allCategories.sects);
                setWorldScenes(allCategories.worldScenes);
                setNovelTypes(allCategories.novelTypes);
            } catch (error) {
                console.error("Error initializing data:", error);
            }
        };

        initData().then(r => {});
    }, []);
};

const NovelFilter = ({onClose}) => {
    const [isLoading, setIsLoading] = React.useState(false);
    const [isOpen, setIsOpen] = useState(true);
    // const [searchKeyword, setSearchKeyword] = useState('');
    const [sortBy, setSortBy] = useState('Lượt đọc tuần');
    const searchText = useSearchText();
    const setSearchText = useSetSearchText();
    const setListNovel = useSetListNovel();
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
    useInitData();
    // useEffect(() => {
    //     const fetchData = async () => {
    //         // const result = await getNovelProgressStatus(); // Giả sử gọi API
    //         // setNovelProgressStatus(result.data.result); // Gọi action của Zustand
    //         const novel = await getNovels();
    //         const allCategories = await fetchAllCategories();
    //         console.log("NovelFilter: a ", allCategories);
    //         setNovelProgressStatus(allCategories.novelProgressStatus);
    //
    //         setListNovel(novel.data.result); // Gọi action của Zustand
    //     };
    //
    //     fetchData().then(r => {});
    // }, []);

    const toggleFilterItem = useToggleFilterItem();
    // const toggleFilterItem = (itemId) =>  useToggleFilterItem("novelProgressStatus", itemId);

    const resetPage = () => {
        changePage(1);
    }

    const handleFilter = () => {
        resetPage();
        setIsLoading(true);

        getFilteredNovels(extractFiltersFromStore()).then(r => {
            setListNovel(r.data.result);
            setIsLoading(false);
        });

    }

    return (
        <>
            <div>
                <div>
                    <div className="relative z-10" role="dialog" aria-modal="true">
                        <div className="fixed inset-0 bg-gray-50/0 transition-opacity"></div>
                        <div className="fixed inset-0 z-10 overflow-y-auto">
                            <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                                <div className="bg-panel p-6 max-w-5/7 w-full rounded-xl background-color-lighter">
                                    {isLoading && (
                                        <LoadingSpinning />
                                    )}
                                    <div className="flex justify-between items-center">
                                        <h2 className="font-bold text-xl hover:cursor-pointer hover:text-yellow-primary"
                                            onClick={handleFilter}
                                        >Tìm kiếm <span><FontAwesomeIcon className={"text-base"} icon={faMagnifyingGlass} /></span></h2>

                                        <button type="button"
                                                className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
                                                onClick={onClose}
                                        >
                                            <span className="sr-only">Close</span>
                                            <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                                                 viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
                                                 aria-hidden="true">
                                                <path strokeLinecap="round" strokeLinejoin="round"
                                                      d="M6 18 18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>

                                    </div>
                                    {/* Filter section shown in second image */}
                                    <div className="mt-6 p-4 rounded-md">

                                        <div className="flex justify-between space-x-4 mb-4">
                                            <div className="w-1/2">
                                                <h2 className="text-base font-semibold text-title text-left">
                                                    Tình trạng
                                                </h2>                                                <input
                                                    type="text"
                                                    className="w-full  border border-gray-600 rounded-md px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                    placeholder="Từ khóa muốn lọc"
                                                    value={searchText}
                                                    onChange={(e) => setSearchText(e.target.value)}
                                                />
                                            </div>
                                            <div className="w-1/2">
                                                <h2 className="text-base font-semibold text-title text-left">
                                                    Tình trạng
                                                </h2>
                                                <div className="relative">
                                                    <select
                                                        className="w-full background-color-lighter border border-gray-600 rounded-md px-3 py-2  appearance-none focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                        value={sortBy}
                                                        onChange={(e) => setSortBy(e.target.value)}
                                                    >
                                                        {menuOptions.map((option) => (
                                                            <option key={option.id} value={option.id}>
                                                                {option.name}
                                                            </option>
                                                        ))}
                                                    </select>
                                                    <div className="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                                                        <svg className="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                                                        </svg>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="highlight rounded-lg grid grid-cols-1 md:grid-cols-2 gap-6">
                                         {/*List part */}
                                        {/*<div>*/}
                                        {/*    <h2 className="text-base font-semibold text-title text-left">*/}
                                        {/*        Tình trạng*/}
                                        {/*    </h2>*/}
                                        {/*    <div className="leading-10 space-x-2 mt-2 flex flex-wrap justify-start">*/}
                                        {/*        {novelProgressStatus?.map?.((item) => (*/}
                                        {/*            <div key={item.id} className="inline-flex">*/}
                                        {/*                <FilterItem*/}
                                        {/*                    filter={item}*/}
                                        {/*                    onFilterChange={() =>*/}
                                        {/*                        toggleFilterItem("novelProgressStatus", item.id)*/}
                                        {/*                    }*/}
                                        {/*                />*/}
                                        {/*            </div>*/}
                                        {/*        ))}*/}

                                        {/*    </div>*/}
                                        {/*</div>*/}

                                        <NovelCategorySection
                                            title="Tình trạng"
                                            items={novelProgressStatus}
                                            toggleCategoryKey= "novelProgressStatus"
                                        />
                                        <NovelCategorySection
                                            title="Thuộc tính truyện"
                                            items={novelAttributes}
                                            toggleCategoryKey= "novelAttribute"
                                        />

                                        <NovelCategorySection
                                            title="Thể loại"
                                            items={genres}
                                            toggleCategoryKey= "genres"
                                        />
                                        <NovelCategorySection
                                            title="Tính cách nhân vật chính"
                                            items={mainCharacterTraits}
                                            toggleCategoryKey= "mainCharacterTrait"
                                        />
                                        <NovelCategorySection
                                            title="Bối cảnh thế giới"
                                            items={worldScenes}
                                            toggleCategoryKey= "worldScene"
                                        />
                                        <NovelCategorySection
                                            title="Lưu phái"
                                            items={sects}
                                            toggleCategoryKey= "sects"
                                        />

                                        <NovelCategorySection
                                            title="Loại"
                                            items={novelTypes}
                                            toggleCategoryKey= "novelType"
                                        />
                                    </div>

                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

const NovelCategorySection = ({ title, items, toggleCategoryKey }) => {
    if (!items?.length) return null;
    const toggleFilterItem = useToggleFilterItem();

    return (
        <div>
            <h2 className="text-base font-semibold text-title text-left mb-1">{title}</h2>
            <div className="leading-10 mt-2 flex flex-wrap justify-start">
                {items.map((item) => (
                    <div key={item.id} className="inline-flex">
                        <FilterItem
                            filter={item}
                            // onFilterChange={() => toggleCategoryKey && toggleCategoryKey(toggleCategoryKey, item.id)}
                            onFilterChange={() => toggleFilterItem(toggleCategoryKey, item.id)}
                        />
                    </div>
                ))}
            </div>
        </div>
    );
};

export { NovelFilter };