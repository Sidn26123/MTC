// import { create } from 'zustand';
// import api from '../middlewares/axios.js';
// import { API } from '../configurations/configuration.js';
// const defaultPagination = {
//     totalPages: 0,
//     pageSize: 10,
//     currentPage: 1,
//     totalElements: 0,
//     data: []
// };
//
// const useChapterStore = create((set,get) => ({
//     currentNovelRating: {...defaultPagination},
//     currentNovelComments: {...defaultPagination},
//
//     fetchComments: async (novelId) => {
//         const state = get().currentNovelComments;
//         const nextPage = state.currentPage;
//
//
//
//         try {
//             const response = await api.get(API.FEEDBACK_COMMENT + "/novel/" + novelId, {
//                 params: {
//                     page: nextPage,
//                     size: state.pageSize,
//                     parentId: "null"
//                 }
//             });
//
//             const pageData = response.data.result;
//             console.log('Fetched next comment page:', pageData);
//             set((prev) => {
//                 // Gộp data cũ và data mới
//                 const merged = [
//                     ...prev.currentNovelComments.data,
//                     ...pageData.data
//                 ];
//
//                 // Lọc trùng theo id
//                 const unique = Array.from(
//                     new Map(merged.map(comment => [comment.id, comment])).values()
//                 );
//
//                 return {
//                     currentNovelComments: {
//                         ...prev.currentNovelComments,
//                         data: unique,
//                         currentPage: pageData.currentPage,
//                         totalPages: pageData.totalPages,
//                         totalElements: pageData.totalElements
//                     }
//                 };
//             });
//         } catch (err) {
//             console.error('Failed to fetch next comment page:', err);
//         }
//     }
//
// }));
//
// export const useUserChapterStore = useChapterStore;
//
// export const fetchNextCommentPage = (chapterId) => {
//     return useChapterStore.getState().fetchComments(chapterId);
// };
//
// export const useCurrentNovelComments = () => {
//     const { currentNovelComments } = useChapterStore.getState();
//     return currentNovelComments;
// }

import { create } from 'zustand';
import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

const defaultPagination = {
    totalPages: 0,
    pageSize: 10,
    currentPage: 1,
    totalElements: 0,
    data: [],
};

const useFeedbackStore = create((set, get) => ({
    currentNovelRating: { ...defaultPagination },
    currentNovelComments: { ...defaultPagination },

    // Fetch comments
    fetchComments: async (novelId) => {
        const state = get().currentNovelComments;
        const nextPage = state.currentPage;

        try {
            const response = await api.get(`${API.FEEDBACK_COMMENT}/novel/${novelId}`, {
                params: {
                    page: nextPage,
                    size: state.pageSize,
                    parentId: null,
                    feedbackType: "COMMENT"
                },
            });

            const pageData = response.data.result;
            set((prev) => {
                // Gộp và lọc dữ liệu trùng lặp
                const merged = [...prev.currentNovelComments.data, ...pageData.data];
                const unique = Array.from(
                    new Map(merged.map((comment) => [comment.id, comment])).values()
                );

                return {
                    currentNovelComments: {
                        ...prev.currentNovelComments,
                        data: unique,
                        currentPage: pageData.currentPage,
                        totalPages: pageData.totalPages,
                        totalElements: pageData.totalElements,
                    },
                };
            });
        } catch (err) {
            console.error('Failed to fetch comments:', err);
        }
    },

    // Fetch ratings
    fetchRatings: async (novelId) => {
        const state = get().currentNovelRating;
        const nextPage = state.currentPage;

        try {
            const response = await api.get(`${API.FEEDBACK_RATING}/novel/${novelId}`, {
                params: {
                    page: nextPage,
                    size: state.pageSize,
                },
            });

            const pageData = response.data.result;
            set((prev) => {
                // Gộp và lọc dữ liệu trùng lặp
                const merged = [...prev.currentNovelRating.data, ...pageData.data];
                const unique = Array.from(
                    new Map(merged.map((rating) => [rating.id, rating])).values()
                );

                return {
                    currentNovelRating: {
                        ...prev.currentNovelRating,
                        data: unique,
                        currentPage: pageData.currentPage,
                        totalPages: pageData.totalPages,
                        totalElements: pageData.totalElements,
                    },
                };
            });
        } catch (err) {
            console.error('Failed to fetch ratings:', err);
        }
    },

    // Reset store
    resetFeedback: () => {
        set({
            currentNovelRating: { ...defaultPagination },
            currentNovelComments: { ...defaultPagination },
        });
    },
}));

// Export hooks and actions
export const useNovelFeedbackStore = useFeedbackStore;

// Selectors
export const useCurrentNovelComments = () => useFeedbackStore((state) => state.currentNovelComments);
export const useCurrentNovelRatings = () => useFeedbackStore((state) => state.currentNovelRating);

// Actions
export const fetchCommentPage = (novelId) => useFeedbackStore.getState().fetchComments(novelId);
export const fetchNextRatingPage = (novelId) => useFeedbackStore.getState().fetchRatings(novelId);
export const resetFeedback = () => useFeedbackStore.getState().resetFeedback();