import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useChapterActions } from '../stores/chapterStore.js';
import { getCurrentFormattedTimeForApi } from '../utils/DatetimeUtil.js';
import { showError } from '../utils/ToastUtils.js';

export const getChapterContentByChapterId = async ({
    novelSlug,
    chapterIdx,
}) => {
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

export const getCurrentChapterContent = async ({ novelSlug, chapterIdx }) => {
    let url = `${API.CHAPTER}/truyen/${novelSlug}/chuong-${chapterIdx}`;
    let response;
    try {
        response = await api.get(url);
    } catch (error) {
        showError(error.response.data.message || 'Lỗi khi lấy nội dung chương');
        return error.response;
    }
    // console.log(response.data.result);
    return response;
};

export const setChapterContent = async ({ chapterId, content }) => {
    const actions = useChapterActions();
    const setCurrentChapter = actions.setCurrentChapter;
    setCurrentChapter(content);
};

export const getCurrentPublisherChapter = async (novelId, limit) => {
    let url = `${API.CHAPTER}/filter`;
    let response = await api.post(url, {
        novelId: novelId,
        sortBy: 'chapterIdx',
        size: limit,
        page: 1,
        publishedBefore: getCurrentFormattedTimeForApi(),
    });

    return response;
};

export const getChapterById = async (chapterId) => {
    const response = await api.get(`${API.CHAPTER}/${chapterId}`);
    return response;
};

export const getChapterByIdWithContent = async (chapterId) => {
    const response = await api.get(`${API.CHAPTER}/${chapterId}/content`);
    return response;
};

export const getPublishedByPublisher = async (publisherId, limit) => {
    let url = `${API.NOVEL}/novels/filter`;
    let response = await api.post(url, {
        sortBy: 'totalViews',
        currentPublisher: publisherId,
        size: limit,
        page: 1,
    });

    return response;
};

export const getFilteredChapters = async (filter) => {
    const response = await api.post(API.NOVEL + '/chapters/filter', filter);
    return response;
};

export const uploadChapter = async (data) => {
    console.log(data);
    const response = await api.post(API.CHAPTER + '/create', data);
    // console.log("Upload chapter response: ", response);
    return response;
};

export const uploadChapters = async (data) => {
    const response = await api.post(API.CHAPTER + '/createMany', data);
    // console.log("Upload chapters response: ", response);
    return response;
};

export const getChapterByNovelSlugAndIdx = async (novelSlug, chapterIdx) => {
    let url = `${API.CHAPTER}/truyen/${novelSlug}/chuong/${chapterIdx}`;
    let response = await api.get(url);

    return response;
};

export const navigateToChapter = async (novelId, chapterId, data) => {
    try{
        const response = await api.post(
            `${API.CHAPTER}/novel/${novelId}/chapter/${chapterId}/navigation`,
            data
        );
        return response;

    } catch (error) {
        console.error('Error navigating to chapter:', error);
        throw error; // Re-throw the error for further handling
    }

};

export const startReadChapter = async (chapterId, data) => {
    return await api.post(`${API.CHAPTER}/${chapterId}/read`, data);
};

export const getJustPublishedChapters = async (page, size) => {
    const response = await api.get(`${API.CHAPTER}/top/published`, {
        params: {
            page: page,
            size: size,
        },
    });
    return response;
};

export const checkCanReadChapter = async (chapterId) => {
    const response = await api.get(`${API.CONTENT_PURCHASED}/can-read`, {
        params: {
            chapterId: chapterId,
        },
    });
    return response;
};
export const checkCanReadChapterOrNovel = async (type, itemId) => {
    var data = {};
    if (type === 'chapter') {
        data.chapterId = itemId;
    } else if (type === 'novel') {
        data.novelId = itemId;
    }

    const response = await api.get(`${API.CONTENT_PURCHASED}/can-read`, {
        params: data,
    });
    return response;
};
export const checkChapterReadable = async (chapterId) => {
    const response = await api.get(`${API.CHAPTER}/chapter/readable`, {
        params: {
            chapterId: chapterId,
        },
    });
    return response;
};

export const updateChapter = async (chapterId, data) => {
    const response = await api.put(`${API.CHAPTER}/${chapterId}`, data);
    console.log('Update chapter response: ', response);
    return response;
};

export const deleteChapter = async (chapterId) => {
    const response = await api.delete(`${API.CHAPTER}/${chapterId}`);
    console.log('Delete chapter response: ', response);
    return response;
};