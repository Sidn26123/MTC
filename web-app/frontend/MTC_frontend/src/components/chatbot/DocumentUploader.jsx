import React, { useState } from 'react';
import { chatService } from '../../services/chatService';

const DocumentUploader = ({ onSuccess }) => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [file, setFile] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];
        if (!selectedFile) return;

        setFile(selectedFile);

        // Auto-fill title from filename
        const fileName = selectedFile.name.split('.')[0];
        if (!title) {
            setTitle(fileName);
        }

        // Read file content
        const reader = new FileReader();
        reader.onload = (e) => {
            setContent(e.target.result);
        };
        reader.readAsText(selectedFile);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!title || !content) {
            setError('Please provide both title and content');
            return;
        }

        try {
            setIsLoading(true);
            setError(null);

            const result = await chatService.uploadDocument(title, content);

            setTitle('');
            setContent('');
            setFile(null);

            if (onSuccess) onSuccess(result);

        } catch (error) {
            setError(error.message || 'Failed to upload document');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="p-4 border base-text-color rounded-lg bg-white shadow-sm">
            <h2 className="text-lg font-medium mb-4">Upload Document for RAG</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Document Title
                    </label>
                    <input
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        placeholder="Enter document title"
                        required
                    />
                </div>

                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Upload File
                    </label>
                    <input
                        type="file"
                        onChange={handleFileChange}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        accept=".txt,.md,.csv,.json"
                    />
                    <p className="text-xs text-gray-500 mt-1">
                        Supported formats: .txt, .md, .csv, .json
                    </p>
                </div>

                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Document Content
                    </label>
                    <textarea
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        rows={8}
                        placeholder="Paste document content here"
                        required
                    />
                </div>

                {error && (
                    <div className="mb-4 bg-red-50 p-2 rounded text-red-600 text-sm">
                        {error}
                    </div>
                )}

                <button
                    type="submit"
                    disabled={isLoading}
                    className={`px-4 py-2 rounded-md ${
                        isLoading ? 'bg-gray-400' : 'bg-blue-600 hover:bg-blue-700'
                    } text-white font-medium focus:outline-none`}
                >
                    {isLoading ? 'Uploading...' : 'Upload Document'}
                </button>
            </form>
        </div>
    );
};

export default DocumentUploader;