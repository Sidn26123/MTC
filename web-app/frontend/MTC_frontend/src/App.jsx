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
import { getTokenFromLocalStorage } from './services/authenticationService.js';
import Page404 from './components/global/Page404.jsx';
import Page500 from './components/global/Page500.jsx';
import { ToastContainer } from 'react-toastify';
import RegisterPage from './pages/users/RegisterPage.jsx';
import { useAuthRoles } from './stores/authStore.js';
import ContentModRoutes from './routers/ContentModRoutes.jsx';
import { useNavigate } from 'react-router';


const App = () => {
    const [loading, setLoading] = useState(true);
    const user = useUserStore((state => state.user));
    const setUser = useUserStore((state => state.setUser));
    const userRoles = useAuthRoles();

    useEffect(() => {
        const token = getTokenFromLocalStorage(); // Lấy token từ localStorage
        if (token) {

            api.get(API.INFO_ME).then(r => {
                    setUser(r.data.result);
            }
            );
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
