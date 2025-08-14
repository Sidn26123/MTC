import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import ChartContainer from './ChartContainer';

const PieChartStats = ({ title, data }) => {
    const chartRef = useRef(null);
    const chartInstance = useRef(null);

    useEffect(() => {
        const ctx = chartRef.current.getContext('2d');
        if (chartInstance.current) chartInstance.current.destroy();

        chartInstance.current = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.map(d => d.label || 'Không tên'),
                datasets: [{
                    data: data.map(d => d.value),
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#8A2BE2']
                }]
            }
        });
    }, [data]);

    return (
        <ChartContainer>
            <h3 className="font-semibold mb-3">{title}</h3>
            <canvas ref={chartRef}></canvas>
        </ChartContainer>
    );
};

export default PieChartStats;
