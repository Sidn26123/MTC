import React, { useEffect, useRef, useState } from 'react';
import { SimpleDropdown } from '../../common/CommonComponents.jsx';
import { Link, useNavigate, useParams } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft, faUpload } from '@fortawesome/free-solid-svg-icons';
import { getNovelById, getNovelBySlug } from '../../services/novelService.js';
import { updateNovel, uploadNovelCover } from '../../services/publisherService.js';

function UpdateNovelInfoPage() {
    const { novelSlug } = useParams();
    const navigate = useNavigate();
    const fileInputRef = useRef(null);

    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [uploadingImage, setUploadingImage] = useState(false);

    // Form data state
    const [formData, setFormData] = useState({
        name: '',
        displayName: '',
        description: '',
        publisherNote: '',
        originalName: '',
        originalLink: '',
        novelType: 'COMPOSE',
        novelVisibility: 'PUBLIC',
        chapterReadToComment: 0,
        chapterReadToRate: 10,
        fullSetPurchaseDiscount: 0,
        novelCoverImage: '',
        genres: [],
        worldScenes: [],
        mainCharacterTraits: [],
        sects: []
    });

    // Dropdown options
    const [dropdownOptions, setDropdownOptions] = useState({
        genres: [],
        worldScenes: [],
        mainCharacterTraits: [],
        sects: []
    });

    // Novel type options
    const novelTypeOptions = [
        { value: 'COMPOSE', label: 'Sáng tác' },
        { value: 'TRANSLATE', label: 'Dịch thuật' },
        { value: 'CONVERT', label: 'Convert' }
    ];

    // Novel visibility options
    const novelVisibilityOptions = [
        { value: 'PUBLIC', label: 'Công khai' },
        { value: 'PRIVATE', label: 'Riêng tư' },
        { value: 'RESTRICTED', label: 'Hạn chế' }
    ];

    useEffect(() => {
        fetchData();
    }, [novelSlug]);

    const fetchData = async () => {
        try {
            setLoading(true);

            // Fetch novel data and dropdown options in parallel
            const [
                novelResponse,
                genresResponse,
                worldScenesResponse,
                mainCharacterTraitsResponse,
                sectsResponse
            ] = await Promise.all([
                getNovelBySlug(novelSlug),
                getGenres(),
                getWorldScenes(),
                getMainCharacterTraits(),
                getSects()
            ]);

            const novel = novelResponse.data.result;
            console.log("Novel data:", novel);

            // Set form data
            setFormData({
                name: novel.name || '',
                displayName: novel.displayName || '',
                description: novel.description || '',
                publisherNote: novel.publisherNote || '',
                originalName: novel.originalName || '',
                originalLink: novel.originalLink || '',
                novelType: novel.novelType || 'COMPOSE',
                novelVisibility: novel.novelVisibility || 'PUBLIC',
                chapterReadToComment: novel.chapterReadToComment || 0,
                chapterReadToRate: novel.chapterReadToRate || 10,
                fullSetPurchaseDiscount: novel.fullSetPurchaseDiscount || 0,
                novelCoverImage: novel.novelCoverImage || '',
                genres: novel.genres || [],
                worldScenes: novel.worldScenes || [],
                mainCharacterTraits: novel.mainCharacterTraits || [],
                sects: novel.sects || []
            });

            // Set dropdown options
            setDropdownOptions({
                genres: genresResponse.data.result || [],
                worldScenes: worldScenesResponse.data.result || [],
                mainCharacterTraits: mainCharacterTraitsResponse.data.result || [],
                sects: sectsResponse.data.result || []
            });

        } catch (error) {
            console.error('Error fetching data:', error);
            alert('Có lỗi xảy ra khi tải dữ liệu!');
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (field, value) => {
        setFormData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handleImageUpload = async (event) => {
        const file = event.target.files[0];
        if (!file) return;

        // Validate file type
        if (!file.type.startsWith('image/')) {
            alert('Vui lòng chọn file hình ảnh!');
            return;
        }

        // Validate file size (max 5MB)
        if (file.size > 5 * 1024 * 1024) {
            alert('File quá lớn! Vui lòng chọn file nhỏ hơn 5MB');
            return;
        }

        try {
            setUploadingImage(true);
            const uploadResponse = await uploadNovelCover(file);
            const imageUrl = uploadResponse.data.result.url;

            handleInputChange('novelCoverImage', imageUrl);
            alert('Upload ảnh bìa thành công!');
        } catch (error) {
            console.error('Error uploading image:', error);
            alert('Có lỗi xảy ra khi upload ảnh!');
        } finally {
            setUploadingImage(false);
        }
    };

    const handleReset = () => {
        fetchData(); // Reload original data
    };

    const handleSubmit = async () => {
        if (!formData.name.trim()) {
            alert('Vui lòng nhập tên truyện!');
            return;
        }

        if (!formData.description.trim()) {
            alert('Vui lòng nhập mô tả truyện!');
            return;
        }

        try {
            setSubmitting(true);

            // Prepare data for API
            const updateData = {
                name: formData.name,
                displayName: formData.displayName,
                description: formData.description,
                publisherNote: formData.publisherNote,
                originalName: formData.originalName,
                originalLink: formData.originalLink,
                novelType: formData.novelType,
                novelVisibility: formData.novelVisibility,
                chapterReadToComment: parseInt(formData.chapterReadToComment),
                chapterReadToRate: parseInt(formData.chapterReadToRate),
                fullSetPurchaseDiscount: parseFloat(formData.fullSetPurchaseDiscount),
                novelCoverImage: formData.novelCoverImage,
                genreIds: formData.genres.map(g => g.id),
                worldSceneIds: formData.worldScenes.map(w => w.id),
                mainCharacterTraitIds: formData.mainCharacterTraits.map(m => m.id),
                sectIds: formData.sects.map(s => s.id)
            };

            console.log('Updating novel with data:', updateData);

            const response = await updateNovel(novelSlug, updateData);
            console.log('Update response:', response);

            alert('Cập nhật truyện thành công!');
            navigate(`/bookhub/books/${novelSlug}`);

        } catch (error) {
            console.error('Error updating novel:', error);
            alert('Có lỗi xảy ra khi cập nhật truyện!');
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center min-h-96">
                <div className="flex flex-col items-center space-y-4">
                    <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-yellow-600"></div>
                    <p className="text-gray-600">Đang tải dữ liệu...</p>
                </div>
            </div>
        );
    }

    return (
        <div className="container mx-auto p-4">
            <div className="flex flex-row gap-x-5 mt-3">
                <div className="flex flex-col w-1/2 rounded-md">
                    {/* Main form */}
                    <div className="flex flex-col gap-y-4 w-full p-4 bg-background-light rounded-md">
                        {/* Tên truyện */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Tên truyện *</span>
                            <input
                                value={formData.name}
                                onChange={(e) => handleInputChange('name', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                placeholder="Nhập tên truyện..."
                            />
                        </div>

                        {/* Tên hiển thị */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Tên hiển thị</span>
                            <input
                                value={formData.displayName}
                                onChange={(e) => handleInputChange('displayName', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                placeholder="Nhập tên hiển thị..."
                            />
                        </div>

                        {/* Mô tả */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Mô tả nội dung *</span>
                            <textarea
                                value={formData.description}
                                onChange={(e) => handleInputChange('description', e.target.value)}
                                className="w-full min-h-36 border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                placeholder="Nhập mô tả nội dung truyện..."
                            />
                        </div>

                        {/* Thể loại */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Thể loại</span>
                            <SimpleDropdown
                                options={dropdownOptions.genres}
                                value={formData.genres}
                                onChange={(value) => handleInputChange('genres', value)}
                                placeholder="Chọn thể loại..."
                            />
                        </div>

                        {/* Bối cảnh thế giới */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Bối cảnh thế giới</span>
                            <SimpleDropdown
                                options={dropdownOptions.worldScenes}
                                value={formData.worldScenes}
                                onChange={(value) => handleInputChange('worldScenes', value)}
                                placeholder="Chọn bối cảnh..."
                            />
                        </div>

                        {/* Tính cách nhân vật */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Tính cách nhân vật chính</span>
                            <SimpleDropdown
                                options={dropdownOptions.mainCharacterTraits}
                                value={formData.mainCharacterTraits}
                                onChange={(value) => handleInputChange('mainCharacterTraits', value)}
                                placeholder="Chọn tính cách..."
                            />
                        </div>

                        {/* Phái */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Phái</span>
                            <SimpleDropdown
                                options={dropdownOptions.sects}
                                value={formData.sects}
                                onChange={(value) => handleInputChange('sects', value)}
                                placeholder="Chọn phái..."
                            />
                        </div>

                        {/* Ghi chú của nhà xuất bản */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Ghi chú của nhà xuất bản</span>
                            <textarea
                                value={formData.publisherNote}
                                onChange={(e) => handleInputChange('publisherNote', e.target.value)}
                                className="w-full min-h-24 border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                placeholder="Nhập ghi chú..."
                            />
                        </div>

                        {/* Loại truyện */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Loại truyện</span>
                            <select
                                value={formData.novelType}
                                onChange={(e) => handleInputChange('novelType', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                            >
                                {novelTypeOptions.map(option => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </select>
                        </div>

                        {/* Quyền riêng tư */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Quyền riêng tư</span>
                            <select
                                value={formData.novelVisibility}
                                onChange={(e) => handleInputChange('novelVisibility', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                            >
                                {novelVisibilityOptions.map(option => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </select>
                        </div>

                        {/* Số chương cần đọc để bình luận */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Số chương cần đọc để bình luận</span>
                            <input
                                type="number"
                                value={formData.chapterReadToComment}
                                onChange={(e) => handleInputChange('chapterReadToComment', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                min="0"
                            />
                        </div>

                        {/* Số chương cần đọc để đánh giá */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Số chương cần đọc để đánh giá</span>
                            <input
                                type="number"
                                value={formData.chapterReadToRate}
                                onChange={(e) => handleInputChange('chapterReadToRate', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                min="0"
                            />
                        </div>

                        {/* Giảm giá mua full set */}
                        <div>
                            <span className="block text-sm font-medium mb-1">Giảm giá mua full set (%)</span>
                            <input
                                type="number"
                                value={formData.fullSetPurchaseDiscount}
                                onChange={(e) => handleInputChange('fullSetPurchaseDiscount', e.target.value)}
                                className="w-full border border-gray-500 rounded-md p-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"
                                min="0"
                                max="100"
                                step="0.1"
                            />
                        </div>
                    </div>

                    {/* Action buttons */}
                    <div className="mt-5">
                        <div className="flex flex-row justify-center items-center gap-x-5 w-full">
                            <div className="w-1/2">
                                <button
                                    onClick={handleReset}
                                    disabled={submitting}
                                    className="bg-gray-500 w-full text-white rounded-md p-3 hover:bg-gray-600 focus:outline-none focus:ring-0 hover:cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
                                >
                                    <div className="flex justify-center items-center gap-x-2">
                                        <span>Nhập Lại</span>
                                        <FontAwesomeIcon icon={faDeleteLeft} />
                                    </div>
                                </button>
                            </div>
                            <div className="w-1/2">
                                <button
                                    onClick={handleSubmit}
                                    disabled={submitting}
                                    className="bg-yellow-500 w-full text-white rounded-md p-3 hover:bg-yellow-600 focus:outline-none focus:ring-0 hover:cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
                                >
                                    <div className="flex justify-center items-center gap-x-2">
                                        <span>{submitting ? 'Đang cập nhật...' : 'Cập Nhật'}</span>
                                        <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                    </div>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Image upload section */}
                <div className="flex flex-col w-1/2 bg-background-light rounded-md p-5">
                    <div className="flex flex-col">
                        <h3 className="text-lg font-semibold mb-4">Ảnh bìa truyện</h3>

                        {/* Current image preview */}
                        {formData.novelCoverImage && (
                            <div className="mb-4">
                                <img
                                    src={formData.novelCoverImage}
                                    alt="Novel Cover"
                                    className="w-full h-64 object-cover rounded-lg border-2 border-gray-300"
                                />
                            </div>
                        )}

                        {/* Upload area */}
                        <div className="flex items-center justify-center w-full">
                            <label
                                htmlFor="dropzone-file"
                                className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
                            >
                                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                                    {uploadingImage ? (
                                        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-500 mb-4"></div>
                                    ) : (
                                        <FontAwesomeIcon icon={faUpload} className="w-8 h-8 mb-4 text-gray-500" />
                                    )}
                                    <p className="mb-2 text-sm text-gray-500">
                                        <span className="font-semibold">
                                            {uploadingImage ? 'Đang upload...' : 'Click để upload'}
                                        </span>
                                        {!uploadingImage && ' hoặc kéo thả'}
                                    </p>
                                    <p className="text-xs text-gray-500">PNG, JPG hoặc GIF (MAX. 5MB)</p>
                                </div>
                                <input
                                    id="dropzone-file"
                                    ref={fileInputRef}
                                    type="file"
                                    className="hidden"
                                    accept="image/*"
                                    onChange={handleImageUpload}
                                    disabled={uploadingImage}
                                />
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}


// function UpdateNovelInfoPage() {
//     const {novelSlug } = useParams();
//     const [novelData, setNovelData] = useState({});
//
//     useEffect(() => {
//         console.log(novelSlug)
//         getNovelBySlug(novelSlug).then((res) => {
//             console.log("novel: ", res.data.result)
//             setNovelData(res.data.result);
//         })
//     }, [novelSlug])
//
//
//     return (
//         <>
//             <div>
//                 <div className={"flex flex-row gap-x-5 mt-3"}>
//                     <div className={'flex flex-col w-1/2  rounded-md'}>
//                         {/*Main part*/}
//                         <div className={'flex flex-col gap-y-2 w-full p-2 pl-3 bg-background-light rounded-md'}>
//                             <div>
//                                 <span>Loai</span>
//                                 <input
//                                     className={'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
//                             </div>
//
//                             <div className={'flex flex-col mt-5 '}>
//                                 <span>Noi dung cac chuong</span>
//                                 <textarea
//                                     className={'w-full min-h-36 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
//                             </div>
//                             <div className={'flex flex-col mt-5 gap-y-2'}>
//                                 <span>Noi dung cac chuong</span>
//                                 <SimpleDropdown />
//                             </div>
//                             <div className={'flex flex-col mt-5 gap-y-2'}>
//                                 <span>Noi dung cac chuong</span>
//                                 <SimpleDropdown />
//                             </div>
//                             <div className={'flex flex-col mt-5 gap-y-2'}>
//                                 <span>Noi dung cac chuong</span>
//                                 <SimpleDropdown />
//                             </div>
//                             <div className={'flex flex-col mt-5 gap-y-2'}>
//                                 <span>Noi dung cac chuong</span>
//                                 <SimpleDropdown />
//                             </div>
//                             <div className={'flex flex-col mt-5 '}>
//                                 <span>Noi dung cac chuong</span>
//                                 <textarea
//                                     className={'w-full min-h-36 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
//                             </div>
//                             <div className={'flex flex-col mt-5 '}>
//                                 <span>Loai</span>
//                                 <input
//                                     className={'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
//                                     type={"number"}
//                                 />
//                             </div>
//                             <div className={'flex flex-col mt-5 '}>
//                                 <span>Loai</span>
//                                 <input
//                                     className={'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
//                                     type={"number"}
//                                 />
//                             </div>
//
//                         </div>
//                         {/*Navigator*/}
//                         <div className={"mt-5"}>
//                             <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
//                                 <div className={"w-1/2"}>
//                                     <button
//                                         className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
//
//                                     >
//                                         <Link to={"/bookhub/books/1/chapters"}>
//                                             <div className={"flex justify-center items-center gap-x-2"}>
//                                                 <span className={"ml-2"}>Nhập Lại</span>
//                                                 <FontAwesomeIcon icon={faDeleteLeft} />
//                                             </div>
//                                         </Link>
//                                     </button>
//                                 </div>
//                                 <div className={'w-1/2'}>
//                                     <button
//                                         className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
//
//                                     >
//                                         <Link to={"/bookhub/books/1/chapters"}>
//                                             <div className={"flex justify-center items-center gap-x-2"}>
//                                                 <span className={"ml-2"}>Đăng Chương</span>
//                                                 <FontAwesomeIcon icon={faArrowUpFromBracket} />
//                                             </div>
//                                         </Link>
//
//                                     </button>
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                     <div className={'flex flex-col w-1/2 bg-background-light rounded-md p-5'}>
//                         <div className={"flex flex-col"}>
//
//                             <div className="flex items-center justify-center w-full">
//                                 <label htmlFor="dropzone-file"
//                                        className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
//                                     <div className="flex flex-col items-center justify-center pt-5 pb-6">
//                                         <svg className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
//                                              aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
//                                              viewBox="0 0 20 16">
//                                             <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"
//                                                   strokeWidth="2"
//                                                   d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
//                                         </svg>
//                                         <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span
//                                             className="font-semibold">Click to upload</span> or drag and drop</p>
//                                         <p className="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF
//                                             (MAX. 800x400px)</p>
//                                     </div>
//                                     <input id="dropzone-file" type="file" className="hidden" />
//                                 </label>
//                             </div>
//
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         </>
//     );
// }

export default UpdateNovelInfoPage;