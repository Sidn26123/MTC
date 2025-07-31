import React, { useState } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Analytics = () => {

    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');
    const defaultMonthValue = `${currentYear}-${currentMonth}`;

    const [hovered, setHovered] = useState(null);

    const [mode, setMode] = useState('Monthly');
    const [filter, setFilter] = useState('MonthYear');
    const [filterValue, setFilterValue] = useState(defaultMonthValue);
    const [data, setData] = useState({
        labels: Array.from({ length: 31 }, (_, i) => i + 1),
        datasets: [{
            label: 'Visitors',
            data: [400, 250, 300, 150, 200, 100, 350, 180, 220, 130, 310, 90, 200, 150, 250, 120, 300, 140, 350, 110, 290, 160, 210, 130, 340, 170, 230, 120, 280, 150, 370],
            backgroundColor: '#4B5EAA',
        }],
    });

    const handleModeChange = (newMode) => {
        setMode(newMode);
        if (newMode === 'Monthly') {
            setFilterValue(defaultMonthValue)
            setData({
                labels: Array.from({ length: 31 }, (_, i) => i + 1),
                datasets: [{
                    label: 'Visitors',
                    data: [400, 250, 300, 150, 200, 100, 350, 180, 220, 130, 310, 90, 200, 150, 250, 120, 300, 140, 350, 110, 290, 160, 210, 130, 340, 170, 230, 120, 280, 150, 370],
                    backgroundColor: '#4B5EAA',
                }],
            });
        } else {
            setFilterValue(currentYear);
            setData({
                labels: Array.from({ length: 12 }, (_, i) => i + 1),
                datasets: [{
                    label: 'Visitors',
                    data: [3200, 2800, 3400, 2600, 2900, 2700, 3100, 2500, 3000, 2700, 3300, 2900],
                    backgroundColor: '#4B5EAA',
                }],
            });
        }
        // setFilterValue('');  // Reset filter khi đổi mode
    };

    // const handleFilterChange = (newFilter) => {
    //     setFilter(newFilter);
    //     // Placeholder for dynamic data based on filter (e.g., specific month or year)
    //     // You can implement API call or data fetching here
    // };
    const handleFilterChange = (e) => {
        const value = e.target.value;
        setFilterValue(value);

        // TODO: fetch hoặc lọc dữ liệu dựa trên giá trị filter
        fetchData(value);
    };

    const fetchData = (value) => {
        // Giả lập xử lý dữ liệu dựa trên tháng/năm (hoặc gọi API)
        if (mode === 'Monthly') {
            console.log('Đang lấy dữ liệu tháng:', value); // value dạng '2025-07'
        } else {
            console.log('Đang lấy dữ liệu năm:', value);   // value dạng '2025'
        }

        // Sau đó cập nhật bảng dựa vào dữ liệu fetch được
        // setData( /* dữ liệu mới */);
    };

    const options = {
        responsive: true,
        plugins: {
            legend: { position: 'top' },
            title: { display: false },
        },
        scales: {
            // y: { beginAtZero: true, max: 500 },
            y: { beginAtZero: true },
        },
    };

    return (
        <div className="w-[600px] mx-auto p-4">
            <h3 className="text-lg font-bold">Analytics</h3>
            <p className="text-gray-600">Visitor analytics of last 30 days</p>
            <div className="flex justify-between items-center mt-4">
                {/* <div>
                    <button onClick={() => handleFilterChange('MonthYear')} className={`mr-2 px-3 py-1 rounded ${filter === 'MonthYear' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}>
                        {filter === 'MonthYear' ? 'Month/Year' : 'Year'}
                    </button>
                    <button onClick={() => handleFilterChange('Year')} className={`px-3 py-1 rounded ${filter === 'Year' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}>
                        Year
                    </button>
                </div> */}
                <div>
                    <button onClick={() => handleModeChange('Monthly')} className={`mr-2 px-3 py-1 rounded ${mode === 'Monthly' ? 'bg-blue-500 text-white' : 'bg-gray-400'}`}>
                        Tháng
                    </button>
                    <button onClick={() => handleModeChange('Yearly')} className={`px-3 py-1 rounded ${mode === 'Yearly' ? 'bg-blue-500 text-white' : 'bg-gray-400'}`}>
                        Năm
                    </button>
                </div>

                <div>
                    {mode === 'Monthly' ? (
                        <input
                            type="month"
                            value={filterValue}
                            onChange={handleFilterChange}
                            className="border px-2 py-1 rounded"
                        />
                    ) : (
                        // <input
                        //     type="number"
                        //     min="2000"
                        //     max={new Date().getFullYear()}
                        //     placeholder="Chọn năm"
                        //     value={filterValue}
                        //     onChange={handleFilterChange}
                        //     className="border px-2 py-1 rounded w-[120px]"
                        // />
                        <select
                            value={filterValue}
                            onChange={handleFilterChange}
                            className="border px-2 py-1 rounded bg-gray-500"
                        >
                            {Array.from({ length: currentYear - 2010 }, (_, i) => 2011 + i).map((year) => (
                                <option key={year} value={year}>{year}</option>
                            ))}
                        </select>
                    )}
                </div>
            </div>
            <Bar data={data} options={options} />
        </div>
    );
};

export default Analytics;