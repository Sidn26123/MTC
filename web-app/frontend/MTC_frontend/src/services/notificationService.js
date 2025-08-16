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

export const readNotification = async (notificationId) => {
    const response = await api.put(API.NOTIFICATION + "/"+ notificationId + "/read");
    return response;
}

export const markAllRead = async () => {
    const response = await api.get(API.NOTIFICATION + "/mark-all-read");
    return response;
}

export const archiveNotification = async (notificationId) => {
    const response = await api.get(API.NOTIFICATION + "/"+ notificationId + "/archive");
    return response;
}