import { Route } from "react-router-dom";
import UserLayout from "../layouts/UserLayout";
import Home from "../pages/users/HomePage.jsx";
import Profile from "../pages/users/ProfilePage.jsx";
import SettingsPage from "../pages/users/SettingsPage.jsx";
import ReadingPage from "../pages/novels/ReadingPage.jsx";
import UpgradeAccountPage from "../pages/users/UpgradeAccountPage.jsx";
import MyBookShelfPage from "../pages/novels/MyBookShelfPage.jsx";
import PaymentHistoryPage from "../pages/users/PaymentHistoryPage.jsx";
import DonatePage from "../pages/users/DonatePage.jsx";
import NovelOverviewPage from "../pages/novels/NovelOverviewPage.jsx";
import NovelListPage from '../pages/novels/NovelListPage.jsx';
import ProtectedRoute from '../components/global/ProtectedRoute.jsx';
import RAGApp from '../components/chatbot/RagApp.jsx';
import MyReportPage from '../pages/users/MyReportPage.jsx';
import ReportDetailPage from '../pages/publishers/ReportDetailPage.jsx';
import NotificationListPage from '../pages/users/NotificationListPage.jsx';
import DonateChooseMomoPage from '../pages/users/DonateChooseMomoPage.jsx';
import PaymentSuccess from "../pages/users/PaymentSuccess.jsx";
import DonateChooseVnpayPage from '../pages/users/DonateChooseVnpayPage.jsx';

const UserRoutes = ({user}) => {
    return (
        <Route element={<UserLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="/truyen" element={<NovelListPage />} />
            <Route path="/truyen/:slug" element={<NovelOverviewPage />} />
            <Route path="/truyen/:slug/:id" element={<ReadingPage />} />
            <Route element={<ProtectedRoute isAllowed={user?.username !== ""} />}>
                <Route path="/profile" element={<Profile />} />
                <Route path="/cai-dat" element={<SettingsPage />} />
                <Route path="/nang-cap-tai-khoan" element={<UpgradeAccountPage />} />
                <Route path="/tu-truyen" element={<MyBookShelfPage />} />
                <Route path="/lich-su-giao-dich" element={<PaymentHistoryPage />} />
                <Route path="/nap-tien" element={<DonatePage />} />
                <Route path="/nap-tien/momo" element={<DonateChooseMomoPage />} />
                <Route path="/nap-tien/vnpay" element={<DonateChooseVnpayPage />} />
                {/*<Route path="/nap-tien/:vnpay" element={<DonateChoosePage />} />*/}
                <Route path="/yeu-cau-ho-tro" element={<MyReportPage />} />
                <Route path="/yeu-cau-ho-tro/:reportId" element={<ReportDetailPage />} />
                <Route path="/thong-bao" element={<NotificationListPage />} />
                <Route path="/chatbot" element={<RAGApp />} />
            </Route>
            <Route path="/payment-success" element={<PaymentSuccess />} />
        </Route>
    );
};

export default UserRoutes;
