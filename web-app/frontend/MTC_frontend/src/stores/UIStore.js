import React from "react"
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import { API } from '../configurations/configuration.js';
// import useStoryReaderStore from './novelAttributeStore.js';

const useUIStore = create((set) => ({
    isChatOpen: false,
    isSidebarOpen: false,
    isNovelFilterPanelOpen: false,
    // Thêm trạng thái khác tùy ý

    toggleChat: () => set((state) => ({ isChatOpen: !state.isChatOpen })),
    toggleSidebar: () => set((state) => ({ isSidebarOpen: !state.isSidebarOpen })),
    toggleNovelFilterPanel: () => set((state) => ({ isNovelFilterPanelOpen: !state.isNovelFilterPanelOpen })),
    openChat: () => set({ isChatOpen: true }),
    closeChat: () => set({ isChatOpen: false }),
    openSidebar: () => set({ isSidebarOpen: true }),
    closeSidebar: () => set({ isSidebarOpen: false }),
    openNovelFilterPanel: () => set({ isNovelFilterPanelOpen: true }),
    closeNovelFilterPanel: () => set({ isNovelFilterPanelOpen: false }),


}));
export const useChatState = () => useUIStore((state) => state.isChatOpen);
// export const useChatActions = () => useUIStore((state) => state.actions);

export const useToggleNovelFilterPanel = () => useUIStore((state) => state.toggleNovelFilterPanel);

export const openNovelFilterPanel = () => useUIStore((state) => state.openNovelFilterPanel);

export default useUIStore;