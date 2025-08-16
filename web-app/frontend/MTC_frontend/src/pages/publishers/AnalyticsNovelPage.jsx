import React, { useEffect, useState, useRef } from 'react';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { fetchCommentsStats, getSingleNovelCommentsTimeline } from '../../services/publisherService.js';
import ReusableLineChart from '../../components/analytics/LineChartStats.jsx';
import { OverviewStats } from '../../components/analytics/OverviewStats.jsx';
import { QuickStats } from '../../components/analytics/QuickStats.jsx';
import { filterComment, filterRating } from '../../services/feedbackService.js';
import CommonTable from '../../components/common/CommonTable.jsx';
import { useCurrentChosenNovelRatings, useSetCurrentChosenNovelRatings } from '../../stores/publisherStore.js';
import { Star, ThumbsUp, ThumbsDown, MessageCircle, Calendar } from 'lucide-react';



function AnalyticsNovelPage() {
    const [stats, setStats] = useState(null);
    const reportRef = useRef(null); // ref để chụp ảnh toàn bộ báo cáo
    const currentChoseNovelRatings = useCurrentChosenNovelRatings();
    const setCurrentChoseNovelRatings = useSetCurrentChosenNovelRatings();
    const [filters, setFilters] = useState({
        from: '2025-06-01',
        to: '2025-08-31',
        novelIds: ['0da33320-83fa-42ca-bc8c-b2b31eba8918']
    });

    const [ratingFilter, setRatingFilter] = useState({
        'rateMin': 1.0
    })

    const getData = async () => {
        const data = await fetchCommentsStats(filters);
        setStats(data);
    };

    const getRatings = async () => {
        const data = await filterRating(ratingFilter)
        setCurrentChoseNovelRatings(data.data.result || []);
    }

    const handleChangePage = (page) => {
        setRatingFilter({
            ...ratingFilter,
            page: page,
        })
    };

    const handlePageSizeChange = (newSize) => {

    };

    const handleSearch = (query) => {
    };

    const handleSort = (sortBy, sortOrder) => {
    };



    const renderRow = (rating, index) => {
        const formatDate = (dateString) => {
            if (!dateString) return '—';
            return new Date(dateString).toLocaleDateString('vi-VN', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
        };

        const renderStars = (rate) => {
            const safeRate = Number(rate) || 0;
            const stars = [];
            const fullStars = Math.floor(safeRate);
            const hasHalfStar = safeRate % 1 !== 0;

            for (let i = 0; i < 5; i++) {
                if (i < fullStars) {
                    stars.push(
                        <Star key={i} className="w-4 h-4 fill-amber-400 text-amber-400" />
                    );
                } else if (i === fullStars && hasHalfStar) {
                    stars.push(
                        <div key={i} className="relative w-4 h-4">
                            <Star className="absolute w-4 h-4 text-gray-300" />
                            <div className="absolute overflow-hidden w-2">
                                <Star className="w-4 h-4 fill-amber-400 text-amber-400" />
                            </div>
                        </div>
                    );
                } else {
                    stars.push(
                        <Star key={i} className="w-4 h-4 text-gray-300" />
                    );
                }
            }
            return stars;
        };

        return (
            <tr
                key={String(rating.id || index)}
                className="border-b border-gray-100 hover:bg-slate-50/50 transition-all duration-200"
            >
                {/* STT */}
                <td className="px-6 py-4">{index + 1}</td>

                {/* User Info */}
                <td className="px-6 py-4">
                    <div className="flex items-center gap-3">
                        <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white font-medium text-sm">
                            {String(rating.ratedBy || 'U').slice(0, 2).toUpperCase()}
                        </div>
                        <div className="flex flex-col">
                            <span className="text-sm font-medium text-gray-900">User</span>
                            <span className="text-xs text-gray-500 flex items-center gap-1">
                            <Calendar className="w-3 h-3" />
                                {formatDate(rating.createdAt)}
                        </span>
                        </div>
                    </div>
                </td>

                {/* Rating */}
                <td className="px-6 py-4">
                    <div className="flex items-center gap-2">
                        <div className="flex gap-0.5">
                            {renderStars(rating.rate)}
                        </div>
                        <span className="text-sm font-semibold text-gray-700 bg-amber-50 px-2 py-0.5 rounded-full">
                        {Number(rating.rate || 0)}/5
                    </span>
                    </div>
                </td>

                {/* Engagement */}
                <td className="px-6 py-4">
                    <div className="flex items-center gap-4">
                        <div className="flex items-center gap-1.5 text-green-600">
                            <ThumbsUp className="w-4 h-4" />
                            <span>{Number(rating.totalLikes || 0)}</span>
                        </div>
                        <div className="flex items-center gap-1.5 text-red-500">
                            <ThumbsDown className="w-4 h-4" />
                            <span>{Number(rating.totalDislikes || 0)}</span>
                        </div>
                        {Number(rating.totalReplies) > 0 && (
                            <div className="flex items-center gap-1.5 text-blue-600">
                                <MessageCircle className="w-4 h-4" />
                                <span>{Number(rating.totalReplies)}</span>
                            </div>
                        )}
                    </div>
                </td>

                {/* Status */}
                <td className="px-6 py-4">
                    {rating.isHidden ? (
                        <span className="badge bg-yellow-100 text-yellow-800">Ẩn</span>
                    ) : rating.isDeleted ? (
                        <span className="badge bg-red-100 text-red-800">Đã xóa</span>
                    ) : (
                        <span className="badge bg-green-100 text-green-800">Hoạt động</span>
                    )}
                </td>
            </tr>
        );
    };



    useEffect(() => {
        getData();
    }, [filters]);

    useEffect(() => {
        getRatings();
    }, [ratingFilter]);

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
                    <CommentListWithToolbar />
                    {currentChoseNovelRatings  &&(
                        <CommonTable
                            data={currentChoseNovelRatings.data}
                            headers={[
                                { label: "STT" },
                                { label: "THÔNG TIN" },
                                { label: "ĐÁNH GIÁ" },
                                { label: "THÔNG TIN" },
                                { label: "TRẠNG THÁI" },

                            ]}
                            renderRow={renderRow}
                            currentPage={currentChoseNovelRatings.currentPage}
                            pageSize={currentChoseNovelRatings.pageSize}
                            totalPages={currentChoseNovelRatings.totalPages}
                            totalElements={currentChoseNovelRatings.totalElements}
                            onPageChange={handleChangePage}
                            onPageSizeChange={handlePageSizeChange}
                            onSearch={handleSearch}
                        />
                    )}
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

const CommentListWithToolbar = () => {
    const [comments, setComments] = useState([]);
    const [sortBy, setSortBy] = useState("createdAt"); // LATEST | MOST_REPLIES
    const [sortDirection, setSortDirection] = useState("DESC"); // ASC | DESC
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    const fetchComments = async () => {
        try {
            const res = await filterComment({
                page: page,
                size:  10,
                sortBy: sortBy,
                sortDirection: sortDirection,
                novelId: "0da33320-83fa-42ca-bc8c-b2b31eba8918"
            });
            if (res.data?.result) {
                setComments(res.data.result.data || []);
                setTotalPages(res.data.result.totalPages);
            }
        } catch (err) {
            console.error("Lỗi fetch comments:", err);
        }
    };

    useEffect(() => {
        fetchComments();
    }, [sortBy, sortDirection, page]);

    return (
        <div className="border rounded p-4 space-y-4">
            {/* Toolbar */}
            <div className="flex items-center justify-between">
                <div className="flex gap-2">
                    <select
                        value={sortBy}
                        onChange={(e) => setSortBy(e.target.value)}
                        className="border rounded px-2 py-1 bg-gray-400"
                    >
                        <option value="createdAt">Thời gian comment</option>
                        <option value="totalReplies">Tổng trả lời</option>
                    </select>

                    <select
                        value={sortDirection}
                        onChange={(e) => setSortDirection(e.target.value)}
                        className="border rounded px-2 py-1"
                    >
                        <option value="DESC">Giảm dần</option>
                        <option value="ASC">Tăng dần</option>
                    </select>
                </div>
            </div>

            {/* Danh sách comment */}
            <ul className="divide-y divide-gray-300">
                {comments.length === 0 ? (
                    <li className="py-4 text-gray-500 text-center">
                        Không có comment nào
                    </li>
                ) : (
                    comments.map((c) => (
                        <li key={c.id} className="py-3">
                            <div className="flex justify-between">
                                <div>
                                    <p className="font-medium">{c.content}</p>
                                    <p className="text-xs text-gray-500">
                                        {new Date(c.createdAt).toLocaleString()}
                                    </p>
                                </div>
                                <div className="text-sm text-gray-600">
                                    {c.totalReplies} replies
                                </div>
                            </div>
                        </li>
                    ))
                )}
            </ul>

            {/* Pagination */}
            <div className="flex justify-center items-center gap-2 mt-4">
                <button
                    disabled={page <= 1}
                    onClick={() => setPage((p) => p - 1)}
                    className="px-3 py-1 border rounded disabled:opacity-50"
                >
                    Prev
                </button>
                <span>
          {page} / {totalPages}
        </span>
                <button
                    disabled={page >= totalPages}
                    onClick={() => setPage((p) => p + 1)}
                    className="px-3 py-1 border rounded disabled:opacity-50"
                >
                    Next
                </button>
            </div>
        </div>
    );
};


export default AnalyticsNovelPage;
