// import React, { useState } from 'react';
// import { useEffect } from 'react';
// import { Bar } from 'react-chartjs-2';
// import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
// import {
//     getNovelStatistic

// } from '../../services/novelStatisticService';
// ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// const Analytics = () => {

//     const currentDate = new Date();
//     const currentYear = currentDate.getFullYear();
//     const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');
//     const defaultMonthValue = `${currentYear}-${currentMonth}`;

//     const [hovered, setHovered] = useState(null);

//     const [mode, setMode] = useState('Monthly');
//     const [filter, setFilter] = useState('MonthYear');
//     const [filterValue, setFilterValue] = useState(defaultMonthValue);
//     const [data, setData] = useState({
//         labels: Array.from({ length: 31 }, (_, i) => i + 1),
//         datasets: [{
//             label: 'Visitors',
//             data: [400, 250, 300, 150, 200, 100, 350, 180, 220, 130, 310, 90, 200, 150, 250, 120, 300, 140, 350, 110, 290, 160, 210, 130, 340, 170, 230, 120, 280, 150, 370],
//             backgroundColor: '#4B5EAA',
//         }],
//     });

//     const fetchData = async (value) => {
//         let startDate = '';
//         let endDate = '';
//         let segmentType = '';
//         let labels = [];
//         let defaultTotals = [];

//         if (mode === 'Monthly') {
//             segmentType = 'DAY';
//             const [year, month] = value.split('-');
//             const start = new Date(year, month - 1, 1);
//             const end = new Date(year, month, 0); // ngày cuối tháng
//             const daysInMonth = end.getDate();

//             labels = Array.from({ length: daysInMonth }, (_, i) => i + 1);
//             defaultTotals = Array(daysInMonth).fill(0);

//             startDate = start.toLocaleDateString('en-GB');
//             endDate = end.toLocaleDateString('en-GB');
//         } else {
//             segmentType = 'MONTH';
//             const year = parseInt(value);
//             labels = Array.from({ length: 12 }, (_, i) => `Tháng ${i + 1}`);
//             defaultTotals = Array(12).fill(0);

//             const start = new Date(year, 0, 1);
//             const end = new Date(year, 11, 31);

//             startDate = start.toLocaleDateString('en-GB');
//             endDate = end.toLocaleDateString('en-GB');
//         }

//         try {
//             const result = await getNovelStatistic(startDate, endDate, segmentType);

//             // Tạo mapping từ chỉ số (ngày hoặc tháng) đến tổng
//             const dataMap = new Map();

//             result.forEach(item => {
//                 const date = new Date(item.startTime);
//                 const key = mode === 'Monthly'
//                     ? date.getDate()                      // Ngày (1-31)
//                     : date.getMonth() + 1;                // Tháng (1-12)

//                 dataMap.set(key, item.total);
//             });

//             const totals = labels.map((label, index) => {
//                 const key = mode === 'Monthly' ? label : index + 1;
//                 return dataMap.get(key) || 0;
//             });

//             setData({
//                 labels,
//                 datasets: [{
//                     label: 'Số lượng truyện',
//                     data: totals,
//                     backgroundColor: '#4B5EAA',
//                 }],
//             });

//         } catch (error) {
//             console.error('Lỗi khi tải dữ liệu thống kê:', error);
//             alert('Không thể tải dữ liệu thống kê.');
//         }
//     };



//     // const fetchData = async (value) => {
//     //     let startDate = '';
//     //     let endDate = '';
//     //     let segmentType = '';

//     //     if (mode === 'Monthly') {
//     //         segmentType = 'DAY';
//     //         const [year, month] = value.split('-');
//     //         const start = new Date(year, month - 1, 1);
//     //         const end = new Date(year, month, 0); // ngày cuối tháng

//     //         startDate = start.toLocaleDateString('en-GB'); // dd/mm/yyyy
//     //         endDate = end.toLocaleDateString('en-GB');

//     //     } else {
//     //         segmentType = 'MONTH';
//     //         const year = parseInt(value);
//     //         const start = new Date(year, 0, 1);
//     //         const end = new Date(year, 11, 31);

//     //         startDate = start.toLocaleDateString('en-GB'); // dd/mm/yyyy
//     //         endDate = end.toLocaleDateString('en-GB');
//     //     }

//     //     try {
//     //         const result = await getNovelStatistic(startDate, endDate, segmentType);
//     //         console.log('Thống kê truyện:', result);    

//     //         const labels = result.map(item => {
//     //             const date = new Date(item.startTime);
//     //             return mode === 'Monthly'
//     //                 ? date.getDate()
//     //                 : `Tháng ${date.getMonth() + 1}`;
//     //         });


//     //         const totals = result.map(item => item.total);

//     //         setData({
//     //             labels,
//     //             datasets: [{
//     //                 label: 'Số lượng truyện',
//     //                 data: totals,
//     //                 backgroundColor: '#4B5EAA',
//     //             }],
//     //         });

//     //     } catch (error) {
//     //         console.error('Lỗi khi tải dữ liệu thống kê:', error);
//     //         alert('Không thể tải dữ liệu thống kê.');
//     //     }
//     // };

//     useEffect(() => {
//         fetchData(filterValue);
//     }, [mode, filterValue]);

//     //     useEffect(() => {
//     //     fetchData(filterValue);
//     // }, []);

//     const handleModeChange = (newMode) => {
//         let newFilterValue = '';

//         if (newMode === 'Monthly') {
//             newFilterValue = defaultMonthValue;
//         } else {
//             newFilterValue = currentYear.toString();
//         }

//         setMode(newMode);
//         setFilterValue(newFilterValue);
//     };

//     //     const handleModeChange = (newMode) => {
//     //     setMode(newMode);
//     //     let newFilterValue = '';

//     //     if (newMode === 'Monthly') {
//     //         newFilterValue = defaultMonthValue;
//     //     } else {
//     //         newFilterValue = currentYear.toString();
//     //     }

//     //     setFilterValue(newFilterValue);
//     //     fetchData(newFilterValue); // Gọi lại sau khi set filter
//     // };


//     // const handleModeChange = (newMode) => {
//     //     setMode(newMode);
//     //     if (newMode === 'Monthly') {
//     //         setFilterValue(defaultMonthValue)
//     //         setData({
//     //             labels: Array.from({ length: 31 }, (_, i) => i + 1),
//     //             datasets: [{
//     //                 label: 'Visitors',
//     //                 data: [400, 250, 300, 150, 200, 100, 350, 180, 220, 130, 310, 90, 200, 150, 250, 120, 300, 140, 350, 110, 290, 160, 210, 130, 340, 170, 230, 120, 280, 150, 370],
//     //                 backgroundColor: '#4B5EAA',
//     //             }],
//     //         });
//     //     } else {
//     //         setFilterValue(currentYear);
//     //         setData({
//     //             labels: Array.from({ length: 12 }, (_, i) => i + 1),
//     //             datasets: [{
//     //                 label: 'Visitors',
//     //                 data: [3200, 2800, 3400, 2600, 2900, 2700, 3100, 2500, 3000, 2700, 3300, 2900],
//     //                 backgroundColor: '#4B5EAA',
//     //             }],
//     //         });
//     //     }
//     //     // setFilterValue('');  // Reset filter khi đổi mode
//     // };


//     // const handleFilterChange = (newFilter) => {
//     //     setFilter(newFilter);
//     //     // Placeholder for dynamic data based on filter (e.g., specific month or year)
//     //     // You can implement API call or data fetching here
//     // };
//     const handleFilterChange = (e) => {
//         const value = e.target.value;
//         setFilterValue(value);

//         // TODO: fetch hoặc lọc dữ liệu dựa trên giá trị filter
//         fetchData(value);
//     };


//     const options = {
//         responsive: true,
//         plugins: {
//             legend: { position: 'top' },
//             title: { display: false },
//         },
//         scales: {
//             // y: { beginAtZero: true, max: 500 },
//             y: { beginAtZero: true },
//         },
//     };

//     return (
//         <div className="w-[600px] mx-auto p-4">
//             <h3 className="text-lg font-bold">Thống kê</h3>
//             <p className="text-gray-100">Tổng số truyện</p>
//             <div className="flex justify-between items-center mt-4">
//                 {/* <div>
//                     <button onClick={() => handleFilterChange('MonthYear')} className={`mr-2 px-3 py-1 rounded ${filter === 'MonthYear' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}>
//                         {filter === 'MonthYear' ? 'Month/Year' : 'Year'}
//                     </button>
//                     <button onClick={() => handleFilterChange('Year')} className={`px-3 py-1 rounded ${filter === 'Year' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}>
//                         Year
//                     </button>
//                 </div> */}
//                 <div>
//                     <button onClick={() => handleModeChange('Monthly')} className={`mr-2 px-3 py-1 rounded ${mode === 'Monthly' ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}>
//                         Tháng
//                     </button>
//                     <button onClick={() => handleModeChange('Yearly')} className={`px-3 py-1 rounded ${mode === 'Yearly' ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}>
//                         Năm
//                     </button>
//                 </div>

//                 <div>
//                     {mode === 'Monthly' ? (
//                         <input
//                             type="month"
//                             value={filterValue}
//                             onChange={handleFilterChange}
//                             className="border px-2 py-1 rounded"
//                         />
//                     ) : (
//                         // <input
//                         //     type="number"
//                         //     min="2000"
//                         //     max={new Date().getFullYear()}
//                         //     placeholder="Chọn năm"
//                         //     value={filterValue}
//                         //     onChange={handleFilterChange}
//                         //     className="border px-2 py-1 rounded w-[120px]"
//                         // />
//                         <select
//                             value={filterValue}
//                             onChange={handleFilterChange}
//                             className="border px-2 py-1 rounded bg-gray-500"
//                         >
//                             {Array.from({ length: currentYear - 2010 }, (_, i) => 2011 + i).map((year) => (
//                                 <option key={year} value={year}>{year}</option>
//                             ))}
//                         </select>
//                     )}
//                 </div>
//             </div>
//             <Bar data={data} options={options} />
//         </div>
//     );
// };

// export default Analytics;


import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { getNovelStatistic } from '../../services/novelStatisticService';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Analytics = () => {
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');
  const defaultMonthValue = `${currentYear}-${currentMonth}`;

  const [mode, setMode] = useState('Monthly');
  const [filterValue, setFilterValue] = useState(defaultMonthValue);
  const [data, setData] = useState({
    labels: [],
    datasets: [],
  });

  const fetchData = async (value) => {
    let startDate = '';
    let endDate = '';
    let segmentType = '';
    let labels = [];
    let defaultTotals = [];

    if (mode === 'Monthly') {
      segmentType = 'DAY';
      const [year, month] = value.split('-');
      const start = new Date(year, month - 1, 1);
      const end = new Date(year, month, 0);
      const daysInMonth = end.getDate();

      labels = Array.from({ length: daysInMonth }, (_, i) => i + 1);
      defaultTotals = Array(daysInMonth).fill(0);

      startDate = start.toLocaleDateString('en-GB');
      endDate = end.toLocaleDateString('en-GB');
    } else {
      segmentType = 'MONTH';
      const year = parseInt(value);
      labels = Array.from({ length: 12 }, (_, i) => `Tháng ${i + 1}`);
      defaultTotals = Array(12).fill(0);

      const start = new Date(year, 0, 1);
      const end = new Date(year, 11, 31);

      startDate = start.toLocaleDateString('en-GB');
      endDate = end.toLocaleDateString('en-GB');
    }

    try {
      const result = await getNovelStatistic(startDate, endDate, segmentType);
      const dataMap = new Map();

      result.forEach((item) => {
        const date = new Date(item.startTime);
        const key = mode === 'Monthly' ? date.getDate() : date.getMonth() + 1;
        dataMap.set(key, item.total);
      });

      const totals = labels.map((_, index) => {
        const key = mode === 'Monthly' ? index + 1 : index + 1;
        return dataMap.get(key) || 0;
      });

      setData({
        labels,
        datasets: [
          {
            label: 'Số lượng truyện',
            data: totals,
            backgroundColor: '#4B5EAA',
          },
        ],
      });
    } catch (error) {
      console.error('Lỗi khi tải dữ liệu thống kê:', error);
      alert('Không thể tải dữ liệu thống kê.');
    }
  };

  const handleModeChange = (newMode) => {
    setMode(newMode);
    setFilterValue(newMode === 'Monthly' ? defaultMonthValue : currentYear.toString());
  };

  const handleFilterChange = (e) => {
    const value = e.target.value;
    setFilterValue(value);
  };

  const getLabelText = () => {
    if (mode === 'Monthly') {
      const [year, month] = filterValue.split('-');
      return `Tổng số truyện tháng ${parseInt(month, 10)}/${year}`;
    } else {
      return `Tổng số truyện năm ${filterValue}`;
    }
  };

  const options = {
    responsive: true,
    plugins: {
      legend: { position: 'top' },
      title: { display: false },
    },
    scales: {
      y: { beginAtZero: true },
    },
  };

  useEffect(() => {
    fetchData(filterValue);
  }, [mode, filterValue]);

  return (
    <div className="w-[600px] mx-auto p-4">
      <h3 className="text-lg font-bold">Thống kê</h3>
      <p className="text-gray-100">{getLabelText()}</p>

      <div className="flex justify-between items-center mt-4">
        <div>
          <button
            onClick={() => handleModeChange('Monthly')}
            className={`mr-2 px-3 py-1 rounded ${mode === 'Monthly' ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}
          >
            Tháng
          </button>
          <button
            onClick={() => handleModeChange('Yearly')}
            className={`px-3 py-1 rounded ${mode === 'Yearly' ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}
          >
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
            <select
              value={filterValue}
              onChange={handleFilterChange}
              className="border px-2 py-1 rounded bg-gray-500"
            >
              {Array.from({ length: currentYear - 2010 }, (_, i) => 2011 + i).map((year) => (
                <option key={year} value={year}>
                  {year}
                </option>
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
