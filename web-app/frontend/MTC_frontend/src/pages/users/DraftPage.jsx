import React, { useEffect, useState } from 'react';
import { CategoryDropdown } from '../../common/CommonComponents.jsx';
import { getAllMyNovels } from '../../services/novelService.js';
import { createDraft, getDraftById, updateDraft } from '../../services/publisherService.js';
import { useSetDrafts } from '../../stores/publisherStore.js';

// function DraftPage() {
//     const [content, setContent] = useState('');
//     const [wordCount, setWordCount] = useState(0);
//     const [novels, setNovels] = useState([]);
//     const [currentNovel, setCurrentNovel] = useState(null);
//     const fetchAllMyNovels = getAllMyNovels;
//     const setDrafts = useSetDrafts();
//     const handleContentChange = (e) => {
//         const text = e.target.value;
//         setContent(text);
//         setWordCount(text.trim().split(/\s+/).length);
//     };
//
//     const handleReset = () => {
//         setContent('');
//         setWordCount(0);
//     };
//
//     const handleSubmit = () => {
//         // Handle novel submission logic here
//         var data = {
//             content: content,
//             novelId: currentNovel ? currentNovel.id : null,
//             title: currentNovel ? currentNovel.name : 'Bản thảo mới',
//         }
//         createDraft(data).then(r => {
//         });
//     };
//
//     function handleSelectField(selected) {
//         setCurrentNovel(selected);
//     }
//
//     useEffect(() => {
//         const res = fetchAllMyNovels().then(r => {
//             setNovels(r.data.result);
//             // setDrafts(r.data.result);
//         });
//     }, []);
//
//     return (
//         <div className="flex flex-col p-5 bg-gray-800 text-gray-200 rounded-lg">
//             <h2 className="text-lg font-semibold mb-4">Thêm bản thảo</h2>
//             <p className="text-sm mb-4">
//                 Bạn có thể thêm bản thảo qua xảt bản hoặc nhập trực tiếp, hoặc đơn giản chỉ cần viết một đoạn văn để không lưu lại
//             </p>
//             <textarea
//                 value={content}
//                 onChange={handleContentChange}
//                 placeholder="Nội dung"
//                 className="w-full h-64 p-4 bg-gray-700 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-yellow-500 resize-none"
//             />
//             <div className="mt-4 text-sm">
//                 Số từ: {wordCount}
//             </div>
//             <div className="mt-5 flex justify-center gap-x-5">
//                 <button
//                     onClick={handleReset}
//                     className="w-1/2 bg-gray-600 text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none"
//                 >
//                     <div className="flex justify-center items-center gap-x-2">
//                         <span>Nhập Lại</span>
//                         {/* Add FontAwesomeIcon if available */}
//                     </div>
//                 </button>
//                 <button
//                     onClick={handleSubmit}
//                     className="w-1/2 bg-yellow-600 text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none"
//                 >
//                     <div className="flex justify-center items-center gap-x-2">
//                         <span>Lưu</span>
//                         {/* Add FontAwesomeIcon if available */}
//                     </div>
//                 </button>
//             </div>
//             <div className="mt-4">
//                 {/*<select className="w-full p-2 bg-gray-700 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-yellow-500">*/}
//                 {/*    <option>Chọn thể loại</option>*/}
//                 {/*    /!* Add category options here *!/*/}
//                 {/*</select>*/}
//                 <CategoryDropdown
//                     dropdown={novels}
//                     placeholder={'Truyện'}
//                     onSelect={(selected) => handleSelectField(selected)}
//                 />
//             </div>
//         </div>
//     );
// }

import { useParams } from 'react-router-dom';

function DraftPage() {
    const { id } = useParams(); // nếu có id ⇒ đang edit
    console.log("DraftPage id:", id);
    const [content, setContent] = useState('');
    const [wordCount, setWordCount] = useState(0);
    const [novels, setNovels] = useState([]);
    const [currentNovel, setCurrentNovel] = useState(null);

    const fetchAllMyNovels = getAllMyNovels;

    useEffect(() => {
        fetchAllMyNovels().then(r => setNovels(r.data.result));
    }, []);

    useEffect(() => {
        if (id) {
            getDraftById(id).then((res) => {
                const draft = res.data.result;
                setContent(draft.content);
                setWordCount(draft.content.trim().split(/\s+/).length);
                setCurrentNovel({ id: draft.novelId, name: draft.title });
            });
        }
    }, [id]);

    const handleContentChange = (e) => {
        const text = e.target.value;
        setContent(text);
        setWordCount(text.trim().split(/\s+/).length);
    };

    const handleReset = () => {
        setContent('');
        setWordCount(0);
        setCurrentNovel(null);
    };

    const handleSubmit = () => {
        const data = {
            content: content,
            novelId: currentNovel?.id || null,
            title: currentNovel?.name || 'Bản thảo mới',
        };

        if (id) {
            updateDraft(id, data).then(); // edit mode
        } else {
            createDraft(data).then(); // create mode
        }
    };

    return (
        <div className="flex flex-col p-5 bg-gray-800 text-gray-200 rounded-lg">
            <h2 className="text-lg font-semibold mb-4">{id ? 'Chỉnh sửa bản thảo' : 'Thêm bản thảo'}</h2>

            <textarea
                value={content}
                onChange={handleContentChange}
                placeholder="Nội dung"
                className="w-full h-64 p-4 bg-gray-700 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-yellow-500 resize-none"
            />

            <div className="mt-4 text-sm">Số từ: {wordCount}</div>

            <div className="mt-5 flex justify-center gap-x-5">
                <button onClick={handleReset} className="w-1/2 bg-gray-600 text-white rounded-md p-2 hover:bg-yellow-500">Nhập lại</button>
                <button onClick={handleSubmit} className="w-1/2 bg-yellow-600 text-white rounded-md p-2 hover:bg-yellow-500">Lưu</button>
            </div>

            <div className="mt-4">
                <CategoryDropdown
                    dropdown={novels}
                    placeholder={'Truyện'}
                    onSelect={(selected) => setCurrentNovel(selected)}
                />
            </div>
        </div>
    );
}


export default DraftPage;