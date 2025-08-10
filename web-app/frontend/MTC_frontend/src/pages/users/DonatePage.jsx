import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router';
// import {vnpay_icon} from '../../assets/VNPAY/vnpay-icon.png';
import vnpay_icon from '../../assets/VNPAY/vnpay_icon.png';
const PotatoIcon = () => (
    <div className="w-auto h-4 mx-1 inline-flex pr-10">
        <FontAwesomeIcon icon={faCoins} />
    </div>
)
;

function DonatePage() {
    const navigate = useNavigate();
    function handleGotoChoosePage() {
        navigate("/nap-tien/momo");
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
                {/*<div className="col-span-1 sm:col-span-2" onClick={handleGotoChoosePage}>*/}
                {/*    <a*/}
                {/*        href=""*/}
                {/*        className="border border-primary bg-secondary text-black px-auto py-5 w-full block rounded-3xl hover:bg-primary hover:text-white"*/}
                {/*    >*/}
                {/*        <div className="flex flex-col space-y-3">*/}
                {/*            <div className="flex justify-center space-x-2">*/}

                {/*                <img*/}
                {/*                    src={vnpay_icon}*/}
                {/*                    alt="momo"*/}
                {/*                    className="h-4"*/}
                {/*                />*/}

                {/*            </div>*/}
                {/*            <div className="text-center font-medium">*/}
                {/*                Mua Xu: Momo*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    </a>*/}
                {/*</div>*/}
            </div>
        </>
    )
};


export default DonatePage;