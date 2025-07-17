import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useSetListNovel } from '../stores/novelStore.js';

export const createNovel = async (data) => {
    const response = await api.post(API.NOVEL +"/novels/create", data);
    return response;
}