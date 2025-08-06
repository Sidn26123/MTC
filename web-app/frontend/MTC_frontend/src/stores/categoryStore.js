import { create } from 'zustand';

export const useCategoryStore = create((set) => ({
    categories: [],

    actions: {
        setCategories: (newCategories) => set(() => ({
            categories: [...newCategories]
        })),

        addCategory: (category) => set((state) => ({
            categories: [...state.categories, category]
        })),

        clearCategories: () => set({ categories: [] }),
    }
}));

// Hooks
export const useCategories = () => useCategoryStore((state) => state.categories);
export const useSetCategories = () => useCategoryStore((state) => state.actions.setCategories);
export const useAddCategory = () => useCategoryStore((state) => state.actions.addCategory);
export const useClearCategories = () => useCategoryStore((state) => state.actions.clearCategories);
