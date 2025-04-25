import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

const ChatFrame = ({ onClose, children }) => {
    return (
        <div className="fixed bottom-4 right-4 w-[300px] h-[400px] bg-white shadow-lg rounded-xl z-50 flex flex-col">
            <div className="flex flex-row justify-between items-center p-2 bg-gray-200 rounded-xl">
                <div className="flex">
                    <span className="text-gray-700">Chatbot</span>
                </div>
                <div>
                    <FontAwesomeIcon icon={faXmark} onClick={onClose} />
                </div>
            </div>

            <div className="flex-1 overflow-y-auto p-2 bg-cus-gray rounded-b-xl">
                {children}
            </div>
        </div>

    );
};

export default ChatFrame;
