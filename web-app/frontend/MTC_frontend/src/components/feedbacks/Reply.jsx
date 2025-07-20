
import React from "react";
import { DialogueMenu } from './UserComment.jsx';


function Reply() {
    const sampleData = {
        onRemove: () => alert("Xóa bình luận"),
        onReport: () => alert("Báo cáo bình luận"),
        onSticky: (status) => alert(status ? "Ghim bình luận" : "Gỡ ghim bình luận"),
        canSticky: false,
        isOwner: false,
    };

    const [showMenu, setShowMenu] = React.useState(false);
    return (
        <div>
            <article className="p-4 mb-6 bg-inherit text-base border border-auto rounded-lg border-gray-500"
                     id="comment-id-1791601">
                <footer className="flex justify-between items-center mb-2">
                    <div className="flex items-center">
                        <p className="inline-flex items-center mr-3 text-sm text-gray-200">
                            <img
                                className="mr-2 w-6 h-6 rounded-full"
                                src="https://static.cdnno.com/user/3e51ba7aa3469845e79f046a025ac3f6/200.jpg?1736056051"
                                alt="Dương Khai"
                            />
                            <a className="font-bold text-title" href="/ho-so/1004984">Dương Khai</a>
                        </p>
                        <p className="text-xs text-muted">2 tháng trước</p>
                    </div>

                    <div className="relative">
                        <div
                            className="inline-flex items-center p-2 pb-0 text-sm font-medium text-center text-gray-400 rounded cursor-pointer"
                            onClick = {() => setShowMenu(!showMenu)}
                        >
                            <svg className="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                <path strokeLinecap="round" strokeLinejoin="round"
                                      d="M6.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM18.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"></path>
                                <div className={'relative position-x-'}>
                                </div>
                            </svg>
                            {showMenu && (
                                <DialogueMenu {...sampleData} />

                            )}

                            <span className="sr-only">Comment settings</span>
                        </div>
                    </div>
                </footer>
                <p className="text-gray-700 line-clamp break-words" id="cmContent1791601">tác đúng con gái viết . thấy
                    hay liền</p>
                <button className="text-xs font-bold text-primary" style={{ display: 'none' }}>Đọc tiếp</button>
                <div className="flex justify-between items-center mt-4">
                    <div className="flex space-x-6">
                        <div
                            className="flex items-center space-x-1 text-xs text-gray-500 hover:underline dark:text-gray-400">
                            <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                 strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                <path strokeLinecap="round" strokeLinejoin="round"
                                      d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08..." />
                            </svg>
                            <span>0</span><span className="hidden md:inline-flex">Thích</span>
                        </div>
                        <div className="text-xs text-gray-500 hover:underline dark:text-gray-400">
                            <span>Trả lời</span>
                        </div>
                    </div>
                </div>
            </article>
        </div>
)
    ;
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


export { Reply, UserReply };
