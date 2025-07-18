import React, { useState } from 'react';

const SectManage = () => {
    const [data, setData] = useState([
        { id: 1, name: 'Thế giới mới' },
        { id: 2, name: 'Cổ trang' },
        { id: 3, name: 'Xuyên không' },
        { id: 4, name: 'Hậu Cung' },
        { id: 5, name: 'Vương quyền' },
        { id: 6, name: 'Sản phẩm F' },
        { id: 7, name: 'Sản phẩm I' },
        { id: 8, name: 'Sản phẩm K' },
        { id: 9, name: 'Sản phẩm L' },
        { id: 10, name: 'Sản phẩm M' },
        { id: 11, name: 'Sản phẩm N' },
        { id: 12, name: 'Sản phẩm O' },
    ]);

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const totalPages = Math.ceil(data.length / itemsPerPage);

    const [showModal, setShowModal] = useState(false);
    const [inputValue, setInputValue] = useState('');
    const [editId, setEditId] = useState(null);

    const currentData = data.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const openAddModal = () => {
        setInputValue('');
        setEditId(null);
        setShowModal(true);
    };

    const openEditModal = (item) => {
        setInputValue(item.name);
        setEditId(item.id);
        setShowModal(true);
    };

    const handleConfirm = () => {
        if (inputValue.trim() === '') return;

        if (editId !== null) {
            setData(prev =>
                prev.map(item =>
                    item.id === editId ? { ...item, name: inputValue } : item
                )
            );
        } else {
            const newItem = {
                id: Date.now(),
                name: inputValue,
            };
            setData(prev => [...prev, newItem]);
        }

        setShowModal(false);
        setInputValue('');
    };

    return (
        <div className="max-w-md mx-auto rounded-xl border border-white p-4 mt-10">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-medium text-gray-100">Quản lý lưu phái</h2>
                <button
                    className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    onClick={openAddModal}
                >
                    Thêm
                </button>
            </div>

            <table className="w-full table-auto border-collapse">
                <thead>
                    <tr>
                        <th className="border-b py-2 text-left text-gray-600">Tên</th>
                        <th className="border-b py-2 text-left text-gray-600">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    {currentData.map((item) => (
                        <tr key={item.id}>
                            <td className="py-2 text-gray-100">{item.name}</td>
                            <td className="py-2">
                                <button
                                    className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
                                    onClick={() => openEditModal(item)}
                                >
                                    Chỉnh sửa
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Pagination */}
            <div className="flex justify-center items-center gap-4 mt-4">
                <button
                    onClick={() => setCurrentPage(Math.max(currentPage - 1, 1))}
                    disabled={currentPage === 1}
                    className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
                >
                    Trang trước
                </button>
                <span className="text-gray-100">Trang {currentPage} / {totalPages}</span>
                <button
                    onClick={() => setCurrentPage(Math.min(currentPage + 1, totalPages))}
                    disabled={currentPage === totalPages}
                    className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
                >
                    Trang sau
                </button>
            </div>

            {/* Popup */}
            {showModal && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                    <div className="bg-gray-700 p-6 rounded-lg w-80">
                        <h3 className="text-lg font-semibold mb-4">
                            {editId !== null ? 'Chỉnh sửa' : 'Thêm'} Item
                        </h3>
                        <input
                            type="text"
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            className="border w-full mb-4 p-2 rounded text-gray-100"
                            placeholder="Nhập tên..."
                        />
                        <button
                            onClick={handleConfirm}
                            className="bg-blue-600 text-white px-4 py-2 rounded w-full hover:bg-blue-700"
                        >
                            Xác nhận
                        </button>
                        <button
                            onClick={() => setShowModal(false)}
                            className="mt-2 text-center w-full text-sm text-gray-100 hover:underline"
                        >
                            Hủy
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default SectManage;

