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