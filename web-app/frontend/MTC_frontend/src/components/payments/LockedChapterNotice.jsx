import React, { useState } from 'react';

const LockedChapterNotice = () => {
    const [isLocked, setIsLocked] = useState(true);
    const [isMultiple, setIsMultiple] = useState(false);
    const [amount, setAmount] = useState('');
    const [defaultPrice, setDefaultPrice] = useState(5); // Ví dụ
    const [keyPrice, setKeyPrice] = useState(1); // Ví dụ

    const handleUnlock = (type) => {
        console.log(`Unlock with type: ${type}, amount: ${amount || 1}`);
        // Gọi API mở khóa ở đây
    };

    if (!isLocked) return null;

    return (
        <div className="p-4 text-lg mx-2 lg:mx-0 flex justify-center">
            <div className="max-w-sm text-center space-y-3">
                <div className="font-bold">- Chương Bị Khóa -</div>

                <div className="flex items-center justify-center">
                    <span>Bạn có thể mở khóa bằng</span>
                    <img
                        src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                        alt="KNBs"
                        className="w-auto h-4 mx-1"
                    />
                    <span>hoặc</span>
                    <svg
                        className="w-4 h-4 text-yellow-400 mx-1"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                        aria-hidden="true"
                    >
                        <path
                            fillRule="evenodd"
                            d="M8 7a5 5 0 1 1 3.61 4.804l-1.903 1.903A1 1 0 0 1 9 14H8v1a1 1 0 0 1-1 1H6v1a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1v-2a1 1 0 0 1 .293-.707L8.196 8.39A5.002 5.002 0 0 1 8 7Zm5-3a.75.75 0 0 0 0 1.5A1.5 1.5 0 0 1 14.5 7 .75.75 0 0 0 16 7a3 3 0 0 0-3-3Z"
                            clipRule="evenodd"
                        />
                    </svg>
                </div>

                {/* Nút mở khóa nhiều chương */}
                <button
                    className="text-primary"
                    onClick={() => setIsMultiple(!isMultiple)}
                >
                    Tôi muốn mở khóa nhiều chương cùng lúc
                </button>

                {/* Form nhập số lượng nếu là unlock nhiều */}
                {isMultiple && (
                    <div className="space-y-2">
            <span className="text-muted italic text-sm">
              Nhập số lượng chương muốn mở khóa đồng thời
            </span>
                        <input
                            className="text-sm w-full placeholder-slate-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-md form-auto text-center"
                            placeholder="nhập số"
                            type="number"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                        />
                    </div>
                )}

                <div className="flex space-x-5">
                    <button
                        onClick={() => handleUnlock('default')}
                        className="border border-primary bg-primary rounded-xl px-4 py-2 w-48 disabled:bg-gray-500"
                    >
            <span className="flex text-white justify-center space-x-2">
              <span className="font-bold">{defaultPrice}</span>
              <img
                  src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                  alt="KNBs"
                  className="w-auto h-6"
              />
            </span>
                    </button>

                    <button
                        onClick={() => handleUnlock('key')}
                        className="border border-primary bg-primary rounded-xl px-4 py-2 w-48 disabled:bg-gray-500"
                    >
            <span className="flex text-white justify-center space-x-1">
              <span className="font-bold">{keyPrice}</span>
              <svg
                  className="w-6 h-6 text-yellow-400"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
              >
                <path
                    fillRule="evenodd"
                    d="M8 7a5 5 0 1 1 3.61 4.804l-1.903 1.903A1 1 0 0 1 9 14H8v1a1 1 0 0 1-1 1H6v1a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1v-2a1 1 0 0 1 .293-.707L8.196 8.39A5.002 5.002 0 0 1 8 7Zm5-3a.75.75 0 0 0 0 1.5A1.5 1.5 0 0 1 14.5 7 .75.75 0 0 0 16 7a3 3 0 0 0-3-3Z"
                    clipRule="evenodd"
                />
              </svg>
            </span>
                    </button>
                </div>

                {/* Link đổi kẹo sang khoai */}
                <a
                    href="https://metruyencv.com/tai-khoan/mua-khoai"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center justify-center text-muted italic text-sm"
                >
                    <span>Đổi</span>
                    <img
                        src="https://assets.metruyencv.com/build/assets/candies-d5f42b83.png"
                        alt="candy"
                        className="w-auto h-3 mx-1"
                    />
                    <span>sang</span>
                    <img
                        src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png"
                        alt="KNBs"
                        className="w-auto h-3 mx-1"
                    />
                    <span>ở đây</span>
                </a>
            </div>
        </div>
    );
};

export default LockedChapterNotice;
