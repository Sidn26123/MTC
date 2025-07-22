import React, { useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const DonutAnalystic = () => {
  const [hovered, setHovered] = useState(null);

  const data = {
    labels: ['Desktop', 'Mobile', 'Tablet', 'Other'],
    datasets: [
      {
        data: [55, 30, 10, 5],
        backgroundColor: ['#4B5EAA', '#6D9DC5', '#B2C9D6', '#E0E7F1'],
        borderWidth: 0,
        hoverOffset: 20,
      },
    ],
  };

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
      if (elements.length > 0) {
        const index = elements[0].index;
        const label = data.labels[index];
        const value = data.datasets[0].data[index];
        setHovered(`${label} ${value}`);
      } else {
        setHovered(null);
      }
    },
  };

  return (
    <div className="w-[300px] mx-auto relative">
      <h3 className="text-center font-semibold mb-4">Thống kê thiết bị</h3>

      <Doughnut data={data} options={options} />

      {hovered && (
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center pointer-events-none text-gray-700 font-semibold text-sm">
          {hovered}
        </div>
      )}
    </div>
  );
};

export default DonutAnalystic;





// import React, { useState } from 'react';
// import { Doughnut } from 'react-chartjs-2';
// import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// ChartJS.register(ArcElement, Tooltip, Legend);

// const DonutAnalystic = () => {
//   const [hovered, setHovered] = useState(null);

//   const data = {
//     labels: ['Desktop', 'Mobile', 'Tablet'],
//     datasets: [
//       {
//         data: [45, 30, 25],
//         backgroundColor: ['#4B5EAA', '#6D9DC5', '#B2C9D6'],
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
//       hover: {
//         onHover: (event, elements) => {
//           if (elements.length > 0) {
//             const index = elements[0].index;
//             setHovered(data.labels[index]);
//           } else {
//             setHovered(null);
//           }
//         },
//       },
//     },
//     cutout: '70%',
//   };

//   return (
//     <div className="w-[300px] mx-auto relative">
//       <h3 className="text-center font-semibold mb-4">Thống kê hình cầu</h3>
//       <Doughnut
//         data={data}
//         options={options}
//         onHover={(event, elements) => {
//           if (elements.length === 0) setHovered(null);
//         }}
//       />
//       {/* {hovered === 'Desktop' && (
//         <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center pointer-events-none text-gray-500">
//           <strong>Desktop</strong> {45}
//         </div>
//       )} */}
//     </div>
//   );


//   // return (
//   //   <div style={{ width: '300px', margin: '0 auto', position: 'relative' }}>
//   //     <h3>Thống kê hình cầu</h3>
//   //     <Doughnut
//   //       data={data}
//   //       options={options}
//   //       onHover={(event, elements) => {
//   //         if (elements.length === 0) setHovered(null);
//   //       }}
//   //     />
//   //     {hovered === 'Desktop' && (
//   //       <div
//   //         style={{
//   //           position: 'absolute',
//   //           top: '50%',
//   //           left: '50%',
//   //           transform: 'translate(-50%, -50%)',
//   //           textAlign: 'center',
//   //           pointerEvents: 'none',
//   //         }}
//   //       >
//   //         <strong>Desktop</strong> {45}
//   //       </div>
//   //     )}
//   //   </div>
//   // );
// };

// export default DonutAnalystic;