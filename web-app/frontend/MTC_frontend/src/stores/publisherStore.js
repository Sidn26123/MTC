// import { create } from 'zustand';
//
// const defaultPagination = {
//     totalPages: 0,
//     pageSize: 10,
//     currentPage: 1,
//     totalElements: 0,
//     data: []
// };
//
// const paginatedKeys = ['myPublishedNovels', 'novelStatistics', 'reports', 'drafts'];
//
// const generatePaginationSetters = (set) => {
//     const actions = {};
//     for (const key of paginatedKeys) {
//         // Setter thay đổi pageSize và reset currentPage = 1
//         actions[`set${capitalize(key)}PageSize`] = (size) => set((state) => ({
//             [key]: {
//                 ...state[key],
//                 pageSize: size,
//                 currentPage: 1
//             }
//         }));
//
//         // Hàm reset về defaultPagination
//         actions[`reset${capitalize(key)}`] = () => set(() => ({
//             [key]: { ...defaultPagination }
//         }));
//     }
//     return actions;
// };
//
// const capitalize = (str) => str.charAt(0).toUpperCase() + str.slice(1);
// const dynamicSetters = (set, keys) => {
//     const actions = {};
//     for (const key of keys) {
//         actions[`set${capitalize(key)}`] = (data) => set((state) => ({
//             [key]: {
//                 ...state[key],
//                 ...data
//             }
//         }));
//     }
//     return actions;
// };
// const usePublisherStore = create((set) => ({
//     general: {},
//     myPublishedNovels: { ...defaultPagination },
//     novelStatistics: { ...defaultPagination },
//     reports: { ...defaultPagination },
//     drafts: { ...defaultPagination },
//     currentChosenPublishedNovel: "",
//     currentNovelChapterList: {...defaultPagination},
//     ...generatePaginationSetters(set),
//     ...dynamicSetters(set, ['currentNovelChapterList']),
//
//     setCurrentNovelChapterList: (data) => set((state) => ({
//         currentNovelChapterList: {
//             ...state.currentNovelChapterList,
//             ...data,
//         }
//     })),
//
//
//
//     setCurrentChosenPublishedNovel: (novelId) => set(() => ({
//         currentChosenPublishedNovel: novelId
//     })),
//
//     resetGeneral: () => set(() => ({
//         general: {}
//     })),
//
//     resetMyPublishedNovels: () => set(() => ({
//         myPublishedNovels: { ...defaultPagination }
//     })),
//
//     resetNovelStatistics: () => set(() => ({
//         novelStatistics: { ...defaultPagination }
//     })),
//
//     resetReports: () => set(() => ({
//         reports: { ...defaultPagination }
//     })),
//
//     resetDrafts: () => set(() => ({
//         drafts: { ...defaultPagination }
//     })),
// }));
//
//
// export const usePublisherGeneral = () => usePublisherStore((state) => state.general);
// export const useMyPublishedNovels = () => usePublisherStore((state) => state.myPublishedNovels);
// export const useNovelStatistics = () => usePublisherStore((state) => state.novelStatistics);
// export const useReports = () => usePublisherStore((state) => state.reports);
// export const useDrafts = () => usePublisherStore((state) => state.drafts);
// export const useCurrentChosenPublishedNovel = () => usePublisherStore((state) => state.currentChosenPublishedNovel);
// export const usePublisherActions = () => usePublisherStore((state) => ({
//
//     setCurrentChosenPublishedNovel: state.setCurrentChosenPublishedNovel,
//     resetMyPublishedNovels: state.resetMyPublishedNovels,
//     resetNovelStatistics: state.resetNovelStatistics,
//     resetReports: state.resetReports,
//     resetDrafts: state.resetDrafts,
//     resetGeneral: state.resetGeneral
//
// }));

import { create } from 'zustand';

const defaultPagination = {
    totalPages: 0,
    pageSize: 10,
    currentPage: 1,
    totalElements: 0,
    data: []
};

// Các key có dạng phân trang
const paginatedKeys = ['myPublishedNovels', 'novelStatistics', 'reports', 'drafts'];

// Các key có nội dung dạng phân trang nhưng là động (ví dụ theo từng novel)
const dynamicPaginationKeys = ['currentNovelChapterList'];

// --- Factory: sinh action setPageSize + reset ---
const generatePaginationSetters = (set) => {
    const actions = {};
    for (const key of paginatedKeys) {
        actions[`set${capitalize(key)}PageSize`] = (size) => set((state) => ({
            [key]: {
                ...state[key],
                pageSize: size,
                currentPage: 1
            }
        }));

        actions[`reset${capitalize(key)}`] = () => set(() => ({
            [key]: { ...defaultPagination }
        }));
    }
    return actions;
};

// --- Factory: sinh action update các key động ---
const generateDynamicSetters = (set, keys) => {
    const actions = {};
    for (const key of keys) {
        actions[`set${capitalize(key)}`] = (data) => set((state) => ({
            [key]: {
                ...state[key],
                ...data
            }
        }));

        actions[`reset${capitalize(key)}`] = () => set(() => ({
            [key]: { ...defaultPagination }
        }));
    }
    return actions;
};

const capitalize = (str) => str.charAt(0).toUpperCase() + str.slice(1);

export const usePublisherStore = create((set) => ({
    // --- State ---
    general: {},
    currentChosenPublishedNovel: "",

    // --- Pagination States ---
    ...Object.fromEntries([...paginatedKeys, ...dynamicPaginationKeys].map(key => [key, { ...defaultPagination }])),

    // --- Actions ---
    resetGeneral: () => set(() => ({ general: {} })),
    setCurrentChosenPublishedNovel: (novelId) => set(() => ({ currentChosenPublishedNovel: novelId })),

    // --- Generated Actions ---
    ...generatePaginationSetters(set),
    ...generateDynamicSetters(set, dynamicPaginationKeys)
}));
