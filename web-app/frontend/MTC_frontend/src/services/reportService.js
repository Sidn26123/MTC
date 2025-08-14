import httpClient from "../configurations/httpClient";
import { API , CONFIG} from "../configurations/configuration";
import { getToken } from "./localStorageService";
import api from '../middlewares/axios.js';

export const getAllReports = async (userId, role, page) => {
    // return await api.get(`${API.FEEDBACK}/reports/user/${userId}?role=${role}&page=${page}`);
    try {
        const response = await api.get(`${API.FEEDBACK}/reports/user/${userId}`, {
            params: {
                role: role,
                page: page
            }
        });
        return response;
    } catch (error) {
        console.error("Error fetching reports:", error);
        throw error;
    }
}