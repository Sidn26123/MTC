import { API } from '../configurations/configuration';
import api from '../middlewares/axios.js';

/**
 * Lấy thống kê truyện
 * @param {string} startDate - Định dạng "dd/MM/yyyy"
 * @param {string} endDate - Định dạng "dd/MM/yyyy"
 * @param {'DAY' | 'MONTH'} segmentType
 * @returns {Promise<Array<{ startTime: string, endTime: string, total: number }>>}
 */
export const getNovelStatistic = async (startDate, endDate, segmentType) => {
    try {
        const response = await api.get(API.NOVEL_STATISTIC, {
            params: {
                startDate,
                endDate,
                segmentType,
            },
        });

        // Trả về mảng kết quả
        return response.data.result;
    } catch (error) {
        console.error("Lỗi khi gọi API thống kê truyện:", error);
        throw error;
    }
};


export const getNovelApprovedStatistic = async (startDate, endDate, segmentType) => {
    try {
        const response = await api.get(API.NOVEL_STATISTIC + "/approved", {
            params: {
                startDate,
                endDate,
                segmentType,
            },
        });

        // Trả về mảng kết quả
        return response.data.result;
    } catch (error) {
        console.error("Lỗi khi gọi API thống kê truyện:", error);
        throw error;
    }
};


export const getNovelClassificationSects = async () => {
    const response = await api.get(API.NOVEL + "/novel-classification/sect" );
    return response;
}

export const getNovelClassificationGenres = async () => {
    const response = await api.get(API.NOVEL + "/novel-classification/genre" );
    return response;
}

export const getNovelClassificationCharacterTrait = async () => {
    const response = await api.get(API.NOVEL + "/novel-classification/character-trait" );
    return response;
}
