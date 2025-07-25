import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const getMyBookshelfList = async (userId) => {
    const response = await api.get(API.BOOKSHELF + "/user/" + userId);
    return response;
}

export const getBookshelfItems = async (bookshelfId, data) => {
    const response = await api.get(API.BOOKSHELF + "/" + bookshelfId + "/items",
        {
            params: data
        });
    return response;
}

export const getItemOfBookshelfByNovelId = async (bookshelfId, novelId) => {
    const response = await api.get(API.BOOKSHELF + bookshelfId + "/items/" + novelId);
    return response;
}

export const getCurrentBookshelf = async () => {
    const response = await api.get(API.BOOKSHELF + "/me");
    return response;
}

export const getMyBookmarkedNovels = async (data) => {
    const response = await api.get(API.BOOKMARKED + "/me", {
        params: data
    });
    return response;
}

export const deleteBookshelfItem = async (bookshelfId, novelId) => {
    const response = await api.delete(API.BOOKSHELF + "/" + bookshelfId + "/items/" + novelId);
    return response;
}

export const deleteBookmarkedNovel = async (novelId) => {
    const response = await api.delete(API.BOOKMARKED + "/" + novelId);
    return response;
}

export const updateBookmarkItem = async (novelId, data) => {
    const response = await api.put(API.BOOKMARKED + "/" + novelId, data);
    return response;
}

export const updateBookshelfItem = async (bookshelfId, data) => {
    const response = await api.put(API.BOOKSHELF + "/items/" + bookshelfId , data);
    return response;
}