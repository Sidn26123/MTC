
// ChatMessage.jsx - Components for different message types
import React from 'react';

// Message component for the current user (right-aligned)
export const CurrentUserMessage = ({ message, timestamp }) => {
    return (
        <div className="flex flex-col items-end mb-2">
            <div className="bg-blue-600 text-white px-3 py-2 rounded-lg max-w-[80%] break-words">
                {message}
            </div>
            <span className="text-xs text-gray-500 mt-1">{timestamp}</span>
        </div>
    );
};

// Message component for other users (left-aligned)
export const OtherUserMessage = ({ username, message, timestamp }) => {
    return (
        <div className="flex flex-col items-start mb-2">
            <span className="text-xs font-semibold text-gray-700">{username}</span>
            <div className="bg-gray-200 px-3 py-2 rounded-lg max-w-[80%] break-words">
                {message}
            </div>
            <span className="text-xs text-gray-500 mt-1">{timestamp}</span>
        </div>
    );
};

// System message (centered)
export const SystemMessage = ({ message }) => {
    return (
        <div className="flex justify-center my-2">
            <div className="bg-gray-100 text-gray-500 text-xs px-3 py-1 rounded-full">
                {message}
            </div>
        </div>
    );
};