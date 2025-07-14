// // layout/AdminLayout.jsx
// import Sidebar from '../components/Sidebar';
// import { Outlet } from 'react-router-dom';

// const AdminLayout = () => (
//   <div className="flex">
//     <Sidebar />
//     <div className="flex-1 p-6 bg-gray-100 min-h-screen">
//       <Outlet /> {/* nơi hiển thị nội dung từng trang */}
//     </div>
//   </div>
// );

// export default AdminLayout;


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
