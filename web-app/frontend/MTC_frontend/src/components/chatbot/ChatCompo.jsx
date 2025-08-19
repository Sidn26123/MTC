// import React, { useState, useEffect, useRef } from 'react';
// import { CurrentUserMessage, OtherUserMessage, SystemMessage } from './ChatComponent.jsx';
// import useChatStore from '../../stores/newChatStore.js';
// import { chatService } from '../../services/chatService.js';
//
// const ChatComponent = ({ conversationId }) => {
//     const [message, setMessage] = useState('');
//     const messagesEndRef = useRef(null);
//     const {
//         setActiveConversation,
//         addMessage,
//         getActiveConversationMessages,
//         setLoading,
//         isLoading,
//         error,
//         setError
//     } = useChatStore();
//
//     // Scroll to bottom when new messages arrive
//     useEffect(() => {
//         messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
//     }, [getActiveConversationMessages()]);
//
//     // Set active conversation when component mounts
//     useEffect(() => {
//         if (conversationId) {
//             setActiveConversation(conversationId);
//
//             // Fetch conversation from server if needed
//             const fetchConversation = async () => {
//                 try {
//                     setLoading(true);
//                     const data = await chatService.getConversation(conversationId);
//
//                     // Format messages and update store
//                     const formattedMessages = data.messages.map(msg => ({
//                         id: msg.id.toString(),
//                         role: msg.role,
//                         content: msg.content,
//                         timestamp: msg.timestamp
//                     }));
//
//                     // Replace messages in the store
//                     formattedMessages.forEach(msg => addMessage(msg));
//
//                 } catch (error) {
//                     console.error('Error fetching conversation:', error);
//                     setError(error.message);
//                 } finally {
//                     setLoading(false);
//                 }
//             };
//
//             fetchConversation().then(r => {
//                     console.log("F");
//                 }
//             );
//         }
//     }, [conversationId]);
//
//     const handleSendMessage = async (e) => {
//         e.preventDefault();
//
//         if (!message.trim()) return;
//
//         // Add user message to the UI immediately
//         const userMessage = {
//             role: 'user',
//             content: message.trim()
//         };
//         addMessage(userMessage);
//
//         // Clear input
//         setMessage('');
//
//         try {
//             // Send message to the server with callbacks
//             await chatService.sendMessage(message.trim(), conversationId, 'llama3.2:1b', {
//                 onStart: () => setLoading(true),
//                 // onSuccess: (data) => {
//                 //     // Add assistant message from the response
//                 //     addMessage({
//                 //         role: 'assistant',
//                 //         content: data.response
//                 //     });
//                 //
//                 //     // If this is a new conversation, update conversation ID
//                 //     if (!conversationId) {
//                 //         setActiveConversation(data.conversation_id);
//                 //         // You might want to update the URL here if needed
//                 //     }
//                 // },
//                 onSuccess: (data) => {
//                     // Add assistant message
//                     addMessage({
//                         role: 'assistant',
//                         content: data.answer
//                     });
//
//                     // Thêm metadata để DocumentList hiển thị
//                     if (data.relevant_docs?.length) {
//                         addMessage({
//                             role: 'system',
//                             content: JSON.stringify({
//                                 type: 'related_docs',
//                                 docs: data.relevant_docs
//                             })
//                         });
//                     }
//
//                     if (!conversationId) {
//                         setActiveConversation(data.conversation_id);
//                     }
//                 },
//
//                 onError: (error) => setError(error.message),
//                 onFinish: () => setLoading(false)
//             });
//         } catch (error) {
//             // Error handling is done in callbacks
//             console.error('Message sending failed:', error);
//         }
//     };
//
//     const messages = getActiveConversationMessages();
//
//     return (
//         <div className="flex flex-col h-full">
//             <div className="flex-1 overflow-y-auto py-2">
//                 {/* Show loading state */}
//                 {isLoading && messages.length === 0 && (
//                     <div className="flex justify-center my-4">
//                         <div className="bg-gray-100 p-2 rounded-lg">Loading...</div>
//                     </div>
//                 )}
//
//                 {/* Show error if any */}
//                 {error && (
//                     <div className="flex justify-center my-2">
//                         <div className="bg-red-100 text-red-800 p-2 rounded-lg">
//                             Error: {error}
//                         </div>
//                     </div>
//                 )}
//
//                 {/* Render messages */}
//                 {messages
//                     .filter(msg => msg.role !== 'system') // Don't show system messages
//                     .map((msg) => (
//                         msg.role === 'user' ? (
//                             <CurrentUserMessage
//                                 key={msg.id}
//                                 message={msg.content}
//                                 timestamp={new Date(msg.timestamp).toLocaleTimeString()}
//                             />
//                         ) : (
//                             <OtherUserMessage
//                                 key={msg.id}
//                                 username="Assistant"
//                                 message={msg.content}
//                                 timestamp={new Date(msg.timestamp).toLocaleTimeString()}
//                             />
//                         )
//                     ))
//                 }
//
//                 {/* Show typing indicator when loading */}
//                 {isLoading && messages.length > 0 && (
//                     <div className="flex items-start mb-2">
//                         <div className="bg-gray-200 px-3 py-2 rounded-lg">
//                             <div className="typing-indicator">
//                                 <span></span>
//                                 <span></span>
//                                 <span></span>
//                             </div>
//                         </div>
//                     </div>
//                 )}
//
//                 {/* Auto-scroll target */}
//                 <div ref={messagesEndRef} />
//             </div>
//
//             {/* Message input */}
//             <form onSubmit={handleSendMessage} className="border-t p-2 flex">
//                 <input
//                     type="text"
//                     value={message}
//                     onChange={(e) => setMessage(e.target.value)}
//                     placeholder="Ask about your documents..."
//                     className="flex-1 border rounded-full px-3 py-1 border-solid border-gray-300 focus:outline-none focus:ring-1 focus:ring-blue-500"
//                     disabled={isLoading}
//                 />
//                 <button
//                     type="submit"
//                     className={`ml-2 ${isLoading ? 'bg-gray-400' : 'bg-blue-500'} text-white rounded-full w-8 h-8 flex items-center justify-center`}
//                     disabled={isLoading || !message.trim()}
//                 >
//                     {isLoading ? (
//                         <span className="animate-spin">↻</span>
//                     ) : (
//                         <span>→</span>
//                     )}
//                 </button>
//             </form>
//         </div>
//     );
// };
//
// export default ChatComponent;

import React, { useState, useEffect, useRef } from 'react';
import { CurrentUserMessage, OtherUserMessage, SystemMessage } from './ChatComponent.jsx';
import { Search, Book, FileText, ChevronRight, Star } from 'lucide-react';
import useChatStore from '../../stores/newChatStore.js';
import { chatService } from '../../services/chatService.js';
import DocumentViewer from './DocumentViewer.jsx';

const ChatComponent = ({ conversationId }) => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const [mode, setMode] = useState('policy'); // 'policy' or 'story'
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

    function handleAddMessage(msg) {
        setMessages(prevMessages => [...prevMessages, msg]);
    }

    const handleSendMessage = async (e) => {
        e.preventDefault();

        if (!message.trim()) return;

        // Add user message to the UI immediately
        const userMessage = {
            role: 'user',
            content: message.trim()
        };
        handleAddMessage(userMessage);

        // Clear input
        setMessage('');
        if (mode === 'policy'){
            try {
                // Send message to the server with callbacks
                await chatService.sendMessageRAG(message.trim(), conversationId, 'llama3.2:1b', {
                    onStart: () => setLoading(true),
                    // onSuccess: (data) => {
                    //     // Add assistant message from the response
                    //     addMessage({
                    //         role: 'assistant',
                    //         content: data.response
                    //     });
                    //
                    //     // If this is a new conversation, update conversation ID
                    //     if (!conversationId) {
                    //         setActiveConversation(data.conversation_id);
                    //         // You might want to update the URL here if needed
                    //     }
                    // },
                    onSuccess: (data) => {
                        // Add assistant message
                        console.log("Received data:", data);
                        // addMessage({
                        //     role: 'assistant',
                        //     content: data.answer
                        // });

                        // Thêm metadata để DocumentList hiển thị
                        handleAddMessage({
                            role: 'assistant',
                            data: data
                        });


                        if (!conversationId) {
                            setActiveConversation(data.conversation_id);
                        }
                    },

                    onError: (error) => setError(error.message),
                    onFinish: () => setLoading(false)
                });
            } catch (error) {
                // Error handling is done in callbacks
                console.error('Message sending failed:', error);
            }
        }
        else if (mode === 'story') {
            try {
                // Send message to the server with callbacks
                await chatService.sendMessageType2(message.trim(), 'llama3.2:1b', {
                    onStart: () => setLoading(true),
                    // onSuccess: (data) => {
                    //     // Add assistant message from the response
                    //     addMessage({
                    //         role: 'assistant',
                    //         content: data.response
                    //     });
                    //
                    //     // If this is a new conversation, update conversation ID
                    //     if (!conversationId) {
                    //         setActiveConversation(data.conversation_id);
                    //         // You might want to update the URL here if needed
                    //     }
                    // },
                    onSuccess: (data) => {
                        // Add assistant message
                        console.log("Received data:", data);
                        // addMessage({
                        //     role: 'assistant',
                        //     content: data.answer
                        // });

                        // Thêm metadata để DocumentList hiển thị
                        handleAddMessage({
                            role: 'assistant',
                            data: data
                        });


                        if (!conversationId) {
                            setActiveConversation(data.conversation_id);
                        }
                    },

                    onError: (error) => setError(error.message),
                    onFinish: () => setLoading(false)
                });
            } catch (error) {
                // Error handling is done in callbacks
                console.error('Message sending failed:', error);
            }
        }

    };

    // const messages = getActiveConversationMessages();

    // return (
    //     <div className="flex flex-col h-full">
    //         <div className="flex-1 overflow-y-auto py-2">
    //             {/* Show loading state */}
    //             {isLoading && messages.length === 0 && (
    //                 <div className="flex justify-center my-4">
    //                     <div className="bg-gray-100 p-2 rounded-lg">Loading...</div>
    //                 </div>
    //             )}
    //
    //             {/* Show error if any */}
    //             {error && (
    //                 <div className="flex justify-center my-2">
    //                     <div className="bg-red-100 text-red-800 p-2 rounded-lg">
    //                         Error: {error}
    //                     </div>
    //                 </div>
    //             )}
    //
    //             {/* Render messages */}
    //             {messages
    //                 .filter(msg => msg.role !== 'system') // Don't show system messages
    //                 .map((msg) => (
    //                     console.log("Rendering message:", msg),
    //                     msg.role === 'user' ? (
    //                         <CurrentUserMessage
    //                             key={msg.id}
    //                             message={msg.content}
    //                             timestamp={new Date(msg.timestamp).toLocaleTimeString()}
    //                         />
    //                     ) : (
    //                         // <OtherUserMessage
    //                         //     key={msg.id}
    //                         //     username="Assistant"
    //                         //     message={msg.content}
    //                         //     timestamp={new Date(msg.timestamp).toLocaleTimeString()}
    //                         // />
    //                         <DocumentViewer data = {msg}/>
    //                     )
    //                 ))
    //             }
    //
    //             {/* Show typing indicator when loading */}
    //             {isLoading && messages.length > 0 && (
    //                 <div className="flex items-start mb-2">
    //                     <div className="bg-gray-200 px-3 py-2 rounded-lg">
    //                         <div className="typing-indicator">
    //                             <span></span>
    //                             <span></span>
    //                             <span></span>
    //                         </div>
    //                     </div>
    //                 </div>
    //             )}
    //
    //             {/* Auto-scroll target */}
    //             <div ref={messagesEndRef} />
    //         </div>
    //
    //         {/* Message input */}
    //         <form onSubmit={handleSendMessage} className="border-t p-2 flex">
    //             <input
    //                 type="text"
    //                 value={message}
    //                 onChange={(e) => setMessage(e.target.value)}
    //                 placeholder="Ask about your documents..."
    //                 className="flex-1 border rounded-full px-3 py-1 border-solid border-gray-300 focus:outline-none focus:ring-1 focus:ring-blue-500"
    //                 disabled={isLoading}
    //             />
    //             <button
    //                 type="submit"
    //                 className={`ml-2 ${isLoading ? 'bg-gray-400' : 'bg-blue-500'} text-white rounded-full w-8 h-8 flex items-center justify-center`}
    //                 disabled={isLoading || !message.trim()}
    //             >
    //                 {isLoading ? (
    //                     <span className="animate-spin">↻</span>
    //                 ) : (
    //                     <span>→</span>
    //                 )}
    //             </button>
    //         </form>
    //     </div>
    // );

    const StoryResultCard = ({ result, index }) => (
        console.log("Rendering story result:", result),
        <div className="bg-purple-50 border border-purple-200 rounded-lg p-4 mb-3">
            <div className="flex items-center justify-between mb-2">
                <div className="flex items-center gap-2">
                    <Book className="w-4 h-4 text-purple-600" />
                    <span className="font-semibold text-purple-800">Chương {result?.chapter_idx}</span>
                </div>
                <div className="flex items-center gap-1">
                    <div className="w-2 h-2 bg-purple-400 rounded-full"></div>
                    <span className="text-xs text-gray-600">{(result?.similarity_score * 100).toFixed(1)}%</span>
                </div>
            </div>
            <p className="text-gray-700 text-sm leading-relaxed line-clamp-3">{result?.content_preview}</p>
            <div className="mt-3 flex justify-between items-center">
                <span className="text-xs text-gray-500">ID: {result?.chapter_id.slice(0, 8)}...</span>
                <button className="text-purple-600 hover:text-purple-800 text-xs font-medium flex items-center gap-1">
                    Đọc Chương <ChevronRight className="w-3 h-3" />
                </button>
            </div>
        </div>
    );
    return (
        <div className="flex flex-col h-full bg-gray-50">
            {/* Mode Toggle Header */}
            <div className="bg-white border-b border-gray-200 p-4">
                <div className="flex items-center justify-center">
                    <div className="bg-gray-100 p-1 rounded-lg flex">
                        <button
                            onClick={() => setMode('policy')}
                            className={`flex items-center gap-2 px-4 py-2 rounded-md text-sm font-medium transition-all ${
                                mode === 'policy'
                                    ? 'bg-blue-600 text-white shadow-sm'
                                    : 'text-gray-600 hover:text-blue-600'
                            }`}
                        >
                            <FileText className="w-4 h-4" />
                            Policy Search
                        </button>
                        <button
                            onClick={() => setMode('story')}
                            className={`flex items-center gap-2 px-4 py-2 rounded-md text-sm font-medium transition-all ${
                                mode === 'story'
                                    ? 'bg-purple-600 text-white shadow-sm'
                                    : 'text-gray-600 hover:text-purple-600'
                            }`}
                        >
                            <Book className="w-4 h-4" />
                            Tìm Truyện
                        </button>
                    </div>
                </div>
                <p className="text-center text-xs text-gray-500 mt-2">
                    {mode === 'policy' ?
                        'Tìm kiếm trong tài liệu chính sách và quy định' :
                        'Tìm kiếm nội dung trong các chương truyện'
                    }
                </p>
            </div>

            {/* Messages Area */}
            <div className="flex-1 overflow-y-auto p-4">
                {/* Show loading state when no messages */}
                {isLoading && messages.length === 0 && (
                    <div className="flex justify-center my-4">
                        <div className="bg-gray-100 p-2 rounded-lg">Loading...</div>
                    </div>
                )}

                {/* Show error if any */}
                {error && (
                    <div className="flex justify-center mb-4">
                        <div className="bg-red-100 text-red-800 p-3 rounded-lg text-sm">
                            Lỗi: {error}
                        </div>
                    </div>
                )}

                {/* Empty state */}
                {messages.length === 0 && !isLoading && (
                    <div className="flex flex-col items-center justify-center h-full text-center">
                        <div className={`w-16 h-16 rounded-full flex items-center justify-center mb-4 ${
                            mode === 'policy' ? 'bg-blue-100' : 'bg-purple-100'
                        }`}>
                            {mode === 'policy' ?
                                <FileText className="w-8 h-8 text-blue-600" /> :
                                <Book className="w-8 h-8 text-purple-600" />
                            }
                        </div>
                        <h3 className="text-lg font-medium text-gray-800 mb-2">
                            {mode === 'policy' ? 'Policy Search Assistant' : 'Trợ lý Tìm Truyện'}
                        </h3>
                        <p className="text-gray-500 text-sm max-w-md">
                            {mode === 'policy' ?
                                'Hỏi tôi về các chính sách, quy định và tài liệu hướng dẫn của công ty.' :
                                'Tìm kiếm nhân vật, sự kiện hoặc nội dung trong các chương truyện.'
                            }
                        </p>
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
                            ) : mode === 'policy' ? (
                                <DocumentViewer key={msg.id} data={msg}/>
                            ) : (
                                // {data?.results && msg.data.results.length > 0 && (
                                //         msg.data.results.map((item, idx) => (
                                //             <StoryResultCard
                                //                 key={item.chapter_id}
                                //                 result={item}
                                //                 index={idx + 1}
                                //             />
                                //         ))
                                //     )}
                                msg.data.results && msg.data.results.length > 0 && msg.data.results.map((item, idx) => (
                                    <StoryResultCard
                                        key={item.chapter_id}
                                        result={item}
                                        index={idx + 1}
                                    />
                                ))
                            )
                    ))
                }

                {/* Show typing indicator when loading and messages exist */}
                {isLoading && messages.length > 0 && (
                    <div className="flex justify-start mb-6">
                        <div className={`rounded-2xl rounded-bl-md p-4 ${
                            mode === 'policy' ? 'bg-blue-100' : 'bg-purple-100'
                        }`}>
                            <div className="flex items-center gap-3">
                                <div className="animate-spin rounded-full h-4 w-4 border-2 border-gray-400 border-t-transparent"></div>
                                <span className="text-sm text-gray-600">
                                {mode === 'policy' ? 'Đang tìm kiếm chính sách...' : 'Đang tìm trong truyện...'}
                            </span>
                            </div>
                        </div>
                    </div>
                )}

                {/* Auto-scroll target */}
                <div ref={messagesEndRef} />
            </div>

            {/* Input Area */}
            <div className="bg-white border-t border-gray-200 p-4">
                <form onSubmit={handleSendMessage} className="flex gap-3">
                    <div className="flex-1 relative">
                        <input
                            type="text"
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            placeholder={mode === 'policy' ?
                                'Hỏi về chính sách công ty...' :
                                'Tìm nhân vật, sự kiện trong truyện...'
                            }
                            className="w-full border border-gray-300 rounded-full px-4 py-3 pr-12 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            disabled={isLoading}
                        />
                        <Search className="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
                    </div>
                    <button
                        type="submit"
                        className={`px-6 py-3 rounded-full text-white font-medium transition-all ${
                            isLoading || !message.trim()
                                ? 'bg-gray-400 cursor-not-allowed'
                                : mode === 'policy'
                                    ? 'bg-blue-600 hover:bg-blue-700'
                                    : 'bg-purple-600 hover:bg-purple-700'
                        }`}
                        disabled={isLoading || !message.trim()}
                    >
                        {isLoading ? (
                            <div className="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent" />
                        ) : (
                            'Gửi'
                        )}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ChatComponent;

//
// import React, { useState, useEffect, useRef } from 'react';
// import { Search, Book, FileText, ChevronRight, Star } from 'lucide-react';
// import useChatStore from '../../stores/chatStore.js';
// import { chatService } from '../../services/chatService.js';
//
// const ChatComponent = ({ conversationId }) => {
//
//     const [message, setMessage] = useState('');
//     const [messages, setMessages] = useState([]);
//     const [mode, setMode] = useState('policy'); // 'policy' or 'story'
//     // const [isLoading, setIsLoading] = useState(false);
//     // const [error, setError] = useState('');
//     const messagesEndRef = useRef(null);
//
//     const {
//         setActiveConversation,
//         addMessage,
//         getActiveConversationMessages,
//         setLoading,
//         isLoading,
//         error,
//         setError
//     } = useChatStore();
//
//     // Scroll to bottom when new messages arrive
//     // useEffect(() => {
//     //     messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
//     // }, [getActiveConversationMessages()]);
//
//     // Set active conversation when component mounts
//
//     function handleAddMessage(msg) {
//         setMessages(prevMessages => [...prevMessages, msg]);
//     }
//
//     const handleSendMessage = async (e) => {
//         e.preventDefault();
//
//         if (!message.trim()) return;
//
//         // Add user message to the UI immediately
//         const userMessage = {
//             role: 'user',
//             content: message.trim()
//         };
//         handleAddMessage(userMessage);
//
//         // Clear input
//         setMessage('');
//
//         try {
//             // Send message to the server with callbacks
//             await chatService.sendMessageRAG(message.trim(), conversationId, 'llama3.2:1b', {
//                 onStart: () => setLoading(true),
//                 // onSuccess: (data) => {
//                 //     // Add assistant message from the response
//                 //     addMessage({
//                 //         role: 'assistant',
//                 //         content: data.response
//                 //     });
//                 //
//                 //     // If this is a new conversation, update conversation ID
//                 //     if (!conversationId) {
//                 //         setActiveConversation(data.conversation_id);
//                 //         // You might want to update the URL here if needed
//                 //     }
//                 // },
//                 onSuccess: (data) => {
//                     // Add assistant message
//                     console.log("Received data:", data);
//                     // addMessage({
//                     //     role: 'assistant',
//                     //     content: data.answer
//                     // });
//
//                     // Thêm metadata để DocumentList hiển thị
//                     handleAddMessage({
//                         role: 'assistant',
//                         data: data
//                     });
//
//
//                     if (!conversationId) {
//                         setActiveConversation(data.conversation_id);
//                     }
//                 },
//
//                 onError: (error) => setError(error.message),
//                 onFinish: () => setLoading(false)
//             });
//         } catch (error) {
//             // Error handling is done in callbacks
//             console.error('Message sending failed:', error);
//         }
//     };
//
//     // const messages = getActiveConversationMessages();
//
//
//     // Mock data for demonstration
//     const mockPolicyResponse = {
//         query: "Company policy on remote work",
//         total_results: 5,
//         results: [
//             {
//                 section: "HR Policies",
//                 content_preview: "Remote work is allowed for eligible employees with manager approval...",
//                 relevance_score: 0.92,
//                 document_id: "policy-001"
//             },
//             {
//                 section: "IT Guidelines",
//                 content_preview: "All remote workers must use VPN and follow security protocols...",
//                 relevance_score: 0.85,
//                 document_id: "policy-002"
//             }
//         ]
//     };
//
//     const mockStoryResponse = {
//         query: "Thiên Ma Truyền Kỳ xuất hiện ở chương nào",
//         total_results: 10,
//         results: [
//             {
//                 chapter_idx: 14,
//                 content_preview: "\"Thiên Ma Truyền Kỳ\" là một trong những bộ truyện hấp dẫn nhất thuộc thể loại huyền huyễn hiện đại...",
//                 similarity_score: 0.512499988079071,
//                 chapter_id: "062b0ef9-5f37-4623-bf21-59e99294c20e"
//             },
//             {
//                 chapter_idx: 2,
//                 content_preview: "Nội dung chương hai với những chi tiết thú vị về nhân vật chính...",
//                 similarity_score: 0.5005999803543091,
//                 chapter_id: "b14769da-180d-4527-bafb-948e4183e971"
//             }
//         ]
//     };
//
//     useEffect(() => {
//         messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
//     }, [messages]);
//
//     // const handleAddMessage = (msg) => {
//     //     setMessages(prevMessages => [...prevMessages, msg]);
//     // };
//     //
//     // const handleSendMessage = async (e) => {
//     //     e.preventDefault();
//     //     if (!message.trim()) return;
//     //
//     //     const userMessage = {
//     //         role: 'user',
//     //         content: message.trim(),
//     //         timestamp: new Date().toISOString()
//     //     };
//     //     handleAddMessage(userMessage);
//     //     setMessage('');
//     //     setIsLoading(true);
//     //
//     //     // Simulate API call
//     //     setTimeout(() => {
//     //         const mockResponse = mode === 'policy' ? mockPolicyResponse : mockStoryResponse;
//     //         handleAddMessage({
//     //             role: 'assistant',
//     //             data: mockResponse,
//     //             mode: mode,
//     //             timestamp: new Date().toISOString()
//     //         });
//     //         setIsLoading(false);
//     //     }, 1500);
//     // };
//
//     const PolicyResultCard = ({ result, index }) => (
//         <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-3">
//             <div className="flex items-center justify-between mb-2">
//                 <div className="flex items-center gap-2">
//                     <FileText className="w-4 h-4 text-blue-600" />
//                     <span className="font-semibold text-blue-800">{result.section}</span>
//                 </div>
//                 <div className="flex items-center gap-1">
//                     <Star className="w-3 h-3 text-yellow-500 fill-current" />
//                     <span className="text-xs text-gray-600">{(result.relevance_score * 100).toFixed(0)}%</span>
//                 </div>
//             </div>
//             <p className="text-gray-700 text-sm leading-relaxed">{result.content_preview}</p>
//             <div className="mt-3 flex justify-between items-center">
//                 <span className="text-xs text-gray-500">Doc ID: {result.document_id}</span>
//                 <button className="text-blue-600 hover:text-blue-800 text-xs font-medium flex items-center gap-1">
//                     View Full Document <ChevronRight className="w-3 h-3" />
//                 </button>
//             </div>
//         </div>
//     );
//
//     const StoryResultCard = ({ result, index }) => (
//         <div className="bg-purple-50 border border-purple-200 rounded-lg p-4 mb-3">
//             <div className="flex items-center justify-between mb-2">
//                 <div className="flex items-center gap-2">
//                     <Book className="w-4 h-4 text-purple-600" />
//                     <span className="font-semibold text-purple-800">Chương {result.chapter_idx}</span>
//                 </div>
//                 <div className="flex items-center gap-1">
//                     <div className="w-2 h-2 bg-purple-400 rounded-full"></div>
//                     <span className="text-xs text-gray-600">{(result.similarity_score * 100).toFixed(1)}%</span>
//                 </div>
//             </div>
//             <p className="text-gray-700 text-sm leading-relaxed line-clamp-3">{result.content_preview}</p>
//             <div className="mt-3 flex justify-between items-center">
//                 <span className="text-xs text-gray-500">ID: {result.chapter_id.slice(0, 8)}...</span>
//                 <button className="text-purple-600 hover:text-purple-800 text-xs font-medium flex items-center gap-1">
//                     Đọc Chương <ChevronRight className="w-3 h-3" />
//                 </button>
//             </div>
//         </div>
//     );
//
//     const MessageRenderer = ({ msg }) => {
//         if (msg.role === 'user') {
//             return (
//                 <div className="flex justify-end mb-4">
//                     <div className="bg-blue-600 text-white rounded-2xl rounded-br-md px-4 py-2 max-w-xs lg:max-w-md">
//                         <p className="text-sm">{msg.content}</p>
//                         <p className="text-xs opacity-75 mt-1">{new Date(msg.timestamp).toLocaleTimeString()}</p>
//                     </div>
//                 </div>
//             );
//         }
//
//         return (
//             <div className="flex justify-start mb-6">
//                 <div className="max-w-4xl">
//                     <div className={`rounded-2xl rounded-bl-md p-4 ${
//                         msg.mode === 'policy' ? 'bg-blue-100' : 'bg-purple-100'
//                     }`}>
//                         <div className="flex items-center gap-2 mb-3">
//                             {msg.mode === 'policy' ?
//                                 <FileText className="w-5 h-5 text-blue-600" /> :
//                                 <Book className="w-5 h-5 text-purple-600" />
//                             }
//                             <span className={`font-medium ${
//                                 msg.mode === 'policy' ? 'text-blue-800' : 'text-purple-800'
//                             }`}>
//                                 {msg.mode === 'policy' ? 'Policy Search Results' : 'Kết quả tìm truyện'}
//                             </span>
//                         </div>
//
//                         <div className="mb-4">
//                             <p className="text-sm text-gray-600 mb-2">
//                                 <strong>Truy vấn:</strong> "{msg.data.query}"
//                             </p>
//                             <p className="text-xs text-gray-500">
//                                 Tìm thấy {msg.data.total_results} kết quả liên quan
//                             </p>
//                         </div>
//
//                         <div className="space-y-3">
//                             {msg.data.results.slice(0, 3).map((result, index) =>
//                                 msg.mode === 'policy' ?
//                                     <PolicyResultCard key={index} result={result} index={index} /> :
//                                     <StoryResultCard key={index} result={result} index={index} />
//                             )}
//                         </div>
//
//                         {msg.data.results.length > 3 && (
//                             <button className={`mt-3 text-xs font-medium ${
//                                 msg.mode === 'policy' ? 'text-blue-600 hover:text-blue-800' : 'text-purple-600 hover:text-purple-800'
//                             }`}>
//                                 Hiển thị thêm {msg.data.results.length - 3} kết quả...
//                             </button>
//                         )}
//                     </div>
//                     <p className="text-xs text-gray-500 mt-1 ml-4">
//                         {new Date(msg.timestamp).toLocaleTimeString()}
//                     </p>
//                 </div>
//             </div>
//         );
//     };
//
//     return (
//         <div className="flex flex-col h-full bg-gray-50">
//             {/* Mode Toggle Header */}
//             <div className="bg-white border-b border-gray-200 p-4">
//                 <div className="flex items-center justify-center">
//                     <div className="bg-gray-100 p-1 rounded-lg flex">
//                         <button
//                             onClick={() => setMode('policy')}
//                             className={`flex items-center gap-2 px-4 py-2 rounded-md text-sm font-medium transition-all ${
//                                 mode === 'policy'
//                                     ? 'bg-blue-600 text-white shadow-sm'
//                                     : 'text-gray-600 hover:text-blue-600'
//                             }`}
//                         >
//                             <FileText className="w-4 h-4" />
//                             Policy Search
//                         </button>
//                         <button
//                             onClick={() => setMode('story')}
//                             className={`flex items-center gap-2 px-4 py-2 rounded-md text-sm font-medium transition-all ${
//                                 mode === 'story'
//                                     ? 'bg-purple-600 text-white shadow-sm'
//                                     : 'text-gray-600 hover:text-purple-600'
//                             }`}
//                         >
//                             <Book className="w-4 h-4" />
//                             Tìm Truyện
//                         </button>
//                     </div>
//                 </div>
//                 <p className="text-center text-xs text-gray-500 mt-2">
//                     {mode === 'policy' ?
//                         'Tìm kiếm trong tài liệu chính sách và quy định' :
//                         'Tìm kiếm nội dung trong các chương truyện'
//                     }
//                 </p>
//             </div>
//
//             {/* Messages Area */}
//             <div className="flex-1 overflow-y-auto p-4">
//                 {error && (
//                     <div className="flex justify-center mb-4">
//                         <div className="bg-red-100 text-red-800 p-3 rounded-lg text-sm">
//                             Lỗi: {error}
//                         </div>
//                     </div>
//                 )}
//
//                 {messages.length === 0 && (
//                     <div className="flex flex-col items-center justify-center h-full text-center">
//                         <div className={`w-16 h-16 rounded-full flex items-center justify-center mb-4 ${
//                             mode === 'policy' ? 'bg-blue-100' : 'bg-purple-100'
//                         }`}>
//                             {mode === 'policy' ?
//                                 <FileText className="w-8 h-8 text-blue-600" /> :
//                                 <Book className="w-8 h-8 text-purple-600" />
//                             }
//                         </div>
//                         <h3 className="text-lg font-medium text-gray-800 mb-2">
//                             {mode === 'policy' ? 'Policy Search Assistant' : 'Trợ lý Tìm Truyện'}
//                         </h3>
//                         <p className="text-gray-500 text-sm max-w-md">
//                             {mode === 'policy' ?
//                                 'Hỏi tôi về các chính sách, quy định và tài liệu hướng dẫn của công ty.' :
//                                 'Tìm kiếm nhân vật, sự kiện hoặc nội dung trong các chương truyện.'
//                             }
//                         </p>
//                     </div>
//                 )}
//
//                 {messages.map((msg, index) => (
//                     <MessageRenderer key={index} msg={msg} />
//                 ))}
//
//                 {isLoading && (
//                     <div className="flex justify-start mb-6">
//                         <div className={`rounded-2xl rounded-bl-md p-4 ${
//                             mode === 'policy' ? 'bg-blue-100' : 'bg-purple-100'
//                         }`}>
//                             <div className="flex items-center gap-3">
//                                 <div className="animate-spin rounded-full h-4 w-4 border-2 border-gray-400 border-t-transparent"></div>
//                                 <span className="text-sm text-gray-600">
//                                     {mode === 'policy' ? 'Đang tìm kiếm chính sách...' : 'Đang tìm trong truyện...'}
//                                 </span>
//                             </div>
//                         </div>
//                     </div>
//                 )}
//
//                 <div ref={messagesEndRef} />
//             </div>
//
//             {/* Input Area */}
//             <div className="bg-white border-t border-gray-200 p-4">
//                 <form onSubmit={handleSendMessage} className="flex gap-3">
//                     <div className="flex-1 relative">
//                         <input
//                             type="text"
//                             value={message}
//                             onChange={(e) => setMessage(e.target.value)}
//                             placeholder={mode === 'policy' ?
//                                 'Hỏi về chính sách công ty...' :
//                                 'Tìm nhân vật, sự kiện trong truyện...'
//                             }
//                             className="w-full border border-gray-300 rounded-full px-4 py-3 pr-12 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
//                             disabled={isLoading}
//                         />
//                         <Search className="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
//                     </div>
//                     <button
//                         type="submit"
//                         className={`px-6 py-3 rounded-full text-white font-medium transition-all ${
//                             isLoading || !message.trim()
//                                 ? 'bg-gray-400 cursor-not-allowed'
//                                 : mode === 'policy'
//                                     ? 'bg-blue-600 hover:bg-blue-700'
//                                     : 'bg-purple-600 hover:bg-purple-700'
//                         }`}
//                         disabled={isLoading || !message.trim()}
//                     >
//                         {isLoading ? (
//                             <div className="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent" />
//                         ) : (
//                             'Gửi'
//                         )}
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// };
//
// export default ChatComponent;