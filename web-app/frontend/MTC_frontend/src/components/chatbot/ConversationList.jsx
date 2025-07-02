import React, { useState, useEffect } from 'react';
import { chatService } from '../../services/chatService';
import useChatStore from '../../stores/newChatStore';

const ConversationList = ({ onSelect }) => {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const { getAllConversations, importConversationsFromServer, deleteConversation, activeConversationId } = useChatStore();

    const fetchConversations = async () => {
        try {
            setIsLoading(true);
            setError(null);
            const serverConversations = await chatService.getConversations();
            console.log('Server conversations:', serverConversations.length);
            // Import server conversations into store
            importConversationsFromServer(serverConversations);
        } catch (error) {
            // setError('Failed to load conversations');
            console.error('Error fetching conversations:', error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchConversations().then(r => {
            console.log("Fa");
        });
    }, []);

    const handleNewConversation = async () => {
        try {
            setIsLoading(true);
            const result = await chatService.createConversation('New Conversation');
            await fetchConversations(); // Refresh the list
            if (onSelect) onSelect(result.id);
        } catch (error) {
            setError('Failed to create conversation');
        } finally {
            setIsLoading(false);
        }
    };

    const handleDeleteConversation = (id, e) => {
        e.stopPropagation();
        deleteConversation(id);
        // In a real app, you might want to also delete from server
    };

    const conversations = getAllConversations();

    return (
        <div className="p-4 border rounded-lg bg-white shadow-sm">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-medium">Conversations</h2>
                <button
                    onClick={handleNewConversation}
                    disabled={isLoading}
                    className="text-sm bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded"
                >
                    New Chat
                </button>
            </div>

            {error && (
                <div className="mb-4 bg-red-50 p-2 rounded text-red-600 text-sm">
                    {error}
                </div>
            )}

            {isLoading && conversations.length === 0 ? (
                <div className="text-center py-4 text-gray-500">Loading conversations...</div>
            ) : conversations.length === 0 ? (
                <div className="text-center py-4 text-gray-500">No conversations found</div>
            ) : (
                <ul className="divide-y">
                    {conversations.map((conv) => (
                        <li
                            key={conv.id}
                            className={`py-3 px-2 hover:bg-gray-200 cursor-pointer rounded ${
                                activeConversationId === conv.id ? 'bg-blue-300' : ''
                            }`}
                            onClick={() => onSelect(conv.id)}
                        >
                            <div className="flex justify-between">
                                <div>
                                    <h3 className="font-medium base-text-color truncate w-48">{conv.title}</h3>
                                    <p className="text-xs text-gray-500">
                                        {new Date(conv.lastUpdated).toLocaleDateString()} •
                                        {conv.messageCount} messages
                                    </p>
                                </div>
                                <button
                                    onClick={(e) => handleDeleteConversation(conv.id, e)}
                                    className="text-gray-400 hover:text-red-500"
                                    title="Delete conversation"
                                >
                                    ✕
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ConversationList;