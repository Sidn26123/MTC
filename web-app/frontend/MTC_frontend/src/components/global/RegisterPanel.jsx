import React from "react";
import { logIn } from '../../services/authenticationService.js';
import api from '../../middlewares/axios.js';
import { API } from '../../configurations/configuration.js';
import useUserStore from '../../stores/userStores.js';
import { redirect, useNavigate } from 'react-router';
import { ScrollableWrapper } from '../wrapper/commonWrapper.jsx';
import {showSuccess} from '../../utils/ToastUtils.js';
import {ERROR_CODE_TYPE_MAPPER} from '../../common/ErrorCodeMapper.js';
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
            email:"emsa@gmail.com",
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
                                    <div className="text-center text-muted">
                                        Chưa có tài khoản?{' '}
                                        <button className="text-primary">Đăng ký ngay</button>
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