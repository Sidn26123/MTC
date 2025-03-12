import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ isAllowed, redirectTo = "/login" }) => {
    if (!isAllowed) {
        return <Navigate to={redirectTo} replace />;
    }
    return <Outlet />;
};

export default ProtectedRoute;
