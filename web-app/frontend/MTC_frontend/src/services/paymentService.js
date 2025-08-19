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


export const purchaseContent = async (data) => {
    try {
        const response = await api.post(API.PAYMENT + "/content-purchases", data);
        return response.data;
    } catch (error) {
        console.error("Error during content purchase:", error);

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
}

export const promotion = async (data) => {
    try {
        const response = await api.post(API.PAYMENT + "/wallets/p/promotion", data);
        return response.data;
    } catch (error) {
        console.error("Error during content purchase:", error);

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
}

export const paymentVnpay = async (data) => {
    const url = `http://localhost:8889/api/v1/payment/wallets/deposit?amount=${data.amount}&bankCode=${data.bankCode}&userId=${data.userId}&currencyId=${data.currencyId}`;
    try {
        const response = await api.post(url, data);
        return response.data;
    }
    catch (error) {
        console.error("Error during VNPAY payment:", error);

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

    // const response = await fetch(url, {
    //     method: 'GET',
    //     headers: {
    //         'Authorization': `Bearer ${token}`,
    //         'Content-Type': 'application/json',
    //     },
    // })
};

export const vnpayCallback = async (data) => {
    // const url = `http://localhost:8889/api/v1/payment/payments/vn-pay-callback?vnp_Amount=${data.amount}&vnp_BankCode=${data.bankCode}&vnp_BankTranNo=${data.vnp_BankTranNo}&vnp_CardType=${data.vnp_CardType}&vnp_OrderInfo=${data.orderInfo}&vnp_PayDate=${data.payDate}&vnp_ResponseCode=${data.responseCode}&vnp_TmnCode=${data.tmnCode}&vnp_TransactionNo=${data.vnp_TransactionNo}&vnp_TransactionStatus=${data.vnp_TransactionStatus}&vnp_TxnRef=${data.vnp_TxnRef}`;
    const url = `http://localhost:8889/api/v1/payment/payments/vn-pay-callback?${data}`;
    console.log("VNPAY Callback URL:", url, data);
    try {
        const response = await api.get(url);
        return response.data;
    }
    catch (error) {
        return undefined; // hoặc throw error nếu muốn xử lý tiếp phía trên
    }
}