import { create } from 'zustand';
const defaultPagination = {
    totalPages: 0,
    pageSize: 10,
    currentPage: 1,
    totalElements: 0,
    data: []
};

const adminStore = create((set) => ({
    general: {},
    current: {},
    chapter: {
        content: "",
        authorNote: ""
    },
    // Các list có phân trang
    listNovel: { ...defaultPagination },
    filteredNovelList: { ...defaultPagination },
    actions: {
        setCurrentNovel: (novel) => set(() => ({
            current: { ...novel }
        })),

        setChapter: (chapterData) => set((state) => ({
            chapter: { ...state.chapter, ...chapterData }
        })),

        setListNovel: (paginationData) => set(() => ({
            listNovel: paginationData
        })),

        setFilteredNovelList: (paginationData) => set(() => ({
            filteredNovelList: paginationData
        })),
    }
}));

export const useAdminStore = () => adminStore((state) => state);
export const useAdminActions = () => adminStore((state) => state.actions);  
export const useAdminGeneral = () => adminStore((state) => state.general);
export const useAdminCurrentNovel = () => adminStore((state) => state.current);
export const useAdminChapter = () => adminStore((state) => state.chapter);
export const useAdminListNovel = () => adminStore((state) => state.listNovel);
export const useAdminFilteredNovelList = () => adminStore((state) => state.filteredNovelList);
