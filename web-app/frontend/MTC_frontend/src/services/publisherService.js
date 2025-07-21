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

export const getAllMyDrafts = async () => {
    const response = await api.get(API.DRAFT + "/my/all");
    return response;
}

export const updateDraft = async (draftId, data) => {
    const response = await api.put(API.DRAFT + "/" + draftId, data);
    return response;
}

export const getDraftById = async (draftId) => {
    const response = await api.get(API.DRAFT + "/" + draftId);
    return response;
}

export const deleteDraft = async (draftId) => {
    const response = await api.delete(API.DRAFT + "/" + draftId);
    return response;
}

export const createDraft = async (data) => {
    const response = await api.post(API.DRAFT + "/create", data);
    return response;
}