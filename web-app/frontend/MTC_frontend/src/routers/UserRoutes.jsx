import { Route } from "react-router-dom";
import UserLayout from "../layouts/UserLayout";
import Home from "../pages/users/HomePage.jsx";
import Profile from "../pages/users/ProfilePage.jsx";
import SettingsPage from "../pages/users/SettingsPage.jsx";
import ReadingPage from "../pages/users/ReadingPage.jsx";
import UpgradeAccountPage from "../pages/users/UpgradeAccountPage.jsx";
import MyBookShelfPage from "../pages/users/MyBookShelfPage.jsx";
import PaymentHistoryPage from "../pages/users/PaymentHistoryPage.jsx";
import DonatePage from "../pages/users/DonatePage.jsx";

const UserRoutes = () => {
    return (
        <Route element={<UserLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="/profile" element={<Profile />} />
            <Route path = "/cai-dat" element={<SettingsPage />} />
            <Route path="/truyen" element={<ReadingPage />} />
            <Route path="/nang-cap-tai-khoan" element={<UpgradeAccountPage />} />
            <Route path="/tu-truyen" element={<MyBookShelfPage />} />
            <Route path="/lich-su-giao-dich" element={<PaymentHistoryPage />} />
            <Route path="/nap-tien" element={<DonatePage />} />

        </Route>
    );
};

export default UserRoutes;
