import { create } from 'zustand';

const useUserStore = create((set) => ({
    user: {
    },
    currentNovelPublisher: {

    },
    setUser: (userData) => set({
        user: {...userData}
    }),
    updateUser: (key, value) => set((state) => ({
        user: {
            ...state.user,
            [key]: value
        }
    })),
    logout: () => set({
        user: {}
    }),
    setCurrentNovelPublisher: (novelPublisher) => set({
        currentNovelPublisher: {...novelPublisher}
    }),
}));

export default useUserStore;

export const useUser = () => useUserStore((state) => state.user);
export const useSetUser = () => useUserStore((state) => state.setUser);

export const useCurrentNovelPublisher = () => useUserStore((state) => state.user);
export const useSetCurrentNovelPublisher = () => useUserStore((state) => state.setUser);