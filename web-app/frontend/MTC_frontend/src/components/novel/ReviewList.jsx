import { useEffect, useState } from "react";
import ReviewItem from "./ReviewItem";
import axios from "axios";
const mockReviews = [
    {
        id: 1,
        user: {
            id: 'user1',
            name: 'Nguyễn Văn A',
            avatar: 'https://via.placeholder.com/40',
            level: 5,
        },
        novel: {
            id: 'novel1',
            name: 'Truyện Hay 1',
            slug: 'truyen-hay-1',
        },
        point: 4,
        content: 'Truyện rất hay và hấp dẫn!',
        createdAt: '2025-07-25T10:00:00Z',
    },
    {
        id: 2,
        user: {
            id: 'user2',
            name: 'Trần Thị B',
            avatar: 'https://via.placeholder.com/40',
            level: 3,
        },
        novel: {
            id: 'novel2',
            name: 'Truyện Thú Vị 2',
            slug: 'truyen-thu-vi-2',
        },
        point: 5,
        content: 'Không thể rời mắt khỏi câu chuyện!',
        createdAt: '2025-07-24T15:30:00Z',
    },
];
const ReviewList = () => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        // Fake fetch
        setTimeout(() => {
            setReviews(mockReviews);
            setLoading(false);
        }, 500);
    }, []);

    return (
        <div id="reviews">
            <a href="https://metruyencv.com/danh-gia" className="box-title flex justify-between items-center">
                <h2>ĐÁNH GIÁ MỚI</h2>
                <svg className="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                    <use href="#icon-45dfdae75c66f6db1091e91efb93d2f4" />
                </svg>
            </a>

            {loading ? (
                <div className="flex justify-center items-center">
                    <svg className="inline animate-spin w-8 h-8 text-primary" fill="none" viewBox="0 0 24 24">
                        <path d="..." />
                    </svg>
                </div>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-5">
                    {reviews.map((review, index) => (
                        <ReviewItem key={review.id || index} review={review} index={index} />
                    ))}
                </div>
            )}
        </div>
    );
};

export default ReviewList;
