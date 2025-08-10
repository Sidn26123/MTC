import { useState } from "react"
import { ChevronDown } from "lucide-react"
import React from "react"

function CustomSelect({ label, value, options, onChange }) {
    const [isOpen, setIsOpen] = useState(false)

    return (
        <div className="relative">
            <label className="block text-sm font-medium text-gray-300 mb-2">{label}</label>
            <div
                className="bg-gray-700 border border-gray-600 rounded-md px-4 py-3 cursor-pointer flex items-center justify-between"
                onClick={() => setIsOpen(!isOpen)}
            >
                <span className="text-gray-300">{value}</span>
                <ChevronDown className={`w-4 h-4 text-gray-400 transition-transform ${isOpen ? "rotate-180" : ""}`} />
            </div>
            {isOpen && (
                <div className="absolute top-full left-0 right-0 bg-gray-700 border border-gray-600 rounded-md mt-1 z-10">
                    {options.map((option, index) => (
                        <div
                            key={index}
                            className="px-4 py-2 hover:bg-gray-600 cursor-pointer text-gray-300"
                            onClick={() => {
                                onChange(option)
                                setIsOpen(false)
                            }}
                        >
                            {option}
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}


export function DetailedStats() {
    const [storyType, setStoryType] = useState("Đọc truyện")
    const [chapter, setChapter] = useState("Tất cả chương")
    const [displayType, setDisplayType] = useState("Biểu đồ theo ngày")
    const [timeRange, setTimeRange] = useState("Hôm nay")
    const [fromDate, setFromDate] = useState("2025-07-11 00:00")
    const [toDate, setToDate] = useState("2025-07-11 23:59")

    return (
        <div className="bg-gray-600 rounded-lg p-6">
            <h2 className="text-xl font-semibold text-white mb-6">Xem thống kê chi tiết</h2>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                <CustomSelect
                    label="Chọn loại thống kê"
                    value={storyType}
                    options={["Đọc truyện", "Tặng quà", "Mở khóa", "Danh hiệu"]}
                    onChange={setStoryType}
                />

                <CustomSelect
                    label="Chọn chương"
                    value={chapter}
                    options={["Tất cả chương", "Chương 1", "Chương 2", "Chương 3"]}
                    onChange={setChapter}
                />

                <CustomSelect
                    label="Hiển thống kê theo"
                    value={displayType}
                    options={["Biểu đồ theo ngày", "Biểu đồ theo tuần", "Biểu đồ theo tháng"]}
                    onChange={setDisplayType}
                />

                <CustomSelect
                    label="Khoảng thời gian"
                    value={timeRange}
                    options={["Hôm nay", "Tuần này", "Tháng này", "Tùy chọn"]}
                    onChange={setTimeRange}
                />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                <div>
                    <label className="block text-sm font-medium text-gray-300 mb-2">Từ ngày</label>
                    <input
                        type="datetime-local"
                        value={fromDate}
                        onChange={(e) => setFromDate(e.target.value)}
                        className="w-full bg-gray-700 border border-gray-600 rounded-md px-4 py-3 text-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-300 mb-2">Đến ngày</label>
                    <input
                        type="datetime-local"
                        value={toDate}
                        onChange={(e) => setToDate(e.target.value)}
                        className="w-full bg-gray-700 border border-gray-600 rounded-md px-4 py-3 text-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
            </div>

            <button className="w-full bg-red-600 hover:bg-red-700 text-white font-medium py-3 px-6 rounded-md transition-colors">
                Xem Thống Kê Chi Tiết
            </button>
        </div>
    )
}
