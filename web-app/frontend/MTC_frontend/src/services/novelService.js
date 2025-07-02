import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useSetListNovel } from '../stores/novelStore.js';

export const getNovels = async () => {
    const response = await api.get(API.NOVEL +"/novels")

    // useSetNovelStatus(response.data.result);
    // useSetListNovel(response.data);
    return response;
}

export const getNovelBySlug = async (slug) => {
    const response = await api.get(API.NOVEL + "/novels/" + slug);
    return response;
}

export const getFilteredNovels = async (filter) => {
    const response = await api.post(API.NOVEL + "/novels/filter", filter);
    return response;
};

export const isNovelBookmarked = async (novelId, novelList) => {
    //check if novelId is in novelList
    const isBookmarked = novelList.some((novel) => novel.id === novelId);
    return isBookmarked;
}