import { toast } from 'react-toastify';
import { toastAutoCloseTimeLong, toastAutoCloseTimeShort } from '../../constants/const.js';

const ScrollableWrapper = ({ height = "75vh", children }) => {
    return (
        <div className={`overflow-y-auto`} style={{ maxHeight: height }}>
            {children}
        </div>
    );
};

export { ScrollableWrapper };



export const showErrorWrapper = (error) => {
    if (error.response && error.response.data && error.response.data.message) {
        toast.error(error.response.data.message, {
            position: 'top-right',
            autoClose: toastAutoCloseTimeShort,
            hideProgressBar: false,
            pauseOnHover: true,
            draggable: true,
            theme: 'colored',
        });
    } else {
        toast.error('An unexpected error occurred', {
            position: 'top-right',
            autoClose: toastAutoCloseTimeShort,
            hideProgressBar: false,
            pauseOnHover: true,
            draggable: true,
            theme: 'colored',
        });
    }
}