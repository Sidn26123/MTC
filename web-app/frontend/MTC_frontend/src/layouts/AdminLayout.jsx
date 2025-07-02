import { Outlet } from "react-router-dom";
import AdminNavbar from "../components/global/AdminNavbar";
import AdminSidebar from "../components/global/AdminSidebar";

const AdminLayout = () => {
    return (
        <div className="">
            <AdminNavbar />
            <div className="">
                <AdminSidebar />
                <main className="n">
                    <Outlet /> {/* Render các trang con */}
                </main>
            </div>
        </div>
    );
};

export default AdminLayout;
