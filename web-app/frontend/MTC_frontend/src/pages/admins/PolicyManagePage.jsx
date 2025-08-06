// import React, { useState } from 'react';
// import { useEffect } from 'react';

// import { getAllPolicies } from '../../services/policyService';

// const PolicyManagePage = () => {

//     const [data, setData] = useState([]);

//     const [currentPage, setCurrentPage] = useState(1);
//     const itemsPerPage = 5;
//     const totalPages = Math.ceil(data.length / itemsPerPage);

//     const [showModal, setShowModal] = useState(false);
//     const [inputValue, setInputValue] = useState('');
//     const [editId, setEditId] = useState(null);

//     const currentData = data.slice(
//         (currentPage - 1) * itemsPerPage,
//         currentPage * itemsPerPage
//     );

//     const fetch = async () => {
//         try {
//             const response = await getAllPolicies();
//             console.log("getAllPolicies response: ", response);
//             setData(response.data?.result || []); // Assuming genres is an object with a 'result' property
//         } catch (error) {
//             console.error("Lỗi khi tải danh sách policy:", error);
//         }
//     };



//     useEffect(() => {

//         fetch();
//     }, []);

//     const openAddModal = () => {
//         setInputValue('');
//         setEditId(null);
//         setShowModal(true);
//     };

//     const openEditModal = (item) => {
//         setInputValue(item.name);
//         setEditId(item.id);
//         setShowModal(true);
//     };


//     const handleConfirm = async () => {
//         if (inputValue.trim() === '') return;

//         try {
//             if (editId !== null) {
//                 // Gọi API cập nhật
//                 console.log("Updating genre with ID:", editId, "and name:", inputValue);
//                 // const updated = await updateGenre({ id: editId, name: inputValue });
//                 // Cập nhật vào danh sách local
//                 setData(prev =>
//                     prev.map(item =>
//                         item.id === editId ? { ...item, name: updated.name } : item
//                     )
//                 );
//             } else {
//                 // Gọi API tạo mới
//                 // const created = await addGenre({ name: inputValue });
//                 // Thêm vào danh sách local
//                 setData(prev => [...prev, created]);
//             }
//             await fetch();

//             setShowModal(false);
//             setInputValue('');
//         } catch (error) {
//             console.error("Lỗi khi thêm/cập nhật thể loại:", error);
//             alert("Đã xảy ra lỗi. Vui lòng thử lại.");
//         }
//     };

//     return (
//         <div className="w-full px-4 sm:px-6 lg:px-8 mt-10">
//             <div className="w-full rounded-xl border border-white p-6 bg-gray-800 shadow-lg">

//                 <div className="flex justify-between items-center mb-4">
//                     <h2 className="text-lg font-medium text-gray-100">Quản lý điều khoản dịch vụ</h2>
//                     <button
//                         className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
//                         onClick={openAddModal}
//                     >
//                         Thêm
//                     </button>
//                 </div>

//                 <table className="w-full table-auto border-collapse text-sm sm:text-base">
//                     <thead>
//                         <tr>
//                             <th className="border-b py-2 text-left text-gray-100">Tên</th>
//                             <th className="border-b py-2 text-left text-gray-100">Hành động</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {currentData.map((item) => (
//                             <tr key={item.id}>
//                                 <td className="py-2 text-gray-100">{item.title}</td>

//                                 <td className="px-4 py-3 whitespace-nowrap">

//                     </td>
//                                 <td className="py-2">
//                                     <button
//                                         className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
//                                         onClick={() => openEditModal(item)}
//                                     >
//                                         Chỉnh sửa
//                                     </button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>

//                 <div className="flex justify-center items-center gap-4 mt-4">
//                     <button
//                         onClick={() => setCurrentPage(Math.max(currentPage - 1, 1))}
//                         disabled={currentPage === 1}
//                         className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
//                     >
//                         Trang trước
//                     </button>
//                     <span className="text-gray-100">
//                         Trang {currentPage} / {totalPages}
//                     </span>
//                     <button
//                         onClick={() => setCurrentPage(Math.min(currentPage + 1, totalPages))}
//                         disabled={currentPage === totalPages}
//                         className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
//                     >
//                         Trang sau
//                     </button>
//                 </div>

//                 {/* Modal giữ nguyên */}
//                 {showModal && (
//                     <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
//                         <div className="bg-gray-700 p-6 rounded-lg w-80">
//                             <h3 className="text-lg font-semibold mb-4">
//                                 {editId !== null ? 'Chỉnh sửa' : 'Thêm'} Item
//                             </h3>
//                             <input
//                                 type="text"
//                                 value={inputValue}
//                                 onChange={(e) => setInputValue(e.target.value)}
//                                 className="border w-full mb-4 p-2 rounded text-gray-100 bg-gray-800"
//                                 placeholder="Nhập tên..."
//                             />
//                             <button
//                                 onClick={handleConfirm}
//                                 className="bg-blue-600 text-white px-4 py-2 rounded w-full hover:bg-blue-700"
//                             >
//                                 Xác nhận
//                             </button>
//                             <button
//                                 onClick={() => setShowModal(false)}
//                                 className="mt-2 text-center w-full text-sm text-gray-100 hover:underline"
//                             >
//                                 Hủy
//                             </button>
//                         </div>
//                     </div>
//                 )}
//             </div>
//         </div>
//     );


//     // return (
//     //     // <div className="max-w-md mx-auto rounded-xl border border-white p-4 mt-10">
//     //     <div className="max-full mx-auto rounded-xl border border-white p-6 mt-10 bg-gray-800">

//     //         <div className="flex justify-between items-center mb-4">
//     //             <h2 className="text-lg font-medium text-gray-100">Quản lý điều khoản dịch vụ</h2>
//     //             <button
//     //                 className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
//     //                 onClick={openAddModal}
//     //             >
//     //                 Thêm
//     //             </button>
//     //         </div>

//     //         {/* <table className="w-full table-auto border-collapse"> */}
//     //         <table className="w-full table-auto border-collapse text-sm sm:text-base">

//     //             <thead>
//     //                 <tr>
//     //                     <th className="border-b py-2 text-left text-gray-100">Tên</th>
//     //                     <th className="border-b py-2 text-left text-gray-100">Hành động</th>
//     //                 </tr>
//     //             </thead>
//     //             <tbody>
//     //                 {currentData.map((item) => (
//     //                     <tr key={item.id}>
//     //                         <td className="py-2 text-gray-100">{item.name}</td>
//     //                         <td className="py-2">
//     //                             <button
//     //                                 className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
//     //                                 onClick={() => openEditModal(item)}
//     //                             >
//     //                                 Chỉnh sửa
//     //                             </button>
//     //                         </td>
//     //                     </tr>
//     //                 ))}
//     //             </tbody>
//     //         </table>

//     //         {/* Pagination */}
//     //         <div className="flex justify-center items-center gap-4 mt-4">
//     //             <button
//     //                 onClick={() => setCurrentPage(Math.max(currentPage - 1, 1))}
//     //                 disabled={currentPage === 1}
//     //                 className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
//     //             >
//     //                 Trang trước
//     //             </button>
//     //             <span className="text-gray-100">Trang {currentPage} / {totalPages}</span>
//     //             <button
//     //                 onClick={() => setCurrentPage(Math.min(currentPage + 1, totalPages))}
//     //                 disabled={currentPage === totalPages}
//     //                 className="px-3 py-1 rounded border text-gray-100 disabled:opacity-50"
//     //             >
//     //                 Trang sau
//     //             </button>
//     //         </div>

//     //         {/* Popup */}
//     //         {showModal && (
//     //             <div className="fixed inset-0 flex items-center justify-center z-50">
//     //                 <div className="bg-gray-700 p-6 rounded-lg w-80">
//     //                     <h3 className="text-lg font-semibold mb-4">
//     //                         {editId !== null ? 'Chỉnh sửa' : 'Thêm'} Item
//     //                     </h3>
//     //                     <input
//     //                         type="text"
//     //                         value={inputValue}
//     //                         onChange={(e) => setInputValue(e.target.value)}
//     //                         className="border w-full mb-4 p-2 rounded text-gray-100"
//     //                         placeholder="Nhập tên..."
//     //                     />
//     //                     <button
//     //                         onClick={handleConfirm}
//     //                         className="bg-blue-600 text-white px-4 py-2 rounded w-full hover:bg-blue-700"
//     //                     >
//     //                         Xác nhận
//     //                     </button>
//     //                     <button
//     //                         onClick={() => setShowModal(false)}
//     //                         className="mt-2 text-center w-full text-sm text-gray-100 hover:underline"
//     //                     >
//     //                         Hủy
//     //                     </button>
//     //                 </div>
//     //             </div>
//     //         )}
//     //     </div>
//     // );
// };

// export default PolicyManagePage;













import React, { useState, useEffect } from 'react';
import { getAllPolicies, createPolicy, updatePolicy } from '../../services/policyService';

const PolicyManagePage = () => {
    const [data, setData] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const totalPages = Math.ceil(data.length / itemsPerPage);

    const [showModal, setShowModal] = useState(false);
    const [titleValue, setTitleValue] = useState('');
    const [contentValue, setContentValue] = useState('');
    const [editId, setEditId] = useState(null);
    const [slug, setSlug] = useState(null);

    const currentData = data.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const fetch = async () => {
        try {
            const response = await getAllPolicies();
            console.log("getAllPolicies response: ", response);
            setData(response.data?.result || []);
        } catch (error) {
            console.error("Lỗi khi tải danh sách policy:", error);
        }
    };

    useEffect(() => {
        fetch();
    }, []);

    const openAddModal = () => {
        setTitleValue('');
        setContentValue('');
        setEditId(null);
        setShowModal(true);
    };

    const openEditModal = (item) => {
        setTitleValue(item.title || '');
        setContentValue(item.content || '');
        setEditId(item.id);
        setSlug(item.slug);
        setShowModal(true);
    };

    const handleConfirm = async () => {
        if (titleValue.trim() === '' || contentValue.trim() === '') return;

        try {
            if (editId !== null) {
                // UPDATE logic

                console.log("updated policy:", slug, titleValue, contentValue);

                const updated = await updatePolicy(slug, titleValue, contentValue);
                setData(prev => prev.map(item => item.id === editId ? updated : item));

            } else {
                // ADD logic
                console.log("Adding new policy:", titleValue, contentValue);
                console.log("slug:", titleValue.toLowerCase().replace(/\s+/g, '-'));
                const created = await createPolicy(titleValue, contentValue);
                setData(prev => [...prev, created]);

            }

            await fetch(); // refresh data
            setShowModal(false);
            setTitleValue('');
            setContentValue('');
        } catch (error) {
            console.error("Lỗi khi thêm/cập nhật điều khoản:", error);
            alert("Đã xảy ra lỗi. Vui lòng thử lại.");
        }
    };

    return (
        <div className="w-full px-4 sm:px-6 lg:px-8 mt-10">
            <div className="w-full rounded-xl border border-white p-6 bg-gray-800 shadow-lg">
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-lg font-medium text-gray-100">Quản lý điều khoản dịch vụ</h2>
                    <button
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                        onClick={openAddModal}
                    >
                        Thêm
                    </button>
                </div>

                <table className="w-full table-auto border-collapse text-sm sm:text-base">
                    <thead>
                        <tr>
                            <th className="border-b py-2 text-left text-gray-100">Tiêu đề</th>
                            <th className="border-b py-2 text-left text-gray-100">Nội dung</th>
                            <th className="border-b py-2 text-right text-gray-100">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentData.map((item) => (
                            <tr key={item.id}>
                                <td className="py-2 text-gray-100">{item.title}</td>

                                <td className="py-2 text-gray-300 max-w-[300px] overflow-hidden text-ellipsis whitespace-nowrap">
                                    {item.content?.slice(0, 100) || ''}...
                                </td>

                                <td className="py-2 text-right space-x-2">
                                    {/* <button
                                        className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600"
                                        onClick={() => openEditModal(item)}
                                    >
                                        Chi tiết
                                    </button> */}
                                    <button
                                        className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
                                        onClick={() => openEditModal(item)}
                                    >
                                        Chi tiết/Chỉnh sửa
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>

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

                {showModal && (
                    <div className="fixed inset-0 flex items-center justify-center z-50 bg-white/30 backdrop-blur-sm">
                        <div className="bg-gray-700 rounded-2xl shadow-2xl p-10 w-[700px] max-w-[90%]">
                            <h3 className="text-2xl font-bold mb-6 text-white">
                                {editId !== null ? 'Chi tiết / Chỉnh sửa' : 'Thêm mới điều khoản'}
                            </h3>

                            <label className="block mb-2 text-lg text-gray-300">Tiêu đề</label>
                            <input
                                type="text"
                                value={titleValue}
                                onChange={(e) => setTitleValue(e.target.value)}
                                className="border w-full mb-6 p-3 rounded text-white bg-gray-800 text-base"
                                placeholder="Nhập tiêu đề..."
                            />

                            <label className="block mb-2 text-lg text-gray-300">Nội dung</label>
                            <textarea
                                value={contentValue}
                                onChange={(e) => setContentValue(e.target.value)}
                                className="border w-full mb-6 p-3 rounded text-white bg-gray-800 min-h-[160px] text-base"
                                placeholder="Nhập nội dung..."
                            />

                            <button
                                onClick={handleConfirm}
                                className="bg-blue-600 text-white px-5 py-3 rounded w-full text-lg hover:bg-blue-700"
                            >
                                Xác nhận
                            </button>
                            <button
                                onClick={() => setShowModal(false)}
                                className="mt-4 text-center w-full text-sm text-gray-100 hover:underline"
                            >
                                Hủy
                            </button>
                        </div>
                    </div>
                )}


                {/* {showModal && (
                    <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-300/30 bg-opacity-50">

                        <div className="bg-gray-700 rounded-lg shadow-lg p-8 max-w-2xl w-full">
                            <h3 className="text-lg font-semibold mb-4 text-white">
                                {editId !== null ? 'Chi tiết / Chỉnh sửa' : 'Thêm mới điều khoản'}
                            </h3>
                            <label className="block mb-2 text-gray-300">Tiêu đề</label>
                            <input
                                type="text"
                                value={titleValue}
                                onChange={(e) => setTitleValue(e.target.value)}
                                className="border w-full mb-4 p-2 rounded text-gray-100 bg-gray-800"
                                placeholder="Nhập tiêu đề..."
                            />
                            <label className="block mb-2 text-gray-300">Nội dung</label>
                            <textarea
                                value={contentValue}
                                onChange={(e) => setContentValue(e.target.value)}
                                className="border w-full mb-4 p-2 rounded text-gray-100 bg-gray-800 min-h-[120px]"
                                placeholder="Nhập nội dung..."
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
                )} */}

            </div>
        </div>
    );
};

export default PolicyManagePage;
