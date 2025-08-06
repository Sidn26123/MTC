// import React from "react";


// import { Link } from 'react-router-dom';

// const adminRoutesPrefix = "/admin";

// // const AdminSidebar = () => (
// //   <div className="w-64 h-screen bg-gray-800 text-white p-4 fixed">
// //     <h2 className="text-xl font-bold mb-4">Admin</h2>
// //     <nav className="flex flex-col gap-2">
// //       <Link to="/admin/dashboard">Dashboard</Link>
// //       <Link to="/admin/users">Hồ sơ</Link>

// //       <Link to="/admin/novels">Quản lý truyện</Link>
// //       <Link to="/admin/accounts">Quản lý tài khoản</Link>
// //       <Link to={`${adminRoutesPrefix}/category`}>Quản lý Danh Mục</Link>
// //       <Link to={`${adminRoutesPrefix}/analytics`}>Thống Kê</Link>
// //       <Link to="/admin/settings">Cài đặt</Link>
// //         {/* Add more links as needed */}
// //     </nav>
// //   </div>
// // );

// const AdminSidebar = () => (
//   <div className="hidden lg:block w-64 h-screen bg-gray-800 text-white p-4 fixed">
//     <h2 className="text-xl font-bold mb-4">Admin</h2>
//     <nav className="flex flex-col gap-2">
//       <Link to="/admin/dashboard">Dashboard</Link>
//       <Link to="/admin/users">Hồ sơ</Link>
//       <Link to="/admin/novels">Quản lý truyện</Link>
//       <Link to="/admin/accounts">Quản lý tài khoản</Link>
//       <Link to="/admin/category">Quản lý Danh Mục</Link>
//       <Link to="/admin/analytics">Thống Kê</Link>
//       <Link to="/admin/settings">Cài đặt</Link>
//     </nav>
//   </div>
// );


// export default AdminSidebar;

//---------------------



import React from "react";
import { Link } from 'react-router-dom';

const adminRoutesPrefix = "/admin";

const AdminSidebar = ({ isOpen, onClose }) => {
  return (
    <>
      {/* Desktop sidebar */}
      <div className="hidden lg:block w-64 h-screen bg-gray-800 text-white p-4 fixed z-20">
        <h2 className="text-xl font-bold mb-4">Admin</h2>
        <SidebarLinks />
      </div>

      {/* Mobile sidebar overlay */}
      {/* {isOpen && (
        <>
          <div
            className="fixed inset-0 bg-black bg-opacity-50 z-30"
            onClick={onClose}
          />
          <div className="fixed top-0 left-0 w-64 h-screen bg-gray-800 text-white p-4 z-40 transition-transform">
            <button onClick={onClose} className="text-right w-full text-white text-lg mb-4">✕</button>
            <SidebarLinks />
          </div>
        </>
      )} */}
      {isOpen && (
        <>
          <div
            className="fixed inset-0 bg-black bg-opacity-50 z-30 backdrop-blur-sm"
            onClick={onClose}
          />
          <div className="fixed top-0 left-0 w-64 h-screen bg-gray-800 text-white p-4 z-40 transition-transform">
            <button onClick={onClose} className="text-right w-full text-white text-lg mb-4">✕</button>
            <SidebarLinks onLinkClick={onClose} />
          </div>
        </>
      )}
    </>
  );
};

const SidebarLinks = ({onLinkClick} ) => (
  <nav className="flex flex-col gap-2">
    <Link to="/admin/dashboard" onClick={onLinkClick} >Dashboard</Link>
    <Link to="/admin/users" onClick={onLinkClick}>Hồ sơ</Link>
    <Link to="/admin/novels" onClick={onLinkClick}>Quản lý truyện</Link>
    <Link to="/admin/accounts" onClick={onLinkClick}>Quản lý tài khoản</Link>
    <Link to={`${adminRoutesPrefix}/category`} onClick={onLinkClick}>Quản lý Danh Mục</Link>
    <Link to={`${adminRoutesPrefix}/analytics`} onClick={onLinkClick}>Thống Kê</Link>
    <Link to={`${adminRoutesPrefix}/policies`} onClick={onLinkClick}>Quản lý Điều Khoản Dịch Vụ</Link>
    {/* <Link to="/admin/settings" onClick={onLinkClick}>Cài đặt</Link> */}
    <Link to="/admin/settings" onClick={onLinkClick}>Cài đặt</Link>
  </nav>
);

export default AdminSidebar;
