export const extractResult = (response) => {
    if (response && response.data && response.data.result) {
        return response.data.result;
    } else {
        throw new Error("Invalid response format");
    }
}