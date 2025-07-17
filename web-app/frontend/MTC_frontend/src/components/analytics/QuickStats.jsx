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
        <div className="bg-gray-800 rounded-lg p-6 mb-8">
            <h2 className="text-xl font-semibold text-white mb-6">Thống kê nhanh tháng này</h2>

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                <div className="space-y-1">
                    <StatItem label="Lượt đọc chương mới: 0" value="" hasInfo />
                    <StatItem label="Giả lượt đọc chương mới: 3" value="" hasInfo />
                    <StatItem label="Lượt đọc chương cũ: 0" value="" hasInfo />
                    <StatItem label="Giả lượt đọc chương cũ: 3" value="" hasInfo />
                </div>

                <div className="space-y-1">
                    <StatItem label="Tiền lượt đọc tháng: 0 đ" value="" hasInfo />
                    <StatItem label="Tiền mở khóa tháng: 0 đ" value="" />
                    <StatItem label="Tiền tặng quà tháng: 0 đ" value="" />
                    <StatItem label="Tiền danh hiệu tháng: 0 đ" value="" />
                </div>
            </div>
        </div>
    )
}
