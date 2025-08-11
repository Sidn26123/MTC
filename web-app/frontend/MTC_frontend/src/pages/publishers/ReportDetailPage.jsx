import React, { useEffect, useState } from 'react';

// function ReportDetailPage() {
//     const [showRequestDetail, setShowRequestDetail] = React.useState(false);
//
//     return (
//         <>
//             <div>
//                 <div className={"flex flex-col gap-x-5 mt-3 rounded-md bg-background-light p-3"}>
//                     <div className={"flex flex-col"}>
//                         <h3>Bao cao loi</h3>
//                         <span>Đây là các báo cáo lỗi bạn cần xử lý, ấn vào báo cáo để xem chi tiết.</span>
//                     </div>
//                     {!showRequestDetail ? (
//                         <div className={"mt-4"}>
//                             {/*<ReportHandleItem />*/}
//                             <table className="w-full text-sm text-left ">
//                                 <thead
//                                     className="text-sm text-gray-500">
//                                 <tr>
//
//                                     <th scope="col" className="px-6 py-3">
//                                         Thông tin
//                                     </th>
//                                     <th scope="col" className="px-6 py-3">
//                                         TEN CHUONG
//                                     </th>
//                                     <th scope="col" className="px-6 py-3">
//                                         XUAT BAN LUC
//                                     </th>
//
//                                 </tr>
//                                 </thead>
//                                 <tbody>
//                                 <tr className="border-gray-500 hover:bg-gray-500/10 hover:cursor-pointer">
//
//                                     <th scope="row"
//                                         className="px-6 py-4 w-3/12 font-medium text-gray-900 whitespace-nowrap dark:text-white">
//                                         <GeneralDetail />
//                                     </th>
//                                     <td className="px-6 py-4">
//                                         <ReportContent />
//                                     </td>
//                                     <td className="px-6 py-4">
//                                         <RequestState />
//                                     </td>
//                                 </tr>
//
//                                 </tbody>
//                             </table>
//
//                         </div>
//                     ) : (
//                         <div className={'mt-4'}>
//
//                         </div>
//                     )}
//
//                 </div>
//             </div>
//         </>
//     );
// }

import { Search, ChevronDown, Lock, Image, Send, Paperclip } from "lucide-react";
import { useParams } from 'react-router';
import {
    commentOnReport,
    getCommentListOfReport,
    getReportById,
    sendComment,
    updateReportStatus,
} from '../../services/feedbackService.js';
import { ExpandableText } from '../../common/CommonComponents.jsx';
import { getProfileById } from '../../services/userService.js';
import { useUser } from '../../stores/userStores.js';
import { getFullPathOfAvatar } from '../../utils/ProfileUtils.js';
import { formatPublishDateTime } from '../../utils/DatetimeUtil.js';
import CommentInReportItem from '../../components/feedbacks/CommentInReport.jsx';

function getNameReportStatus(status){
    const dataStatus = {
        CLOSED: "Đóng",
        PENDING: "Đang xử lý",
        OPEN: "Mở",
    }

    return dataStatus[status] || "Không xác định";
}


function ReportDetailPage() {
    const [searchTerm, setSearchTerm] = useState("");
    const [commentText, setCommentText] = useState("");
    const [reportData, setReportData] = useState(null);
    const [handlerProfile, setHandlerProfile] = useState(null);
    const [requesterProfile, setRequesterProfile] = useState(null);
    const [commentList, setCommentList] = useState([]);
    const [isReply, setIsReply] = useState(false);
    const [replyToUsername, setReplyToUsername] = useState("");
    const user = useUser();
    const { reportId } = useParams();

    useEffect(() => {
        getReportById(reportId).then(
            (response) => {
                console.log("Report details:", response.data);
                getProfileById(response.data.result.reporterId).then(r => {
                    setRequesterProfile(r.data.result);
                })
                setReportData(response.data.result);
                if (response.data.result.assignedTo) {
                    getProfileById(response.data.result.assignedTo).then(r => {
                        console.log("Handler profile:", r.data);
                        setHandlerProfile(r.data.result);
                    });

                }
            },

        )
        getCommentListOfReport(reportId).then(
            (response) => {
                console.log("Comments:", response.data);
                setCommentList(response.data.result);
            }
        )
    }, [reportId]);

    useEffect(() => {

    }, [commentList]);

    function handleSendComment(){
        if (!commentText.trim()) {
            return; // Không gửi nếu không có nội dung
        }
        // Gọi API gửi bình luận ở đây

        commentOnReport(
            {
                reportId: reportId,
                feedbackType: "REPORT",
                content: commentText,
                commenterRole: getCommenterRole(),
            }
        ).then(r => {
            // console.log("Comment sent successfully:", r.data);
            // commentList.data.unshift(r.data.result);
            // commentList.totalElements += 1;
            getCommentListOfReport(reportId).then(response => {
                console.log("Updated comments:", response.data);
                setCommentList(response.data.result || []);
            });
            setCommentText("");

        })
        // Sau khi gửi thành công, có thể cập nhật lại danh sách bình luận
        setCommentText(""); // Xóa nội dung bình luận sau khi gửi
    }

    function getCommenterRole(){
        if (user?.username === requesterProfile?.username) {
            return "REPORTER";
        }
        if (user?.username === handlerProfile?.username) {
            return "PUBLISHER";
        }
        else if (handlerProfile?.username){
            return "ADMIN";
        }
    }

    function handleCloseReport() {
        updateReportStatus(reportId, {
            status: "CLOSED"
        }).then(
            (response) => {
                console.log("Report closed successfully:", response.data);
                // Cập nhật trạng thái của reportData
                setReportData((prevData) => ({
                    ...prevData,
                    status: "CLOSED",
                }));
            },
            (error) => {
                console.error("Error closing report:", error);
            }
        );
    }

    function handleReopenReport() {
        updateReportStatus(reportId, {
            status: "PENDING"
        }).then(
            (response) => {
                console.log("Report reopened successfully:", response.data);
                // Cập nhật trạng thái của reportData
                setReportData((prevData) => ({
                    ...prevData,
                    status: "PENDING",
                }));
            },
            (error) => {
                console.error("Error reopening report:", error);
            }
        );
    }

    function handleChangeReportStatus(){
        if (reportData.status === "CLOSED") {
            handleReopenReport();
        } else {
            handleCloseReport();
        }
    }

    function onReply(username){
        console.log(username);
        setReplyToUsername(username);
        setIsReply(!isReply);
        if (isReply) {
            setCommentText(`@${username} `); // Thêm @username vào ô nhập bình luận
        }
    }

    return (
        <div className="min-h-screen bg-gray-900 text-gray-100">
            {/* Header */}
            <div className="bg-gray-800 px-6 py-8">
                <div className="max-w-7xl mx-auto">
                    <h1 className="text-2xl font-semibold text-white mb-2">
                        Yêu cầu & Báo cáo
                    </h1>
                    <p className="text-gray-400">
                        Nơi tạo yêu cầu và xử lý các vấn đề liên quan tới
                        truyện.
                    </p>
                </div>
            </div>

            {/* Controls Bar */}
            <div className="bg-gray-800 px-6 py-4 border-t border-gray-700">
                <div className="max-w-7xl mx-auto flex items-center justify-between">
                    <button className="bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-md font-medium transition-colors">
                        Tạo Mới
                    </button>

                    {/*Trang chi tiết mở comment ra*/}
                    {/*<div className="flex items-center gap-4">*/}
                    {/*    <div className="relative">*/}
                    {/*        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />*/}
                    {/*        <input*/}
                    {/*            type="text"*/}
                    {/*            placeholder="Tên truyện"*/}
                    {/*            value={searchTerm}*/}
                    {/*            onChange={(e) => setSearchTerm(e.target.value)}*/}
                    {/*            className="bg-gray-700 border border-gray-600 rounded-md pl-10 pr-4 py-2 text-gray-100 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent w-64"*/}
                    {/*        />*/}
                    {/*    </div>*/}

                    {/*    <div className="relative">*/}
                    {/*        <select className="bg-gray-700 border border-gray-600 rounded-md px-4 py-2 text-gray-100 focus:outline-none focus:ring-2 focus:ring-orange-500 appearance-none pr-10">*/}
                    {/*            <option>Yêu cầu đã đóng</option>*/}
                    {/*            <option>Yêu cầu mở</option>*/}
                    {/*            <option>Tất cả yêu cầu</option>*/}
                    {/*        </select>*/}
                    {/*        <ChevronDown className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4 pointer-events-none" />*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                </div>
            </div>

            <div className="max-w-7xl mx-auto flex gap-6 p-6">
                {/* Sidebar */}
                <div className="w-80 space-y-4">
                    <div className="bg-gray-800 rounded-lg">
                        <div className="p-4 border-b border-gray-700">
                            <h3 className="font-semibold text-gray-200">
                                TIÊU ĐỀ
                            </h3>
                        </div>
                        <div className="p-4">
                            <div className="bg-gray-700 rounded-md p-3">
                                {reportData && (
                                    <h4 className="text-orange-400 font-medium mb-1">
                                        {reportData.title}
                                    </h4>
                                )}
                                {reportData && (
                                    <p className="text-gray-400 text-sm">
                                        {reportData.description ? (
                                            <ExpandableText
                                                text={reportData.description}
                                                limit={120}
                                            />
                                        ) : (
                                            'Không có mô tả'
                                        )}
                                    </p>
                                )}
                            </div>
                        </div>
                    </div>

                    <div className="bg-gray-800 rounded-lg">
                        <div className="p-4 border-b border-gray-700">
                            <h3 className="font-semibold text-gray-200">
                                TÌNH TRẠNG
                            </h3>
                        </div>
                        <div className="p-4">
                            <div className="bg-orange-600 text-white text-center py-2 rounded-md text-sm font-medium">
                                {reportData ? reportData.status : 'Đang xử lý'}
                            </div>
                        </div>
                    </div>
                </div>

                {/* Main Content */}
                <div className="flex-1 bg-gray-800 rounded-lg">
                    {/* Post Header */}
                    <div className="p-6 border-b border-gray-700">
                        <span className="text-gray-400 text-sm">
                            (ID: {reportId})
                        </span>
                        <div className="flex items-center justify-between mb-4">
                            <div className="flex items-center gap-3">
                                <img
                                    src={getFullPathOfAvatar(
                                        requesterProfile?.avatarPath
                                    )}
                                    alt="Sidnn avatar"
                                    className="w-10 h-10 rounded-full"
                                />
                                <div>
                                    <div className="flex items-center gap-2">
                                        <span className="font-semibold text-white">
                                            {requesterProfile?.email}
                                        </span>
                                    </div>
                                    <div className="text-gray-400 text-sm">
                                        {reportData
                                            ? formatPublishDateTime(
                                                  reportData.createdAt
                                              )
                                            : 'Đang tải...'}
                                    </div>
                                </div>
                            </div>

                            <button className="flex items-center gap-2 bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-md font-medium transition-colors"
                                onClick={handleChangeReportStatus}
                            >
                                <Lock className="w-4 h-4" /> {getNameReportStatus(reportData?.status)}
                            </button>
                        </div>

                        <h2 className="text-xl font-semibold text-white mb-4">
                            {reportData ? reportData.title : 'Đang tải...'}
                        </h2>

                        <div className="text-gray-300 leading-relaxed">
                            <p>{reportData?.content}</p>
                        </div>
                        {/* Người xử lý report*/}
                        <div className="flex items-center gap-2 mt-4">
                            <img
                                src={getFullPathOfAvatar(
                                    handlerProfile?.avatarPath
                                )}
                                alt="Sidnn avatar"
                                className="w-10 h-10 rounded-full"
                            />
                            <span className="text-orange-400 text-sm">
                                {handlerProfile
                                    ? handlerProfile.username
                                    : 'Đang chờ'}
                            </span>
                        </div>
                    </div>

                    {/* Comments Section */}
                    <div className="p-6">
                        <h3 className="text-lg font-semibold text-white mb-4">
                            Bình luận
                        </h3>

                        <div className="flex items-start gap-3 mb-6">
                            <img
                                src={getFullPathOfAvatar(
                                    requesterProfile?.avatarPath
                                )}
                                alt="Sidnn avatar"
                                className="w-10 h-10 rounded-full"
                            />
                            <div className="flex flex-row w-full gap-3">
                                <textarea
                                    value={commentText}
                                    onChange={(e) =>
                                        setCommentText(e.target.value)
                                    }
                                    disabled={reportData && reportData.status === 'CLOSED'}
                                    placeholder= "Viết bình luận"
                                    className="w-full bg-gray-700 border border-gray-600 rounded-md p-3 text-gray-100 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent resize-none"
                                    rows={3}
                                />
                                <div className="flex items-center justify-between mt-3">
                                    {/*<button className="flex items-center gap-2 text-gray-400 hover:text-gray-300 transition-colors">*/}
                                    {/*    <Paperclip className="w-4 h-4" />*/}
                                    {/*    <span className="text-sm">*/}
                                    {/*        Đính kèm hình ảnh (tối đa 3)*/}
                                    {/*    </span>*/}
                                    {/*</button>*/}
                                    <button className="flex  bg-orange-600 hover:bg-orange-700 text-white p-2 rounded-full transition-colors"
                                    onClick={handleSendComment}>
                                        <Send className="w-4 h-4" />
                                    </button>
                                </div>
                            </div>
                        </div>

                        {/* Existing Comment */}
                        <div className="border-t border-gray-700 pt-4">
                            {commentList && commentList.data && commentList.data.length > 0 ? (
                                commentList.data.map((comment) => (
                                    <>
                                        <div>
                                            {comment && (
                                                <>
                                                    <CommentInReportItem comment={comment} isLock={true} onReply={onReply} />
                                                </>
                                            )}
                                        </div>
                                    </>
                                )
                            )) : (
                                <></>
                            )}
                            {/*<div className="flex items-start gap-3">*/}
                            {/*    <Image*/}
                            {/*        src="/placeholder.svg?height=40&width=40"*/}
                            {/*        alt="Clark avatar"*/}
                            {/*        className="w-10 h-10 rounded-full"*/}
                            {/*    />*/}
                            {/*    <div className="flex-1">*/}
                            {/*        <div className="flex items-center gap-2 mb-1">*/}
                            {/*            <span className="font-semibold text-white">*/}
                            {/*                Clark*/}
                            {/*            </span>*/}
                            {/*            <span className="text-gray-400 text-sm">*/}
                            {/*                09:10 - 24/03/2025*/}
                            {/*            </span>*/}
                            {/*        </div>*/}
                            {/*    </div>*/}
                            {/*</div>*/}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default ReportDetailPage;

const RequestState = () => {
    return (
        <>
            <div>
                <div
                    className={'flex justify-center max-w-30 bg-green-500 p-1 rounded-md hover:cursor-pointer hover:bg-green-600'}>
                    <span>Đang xử lý</span>
                </div>
            </div>
        </>
    );
};

const GeneralDetail = () => {
    return (
        <>
            <div>
                <div className={'flex flex-col gap-y-2'}>
                    <div className={'flex flex-row gap-x-2'}>
                        <img
                            className="h-10 w-10 shadow-xl rounded"
                            loading="lazy"
                            src="https://static.cdnno.com/poster/quy-bi-chi-chu/150.jpg?1585206121"
                            alt=""
                        />
                        <div className={"flex flex-col text-yellow-500"}>
                            <span className={""}>Sidn</span>
                            <span className={"text-sm text-gray-400"}>Báo cáo nội dung truyện</span>
                        </div>
                    </div>

                </div>
            </div>
        </>
    );
};

const ReportContent = () => {
    return (
        <>
            <div>
                <div className={"flex flex-col"}>
                    <div className={"text-yellow-500"}>
                        Truyen abc - Chuong 1
                    </div>
                    <div>
                        <span>Mô tả: 12/12/2021</span>
                    </div>
                </div>
            </div>
        </>
    )
}

const ReportHandleItem = () => {
    return (
        <>
            <div className={''}>
                <div
                    className={'flex flex-row p-3 rounded-md gap-x-5 mt-3 rounded-md bg-background-light p-3  hover:bg-gray-500/10 hover:cursor-pointer'}>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Người báo cáo</span>
                        <span>Ngày báo cáo</span>
                        <span>Trạng thái</span>
                    </div>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Nguyễn Văn A</span>
                        <span>12/12/2021</span>
                        <span>Chưa xử lý</span>
                    </div>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Báo cáo</span>
                        <span>Ngày xử lý</span>
                        <span>Lý do</span>
                    </div>
                </div>
            </div>
        </>

    );
};

const ReportDetail = () => {
    return (
        <>
            <div>

            </div>
        </>
    );
};


const NovelContentDetail = () => {
    return (
        <>
            <div>
                <div className={"flex flex-col gap-x-5 mt-3 rounded-md bg-background-light p-3"}>
                    <div className={"flex flex-col"}>
                        <h3>Chi tiết nội dung truyện</h3>
                        <span>Đây là nội dung truyện bạn cần xem chi tiết.</span>
                    </div>
                    <div className={"mt-4"}>
                        <NovelContentDetailItem />
                    </div>
                </div>
            </div>
        </>
    )
}