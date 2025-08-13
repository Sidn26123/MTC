import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const RevueService = async (year) => {
        try {
            const response = await api.get(API.PAYMENT + `/payment-requests/venu/${year}`);
            console.log("Revue data:", response.data);
            return response;
        } catch (error) {
            console.error("Error fetching revue data:", error);
            throw error; // Re-throw the error for further handling if needed
        }
    
};