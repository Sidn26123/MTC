import React from "react";
import { logIn } from '../../services/authenticationService.js';
import { getMyInfo } from '../../services/userService.js';
import useUserStore from '../../stores/userStores.js';
import { API } from '../../configurations/configuration.js';
import api from '../../middlewares/axios.js';
function UpdateAccountPage() {

    const setUser = useUserStore((state) => state.setUser);
    const getUser = useUserStore((state) => state.user);


    const handleSubmit = async (event) => {
        event.preventDefault();
    //
    //     try {
    //         const response = await logIn("admin", "admin");
    //         console.log("Response body:", response.data);
    //         const profile = await fetchUserInfo();
    //         setUser(profile.result);
    //         // console.log("Result profile:", profile.result);
    //         console.log("User profile:", getUser);
    //     } catch (error) {
    //         console.error("Error during login:", error);
    //         // const errorResponse = error.response.data;
    //     }
    // };
    //
    // const fetchUserInfo = async () => {
    //     try {
    //         const response = await api.get(API.MY_INFO);
    //         return response.data;
    //     } catch (err) {
    //         console.error('Lỗi khi lấy user info:', err);
    //     }
    };

    return (
        <>
            <button onClick={handleSubmit} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                Login
            </button>
        </>
    );
}

export default UpdateAccountPage;