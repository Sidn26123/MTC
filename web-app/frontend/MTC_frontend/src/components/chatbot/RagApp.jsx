import React, { useState } from 'react';
import ChatComponent from './ChatCompo';
import DocumentUploader from './DocumentUploader';
import DocumentList from './DocumentList';
import ConversationList from './ConversationList';

const RAGApp = () => {
    const [activeConversationId, setActiveConversationId] = useState(null);
    const [showUploader, setShowUploader] = useState(false);

    return (
        <div className="container base-text-color mx-auto p-4">
            <div className="flex flex-col lg:flex-row gap-4">
                {/* Sidebar */}
                <div className="lg:w-1/4 space-y-4">
                    <ConversationList onSelect={setActiveConversationId} />

                    <DocumentList />

                    <button
                        onClick={() => setShowUploader(!showUploader)}
                        className="w-full py-2 bg-gray-300 base-text-color hover:bg-gray-500 rounded text-center"
                    >
                        {showUploader ? 'Hide Document Uploader' : 'Upload New Document'}
                    </button>

                    {showUploader && (
                        <DocumentUploader onSuccess={() => setShowUploader(false)} />
                    )}
                </div>

                {/* Main chat area */}
                <div className="lg:w-3/4 bg-white border rounded-lg shadow-sm h-[600px]">
                    {activeConversationId ? (
                        <ChatComponent conversationId={activeConversationId} />
                    ) : (
                        <div className="flex items-center justify-center h-full text-gray-500">
                            Select a conversation or start a new one
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default RAGApp;