import { API } from "../configurations/configuration";
import httpClient from '../configurations/httpClient.js';

import { getToken } from "./localStorageService.js";

const API_BASE_URL = 'http://127.0.0.1:8000/api/chat';
const API_RAG_URL = 'http://127.0.0.1:8000/api/rag';

const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzU1Njc1MDc4LCJpYXQiOjE3NTU1ODg2NzgsImp0aSI6IjA0NmI0YTRiOWRhNzQzM2M5Nzg3YzA2ODkzZTFhN2UxIiwidXNlcl9pZCI6MX0.qh7pcU_jJhizONAwZsNN4j70FEDdtPrYceQG2vQAzjw"

export const chatService = {
    // Document management
    async uploadDocument(title, content) {
        try {
            const response = await fetch(`${API_BASE_URL}/documents/upload/`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Bearer': token || '',
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
            const response = await fetch(`${API_RAG_URL}/documents`, {
                method: 'GET',
                headers: {
                    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
                },
            });

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
    },

    async sendMessageRAG(message, conversationId = null, model = 'llama3.2:1b', callbacks = {}) {
        console.log("Sending RAG message:", message, conversationId, model);
        try {
            const { onStart, onSuccess, onError, onFinish } = callbacks;

            if (onStart) onStart();

            const url =
                `${API_RAG_URL}/ask/`;

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
                },
                body: JSON.stringify({ question: message, model }),
            });



            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Failed to send message');
            }

            const data = await response.json();
            console.log("RAG response data:", data);
            if (onSuccess) onSuccess(data);

            return data;
        } catch (error) {
            console.error('Error sending message:', error);
            if (callbacks.onError) callbacks.onError(error);
            throw error;
        } finally {
            if (callbacks.onFinish) callbacks.onFinish();
        }
    },

    async sendMessageType2(message, model, callbacks = {}) {
        console.log("Sending message type 2:", message, model);
        try {
            const { onStart, onSuccess, onError, onFinish } = callbacks;

            if (onStart) onStart();

            const url = `${API_RAG_URL}/novel/search/`;

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
                },
                body: JSON.stringify({ query: message, top_k: 5 }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Failed to send message');
            }

            const data = await response.json();
            console.log("Response data:", data);
            if (onSuccess) onSuccess(data);

            return data;
        } catch (error) {
            console.error('Error sending message:', error);
            if (callbacks.onError) callbacks.onError(error);
            throw error;
        } finally {
            if (callbacks.onFinish) callbacks.onFinish();
        }
    },

    async getDocumentDB(documentId) {
        try {
            const response = await fetch(`${API_RAG_URL}/documents/${documentId}`, {
                method: 'GET',
                headers: {
                    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch document');
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching document:', error);
            throw error;
        }
    },

    async getDocumentByChunkid(chunkId) {
        try {
            const response = await fetch(`${API_RAG_URL}/document/chunk/${chunkId}`, {
                method: 'GET',
                headers: {
                    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch document by chunk ID');
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching document by chunk ID:', error);
            throw error;
        }
    }
};