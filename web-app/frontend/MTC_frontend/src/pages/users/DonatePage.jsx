import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router';

const PotatoIcon = () => (
    <div className="w-auto h-4 mx-1 inline-flex pr-10">
        <FontAwesomeIcon icon={faCoins} />
    </div>
)
;

function DonatePage() {
    const navigate = useNavigate();
    function handleGotoChoosePage() {
        navigate("/nap-tien/chi-tiet");
    }

    return (
        <>

            <div className="bg-secondary p-4 rounded-xl mb-6 text-black">
            <p>
                <strong>Vui lòng đọc kỹ nội dung bên dưới trước khi mua:</strong>
            </p>
            <div className="mt-2 pl-2">
                {[
                    "là đơn vị tiền ảo chỉ lưu hành trong hệ thống",
                    "chỉ có thể dùng để nâng cấp tài khoản, mở khóa chương, tặng quà cho tác giả",
                    "đã mua sẽ không được hoàn lại vì bất cứ lý do nào",
                    "chỉ được cộng cho bạn khi nào chúng tôi chắc chắn rằng đã nhận được thanh toán của bạn",
                    "có thể mua thông qua một trong các hình thức thanh toán bên dưới",
                ].map((text, index) => (
                    <div className="items-center" key={index}>
                        <PotatoIcon />
                        {text}
                    </div>
                ))}
            </div>
        </div>

                                  {/* Các phương thức thanh toán */}
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {/* Paypal / Visa / MasterCard */}
            {/*<div className="col-span-1">*/}
            {/*    <a*/}
            {/*        href="https://metruyencv.com/tai-khoan/mua-khoai/paypal"*/}
            {/*        className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white"*/}
            {/*    >*/}
            {/*        <div className="flex flex-col space-y-3">*/}
            {/*            <div className="flex justify-center space-x-2">*/}
            {/*                <img*/}
            {/*                    src="https://assets.metruyencv.com/build/assets/paypal-99cbe9aa.png"*/}
            {/*                    alt="paypal"*/}
            {/*                    className="h-4"*/}
            {/*                />*/}
            {/*                <img*/}
            {/*                    src="https://assets.metruyencv.com/build/assets/visa-2110653d.png"*/}
            {/*                    alt="visa"*/}
            {/*                    className="h-4"*/}
            {/*                />*/}
            {/*                <img*/}
            {/*                    src="https://assets.metruyencv.com/build/assets/master-card-5219270a.png"*/}
            {/*                    alt="master"*/}
            {/*                    className="h-4"*/}
            {/*                />*/}
            {/*            </div>*/}
            {/*            <div className="text-center font-medium">*/}
            {/*                Thanh toán qua Paypal, Visa, Master Card*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </a>*/}
            {/*</div>*/}

            {/*/!* Đổi Kẹo sang Khoai *!/*/}
            {/*<div className="col-span-1">*/}
            {/*    <button*/}
            {/*        className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white disabled:bg-gray-500"*/}
            {/*        onClick={() => alert("Đổi Kẹo sang Khoai")}*/}
            {/*    >*/}
            {/*        <div className="flex flex-col space-y-3">*/}
            {/*            <div className="flex justify-center items-center space-x-2 font-medium text-lg">*/}
            {/*                <img*/}
            {/*                    src="https://assets.metruyencv.com/build/assets/candies-d5f42b83.png"*/}
            {/*                    alt="candy"*/}
            {/*                    className="w-auto h-6"*/}
            {/*                />*/}
            {/*                <svg*/}
            {/*                    className="h-6"*/}
            {/*                    xmlns="http://www.w3.org/2000/svg"*/}
            {/*                    width="24"*/}
            {/*                    height="24"*/}
            {/*                    viewBox="0 0 24 24"*/}
            {/*                    fill="currentColor"*/}
            {/*                >*/}
            {/*                    <path d="M12.089 3.634a2 2 0 0 0 -1.089 1.78l-.001 2.586h-6.999a2 2 0 0 0 -2 2v4l.005 .15a2 2 0 0 0 1.995 1.85l6.999 -.001l.001 2.587a2 2 0 0 0 3.414 1.414l6.586 -6.586a2 2 0 0 0 0 -2.828l-6.586 -6.586a2 2 0 0 0 -2.18 -.434l-.145 .068z" />*/}
            {/*                </svg>*/}
            {/*                <img*/}
            {/*                    src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"*/}
            {/*                    alt="KNBs"*/}
            {/*                    className="w-auto h-6"*/}
            {/*                />*/}
            {/*            </div>*/}
            {/*            <div className="text-center font-medium">*/}
            {/*                Lưu ý không đổi ngược lại thành Kẹo được*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </button>*/}
            {/*</div>*/}

            {/* P2P */}
            <div className="col-span-1 sm:col-span-2" onClick={handleGotoChoosePage}>
                <a
                    href=""
                    className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white"
                >
                    <div className="flex flex-col space-y-3">
                        <div className="flex justify-center space-x-2">

                            <img
                                src="https://assets.metruyencv.com/build/assets/momo-icon-f8fdcda0.png"
                                alt="momo"
                                className="h-4"
                            />

                        </div>
                        <div className="text-center font-medium">
                            Mua Xu: Momo
                        </div>
                    </div>
                </a>
            </div>
        </div>
        </>
    )
};


export default DonatePage;