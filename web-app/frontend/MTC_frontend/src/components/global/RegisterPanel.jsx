import React from "react";
import { logIn } from '../../services/authenticationService.js';
import api from '../../middlewares/axios.js';
import { API } from '../../configurations/configuration.js';
import useUserStore from '../../stores/userStores.js';
import { redirect, useNavigate } from 'react-router';
import { ScrollableWrapper } from '../wrapper/commonWrapper.jsx';
import { showSuccess } from '../../utils/ToastUtils.js';
import { ERROR_CODE_TYPE_MAPPER } from '../../common/ErrorCodeMapper.js';
const RegisterPanel = ({ onClose }) => {
    const navigate = useNavigate();
    const setUser = useUserStore((state) => state.setUser);
    const getUser = useUserStore((state) => state.user);
    const [userInfo, setUserInfo] = React.useState({
        email: "",
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        userProfileCreationRequest: {
            username: "",
            // "avatarPath": "/images/avatars/john.png",
            email: "emsa@gmail.com",
            status: "ACTIVE",
            gender: "MALE",
            firstName: "asd",
            lastName: "sdsd",
            dateOfBirth: "1995-08-15T00:00:00Z"
        }
    });

    const [errors, setRegisterFormError] = React.useState({
        email: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
    });

    // 🔁 Gộp lại thành 1 hàm thay đổi field
    const handleChangeUserInfo = (name, value) => {
        setUserInfo((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    // ✅ Hàm set lỗi cho từng field
    const setErrorField = (field, message) => {
        setRegisterFormError((prev) => ({
            ...prev,
            [field]: message,
        }));
    };

    // ✅ Xóa tất cả lỗi
    const clearError = () => {
        setRegisterFormError({
            email: "",
            password: "",
            confirmPassword: "",
            firstName: "",
            lastName: "",
        });
    };

    // ✅ Validate và xử lý đăng ký
    const onRegister = async (event) => {
        event.preventDefault();
        clearError();

        let hasError = false;

        if (!userInfo.email.includes('@')) {
            setErrorField("email", "Email không hợp lệ");
            hasError = true;
        }

        if (userInfo.password.length < 6) {
            setErrorField("password", "Mật khẩu phải có ít nhất 6 ký tự");
            hasError = true;
        }

        if (userInfo.password !== userInfo.confirmPassword) {
            setErrorField("confirmPassword", "Mật khẩu không khớp");
            hasError = true;
        }

        if (hasError) return;

        try {
            userInfo.userProfileCreationRequest.username = userInfo.email;
            userInfo.userProfileCreationRequest.email = userInfo.email;
            userInfo.username = userInfo.email;
            await sendRegisterRequest();
        } catch (error) {
            // Handle lỗi server
        }
    };

    const onGoogleRegister  = async (event) => {
        console.log("Sign up with Google clicked");
    };

    const sendRegisterRequest = async () => {
        try {
            const response = await api.post(API.REGISTER, userInfo);
            if (response.status === 200) {
                const user = response.data;
                showSuccess("Đăng ký thành công");
                setUser(user);
                navigate("/");
                onClose();
            } else {
                console.error("Đăng ký không thành công");
            }
        } catch (error) {
            const errorCode = error.response?.data?.code;
            var type = ERROR_CODE_TYPE_MAPPER[errorCode];
            var message = error.response?.data?.message;
            setErrorField(type, message);
            console.error("Lỗi khi gửi yêu cầu đăng ký:", error);
        }
    }



    return (
        <div className="relative z-10" role="dialog" aria-modal="true">
            <div className="fixed inset-0 bg-gray-50/20 transition-opacity"></div>
            <div className="fixed inset-0 z-10 overflow-y-auto">
                <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0 min-w-[200px]">
                    <ScrollableWrapper>
                        <div className="bg-panel min-w-lg p-6 max-w-xs sm:max-w-sm md:max-w-md w-full rounded-tl-xl rounded-bl-xl  bg-background">
                            <div className="flex justify-between items-center">
                                <img
                                    className="h-8 w-auto"
                                    src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png"
                                    alt=""
                                />
                                <h3 className="font-bold text-xl">Đăng ký</h3>
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
                            <div className="space-y-4 mt-6 mx-2">
                                <div className="space-y-2">
                                    <div className="flex justify-between items-center">
                                        <span>Email</span>
                                    </div>
                                    <div className={"flex flex-col justify-left items-start"}>
                                        <input
                                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                            type="text"
                                            value={userInfo.email}
                                            onChange={(event) => handleChangeUserInfo("email", event.target.value)}
                                        />
                                        {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email}</p>}
                                    </div>
                                </div>
                                {/*Password*/}
                                <div className="space-y-2">
                                    <div className="flex justify-between items-center">
                                        <span>Mật khẩu</span>
                                    </div>
                                    <div className={'flex flex-col justify-left items-start'}>
                                        <input
                                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                            type="password"
                                            value={userInfo.password}
                                            onChange={(event) => handleChangeUserInfo('password', event.target.value)}

                                        />
                                        {errors.password &&
                                            <p className="text-red-500 text-sm mt-1">{errors.password}</p>}

                                    </div>
                                </div>
                                {/*Confirm password*/}
                                <div className="space-y-2 mt-5">
                                    <div className="flex justify-between items-center">
                                        <span>Nhập lại mật khẩu</span>
                                    </div>
                                    <div className={'flex flex-col justify-left items-start'}>
                                        <input
                                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                            type="password"
                                            value={userInfo.confirmPassword}
                                            onChange={(event) => handleChangeUserInfo('confirmPassword', event.target.value)}

                                        />
                                        {errors.confirmPassword && (
                                            <p className="text-red-500 text-sm mt-1">{errors.confirmPassword}</p>
                                        )}

                                    </div>
                                </div>
                                {/*Ten*/}
                                <div className="space-y-2 mt-5">
                                    <div className="flex justify-between items-center">
                                        <span>Họ</span>
                                    </div>
                                    <div className={'flex flex-col justify-left items-start'}>
                                        <input
                                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                            value={userInfo.firstName}
                                            onChange={(event) => handleChangeUserInfo('firstName', event.target.value)}

                                        />
                                        {errors.firstName && (
                                            <p className="text-red-500 text-sm mt-1">{errors.firstName}</p>
                                        )}

                                    </div>
                                </div>
                                {/*Ho*/}
                                <div className="space-y-2 mt-5">
                                    <div className="flex justify-between items-center">
                                        <span>Tên</span>
                                    </div>
                                    <div className={'flex flex-col justify-left items-start'}>
                                        <input
                                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                            value={userInfo.lastName}
                                            onChange={(event) => handleChangeUserInfo('lastName', event.target.value)}

                                        />
                                        {errors.lastName && (
                                            <p className="text-red-500 text-sm mt-1">{errors.lastName}</p>
                                        )}

                                    </div>
                                </div>
                            </div>

                            <div className="space-y-3 mt-5">
                                <div className="flex justify-center">
                                    <button
                                        className="bg-gray-500 text-white rounded-xl w-1/2 text-xl py-2 disabled:opacity-25"
                                        onClick={onRegister}

                                    >
                                        Đăng ký
                                    </button>
                                </div>
                                <div className="flex justify-center">
                                    <button
                                        className="bg-white text-black border border-gray-300 rounded-xl w-1/2 text-xl py-2 flex items-center justify-center gap-2 hover:bg-gray-100"
                                        onClick={onGoogleRegister}
                                    >
                                        <svg className="w-5 h-5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                            <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
                                            <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-1.02.68-2.31 1.08-3.71 1.08-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
                                            <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z" fill="#FBBC05" />
                                            <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
                                        </svg>
                                        Đăng ký với Google
                                    </button>
                                </div>
                                <div className="text-center text-muted">
                                    Đã có tài khoản?{' '}
                                    <button className="text-primary">Đăng nhập ngay</button>
                                </div>
                            </div>
                        </div>

                    </ScrollableWrapper>
                </div>
            </div>
        </div>
    );
};

export default RegisterPanel;