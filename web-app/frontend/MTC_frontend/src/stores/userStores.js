import { create } from 'zustand';

const useUserStore = create((set) => ({
    user: {
    },
    profile: {},
    currentNovelPublisher: {

    },
    setUser: (userData) => set({
        user: {...userData}
    }),
    setProfile: (profileData) => set({
        profile: {...profileData}
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
export const useProfile = () => useUserStore((state) => state.profile);
export const useCurrentNovelPublisher = () => useUserStore((state) => state.currentNovelPublisher);
export const useSetCurrentNovelPublisher = () => useUserStore((state) => state.setCurrentNovelPublisher);
export const useUpdateUser = () => useUserStore((state) => state.updateUser);
export const useSetProfile = () => useUserStore((state) => state.setProfile);