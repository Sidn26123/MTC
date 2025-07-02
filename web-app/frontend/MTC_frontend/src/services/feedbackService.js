import api from '../middlewares/axios.js';
import { API } from '../configurations/configuration.js';

export const sendRating = async (rating) => {
    const response = await api.post(API.FEEDBACK_RATING, rating);
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

