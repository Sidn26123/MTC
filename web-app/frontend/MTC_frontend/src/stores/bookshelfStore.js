import { create } from 'zustand';

import {defaultPagination} from '../constants/const.js';

const userChapterStore = create((set) => ({
    currentBookshelf: {},
    currentNovelReadingChapter: {},
    bookshelfList: [],
    bookshelfItems: { ...defaultPagination },
    bookmarkedNovels: { ...defaultPagination },

    actions: {
        setCurrentBookshelf: (bookshelf) => set({ currentBookshelf: bookshelf }),
        setBookshelfList: (list) => set({ bookshelfList: list }),
        setBookshelfItems: (pagination) => set({ bookshelfItems: pagination }),
        setBookmarkedNovels: (pagination) => set({ bookmarkedNovels: pagination }),
        setCurrentNovelReadingChapter: (data) =>
            set({ currentNovelReadingChapter: data }),

        resetStore: () =>
            set({
                currentBookshelf: {},
                bookshelfList: [],
                bookshelfItems: { ...defaultPagination },
                bookmarkedNovels: { ...defaultPagination },
            }),
    },
}));

// Export hooks
export const useCurrentBookshelf = () =>
    userChapterStore((state) => state.currentBookshelf);

export const useBookshelfList = () =>
    userChapterStore((state) => state.bookshelfList);

export const useBookshelfItems = () =>
    userChapterStore((state) => state.bookshelfItems);

export const useBookmarkedNovels = () =>
    userChapterStore((state) => state.bookmarkedNovels);

export const useUserChapterActions = () =>
    userChapterStore((state) => state.actions);

// ----- Action selectors (riêng lẻ) -----
export const useSetCurrentBookshelf = () =>
    userChapterStore((state) => state.actions.setCurrentBookshelf);

export const useSetBookshelfList = () =>
    userChapterStore((state) => state.actions.setBookshelfList);

export const useSetBookshelfItems = () =>
    userChapterStore((state) => state.actions.setBookshelfItems);

export const useSetBookmarkedNovels = () =>
    userChapterStore((state) => state.actions.setBookmarkedNovels);

export const useResetUserChapterStore = () =>
    userChapterStore((state) => state.actions.resetStore);

export const useSetCurrentNovelReadingChapter = () =>
    userChapterStore((state) => state.actions.setCurrentNovelReadingChapter);

export const useCurrentNovelReadingChapter = () =>
    userChapterStore((state) => state.currentNovelReadingChapter);