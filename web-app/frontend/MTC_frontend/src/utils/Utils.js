export const extractResult = (response) => {
    if (response && response.data && response.data.result) {
        return response.data.result;
    } else {
        throw new Error("Invalid response format");
    }
}

export const getObjectFromList = (list, key, value) => {
    if (!list || !Array.isArray(list)) {
        throw new Error("Invalid list provided");
    }
    return list.find(item => item[key] === value) || null;
}

//Lam tron 2 con so sau dau .
export const roundToTwoDecimalPlaces = (num) => {
    if (typeof num !== 'number') {
        throw new Error("Invalid number provided");
    }
    return Math.round((num + Number.EPSILON) * 100) / 100;
}