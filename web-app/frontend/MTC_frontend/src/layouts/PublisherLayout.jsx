import AdminNavbar from '../components/global/AdminNavbar.jsx';
import AdminSidebar from '../components/global/AdminSidebar.jsx';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/publishers/Sidebar.jsx';
import Navbar from '../components/publishers/Navbar.jsx';


const PublisherLayout = () => {
    return (
        <div className="flex flex-row">
            <div className={"w-2/12 background-color-lighter"}>
                <Sidebar />
            </div>
            <div className="w-10/12">
                <div className={"flex flex-col p-5"}>
                    <div className={"pb-3 w-full"}>
                        <Navbar />
                    </div>
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

export default PublisherLayout;
