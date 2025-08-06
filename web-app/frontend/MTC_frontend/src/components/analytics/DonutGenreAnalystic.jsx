import React, { useState, useEffect } from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { getNovelClassificationGenres } from '../../services/novelStatisticService';

ChartJS.register(ArcElement, Tooltip, Legend);

const DonutGenreAnalystic = () => {
  const [hovered, setHovered] = useState(null);
  const [chartData, setChartData] = useState(null);

  const colors = ['#4B5EAA', '#6D9DC5', '#B2C9D6', '#E0E7F1', '#CBD5E1', '#AFCBFF'];

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getNovelClassificationGenres();
        const rawData = response.data.result;

        // Sắp xếp theo total giảm dần
        const sorted = [...rawData].sort((a, b) => b.total - a.total);

        // Lấy top 3 (hoặc 4) loại
        const topItems = sorted.slice(0, 3);
        const otherItems = sorted.slice(3);

        // Gộp "other"
        const otherTotal = otherItems.reduce((sum, item) => sum + item.total, 0);

        const labels = topItems.map(item => item.name);
        const data = topItems.map(item => item.total);

        if (otherTotal > 0) {
          labels.push('Other');
          data.push(otherTotal);
        }

        setChartData({
          labels,
          datasets: [
            {
              data,
              backgroundColor: colors.slice(0, labels.length),
              borderWidth: 0,
              hoverOffset: 20,
            },
          ],
        });
      } catch (error) {
        console.error('Lỗi khi tải dữ liệu thể loại:', error);
      }
    };

    fetchData();
  }, []);

  const options = {
    plugins: {
      legend: {
        position: 'bottom',
      },
      tooltip: {
        enabled: false,
      },
    },
    cutout: '70%',
    onHover: (event, elements) => {
      if (!chartData) return;
      if (elements.length > 0) {
        const index = elements[0].index;
        const label = chartData.labels[index];
        const value = chartData.datasets[0].data[index];
        setHovered(`${label}: ${value}`);
      } else {
        setHovered(null);
      }
    },
  };

  return (
    <div className="w-[300px] mx-auto relative">
      <h3 className="text-center font-semibold mb-4">Thống kê truyện theo Thể loại</h3>

      {chartData ? (
        <>
          <Doughnut data={chartData} options={options} />
          {hovered && (
            <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center pointer-events-none text-gray-100 font-semibold text-sm">
              {hovered}
            </div>
          )}
        </>
      ) : (
        <div className="text-center text-gray-500">Đang tải dữ liệu...</div>
      )}
    </div>
  );
};

//   const data = {
//     labels: ['Desktop', 'Mobile', 'Tablet', 'Other'],
//     datasets: [
//       {
//         data: [55, 30, 10, 5],
//         backgroundColor: ['#4B5EAA', '#6D9DC5', '#B2C9D6', '#E0E7F1'],
//         borderWidth: 0,
//         hoverOffset: 20,
//       },
//     ],
//   };

//   const options = {
//     plugins: {
//       legend: {
//         position: 'bottom',
//       },
//       tooltip: {
//         enabled: false,
//       },
//     },
//     cutout: '70%',
//     onHover: (event, elements) => {
//       if (elements.length > 0) {
//         const index = elements[0].index;
//         const label = data.labels[index];
//         const value = data.datasets[0].data[index];
//         setHovered(`${label} ${value}`);
//       } else {
//         setHovered(null);
//       }
//     },
//   };

//   return (
//     <div className="w-[300px] mx-auto relative">
//       <h3 className="text-center font-semibold mb-4">Thống kê thiết bị</h3>

//       <Doughnut data={data} options={options} />

//       {hovered && (
//         <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center pointer-events-none text-gray-700 font-semibold text-sm">
//           {hovered}
//         </div>
//       )}
//     </div>
//   );
// };

export default DonutGenreAnalystic;




