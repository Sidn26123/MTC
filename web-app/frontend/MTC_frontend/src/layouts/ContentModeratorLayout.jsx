import { Outlet } from "react-router-dom";
import AdminNavbar from '../components/global/AdminNavbar.jsx';
import AdminSidebar from '../components/global/AdminSidebar.jsx';
import ContentModeratorNavbar from '../components/global/ContentModeratorNarbar.jsx';
import ContentModeratorSidebar from '../components/global/ContentModeratorSidebar.jsx';

const ContentModeratorLayout = () => {
    return (
        <div className="">
            <ContentModeratorSidebar />
            <div className="">
                <AdminNavbar />
                <main className="n">
                    <Outlet /> {/* Render các trang con */}
                </main>
            </div>
        </div>
    );
}

export default ContentModeratorLayout;