import { create } from 'zustand';

const useUserStore = create((set) => ({
    user: {
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
    })
}));

export default useUserStore;