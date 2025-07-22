import { Route } from 'react-router-dom';
import AdminLayout from '../layouts/AdminLayout';
import Dashboard from '../pages/admins/Dashboard';
import ManageUsers from '../pages/admins/UserManagePage.jsx';
import ProtectedRoute from '../components/global/ProtectedRoute.jsx';
import NovelManagePage from '../pages/admins/NovelManagePage.jsx';
import AccountManagePage from '../pages/admins/AccountManagePage.jsx';
import CategoryManagePage from '../pages/admins/CategoryManagePage.jsx';
import AnalyticsPage from '../pages/admins/AnalyticsPage.jsx';
import { hasScope } from '../services/authoriazationService.js';

const adminRoutesPrefix = "/admin";

const AdminRoutes = ({ user, userRoles}) => {


    console.log("AdminRoutes user: ", user);
    console.log("AdminRoutes userRoles: ", userRoles);

    const isAdmin = hasScope(userRoles, "ROLE_ADMIN");

    return (
      //  <Route element={<ProtectedRoute isAllowed={isAdmin} />}>
            <Route element={<AdminLayout />}>
                <Route path={`${adminRoutesPrefix}/dashboard`} element={<Dashboard />} />
                <Route path={`${adminRoutesPrefix}/novels`} element={<NovelManagePage />} />
                <Route path={`${adminRoutesPrefix}/users`} element={<ManageUsers />} />
                <Route path={`${adminRoutesPrefix}/accounts`} element={<AccountManagePage />} />
                <Route path={`${adminRoutesPrefix}/category`} element={<CategoryManagePage />} />
                <Route path={`${adminRoutesPrefix}/analytics`} element={<AnalyticsPage />} />
                {/* <Route path="settings" element={<Settings />} /> */}
            </Route>
        //</Route>
    );
};

export default AdminRoutes;
