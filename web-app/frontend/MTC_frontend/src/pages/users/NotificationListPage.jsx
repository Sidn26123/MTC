// import React, { useEffect, useState } from 'react';
// import NotificationList from '../../components/notification/NotificationList.jsx';
// import { getNotifications, readNotification } from '../../services/notificationService.js';
// import { useNotifications, useSetNotifications } from '../../stores/notificationStore.js'; // giả sử component con đã có
// // import { Spinner } from './Spinner'; // component loading đơn giản (bạn có thể tạo thêm)
//
// const NotificationListPage = () => {
//     const [loading, setLoading] = useState(true);
//     const notifications = useNotifications();
//     const setNotifications = useSetNotifications();
//
//
//     useEffect(() => {
//         getNotifications().then(r => {
//             console.log('r', r);
//             setNotifications(r.data.result);
//             setLoading(false);
//         });
//     }, [] );
//
//     const handleMarkAllAsRead = () => {
//         setNotifications((prev) =>
//             prev.map((n) => ({ ...n, read: true }))
//         );
//     };
//
//     const handleMarkAsRead = (item) => {
//         console.log(item);
//         readNotification(item.id)
//         // setNotifications((prev) =>
//         //     prev.map((n) => (n.id === id ? { ...n, read: true } : n))
//         // );
//     };
//
//     const handleRemove = (id) => {
//         setNotifications((prev) => prev.filter((n) => n.id !== id));
//     };
//
//     const handleGoTo = (id) => {
//         alert(`Đi đến thông báo ${id}`);
//     };
//
//     return (
//         <>
//             {loading ? (
//                 // <Spinner />
//                 <></>
//             ) : notifications.totalElements === 0 ? (
//                 <div className="text-center text-gray-500 py-6">Không có thông báo nào.</div>
//             ) : (
//                 <NotificationList
//                     data={notifications}
//                     markAllAsRead={handleMarkAllAsRead}
//                     markAsRead={handleMarkAsRead}
//                     remove={handleRemove}
//                     goTo={handleGoTo}
//                 />
//             )}
//         </>
//     );
// };
//
// export default NotificationListPage;

import React, { useEffect, useState } from 'react';
import NotificationList from '../../components/notification/NotificationList.jsx';
import { getNotifications, readNotification, markAllRead, archiveNotification } from '../../services/notificationService.js';
import { useNotifications, useSetNotifications } from '../../stores/notificationStore.js';

const NotificationListPage = () => {
    const [loading, setLoading] = useState(true);
    const notifications = useNotifications();
    const setNotifications = useSetNotifications();

    useEffect(() => {
        fetchNotifications();
    }, []);

    const fetchNotifications = async () => {
        try {
            setLoading(true);
            const response = await getNotifications();
            console.log('Notifications response:', response);
            setNotifications(response.data.result);
        } catch (error) {
            console.error('Error fetching notifications:', error);
            // Có thể thêm toast notification ở đây
        } finally {
            setLoading(false);
        }
    };

    const handleMarkAllAsRead = async () => {
        try {
            // Gọi API để đánh dấu tất cả là đã đọc
            await markAllRead();

            // Cập nhật state local
            setNotifications((prev) => ({
                ...prev,
                data: prev.data.map((n) => ({
                    ...n,
                    isRead: true,
                    readAt: new Date().toISOString()
                }))
            }));
        } catch (error) {
            console.error('Error marking all as read:', error);
            // Có thể thêm toast notification ở đây
        }
    };

    const handleMarkAsRead = async (item) => {
        if (item.isRead) return; // Đã đọc rồi thì không làm gì

        try {
            console.log('Marking as read:', item.id);
            await readNotification(item.id);

            // Cập nhật state local
            setNotifications((prev) => ({
                ...prev,
                data: prev.data.map((n) =>
                    n.id === item.id
                        ? {
                            ...n,
                            isRead: true,
                            readAt: new Date().toISOString()
                        }
                        : n
                )
            }));
        } catch (error) {
            console.error('Error marking notification as read:', error);
            // Có thể thêm toast notification ở đây
        }
    };

    const handleRemove = async (id) => {
        try {
            await archiveNotification(id);

            // Cập nhật state local
            setNotifications((prev) => ({
                ...prev,
                data: prev.data.filter((n) => n.id !== id),
                totalElements: prev.totalElements - 1
            }));
        } catch (error) {
            console.error('Error deleting notification:', error);
            // Có thể thêm toast notification ở đây
        }
    };

    const handleGoTo = (item) => {
        console.log('Navigate to notification:', item);

        // Đánh dấu đã đọc khi click vào
        if (!item.isRead) {
            handleMarkAsRead(item);
        }

        // Xử lý navigation dựa trên actionUrl hoặc notificationType
        if (item.actionUrl) {
            window.open(item.actionUrl, '_blank');
        } else {
            // Xử lý navigation dựa trên notification type
            switch (item.notificationType) {
                case 'STORY_LIKED':
                case 'STORY_COMMENTED':
                    // Navigate đến story detail
                    // router.push(`/story/${item.metadata?.storyId}`);
                    console.log('Navigate to story:', item.metadata);
                    break;
                default:
                    console.log('No specific navigation defined for:', item.notificationType);
            }
        }
    };

    // Component loading
    if (loading) {
        return (
            <div className="flex items-center justify-center min-h-96">
                <div className="flex flex-col items-center space-y-4">
                    <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
                    <p className="text-gray-600 dark:text-gray-400">Đang tải thông báo...</p>
                </div>
            </div>
        );
    }

    return (
        <div className="container mx-auto px-4 py-6">
            <NotificationList
                data={notifications}
                markAllAsRead={handleMarkAllAsRead}
                markAsRead={handleMarkAsRead}
                remove={handleRemove}
                goTo={handleGoTo}
            />
        </div>
    );
};

export default NotificationListPage;