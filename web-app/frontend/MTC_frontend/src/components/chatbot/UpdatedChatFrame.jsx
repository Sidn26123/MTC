import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import ChatContainer from './ChatContainer';
import MessageInput from './MessageInput.jsx';

const UpdatedChatFrame = ({ onClose, conversationId, currentUser }) => {
    return (
        <div className="fixed bottom-4 right-4 w-[300px] h-[400px] bg-white shadow-lg rounded-xl z-50 flex flex-col">
            <div className="flex flex-row justify-between items-center p-2 bg-gray-200 rounded-t-xl">
                <div className="flex">
                    <span className="font-medium base-text-color">Chatbot</span>
                </div>
                <div>
                    <FontAwesomeIcon
                        icon={faXmark}
                        onClick={onClose}
                        className="cursor-pointer base-text-color hover:text-gray-800 "
                    />
                </div>
            </div>

            <div className="flex-1 overflow-hidden flex flex-col">
                <ChatContainer
                    conversationId={conversationId}
                    currentUserId={currentUser.id}
                />
                <MessageInput
                    conversationId={conversationId}
                    currentUser={currentUser}
                />
            </div>
        </div>
    );
};

export default UpdatedChatFrame;
