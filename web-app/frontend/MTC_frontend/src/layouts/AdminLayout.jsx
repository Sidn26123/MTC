// // // layout/AdminLayout.jsx
// // import Sidebar from '../components/Sidebar';
// // import { Outlet } from 'react-router-dom';

// // const AdminLayout = () => (
// //   <div className="flex">
// //     <Sidebar />
// //     <div className="flex-1 p-6 bg-gray-100 min-h-screen">
// //       <Outlet /> {/* nơi hiển thị nội dung từng trang */}
// //     </div>
// //   </div>
// // );

// // export default AdminLayout;


// import { Outlet } from "react-router-dom";
// import AdminNavbar from "../components/global/AdminNavbar";
// import AdminSidebar from "../components/global/AdminSidebar";

// // const AdminLayout = () => {
// //     return (
// //             <div className="flex flex-row">
// //                 <div className={"w-2/12 "}>
// //                     <AdminSidebar />
// //                 </div>
// //                 <div className="w-10/12">
// //                     <div className={"flex flex-col p-5"}>
// //                         {/* <div className={"pb-3 w-full"}>
// //                             <AdminNavbar />
// //                         </div> */}
// //                         <div className="">
// //                             <main className="n">
// //                                 <Outlet /> {/* Render các trang con */}
// //                             </main>
// //                         </div>
// //                     </div>
    
    
    
// //                 </div>
// //             </div>
// //         );

// // };

// const AdminLayout = () => {
//   return (
//     <div className="flex flex-row">
//       {/* Sidebar chỉ hiển thị trên lg trở lên */}
//       <div className="hidden lg:block w-64">
//         <AdminSidebar />
//       </div>

//       {/* Main content */}
//       <div className="flex-1 lg:ml-64">
//         <div className="flex flex-col p-5">
//           <main>
//             <Outlet /> {/* Render các trang con */}
//           </main>
//         </div>
//       </div>
//     </div>
//   );
// };


// export default AdminLayout;



//--------------------------------------------------------------------------


import { Outlet } from "react-router-dom";
import { useState } from "react";
import AdminNavbar from "../components/global/AdminNavbar";
import AdminSidebar from "../components/global/AdminSidebar";
import { Menu } from "lucide-react";

const AdminLayout = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const toggleSidebar = () => setSidebarOpen(!sidebarOpen);
  const closeSidebar = () => setSidebarOpen(false);

  return (
    <div className="flex flex-row relative">
      {/* Sidebar */}
      <AdminSidebar isOpen={sidebarOpen} onClose={closeSidebar} />

      {/* Main content */}
      <div className="flex-1 lg:ml-64 min-h-screen">
        {/* Mobile menu icon */}
        <div className="lg:hidden p-4">
          <button onClick={toggleSidebar}>
            <Menu className="w-6 h-6 text-white" />
          </button>
        </div>

        {/* Main body */}
        <div className="flex flex-col p-4">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default AdminLayout;
