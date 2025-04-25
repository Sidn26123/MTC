import React from "react"
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import { API } from '../configurations/configuration.js';
// import useStoryReaderStore from './novelAttributeStore.js';

const useUIStore = create((set) => ({
    isChatOpen: false,
    isSidebarOpen: false,
    // Thêm trạng thái khác tùy ý

    toggleChat: () => set((state) => ({ isChatOpen: !state.isChatOpen })),
    toggleSidebar: () => set((state) => ({ isSidebarOpen: !state.isSidebarOpen })),
    openChat: () => set({ isChatOpen: true }),
    closeChat: () => set({ isChatOpen: false }),


}));
export const useChatState = () => useUIStore((state) => state.isChatOpen);
// export const useChatActions = () => useUIStore((state) => state.actions);


export default useUIStore;