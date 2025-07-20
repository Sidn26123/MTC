// export const extractPageData = (data) => {
//     const { secret, ...rest } = data;
//     return rest;
// };

export const extractPageData = (data, excludedFields = []) => {
    if (!data || typeof data !== 'object') return {};

    return Object.fromEntries(
        Object.entries(data).filter(([key]) => !excludedFields.includes(key))
    );
};

export const initPageData = () => {
    return {
        page: 1,
        size: 10,
        totalPages: 0,
        totalItems: 0,
        items: [],
    };
}