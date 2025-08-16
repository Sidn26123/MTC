import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';
import { wrapApiFunctions } from '../components/wrapper/apiWrapper.js';

export const sendRating = async (rating) => {
    console.log("Sending rating:", rating);
    try{
        const response = await api.post(API.FEEDBACK_RATING, rating);

    }
    catch (error) {
        throw error; // Re-throw the error for further handling if needed
    }
    return response;
}

export const sendComment = async (comment) => {
    console.log("Sending comment:", comment);
    try{
        const response = await api.post(API.FEEDBACK_COMMENT, comment);
        return response;

    }
    catch (error) {
        console.error("Error sending comment:", error);
        throw error; // Re-throw the error for further handling if needed
    }
}
export const getRatingOfNovel = async (novelId) => {
    console.log("Fetching rating for novel with ID:", novelId);
    const response = await api.get(API.FEEDBACK_RATING + '/novel/' + novelId);
    return response;
}

export const getCommentOfNovel = async (novelId, pageData) => {
    try{
        const response = await api.get(API.FEEDBACK_COMMENT + '/novel/' + novelId, {
            params: pageData
        });
        console.log("Fetching comments for novel with ID:", novelId, "Response:", response);
        return response;

    }
    catch (error) {
        throw error; // Re-throw the error for further handling if needed
    }
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

export const filterComment = async (data) => {
    const response = await api.post(API.FEEDBACK_COMMENT + "/filter", data);
    return response;
}

export const filterRating = async (data) => {
    const response = await api.post(API.FEEDBACK_RATING + "/filter", data);
    return response;
}

export const getRatingOfNovelGroupByStart = async (novelId) => {
    const response = await api.get(API.FEEDBACK_RATING + '/novel/' + novelId + '/stats');
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
                status: status,
            },

        });
        return res;
    } catch (error) {
        console.error(error);
    }
}

export const filterMyReportToHandle = async (data) => {
    try {
        const res = await api.get(API.REPORT + "/filter" , {
            params: data,
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

export const commentOnReport = async (data) => {
    try {
        const res = await api.post(API.REPORT + "/report-comments", data);
        console.log("Comment on report response:", res);
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

export const getNewestRatings = async (limit) => {
    try {
        const res = await api.get(API.FEEDBACK_RATING + "/new", {
            params: {
                limit: limit,
            },
        });
        return res;
    } catch (error) {
        console.error("Error fetching newest ratings:", error);
        throw error; // Re-throw the error for further handling if needed
    }

}

export const updateReportStatus = async (reportId, data) => {
    try {
        const res = await api.put(API.REPORT + "/" + reportId + "/status", data);
        return res;
    } catch (error) {
        console.error("Error updating report status:", error);
        throw error; // Re-throw the error for further handling if needed
    }
}

const apiFunctions = {
    sendRating,
    sendComment,
    getRatingOfNovel
};

export default wrapApiFunctions(apiFunctions);