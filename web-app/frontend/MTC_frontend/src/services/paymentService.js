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

export const paymentDeposit = async (data) => {
    try {
        const response = await api.post(API.MOMO_PAYMENT, data);
        return response.data;
    } catch (error) {
        console.error("Error during payment deposit:", error);

        if (error.response) {
            console.error("Status:", error.response.status);
            console.error("Response data:", error.response.data);
        } else if (error.request) {
            console.error("No response received. Request was:", error.request);
        } else {
            console.error("Request config error:", error.message);
        }

        return undefined; // hoặc throw error nếu muốn xử lý tiếp phía trên
    }
};
