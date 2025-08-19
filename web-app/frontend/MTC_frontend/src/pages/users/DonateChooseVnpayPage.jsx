import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import { useUser } from '../../stores/userStores.js';
import { currencyIdForDonateRelate } from '../../constants/const.js';
import { paymentDeposit, paymentVnpay } from '../../services/paymentService.js';
import { CoinIcon } from '../../components/payments/Currency.jsx';
import { getUserIdFromContext } from '../../services/authenticationService.js';

const numberFormat = (num) =>
    new Intl.NumberFormat("vi-VN", { style: "decimal" }).format(num);

const PotatoIcon = () => (
        <div className="w-auto h-4 mx-1 inline-flex pr-10">
            <FontAwesomeIcon icon={faCoins} />
        </div>
    )
;

// export const DonateChooseVnpayPage = () => {
//     const [isLoggedIn, setIsLoggedIn] = useState(true); // giả định đăng nhập
//     const [userName, setUserName] = useState("Sidnn"); // mock tên user
//     const [isLoading, setIsLoading] = useState(false);
//     const [isSubmitting, setIsSubmitting] = useState(false);
//     const [order, setOrder] = useState(null);
//     const user = useUser();
//     const handleStore = (key) => {
//         setIsSubmitting(true);
//         // Giả lập xử lý order
//         setTimeout(() => {
//             setOrder({
//                 method: "momo",
//                 amount: key,
//                 code: "ORD123456",
//                 amount_usd: (key / 20000).toFixed(2),
//                 checkout_url: "https://paypal.com/checkout?order=ORD123456",
//             });
//             setIsSubmitting(false);
//         }, 1000);
//     };
//     const [selectedAmount, setSelectedAmount] = useState(null);
//     const [loading, setLoading] = useState(false);
//
//     const handlePaymentVnpay = async () => {
//         const amount = Number(selectedAmount || Object.keys(MOCK_PACKAGES)[0]);
//         // setSelectedAmount(amount);
//         setLoading(true);
//         try {
//             const data = {
//                 "customer": userId,
//                 "amount": amount,
//                 "userId": userId,
//                 "currencyId": currencyIdForDonateRelate
//             }
//             const response = await paymentDeposit(data);
//             // Nếu gọi thành công, redirect đến MoMo
//             if (response?.payUrl) {
//                 window.open(response.payUrl, '_blank');
//             } else {
//                 alert("Gọi API thành công nhưng không nhận được URL thanh toán.");
//             }
//         } catch (error) {
//             console.error("Lỗi khi gọi thanh toán:", error);
//             alert("Đã xảy ra lỗi khi gọi API thanh toán.");
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     function handleSubmitPayment(key) {
//         setSelectedAmount(key);
//         handlePaymentVnpay().then(r => {});
//     }
//
//     const renderPackages = () => (
//         <div className="space-y-4">
//             {Object.keys(MOCK_PACKAGES).map((key) => (
//                 <button
//                     key={key}
//                     onClick={() => handleSubmitPayment(key)}
//                     className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white"
//                 >
//                     <div className="flex justify-center space-x-2">
//                         {!isSubmitting ? (
//                             <>
//                                 <span className="font-medium">{numberFormat(key)}</span>
//                                 <PotatoIcon />
//                             </>
//                         ) : (
//                             <svg
//                                 className="inline animate-spin w-5 h-5"
//                                 xmlns="http://www.w3.org/2000/svg"
//                                 fill="none"
//                                 viewBox="0 0 24 24"
//                                 strokeWidth={1.5}
//                                 stroke="currentColor"
//                             >
//                                 <path
//                                     strokeLinecap="round"
//                                     strokeLinejoin="round"
//                                     d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313..."
//                                 />
//                             </svg>
//                         )}
//                     </div>
//                 </button>
//             ))}
//         </div>
//     );
//
//     return (
//         <main className="mt-6 px-4 lg:px-0">
//             {!isLoggedIn ? (
//                 <div className="flex justify-center">
//                     <button
//                         onClick={() => console.log("Open login modal")}
//                         className="text-primary font-semibold italic"
//                     >
//                         Vui lòng đăng nhập để xem
//                     </button>
//                 </div>
//             ) : (
//                 <div>
//                     {isLoading ? (
//                         <div className="flex justify-center">
//                             <svg
//                                 className="inline animate-spin w-8 h-8"
//                                 xmlns="http://www.w3.org/2000/svg"
//                                 fill="none"
//                                 viewBox="0 0 24 24"
//                                 strokeWidth={1.5}
//                                 stroke="currentColor"
//                             >
//                                 <path
//                                     strokeLinecap="round"
//                                     strokeLinejoin="round"
//                                     d="..."
//                                 />
//                             </svg>
//                         </div>
//                     ) : (
//                         <>
//                             {!order ? (
//                                 <>
//                                     <div>
//                                         <span>Xin chào </span>
//                                         <span className="font-semibold text-primary">{user.email}</span>
//                                         <span>, vui lòng chọn số lượng muốn mua</span>
//                                     </div>
//                                     {renderPackages()}
//                                 </>
//                             ) : order.method === "momo" ? (
//                                 <div>
//                                     <div className="text-center items-center mb-6">
//                                         Bạn đang mua{" "}
//                                         <span className="font-bold">{numberFormat(order.amount)}</span>
//                                         <CoinIcon />
//                                         (đơn hàng: <strong>{order.code}</strong>), số tiền cần thanh toán là{" "}
//                                         <strong>{order.amount}$</strong>. Ấn vào nút thanh toán bên dưới để
//                                         thanh toán qua Momo.
//                                     </div>
//                                     <div className="flex justify-center">
//                                         {order.checkout_url ? (
//                                             <div
//                                                 onClick={() => handlePaymentMomo()}
//                                                 className="bg-primary text-white px-5 py-2 rounded"
//                                             >
//                                                 Thanh toán {order.amount}$
//                                             </div>
//                                         ) : (
//                                             <div className="text-center italic text-red-500 font-bold">
//                                                 Có lỗi trong quá trình thanh toán, vui lòng thử lại hoặc Yêu cầu hỗ
//                                                 trợ
//                                             </div>
//                                         )}
//                                     </div>
//                                 </div>
//                             ) : null}
//                         </>
//                     )}
//                 </div>
//             )}
//         </main>
//     );
// };
//
// export default DonateChooseVnpayPage;

 // Thay thế với đường dẫn thực tế

// Mock packages - thay thế bằng dữ liệu thực tế của bạn
const MOCK_PACKAGES = {
    100000: "100,000 VND",
    200000: "200,000 VND",
    500000: "500,000 VND",
    1000000: "1,000,000 VND",
    2000000: "2,000,000 VND"
};

// Thay thế bằng currency ID thực tế
// const currencyIdForDonateRelate = "f846a93f-af91-4ad9-aaf6-926ecb88595f";

// API service function
const createVNPayPayment = async (data) => {
    const { amount, bankCode, userId, currencyId } = data;
    const res = await paymentVnpay(data);
    console.log(res)
    return res;
    //
    // if (!response.ok) {
    //     throw new Error(`HTTP error! status: ${response.status}`);
    // }
    //
    // return response.json();
};

export const DonateChooseVnpayPage = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const [isLoading, setIsLoading] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [selectedAmount, setSelectedAmount] = useState(null);
    const [order, setOrder] = useState(null);
    const [selectedBankCode, setSelectedBankCode] = useState('NCB');
    const [paymentStatus, setPaymentStatus] = useState(null);
    const [isOpen, setIsOpen] = useState(false);
    const [response, setResponse] = useState(null);
    const user = useUser();
    const userId = getUserIdFromContext();

    // Danh sách các ngân hàng VNPay hỗ trợ
    const bankCodes = [
        { code: 'NCB', name: 'NCB' },
        { code: 'BIDV', name: 'BIDV' },
        { code: 'VCB', name: 'Vietcombank' },
        { code: 'VTB', name: 'VietinBank' },
        { code: 'TCB', name: 'Techcombank' },
        { code: 'MB', name: 'MB Bank' },
        { code: 'ACB', name: 'ACB' },
        { code: 'SHB', name: 'SHB' },
        { code: 'VPB', name: 'VPBank' },
    ];

    // Kiểm tra payment callback khi component mount
    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const responseCode = urlParams.get('vnp_ResponseCode');
        const transactionStatus = urlParams.get('vnp_TransactionStatus');
        const txnRef = urlParams.get('vnp_TxnRef');
        const amount = urlParams.get('vnp_Amount');

        if (responseCode && transactionStatus && txnRef) {
            if (responseCode === '00' && transactionStatus === '00') {
                setPaymentStatus({
                    success: true,
                    message: `Thanh toán thành công! Số tiền: ${parseInt(amount) / 100} VND`,
                    transactionId: txnRef
                });
            } else {
                setPaymentStatus({
                    success: false,
                    message: 'Thanh toán không thành công. Vui lòng thử lại.',
                    transactionId: txnRef
                });
            }

            // Clear URL parameters sau khi xử lý
            window.history.replaceState({}, document.title, window.location.pathname);
        }
    }, []);

    const handleVNPayPayment = async (amount) => {
        if (!user?.id) {
            alert('Vui lòng đăng nhập để tiếp tục');
            return;
        }

        setIsSubmitting(true);
        setSelectedAmount(amount);

        try {
            const paymentData = {
                amount: amount,
                bankCode: selectedBankCode,
                userId: userId,
                currencyId: currencyIdForDonateRelate
            };
            setIsOpen(true);

            const response = await createVNPayPayment(paymentData);

            if (response.code === 0 && response.result?.paymentUrl) {
                setResponse(response);
                // Tạo order để tracking
                setOrder({
                    method: "vnpay",
                    amount: amount,
                    requestId: response.result.requestId,
                    transactionId: response.result.transactionId,
                    paymentUrl: response.result.paymentUrl,
                    status: response.result.status,
                    expiresAt: response.result.expiresAt
                });
                // Chuyển hướng đến trang thanh toán VNPay
                // window.location.href = response.result.paymentUrl;

            } else {
                throw new Error('Không nhận được URL thanh toán từ VNPay');
            }
        } catch (error) {
            console.error('Lỗi khi tạo thanh toán VNPay:', error);
            alert(`Đã xảy ra lỗi: ${error.message}`);
        } finally {
            setIsSubmitting(false);
        }
    };

    const openNewTab = () =>{
        console.log("Opening new tab with payment URL:", response);
        const newTab = window.open(response.result.paymentUrl, "_blank");
        if (newTab) {
            newTab.focus();
        }
    }

    const renderBankSelection = () => (
        <div className="mb-6">
            <label className="block text-sm font-medium mb-2">
                Chọn ngân hàng thanh toán:
            </label>
            <select
                value={selectedBankCode}
                onChange={(e) => setSelectedBankCode(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
                {bankCodes.map((bank) => (
                    <option key={bank.code} value={bank.code}>
                        {bank.name}
                    </option>
                ))}
            </select>
        </div>
    );

    const renderPackages = () => (
        <div className="space-y-4">
            {Object.keys(MOCK_PACKAGES).map((key) => (
                <button
                    key={key}
                    onClick={() => handleVNPayPayment(parseInt(key))}
                    disabled={isSubmitting}
                    className={`border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white transition-all ${
                        isSubmitting ? 'opacity-50 cursor-not-allowed' : ''
                    }`}
                >
                    <div className="flex justify-center items-center space-x-2">
                        {isSubmitting && selectedAmount === parseInt(key) ? (
                            <div className="flex items-center space-x-2">
                                <svg
                                    className="animate-spin w-5 h-5"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                >
                                    <circle
                                        className="opacity-25"
                                        cx="12"
                                        cy="12"
                                        r="10"
                                        stroke="currentColor"
                                        strokeWidth="4"
                                    />
                                    <path
                                        className="opacity-75"
                                        fill="currentColor"
                                        d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                                    />
                                </svg>
                                <span>Đang xử lý...</span>
                            </div>
                        ) : (
                            <>
                                <span className="font-medium">{numberFormat(key)} VND</span>
                                <PotatoIcon />
                            </>
                        )}
                    </div>
                </button>
            ))}
        </div>
    );

    const renderPaymentStatus = () => {
        if (!paymentStatus) return null;

        return (
            <div className={`p-4 rounded-lg mb-6 ${
                paymentStatus.success
                    ? 'bg-green-100 border border-green-400 text-green-700'
                    : 'bg-red-100 border border-red-400 text-red-700'
            }`}>
                <div className="flex items-center">
                    {paymentStatus.success ? (
                        <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                        </svg>
                    ) : (
                        <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                        </svg>
                    )}
                    <span className="font-medium">{paymentStatus.message}</span>
                </div>
                {paymentStatus.transactionId && (
                    <p className="text-sm mt-2">Mã giao dịch: {paymentStatus.transactionId}</p>
                )}
                <button
                    onClick={() => setPaymentStatus(null)}
                    className="mt-2 text-sm underline hover:no-underline"
                >
                    Đóng thông báo
                </button>
            </div>
        );
    };

    const renderOrderSummary = () => {
        if (!order) return null;

        return (
            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
                <h3 className="font-semibold text-blue-800 mb-2">Thông tin đơn hàng</h3>
                <div className="text-sm text-blue-700 space-y-1">
                    <p>Mã đơn hàng: <span className="font-mono">{order.requestId}</span></p>
                    <p>Số tiền: <span className="font-semibold">{numberFormat(order.amount)} VND</span></p>
                    <p>Phương thức: VNPay</p>
                    <p>Trạng thái: <span className="capitalize">{order.status}</span></p>
                    {order.expiresAt && (
                        <p>Hết hạn: {new Date(order.expiresAt).toLocaleString('vi-VN')}</p>
                    )}
                </div>
            </div>
        );
    };

    return (
        <main className="mt-6 px-4 lg:px-0 max-w-2xl mx-auto">
            {!isLoggedIn ? (
                <div className="flex justify-center">
                    <button
                        onClick={() => console.log("Open login modal")}
                        className="text-primary font-semibold italic"
                    >
                        Vui lòng đăng nhập để xem
                    </button>
                </div>
            ) : (
                <div>
                    {isLoading ? (
                        <div className="flex justify-center py-8">
                            <svg
                                className="animate-spin w-8 h-8 text-primary"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                            >
                                <circle
                                    className="opacity-25"
                                    cx="12"
                                    cy="12"
                                    r="10"
                                    stroke="currentColor"
                                    strokeWidth="4"
                                />
                                <path
                                    className="opacity-75"
                                    fill="currentColor"
                                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                                />
                            </svg>
                        </div>
                    ) : (
                        <>
                            {renderPaymentStatus()}
                            {renderOrderSummary()}

                            <div>
                                <div className="mb-6">
                                    <span>Xin chào </span>
                                    <span className="font-semibold text-primary">{user?.email}</span>
                                    <span>, vui lòng chọn số tiền muốn nạp</span>
                                </div>

                                {renderBankSelection()}
                                {renderPackages()}

                                <div className="mt-6 p-4 bg-yellow-50 border border-yellow-200 rounded-lg">
                                    <p className="text-sm text-yellow-800">
                                        <strong>Lưu ý:</strong> Sau khi chọn số tiền, bạn sẽ được chuyển đến trang thanh toán VNPay.
                                        Vui lòng hoàn thành thanh toán trong thời gian quy định.
                                    </p>
                                </div>
                            </div>
                        </>
                    )}
                </div>
            )}
            {isOpen && (
                <div className={"flex flex-row justify-center items-center mt-6"}>
                    <button
                        onClick={openNewTab}
                        className="px-6 py-3 bg-blue-600 text-white font-semibold rounded-xl shadow-md
               hover:bg-blue-700 active:scale-95 transition-all duration-200"
                    >
                        💳 Thanh toán ({selectedAmount})
                    </button>
                </div>

            )}
        </main>
    );
};

export default DonateChooseVnpayPage;