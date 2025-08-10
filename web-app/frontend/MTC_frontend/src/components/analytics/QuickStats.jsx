"use client"

import { ChevronRight, Info } from "lucide-react"
import PropTypes from "prop-types"
import React from "react"

function StatItem({ label, value, hasInfo = false }) {
    return (
        <div className="flex items-center justify-between py-3 px-4 hover:bg-gray-700 transition-colors">
            <div className="flex items-center space-x-2">
                <ChevronRight className="w-4 h-4 text-gray-400" />
                <span className="text-gray-300">{label}</span>
                {hasInfo && <Info className="w-4 h-4 text-gray-500" />}
            </div>
            <span className="text-white font-medium">{value}</span>
        </div>
    )
}


export function QuickStats() {
    return (
        <div className="bg-gray-600 rounded-lg p-6 mb-8">
            <h2 className="text-xl font-semibold text-white mb-6">Thống kê nhanh tháng này</h2>
            <RatingBars />
        </div>
    )
}
import { Star } from "lucide-react";

const ratingData = {
    "1": 0,
    "2": 0,
    "3": 1,
    "4": 3,
    "5": 2
};

export function RatingBars() {
    const maxCount = Math.max(...Object.values(ratingData), 1); // đảm bảo > 0 tránh chia 0

    return (
        <div className="bg-gray-600 rounded-lg p-6 mb-8">
            <h2 className="text-xl font-semibold text-white mb-6">Đánh giá người dùng</h2>

            <div className="space-y-3">
                {[5, 4, 3, 2, 1].map((star) => {
                    const count = ratingData[star] ?? 0;
                    const percent = (count / maxCount) * 100;

                    return (
                        <div key={star} className="flex items-center space-x-4">
                            <div className="flex items-center w-16 text-yellow-400">
                                <Star className="w-4 h-4 fill-yellow-400 mr-1" />
                                <span className="text-sm text-white">{star}</span>
                            </div>

                            <div className="flex-1 bg-gray-700 rounded h-3 overflow-hidden">
                                <div
                                    className="bg-yellow-400 h-full transition-all duration-300"
                                    style={{ width: `${percent}%` }}
                                />
                            </div>

                            <div className="w-8 text-right text-sm text-white">{count}</div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}
