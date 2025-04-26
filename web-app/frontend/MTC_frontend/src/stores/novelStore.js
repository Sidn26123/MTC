import { create } from 'zustand';

const userNovelStore = create((set) => ({
    general: {

    },
    current: {
        novelId: "",
        novelSlug: "a-b-c-csssdssssdx",
        novelName: "",
        authorName: "",
    },
    chapter: {
        //...info
        content : "",
        authorNote: ""
    },
    listNovel: [],

    actions: {
    }

}));

// export default userNovelStore;

export const useNovelStore = () => userNovelStore((state => state.current))
export const useNovelGeneral = () => userNovelStore((state => state.general))
export const useNovelChapter = () => userNovelStore((state => state.chapter))
export const useNovelActions = () => userNovelStore((state => state.actions))
export const useNovelList = () => userNovelStore((state => state.listNovel))
export const useCurrentNovelSlug = () => userNovelStore((state => state.current.novelSlug))