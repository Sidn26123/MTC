import { showErrorWrapper } from './commonWrapper.jsx'; // file xử lý UI thông báo lỗi

// export const apiWrapper = (apiFn) => {
//     return async (...args) => {
//         try {
//             return await apiFn(...args);
//         } catch (error) {
//             showErrorWrapper(error);
//             throw error; // vẫn throw để nếu cần handle riêng ở nơi gọi
//         }
//     };
// };


export const wrapApiFunctions = (apiFunctions) => {
    const wrapped = {};
    for (const key in apiFunctions) {
        if (typeof apiFunctions[key] === 'function') {
            wrapped[key] = async (...args) => {
                try {
                    return await apiFunctions[key](...args);

                } catch (error) {
                    showErrorWrapper(error);
                    throw error;
                }
            };
        } else {
            wrapped[key] = apiFunctions[key];
        }
    }
    return wrapped;
};