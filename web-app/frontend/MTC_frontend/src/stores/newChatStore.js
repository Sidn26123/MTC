import { create } from 'zustand';
import { persist } from 'zustand/middleware';

// Define the chat store with Zustand
const useChatStore = create(
    persist(
        (set, get) => ({
            conversations: {},
            activeConversationId: null,
            isLoading: false,
            error: null,

            // Set active conversation
            setActiveConversation: (conversationId) => {
                set({
                    activeConversationId: conversationId,
                    error: null
                });

                // Initialize conversation if it doesn't exist
                if (!get().conversations[conversationId]) {
                    set((state) => ({
                        conversations: {
                            ...state.conversations,
                            [conversationId]: {
                                messages: [],
                                metadata: {
                                    title: "New Conversation",
                                    lastUpdated: new Date().toISOString()
                                }
                            }
                        }
                    }));
                }
            },

            // Add a system message to the active conversation
            setSystemMessage: (message) => {
                const { activeConversationId } = get();
                if (!activeConversationId) return;

                set((state) => {
                    const conversation = state.conversations[activeConversationId];
                    const existingSystemIndex = conversation.messages.findIndex(msg => msg.role === 'system');

                    let updatedMessages = [...conversation.messages];
                    if (existingSystemIndex >= 0) {
                        // Update existing system message
                        updatedMessages[existingSystemIndex] = {
                            ...updatedMessages[existingSystemIndex],
                            content: message
                        };
                    } else {
                        // Add new system message at the beginning
                        updatedMessages = [
                            { id: Date.now().toString(), role: 'system', content: message, timestamp: new Date().toISOString() },
                            ...updatedMessages
                        ];
                    }

                    return {
                        conversations: {
                            ...state.conversations,
                            [activeConversationId]: {
                                ...conversation,
                                messages: updatedMessages,
                                metadata: {
                                    ...conversation.metadata,
                                    lastUpdated: new Date().toISOString()
                                }
                            }
                        }
                    };
                });
            },

            // Add a new message to the active conversation
            addMessage: (message) => {
                const { activeConversationId } = get();
                if (!activeConversationId) return null;

                const newMessage = {
                    id: Date.now().toString(),
                    ...message,
                    timestamp: new Date().toISOString()
                };

                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [activeConversationId]: {
                            ...state.conversations[activeConversationId],
                            messages: [
                                ...state.conversations[activeConversationId].messages,
                                newMessage
                            ],
                            metadata: {
                                ...state.conversations[activeConversationId].metadata,
                                lastUpdated: new Date().toISOString()
                            }
                        }
                    }
                }));

                return newMessage;
            },

            // Update conversation metadata
            updateConversationMetadata: (conversationId, metadata) => {
                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [conversationId]: {
                            ...state.conversations[conversationId],
                            metadata: {
                                ...state.conversations[conversationId].metadata,
                                ...metadata,
                                lastUpdated: new Date().toISOString()
                            }
                        }
                    }
                }));
            },

            // Set loading state
            setLoading: (isLoading) => set({ isLoading }),

            // Set error
            setError: (error) => set({ error }),

            // Get messages for active conversation
            getActiveConversationMessages: () => {
                const { activeConversationId, conversations } = get();
                return activeConversationId && conversations[activeConversationId]
                    ? conversations[activeConversationId].messages
                    : [];
            },

            // Get all conversations as an array
            getAllConversations: () => {
                const { conversations } = get();
                return Object.entries(conversations).map(([id, data]) => ({
                    id,
                    ...data.metadata,
                    messageCount: data.messages.filter(m => m.role !== 'system').length
                }));
            },

            // Delete a conversation
            deleteConversation: (conversationId) => {
                set((state) => {
                    const { [conversationId]: deleted, ...remainingConversations } = state.conversations;

                    // If we're deleting the active conversation, set activeConversationId to null
                    const newActiveId = state.activeConversationId === conversationId ? null : state.activeConversationId;

                    return {
                        conversations: remainingConversations,
                        activeConversationId: newActiveId
                    };
                });
            },

            // Import conversations from the server
            importConversationsFromServer: (serverConversations) => {
                console.log('Importing conversations from server:', serverConversations.length);
                const formattedConversations = {};

                serverConversations.forEach(conv => {
                    formattedConversations[conv.id] = {
                        messages: conv.messages.map(msg => ({
                            id: msg.id.toString(),
                            role: msg.role,
                            content: msg.content,
                            timestamp: msg.timestamp
                        })),
                        metadata: {
                            title: conv.title,
                            lastUpdated: conv.created_at
                        }
                    };
                });

                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        ...formattedConversations,
                    }
                }));
            }
        }),
        {
            name: 'rag-chat-storage', // name for the localStorage key
        }
    )
);

export default useChatStore;