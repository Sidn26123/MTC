import httpClient from "../configurations/httpClient";
import { API , CONFIG} from "../configurations/configuration";
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
    return await api.get(API.MY_INFO + "/me");
};

export const getProfileById = async (userId) => {
    return await api.get(API.USER + "/byId?userId=" + userId);

}

export const getProfileById1 = async (userId) => {
    return await api.get(API.USER + "/" + userId);
}

export const getProfileByUserId = async (userId) => {
    return await api.get(API.USER + "/users/" + userId);
}

// export const getAllUsers = async () => {
//     return await api.get(CONFIG.IDENTITY_SERVICE + "/users");
// }

export const getAllUsers = async (page) => {
  return await api.get( `${CONFIG.IDENTITY_SERVICE}/users?page=${page}`);
    // `/api/v1/identity/users?page=${page}`);
};

export const updateUserRole = async (userId, roleUpdate) => {
    try {
        const response = await api.put(
            `${API.IDENTITY_SERVICE}/users/${userId}`,
            {
                roles: [roleUpdate], // nếu backend yêu cầu là mảng như ["ADMIN"]
            }
        );
        return response.data;
    } catch (error) {
        console.error("Lỗi khi cập nhật vai trò người dùng:", error);
        throw error;
    }
}