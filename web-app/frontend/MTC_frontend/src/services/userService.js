import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";
import api from '../middlewares/axios.js';
import useUserStore from '../stores/userStores.js';
import {logOut} from './authenticationService.js'
export const getMyInfo = async () => {
    // return await httpClient.get(API.MY_INFO, {
    //     headers: {
    //         Authorization: `Bearer ${getToken()}`,
    //     },
    // });
    return await api.get(API.MY_INFO);
};

export const getProfileById = async (userId) => {
    return await api.get(API.USER + "/byId?userId=" + userId);

}