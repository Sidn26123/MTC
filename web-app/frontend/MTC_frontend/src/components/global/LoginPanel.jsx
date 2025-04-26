import React from "react";
import { logIn } from '../../services/authenticationService.js';
import api from '../../middlewares/axios.js';
import { API } from '../../configurations/configuration.js';
import useUserStore from '../../stores/userStores.js';
import { redirect, useNavigate } from 'react-router';
import authStore, { useAuthActions } from '../../stores/authStore.js';
import {showSuccess} from '../../utils/ToastUtils.js';
import { getScopeArray, parseJwt } from '../../utils/JWTUtils.js';
// import {login} from '../../stores/authStore.js';
const LoginPanel = ({ onClose }) => {
    const navigate = useNavigate();
    const setUser = useUserStore((state) => state.setUser);
    const getUser = useUserStore((state) => state.user);
    // const authActions = authStore((state) => state.actions);
    const { login: loginAccount } = useAuthActions();
    const [userInfo, setUserInfo] = React.useState({
        email: "",
        password: "",
    })
    const onLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await logIn(userInfo.email, userInfo.password);
            const profile = await fetchUserInfo();
            setUser(profile.result);
            const token = response.data.result.token;

            const jwtData = parseJwt(token);
            const scope = getScopeArray(token);
            loginAccount({
                token: response.data.token,
                refreshToken: null,
                roles: scope,
                userId: jwtData.userId,
            })
            showSuccess("Đăng nhập thành công");
            // navigate(-1);
            onClose();
        } catch (error) {
        }

    }
    const fetchUserInfo = async () => {
        try {
            var myInfoStr = API.MY_INFO + "?username=" + userInfo.email;
            const response = await api.get(myInfoStr);
            return response.data;
        } catch (err) {
            console.error('Lỗi khi lấy user info:', err);
        }
    };

    const handleChangeEmail = (event) => {
        setUserInfo({
            ...userInfo,
            email: event.target.value,
        });
    }

    const handleChangePassword = (event) => {
        setUserInfo({
            ...userInfo,
            password: event.target.value,
        })
    }

    return (
        <div className="relative z-10" role="dialog" aria-modal="true">
            <div className="fixed inset-0 bg-gray-50/20 transition-opacity"></div>
            <div className="fixed inset-0 z-10 overflow-y-auto">
                <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0 ">
                    <div className="bg-panel p-6 max-w-xs sm:max-w-sm md:max-w-md w-full rounded-xl bg-background">
                        <div className="flex justify-between items-center">
                            <img
                                className="h-8 w-auto"
                                src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png"
                                alt=""
                            />
                            <h3 className="font-bold text-xl">Đăng nhập</h3>
                            <button
                                type="button"
                                className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
                                onClick={onClose}
                            >
                                <span className="sr-only">Close</span>
                                <svg
                                    className="w-6 h-6"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth="1.5"
                                    stroke="currentColor"
                                    aria-hidden="true"
                                    data-slot="icon"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        d="M6 18 18 6M6 6l12 12"
                                    ></path>
                                </svg>
                            </button>
                        </div>
                        <div className="space-y-6 mt-10 mx-2">
                            <div className="space-y-2">
                                <div className="flex justify-between items-center">
                                    <span>Email</span>
                                </div>
                                <div>
                                    <input
                                        className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                        placeholder="Email"
                                        type="text"
                                        value={userInfo.email}
                                        onChange={(event) => handleChangeEmail(event)}
                                    />
                                </div>
                            </div>
                            <div className="space-y-2">
                                <div className="flex justify-between items-center">
                                    <span>Mật khẩu</span>
                                </div>
                                <div>
                                    <input
                                        className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                        placeholder="Password"
                                        type="password"
                                        value={userInfo.password}
                                        onChange={(event) => handleChangePassword(event)}

                                    />
                                </div>
                                <div className="flex justify-end">
                                    <button className="text-sm text-blue-500 italic">Quên mật khẩu</button>
                                </div>
                            </div>
                            <div className="space-y-3">
                                <div className="flex justify-center">
                                    <button
                                        className="bg-gray-500 text-white rounded-xl w-1/2 text-xl py-2 disabled:opacity-25"
                                        onClick={onLogin}

                                    >
                                        Đăng nhập
                                    </button>
                                </div>
                                <div className="text-center text-muted">
                                    Chưa có tài khoản?{" "}
                                    <button className="text-primary text-blue-500 italic">Đăng ký ngay!</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginPanel;