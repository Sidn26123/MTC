import React, { useEffect, useState } from 'react';
import NotificationList from '../../components/notification/NotificationList.jsx';
import { getNotifications } from '../../services/notificationService.js';
import { useNotifications, useSetNotifications } from '../../stores/notificationStore.js'; // giả sử component con đã có
// import { Spinner } from './Spinner'; // component loading đơn giản (bạn có thể tạo thêm)

const NotificationListPage = () => {
    const [loading, setLoading] = useState(true);
    // const [notifications, setNotifications] = useState([]);
    const notifications = useNotifications();
    const setNotifications = useSetNotifications();
    // Giả lập gọi API
    // useEffect(() => {
    //     setLoading(true);
    //     setTimeout(() => {
    //         const mockData = [
    //             {
    //                 id: 1,
    //                 title: 'Bạn có chương mới',
    //                 content: 'Truyện "Thiên Hạ Vô Song" vừa cập nhật chương 120',
    //                 read: false,
    //             },
    //             {
    //                 id: 2,
    //                 title: 'Thông báo hệ thống',
    //                 content: 'Chào mừng bạn quay lại!',
    //                 read: true,
    //             },
    //         ];
    //         setNotifications(mockData); // hoặc [] để test empty state
    //         setLoading(false);
    //     }, 1000);
    // }, []);

    useEffect(() => {
        getNotifications().then(r => {
            console.log('r', r);
            setNotifications(r.data.result);
            setLoading(false);
        });
    }, [] );

    const handleMarkAllAsRead = () => {
        setNotifications((prev) =>
            prev.map((n) => ({ ...n, read: true }))
        );
    };

    const handleMarkAsRead = (id) => {
        setNotifications((prev) =>
            prev.map((n) => (n.id === id ? { ...n, read: true } : n))
        );
    };

    const handleRemove = (id) => {
        setNotifications((prev) => prev.filter((n) => n.id !== id));
    };

    const handleGoTo = (id) => {
        alert(`Đi đến thông báo ${id}`);
    };

    return (
        <>
            {loading ? (
                // <Spinner />
                <></>
            ) : notifications.totalElements === 0 ? (
                <div className="text-center text-gray-500 py-6">Không có thông báo nào.</div>
            ) : (
                <NotificationList
                    data={notifications}
                    markAllAsRead={handleMarkAllAsRead}
                    markAsRead={handleMarkAsRead}
                    remove={handleRemove}
                    goTo={handleGoTo}
                />
            )}
        </>
    );
};

export default NotificationListPage;
