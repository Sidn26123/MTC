import React, { useRef, useState, useEffect } from "react";
import { Chart } from "chart.js/auto";

const ReusableLineChart = ({
                               title = "Biểu đồ thống kê",
                               fetchData,
                               defaultData = [],
                               years = [],
                               labels = [],
                               datasetLabel = "Dữ liệu",
                               color = "rgba(54, 162, 235, 1)"
                           }) => {
    const chartRef = useRef(null);
    const chartInstance = useRef(null);
    const [selectedYear, setSelectedYear] = useState(years[years.length - 1] || new Date().getFullYear());
    const [chartLabels, setChartLabels] = useState(labels);
    const [chartValues, setChartValues] = useState([]);

    const loadData = async (year) => {
        try {
            const res = await fetchData(year);

            // Nếu API trả về object { code, result }
            const dataArr = res?.result || res;

            if (Array.isArray(dataArr) && dataArr.length) {
                setChartLabels(dataArr.map(item => item.timeLabel));
                setChartValues(dataArr.map(item => item.commentCount));
            } else {
                setChartLabels(labels);
                setChartValues(defaultData);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
            setChartLabels(labels);
            setChartValues(defaultData);
        }
    };

    useEffect(() => {
        loadData(selectedYear);
    }, [selectedYear]);

    useEffect(() => {
        if (!chartLabels.length || !chartValues.length) return;
        const ctx = chartRef.current.getContext("2d");

        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        chartInstance.current = new Chart(ctx, {
            type: "line",
            data: {
                labels: chartLabels,
                datasets: [{
                    label: `${datasetLabel} ${selectedYear}`,
                    data: chartValues,
                    fill: true,
                    backgroundColor: color.replace("1)", "0.2)"),
                    borderColor: color,
                    borderWidth: 2,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    }, [chartLabels, chartValues, selectedYear, color, datasetLabel]);

    return (
        <div>
            <h2>{title}</h2>
            {years.length > 0 && (
                <>
                    <label htmlFor="yearSelect">Chọn năm: </label>
                    <select
                        className="bg-gray-200 text-gray-800 p-2 rounded"
                        id="yearSelect"
                        value={selectedYear}
                        onChange={(e) => setSelectedYear(Number(e.target.value))}
                    >
                        {years.map(year => (
                            <option key={year} value={year}>{year}</option>
                        ))}
                    </select>
                </>
            )}
            <canvas ref={chartRef} width="400" height="200"></canvas>
        </div>
    );
};


export default ReusableLineChart;
