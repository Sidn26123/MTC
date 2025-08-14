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
    currentChapterList: [],
    currentChapters: {},
    currentChoseChapter: {},
    currentChoseNovelComments: {},
    currentChoseNovelRatings: {},
    currentReportToHandle: {},

    drafts: [],
    actions: {
        // setMyPublishedNovels: (paginationData) => set((state) => ({
        //     myPublishedNovels: paginationData
        // })),
        setMyPublishedNovels: (paginationData) =>
            set((prev) => ({
                myPublishedNovels: {
                    ...prev.myPublishedNovels, // giữ nguyên data cũ
                    ...Object.fromEntries(
                        Object.entries(paginationData).filter(([_, v]) => v !== undefined && v !== null)
                    )
                }
            })),
        setCurrentChapterList: (paginationData) => set((state) => ({
            currentChapterList: paginationData
        })),
        setCurrentChosenPublishedNovel: (novelId) => set(() => ({
            currentChosenPublishedNovel: novelId
        })),
        setCurrentPublishedNovel: (novelData) => set((state) => ({
            currentPublishedNovel: novelData
        })),
        setChapterPrepareForPublish: (chapters) => set(() => ({
            chapterPrepareForPublish: chapters
        })),
        setNovelStatistics: (statistics) => set((state) => ({
            novelStatistics: statistics
        })),
        setDrafts: (drafts) => set((state) => ({
            drafts: drafts
        })),
        setCurrentChosenChapter: (chapter) => set((state) => ({
            currentChoseChapter: chapter
        })),
        setCurrentChapters: (chapters) => set((state) => ({
            currentChapters: chapters
        })),
        setCurrentChosenNovelComments: (comments) => set((state) => ({
            currentChoseNovelComments: comments
        })),
        setCurrentChosenNovelRatings: (ratings) => set((state) => ({
            currentChoseNovelRatings: ratings
        })),
        setCurrentReportToHandle: (report) => set((state) => ({
            currentReportToHandle: report
        })),
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
export const useDrafts = () => usePublisherStore((state) => state.drafts);
export const useCurrentChapterList = () => usePublisherStore((state) => state.currentChapterList);
export const useCurrentChosenChapter = () => usePublisherStore((state) => state.currentChoseChapter);


export const useSetMyPublishedNovels = () => usePublisherStore((state) => state.actions.setMyPublishedNovels);
export const useSetCurrentChosenPublishedNovel = () => usePublisherStore((state) => state.actions.setCurrentChosenPublishedNovel);
export const useSetCurrentPublishedNovel = () => usePublisherStore((state) => state.actions.setCurrentPublishedNovel);
export const useSetChapterPrepareForPublish = () => usePublisherStore((state) => state.actions.setChapterPrepareForPublish);
export const useSetDrafts = () => usePublisherStore((state) => state.actions.setDrafts);
export const useSetCurrentChapterList = () => usePublisherStore((state) => state.actions.setCurrentChapterList);
export const useSetCurrentChosenChapter = () => usePublisherStore((state) => state.actions.setCurrentChosenChapter);

export const useSetCurrentChapters = () => usePublisherStore((state) => state.actions.setCurrentChapters);
export const useCurrentChapters = () => usePublisherStore((state) => state.currentChapters);
export const useSetCurrentChosenNovelComments = () => usePublisherStore((state) => state.actions.setCurrentChosenNovelComments);
export const useCurrentChosenNovelComments = () => usePublisherStore((state) => state.currentChoseNovelComments);
export const useSetCurrentChosenNovelRatings = () => usePublisherStore((state) => state.actions.setCurrentChosenNovelRatings);
export const useCurrentChosenNovelRatings = () => usePublisherStore((state) => state.currentChoseNovelRatings);
export const useSetCurrentReportToHandle = () => usePublisherStore((state) => state.actions.setCurrentReportToHandle);
export const useCurrentReportToHandle = () => usePublisherStore((state) => state.currentReportToHandle);