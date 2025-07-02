import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp } from '@fortawesome/free-solid-svg-icons';
import { Reply, UserReply } from './Reply.jsx';
import { getProfileById } from '../../services/userService.js';
import { timeAgo } from '../../utils/DatetimeUtil.js';

function UserComment({ comment }) {
    const [isMenuOpen, setIsMenuOpen] = React.useState(false);
    const [isReplyOpen, setIsReplyOpen] = React.useState(true);
    const [userCommentProfile, setUserCommentProfile] = React.useState(null);
    const sampleData = {
        // onRemove: () => alert("Xóa bình luận"),
        // onReport: () => alert("Báo cáo bình luận"),
        // onSticky: (status) => alert(status ? "Ghim bình luận" : "Gỡ ghim bình luận"),
        canSticky: false,
        isOwner: false,
    };
    if (!comment) {
        comment = sampleData;
    } else {
        comment = {
            ...sampleData,
            ...comment
        }
    }

    const childComments = [];
    // useEffect(() => {
    //     const fetchProfile = () => {
    //         if (comment.commentedBy) {
    //             const profile = getProfileById(comment.commentedBy);
    //             setUserCommentProfile(profile);
    //         }
    //     };
    //     fetchProfile();
    // }, [comment.commentedBy]);

    function timeUtil(timestamp){
        return timeAgo(new Date(timestamp));
    }
    return (
        <>
            <div>
                <article
                    className="select-none p-4 mb-6 bg-inherit text-base border border-auto border-gray-500 rounded-lg"
                    id="comment-id-4329074">
                    <footer className="flex justify-between items-center mb-2">
                        <div className="flex items-center">
                            <p className="inline-flex items-center mr-3 text-sm text-gray-200">
                                <img
                                    className="mr-2 w-6 h-6 rounded-full"
                                    src="https://static.cdnno.com/user/3e51ba7aa3469845e79f046a025ac3f6/200.jpg?1736056051"
                                    alt="Dương Khai"
                                />
                                {userCommentProfile && userCommentProfile.username && (
                                    <a className="font-bold text-title select-text" href="/ho-so/1004984">{userCommentProfile.username}</a>

                            )}
                            </p>
                            {comment.createdAt && (
                                <p className="text-xs text-muted">{timeAgo(comment.createdAt)}</p>

                            )}
                        </div>

                        <div className="relative">
                        <div
                                className="inline-flex items-center p-2 pb-0 text-sm font-medium text-center text-gray-400 rounded cursor-pointer"
                                onClick={() => setIsMenuOpen(!isMenuOpen)}
                            >
                                <svg className="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none"
                                     viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M6.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM18.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"></path>
                                    <div className={'relative position-x-'}>
                                    </div>
                                </svg>
                                {isMenuOpen && (
                                    <DialogueMenu {...comment} />

                                )}

                                <span className="sr-only">Comment settings</span>
                            </div>
                            <div
                                className="absolute top-8 right-0 w-24 bg-gray-200 rounded divide-y divide-gray-100 shadow"
                                style={{ display: 'none' }}>
                                <ul className="text-sm text-gray-700">
                                    <li className="hover:bg-gray-100" style={{ display: 'true' }}>
                                        <button className="block py-2 px-4">Xóa</button>
                                    </li>
                                    <li className="hover:bg-gray-100">
                                        <button className="block py-2 px-4">Báo cáo</button>
                                    </li>
                                    <li className="hover:bg-gray-100" style={{ display: 'none' }}>
                                        <button className="block py-2 px-4">Ghim</button>
                                        <button className="block py-2 px-4" style={{ display: 'none' }}>Gỡ ghim</button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </footer>
                    <p className="text-gray-300 line-clamp break-words select-text" id="cmContent4329074">{comment.content}</p>
                    <ul style={{ listStyleType: 'circle' }} className="mt-3 pl-4 text-xs">
                        {/*<li>*/}
                        {/*<a target="_blank" className="text-primary" rel="noopener noreferrer">Attachment Title</a>*/}
                        {/*</li>*/}
                    </ul>
                    <button className="text-xs font-bold text-primary" style={{ display: 'none' }}>Đọc tiếp</button>
                    <div className="flex justify-between items-center mt-4">
                        <div className="flex space-x-6">
                            <div className="flex items-center space-x-1 text-xs text-gray-500 hover:underline">
                                <FontAwesomeIcon icon={faThumbsUp} />
                                <span>0</span>
                                <span className="hidden md:inline-flex">Thích</span>
                            </div>
                            <div className="text-xs text-gray-500 hover:underline">
                                <span className="flex items-center space-x-1">
                                  <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"
                                       viewBox="0 0 24 24" strokeWidth="1.5"
                                       stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z"></path>
                                  </svg>
                                  <span>0</span>
                                  <span className="hidden md:inline-flex">Trả lời</span>
                                </span>
                            </div>
                        </div>
                        <div className="w-52 md:w-60 text-right ">
                            <div className="text-muted truncate text-xs">Chương 1: Trọng sinh, Tô Tiểu Tình cùng long
                                phượng thai bảo bảo còn sống
                            </div>
                        </div>
                    </div>
                </article>
                {!isReplyOpen && (
                    <div className="ml-12 mb-4" onClick={() => setIsReplyOpen(true)}>
                        <button className="font-bold text-sm">Xem 9 trả lời
                        </button>
                    </div>
                )}

                {isReplyOpen && (
                    <div className={'ml-12'}>
                        <Reply />
                        <UserReply />
                        <div className="ml-12 mb-4" onClick={() => setIsReplyOpen(false)}>
                            <button className="font-bold text-sm">Ẩn các câu trả lời
                            </button>
                        </div>
                    </div>
                )
                }
            </div>
        </>
    )
}

export default UserComment;

const DialogueMenu = ({ onRemove, onReport, onSticky, canSticky, isOwner }) => {
    return (
        <div
            className="absolute top-8 right-0 w-24 bg-gray-200 rounded divide-y divide-gray-100 shadow background-color-lighter" style = {{display: "true"}}>
            <ul className="text-sm text-gray-200 ">
                    <li className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                        <button className="block py-2 px-4" onClick={onRemove}>Xóa</button>
                    </li>
                {!isOwner && (
                    <li className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                        <button className="block py-2 px-4" onClick={onReport}>Báo cáo</button>
                    </li>
                )}
                {canSticky && (
                    <li className="hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                        <button className="block py-2 px-4" onClick={() => onSticky(true)}>Ghim</button>
                        <button className="block py-2 px-4" onClick={() => onSticky(false)}>Gỡ ghim</button>
                    </li>
                )}
            </ul>
        </div>
    );
};

const RootComment = ({comment}) => {
    return (
        <>

        </>
    )
}

export { DialogueMenu };

