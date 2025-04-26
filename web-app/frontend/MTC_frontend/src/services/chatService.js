import { API } from "../configurations/configuration";
import httpClient from '../configurations/httpClient.js';

import { getToken } from "./localStorageService.js";

const API_BASE_URL = 'http://127.0.0.1:8000/api/chat';

export const chatService = {
    // Document management
    async uploadDocument(title, content) {
        try {
            const response = await fetch(`${API_BASE_URL}/documents/upload/`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ title, content }),
            });

            if (!response.ok) {
                throw new Error('Failed to upload document');
            }

            return await response.json();
        } catch (error) {
            console.error('Error uploading document:', error);
            throw error;
        }
    },

    async getDocuments() {
        try {
            const response = await fetch(`${API_BASE_URL}/documents/`);

            if (!response.ok) {
                throw new Error('Failed to fetch documents');
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching documents:', error);
            throw error;
        }
    },

    // Conversation management
    async createConversation(title, systemMessage = null) {
        try {
            const response = await fetch(`${API_BASE_URL}/conversations/create/`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    title,
                    system_message: systemMessage
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to create conversation');
            }

            return await response.json();
        } catch (error) {
            console.error('Error creating conversation:', error);
            throw error;
        }
    },

    async getConversations() {
        try {
            const response = await fetch(`${API_BASE_URL}/conversations/`);

            if (!response.ok) {
                throw new Error('Failed to fetch conversations');
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching conversations:', error);
            throw error;
        }
    },

    async getConversation(conversationId) {
        try {
            const response = await fetch(`${API_BASE_URL}/conversations/${conversationId}/`);

            if (!response.ok) {
                throw new Error('Failed to fetch conversation');
            }

            return await response.json();
        } catch (error) {
            console.error(`Error fetching conversation ${conversationId}:`, error);
            throw error;
        }
    },

    // Chat functionality
    async sendMessage(message, conversationId = null, model = 'llama3.2:1b', callbacks = {}) {
        try {
            const { onStart, onSuccess, onError, onFinish } = callbacks;

            if (onStart) onStart();

            const url = conversationId
                ? `${API_BASE_URL}/chat/${conversationId}/`
                : `${API_BASE_URL}/chat/`;

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ message, model }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Failed to send message');
            }

            const data = await response.json();

            if (onSuccess) onSuccess(data);

            return data;
        } catch (error) {
            console.error('Error sending message:', error);
            if (callbacks.onError) callbacks.onError(error);
            throw error;
        } finally {
            if (callbacks.onFinish) callbacks.onFinish();
        }
    }
};