import axios from 'axios';
import { getTokenFromLocalStorage } from './../services/authenticationService.js';
import { CONFIG } from '../configurations/configuration';
import { redirect, useNavigate } from 'react-router';
import { toast } from 'react-toastify';
import { showError } from '../utils/ToastUtils.js';
const api = axios.create({
    baseURL: CONFIG.API_GATEWAY,
    timeout: 30000,
    headers: {
        'Content-Type': 'application/json',
    },
    paramsSerializer: (params) => {
        const searchParams = new URLSearchParams();
        for (const key in params) {
            const value = params[key];
            if (Array.isArray(value)) {
                value.forEach((val) => searchParams.append(key, val));
            } else if (value !== undefined && value !== null) {
                searchParams.append(key, value);
            }
        }
        return searchParams.toString();
    }

});

api.interceptors.request.use(
    (config) => {
        const token = getTokenFromLocalStorage();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);
// ===== RESPONSE INTERCEPTOR =====
api.interceptors.response.use(
    (response) => response,
    (error) => {
        const status = error.response?.status;

        if (status === 401) {
            showError('Bạn cần đăng nhập để tiếp tục.');
            // Optional: chuyển về trang login
            // window.location.href = '/login';
        } else if (status === 500) {
            // const navigate = useNavigate();
            window.location.href = '/500'; // Trang 500 bạn tự tạo
            // navigate("/500");
        }

        return Promise.reject(error);
    }
);

export const setupInterceptors = (navigate) => {
    api.interceptors.response.use(
        (response) => response,
        (error) => {
            const status = error.response?.status;

            if (status === 401) {
                showError('Bạn cần đăng nhập để tiếp tục.');
            } else if (status === 500) {
                navigate("/500");
            }

            return Promise.reject(error);
        }
    );
};

export default api;
