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



export const googleCallback = async (code) => {
    const response = await httpClient.get(API.GOOGLE_CALLBACK + `?code=${code}`);
    console.log("Google Callback Response:", response.data?.data?.token);
    setToken(response.data?.data?.token);

    console.log("get token", getToken());
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
    return payload ? payload.user_id : null;
}

export const getUserIdFromToken = (token) => {
    if (!token) return null;

    const payload = parseJwt(token);
    return payload ? payload.user_id : null;
}


export const logInWithGoogle = async () => {
    const response = await httpClient.get(API.LOGIN_GOOGLE);
    setToken(response.data?.result?.token);
    return response;
};

export const isLoggedIn = () => {
    const token = getToken();
    if (!token) return false;

    const payload = parseJwt(token);
    return payload && payload.exp > Date.now() / 1000; // Check if token is not expired
}

export const isTokenValid = (token) => {
    if (!token) return false;

    const payload = parseJwt(token);
    return payload && payload.exp > Date.now() / 1000; // Check if token is not expired
}