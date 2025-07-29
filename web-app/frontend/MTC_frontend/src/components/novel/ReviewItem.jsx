import { useEffect, useState } from 'react';
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import { ExpandableText } from '../../common/CommonComponents.jsx';
import { getProfileById } from '../../services/userService.js';
import { getFullPathOfAvatar, getUserName } from '../../utils/ProfileUtils.js';
import { getNovelById, getNovelBySlug } from '../../services/novelService.js';
import { timeAgo } from '../../utils/DatetimeUtil.js';
dayjs.extend(relativeTime);

const ReviewItem = ({ review }) => {
    const [liked, setLiked] = useState(false);
    const [profile, setProfile] = useState(null);
    const [novel, setNovel] = useState(null);
    const handleLike = () => {
        setLiked(!liked);
        // Optionally increase like count here
    };
    useEffect(() => {
        getProfileById(review.ratedBy).then((response) => {
            // Update review.user with full profile data if needed

            setProfile(response.data.result);
        }).catch((error) => {
            console.error("Error fetching user profile:", error);
        });
        getNovelById(review.novelId).then((response) => {
            setNovel(response.data.result);
        });
    }, []);

    return <>
        {profile && profile.id && (
            <article className="p-4 mb-6 text-base highlight bg-gray-700/75 rounded-md shadow-sm">
                <footer className="mb-2">
                    <div className="flex items-center justify-between space-x-3">
                        <div className="flex items-center space-x-3">
                            <img
                                src={getFullPathOfAvatar(profile.avatarPath)}
                                alt={getUserName(profile)}
                                className="w-10 h-10 rounded-full"
                            />
                            <p className="items-center text-sm text-gray-900 space-x-1">
                                <span className="font-bold">{getUserName(profile)}</span>
                                <span className="text-muted">đã đánh giá</span>
                                <a
                                    target="_blank"
                                    rel="noreferrer"
                                    className="text-title font-semibold"
                                >
                                    {novel && novel.name}
                                </a>
                            </p>
                        </div>

                        <div className="flex space-x-4 text-xs text-gray-500">
                            <div className="flex items-center">
                                <svg
                                    className="w-5 h-5 text-yellow-400"
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                >
                                    <path
                                        fillRule="evenodd"
                                        d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.006
                   5.404.434c1.164.093 1.636 1.545.749 2.305l-4.117 3.527
                   1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12
                   18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273
                   -4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.434
                   2.082-5.005Z"
                                        clipRule="evenodd"
                                    />
                                </svg>
                                <span className="ml-1">{review.rate}</span>
                            </div>
                        </div>
                    </div>
                </footer>

                <p className="text-gray-700 whitespace-pre-wrap break-words">
                    {< ExpandableText text={review.content} />}
                </p>

                <div className="flex justify-between items-center mt-4">
                    <div className="flex space-x-6">
                        <button
                            type="button"
                            onClick={handleLike}
                            className="flex items-center space-x-1 text-xs text-gray-500 hover:underline"
                        >
                            {!liked ? (
                                <svg
                                    className="w-4 h-4"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth="1.5"
                                    stroke="currentColor"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        d="M6.633 10.25c.806 0 1.533-.446..."
                                    />
                                </svg>
                            ) : (
                                <svg
                                    className="w-4 h-4 text-primary"
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                >
                                    <path d="..." />
                                </svg>
                            )}
                            <span>{liked ? review.totalLikes + 1 : review.totalLikes}</span>
                            <span className="hidden md:inline-flex">Thích</span>
                        </button>
                    </div>

                    <div className="w-52 text-right">
                        <div className="text-muted text-xs truncate">
                            {timeAgo(review.createdAt)}
                        </div>
                    </div>
                </div>
            </article>

        )
        }
    </>
};

export default ReviewItem;
