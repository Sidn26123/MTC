"use client"

import { BookOpen, Lock, DollarSign, Star, Gift, Users, Bookmark, MessageSquare, Eye, TrendingUp } from "lucide-react"
import PropTypes from "prop-types"
import React from "react"
import { useCurrentPublishedNovel } from '../../stores/publisherStore.js';

function StatCard({ icon, label, value, iconColor }) {
    return (
        <div className="bg-gray-700 rounded-lg p-4 flex items-center space-x-4">
            <div className={`p-2 rounded-lg ${iconColor}`}>{icon}</div>
            <div className="flex-1">
                <p className="text-gray-300 text-sm">{label}</p>
                <p className="text-white font-semibold text-lg">{value}</p>
            </div>
        </div>
    )
}

// (Optional) Kiểm tra kiểu props bằng PropTypes
StatCard.propTypes = {
    icon: PropTypes.node.isRequired,
    label: PropTypes.string.isRequired,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    iconColor: PropTypes.string.isRequired,
}

export function OverviewStats() {
    const novel = useCurrentPublishedNovel();
    console.log("Current Novel in OverviewStats:", novel);

    const stats = [
        {
            icon: <BookOpen className="w-5 h-5" />,
            label: "Tổng chương",
            value: novel?.totalChapters ? novel.totalChapters : "0",
            iconColor: "bg-orange-600",
        },
        {
            icon: <Lock className="w-5 h-5" />,
            label: "Đã có 0 lượt mở khóa",
            value: "0",
            iconColor: "bg-red-600",
        },
        {
            icon: <Star className="w-5 h-5" />,
            label: "Đánh giá",
            value: novel?.avgRate ? `${novel.avgRate} điểm / (${novel.totalRates})` : "0 điểm",
            iconColor: "bg-pink-600",
        },
        {
            icon: <DollarSign className="w-5 h-5" />,
            label: "Tổng tiền mở khóa",
            value: "0 đ",
            iconColor: "bg-green-600",
        },
        {
            icon: <Gift className="w-5 h-5" />,
            label: "Lượt đề cử",
            value: novel?.totalPromotions ? novel.totalPromotions : "0",
            iconColor: "bg-red-600",
        },

        {
            icon: <Users className="w-5 h-5" />,
            label: "Người đang theo dõi",
            value: "0",
            iconColor: "bg-gray-600",
        },

        {
            icon: <Bookmark className="w-5 h-5" />,
            label: "Lượt đánh dấu",
            value: novel?.totalBookmarks ? novel.totalBookmarks : "0",
            iconColor: "bg-green-600",
        },
        {
            icon: <Eye className="w-5 h-5" />,
            label: "Lượt đọc",
            value: novel?.totalViews ? novel.totalViews : "0",
            iconColor: "bg-red-600",
        },
        {
            icon: <MessageSquare className="w-5 h-5" />,
            label: "Lượt thảo luận",
            value: novel?.totalComments ? novel.totalComments : "0",
            iconColor: "bg-blue-600",
        },
        {
            icon: <TrendingUp className="w-5 h-5" />,
            label: "Tổng quy đổi từ lượt đọc 0 đ",
            value: "0 đ",
            iconColor: "bg-yellow-600",
        },
    ]

    return (
        <div className="bg-gray-600 rounded-lg p-6">
            <div className="mb-6">
                <h2 className="text-xl font-semibold text-white">Thống kê tổng</h2>
                <p className="text-gray-400">Truyện Mới</p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                {stats.map((stat, index) => (
                    <StatCard key={index} icon={stat.icon} label={stat.label} value={stat.value} iconColor={stat.iconColor} />
                ))}
            </div>
        </div>
    )
}
