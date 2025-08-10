import React, { useEffect } from 'react';
import { useState } from "react"
import { Edit3, X } from "lucide-react"
import { useNavigate } from 'react-router';
import { useDrafts, useSetDrafts } from '../../stores/publisherStore.js';
import { getAllMyNovels } from '../../services/novelService.js';
import { deleteDraft, getAllMyDrafts } from '../../services/publisherService.js';

function ManageDraftPage() {
    const navigate = useNavigate();
    const messages = useDrafts();
    const fetchAllMyDrafts = getAllMyDrafts;

    const setDrafts = useSetDrafts(); // ✅ Gọi hook ở đầu component

    useEffect(() => {
        fetchAllMyDrafts().then(r => {
            setDrafts(r.data.result); // ✅ Dùng như function
            console.log("Drafts fetched:", r.data.result);
        });
    }, [setDrafts]);


    function handleEdit(id) {
        navigate(`/bookhub/ban-nhap/${id}`);
    }

    function handleDelete(id) {
        if (window.confirm("Bạn có chắc chắn muốn xóa bản nháp này?")) {
            // Gọi API xóa bản nháp
            // Giả sử bạn có hàm deleteDraft(id) để xóa bản nháp
            deleteDraft(id).then(() => {
                // Cập nhật lại danh sách sau khi xóa
                fetchAllMyDrafts().then(r => {
                    setDrafts(r.data.result);
                });
            }).catch(error => {
                console.error("Error deleting draft:", error);
                alert("Xóa bản nháp thất bại. Vui lòng thử lại.");
            });
        }

    }

    return (
        <div className="min-h-screen bg-gray-800 p-6">
            <div className=" mx-auto">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {messages && messages.length && messages.map((message) => (
                        <div key={message.id} className="bg-gray-700 rounded-lg p-4 relative border border-gray-600">

                            {/* Header với title và các nút action */}
                            <div className="flex items-center justify-between mb-3">
                                <h3 className="text-red-400 font-medium text-sm">{message.title}</h3>
                                <div className="flex items-center gap-2">
                                    <button
                                        onClick={() => handleEdit(message.id)}
                                        className="text-red-400 hover:text-red-300 transition-colors p-1"
                                        aria-label="Chỉnh sửa tin nhắn"
                                    >
                                        <Edit3 size={16} />
                                    </button>
                                    <button
                                        onClick={() => handleDelete(message.id)}
                                        className="text-red-400 hover:text-red-300 transition-colors p-1"
                                        aria-label="Xóa tin nhắn"
                                    >
                                        <X size={16} />
                                    </button>
                                </div>
                            </div>

                            {/* Nội dung tin nhắn */}
                            <div className="text-gray-300 text-sm leading-relaxed">{message.content}</div>
                        </div>
                    ))}
                </div>

                {messages.length === 0 && (
                    <div className="text-center text-gray-400 mt-12">
                        <p>Không có tin nhắn nào</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default ManageDraftPage;