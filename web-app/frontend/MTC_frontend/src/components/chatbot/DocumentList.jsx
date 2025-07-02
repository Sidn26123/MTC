import React, { useState, useEffect } from 'react';
import { chatService } from '../../services/chatService.js';

const DocumentList = () => {
    const [documents, setDocuments] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchDocuments = async () => {
        try {
            setIsLoading(true);
            setError(null);
            const data = await chatService.getDocuments();
            setDocuments(data);
        } catch (error) {
            setError('Failed to load documents');
            console.error('Error fetching documents:', error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchDocuments();
    }, []);

    return (
        <div className="p-4 border base-text-color rounded-lg bg-white shadow-sm">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-medium">Knowledge Base Documents</h2>
                <button
                    onClick={fetchDocuments}
                    disabled={isLoading}
                    className="text-blue-600 hover:text-blue-800 text-sm"
                >
                    {isLoading ? 'Refreshing...' : 'Refresh'}
                </button>
            </div>

            {error && (
                <div className="mb-4 bg-red-50 p-2 rounded text-red-600 text-sm">
                    {error}
                </div>
            )}

            {isLoading && documents.length === 0 ? (
                <div className="text-center py-4 text-gray-500">Loading documents...</div>
            ) : documents.length === 0 ? (
                <div className="text-center py-4 text-gray-500">No documents found</div>
            ) : (
                <ul className="divide-y">
                    {documents.map((doc) => (
                        <li key={doc.id} className="py-3">
                            <div className="flex justify-between">
                                <div>
                                    <h3 className="font-medium">{doc.title}</h3>
                                    <p className="text-sm text-gray-500">
                                        Uploaded: {new Date(doc.created_at).toLocaleDateString()}
                                    </p>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default DocumentList;
