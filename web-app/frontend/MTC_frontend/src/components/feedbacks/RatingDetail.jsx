import React, { useState } from 'react';
import {Reply, UserReply} from "./Reply.jsx";

const RatingDetail = ({rating}) => {



    return (
        <>
            <div className={"pt-2"}>
                <article className="p-4 mb-6 text-base bg-black rounded-lg">
                    <footer className="flex justify-between items-center mb-2 bg-dark">
                        <div className="flex items-center">
                            <p className="inline-flex items-center mr-3 text-sm text-gray-900">
                                <img
                                    className="mr-2 w-6 h-6 rounded-full "
                                    src="https://static.cdnno.com/user/835bbd840351a54f46f58021ecf2caed/200.jpg?1700054846"
                                    alt="Hạ Bút"
                                />
                                <span className="font-bold text-white">Hạ Bút</span>
                            </p>
                            <div className="flex space-x-4 text-xs text-gray-500">
                                <div className="flex items-center">
                                    <svg className="w-5 h-5 text-yellow-400" xmlns="http://www.w3.org/2000/svg"
                                         viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                                        <path fillRule="evenodd"
                                              d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.006 5.404.434c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.434 2.082-5.005Z"
                                              clipRule="evenodd">
                                        </path>
                                    </svg>
                                    <span className="ml-1">5</span>
                                </div>
                                <div className="flex items-center">
                                    <svg className="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none"
                                         viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                        <path strokeLinecap="round" strokeLinejoin="round"
                                              d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z">
                                        </path>
                                        <path strokeLinecap="round" strokeLinejoin="round"
                                              d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z">
                                        </path>
                                    </svg>
                                    <span className="ml-1">234 chương</span>
                                </div>
                            </div>
                        </div>
                    </footer>
                    <p className="text-gray-700 line-clamp break-words">
                        Sau gần 250c mình xin mạn phép đánh giá<br/>
                        * Main trùng sinh về quá khứ, nhưng k có hệ thống, dùng kiến thức lúc trước để kiếm tiền, nuôi
                        vợ con. Cùng với sinh hoạt hàng ngày.<br/>
                        * Khác với những truyện đô thị, trùng sinh khác, bộ này rất ít có trang bức đánh mặt, mà là
                        thiên về tranh đấu thương trường hơn...
                    </p>
                    <span className="text-xs font-bold primary-text-color hover:cursor-pointer">Đọc tiếp</span>
                    <div className="flex justify-between items-center mt-4">
                        <div className="flex space-x-6">
                            <div
                                className="flex items-center space-x-1 text-xs text-gray-500 hover:underline hover:cursor-pointer">
                                <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg"
                                     fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
                                     aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75a.75.75 0 0 1 .75-.75 2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282m0 0h3.126">
                                    </path>
                                </svg>
                                <span>10</span>
                                <span className="hidden md:inline-flex">Thích</span>
                            </div>
                            <div
                                className="flex items-center space-x-1 text-xs text-gray-500 hover:underline hover:cursor-pointer">
                                <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"
                                     viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z">
                                    </path>
                                </svg>
                                <span>1</span>
                                <span className="hidden md:inline-flex">Trả lời</span>
                            </div>
                        </div>
                        <div className="w-52 md:w-60 text-right">
                            <div className="text-muted truncate text-xs">3 năm trước</div>
                        </div>
                    </div>
                </article>
            </div>
            <div className={"ml-12"}>
                <Reply />
                <UserReply />
            </div>
            {/*<UserReply />*/}

        </>

    );
}

const ReviewPanel = ({ book, chapter, onSubmitReview, onSubmitTranslation }) => {
    const [score, setScore] = useState(5);
    const [translationScore, setTranslationScore] = useState(5);

    return (
        <div className={`grid grid-cols-1 w-full bg-panel border border-auto p-4 gap-4 border-gray-500 ${book.kind === 1 ? "sm:grid-cols-2" : ""}`}>
            {/* Chấm điểm nội dung truyện */}
            <div className="space-y-6 p-2">
                <div className="flex flex-col space-y-3 w-full">
                    <h3>
                        Chấm điểm nội dung truyện: <span className="font-bold text-primary">{score}</span> điểm
                    </h3>
                    <div className="w-full">
                        <input
                            type="range"
                            min="1"
                            max="5"
                            step="0.1"
                            value={score}
                            onChange={(e) => setScore(e.target.value)}
                            className="slider"
                        />
                    </div>
                    <div className="text-muted italic text-center text-xs text-balance">
                        Đây là đánh giá nhanh, không kèm nội dung. Nếu bạn muốn viết đánh giá đầy đủ, vui lòng vào phần thông tin truyện để đánh giá.
                    </div>
                    <div className="flex justify-center">
                        <button
                            onClick={() => onSubmitReview(score)}
                            className="border border-primary text-primary w-1/2 py-1 rounded disabled:bg-gray-500"
                        >
                            GỬI
                        </button>
                    </div>
                </div>
            </div>

            {/* Chấm điểm chất lượng convert chương */}
            {book.kind === 1 && (
                <div className="space-y-6 p-2">
                    <div className="flex flex-col space-y-3 w-full">
                        <h3>
                            Chấm điểm chất lượng convert chương này: <span className="font-bold text-primary">{translationScore}</span> điểm
                        </h3>
                        <div className="w-full">
                            <input
                                type="range"
                                min="3"
                                max="5"
                                step="0.1"
                                value={translationScore}
                                onChange={(e) => setTranslationScore(e.target.value)}
                                className="slider"
                            />
                        </div>
                        <div className="text-muted italic text-center text-xs text-balance">
                            Đây là bản convert, vui lòng đánh giá dựa trên tiêu chí bản convert, không phải bản dịch.
                        </div>
                        <div className="flex justify-center">
                            <button
                                onClick={() => onSubmitTranslation(translationScore)}
                                className="border border-primary text-primary w-1/2 py-1 rounded disabled:bg-gray-500"
                            >
                                GỬI
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export  {RatingDetail, ReviewPanel};
