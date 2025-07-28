import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";
import api from '../middlewares/axios.js';
import { useChapterActions, useCurrentChapterIdx } from '../stores/chapterStore.js';
import { useCurrentNovelSlug } from '../stores/novelStore.js';
import { getCurrentFormattedTimeForApi } from '../utils/DatetimeUtil.js';
export const getChapterContentByChapterId = async ({novelSlug, chapterIdx}) => {
    // return await httpClient.get(API.MY_INFO, {
    //     headers: {
    //         Authorization: `Bearer ${getToken()}`,
    //     },
    // });
    // const actions = useChapterActions();
    // const setCurrentChapter = actions.setCurrentChapter;
    // var url = `${API.CHAPTER}/chapter/${chapterId}/content`;
    var url = `${API.CHAPTER}/truyen/${novelSlug}/chuong-${chapterIdx}`;
    var response = await api.get(url);
    // setCurrentChapter(response.data.result);
    return response;
};

export const getCurrentChapterContent = async ({novelSlug, chapterIdx}) => {


    let url = `${API.CHAPTER}/truyen/${novelSlug}/chuong-${chapterIdx}`;
    let response;
    try{
        response = await api.get(url);
    }
    catch (error) {
        console.log("Error: ", error);
        return error.response;
    }
    console.log(response.data.result);
    return response;
}

export const setChapterContent = async ({chapterId, content}) => {
    const actions = useChapterActions();
    const setCurrentChapter = actions.setCurrentChapter;
    setCurrentChapter(content);
}

export const getCurrentPublisherChapter = async (novelId, limit) => {
    let url = `${API.CHAPTER}/filter`;
    let response = await api.post(
        url,
        {
            novelId: novelId,
            sortBy: "chapterIdx",
            size: limit,
            page:1,
            publishedBefore: getCurrentFormattedTimeForApi()
        }
    )

    return response;
}

export const getChapterById = async (chapterId) => {
    // return getCurrentPublisherChapter()
}

export const getPublishedByPublisher = async (publisherId, limit) => {
    let url = `${API.NOVEL}/novels/filter`;
    let response = await api.post(
        url,
        {
            sortBy: "totalViews",
            currentPublisher: publisherId,
            size: limit,
            page:1,
        }
    )

    return response;
}

export const getFilteredChapters = async (filter) => {
    const response = await api.post(API.NOVEL + "/chapters/filter", filter);
    return response;
};

export const uploadChapter = async (data) => {
    const response = await api.post(API.CHAPTER + "/create", data);

    return response;
}

export const uploadChapters = async (data) => {
    const response = await api.post(API.CHAPTER + "/createMany", data);

    return response;
}

export const getChapterByNovelSlugAndIdx = async (novelSlug, chapterIdx) => {

}

export const navigateToChapter = async (novelId, chapterId, data) => {
    const response = await api.post(`${API.CHAPTER}/novel/${novelId}/chapter/${chapterId}/navigation`, data)
    console.log("Navigate to chapter response: ", response);
    return response
}

export const startReadChapter = async (chapterId, data) => {
    const response = await api.post(`${API.CHAPTER}/${chapterId}/read`, data)
}

export const getJustPublishedChapters = async (page, size) => {
    const response = await api.get(`${API.CHAPTER}/top/published`, {
        params: {
            page: page,
            size: size
        }
    });
    return response;
}