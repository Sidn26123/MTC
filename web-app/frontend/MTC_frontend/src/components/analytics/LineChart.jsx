import React, { useEffect, useRef, useState } from 'react';
import Chart from 'chart.js/auto';
import { RevueService } from '../../services/analysticService.js';

const LineChart = () => {
  const chartRef = useRef(null);
  const chartInstance = useRef(null);
  const [selectedYear, setSelectedYear] = useState(2025);
  const [chartData, setChartData] = useState([]);

  const fetchData = async (year) => {
    try {
      const data = await RevueService(year);
      // Giả sử API trả về [1, 2, 3, ..., 12]
      console.log("Dữ liệu từ API:", data);
      setChartData(data.data);
    } catch (error) {
      console.error("Error fetching data:", error);
      // Nếu lỗi thì set dữ liệu mặc định
      setChartData([32.0, 32.5, 33.0, 33.5, 34.0, 34.5, 35.0, 0, 0, 36.5, 37.0, 37.5]);
    }
  };

  useEffect(() => {
    fetchData(selectedYear);
  }, [selectedYear]);

  useEffect(() => {
    if (!chartData.length) return;

    const ctx = chartRef.current.getContext('2d');

    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    chartInstance.current = new Chart(ctx, {
      type: 'line',
      data: {
        labels: [
          'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
          'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
        ],
        datasets: [{
          label: `Doanh thu theo tháng năm ${selectedYear}`,
          data: chartData,
          fill: true,
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 2,
          tension: 0.1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }, [chartData, selectedYear]);

  const years = Array.from({ length: 2025 - 2020 + 1 }, (_, i) => 2020 + i);

  return (
    <div>
      <h2>Thống kê doanh thu</h2>
      <label htmlFor="yearSelect">Chọn năm: </label>
      <select
        className='bg-gray-200 text-gray-800 p-2 rounded'
        id="yearSelect"
        value={selectedYear}
        onChange={(e) => setSelectedYear(Number(e.target.value))}
      >
        {years.map(year => (
          <option key={year} value={year}>{year}</option>
        ))}
      </select>

      <canvas ref={chartRef} width="400" height="200"></canvas>
    </div>
  );
};

export default LineChart;





// import React, { useEffect, useRef, useState } from 'react';
// import Chart from 'chart.js/auto';
// import { RevueService } from '../../services/analysticService.js'; // Import dịch vụ để lấy dữ liệu

// const LineChart = () => {
//   const chartRef = useRef(null);
//   const chartInstance = useRef(null);
//   const [selectedYear, setSelectedYear] = useState(2025); // Năm mặc định là 2025

//   // Dữ liệu mẫu cho từng năm (bạn có thể thay đổi)
//   const getMonthlyData = (year) => {
//     const baseData = [32.0, 32.5, 33.0, 33.5, 34.0, 34.5, 35.0, 0, 0, 36.5, 37.0, 37.5];
//     // Điều chỉnh dữ liệu dựa trên năm (chỉ là ví dụ, bạn có thể tùy chỉnh logic)
//     const offset = (2025 - year) * 0.5; // Tạo sự khác biệt nhỏ giữa các năm
//     return baseData.map(value => value - offset);
//   };

//   const fetchData = async () => {
//     try {
//       const data = await RevueService(selectedYear);

//      const dataMap = new Map();

//       // Xử lý dữ liệu nếu cần, ví dụ: chuyển đổi định dạng
//       return data.map(item => item.value); // Giả sử dữ liệu trả về là mảng các giá trị
//     } catch (error) {
//       console.error("Error fetching data:", error);
//       return getMonthlyData(selectedYear); // Trả về dữ liệu mẫu nếu có lỗi
//     }
//   }


//   useEffect(() => {
//     const ctx = chartRef.current.getContext('2d');

//     // Hủy biểu đồ cũ nếu đã tồn tại
//     if (chartInstance.current) {
//       chartInstance.current.destroy();
//     }

//     // Tạo biểu đồ mới
//     chartInstance.current = new Chart(ctx, {
//       type: 'line',
//       data: {
//         labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7',
//              'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
//         datasets: [{
//           label: `Doanh thu theo tháng năm: ${selectedYear}`,
//           data: getMonthlyData(selectedYear),
//           fill: true,
//           backgroundColor: 'rgba(54, 162, 235, 0.2)',
//           borderColor: 'rgba(54, 162, 235, 1)',
//           borderWidth: 2,
//           tension: 0.1
//         }]
//       },
//       options: {
//         scales: {
//           y: {
//             beginAtZero: false
//           }
//         }
//       }
//     });

//     // Dọn dẹp khi component unmount
//     return () => {
//       if (chartInstance.current) {
//         chartInstance.current.destroy();
//       }
//     };
//   }, [selectedYear]); // Cập nhật lại khi selectedYear thay đổi

//   // Tạo danh sách năm từ 2010 đến hiện tại (2025)
//   const years = Array.from({ length: 2025 - 2010 + 1 }, (_, i) => 2010 + i);

//   return (
//     <div>
//       <div>
//         <h2>Thông kê doanh thu</h2>
//         <label htmlFor="yearSelect">Chọn năm: </label>
//         <select
//           className='bg-gray-200 text-gray-800 p-2 rounded'
//           id="yearSelect"
//           value={selectedYear}
//           onChange={(e) => setSelectedYear(Number(e.target.value))}
//         >
//           {years.map(year => (
//             <option key={year} value={year}>{year}</option>
//           ))}
//         </select>
//       </div>
//       <canvas ref={chartRef} width="400" height="200"></canvas>
//     </div>
//   );
// };

// export default LineChart;