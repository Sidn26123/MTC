import { useState } from "react";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";

dayjs.extend(relativeTime);

const GiftPanel = ({ bookId, chapterId, user, donations, onDonate }) => {
    const [price, setPrice] = useState("");
    const formatter = new Intl.NumberFormat("vi-VN");

    const handleDonate = () => {
        const amount = Number(price);
        if (amount >= 5000 && amount % 1000 === 0) {
            onDonate(amount);
        } else {
            alert("Số KNB phải lớn hơn 5,000 và chia hết cho 1,000.");
        }
    };

    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 w-full bg-panel border border-auto p-4 gap-4 border-gray-500">
            {/* Phần tặng quà */}
            <div className="space-y-6">
                <h3 className="font-bold">Tặng quà</h3>
                <div className="space-y-3">
                    <div className="relative">
                        <input
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            className="h-10 w-full pl-5 pr-10 text-sm placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded form-auto"
                            placeholder="Nhập số"
                        />
                        <span className="absolute inset-y-0 right-0 p-2 mr-px text-primary rounded-r-lg">
              <img
                  src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                  alt="KNBs"
                  className="w-auto h-7"
              />
            </span>
                    </div>
                    <span className="flex text-muted italic text-xs">
            Số{" "}
                        <img
                            src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                            alt="KNBs"
                            className="w-auto h-4 mx-1"
                        />{" "}
                        phải lớn hơn 5,000 và chia hết cho 1,000.
          </span>
                </div>
                <div className="space-y-3">
                    <div className="flex justify-center">
                        <button
                            onClick={handleDonate}
                            className="border border-primary text-primary w-1/2 py-1 rounded"
                        >
                            TẶNG QUÀ
                        </button>
                    </div>
                </div>

                {/* Hiển thị số dư tài khoản */}
                {user?.isLoggedIn && (
                    <div className="flex justify-center items-center text-muted italic text-center text-xs text-balance">
                        <span>Đang có</span>
                        <span className="font-bold mx-1">{formatter.format(user.balance)}</span>
                        <img
                            src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                            alt="KNBs"
                            className="w-auto h-5 mr-1"
                        />{" "}
                        trong tài khoản.
                    </div>
                )}
            </div>

            {/* Phần danh sách hoạt động */}
            <div className="space-y-6">
                <h3 className="font-bold">Hoạt động</h3>
                {donations.length > 0 ? (
                    <ul role="list" className="space-y-6">
                        {donations.map((donate, index) => (
                            <li key={index} className="relative flex gap-x-4">
                                {index + 1 < donations.length && (
                                    <div className="absolute left-0 top-0 flex w-6 justify-center -bottom-6">
                                        <div className="w-px bg-gray-200"></div>
                                    </div>
                                )}
                                <div className="relative flex h-6 w-6 flex-none items-center justify-center bg-panel">
                                    <div className="h-1.5 w-1.5 rounded-full bg-gray-100 ring-1 ring-gray-300"></div>
                                </div>
                                <div className="flex-auto py-0.5 space-y-1">
                                    <div className="flex justify-between gap-x-4">
                                        <div className="py-0.5 text-xs text-muted">
                      <span className="flex items-center">
                        <span className="font-medium text-title mr-1">
                          {donate.user?.name}
                        </span>
                        tặng
                        <span className="font-medium mx-1">
                          {formatter.format(donate.amount)}
                        </span>
                        <img
                            src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                            alt="KNBs"
                            className="w-auto h-4"
                        />
                      </span>
                                        </div>
                                        <time className="flex-none py-0.5 text-xs text-gray-500">
                                            {dayjs(donate.created_at).fromNow()}
                                        </time>
                                    </div>
                                    <p className="text-xs text-muted ml-1">{donate.chapter?.name}</p>
                                </div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <div className="flex justify-center pt-12">
                        <svg
                            className="inline animate-spin w-6 h-6 text-primary"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth="1.5"
                            stroke="currentColor"
                            aria-hidden="true"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"
                            />
                        </svg>
                    </div>
                )}
            </div>
        </div>
    );
};

export default GiftPanel;
