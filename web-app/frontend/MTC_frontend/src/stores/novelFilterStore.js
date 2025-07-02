// const initState = {
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'asc',
//     novelProgressStatus: [], //Dang ra, hoan thanh | [{id, name, value, isOnFilter}]
//     novelType: [], //Truyen sang tac, truyen dich
//     genres: [], //The loai
//     worldScene: [], //Boi canh the gioi
//     novelAttribute: [], //Dac diem truyen
//     totalChapterOfNovelFrom: -1, //Tong so chuong tu default = -1 => không đưa vào queries
//     totalChapterOfNovelTo: -1, //Tong so chuong den
//     ratingFrom: -1, //Tu
//     ratingTo: -1, //Den
//     mainCharacterTrait: [], //Tinh cach nhan vat chinh
//     authorName: '', //Ten tac gia
//     sects: [], //Lưu phai
//
// }

import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';

const initState = {
    searchText: '',
    sortBy: 'createdAt',
    sortOrder: 'asc',
    novelProgressStatus: [],
    novelType: [],
    novelState: [],
    novelVisibility: [],
    genres: [],
    worldScene: [],
    novelAttribute: [],
    totalChapterOfNovelFrom: -1,
    totalChapterOfNovelTo: -1,
    ratingFrom: -1,
    ratingTo: -1,
    mainCharacterTrait: [],
    authorName: '',
    sects: [],
    page: 0,
    size: 10
};

const stripIsOnFilter = (list) =>
    Array.isArray(list)
        ? list.map(item => ({ ...item, isOnFilter: false }))
        : [];

//
// const useFilterStore  = create(
//     devtools(
//         (set,get) => ({
//             ...initState,
//
//             setSearchText: (text) => set({ searchText: text }),
//             setSortBy: (value) => set({ sortBy: value }),
//             setSortOrder: (value) => set({ sortOrder: value }),
//             setNovelStatus: (list) => set({ novelProgressStatus: list }),
//             setNovelType: (list) => set({ novelType: list }),
//             setGenres: (list) => set({ genres: list }),
//             setWorldScene: (list) => set({ worldScene: list }),
//             setNovelAttribute: (list) => set({ novelAttribute: list }),
//             setTotalChapterOfNovelFrom: (value) => set({ totalChapterOfNovelFrom: value }),
//             setTotalChapterOfNovelTo: (value) => set({ totalChapterOfNovelTo: value }),
//             setRatingFrom: (value) => set({ ratingFrom: value }),
//             setRatingTo: (value) => set({ ratingTo: value }),
//             setMainCharacterTrait: (list) => set({ mainCharacterTrait: list }),
//             setAuthorName: (text) => set({ authorName: text }),
//             setSects: (list) => set({ sects: list }),
//             setPage: (page) => set({ page }),
//             setSize: (size) => set({ size }),
//
//             resetFilters: () => set({ ...initState }),
//
//             updateFilterItem: (fieldName, itemId, newData) =>
//                 set((state) => {
//                     if (!Array.isArray(state[fieldName])) return {};
//                     const updatedList = state[fieldName].map((item) =>
//                         item.id === itemId ? { ...item, ...newData } : item
//                     );
//                     return { [fieldName]: updatedList };
//                 }),
//
//             toggleFilterItem: (fieldName, itemId) =>
//                 set((state) => {
//                     if (!Array.isArray(state[fieldName])) return {};
//                     const updatedList = state[fieldName].map((item) =>
//                         item.id === itemId ? { ...item, isOnFilter: !item.isOnFilter } : item
//                     );
//                     return { [fieldName]: updatedList };
//                 }),
//         }),
//         { name: 'FilterStore' }
//     )
// );

const useFilterStore = create(
    devtools(
        persist(
            (set, get) => ({
                ...initState,

                setSearchText: (text) => set({ searchText: text }),
                setSortBy: (value) => set({ sortBy: value }),
                setSortOrder: (value) => set({ sortOrder: value }),
                setNovelStatus: (list) => set({ novelProgressStatus: list }),
                setNovelType: (list) => set({ novelType: list }),
                setGenres: (list) => set({ genres: list }),
                setWorldScene: (list) => set({ worldScene: list }),
                setNovelAttribute: (list) => set({ novelAttribute: list }),
                setTotalChapterOfNovelFrom: (value) => set({ totalChapterOfNovelFrom: value }),
                setTotalChapterOfNovelTo: (value) => set({ totalChapterOfNovelTo: value }),
                setRatingFrom: (value) => set({ ratingFrom: value }),
                setRatingTo: (value) => set({ ratingTo: value }),
                setMainCharacterTrait: (list) => set({ mainCharacterTrait: list }),
                setAuthorName: (text) => set({ authorName: text }),
                setSects: (list) => set({ sects: list }),
                setPage: (page) => set({ page }),
                setSize: (size) => set({ size }),
                setNovelState: (list) => set({ novelState: list }),
                setNovelVisibility: (list) => set({ novelVisibility: list }),

                resetFilters: () => set({ ...initState }),

                updateFilterItem: (fieldName, itemId, newData) =>
                    set((state) => {
                        if (!Array.isArray(state[fieldName])) return {};
                        const updatedList = state[fieldName].map((item) =>
                            item.id === itemId ? { ...item, ...newData } : item
                        );
                        return { [fieldName]: updatedList };
                    }),

                toggleFilterItem: (fieldName, itemId) =>
                    set((state) => {
                        if (!Array.isArray(state[fieldName])) return {};
                        const updatedList = state[fieldName].map((item) =>
                            item.id === itemId ? { ...item, isOnFilter: !item.isOnFilter } : item
                        );
                        return { [fieldName]: updatedList };
                    }),
            }),
            {
                name: 'FilterStore',
                // Chỉ persist các field không chứa `isOnFilter`
                partialize: (state) => ({
                    ...state,
                    novelProgressStatus: stripIsOnFilter(state.novelProgressStatus),
                    novelType: stripIsOnFilter(state.novelType),
                    novelAttribute: stripIsOnFilter(state.novelAttribute),
                    genres: stripIsOnFilter(state.genres),
                    worldScene: stripIsOnFilter(state.worldScene),
                    mainCharacterTrait: stripIsOnFilter(state.mainCharacterTrait),
                    sects: stripIsOnFilter(state.sects),
                }),
            }
        )
    )
);

let shouldPersist = false;

export default useFilterStore;
// const useFilterStore = shouldPersist
//     ? create(devtools(persist(createStore, { name: 'filter-storage' }), { name: 'FilterStore' }))
//     : create(devtools(createStore));
// export default useFilterStore();
