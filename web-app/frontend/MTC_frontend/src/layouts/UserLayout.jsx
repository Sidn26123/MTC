import { Outlet } from "react-router-dom";
import UserNavbar from "../components/global/Navbar.jsx";
// import UserSidebar from "../components/UserSidebar";

const UserLayout = () => {
    return (
        <div className={"background-color h-screen"}>
            <div>
                <UserNavbar />
                <div className="px-20 ">
                    <main className="">
                        <Outlet />
                    </main>
                </div>
            </div>

        </div>
    );
};

export default UserLayout;
