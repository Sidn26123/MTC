import { create } from 'zustand';
const defaultPagination = {
    totalPages: 0,
    pageSize: 10,
    currentPage: 1,
    totalElements: 0,
    data: []
};

const userNovelStore = create((set) => ({
    general: {},

    current: {

    },

    chapter: {
        content: "",
        authorNote: ""
    },

    // Các list có phân trang
    listNovel: { ...defaultPagination },
    justReadNovelList: { ...defaultPagination },
    promotedNovelList: { ...defaultPagination },
    filteredNovelList: { ...defaultPagination },
    bookShelf: { ...defaultPagination },
    bookMark: { ...defaultPagination },
    publishedByPublisher: { ...defaultPagination },
    actions: {
        setCurrentNovel: (novel) => set(() => ({
            current: { ...novel }
        })),

        setPublishedByPublisher: (publishedByPublisher) => set(() => ({
            publishedByPublisher: { ...publishedByPublisher }
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

        setJustReadNovelList: (paginationData) => set(() => ({
            justReadNovelList: paginationData
        })),

        setPromotedNovelList: (paginationData) => set(() => ({
            promotedNovelList: paginationData
        })),

        setBookShelf: (paginationData) => set(() => ({
            bookShelf: paginationData
        })),

        setBookMark: (paginationData) => set(() => ({
            bookMark: paginationData
        })),

        // Các hàm cập nhật currentPage cho từng list
        setListNovelPage: (page) => set((state) => ({
            listNovel: { ...state.listNovel, currentPage: page }
        })),

        setFilteredNovelListPage: (page) => set((state) => ({
            filteredNovelList: { ...state.filteredNovelList, currentPage: page }
        })),

        setJustReadNovelListPage: (page) => set((state) => ({
            justReadNovelList: { ...state.justReadNovelList, currentPage: page }
        })),

        setPromotedNovelListPage: (page) => set((state) => ({
            promotedNovelList: { ...state.promotedNovelList, currentPage: page }
        })),

        setBookShelfPage: (page) => set((state) => ({
            bookShelf: { ...state.bookShelf, currentPage: page }
        })),

        setBookMarkPage: (page) => set((state) => ({
            bookMark: { ...state.bookMark, currentPage: page }
        })),

        // Các hàm cập nhật pageSize cho từng list
        setListNovelPageSize: (size) => set((state) => ({
            listNovel: { ...state.listNovel, pageSize: size, currentPage: 1 } // Reset về page 1 khi thay đổi pageSize
        })),

        setFilteredNovelListPageSize: (size) => set((state) => ({
            filteredNovelList: { ...state.filteredNovelList, pageSize: size, currentPage: 1 }
        })),

        setJustReadNovelListPageSize: (size) => set((state) => ({
            justReadNovelList: { ...state.justReadNovelList, pageSize: size, currentPage: 1 }
        })),

        setPromotedNovelListPageSize: (size) => set((state) => ({
            promotedNovelList: { ...state.promotedNovelList, pageSize: size, currentPage: 1 }
        })),

        setBookShelfPageSize: (size) => set((state) => ({
            bookShelf: { ...state.bookShelf, pageSize: size, currentPage: 1 }
        })),

        setBookMarkPageSize: (size) => set((state) => ({
            bookMark: { ...state.bookMark, pageSize: size, currentPage: 1 }
        })),


        resetCurrentNovel: () => set(() => ({
            current: {
                novelId: "",
                novelSlug: "",
                novelName: "",
                authorName: "",
            }
        })),

        resetChapter: () => set(() => ({
            chapter: {
                content: "",
                authorNote: ""
            }
        }))
    }
}));
// export default userNovelStore;

export const useCurrentNovel = () => userNovelStore((state) => state.current);
export const useNovelGeneral = () => userNovelStore((state) => state.general);
export const useNovelChapter = () => userNovelStore((state) => state.chapter);
export const useNovelActions = () => userNovelStore((state) => state.actions);

// Lấy full object phân trang
export const useNovelList = () => userNovelStore((state) => state.listNovel);
export const useFilteredNovelList = () => userNovelStore((state) => state.filteredNovelList);
export const useBookShelf = () => userNovelStore((state) => state.bookShelf);
export const useBookMark = () => userNovelStore((state) => state.bookMark);
export const useJustReadNovelList = () => userNovelStore((state) => state.justReadNovelList);
export const usePromotedNovelList = () => userNovelStore((state) => state.promotedNovelList);
export const usePublishedByPublisher = () => userNovelStore((state) => state.publishedByPublisher);
// Chỉ lấy phần `data` của từng list
export const useNovelListData = () => userNovelStore((state) => state.listNovel.data || []);
export const useBookShelfData = () => userNovelStore((state) => state.bookShelf.data || []);
export const useBookMarkData = () => userNovelStore((state) => state.bookMark.data || []);
export const useFilteredNovelListData = () => userNovelStore((state) => state.filteredNovelList.data || []);
export const useJustReadNovelListData = () => userNovelStore((state) => state.justReadNovelList.data || []);
export const usePromotedNovelListData = () => userNovelStore((state) => state.promotedNovelList.data || []);

// Hooks cho việc cập nhật currentPage
export const useSetListNovelPage = () => userNovelStore((state) => state.actions.setListNovelPage);
export const useSetFilteredNovelListPage = () => userNovelStore((state) => state.actions.setFilteredNovelListPage);
export const useSetJustReadNovelListPage = () => userNovelStore((state) => state.actions.setJustReadNovelListPage);
export const useSetPromotedNovelListPage = () => userNovelStore((state) => state.actions.setPromotedNovelListPage);
export const useSetBookShelfPage = () => userNovelStore((state) => state.actions.setBookShelfPage);
export const useSetBookMarkPage = () => userNovelStore((state) => state.actions.setBookMarkPage);
export const useSetCurrentNovel = () => userNovelStore((state) => state.actions.setCurrentNovel);
export const useSetPublishedByPublisher = () => userNovelStore((state) => state.actions.setPublishedByPublisher);


// Hooks cho việc cập nhật pageSize
export const useSetListNovelPageSize = () => userNovelStore((state) => state.actions.setListNovelPageSize);
export const useSetFilteredNovelListPageSize = () => userNovelStore((state) => state.actions.setFilteredNovelListPageSize);
export const useSetJustReadNovelListPageSize = () => userNovelStore((state) => state.actions.setJustReadNovelListPageSize);
export const useSetPromotedNovelListPageSize = () => userNovelStore((state) => state.actions.setPromotedNovelListPageSize);
export const useSetBookShelfPageSize = () => userNovelStore((state) => state.actions.setBookShelfPageSize);
export const useSetBookMarkPageSize = () => userNovelStore((state) => state.actions.setBookMarkPageSize);



// Truy xuất từng field nhỏ
export const useCurrentNovelSlug = () => userNovelStore((state) => state.current.slug);


export const useSetListNovel = () => userNovelStore((state) => state.actions.setListNovel);
