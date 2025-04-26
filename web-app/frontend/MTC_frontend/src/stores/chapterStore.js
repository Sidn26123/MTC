import { create } from 'zustand';

const userChapterStore = create((set) => ({
    currentChapter: {
        chapterIdx: 6,
        content: 'asd',
    },
    listChapter: [],
    actions: {
        setCurrentChapter: (chapter) =>
            set({
                currentChapter: { ...chapter },
            }),
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
    },
}));

export const useChapterStore = () =>
    userChapterStore((state) => state.currentChapter);

export const useCurrentChapterContent = () =>
    userChapterStore((state) => state.currentChapter.content);

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
