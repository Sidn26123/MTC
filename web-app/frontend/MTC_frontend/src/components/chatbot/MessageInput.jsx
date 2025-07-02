import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import useChatStore from '../../stores/chatStore.js';

const MessageInput = ({ conversationId, currentUser }) => {
    const [message, setMessage] = useState('');
    const { addMessage } = useChatStore();

    const handleSendMessage = (e) => {
        e.preventDefault();

        if (message.trim()) {
            addMessage(conversationId, {
                type: 'user',
                text: message.trim(),
                senderId: currentUser.id,
                senderName: currentUser.name,
            });

            setMessage('');
        }
    };

    return (
        <form onSubmit={handleSendMessage} className="border-t p-2 flex">
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type a message..."
                className="flex-1 border rounded-full px-3 py-1 base-text-color"
            />
            <button
                type="submit"
                className="ml-2 bg-blue-500 text-white rounded-full w-8 h-8 flex items-center justify-center"
                disabled={!message.trim()}
            >
                <FontAwesomeIcon icon={faPaperPlane} size="sm" />
            </button>
        </form>
    );
};

export default MessageInput;
