import { Outlet } from "react-router-dom";
import UserNavbar from "../components/global/Navbar.jsx";
import Footer from "../components/global/Footer.jsx";
import ChatFrame from '../components/chatbot/ChatFrame.jsx';
import { useState } from 'react';
import useUIStore, {useChatState } from '../stores/UIStore.js';
import UpdatedChatFrame from '../components/chatbot/UpdatedChatFrame.jsx';
// import UserSidebar from "../components/UserSidebar";

const UserLayout = () => {
    const isChatOpen = useChatState();
    const closeChatFrame = useUIStore((state) => state.closeChat);

    const currentUser = {
        id: 'user-123',
        name: 'Current User'
    };


    return (
        <div className={"background-color "}>
            <div>
                <UserNavbar />
                <div className="px-20 ">
                    {/*<main className="">*/}
                        <Outlet />
                    {/*</main>*/}
                </div>
                {/*{isChatOpen && (*/}
                {/*    <ChatFrame onClose = {() => closeChatFrame()}/>*/}
                {/*)}*/}
                {isChatOpen && (
                    <UpdatedChatFrame
                        onClose={() => closeChatFrame()}
                        conversationId="chat-1" // This could be dynamic based on who you're chatting with
                        currentUser={currentUser}
                    />
                )}
                <Footer />
            </div>

        </div>
    );
};

export default UserLayout;
