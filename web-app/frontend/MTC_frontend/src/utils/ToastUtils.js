// src/utils/toast.js
import { toast } from 'react-toastify';
const autoClose = 1000;
export const showSuccess = (message) => {
    toast.success(message, {
        position: 'top-right',
        autoClose: autoClose,
        hideProgressBar: false,
        pauseOnHover: true,
        draggable: true,
        theme: 'colored',
    });
};

export const showError = (message) => {
    toast.error(message, {
        position: 'top-right',
        autoClose: autoClose,
        hideProgressBar: false,
        pauseOnHover: true,
        draggable: true,
        theme: 'colored',
    });
};

export const showInfo = (message) => {
    toast.info(message, {
        position: 'top-right',
        autoClose: autoClose,
        hideProgressBar: false,
        pauseOnHover: true,
        draggable: true,
        theme: 'colored',
    });
};

export const showWarning = (message) => {
    toast.warning(message, {
        position: 'top-right',
        autoClose: autoClose,
        hideProgressBar: false,
        pauseOnHover: true,
        draggable: true,
        theme: 'colored',
    });
};
