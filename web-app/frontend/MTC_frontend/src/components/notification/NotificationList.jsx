import React from 'react';
import dayjs from 'dayjs';

// const NotificationList = ({
//                               data = {},
//                               markAllAsRead,
//                               markAsRead,
//                               remove,
//                               goTo,
//                           }) => {
//     return (
//         <div className="table w-full">
//             <div className="table-header-group bg-secondary h-12 text-black">
//                 <div className="table-row">
//                     <div className="table-cell text-left p-4 text-xs uppercase">Thông báo</div>
//                     <div className="table-cell text-right align-middle px-4">
//                         <button onClick={markAllAsRead} className="text-primary">
//                             <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
//                                 <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
//                             </svg>
//                         </button>
//                     </div>
//                 </div>
//             </div>
//
//             <div className="table-row-group">
//                 {data && data.totalElements && data.data.map((item, index) => (
//                     <div key={index} className={`table-row ${!item.read_at ? 'highlight' : ''}`}>
//                         <div onClick={() => goTo(item)} className="table-cell border-b border-auto p-4 cursor-pointer">
//                             <div className="flex space-x-2 md:text-base">
//                                 {item.data?.image && (
//                                     <img
//                                         src={item.data.image}
//                                         alt="notification"
//                                         className="w-10 h-10 rounded-full aspect-square object-cover"
//                                     />
//                                 )}
//                                 <div className="space-y-2">
//                                     <div>
//                                         <span className="font-medium dark:font-semibold">
//                                           {item.title ? item.title + ': ' : ''}
//                                         </span>
//                                         <span>{item.data?.message}</span>
//                                     </div>
//                                     <div className="text-xs text-muted">
//                                         {dayjs(item.created_at).format('YYYY-MM-DD HH:mm:ss')}
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                         <div className="table-cell border-b border-auto text-right p-4 space-x-1 align-middle">
//                             <button onClick={() => markAsRead(item)} className="text-gray-500">
//                                 <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
//                                     <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
//                                 </svg>
//                             </button>
//                             <button onClick={() => remove(item)} className="text-gray-500">
//                                 <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
//                                     <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166M4.772 5.79a48.11 48.11 0 0 1 3.478-.397M7.5 5.393v-.916c0-1.18.91-2.164 2.09-2.201a51.964 51.964 0 0 1 3.32 0c1.18.037 2.09 1.022 2.09 2.201v.916M4.772 5.79l1.068 13.883a2.25 2.25 0 0 0 2.244 2.077h7.832a2.25 2.25 0 0 0 2.244-2.077l1.068-13.883" />
//                                 </svg>
//                             </button>
//                         </div>
//                     </div>
//                 ))}
//             </div>
//         </div>
//     );
// };


const NotificationList = ({
                              data = {},
                              markAllAsRead,
                              markAsRead,
                              remove,
                              goTo,
                          }) => {
    // Function để lấy avatar mặc định theo loại notification
    const getAvatarByType = (notificationType, senderId) => {
        const avatarMap = {
            'STORY_LIKED': 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face',
            'STORY_COMMENTED': 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=40&h=40&fit=crop&crop=face',
            'SYSTEM': 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=40&h=40&fit=crop&crop=face'
        };

        // Nếu là admin thì dùng avatar hệ thống
        if (senderId === 'admin-001') {
            return 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face';
        }

        return avatarMap[notificationType] || 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=40&h=40&fit=crop&crop=face';
    };

    // Function để lấy màu priority
    const getPriorityColor = (priority) => {
        switch (priority) {
            case 'HIGH':
                return 'text-red-600';
            case 'MEDIUM':
                return 'text-yellow-600';
            case 'LOW':
                return 'text-green-600';
            default:
                return 'text-gray-600';
        }
    };

    // Function để lấy icon theo notification type
    const getNotificationIcon = (notificationType) => {
        switch (notificationType) {
            case 'STORY_LIKED':
                return (
                    <svg className="w-4 h-4 text-red-500" fill="currentColor" viewBox="0 0 24 24">
                        <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                    </svg>
                );
            case 'STORY_COMMENTED':
                return (
                    <svg className="w-4 h-4 text-blue-500" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z" />
                    </svg>
                );
            default:
                return (
                    <svg className="w-4 h-4 text-gray-500" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
                    </svg>
                );
        }
    };

    return (
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-200 dark:border-gray-700">
            {/* Header */}
            <div className="bg-gray-50 dark:bg-gray-900 px-6 py-4 border-b border-gray-200 dark:border-gray-700 rounded-t-lg">
                <div className="flex items-center justify-between">
                    <div className="flex items-center space-x-2">
                        <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
                            Thông báo
                        </h2>
                        {data.totalElements > 0 && (
                            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">
                                {data.totalElements}
                            </span>
                        )}
                    </div>
                    <button
                        onClick={markAllAsRead}
                        className="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-md text-blue-600 bg-blue-100 hover:bg-blue-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:text-blue-400 dark:bg-blue-900 dark:hover:bg-blue-800 transition-colors duration-200"
                    >
                        <svg className="w-4 h-4 mr-1" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                        </svg>
                        Đánh dấu tất cả
                    </button>
                </div>
            </div>

            {/* Notification List */}
            <div className="divide-y divide-gray-200 dark:divide-gray-700">
                {data && data.totalElements && data.data.map((item, index) => (
                    <div
                        key={item.id}
                        className={`relative p-4 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200 ${
                            !item.isRead
                                ? 'bg-blue-50 dark:bg-blue-900/20 border-l-4 border-l-blue-500'
                                : 'bg-white dark:bg-gray-800'
                        }`}
                    >
                        {/* Unread indicator dot */}
                        {!item.isRead && (
                            <div className="absolute top-4 left-2 w-2 h-2 bg-blue-500 rounded-full"></div>
                        )}

                        <div className="flex space-x-3">
                            {/* Avatar */}
                            <div className="flex-shrink-0">
                                <div className="relative">
                                    <img
                                        src={getAvatarByType(item.notificationType, item.senderId)}
                                        alt="Avatar"
                                        className="w-10 h-10 rounded-full object-cover ring-2 ring-gray-200 dark:ring-gray-600"
                                    />
                                    {/* Notification type icon overlay */}
                                    <div className="absolute -bottom-1 -right-1 bg-white dark:bg-gray-800 rounded-full p-1 ring-2 ring-gray-100 dark:ring-gray-700">
                                        {getNotificationIcon(item.notificationType)}
                                    </div>
                                </div>
                            </div>

                            {/* Content */}
                            <div
                                className="flex-1 cursor-pointer"
                                onClick={() => goTo(item)}
                            >
                                <div className="flex items-start justify-between">
                                    <div className="flex-1">
                                        {/* Title and Priority */}
                                        <div className="flex items-center space-x-2 mb-1">
                                            <h3 className={`text-sm font-medium ${!item.isRead ? 'text-gray-900 dark:text-white' : 'text-gray-700 dark:text-gray-300'}`}>
                                                {item.title}
                                            </h3>
                                            {item.priority && (
                                                <span className={`inline-flex items-center px-1.5 py-0.5 rounded-full text-xs font-medium ${
                                                    item.priority === 'HIGH'
                                                        ? 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                                                        : item.priority === 'MEDIUM'
                                                            ? 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
                                                            : 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
                                                }`}>
                                                    {item.priority}
                                                </span>
                                            )}
                                        </div>

                                        {/* Content */}
                                        <p className={`text-sm ${!item.isRead ? 'text-gray-900 dark:text-gray-100' : 'text-gray-600 dark:text-gray-400'}`}>
                                            {item.content}
                                        </p>

                                        {/* Timestamp */}
                                        <p className="text-xs text-gray-500 dark:text-gray-400 mt-2 flex items-center space-x-2">
                                            <svg className="w-3 h-3" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                                            </svg>
                                            <span>{dayjs(item.createdAt).format('DD/MM/YYYY HH:mm')}</span>
                                            {item.readAt && (
                                                <>
                                                    <span>•</span>
                                                    <span>Đã đọc lúc {dayjs(item.readAt).format('DD/MM HH:mm')}</span>
                                                </>
                                            )}
                                        </p>
                                    </div>

                                    {/* Actions */}
                                    <div className="flex items-center space-x-2 ml-4">
                                        {!item.isRead && (
                                            <button
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    markAsRead(item);
                                                }}
                                                className="p-1.5 text-gray-400 hover:text-blue-600 hover:bg-blue-100 rounded-full transition-colors duration-200"
                                                title="Đánh dấu đã đọc"
                                            >
                                                <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                                    <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                                                </svg>
                                            </button>
                                        )}
                                        <button
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                remove(item.id);
                                            }}
                                            className="p-1.5 text-gray-400 hover:text-red-600 hover:bg-red-100 rounded-full transition-colors duration-200"
                                            title="Xóa thông báo"
                                        >
                                            <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                                <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166M4.772 5.79a48.11 48.11 0 0 1 3.478-.397M7.5 5.393v-.916c0-1.18.91-2.164 2.09-2.201a51.964 51.964 0 0 1 3.32 0c1.18.037 2.09 1.022 2.09 2.201v.916M4.772 5.79l1.068 13.883a2.25 2.25 0 0 0 2.244 2.077h7.832a2.25 2.25 0 0 0 2.244-2.077l1.068-13.883" />
                                            </svg>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* Empty State */}
            {(!data || data.totalElements === 0) && (
                <div className="text-center py-12">
                    <svg className="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
                    </svg>
                    <h3 className="mt-2 text-sm font-medium text-gray-900 dark:text-white">Không có thông báo</h3>
                    <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">Bạn chưa có thông báo nào.</p>
                </div>
            )}
        </div>
    );
};

export default NotificationList;
