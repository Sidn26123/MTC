import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import ChartContainer from './ChartContainer';

const BarChartStats = ({ title, data }) => {
    const chartRef = useRef(null);
    const chartInstance = useRef(null);

    useEffect(() => {
        const ctx = chartRef.current.getContext('2d');
        if (chartInstance.current) chartInstance.current.destroy();

        chartInstance.current = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.map(d => d.label || 'Không tên'),
                datasets: [{
                    label: title,
                    data: data.map(d => d.value),
                    backgroundColor: '#FF6384'
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });
    }, [data, title]);

    return (
        <ChartContainer>
            <h3 className="font-semibold mb-3">{title}</h3>
            <canvas ref={chartRef}></canvas>
        </ChartContainer>
    );
};

export default BarChartStats;
