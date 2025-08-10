
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
    tickets: [],
    status: [
        {
            name: "Đã gửi yêu cầu",
            id: "PENDING",
        },
        {
            name: "Đang xử lý",
            id: "ACCEPTED",
        },
        {
            name: "Bị từ chối",
            id: "REJECTED",
        },

        {
            name: "Đã xử lý",
            id: "RESOLVED",
        },
        {
            name: "Đã hủy",
            id: "CLOSED",
        },
    ],
    isLoading: false,
    actions: {
        setTickets: (tickets) => set((state) => ({
            tickets: tickets
        })),
        setCurrentNovelRating: (pagination) => set({ currentNovelRating: pagination }),

},
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
    setCurrentNovelComments: (pagination) => set({ currentNovelComments: pagination }),

    // Reset store
    resetFeedback: () => {
        set({
            currentNovelRating: { ...defaultPagination },
            currentNovelComments: { ...defaultPagination },
        });
    },
}));

// Export hooks and actions
export default useFeedbackStore;

// Selectors
export const useCurrentNovelComments = () => useFeedbackStore((state) => state.currentNovelComments);
export const useCurrentNovelRatings = () => useFeedbackStore((state) => state.currentNovelRating);
export const useTickets = () => useFeedbackStore((state) => state.tickets);
export const useTicketsStatus = () => useFeedbackStore((state) => state.status);
export const useIsLoading = () => useFeedbackStore((state) => state.isLoading);
export const useFetchTickets = () => useFeedbackStore((state) => state.fetchTickets);
export const useSetTickets = () => useFeedbackStore((state) => state.actions.setTickets);
export const useSetCurrentNovelComments = () => useFeedbackStore((state) => state.actions.setCurrentNovelComments);
// Actions
export const fetchCommentPage = (novelId) => useFeedbackStore.getState().fetchComments(novelId);
export const fetchRatingPage = (novelId) => useFeedbackStore.getState().fetchRatings(novelId);
export const resetFeedback = () => useFeedbackStore.getState().resetFeedback();
export const useSetCurrentNovelRating = () => useFeedbackStore((state) => state.actions.setCurrentNovelRating);