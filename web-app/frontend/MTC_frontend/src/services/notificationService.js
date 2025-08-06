import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useSetListNovel } from '../stores/novelStore.js';

export const getNotifications = async (pageData) => {
    const response = await api.get(API.NOTIFICATION, { params: pageData });

    // useSetNovelStatus(response.data.result);
    // useSetListNovel(response.data);
    return response;
}
export const getUnreadCount = async () => {
    const response = await api.get(API.NOTIFICATION + "/unread-count");
    return response;
}