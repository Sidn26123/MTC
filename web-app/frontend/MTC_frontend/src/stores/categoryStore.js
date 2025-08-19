import { create } from 'zustand';
//
// export const useCategoryStore = create((set) => ({
//     categories: [],
//     novelProgressStatus: [],
//     novelAttributes: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     mainCharacterTraits: [],
//     sects: [],
//     worldScenes: [],
//     novelTypes: [],
//
//
//     actions: {
//         setCategories: (newCategories) => set(() => ({
//             categories: [...newCategories]
//         })),
//
//         addCategory: (category) => set((state) => ({
//             categories: [...state.categories, category]
//         })),
//
//         clearCategories: () => set({ categories: [] }),
//     }
// }));
//
// // Hooks
// export const useCategories = () => useCategoryStore((state) => state.categories);
// export const useSetCategories = () => useCategoryStore((state) => state.actions.setCategories);
// export const useAddCategory = () => useCategoryStore((state) => state.actions.addCategory);
// export const useClearCategories = () => useCategoryStore((state) => state.actions.clearCategories);

export const useCategoryStore = create((set) => ({
    categories: [],
    novelProgressStatus: [],
    novelAttributes: [],
    novelState: [],
    novelVisibility: [],
    genres: [],
    mainCharacterTraits: [],
    sects: [],
    worldScenes: [],
    novelTypes: [],

    actions: {
        setData: (key, newData) =>
            set(() => ({
                [key]: [...newData],
            })),

        addData: (key, item) =>
            set((state) => ({
                [key]: [...state[key], item],
            })),

        clearData: (key) =>
            set(() => ({
                [key]: [],
            })),
    },
}));

// Factory để tạo hooks
export const useCategoryData = (key) => () =>
    useCategoryStore((state) => state[key]);

export const useCategoryAction = (actionName) => () =>
    useCategoryStore((state) => state.actions[actionName]);