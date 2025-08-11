import React, { useEffect, useState } from 'react';
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import { getProfileById, getProfileById1 } from '../../services/userService.js';
import { UserReply } from './Reply.jsx';
import { getFullPathOfAvatar, getUserName } from '../../utils/ProfileUtils.js';
import { HaveReplyUserText } from '../common/SpecialElements.jsx';
dayjs.extend(relativeTime);

export default function CommentInReportItem({ comment, index, isLock, onReply }) {
    const [isReply, setIsReply] = useState(false);

    console.log("CommentInReportItem comment:", comment);
    if (!comment) return null;
    const [profile, setProfile] = useState({});

    function handleReply(username){
        console.log("Replying to:", username);
        setIsReply(!isReply);
        if (onReply) {
            onReply(username);
        }
    }

    useEffect(() => {
        getProfileById(comment?.commenterId).then(
            (response) => {
                if (response.status === 404){
                    console.error("User profile not found.");
                    return;
                }
                if (response.data.result) {
                    setProfile(response.data.result);
                    console.log("User profile fetched successfully:", response.data.result);
                } else {
                    console.error("Failed to fetch user profile.");
                }
            }
        )
    }, []);

    return (
        <article
            className="p-4 mb-6 bg-white dark:bg-inherit text-base border border-auto rounded-lg"
            id={`comment-id-${comment.id}`}
        >
            {/* Header */}
            <footer className="flex justify-between items-center mb-2">
                <div className="flex items-center">
                    <p className="inline-flex items-center mr-3 text-sm text-gray-900 dark:text-white">
                        <img
                            className="mr-2 w-6 h-6 rounded-full"
                            src={getFullPathOfAvatar(profile?.avatarPath)}
                            alt={comment.commenter?.name}
                        />
                        <a
                            className="font-bold text-title"
                            href={`/ho-so/${comment.commenterId}`}
                        >
                            {getUserName(profile)}
                        </a>
                    </p>
                    <p className="text-xs text-muted">
                        {dayjs(comment.created_at).fromNow()}
                    </p>
                </div>

                {/* Menu */}
                <div className="relative">
                    <button
                        disabled={isLock}
                        className="inline-flex items-center p-2 text-sm font-medium text-center text-gray-400 rounded hover:bg-gray-200 dark:hover:bg-gray-700"
                    >
                        <svg
                            className="w-5 h-5"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth="1.5"
                            stroke="currentColor"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M6.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM18.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"
                            />
                        </svg>
                        <span className="sr-only">Comment settings</span>
                    </button>
                </div>
            </footer>

            {/* Nội dung */}
            <p className="text-gray-700 dark:text-gray-400 line-clamp break-words">
                {HaveReplyUserText({ text: comment.content })}
            </p>

            {/* Attachments */}
            {comment.attachments?.length > 0 && (
                <ul style={{ listStyleType: "circle" }} className="mt-3 pl-4 text-xs">
                    {comment.attachments.map((attachment, idx) => (
                        <li key={idx}>
                            <a
                                href={attachment.url}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="text-primary"
                            >
                                {attachment.title}
                            </a>
                        </li>
                    ))}
                </ul>
            )}

            {/* Actions */}
            <div className="flex space-x-6 mt-4">
                <div className="flex space-x-6 mt-4">
                    {/*<button*/}
                    {/*    disabled={isLock}*/}
                    {/*    type="button"*/}
                    {/*    className="flex items-center space-x-1 text-xs text-gray-500 hover:underline dark:text-gray-400"*/}
                    {/*>*/}
                    {/*    👍 <span>{comment.like_count}</span>*/}
                    {/*    <span className="hidden md:inline-flex">Thích</span>*/}
                    {/*</button>*/}
                    <button
                        onClick={() => handleReply(profile?.username)}
                        type="button"
                        className="text-xs text-gray-500 hover:underline dark:text-gray-400"
                    >
                        Trả lời
                    </button>
                </div>

            </div>
            <div>
                {/*{isReply && (*/}
                {/*    <div className={"mt-5"}>*/}
                {/*        <UserReply />*/}
                {/*    </div>*/}
                {/*)}*/}
            </div>
        </article>
    );
}
