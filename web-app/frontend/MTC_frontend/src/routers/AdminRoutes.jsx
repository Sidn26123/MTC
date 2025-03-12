import { Route } from "react-router-dom";
import AdminLayout from "../layouts/AdminLayout";
import Dashboard from "../pages/admins/Dashboard";
import ManageUsers from "../pages/admins/UserManagePage.jsx";
import ProtectedRoute from "../components/global/ProtectedRoute.jsx";

const AdminRoutes = ({ user }) => {
    return (
        <Route element={<ProtectedRoute isAllowed={user?.role === "admin"} />}>
            <Route element={<AdminLayout />}>
                <Route path="dashboard" element={<Dashboard />} />
                <Route path="users" element={<ManageUsers />} />
            </Route>
        </Route>
    );
};

export default AdminRoutes;
