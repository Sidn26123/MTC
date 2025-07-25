import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const sendRating = async (rating) => {
    console.log("Sending rating:", rating);
    try{
        const response = await api.post(API.FEEDBACK_RATING, rating);

    }
    catch (error) {
        console.error("Error sending rating:", error);
        throw error; // Re-throw the error for further handling if needed
    }
    return response;
}

export const getRatingOfNovel = async (novelId) => {
    const response = await api.get(API.FEEDBACK_RATING + '/' + novelId);
    return response;
}

export const getChildCommentsOfRating = (ratingId, comments) => {
    //return danh sách các comment con của rating có id là ratingId, type là rating

    const childComments = comments.filter(comment => comment.parentId === ratingId && comment.type === 'rating');

    return childComments;
}

export const getChildCommentsOfComment = (commentId, comments) => {
    //return danh sách các comment con của comment có id là commentId, type là comment

    const childComments = comments.filter(comment => comment.parentId === commentId && comment.type === 'comment');

    return childComments;
}

export const reportNovel = async (data) => {
    const response = await api.post(API.REPORT + "/novel", data);
    return response;
}

export const reportComment = async (data) => {
    const response = await api.post(API.REPORT + "/comment", data);
    return response;
}

export const reportRating = async (data) => {
    const response = await api.post(API.REPORT + "/rating", data);
    return response;
}

export const fetchTickets = async (status) => {
    try {
        const res = await api.get(API.REPORT + "/filter", {
            params: {
                page: 0,
                size: 10,
                status: "PENDING",
            },

        });
        return res;
    } catch (error) {
        console.error(error);
    }
}

export const fetchMyPullReports = async () => {
    try {
        const res = await api.get(API.REPORT + "/filter", {
            params: {
                page: 0,
                size: 10,

            },

        });
        return res;
    } catch (error) {
        console.error(error);
    }
}

export const fetchMyIssues = async () => {
    try {
        const res = await api.get(API.REPORT + "/filter", {
            params: {
                page: 0,
                size: 10,

            },

        });
        return res;
    } catch (error) {
        console.error(error);
    }
}

export const getReportById = async (id) => {
    try {
        const res = await api.get(API.REPORT + "/" + id);
        return res;
    } catch (error) {
        console.error(error);
    }
}

export const getCommentListOfReport = async (reportId) => {
    try {
        const res = await api.get(API.REPORT + "/report-comments/report/" + reportId);
        return res;
    } catch (error) {
        console.error(error);
    }
}