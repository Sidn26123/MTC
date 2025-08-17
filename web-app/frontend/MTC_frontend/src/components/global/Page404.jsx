import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Page404 = () => {
    const navigate = useNavigate();

    return (
        <section className="bg-gray-100 h-screen flex items-center justify-center">
            <div className="text-center px-8 py-10 border border-gray-300 rounded-xl shadow-sm bg-white">
                <h1 className="mb-6 text-8xl font-extrabold text-gray-700">404</h1>
                <p className="mb-4 text-2xl font-semibold text-gray-800">
                    Trang không tồn tại
                </p>
                <p className="mb-8 text-gray-500">
                    Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu.
                </p>
                <div className="flex flex-col sm:flex-row gap-4 justify-center">
                    <Link
                        to="/"
                        className="px-6 py-2 rounded-lg bg-gray-800 text-white hover:bg-gray-700 transition border border-gray-700"
                    >
                        ⬅️ Về trang chủ
                    </Link>
                    <button
                        onClick={() => navigate(-1)}
                        className="px-6 py-2 rounded-lg border border-gray-400 text-gray-700 hover:bg-gray-200 transition"
                    >
                        🔙 Quay lại
                    </button>
                </div>
            </div>
        </section>
    );
};

export default Page404;
