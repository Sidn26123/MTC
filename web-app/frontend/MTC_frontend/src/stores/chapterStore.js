import { create } from 'zustand';
import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

const userChapterStore = create((set) => ({
    currentChapter: {
        chapterIdx: 6,
        content: 'asd',
    },
    listChapter: [],
    filteredChapterList: [],
    listChapterRecentPublishedOfNovel: [],
    actions: {
        setCurrentChapter: (chapter) =>
            set({
                currentChapter: { ...chapter },
            }),
        setListChapterRecentPublishedOfNovel: (chapters) =>
            set({ listChapterRecentPublishedOfNovel: chapters }),

        updateCurrentChapterField: (key, value) =>
            set((state) => ({
                currentChapter: {
                    ...state.currentChapter,
                    [key]: value,
                },
            })),
        setListChapter: (chapters) => set({ listChapter: chapters }),
        updateListChapter: (index, value) =>
            set((state) => {
                const newList = [...state.listChapter];
                newList[index] = value;
                return { listChapter: newList };
            }),
        addChapterContent: (content) =>
            set((state) => ({
                currentChapter: {
                    ...state.currentChapter,
                    content: state.currentChapter.content + content.content,
                    chapterIdx: content.chapterIdx,
                },
            })),
        fetchListChapter : async (novelSlug, page, size) => {
            const response = await api.get(API.CHAPTER + "/truyen/" + novelSlug + "/chapterList",
                {
                    params: {
                        page: page,
                        size: size,
                        sortBy: "chapterIdx"
                    }
                }
                );

            const pageData = response.data.result;

            set((prev) => {
                const merged = [
                    ...prev.listChapter,
                    ...pageData.data
                ]

                const unique = Array.from(
                    new Map(merged.map(chapter => [chapter.id, chapter])).values()
                );

                return {
                    listChapter: unique,
                }
            })


        }
    },
}));

export const useChapterStore = () =>
    userChapterStore((state) => state.currentChapter);
export const useListChapterCurrentPublished = () =>
    userChapterStore((state) => state.listChapterRecentPublishedOfNovel);

export const useCurrentChapterContent = () =>
    userChapterStore((state) => state.currentChapter.content);
export const useCurrentChapter = () =>
    userChapterStore((state) => state.currentChapter);
export const useChapterActions = () =>
    userChapterStore((state) => state.actions);

export const useCurrentChapterIdx = () =>
    userChapterStore((state) => state.currentChapter.chapterIdx);

export const useIncreaseChapterIdx = () => {
    const { currentChapter, setCurrentChapter } = userChapterStore((state) => ({
        currentChapter: state.currentChapter,
        setCurrentChapter: state.actions.setCurrentChapter,
    }));

    return () =>
        setCurrentChapter({
            ...currentChapter,
            chapterIdx: currentChapter.chapterIdx + 1,
        });
};

export const useDecreaseChapterIdx = () => {
    const { currentChapter, setCurrentChapter } = userChapterStore((state) => ({
        currentChapter: state.currentChapter,
        setCurrentChapter: state.actions.setCurrentChapter,
    }));

    return () =>
        setCurrentChapter({
            ...currentChapter,
            chapterIdx: currentChapter.chapterIdx - 1,
        });
};

// export const useChapterActions = () =>
//     userChapterStore((state) => state.actions);

export const useListChapter = () =>
    userChapterStore((state) => state.listChapter);