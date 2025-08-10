import React, { useEffect, useState } from 'react';
import { MoreHorizontal } from "lucide-react"


import dayjs from "dayjs";

import relativeTime from "dayjs/plugin/relativeTime";
dayjs.extend(relativeTime);

import { useParams } from 'react-router-dom';
import { getReportById } from '../../services/feedbackService.js';


const mockComments = [
    {
        id: 1,
        user_id: 123,
        creator: {
            id: 123,
            name: "Nguyễn Văn A",
            avatar: "https://i.pravatar.cc/150?img=1",
        },
        content: "Đây là bình luận mẫu. Nội dung rất dài nên cần phải rút gọn để thử tính năng 'Đọc tiếp' của UI.",
        created_at: dayjs().subtract(2, "hour").toISOString(),
        attachments: [
            {
                url: "https://example.com/file1.pdf",
                title: "File 1 đính kèm",
            },
        ],
        sticky: false,
    },
    {
        id: 2,
        user_id: 456,
        creator: {
            id: 456,
            name: "Trần Thị B",
            avatar: "https://i.pravatar.cc/150?img=2",
        },
        content: "Bình luận ngắn.",
        created_at: dayjs().subtract(1, "day").toISOString(),
        attachments: [],
        sticky: true,
    },
];


const ReportDetailPage = () => {
    const { reportId } = useParams(); // nếu có id ⇒ đang edit
    useEffect(() => {
        getReportById(reportId).then((res) => {
            const report = res.data.result;
            console.log("Report details:", report);
            // Xử lý dữ liệu báo cáo ở đây, ví dụ: cập nhật state hoặc hiển thị thông tin
        });
    }, [reportId]);
    const [replyText, setReplyText] = useState("")

    const messages = [
        {
            id: 1,
            user: "Sidnn",
            avatar: "/placeholder.svg?height=40&width=40",
            time: "vài giây trước",
            content: "sd sd ds d",
        },
        {
            id: 2,
            user: "Sidnn",
            avatar: "/placeholder.svg?height=40&width=40",
            time: "vài giây trước",
            content: "Yêu cầu báo cáo đã được đóng",
        },
    ]

    const handleSendReply = () => {
        if (replyText.trim()) {
            console.log("Sending reply:", replyText)
            setReplyText("")
        }
    }

    const handleKeyPress = (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault()
            handleSendReply()
        }
    }

    const currentUserId = 123;

    const handleDelete = (comment) => {
        alert(`Xóa bình luận ID ${comment.id}`);
    };

    const handleReport = (comment) => {
        alert(`Báo cáo bình luận ID ${comment.id}`);
    };

    const handleSticky = (comment, isSticky) => {
        alert(`${isSticky ? "Ghim" : "Gỡ ghim"} bình luận ID ${comment.id}`);
    };
    return (
        <>
            <ReportDetail />
            <div className={"pl-5"}>
                {mockComments.map((comment) => (
                    <CommentItem
                        key={comment.id}
                        comment={comment}
                        currentUserId={currentUserId}
                        onDelete={handleDelete}
                        onReport={handleReport}
                        onSticky={handleSticky}
                    />
                ))}
                <UserReply />

            </div>

        </>

    )
}


export default ReportDetailPage


export const CommentItem = ({ comment, currentUserId, onDelete, onReport, onSticky }) => {
    const [menuOpen, setMenuOpen] = useState(false);
    const [readMore, setReadMore] = useState(false);

    const isOwnComment = comment.user_id === currentUserId;

    return (
        <article className="p-4 mb-6 bg-white dark:bg-inherit text-base border rounded-lg">
            <footer className="flex justify-between items-center mb-2">
                <div className="flex items-center">
                    <p className="inline-flex items-center mr-3 text-sm text-gray-900 dark:text-white">
                        <img
                            className="mr-2 w-6 h-6 rounded-full"
                            src={comment.creator?.avatar}
                            alt={comment.creator?.name}
                        />
                        <a href={`/ho-so/${comment.creator?.id}`} className="font-bold text-title">
                            {comment.creator?.name}
                        </a>
                    </p>
                    <p className="text-xs text-muted">{dayjs(comment.created_at).fromNow()}</p>
                </div>

                <div className="relative">
                    <button
                        onClick={() => setMenuOpen(!menuOpen)}
                        className="inline-flex items-center p-2 text-sm text-gray-400 rounded hover:bg-gray-200 dark:hover:bg-gray-700"
                    >
                        <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M6.75 12a.75.75...Z" />
                        </svg>
                        <span className="sr-only">Comment settings</span>
                    </button>

                    {menuOpen && (
                        <div className="absolute top-8 right-0 w-24 bg-gray-200 dark:bg-gray-700 rounded shadow divide-y">
                            <ul className="text-sm text-gray-700 dark:text-gray-200">
                                <li>
                                    <button
                                        onClick={() => onDelete(comment)}
                                        className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600"
                                    >
                                        Xóa
                                    </button>
                                </li>
                                {!isOwnComment && (
                                    <li>
                                        <button
                                            onClick={() => onReport(comment)}
                                            className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600"
                                        >
                                            Báo cáo
                                        </button>
                                    </li>
                                )}
                                {onSticky && (
                                    <>
                                        {!comment.sticky ? (
                                            <li>
                                                <button
                                                    onClick={() => onSticky(comment, true)}
                                                    className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600"
                                                >
                                                    Ghim
                                                </button>
                                            </li>
                                        ) : (
                                            <li>
                                                <button
                                                    onClick={() => onSticky(comment, false)}
                                                    className="block py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600"
                                                >
                                                    Gỡ ghim
                                                </button>
                                            </li>
                                        )}
                                    </>
                                )}
                            </ul>
                        </div>
                    )}
                </div>
            </footer>

            <p className="text-gray-700 dark:text-gray-400 break-words">
                {readMore ? comment.content : comment.content?.slice(0, 300)}
                {comment.content?.length > 300 && (
                    <button
                        onClick={() => setReadMore(!readMore)}
                        className="text-xs font-bold text-primary ml-2"
                    >
                        {readMore ? "Thu gọn" : "Đọc tiếp"}
                    </button>
                )}
            </p>

            {comment.attachments?.length > 0 && (
                <ul className="mt-3 pl-4 text-xs list-disc">
                    {comment.attachments.map((file, i) => (
                        <li key={i}>
                            <a href={file.url} target="_blank" rel="noopener noreferrer" className="text-primary">
                                {file.title}
                            </a>
                        </li>
                    ))}
                </ul>
            )}

            {/* Bạn có thể bổ sung thêm các chức năng như like, reply, sticky... tại đây */}
        </article>
    );
}

function UserReply() {
    return (
        <div>
            <div className="flex items-center mb-4 space-x-2">
                <textarea
                    rows="1"
                    className="px-2 pt-2 rounded w-full text-gray-900 border-0 focus:ring-0 focus:outline-none dark:text-white dark:placeholder-gray-400 dark:bg-black shadow"
                    required
                    id="replyForm-54547"
                    placeholder="Trả lời ..."
                    style={{
                        overflow: "hidden",
                        overflowWrap: "break-word",
                        resize: "none",
                        textAlign: "start",
                        height: "40px",
                    }}
                ></textarea>
                <button
                    className="px-4 py-2 border border-primary shadow-sm text-sm font-medium rounded-md text-primary bg-inherit focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 btn-outline-primary w-16 h-9 disabled:bg-gray-500"
                >
                    GỬI
                </button>
            </div>
        </div>
    );
}


const ReportDetail = ({ model }) => {
    const title = model?.title ?? `Yêu cầu hỗ trợ #${model?.id}`;
    const assignedName = model?.assigned?.name ?? "Chưa xác định";
    const createdAt = dayjs(model?.created_at).format("YYYY-MM-DD HH:mm:ss");
    const showCloseButton = model?.status !== "close";
    const relateName = model?.relate?.name;
    const content = "Nội dung báo cáo: " + (model?.content || "Chưa có nội dung");
    return (
        <>
            <div className="flex justify-between items-center bg-secondary text-black uppercase p-3 text-xs h-12">
                <div>{title}</div>
                <div>
                    Xử lý bởi <span className="font-bold">{assignedName}</span>
                </div>
            </div>

            <div className="p-4 mb-6 text-base bg-white border border-secondary dark:bg-black">
                <div className="flex justify-between items-center mb-2">
                    <div className="flex items-center">
                        <p className="inline-flex items-center mr-3 text-sm text-gray-900 dark:text-white">
                            <img
                                className="mr-2 w-10 h-10 rounded-full"
                                src={
                                    model?.creator?.avatar ??
                                    'https://static.cdnno.com/default-avatar.jpg'
                                }
                                alt={model?.creator?.name ?? 'User'}
                            />
                        </p>
                        <div className="flex flex-col space-y-1">
                            <span className="font-bold">
                                {model?.creator?.name ?? 'Unknown'}
                            </span>
                            <span className="text-xs text-muted">
                                {createdAt}
                            </span>
                        </div>
                    </div>

                    {showCloseButton && (
                        <div className="relative">
                            <button
                                onClick={() => {
                                    /* close handler */
                                }}
                                className="inline-flex items-center p-2 text-sm text-white rounded bg-primary space-x-2"
                                type="button"
                            >
                                <svg
                                    className="w-3 h-3"
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                    aria-hidden="true"
                                >
                                    <path
                                        fillRule="evenodd"
                                        d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z"
                                        clipRule="evenodd"
                                    />
                                </svg>
                                <span>Đóng</span>
                            </button>
                        </div>
                    )}
                </div>

                <div className="flex flex-col space-y-2 mt-3">
                    {relateName && <div>{`Báo cáo: ${relateName}`}</div>}
                    <p className="text-gray-700 dark:text-gray-300">
                        {/*          {model?.content?.split("\n").map((line, index) => (*/}
                        {/*              <span key={index}>*/}
                        {/*  {line}*/}
                        {/*                  <br />*/}
                        {/*</span>*/}
                        {/*          )) ?? " "}*/}
                        {content}
                    </p>
                </div>
            </div>
        </>
    );
};



