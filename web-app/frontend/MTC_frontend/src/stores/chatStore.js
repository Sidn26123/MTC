import { create } from 'zustand';
import { persist } from 'zustand/middleware';

const useChatStore = create(
    persist(
        (set, get) => ({
            conversations: {}, // Object with conversation IDs as keys
            activeConversationId: null,

            // Set active conversation
            setActiveConversation: (conversationId) => {
                set({ activeConversationId: conversationId });

                // Initialize conversation if it doesn't exist
                if (!get().conversations[conversationId]) {
                    set((state) => ({
                        conversations: {
                            ...state.conversations,
                            [conversationId]: {
                                messages: [],
                                unreadCount: 0,
                            }
                        }
                    }));
                }
            },

            // Add a new message to a conversation
            addMessage: (conversationId, message) => {
                const timestamp = new Date().toISOString();
                const newMessage = {
                    id: Date.now().toString(),
                    ...message,
                    timestamp,
                };

                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [conversationId]: {
                            messages: [
                                ...(state.conversations[conversationId]?.messages || []),
                                newMessage
                            ],
                            unreadCount: conversationId === state.activeConversationId
                                ? 0
                                : (state.conversations[conversationId]?.unreadCount || 0) + 1
                        }
                    }
                }));

                return newMessage;
            },

            // Get messages for active conversation
            getActiveConversationMessages: () => {
                const { activeConversationId, conversations } = get();
                return activeConversationId && conversations[activeConversationId]
                    ? conversations[activeConversationId].messages
                    : [];
            },

            // Mark conversation as read
            markConversationAsRead: (conversationId) => {
                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [conversationId]: {
                            ...state.conversations[conversationId],
                            unreadCount: 0
                        }
                    }
                }));
            },

            // Delete a message
            deleteMessage: (conversationId, messageId) => {
                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [conversationId]: {
                            ...state.conversations[conversationId],
                            messages: state.conversations[conversationId].messages.filter(
                                (message) => message.id !== messageId
                            )
                        }
                    }
                }));
            },

            // Clear conversation history
            clearConversation: (conversationId) => {
                set((state) => ({
                    conversations: {
                        ...state.conversations,
                        [conversationId]: {
                            messages: [],
                            unreadCount: 0
                        }
                    }
                }));
            },
        }),
        {
            name: 'chat-storage', // name for the localStorage key
        }
    )
);

export const useChatState = () => useChatStore((state) => state.conversations);

export default useChatStore;