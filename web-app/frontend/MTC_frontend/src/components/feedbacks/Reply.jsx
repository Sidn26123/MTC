// import React from "react"
//
// function Reply(){
//
//     return (
//         <>
//             <div>
//                 <article data-x-bind="CommentItem(index)"
//                          className="p-4 mb-6 bg-white dark:bg-inherit text-base border border-auto rounded-lg"
//                          id="comment-id-1791601">
//                     <footer className="flex justify-between items-center mb-2">
//                         <div className="flex items-center"><p
//                             className="inline-flex items-center mr-3 text-sm text-gray-900 dark:text-white"><img
//                             data-x-bind="CommenterAvatar(index)" className="mr-2 w-6 h-6 rounded-full"
//                             src="https://static.cdnno.com/user/682422a2e30d73d843efef82e9c814b8/200.jpg?1729986404"
//                             alt="Trần Thị Hiền"/><a data-x-bind="CommenterName(index)" className="font-bold text-title"
//                                                     href="/ho-so/1228890">Trần Thị Hiền</a></p><p
//                             data-x-text="dayjs(comment.created_at).fromNow()" className="text-xs text-muted">3 năm
//                             trước</p></div>
//                         <div data-x-data="expand" className="relative">
//                             <button data-x-bind="Toggle"
//                                     className="inline-flex items-center p-2 text-sm font-medium text-center text-gray-400 rounded hover:bg-gray-200 dark:hover:bg-gray-700"
//                                     type="button">
//                                 <svg className="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none"
//                                      viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true"
//                                      data-slot="icon">
//                                     <path strokeLinecap="round" strokeLinejoin="round"
//                                           d="M6.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM18.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"></path>
//                                 </svg>
//                                 <span className="sr-only">UserComment settings</span></button>
//                             <div data-x-bind="Dialogue"
//                                  className="absolute top-8 right-0 w-24 bg-gray-200 rounded divide-y divide-gray-100 shadow dark:bg-gray-700 dark:divide-gray-600"
//                                  style="display: none;">
//                                 <ul className="text-sm text-gray-700 dark:text-gray-200">
//                                     <li data-x-bind="CommentRemove(index)"
//                                         className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
//                                         style="display: none;">
//                                         <button className="block py-2 px-4">Xóa</button>
//                                     </li>
//                                     <li data-x-show="comment.user_id !== $store.account?.userData?.id"
//                                         className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
//                                         <button data-x-bind="OpenTicket(comment, ticketOpts)"
//                                                 className="block py-2 px-4">Báo cáo
//                                         </button>
//                                     </li>
//                                     <li data-x-show="canSticky"
//                                         className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
//                                         style="display: none;">
//                                         <button data-x-bind="CommentSticky(index, 1)" className="block py-2 px-4">Ghim
//                                         </button>
//                                         <button data-x-bind="CommentSticky(index, 0)" className="block py-2 px-4"
//                                                 style="display: none;">Gỡ ghim
//                                         </button>
//                                     </li>
//                                 </ul>
//                             </div>
//                         </div>
//                     </footer>
//                     <p data-x-bind="CommentContent(index)"
//                        className="text-gray-700 dark:text-gray-400 line-clamp break-words" id="cmContent1791601">tác
//                         đúng con gái viết . thấy hay liền</p>
//                     <template data-x-if="comment.attachments?.length > 0">
//                         <ul style="list-style-type:circle" className="mt-3 pl-4 text-xs">
//                             <template data-x-for="(attachment, index) in comment.attachments">
//                                 <li><a data-x-bind:href="attachment.url" data-x-text="attachment.title" target="_blank"
//                                        className="text-primary" rel="noopener noreferrer"></a></li>
//                             </template>
//                         </ul>
//                     </template>
//                     <button data-x-bind="CommentReadMore(index)" className="text-xs font-bold text-primary"
//                             style="display: none;">Đọc tiếp
//                     </button>
//                     <div data-x-show="model?.object_type != 'Ticket'"
//                          className="flex justify-between items-center mt-4">
//                         <div className="flex space-x-6">
//                             <button data-x-data="like(comment)" data-x-bind="LikeToggle" type="button"
//                                     className="flex items-center space-x-1 text-xs text-gray-500 hover:underline dark:text-gray-400">
//                                 <svg data-x-show="!likeId" className="w-4 h-4" xmlns="http://www.w3.org/2000/svg"
//                                      fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
//                                      aria-hidden="true" data-slot="icon">
//                                     <path strokeLinecap="round" strokeLinejoin="round"
//                                           d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75a.75.75 0 0 1 .75-.75 2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282m0 0h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23H5.904m10.598-9.75H14.25M5.904 18.5c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 0 1-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 9.953 4.167 9.5 5 9.5h1.053c.472 0 .745.556.5.96a8.958 8.958 0 0 0-1.302 4.665c0 1.194.232 2.333.654 3.375Z"></path>
//                                 </svg>
//                                 <svg data-x-show="likeId" className="w-4 h-4 text-primary"
//                                      xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
//                                      aria-hidden="true" data-slot="icon" style="display: none;">
//                                     <path
//                                         d="M7.493 18.5c-.425 0-.82-.236-.975-.632A7.48 7.48 0 0 1 6 15.125c0-1.75.599-3.358 1.602-4.634.151-.192.373-.309.6-.397.473-.183.89-.514 1.212-.924a9.042 9.042 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75A.75.75 0 0 1 15 2a2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H14.23c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23h-.777ZM2.331 10.727a11.969 11.969 0 0 0-.831 4.398 12 12 0 0 0 .52 3.507C2.28 19.482 3.105 20 3.994 20H4.9c.445 0 .72-.498.523-.898a8.963 8.963 0 0 1-.924-3.977c0-1.708.476-3.305 1.302-4.666.245-.403-.028-.959-.5-.959H4.25c-.832 0-1.612.453-1.918 1.227Z"></path>
//                                 </svg>
//                                 <span data-x-text="comment.like_count">0</span><span
//                                 className="hidden md:inline-flex">Thích</span></button>
//                             <button data-x-bind="CommentOpenReply(comment)" type="button"
//                                     className="text-xs text-gray-500 hover:underline dark:text-gray-400"><span
//                                 data-x-show="!isReplies" className="flex items-center space-x-1" style="display: none;"><svg
//                                 className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
//                                 strokeWidth="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon"><path
//                                 strokeLinecap="round" strokeLinejoin="round"
//                                 d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z"></path></svg><span
//                                 data-x-text="comment.comment_count">0</span><span className="hidden md:inline-flex">Trả lời</span></span><span
//                                 data-x-show="isReplies &amp;&amp; comment.user_id !== $store.account?.userData?.id"
//                                 data-x-text="`Trả lời ${comment.creator?.name}`">Trả lời Trần Thị Hiền</span></button>
//                             <div data-x-show="comment.sticky"
//                                  className="flex items-center space-x-1 text-xs text-gray-500 dark:text-gray-400"
//                                  style="display: none;">
//                                 <svg className="w-4 h-4 text-primary" xmlns="http://www.w3.org/2000/svg" width="24"
//                                      height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none"
//                                      strokeLinecap="round" strokeLinejoin="round">
//                                     <path
//                                         d="M16 3a1 1 0 0 1 .117 1.993l-.117 .007v4.764l1.894 3.789a1 1 0 0 1 .1 .331l.006 .116v2a1 1 0 0 1 -.883 .993l-.117 .007h-4v4a1 1 0 0 1 -1.993 .117l-.007 -.117v-4h-4a1 1 0 0 1 -.993 -.883l-.007 -.117v-2a1 1 0 0 1 .06 -.34l.046 -.107l1.894 -3.791v-4.762a1 1 0 0 1 -.117 -1.993l.117 -.007h8z"
//                                         strokeWidth="0" fill="currentColor"></path>
//                                 </svg>
//                             </div>
//                         </div>
//                         <div data-x-show="!isReplies" className="w-52 md:w-60 text-right" style="display: none;">
//                             <div data-x-text="comment.relate?.name" className="text-muted truncate text-xs"></div>
//                         </div>
//                     </div>
//                 </article>
//             </div>
//         </>
//     )
// }
//
// export default Reply;

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
