import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const getTermOfService = async () => {
    const response = await api.get(API.POLICY +"/terms-of-service");

    return response;
}

export const getPolicyBySlug = async (slug) => {
    const response = await api.get(API.POLICY +"/" + slug);
    console.log("getPolicyBySlug response: ", response);
    return response;
}