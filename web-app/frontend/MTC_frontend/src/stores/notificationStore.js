import { create } from 'zustand';

import {defaultPagination} from '../constants/const.js';

const useNotificationStore = create((set) => ({
    notifications: { ...defaultPagination },
    unreadCount : 0,

    actions: {
        setNotifications: (notifications) => set({ notifications }),
        setUnreadCount: (count) => set({ unreadCount: count }),
        resetNotifications: () =>
            set({
                notifications: { ...defaultPagination },
                unreadCount: 0,
            }),
    },
}));

// Export hooks
export const useNotifications = () =>
    useNotificationStore((state) => state.notifications);

export const useUnreadCount = () =>
    useNotificationStore((state) => state.unreadCount);

export const useSetNotifications = () =>
    useNotificationStore((state) => state.actions.setNotifications);

export const useSetUnreadCount = () =>
    useNotificationStore((state) => state.actions.setUnreadCount);