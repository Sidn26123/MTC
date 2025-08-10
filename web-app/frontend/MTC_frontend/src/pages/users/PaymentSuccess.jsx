import React, { useState, useEffect } from 'react';
import { CheckCircle, ArrowLeft, CreditCard, User, Hash, Coins } from 'lucide-react';


// import React, { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
//
// const PaymentSuccess = () => {
//   const [orderId, setOrderId] = useState("");
//   const [requestId, setRequestId] = useState("");
//   const [userId, setUserId] = useState("");
//   const [currencyId, setCurrencyId] = useState("");
//   const navigate = useNavigate();
//
//   useEffect(() => {
//     const query = new URLSearchParams(window.location.search);
//     const orderId = query.get("orderId");
//     const requestId = query.get("requestId");
//     const extraDataEncoded = query.get("extraData");
//
//     setOrderId(orderId || "");
//     setRequestId(requestId || "");
//
//     if (extraDataEncoded) {
//       try {
//         const decoded = atob(extraDataEncoded);
//         const parsed = JSON.parse(decoded);
//         setUserId(parsed.userId || "");
//         setCurrencyId(parsed.currencyId || "");
//       } catch (err) {
//         console.error("Lỗi giải mã extraData:", err);
//       }
//     }
//   }, []);
//
//   return (
//     <div style={{ padding: "2rem", fontFamily: "Arial" }}>
//       <h2 style={{ color: "green" }}>🎉 Thanh toán thành công!</h2>
//       <p><strong>Mã đơn hàng:</strong> {orderId}</p>
//       <p><strong>Mã giao dịch:</strong> {requestId}</p>
//       <p><strong>User ID:</strong> {userId}</p>
//       <p><strong>Currency ID:</strong> {currencyId}</p>
//
//       <button
//         onClick={() => navigate("/")}
//         style={{
//           marginTop: "1.5rem",
//           padding: "10px 20px",
//           backgroundColor: "#007bff",
//           color: "white",
//           border: "none",
//           borderRadius: "5px",
//           cursor: "pointer"
//         }}
//       >
//         🔙 Trở về trang chủ
//       </button>
//     </div>
//   );
// };
//
// export default PaymentSuccess;


const PaymentSuccess = () => {
    const [orderId, setOrderId] = useState("");
    const [requestId, setRequestId] = useState("");
    const [userId, setUserId] = useState("");
    const [currencyId, setCurrencyId] = useState("");
    const [isVisible, setIsVisible] = useState(false);

    // Mock navigate function since useNavigate is not available
    const navigate = (path) => {
        console.log(`Navigating to: ${path}`);
    };

    useEffect(() => {
        const query = new URLSearchParams(window.location.search);
        const orderId = query.get("orderId");
        const requestId = query.get("requestId");
        const extraDataEncoded = query.get("extraData");

        setOrderId(orderId || "ORD-123456789");
        setRequestId(requestId || "REQ-987654321");

        if (extraDataEncoded) {
            try {
                const decoded = atob(extraDataEncoded);
                const parsed = JSON.parse(decoded);
                setUserId(parsed.userId || "USR-456789");
                setCurrencyId(parsed.currencyId || "VND");
            } catch (err) {
                console.error("Lỗi giải mã extraData:", err);
                setUserId("USR-456789");
                setCurrencyId("VND");
            }
        } else {
            setUserId("USR-456789");
            setCurrencyId("VND");
        }

        // Animation trigger
        setTimeout(() => setIsVisible(true), 100);
    }, []);

    return (
        <div className="min-h-screen bg-gradient-to-br from-green-50 via-emerald-50 to-teal-50 flex items-center justify-center p-4">
            <div className={`max-w-md w-full transform transition-all duration-1000 ${
                isVisible ? 'translate-y-0 opacity-100' : 'translate-y-8 opacity-0'
            }`}>

                {/* Success Card */}
                <div className="bg-white rounded-3xl shadow-2xl overflow-hidden border border-green-100">

                    {/* Header Section */}
                    <div className="bg-gradient-to-r from-green-500 to-emerald-600 px-8 py-12 text-center relative overflow-hidden">
                        <div className="absolute inset-0 bg-black bg-opacity-10"></div>
                        <div className="relative z-10">
                            <div className="inline-flex items-center justify-center w-20 h-20 bg-white bg-opacity-20 rounded-full mb-4 animate-pulse">
                                <CheckCircle className="w-12 h-12 text-white" />
                            </div>
                            <h1 className="text-3xl font-bold text-white mb-2">Thành công!</h1>
                            <p className="text-green-100 text-lg">Thanh toán của bạn đã được xử lý</p>
                        </div>

                        {/* Decorative elements */}
                        <div className="absolute top-4 right-4 w-16 h-16 bg-white bg-opacity-10 rounded-full"></div>
                        <div className="absolute bottom-4 left-4 w-12 h-12 bg-white bg-opacity-10 rounded-full"></div>
                    </div>

                    {/* Content Section */}
                    <div className="px-8 py-8 space-y-6">

                        {/* Transaction Details */}
                        <div className="space-y-4">
                            <h2 className="text-xl font-semibold text-gray-800 mb-4 border-b border-gray-100 pb-2">
                                Chi tiết giao dịch
                            </h2>

                            {/* Order ID */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                                        <Hash className="w-5 h-5 text-blue-600" />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Mã đơn hàng</p>
                                    <p className="text-lg font-semibold text-gray-900 truncate">{orderId}</p>
                                </div>
                            </div>

                            {/* Request ID */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                                        <CreditCard className="w-5 h-5 text-purple-600" />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Mã giao dịch</p>
                                    <p className="text-lg font-semibold text-gray-900 truncate">{requestId}</p>
                                </div>
                            </div>

                            {/* User ID */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
                                        <User className="w-5 h-5 text-green-600" />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Mã người dùng</p>
                                    <p className="text-lg font-semibold text-gray-900 truncate">{userId}</p>
                                </div>
                            </div>

                            {/* Currency ID */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className="w-10 h-10 bg-yellow-100 rounded-lg flex items-center justify-center">
                                        <Coins className="w-5 h-5 text-yellow-600" />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Loại tiền tệ</p>
                                    <p className="text-lg font-semibold text-gray-900 truncate">{currencyId}</p>
                                </div>
                            </div>
                        </div>

                        {/* Success Message */}
                        <div className="bg-green-50 border border-green-200 rounded-xl p-4">
                            <p className="text-green-800 text-center">
                                ✨ Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi!
                            </p>
                        </div>

                        {/* Action Button */}
                        <button
                            onClick={() => navigate("/")}
                            className="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-semibold py-4 px-6 rounded-xl transition-all duration-300 transform hover:scale-105 hover:shadow-lg flex items-center justify-center space-x-2"
                        >
                            <ArrowLeft className="w-5 h-5" />
                            <span>Trở về trang chủ</span>
                        </button>
                    </div>
                </div>

                {/* Footer Note */}
                <div className="text-center mt-6">
                    <p className="text-gray-500 text-sm">
                        Nếu có thắc mắc, vui lòng liên hệ bộ phận hỗ trợ
                    </p>
                </div>
            </div>
        </div>
    );
};

export default PaymentSuccess;