import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { parseJwt } from '../utils/JWTUtils.js';

export const logIn = async (username, password) => {

    const response = await httpClient.post(API.LOGIN, {
        username: username,
        password: password,
    });
    console.log("Response body:", response.data);
    setToken(response.data?.result?.token);

    return response;
};

export const logOut = () => {
    removeToken();
};

export const isAuthenticated = () => {
    return getToken();
};

export const getTokenFromLocalStorage = () => {
    return getToken();
}

export const getUserIdFromContext = () => {
    const token = getToken();
    if (!token) return null;

    const payload = parseJwt(token);
    return payload ? payload.userId : null;
}
export const logInWithGoogle = async () => {
    const response = await httpClient.get(API.LOGIN_GOOGLE);
    setToken(response.data?.result?.token);
    return response;
};