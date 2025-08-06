import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import { useUser } from '../../stores/userStores.js';
import { currencyIdForDonateRelate } from '../../constants/const.js';
import { paymentDeposit } from '../../services/paymentService.js';
import { CoinIcon } from '../../components/payments/Currency.jsx';

const MOCK_PACKAGES = {
    50000: {},
    100000: {},
    200000: {},
    500000: {},
};

const numberFormat = (num) =>
    new Intl.NumberFormat("vi-VN", { style: "decimal" }).format(num);

const PotatoIcon = () => (
        <div className="w-auto h-4 mx-1 inline-flex pr-10">
            <FontAwesomeIcon icon={faCoins} />
        </div>
    )
;

export const DonateChoosePage = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true); // giả định đăng nhập
    const [userName, setUserName] = useState("Sidnn"); // mock tên user
    const [isLoading, setIsLoading] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [order, setOrder] = useState(null);
    const user = useUser();
    const handleStore = (key) => {
        setIsSubmitting(true);
        // Giả lập xử lý order
        setTimeout(() => {
            setOrder({
                method: "momo",
                amount: key,
                code: "ORD123456",
                amount_usd: (key / 20000).toFixed(2),
                checkout_url: "https://paypal.com/checkout?order=ORD123456",
            });
            setIsSubmitting(false);
        }, 1000);
    };
    const [selectedAmount, setSelectedAmount] = useState(null);
    const [loading, setLoading] = useState(false);

    const handlePaymentMomo = async () => {
        const amount = Number(selectedAmount || Object.keys(MOCK_PACKAGES)[0]);
        // setSelectedAmount(amount);
        setLoading(true);
        try {
            const data = {
                "customer": user.id,
                "amount": 50000,
                "userId": user.id,
                "currencyId": currencyIdForDonateRelate
            }
            console.log("Calling paymentDeposit with data:", data);
            const response = await paymentDeposit(data);
            console.log("Response from paymentDeposit:", response);
            // Nếu gọi thành công, redirect đến MoMo
            if (response?.payUrl) {
                window.open(response.payUrl, '_blank');
            } else {
                alert("Gọi API thành công nhưng không nhận được URL thanh toán.");
            }
        } catch (error) {
            console.error("Lỗi khi gọi thanh toán:", error);
            alert("Đã xảy ra lỗi khi gọi API thanh toán.");
        } finally {
            setLoading(false);
        }
    };

    function handleSubmitPayment(key) {
        setSelectedAmount(key);
        handlePaymentMomo().then(r => {});
    }

    const renderPackages = () => (
        <div className="space-y-4">
            {Object.keys(MOCK_PACKAGES).map((key) => (
                <button
                    key={key}
                    onClick={() => handleSubmitPayment(key)}
                    className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white"
                >
                    <div className="flex justify-center space-x-2">
                        {!isSubmitting ? (
                            <>
                                <span className="font-medium">{numberFormat(key)}</span>
                                <PotatoIcon />
                            </>
                        ) : (
                            <svg
                                className="inline animate-spin w-5 h-5"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth={1.5}
                                stroke="currentColor"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313..."
                                />
                            </svg>
                        )}
                    </div>
                </button>
            ))}
        </div>
    );

    return (
        <main className="mt-6 px-4 lg:px-0">
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
                        <div className="flex justify-center">
                            <svg
                                className="inline animate-spin w-8 h-8"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth={1.5}
                                stroke="currentColor"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="..."
                                />
                            </svg>
                        </div>
                    ) : (
                        <>
                            {!order ? (
                                <>
                                    <div>
                                        <span>Xin chào </span>
                                        <span className="font-semibold text-primary">{user.email}</span>
                                        <span>, vui lòng chọn số lượng muốn mua</span>
                                    </div>
                                    {renderPackages()}
                                </>
                            ) : order.method === "momo" ? (
                                <div>
                                    <div className="text-center items-center mb-6">
                                        Bạn đang mua{" "}
                                        <span className="font-bold">{numberFormat(order.amount)}</span>
                                        <CoinIcon />
                                        (đơn hàng: <strong>{order.code}</strong>), số tiền cần thanh toán là{" "}
                                        <strong>{order.amount}$</strong>. Ấn vào nút thanh toán bên dưới để
                                        thanh toán qua Momo.
                                    </div>
                                    <div className="flex justify-center">
                                        {order.checkout_url ? (
                                            <div
                                                onClick={() => handlePaymentMomo()}
                                                className="bg-primary text-white px-5 py-2 rounded"
                                            >
                                                Thanh toán {order.amount}$
                                            </div>
                                        ) : (
                                            <div className="text-center italic text-red-500 font-bold">
                                                Có lỗi trong quá trình thanh toán, vui lòng thử lại hoặc Yêu cầu hỗ
                                                trợ
                                            </div>
                                        )}
                                    </div>
                                </div>
                            ) : null}
                        </>
                    )}
                </div>
            )}
        </main>
    );
};

export default DonateChoosePage;
