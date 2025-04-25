import React, { useEffect, useRef } from 'react';
import { CurrentUserMessage, OtherUserMessage, SystemMessage } from './ChatComponent.jsx';
import useChatStore from '../../stores/chatStore.js';

function getTimeFormat(date) {
    const options = { hour: '2-digit', minute: '2-digit' };
    return new Date(date).toLocaleTimeString([], options);
}

const ChatContainer = ({ conversationId, currentUserId }) => {
    const messagesEndRef = useRef(null);
    const { setActiveConversation, getActiveConversationMessages, markConversationAsRead } = useChatStore();

    useEffect(() => {
        // Set this as active conversation
        setActiveConversation(conversationId);
        markConversationAsRead(conversationId);
    }, [conversationId, setActiveConversation, markConversationAsRead]);

    // Auto-scroll to bottom when new messages arrive
    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [getActiveConversationMessages()]);

    const messages = getActiveConversationMessages();

    return (
        <div className="flex flex-col h-full">
            <div className="flex-1 overflow-y-auto py-2">
                {messages.map((msg) => {
                    if (msg.type === 'system') {
                        return <SystemMessage key={msg.id} message={msg.text} />;
                    } else if (msg.senderId === currentUserId) {
                        return (
                            <CurrentUserMessage
                                key={msg.id}
                                message={msg.text}
                                timestamp={getTimeFormat(msg.timestamp)}
                            />
                        );
                    } else {
                        return (
                            <OtherUserMessage
                                key={msg.id}
                                username={msg.senderName || 'User'}
                                message={msg.text}
                                timestamp={new Date(msg.timestamp).toLocaleTimeString()}
                            />
                        );
                    }
                })}
                <div ref={messagesEndRef} />
            </div>
        </div>
    );
};

export default ChatContainer;