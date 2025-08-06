import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const getTermOfService = async () => {
    const response = await api.get(API.POLICY +"/terms-of-service");

    return response;
}

export const getAllPolicies = async () => {
    const response = await api.get(API.POLICY);
    console.log("getAllPolicies response: ", response);
    return response;
}

export const getPolicyBySlug = async (slug) => {
    const response = await api.get(API.POLICY +"/" + slug);
    console.log("getPolicyBySlug response: ", response);
    return response;
}

export const createPolicy = async (title, content) => {
    const response = await api.post(API.POLICY,{
        slug: title.toLowerCase().replace(/\s+/g, '-'),
        title: title,
        content: content,
    } );
    console.log("createPolicy response: ", response);
    return response;
}

export const updatePolicy = async (slug, titleValue, contentValue ) => {

    console.log("api: ", API.POLICY +"/" + slug);
    console.log("titleUpdate: ", titleValue);
    console.log("contentUpdate: ", contentValue);

    const response = await api.put(API.POLICY +"/" + slug, {
        title: titleValue,
        content: contentValue,
    });
    console.log("updatePolicy response: ", response);
    return response;
}