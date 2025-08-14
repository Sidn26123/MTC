// import React, { useEffect, useState } from 'react';
// import { DefaultNavigator } from '../../components/global/Navigators.jsx';
// import { OverviewStats } from '../../components/analytics/OverviewStats.jsx';
// import { DetailedStats } from '../../components/analytics/DetailStats.jsx';
// import { fetchCommentsStats, getSingleNovelCommentsTimeline } from '../../services/publisherService.js';
// import { QuickStats } from '../../components/analytics/QuickStats.jsx';
// import ChartToolbar from '../../components/analytics/ChartToolbar.jsx';
// import ChartContainer from '../../components/analytics/ChartContainer.jsx';
// import PieChartStats from '../../components/analytics/PieChartStats.jsx';
// import LineChartStats from '../../components/analytics/LineChartStats.jsx';
// import BarChartStats from '../../components/analytics/BarChartStats.jsx';
// import HeatmapStats from '../../components/analytics/HeatMapStats.jsx';
// import WordCloudStats from '../../components/analytics/WordCloudStats.jsx';
// import LineChart from '../../components/analytics/LineChart.jsx';
// import ReusableLineChart from '../../components/analytics/LineChartStats.jsx';
//
// function AnalyticsNovelPage() {
//     const [stats, setStats] = useState(null);
//     const [filters, setFilters] = useState({
//         from: '2025-06-01',
//         to: '2025-08-31',
//         novelIds: ["0da33320-83fa-42ca-bc8c-b2b31eba8918"]
//     });
//
//     const getData = async () => {
//         const data = await fetchCommentsStats(filters);
//         setStats(data);
//     };
//
//     useEffect(() => {
//         getData();
//     }, [filters]);
//
//     if (!stats) return <div>Đang tải dữ liệu...</div>;
//
//     return (
//         <>
//             <div className="min-h-screen border-1 border-gray-500 border-rounded">
//
//                 <div className="container mx-auto px-4 py-8 space-y-8">
//                     <OverviewStats />
//                     <QuickStats />
//                     {/*<DetailedStats />*/}
//
//                 </div>
//                 <div className="flex flex-row gap-4">
//                     <div className="w-1/2">
//                         <ReusableLineChart
//                             title="Biểu đồ comment theo ngày"
//                             fetchData={async (year) => {
//                                 return await getSingleNovelCommentsTimeline(
//                                     "0da33320-83fa-42ca-bc8c-b2b31eba8918",
//                                     {
//                                         year,
//                                         startDate: "2025-06-01",
//                                         endDate: "2025-06-30",
//                                         timeRange: "DAILY"
//                                     }
//                                 );
//                             }}
//                             defaultData={[0, 0, 0, 0]}
//                             years={[2023, 2024, 2025]}
//                             datasetLabel="Số comment"
//                             color="rgba(255, 99, 132, 1)"
//                         />
//                     </div>
//
//                     <div className="w-1/2">
//                         <ReusableLineChart
//                             title="Biểu đồ comment theo ngày"
//                             fetchData={async (year) => {
//                                 return await getSingleNovelCommentsTimeline(
//                                     "0da33320-83fa-42ca-bc8c-b2b31eba8918",
//                                     {
//                                         year,
//                                         startDate: "2025-06-01",
//                                         endDate: "2025-06-30",
//                                         timeRange: "DAILY"
//                                     }
//                                 );
//                             }}
//                             defaultData={[0, 0, 0, 0]}
//                             years={[2023, 2024, 2025]}
//                             datasetLabel="Số comment"
//                             color="rgba(255, 99, 132, 1)"
//                         />
//                     </div>
//                 </div>
//
//             </div>
//         </>
//     );
// }
//
// export default AnalyticsNovelPage;

import React, { useEffect, useState, useRef } from 'react';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { fetchCommentsStats, getSingleNovelCommentsTimeline } from '../../services/publisherService.js';
import ReusableLineChart from '../../components/analytics/LineChartStats.jsx';
import { OverviewStats } from '../../components/analytics/OverviewStats.jsx';
import { QuickStats } from '../../components/analytics/QuickStats.jsx';
function AnalyticsNovelPage() {
    const [stats, setStats] = useState(null);
    const reportRef = useRef(null); // ref để chụp ảnh toàn bộ báo cáo

    const [filters, setFilters] = useState({
        from: '2025-06-01',
        to: '2025-08-31',
        novelIds: ['0da33320-83fa-42ca-bc8c-b2b31eba8918']
    });

    const getData = async () => {
        const data = await fetchCommentsStats(filters);
        setStats(data);
    };

    useEffect(() => {
        getData();
    }, [filters]);

    const handleDownloadPDF = async () => {
        // const element = reportRef.current;
        // if (!element) return;
        //
        // // Clone element để không phá vỡ DOM gốc
        // const clone = element.cloneNode(true);
        //
        // // Replace tất cả màu OKLCH sang RGB/HSL
        // clone.querySelectorAll("*").forEach(el => {
        //     const style = getComputedStyle(el);
        //
        //     // backgroundColor
        //     if (style.backgroundColor.startsWith("oklch")) {
        //         // map tạm thời sang trắng
        //         el.style.backgroundColor = "rgb(255, 255, 255)";
        //     }
        //
        //     // text color
        //     if (style.color.startsWith("oklch")) {
        //         el.style.color = "rgb(0, 0, 0)";
        //     }
        //
        //     // Ẩn tất cả iframe
        //     if (el.tagName.toLowerCase() === "iframe") {
        //         el.style.display = "none";
        //     }
        // });
        //
        // // Chụp màn hình phần báo cáo
        // const canvas = await html2canvas(clone, { scale: 2 });
        // const imgData = canvas.toDataURL('image/png');
        //
        // // Tạo file PDF
        // const pdf = new jsPDF('p', 'mm', 'a4');
        // const pageWidth = pdf.internal.pageSize.getWidth();
        // const pageHeight = pdf.internal.pageSize.getHeight();
        //
        // // Tính toán kích thước ảnh cho vừa trang PDF
        // const imgProps = pdf.getImageProperties(imgData);
        // const imgWidth = pageWidth;
        // const imgHeight = (imgProps.height * imgWidth) / imgProps.width;
        //
        // pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        // pdf.save('bao-cao-thong-ke.pdf');
        handleRenderPDF(reportData);

    };



    /**
     * Render trực tiếp báo cáo PDF (không dùng html2canvas)
     * @param {Object} reportData
     *  {
     *    totalComments: number,
     *    commentsByNovel: [{ label: string, value: number }],
     *    commentsOverTime: [{ time: string, count: number }]
     *  }
     */
    const handleRenderPDF = (reportData) => {
        const pdf = new jsPDF('p', 'mm', 'a4');
        const pageWidth = pdf.internal.pageSize.getWidth();

        // --- Tiêu đề ---
        pdf.setFontSize(18);
        pdf.text("BÁO CÁO THỐNG KÊ", pageWidth / 2, 20, { align: "center" });

        // --- Ngày báo cáo ---
        pdf.setFontSize(12);
        pdf.text(`Ngày: ${new Date().toLocaleDateString()}`, 14, 30);

        // --- Thống kê tổng ---
        pdf.setFontSize(14);
        pdf.text(`Tổng comment: ${reportData.totalComments}`, 14, 40);

        // --- Biểu đồ (nếu có) ---
        // Nếu bạn có chart dưới dạng canvas, có thể convert sang image và add vào PDF
        // Ví dụ: imgData = canvas.toDataURL('image/png');
        // pdf.addImage(imgData, 'PNG', 14, 50, 180, 80);


        // --- Lưu PDF ---
        pdf.save("bao-cao-thong-ke.pdf");
    };
    const reportData = {
        totalComments: 51,
        commentsByNovel: [
            { label: "Truyện A", value: 20 },
            { label: "Truyện B", value: 31 },
        ],
        commentsOverTime: [
            { time: "2025-06-09", count: 17 },
            { time: "2025-06-10", count: 34 },
        ]
    };


    if (!stats) return <div>Đang tải dữ liệu...</div>;

    return (
        <>
            <div className="min-h-screen border border-gray-500 rounded p-4">
                {/* Nút tải PDF */}
                <div className="flex justify-end mb-4">
                    <button
                        onClick={handleDownloadPDF}
                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                    >
                        📄 Tải báo cáo PDF
                    </button>
                </div>

                {/* Nội dung báo cáo */}
                <div ref={reportRef}>
                    <div className="container mx-auto px-4 py-8 space-y-8">
                        <OverviewStats />
                        <QuickStats />
                    </div>

                    <div className="flex flex-row gap-4">
                        <div className="w-1/2">
                            <ReusableLineChart
                                title="Biểu đồ comment theo ngày"
                                fetchData={async (year) => {
                                    return await getSingleNovelCommentsTimeline(
                                        '0da33320-83fa-42ca-bc8c-b2b31eba8918',
                                        {
                                            year,
                                            startDate: '2025-06-01',
                                            endDate: '2025-06-30',
                                            timeRange: 'DAILY'
                                        }
                                    );
                                }}
                                defaultData={[0, 0, 0, 0]}
                                years={[2023, 2024, 2025]}
                                datasetLabel="Số comment"
                                color="rgba(255, 99, 132, 1)"
                            />
                        </div>

                        <div className="w-1/2">
                            <ReusableLineChart
                                title="Biểu đồ comment theo ngày"
                                fetchData={async (year) => {
                                    return await getSingleNovelCommentsTimeline(
                                        '0da33320-83fa-42ca-bc8c-b2b31eba8918',
                                        {
                                            year,
                                            startDate: '2025-06-01',
                                            endDate: '2025-06-30',
                                            timeRange: 'DAILY'
                                        }
                                    );
                                }}
                                defaultData={[0, 0, 0, 0]}
                                years={[2023, 2024, 2025]}
                                datasetLabel="Số comment"
                                color="rgba(255, 99, 132, 1)"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default AnalyticsNovelPage;
