import { API, API_CATEGORY } from '../configurations/configuration';
import api from '../middlewares/axios.js';
// import { useCategories } from '../stores/categoryStore.js';

export const getCategories = async () => {
    const response = await api.get(API_CATEGORY.GENRES)

    // useSetNovelStatus(response.data.result);
    // useSetListNovel(response.data);
    return response;
}