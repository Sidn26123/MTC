import React from "react";


import { Link } from 'react-router-dom';

const adminRoutesPrefix = "/admin";

const AdminSidebar = () => (
  <div className="w-64 h-screen bg-gray-800 text-white p-4">
    <h2 className="text-xl font-bold mb-4">Admin</h2>
    <nav className="flex flex-col gap-2">
      <Link to="/admin/dashboard">Dashboard</Link>
      <Link to="/admin/users">Hồ sơ</Link>
      
      <Link to="/admin/novels">Quản lý truyện</Link>
      <Link to="/admin/accounts">Quản lý tài khoản</Link>
      <Link to="/admin/settings">Cài đặt</Link>
      <Link to={`${adminRoutesPrefix}/category`}>Quản lý Danh Mục</Link>
        {/* Add more links as needed */}
    </nav>
  </div>
);

export default AdminSidebar;
