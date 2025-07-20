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
    currentPublishedNovel: {},
    myPublishedNovels: [],
    chapterPrepareForPublish: [],
    actions: {
        setMyPublishedNovels: (paginationData) => set((state) => ({
            myPublishedNovels: paginationData
        })),
        setCurrentChosenPublishedNovel: (novelId) => set(() => ({
            currentChosenPublishedNovel: novelId
        })),
        setCurrentPublishedNovel: (novelData) => set((state) => ({
            currentPublishedNovel: novelData
        })),
        setChapterPrepareForPublish: (chapters) => set(() => ({
            chapterPrepareForPublish: chapters
        }))
    },
    // --- Pagination States ---
    ...Object.fromEntries([...paginatedKeys, ...dynamicPaginationKeys].map(key => [key, { ...defaultPagination }])),

    // --- Actions ---
    resetGeneral: () => set(() => ({ general: {} })),
    setCurrentChosenPublishedNovel: (novelId) => set(() => ({ currentChosenPublishedNovel: novelId })),

    // --- Generated Actions ---
    ...generatePaginationSetters(set),
    ...generateDynamicSetters(set, dynamicPaginationKeys)
}));

export const usePublisherGeneral = () => usePublisherStore((state) => state.general);
export const useMyPublishedNovels = () => usePublisherStore((state) => state.myPublishedNovels);
export const useNovelStatistics = () => usePublisherStore((state) => state.novelStatistics);
export const useCurrentChosenPublishedNovel = () => usePublisherStore((state) => state.currentChosenPublishedNovel);
export const useCurrentPublishedNovel = () => usePublisherStore((state) => state.currentPublishedNovel);
export const useChapterPrepareForPublish = () => usePublisherStore((state) => state.chapterPrepareForPublish);

export const useSetMyPublishedNovels = () => usePublisherStore((state) => state.actions.setMyPublishedNovels);
export const useSetCurrentChosenPublishedNovel = () => usePublisherStore((state) => state.actions.setCurrentChosenPublishedNovel);
export const useSetCurrentPublishedNovel = () => usePublisherStore((state) => state.actions.setCurrentPublishedNovel);
export const useSetChapterPrepareForPublish = () => usePublisherStore((state) => state.actions.setChapterPrepareForPublish);