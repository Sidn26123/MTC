import React, { useState, useEffect, useRef } from 'react';
import { CurrentUserMessage, OtherUserMessage, SystemMessage } from './ChatComponent.jsx';
import useChatStore from '../../stores/newChatStore.js';
import { chatService } from '../../services/chatService.js';

const ChatComponent = ({ conversationId }) => {
    const [message, setMessage] = useState('');
    const messagesEndRef = useRef(null);
    const {
        setActiveConversation,
        addMessage,
        getActiveConversationMessages,
        setLoading,
        isLoading,
        error,
        setError
    } = useChatStore();

    // Scroll to bottom when new messages arrive
    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [getActiveConversationMessages()]);

    // Set active conversation when component mounts
    useEffect(() => {
        if (conversationId) {
            setActiveConversation(conversationId);

            // Fetch conversation from server if needed
            const fetchConversation = async () => {
                try {
                    setLoading(true);
                    const data = await chatService.getConversation(conversationId);

                    // Format messages and update store
                    const formattedMessages = data.messages.map(msg => ({
                        id: msg.id.toString(),
                        role: msg.role,
                        content: msg.content,
                        timestamp: msg.timestamp
                    }));

                    // Replace messages in the store
                    formattedMessages.forEach(msg => addMessage(msg));

                } catch (error) {
                    console.error('Error fetching conversation:', error);
                    setError(error.message);
                } finally {
                    setLoading(false);
                }
            };

            fetchConversation().then(r => {
                    console.log("F");
                }
            );
        }
    }, [conversationId]);

    const handleSendMessage = async (e) => {
        e.preventDefault();

        if (!message.trim()) return;

        // Add user message to the UI immediately
        const userMessage = {
            role: 'user',
            content: message.trim()
        };
        addMessage(userMessage);

        // Clear input
        setMessage('');

        try {
            // Send message to the server with callbacks
            await chatService.sendMessage(message.trim(), conversationId, 'llama3.2:1b', {
                onStart: () => setLoading(true),
                onSuccess: (data) => {
                    // Add assistant message from the response
                    addMessage({
                        role: 'assistant',
                        content: data.response
                    });

                    // If this is a new conversation, update conversation ID
                    if (!conversationId) {
                        setActiveConversation(data.conversation_id);
                        // You might want to update the URL here if needed
                    }
                },
                onError: (error) => setError(error.message),
                onFinish: () => setLoading(false)
            });
        } catch (error) {
            // Error handling is done in callbacks
            console.error('Message sending failed:', error);
        }
    };

    const messages = getActiveConversationMessages();

    return (
        <div className="flex flex-col h-full">
            <div className="flex-1 overflow-y-auto py-2">
                {/* Show loading state */}
                {isLoading && messages.length === 0 && (
                    <div className="flex justify-center my-4">
                        <div className="bg-gray-100 p-2 rounded-lg">Loading...</div>
                    </div>
                )}

                {/* Show error if any */}
                {error && (
                    <div className="flex justify-center my-2">
                        <div className="bg-red-100 text-red-800 p-2 rounded-lg">
                            Error: {error}
                        </div>
                    </div>
                )}

                {/* Render messages */}
                {messages
                    .filter(msg => msg.role !== 'system') // Don't show system messages
                    .map((msg) => (
                        msg.role === 'user' ? (
                            <CurrentUserMessage
                                key={msg.id}
                                message={msg.content}
                                timestamp={new Date(msg.timestamp).toLocaleTimeString()}
                            />
                        ) : (
                            <OtherUserMessage
                                key={msg.id}
                                username="Assistant"
                                message={msg.content}
                                timestamp={new Date(msg.timestamp).toLocaleTimeString()}
                            />
                        )
                    ))
                }

                {/* Show typing indicator when loading */}
                {isLoading && messages.length > 0 && (
                    <div className="flex items-start mb-2">
                        <div className="bg-gray-200 px-3 py-2 rounded-lg">
                            <div className="typing-indicator">
                                <span></span>
                                <span></span>
                                <span></span>
                            </div>
                        </div>
                    </div>
                )}

                {/* Auto-scroll target */}
                <div ref={messagesEndRef} />
            </div>

            {/* Message input */}
            <form onSubmit={handleSendMessage} className="border-t p-2 flex">
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Ask about your documents..."
                    className="flex-1 border rounded-full px-3 py-1 border-solid border-gray-300 focus:outline-none focus:ring-1 focus:ring-blue-500"
                    disabled={isLoading}
                />
                <button
                    type="submit"
                    className={`ml-2 ${isLoading ? 'bg-gray-400' : 'bg-blue-500'} text-white rounded-full w-8 h-8 flex items-center justify-center`}
                    disabled={isLoading || !message.trim()}
                >
                    {isLoading ? (
                        <span className="animate-spin">↻</span>
                    ) : (
                        <span>→</span>
                    )}
                </button>
            </form>
        </div>
    );
};

export default ChatComponent;