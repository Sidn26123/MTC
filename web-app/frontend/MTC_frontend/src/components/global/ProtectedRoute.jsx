import { Navigate, Outlet } from "react-router-dom";
import {showWarning} from '../../utils/ToastUtils.js';

const ProtectedRoute = ({ isAllowed, redirectTo = "/" }) => {
    if (!isAllowed) {
        showWarning("Bạn không có quyền truy cập trang này");
        return <Navigate to={redirectTo} replace />;
    }
    return <Outlet />;
};

export default ProtectedRoute;
