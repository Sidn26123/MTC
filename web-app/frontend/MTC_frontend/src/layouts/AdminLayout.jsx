
import { Outlet } from "react-router-dom";
import AdminNavbar from "../components/global/AdminNavbar";
import AdminSidebar from "../components/global/AdminSidebar";

const AdminLayout = () => {
    return (
            <div className="flex flex-row">
                <div className={"w-2/12 "}>
                    <AdminSidebar />
                </div>
                <div className="w-10/12">
                    <div className={"flex flex-col p-5"}>
                        {/* <div className={"pb-3 w-full"}>
                            <AdminNavbar />
                        </div> */}
                        <div className="">
                            <main className="n">
                                <Outlet /> {/* Render các trang con */}
                            </main>
                        </div>
                    </div>
    
    
    
                </div>
            </div>
        );

};

export default AdminLayout;
