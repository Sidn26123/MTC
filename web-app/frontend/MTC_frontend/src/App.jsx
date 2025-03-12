import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminRoutes from "./routers/AdminRoutes";
import UserRoutes from "./routers/UserRoutes";
import Login from "./pages/users/LoginPage.jsx";
import './index.css'

const App = () => {
    const user = { role: "admian" }; // Giả sử có logic lấy user từ context hoặc redux

    return (
        <div className={"scroll-container w-screen"}>
            <Router>
                <Routes>
                    <Route path="/login" element={<Login />} />
                    {AdminRoutes({ user })}
                    {UserRoutes()}
                </Routes>
            </Router>
        </div>

    );
};

export default App;
