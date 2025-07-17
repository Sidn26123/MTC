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

