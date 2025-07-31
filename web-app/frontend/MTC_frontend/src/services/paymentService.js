import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const getPaymentHistory = async (userId, pageData) => {
    try {
        const response = await api.get(API.TRANSACTIONS + "/user/" + userId, {
            params: pageData
        });
        return response;
    } catch (error) {
        console.error("Error fetching payment history:", error);
        throw error; // Re-throw the error for further handling if needed
    }
}

export const getMyWallet = async (userId) => {
    try {
        const response = await api.get(API.WALLET + "/user/" + userId);
        return response;
    } catch (error) {
        console.error("Error fetching wallet data:", error);
        throw error; // Re-throw the error for further handling if needed
    }
}