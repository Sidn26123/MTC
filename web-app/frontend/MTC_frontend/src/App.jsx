import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminRoutes from "./routers/AdminRoutes";
import UserRoutes from "./routers/UserRoutes";
import Login from "./pages/users/LoginPage.jsx";
import './index.css'
import PublisherRoutes from './routers/PublisherRoutes.jsx';
import { useEffect, useState } from 'react';
import api, { setupInterceptors } from './middlewares/axios.js';
import { API } from './configurations/configuration.js';
import useUserStore from './stores/userStores.js';
import LoadingSpinning from './components/global/LoadingSpinning.jsx';
import { getTokenFromLocalStorage, getUserIdFromToken } from './services/authenticationService.js';
import Page404 from './components/global/Page404.jsx';
import Page500 from './components/global/Page500.jsx';
import { ToastContainer } from 'react-toastify';
import RegisterPage from './pages/users/RegisterPage.jsx';
import { useAuthRoles } from './stores/authStore.js';
import ContentModRoutes from './routers/ContentModRoutes.jsx';
import { useNavigate } from 'react-router';
import GoogleCallbackComponent from './components/global/GoogleCallbackComponent.jsx';
import { getCurrentBookshelf } from './services/bookshelfService.js';
import { useSetCurrentBookshelf } from './stores/bookshelfStore.js';
import { getMyWallet } from './services/paymentService.js';
import { useSetMyWallet } from './stores/paymentStore.js';


const App = () => {
    const [loading, setLoading] = useState(true);
    const user = useUserStore((state => state.user));
    const setUser = useUserStore((state => state.setUser));
    const userRoles = useAuthRoles();
    const setCurrentBookshelf = useSetCurrentBookshelf();
    const setMyWallet = useSetMyWallet();
    useEffect(() => {
        const token = getTokenFromLocalStorage(); // Lấy token từ localStorage
        const userId = getUserIdFromToken(token); // Lấy userId từ localStorage nếu cần
        if (token) {

            api.get(API.INFO_ME).then(r => {
                    setUser(r.data.result);
            });
            getCurrentBookshelf().then((response) => {
                if (response.data.result) {
                    console.log("Current Bookshelf:", response.data.result);
                    // Lưu thông tin sách vào store hoặc state nếu cần
                    setCurrentBookshelf(response.data.result);
                } else {
                    console.error("Failed to fetch current bookshelf.");
                }
            });
            getMyWallet(userId).then((response) => {
                console.log("Wallet Data:", response.data.result);
                setMyWallet(response.data.result);
            });
            setLoading(false); // Có token thì gọi API
        } else {
            setLoading(false); // Không có token thì không gọi API
        }

        // setupInterceptors(navigate);
    }, []);
    return (
        <div className={"s-container "}>
            {loading ? <>
                <LoadingSpinning />
                </>
            :
            <>
                <Router>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<RegisterPage />} />
                        <Route path="/auth/google/callback" element={<GoogleCallbackComponent />} />
                        {AdminRoutes({ user, userRoles })}
                        {UserRoutes({user})}
                        {PublisherRoutes({ user })}
                        {ContentModRoutes ({ user })}
                        <Route path="/500" element={<Page500 />} />
                        <Route path="/404" element={<Page404 />} />
                        <Route path="*" element={<Page404 />} />
                    </Routes>
                </Router>
            </>}
            <ToastContainer />
        </div>

    );
};

export default App;
