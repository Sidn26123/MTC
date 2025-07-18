import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useSetListNovel } from '../stores/novelStore.js';

export const createNovel = async (data) => {
    const response = await api.post(API.NOVEL +"/novels/create", data);
    return response;
}

export const uploadNovelCover = async (novelId, file) => {
    const formData = new FormData();
    formData.append('file', file);
    console.log("Uploading novel cover for novelId:", novelId);
    console.log("File details:", file);
    const response = await api.post(
        API.FILES + `/novel/${novelId}/image/cover`,
        formData,
        {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        }
    );

    console.log("Upload novel cover response:", response);
    return response;
};
