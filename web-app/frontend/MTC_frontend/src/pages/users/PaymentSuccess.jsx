import React, { useState, useEffect } from 'react';
import { CheckCircle, ArrowLeft, CreditCard, User, Hash, Coins, Globe } from 'lucide-react';
import { vnpayCallback } from '../../services/paymentService.js';
import { getUserIdFromContext } from '../../services/authenticationService.js';
import { currencyIdForDonateRelate } from '../../constants/const.js';

const PaymentSuccess = () => {
    const [paymentData, setPaymentData] = useState({
        orderId: "",
        requestId: "",
        transactionId: "",
        userId: "",
        currencyId: "",
        status: "",
        expiresAt: "",
        provider: "momo" // "momo" or "vnpay"
    });
    let userId = getUserIdFromContext();
    console.log("User ID from context:", userId);
    let currencyId = currencyIdForDonateRelate;
    const [isVisible, setIsVisible] = useState(false);

    // Mock navigate function since useNavigate is not available
    const navigate = (path) => {
        console.log(`Navigating to: ${path}`);
    };

    // Parse VNPay URL parameters
    const parseVNPayParams = (search) => {
        const params = new URLSearchParams(search);
        return {
            orderId: params.get("vnp_TxnRef") || "",
            requestId: params.get("vnp_TxnRef") || "",
            transactionId: params.get("vnp_TransactionNo") || "",
            amount: params.get("vnp_Amount") || "",
            responseCode: params.get("vnp_ResponseCode") || "",
            transactionStatus: params.get("vnp_TransactionStatus") || "",
            orderInfo: params.get("vnp_OrderInfo") || "",
            bankCode: params.get("vnp_BankCode") || "",
            payDate: params.get("vnp_PayDate") || ""
        };
    };

    // Parse MoMo URL parameters
    const parseMoMoParams = (search) => {
        const query = new URLSearchParams(search);
        const orderId = query.get("orderId");
        const requestId = query.get("requestId");
        const extraDataEncoded = query.get("extraData");



        if (extraDataEncoded) {
            try {
                const decoded = atob(extraDataEncoded);
                const parsed = JSON.parse(decoded);
                userId = parsed.userId || "USR-456789";
                currencyId = parsed.currencyId || currencyIdForDonateRelate;
            } catch (err) {
                console.error("Lỗi giải mã extraData:", err);
            }
        }

        return {
            orderId: orderId || "ORD-123456789",
            requestId: requestId || "REQ-987654321",
            transactionId: query.get("transId") || "",
            userId,
            currencyId,
            status: query.get("resultCode") === "0" ? "SUCCESS" : "FAILED"
        };
    };

    // Detect payment provider and parse parameters
    const detectProviderAndParse = (search) => {
        const params = new URLSearchParams(search);

        // Check for VNPay parameters
        if (params.has("vnp_TxnRef") || params.has("vnp_ResponseCode")) {
            const vnpayData = parseVNPayParams(search);
            vnpayCallback(search).then(response => {
                console.log("VNPay Callback Response:", response);
            })
            const isSuccess = vnpayData.responseCode === "00";

            return {
                provider: "vnpay",
                orderId: vnpayData.orderId,
                requestId: vnpayData.requestId,
                transactionId: vnpayData.transactionId,
                userId: userId,
                currencyId: currencyId,
                status: isSuccess ? "SUCCESS" : "FAILED",
                expiresAt: "",
                extraInfo: {
                    amount: vnpayData.amount,
                    bankCode: vnpayData.bankCode,
                    orderInfo: vnpayData.orderInfo,
                    payDate: vnpayData.payDate
                }
            };
        }

        // Default to MoMo parsing
        const momoData = parseMoMoParams(search);
        return {
            provider: "momo",
            ...momoData,
            expiresAt: "",
            extraInfo: {}
        };
    };

    useEffect(() => {
        const parsedData = detectProviderAndParse(window.location.search);
        setPaymentData(parsedData);

        // Animation trigger
        setTimeout(() => setIsVisible(true), 100);
    }, []);

    // Format payment provider name
    const getProviderDisplayName = (provider) => {
        return provider === "vnpay" ? "VNPay" : "MoMo";
    };

    // Format amount for VNPay (VNPay sends amount in smallest currency unit)
    const formatAmount = (amount) => {
        if (!amount) return "";
        const numAmount = parseInt(amount);
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(numAmount / 100);
    };

    // Get status color and text
    const getStatusDisplay = (status) => {
        if (status === "SUCCESS") {
            return {
                color: "text-green-600",
                bgColor: "bg-green-100",
                text: "Thành công"
            };
        } else if (status === "FAILED") {
            return {
                color: "text-red-600",
                bgColor: "bg-red-100",
                text: "Thất bại"
            };
        } else {
            return {
                color: "text-yellow-600",
                bgColor: "bg-yellow-100",
                text: "Đang xử lý"
            };
        }
    };

    const statusDisplay = getStatusDisplay(paymentData.status);
    const isSuccess = paymentData.status === "SUCCESS";

    return (
        <div className={`min-h-screen bg-gradient-to-br ${
            isSuccess
                ? 'from-green-50 via-emerald-50 to-teal-50'
                : 'from-red-50 via-rose-50 to-pink-50'
        } flex items-center justify-center p-4`}>
            <div className={`max-w-md w-full transform transition-all duration-1000 ${
                isVisible ? 'translate-y-0 opacity-100' : 'translate-y-8 opacity-0'
            }`}>

                {/* Success/Failure Card */}
                <div className="bg-white rounded-3xl shadow-2xl overflow-hidden border border-gray-100">

                    {/* Header Section */}
                    <div className={`bg-gradient-to-r ${
                        isSuccess
                            ? 'from-green-500 to-emerald-600'
                            : 'from-red-500 to-rose-600'
                    } px-8 py-12 text-center relative overflow-hidden`}>
                        <div className="absolute inset-0 bg-black bg-opacity-10"></div>
                        <div className="relative z-10">
                            <div className="inline-flex items-center justify-center w-20 h-20 bg-white bg-opacity-20 rounded-full mb-4 animate-pulse">
                                <CheckCircle className="w-12 h-12 text-white" />
                            </div>
                            <h1 className="text-3xl font-bold text-white mb-2">
                                {isSuccess ? "Thành công!" : "Thất bại!"}
                            </h1>
                            <p className={`${
                                isSuccess ? 'text-green-100' : 'text-red-100'
                            } text-lg`}>
                                {isSuccess
                                    ? "Thanh toán của bạn đã được xử lý"
                                    : "Thanh toán không thành công"
                                }
                            </p>

                            {/* Provider Badge */}
                            <div className="inline-flex items-center bg-white bg-opacity-20 rounded-full px-3 py-1 mt-3">
                                <Globe className="w-4 h-4 text-white mr-2" />
                                <span className="text-white text-sm font-medium">
                                    {getProviderDisplayName(paymentData.provider)}
                                </span>
                            </div>
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

                            {/* Status */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className={`w-10 h-10 ${statusDisplay.bgColor} rounded-lg flex items-center justify-center`}>
                                        <CheckCircle className={`w-5 h-5 ${statusDisplay.color}`} />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Trạng thái</p>
                                    <p className={`text-lg font-semibold ${statusDisplay.color}`}>
                                        {statusDisplay.text}
                                    </p>
                                </div>
                            </div>

                            {/* Order ID */}
                            {/*<div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">*/}
                            {/*    <div className="flex-shrink-0">*/}
                            {/*        <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">*/}
                            {/*            <Hash className="w-5 h-5 text-blue-600" />*/}
                            {/*        </div>*/}
                            {/*    </div>*/}
                            {/*    <div className="flex-1 min-w-0">*/}
                            {/*        <p className="text-sm font-medium text-gray-600">Mã đơn hàng</p>*/}
                            {/*        <p className="text-lg font-semibold text-gray-900 truncate">{paymentData.orderId}</p>*/}
                            {/*    </div>*/}
                            {/*</div>*/}

                            {/* Transaction ID (for VNPay) */}
                            {paymentData.transactionId && (
                                <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                    <div className="flex-shrink-0">
                                        <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                                            <CreditCard className="w-5 h-5 text-purple-600" />
                                        </div>
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <p className="text-sm font-medium text-gray-600">Mã giao dịch</p>
                                        <p className="text-lg font-semibold text-gray-900 truncate">{paymentData.transactionId}</p>
                                    </div>
                                </div>
                            )}

                            {/* Request ID */}
                            <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                <div className="flex-shrink-0">
                                    <div className="w-10 h-10 bg-indigo-100 rounded-lg flex items-center justify-center">
                                        <Hash className="w-5 h-5 text-indigo-600" />
                                    </div>
                                </div>
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-600">Mã yêu cầu</p>
                                    <p className="text-lg font-semibold text-gray-900 truncate">{paymentData.referenceId}</p>
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
                                    <p className="text-lg font-semibold text-gray-900 truncate">{paymentData.userId}</p>
                                </div>
                            </div>

                            {/* Currency ID */}
                            {/*<div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">*/}
                            {/*    <div className="flex-shrink-0">*/}
                            {/*        <div className="w-10 h-10 bg-yellow-100 rounded-lg flex items-center justify-center">*/}
                            {/*            <Coins className="w-5 h-5 text-yellow-600" />*/}
                            {/*        </div>*/}
                            {/*    </div>*/}
                            {/*    <div className="flex-1 min-w-0">*/}
                            {/*        <p className="text-sm font-medium text-gray-600">Loại tiền tệ</p>*/}
                            {/*        <p className="text-lg font-semibold text-gray-900 truncate">{paymentData.currencyId}</p>*/}
                            {/*    </div>*/}
                            {/*</div>*/}

                            {/* Amount (for VNPay) */}
                            {paymentData.provider === "vnpay" && paymentData.extraInfo?.amount && (
                                <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                    <div className="flex-shrink-0">
                                        <div className="w-10 h-10 bg-emerald-100 rounded-lg flex items-center justify-center">
                                            <Coins className="w-5 h-5 text-emerald-600" />
                                        </div>
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <p className="text-sm font-medium text-gray-600">Số tiền</p>
                                        <p className="text-lg font-semibold text-gray-900">
                                            {formatAmount(paymentData.extraInfo.amount)}
                                        </p>
                                    </div>
                                </div>
                            )}

                            {/* Bank Code (for VNPay) */}
                            {paymentData.provider === "vnpay" && paymentData.extraInfo?.bankCode && (
                                <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
                                    <div className="flex-shrink-0">
                                        <div className="w-10 h-10 bg-cyan-100 rounded-lg flex items-center justify-center">
                                            <CreditCard className="w-5 h-5 text-cyan-600" />
                                        </div>
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <p className="text-sm font-medium text-gray-600">Ngân hàng</p>
                                        <p className="text-lg font-semibold text-gray-900">{paymentData.extraInfo.bankCode}</p>
                                    </div>
                                </div>
                            )}
                        </div>

                        {/* Success/Failure Message */}
                        <div className={`${
                            isSuccess
                                ? 'bg-green-50 border-green-200'
                                : 'bg-red-50 border-red-200'
                        } border rounded-xl p-4`}>
                            <p className={`${
                                isSuccess ? 'text-green-800' : 'text-red-800'
                            } text-center`}>
                                {isSuccess
                                    ? "✨ Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi!"
                                    : "❌ Vui lòng thử lại hoặc liên hệ bộ phận hỗ trợ"
                                }
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


// const PaymentSuccess = () => {
//     const [orderId, setOrderId] = useState("");
//     const [requestId, setRequestId] = useState("");
//     const [userId, setUserId] = useState("");
//     const [currencyId, setCurrencyId] = useState("");
//     const [isVisible, setIsVisible] = useState(false);
//
//     // Mock navigate function since useNavigate is not available
//     const navigate = (path) => {
//         console.log(`Navigating to: ${path}`);
//     };
//
//     useEffect(() => {
//         const query = new URLSearchParams(window.location.search);
//         const orderId = query.get("orderId");
//         const requestId = query.get("requestId");
//         const extraDataEncoded = query.get("extraData");
//
//         setOrderId(orderId || "ORD-123456789");
//         setRequestId(requestId || "REQ-987654321");
//
//         if (extraDataEncoded) {
//             try {
//                 const decoded = atob(extraDataEncoded);
//                 const parsed = JSON.parse(decoded);
//                 setUserId(parsed.userId || "USR-456789");
//                 setCurrencyId(parsed.currencyId || "VND");
//             } catch (err) {
//                 console.error("Lỗi giải mã extraData:", err);
//                 setUserId("USR-456789");
//                 setCurrencyId("VND");
//             }
//         } else {
//             setUserId("USR-456789");
//             setCurrencyId("VND");
//         }
//
//         // Animation trigger
//         setTimeout(() => setIsVisible(true), 100);
//     }, []);
//
//     return (
//         <div className="min-h-screen bg-gradient-to-br from-green-50 via-emerald-50 to-teal-50 flex items-center justify-center p-4">
//             <div className={`max-w-md w-full transform transition-all duration-1000 ${
//                 isVisible ? 'translate-y-0 opacity-100' : 'translate-y-8 opacity-0'
//             }`}>
//
//                 {/* Success Card */}
//                 <div className="bg-white rounded-3xl shadow-2xl overflow-hidden border border-green-100">
//
//                     {/* Header Section */}
//                     <div className="bg-gradient-to-r from-green-500 to-emerald-600 px-8 py-12 text-center relative overflow-hidden">
//                         <div className="absolute inset-0 bg-black bg-opacity-10"></div>
//                         <div className="relative z-10">
//                             <div className="inline-flex items-center justify-center w-20 h-20 bg-white bg-opacity-20 rounded-full mb-4 animate-pulse">
//                                 <CheckCircle className="w-12 h-12 text-white" />
//                             </div>
//                             <h1 className="text-3xl font-bold text-white mb-2">Thành công!</h1>
//                             <p className="text-green-100 text-lg">Thanh toán của bạn đã được xử lý</p>
//                         </div>
//
//                         {/* Decorative elements */}
//                         <div className="absolute top-4 right-4 w-16 h-16 bg-white bg-opacity-10 rounded-full"></div>
//                         <div className="absolute bottom-4 left-4 w-12 h-12 bg-white bg-opacity-10 rounded-full"></div>
//                     </div>
//
//                     {/* Content Section */}
//                     <div className="px-8 py-8 space-y-6">
//
//                         {/* Transaction Details */}
//                         <div className="space-y-4">
//                             <h2 className="text-xl font-semibold text-gray-800 mb-4 border-b border-gray-100 pb-2">
//                                 Chi tiết giao dịch
//                             </h2>
//
//                             {/* Order ID */}
//                             <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
//                                 <div className="flex-shrink-0">
//                                     <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
//                                         <Hash className="w-5 h-5 text-blue-600" />
//                                     </div>
//                                 </div>
//                                 <div className="flex-1 min-w-0">
//                                     <p className="text-sm font-medium text-gray-600">Mã đơn hàng</p>
//                                     <p className="text-lg font-semibold text-gray-900 truncate">{orderId}</p>
//                                 </div>
//                             </div>
//
//                             {/* Request ID */}
//                             <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
//                                 <div className="flex-shrink-0">
//                                     <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
//                                         <CreditCard className="w-5 h-5 text-purple-600" />
//                                     </div>
//                                 </div>
//                                 <div className="flex-1 min-w-0">
//                                     <p className="text-sm font-medium text-gray-600">Mã giao dịch</p>
//                                     <p className="text-lg font-semibold text-gray-900 truncate">{requestId}</p>
//                                 </div>
//                             </div>
//
//                             {/* User ID */}
//                             <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
//                                 <div className="flex-shrink-0">
//                                     <div className="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
//                                         <User className="w-5 h-5 text-green-600" />
//                                     </div>
//                                 </div>
//                                 <div className="flex-1 min-w-0">
//                                     <p className="text-sm font-medium text-gray-600">Mã người dùng</p>
//                                     <p className="text-lg font-semibold text-gray-900 truncate">{userId}</p>
//                                 </div>
//                             </div>
//
//                             {/* Currency ID */}
//                             <div className="flex items-center space-x-4 p-4 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors">
//                                 <div className="flex-shrink-0">
//                                     <div className="w-10 h-10 bg-yellow-100 rounded-lg flex items-center justify-center">
//                                         <Coins className="w-5 h-5 text-yellow-600" />
//                                     </div>
//                                 </div>
//                                 <div className="flex-1 min-w-0">
//                                     <p className="text-sm font-medium text-gray-600">Loại tiền tệ</p>
//                                     <p className="text-lg font-semibold text-gray-900 truncate">{currencyId}</p>
//                                 </div>
//                             </div>
//                         </div>
//
//                         {/* Success Message */}
//                         <div className="bg-green-50 border border-green-200 rounded-xl p-4">
//                             <p className="text-green-800 text-center">
//                                 ✨ Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi!
//                             </p>
//                         </div>
//
//                         {/* Action Button */}
//                         <button
//                             onClick={() => navigate("/")}
//                             className="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-semibold py-4 px-6 rounded-xl transition-all duration-300 transform hover:scale-105 hover:shadow-lg flex items-center justify-center space-x-2"
//                         >
//                             <ArrowLeft className="w-5 h-5" />
//                             <span>Trở về trang chủ</span>
//                         </button>
//                     </div>
//                 </div>
//
//                 {/* Footer Note */}
//                 <div className="text-center mt-6">
//                     <p className="text-gray-500 text-sm">
//                         Nếu có thắc mắc, vui lòng liên hệ bộ phận hỗ trợ
//                     </p>
//                 </div>
//             </div>
//         </div>
//     );
// };
//
// export default PaymentSuccess;

