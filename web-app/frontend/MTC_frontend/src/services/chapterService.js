import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";
import api from '../middlewares/axios.js';
import { useChapterActions, useCurrentChapterIdx } from '../stores/chapterStore.js';
import { useCurrentNovelSlug } from '../stores/novelStore.js';
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
    console.log("Response body:", response.data.result);
    // setCurrentChapter(response.data.result);
    return response;
};

export const getCurrentChapterContent = async ({novelSlug, chapterIdx}) => {


    let url = `${API.CHAPTER}/truyen/${novelSlug}/chuong-${chapterIdx}`;
    let response = await api.get(url);
    console.log("Response body:", response.data.result);
    return response;
}

export const setChapterContent = async ({chapterId, content}) => {
    const actions = useChapterActions();
    const setCurrentChapter = actions.setCurrentChapter;
    setCurrentChapter(content);
}