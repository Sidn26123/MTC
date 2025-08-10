import React, { useEffect, useState } from 'react';
import { chatService } from '../../services/chatService.js';
import PdfViewer from '../common/PDFViewer.jsx';
// Mock data cho documents chi tiết (giả lập database)
// const [documentsDetail] = useState({
//     8: {
//         id: 8,
//         title: "Mục lục tài liệu",
//         type: "txt",
//         content: "iii \n \nMỤC LỤC \nMỞ ĐẦU ........................................................................................................................................ 1 \nCHƯƠNG 1: GIỚI THIỆU ĐỀ TÀI ........................................................................................... 2\n\nĐây là phần mục lục chi tiết của tài liệu, bao gồm các chương và phần quan trọng của tài liệu nghiên cứu.\n\nCác phần chính trong tài liệu:\n- Giới thiệu tổng quan\n- Phân tích yêu cầu hệ thống\n- Thiết kế kiến trúc\n- Cài đặt và triển khai\n- Kết luận và hướng phát triển",
//         fileName: "muc_luc.txt",
//         fileUrl: "/documents/muc_luc.txt"
//     },
//     3: {
//         id: 3,
//         title: "Phân tích Use Case",
//         type: "pdf",
//         content: "PHÂN TÍCH USE CASE HỆ THỐNG\n\n1. Giới thiệu\nTài liệu này mô tả chi tiết các use case trong hệ thống quản lý siêu thị.\n\n2. Các Actor chính:\n- Khách hàng (Customer)\n- Nhân viên bán hàng (Sales Staff)\n- Quản lý (Manager)\n- Hệ thống thanh toán (Payment System)\n\n3. Use Cases chính:\n\n3.1 UC001: Đăng nhập hệ thống\n- Actor: Nhân viên, Quản lý\n- Mô tả: Xác thực người dùng trước khi truy cập hệ thống\n- Pre-condition: Người dùng có tài khoản hợp lệ\n- Post-condition: Người dùng được cấp quyền truy cập\n\n3.2 UC002: Quét mã sản phẩm\n- Actor: Nhân viên bán hàng\n- Mô tả: Quét mã vạch để thêm sản phẩm vào giỏ hàng\n- Pre-condition: Sản phẩm có mã vạch hợp lệ\n- Post-condition: Sản phẩm được thêm vào hóa đơn\n\n3.3 UC003: Thanh toán\n- Actor: Khách hàng, Nhân viên, Hệ thống thanh toán\n- Mô tả: Xử lý thanh toán cho đơn hàng\n- Pre-condition: Có đơn hàng hợp lệ\n- Post-condition: Giao dịch hoàn tất\n\n4. Biểu đồ Use Case\n[Biểu đồ sẽ được vẽ riêng]\n\n5. Kết luận\nCác use case trên đây sẽ được sử dụng để thiết kế và phát triển hệ thống.",
//         fileName: "usecase_analysis.pdf",
//         fileUrl: "/documents/usecase_analysis.pdf"
//     },
//     2: {
//         id: 2,
//         title: "Nhiệm vụ PM",
//         type: "txt",
//         content: "NHIỆM VỤ VÀ VAI TRÒ CỦA PROJECT MANAGER\n\n1. Định nghĩa Project Manager (PM)\nProject Manager là người chịu trách nhiệm lãnh đạo và quản lý một dự án từ khởi đầu đến kết thúc.\n\n2. Nhiệm vụ chính của PM:\n\n2.1 Lập kế hoạch dự án\n- Xác định phạm vi dự án (Project Scope)\n- Thiết lập timeline và milestone\n- Phân bổ tài nguyên\n- Quản lý ngân sách\n\n2.2 Quản lý đội ngũ\n- Tuyển chọn và phân công nhân viên\n- Tạo động lực và hỗ trợ team\n- Giải quyết xung đột nội bộ\n- Đánh giá hiệu suất làm việc\n\n2.3 Quản lý rủi ro\n- Nhận diện rủi ro tiềm ẩn\n- Đánh giá tác động của rủi ro\n- Xây dựng kế hoạch ứng phó\n- Theo dõi và kiểm soát rủi ro\n\n2.4 Giao tiếp và báo cáo\n- Liên lạc với stakeholders\n- Báo cáo tiến độ định kỳ\n- Tổ chức họp nhóm\n- Quản lý thông tin dự án\n\n3. Kỹ năng cần thiết:\n- Kỹ năng lãnh đạo\n- Kỹ năng giao tiếp\n- Kỹ năng quản lý thời gian\n- Kỹ năng giải quyết vấn đề\n- Kiến thức về công cụ quản lý dự án\n\n4. Thách thức thường gặp:\n- Thay đổi yêu cầu từ khách hàng\n- Thiếu hụt tài nguyên\n- Vượt quá ngân sách\n- Xung đột trong team\n- Áp lực về deadline\n\n5. Kết luận:\nPM đóng vai trò then chốt trong thành công của dự án. Việc thực hiện tốt các nhiệm vụ trên sẽ giúp dự án đạt được mục tiêu đề ra.",
//         fileName: "pm_roles.txt",
//         fileUrl: "/documents/pm_roles.txt"
//     }
// });
// const DocumentViewer = ({data}) => {
//     console.log(data);
//     // Test data - giống như data server trả về
//     const [serverData] = useState({
//         answer: "Dưới đây là câu trả lời cho câu hỏi \"Mục lục trong tài liệu gồm những phần nào?\"\n\nMục lục trong tài liệu này bao gồm:\n\n- MỤC LỤC\n- MỞ ĐẦU\n- CHƯƠNG 1: GIỚI THIỆU ĐỀ TÀI\n- giúp PM đọc mã hàng của siêu thị...). Tương tự, một số tình huống hỗ trợ cho PM cũng có thể phát sinh trong lúc này (vd: Login là tình huống đặt ra từ PM, để xác thực và xác quyền cho user trong các usecase quan trọng như \"đặt hàng\", \"trả tiền\" )\n- Lược đồ usecase đã vẽ trong mục II.2 có thể phải được cập nhật lại để thêm usecase và actors mới.\n- Nhiệm vụ của PM đối với tổ chức là giúp cho tổ chức đó thực hiện được vai trò/nhiệm vụ của tổ chức\n- Những vấn đề gây khó khăn cho việc thực hiện nhiệm vụ của tổ chức chính là mục tiêu trợ giúp của PM.\n\nVề phần MỤC LỤC, tài liệu này có một số phần quan trọng:\n\n1. MỞ ĐẦU: Đây là phần đầu tiên trong tài liệu, giới thiệu về chủ đề và các mục đích của tài liệu.\n2. CHƯƠNG 1: GIỚI THIỆU ĐỀ TÀI\n3. giúp PM đọc mã hàng của siêu thị...). Tương tự, một số tình huống hỗ trợ cho PM cũng có thể phát sinh trong lúc này (vd: Login là tình huống đặt ra từ PM, để xác thực và xác quyền cho user trong các usecase quan trọng như \"đặt hàng\", \"trả tiền\" )\n4. Lược đồ usecase đã vẽ trong mục II.2 có thể phải được cập nhật lại để thêm usecase và actors mới.\n5. Nhiệm vụ của PM đối với tổ chức là giúp cho tổ chức đó thực hiện được vai trò/nhiệm vụ của tổ chức\n6. Những vấn đề gây khó khăn cho việc thực hiện nhiệm vụ của tổ chức chính là mục tiêu trợ giúp của PM.\n\nTóm lại, tài liệu này cung cấp thông tin về nội dung và cấu trúc của tài liệu, cũng như giới thiệu về mục đích và phạm vi của nó.",
//         relevant_ids: [8, 3, 2],
//         relevant_docs: [
//             "iii \n \nMỤC LỤC \nMỞ ĐẦU ........................................................................................................................................ 1 \nCHƯƠNG 1: GIỚI THIỆU ĐỀ TÀI ........................................................................................... 2",
//             "3\ngiúp PM đọc mã hàng của siêu thị...). Tương tự, một số tình huống hỗ trợ cho PM cũng có thể phát\nsinh trong lúc này (vd: Login là tình huống đặt ra từ PM, để xác thực và xác quyền cho user trong các\nusecase quan trọng như  ).\nLược đồ usecase đã vẽ trong mục II.2 có thể phải được cập nhật lại để thêm usecase và actors mới.",
//             "Nhiệm vụ của PM đối với tổ chức là giúp cho tổ chức đó thực hiện được vai trò/nhiệm vụ của tổ chức\nđó. Những vấn đề gây khó khăn cho việc thực hiện nhiệm vụ của tổ chức chính là mục tiêu trợ giúp\ncủa PM. Như vậy trong công đoạn phân tích hệ thống, thì PM được xem như là một công cụ (một hộp\nđen, một đối tượng) bên trong một hệ thống lớn là bộ máy đang hoạt động của tổ chức mà PM sẽ"
//         ]
//     });
//
//     // State để quản lý modal
//     const [selectedDocIndex, setSelectedDocIndex] = useState(null);
//     const [isModalOpen, setIsModalOpen] = useState(false);
//     const [showDocuments, setShowDocuments] = useState(false);
//     const [isSourceModalOpen, setIsSourceModalOpen] = useState(false);
//     const [selectedSourceDoc, setSelectedSourceDoc] = useState(null);
//
//     // Mock data cho documents chi tiết (giả lập database)
//     const [documentsDetail] = useState({
//         8: {
//             id: 8,
//             title: "Mục lục tài liệu",
//             type: "text",
//             content: "iii \n \nMỤC LỤC \nMỞ ĐẦU ........................................................................................................................................ 1 \nCHƯƠNG 1: GIỚI THIỆU ĐỀ TÀI ........................................................................................... 2\n\nĐây là phần mục lục chi tiết của tài liệu, bao gồm các chương và phần quan trọng của tài liệu nghiên cứu."
//         },
//         3: {
//             id: 3,
//             title: "Phân tích Use Case",
//             type: "pdf",
//             content: "3\ngiúp PM đọc mã hàng của siêu thị...). Tương tự, một số tình huống hỗ trợ cho PM cũng có thể phát\nsinh trong lúc này (vd: Login là tình huống đặt ra từ PM, để xác thực và xác quyền cho user trong các\nusecase quan trọng như  ).\nLược đồ usecase đã vẽ trong mục II.2 có thể phải được cập nhật lại để thêm usecase và actors mới.\n\nPhần này mô tả chi tiết về việc phân tích các use case trong hệ thống, bao gồm các actor và các tình huống sử dụng."
//         },
//         2: {
//             id: 2,
//             title: "Nhiệm vụ PM",
//             type: "txt",
//             content: "Nhiệm vụ của PM đối với tổ chức là giúp cho tổ chức đó thực hiện được vai trò/nhiệm vụ của tổ chức\nđó. Những vấn đề gây khó khăn cho việc thực hiện nhiệm vụ của tổ chức chính là mục tiêu trợ giúp\ncủa PM. Như vậy trong công đoạn phân tích hệ thống, thì PM được xem như là một công cụ (một hộp\nđen, một đối tượng) bên trong một hệ thống lớn là bộ máy đang hoạt động của tổ chức mà PM sẽ\n\nDocument này mô tả chi tiết về vai trò và nhiệm vụ của Project Manager trong tổ chức."
//         }
//     });
//
//     // Hàm xử lý khi nhấn vào số ID
//     const handleIdClick = (index) => {
//         setSelectedDocIndex(index);
//         setIsModalOpen(true);
//     };
//
//     // Hàm đóng modal
//     const closeModal = () => {
//         setIsModalOpen(false);
//         setSelectedDocIndex(null);
//     };
//
//     // Hàm xem nguồn document
//     const handleViewSource = () => {
//         if (selectedDocIndex !== null) {
//             const docId = serverData.relevant_ids[selectedDocIndex];
//             const sourceDoc = documentsDetail[docId];
//             setSelectedSourceDoc(sourceDoc);
//             setIsSourceModalOpen(true);
//         }
//     };
//
//     // Hàm đóng source modal
//     const closeSourceModal = () => {
//         setIsSourceModalOpen(false);
//         setSelectedSourceDoc(null);
//     };
//
//     // Hàm lấy data từ dưới lên và hiển thị documents
//     const handleShowDocuments = () => {
//         setShowDocuments(true);
//     };
//
//     // Hàm lấy icon theo loại file
//     const getFileIcon = (type) => {
//         switch(type) {
//             case 'pdf':
//                 return '📄';
//             case 'txt':
//                 return '📝';
//             default:
//                 return '📋';
//         }
//     };
const DOCUMENT_MEDIA = 'http://localhost:8000/media';

const DocumentViewer = ({ data }) => {
    const [serverData, setServerData] = useState(null);
    const [documentsDetail, setDocumentsDetail] = useState({});
    const [selectedDocIndex, setSelectedDocIndex] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [showDocuments, setShowDocuments] = useState(false);
    const [isSourceModalOpen, setIsSourceModalOpen] = useState(false);
    const [selectedSourceDoc, setSelectedSourceDoc] = useState(null);
    const [originDocuments, setOriginDocuments] = useState([]);
    // Lấy dữ liệu từ API
    useEffect(() => {
        setServerData({
            data: data.data
        })
    }, []);

    function handleGetOriginDocument(documentId){
        chatService.getDocumentDB(documentId).then((response) => {
            console.log("Origin document data: ", response);
            setOriginDocuments(response.data);
        })
    }

    // Khi đang load
    if (!serverData) {
        return <p>Đang tải dữ liệu...</p>;
    }

    const handleIdClick = (index) => {
        setSelectedDocIndex(index);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedDocIndex(null);
    };

    const handleViewSource = () => {
        if (selectedDocIndex !== null) {
            // const docId = serverData.data.relevant_ids[selectedDocIndex];
            // const sourceDoc = documentsDetail[docId];
            // setSelectedSourceDoc(sourceDoc);
            chatService.getDocumentByChunkid(serverData.data.relevant_chunk_id[selectedDocIndex]).then((response) => {
                const data = response;
                console.log("Source document data:) ", data);

                if (data.document_type === '') {
                    // Nếu là file PDF
                    setSelectedSourceDoc({
                        id: data.id,
                        title: data.name,
                        type: 'pdf',
                        // content: data.content,
                        fileName: data.name,
                        fileUrl:DOCUMENT_MEDIA + "/" + data.file_path
                    });

                }
              // if ()
            })
            setIsSourceModalOpen(true);
        }
    };

    const closeSourceModal = () => {
        setIsSourceModalOpen(false);
        setSelectedSourceDoc(null);
    };

    const handleShowDocuments = () => {
        setShowDocuments(true);
    };

    const getFileIcon = (type) => {
        switch (type) {
            case 'pdf': return '📄';
            case 'txt': return '📝';
            default: return '📋';
        }
    };

    return (
        <div className="max-w-4xl mx-auto p-6 bg-white">
            <div className="bg-gray-50 rounded-lg p-6 mb-6">

                {/* Hiển thị câu trả lời */}
                <div className="bg-white rounded-lg p-4 mb-4 border border-gray-200">
                    <h3 className="text-lg font-semibold text-gray-700 mb-3">Câu trả lời:</h3>
                    <div className="text-gray-600 whitespace-pre-line leading-relaxed">
                        {serverData.data.answer}
                    </div>
                </div>

                {/* Hiển thị relevant IDs */}
                <div className="mb-4">
                    <h3 className="text-lg font-semibold text-gray-700 mb-3">Document IDs liên quan:</h3>

                    <div className="flex gap-3">
                        {serverData.data.relevant_ids.map((id, index) => (
                            <button
                                key={id}
                                onClick={() => handleIdClick(index)}
                                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg font-medium transition-colors duration-200 shadow-sm"
                            >
                                {id}
                            </button>
                        ))}
                    </div>
                </div>

                {/* Button lấy data từ dưới lên */}
                {/*<div className="mb-4">*/}
                {/*    <button*/}
                {/*        onClick={handleShowDocuments}*/}
                {/*        className="bg-green-500 hover:bg-green-600 text-white px-6 py-3 rounded-lg font-medium transition-colors duration-200 shadow-sm"*/}
                {/*    >*/}
                {/*        📥 Lấy thông tin chi tiết documents*/}
                {/*    </button>*/}
                {/*</div>*/}
            </div>

            {/* Modal hiển thị document content */}
            {isModalOpen && selectedDocIndex !== null && (
                <div className="fixed inset-0 bg-opacity-50 flex items-center justify-center z-50 p-4">
                    <div className="bg-white rounded-xl max-w-2xl w-full max-h-[80vh] overflow-hidden shadow-2xl">
                        {/* Modal Header */}
                        <div className="flex items-center justify-between p-6 border-b border-gray-200 bg-blue-50">
                            <div className="flex items-center gap-4">

                                <h3 className="text-xl font-bold text-blue-800">
                                    Document ID: {serverData.data.relevant_ids[selectedDocIndex]}
                                </h3>
                                <button
                                    onClick={handleViewSource}
                                    className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg font-medium transition-colors duration-200 shadow-sm text-sm"
                                >
                                    📄 Xem nguồn
                                </button>
                            </div>
                            <button
                                onClick={closeModal}
                                className="text-gray-500 hover:text-gray-700 text-2xl font-bold w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-200 transition-colors"
                            >
                                ×
                            </button>
                        </div>

                        {/* Modal Body */}
                        <div className="p-6 overflow-y-auto max-h-[calc(80vh-120px)]">
                            <div className="bg-blue-50 rounded-lg p-4 border border-blue-200">
                                <h4 className="text-lg font-semibold text-blue-800 mb-3">Nội dung tài liệu:</h4>
                                <div className="text-blue-700 whitespace-pre-line leading-relaxed">
                                    {serverData.data.relevant_docs[selectedDocIndex]}
                                </div>
                            </div>
                        </div>

                        {/* Modal Footer */}
                        <div className="flex justify-end gap-3 p-6 border-t border-gray-200 bg-gray-50">
                            <button
                                onClick={closeModal}
                                className="bg-gray-500 hover:bg-gray-600 text-white px-6 py-2 rounded-lg font-medium transition-colors duration-200"
                            >
                                Đóng
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Source Modal - hiển thị file nguồn */}
            {isSourceModalOpen && selectedSourceDoc && (
                <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-[60] p-4">
                    <div className="bg-white rounded-xl max-w-4xl w-full max-h-[90vh] overflow-hidden shadow-2xl">
                        {/* Source Modal Header */}
                        <div className="flex items-center justify-between p-6 border-b border-gray-200 bg-green-50">
                            <div className="flex items-center gap-3">
                                <span className="text-2xl">{getFileIcon(selectedSourceDoc.type)}</span>
                                <div>
                                    <h3 className="text-xl font-bold text-green-800">
                                        {selectedSourceDoc.title}
                                    </h3>
                                    <p className="text-sm text-green-600">
                                        Document ID: {selectedSourceDoc.id} • Loại: {selectedSourceDoc.type.toUpperCase()}
                                    </p>
                                </div>
                            </div>
                            <button
                                onClick={closeSourceModal}
                                className="text-gray-500 hover:text-gray-700 text-2xl font-bold w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-200 transition-colors"
                            >
                                ×
                            </button>
                        </div>

                        {console.log("Selected Source Document: ", selectedSourceDoc)}
                        {/* Source Modal Body */}
                        <div className="p-6 overflow-y-auto max-h-[calc(90vh-140px)]">
                            {selectedSourceDoc.id && selectedSourceDoc.document_type === '' || selectedSourceDoc.type === 'pdf' ? (
                                <div className="bg-red-50 rounded-lg p-6 border border-red-200">
                                    <div className="flex items-center justify-between mb-4">
                                        <div className="flex items-center gap-3">
                                            <span className="text-3xl">📄</span>
                                            <h4 className="text-lg font-semibold text-red-800">PDF Document</h4>
                                        </div>
                                        <div className="text-sm text-red-600">
                                            File: {selectedSourceDoc.fileName}
                                        </div>
                                    </div>
                                    <div className="bg-white rounded-lg border border-red-200 shadow-inner">
                                        {/* PDF Viewer simulation */}
                                        <PdfViewer fileUrl={encodeURI(selectedSourceDoc.fileUrl)} />
                                        <div className="bg-gray-100 p-2 border-b border-red-200 flex items-center gap-2 text-sm text-gray-600">
                                            <span>📄</span>
                                            <span>PDF Content Preview</span>
                                            <span className="ml-auto">Page 1</span>
                                        </div>
                                        <div className="p-6 min-h-[400px] bg-white">
                                            <div className="text-gray-800 whitespace-pre-line leading-relaxed">
                                                {selectedSourceDoc.content}
                                            </div>
                                        </div>
                                    </div>
                                    <div className="mt-4 text-center">
                                        <p className="text-sm text-red-600 mb-2">
                                            💡 Trong thực tế, đây sẽ là PDF viewer embed hoặc PDF.js
                                        </p>
                                        <button className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg text-sm transition-colors">
                                            📥 Tải xuống PDF
                                        </button>
                                    </div>
                                </div>
                            ) : (
                                <div className="bg-blue-50 rounded-lg p-6 border border-blue-200">
                                    <div className="flex items-center justify-between mb-4">
                                        <div className="flex items-center gap-3">
                                            <span className="text-3xl">📝</span>
                                            <h4 className="text-lg font-semibold text-blue-800">Text Document</h4>
                                        </div>
                                        <div className="text-sm text-blue-600">
                                            File: {selectedSourceDoc.fileName}
                                        </div>
                                    </div>
                                    <div className="bg-white rounded-lg border border-blue-200 shadow-inner">
                                        {/* Text file header */}
                                        <div className="bg-gray-100 p-2 border-b border-blue-200 flex items-center gap-2 text-sm text-gray-600">
                                            <span>📝</span>
                                            <span>Text File Content</span>
                                            <span className="ml-auto">{selectedSourceDoc.content.split('\n').length} lines</span>
                                        </div>
                                        <div className="p-6">
                                            <div className="text-gray-800 whitespace-pre-line leading-relaxed font-mono text-sm">
                                                {selectedSourceDoc.content}
                                            </div>
                                        </div>
                                    </div>
                                    <div className="mt-4 text-center">
                                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg text-sm transition-colors">
                                            📥 Tải xuống TXT
                                        </button>
                                    </div>
                                </div>
                            )}
                        </div>

                        {/* Source Modal Footer */}
                        <div className="flex justify-between items-center gap-3 p-6 border-t border-gray-200 bg-gray-50">
                            <div className="flex items-center gap-4 text-sm text-gray-600">
                                <span>📁 File: {selectedSourceDoc.fileName}</span>
                                <span>📊 Type: {selectedSourceDoc.type.toUpperCase()}</span>
                                {/*<span>📏 Size: {Math.round(selectedSourceDoc.content.length / 1024 * 10) / 10} KB</span>*/}
                            </div>
                            <button
                                onClick={closeSourceModal}
                                className="bg-gray-500 hover:bg-gray-600 text-white px-6 py-2 rounded-lg font-medium transition-colors duration-200"
                            >
                                Đóng
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Hiển thị chi tiết documents */}
            {showDocuments && (
                <div className="bg-white rounded-lg border border-gray-200 p-6">
                    <h2 className="text-xl font-bold text-gray-800 mb-4">Chi tiết Documents</h2>
                    <div className="space-y-4">
                        {serverData.data.relevant_ids.map(id => {
                            const doc = documentsDetail[id];
                            return (
                                <div key={id} className="border border-gray-300 rounded-lg p-4 hover:shadow-md transition-shadow duration-200">
                                    <div className="flex items-center gap-3 mb-3">
                                        <span className="text-2xl">{getFileIcon(doc.type)}</span>
                                        <div>
                                            <h3 className="text-lg font-semibold text-gray-800">
                                                Document ID: {doc.id}
                                            </h3>
                                            <p className="text-sm text-gray-600">
                                                {doc.title} • Loại: {doc.type.toUpperCase()}
                                            </p>
                                        </div>
                                    </div>
                                    <div className="bg-gray-50 rounded-lg p-3">
                                        <h4 className="text-sm font-medium text-gray-700 mb-2">Nội dung:</h4>
                                        <div className="text-gray-600 whitespace-pre-line text-sm leading-relaxed max-h-40 overflow-y-auto">
                                            {doc.content}
                                        </div>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            )}
        </div>
    );
};


export default DocumentViewer;