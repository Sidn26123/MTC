import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { Search, ArrowLeft, ArrowRight } from "lucide-react";
import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
import { useUser } from '../../stores/userStores.js';
import { getCategories } from '../../services/categoryService.js';

// --- Các component tùy chỉnh ---
const CustomCard = ({ children, className }) => (
  <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
);
const CustomCardHeader = ({ children, className }) => (
  <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
);
const CustomCardTitle = ({ children, className }) => (
  <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
);
const CustomCardContent = ({ children, className }) => (
  <div className={`p-0 ${className}`}>{children}</div>
);
const CustomInput = ({ type, placeholder, className, ...props }) => (
  <input
    type={type}
    placeholder={placeholder}
    className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
    {...props}
  />
);
const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
  const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
  let variantClasses = "";
  let sizeClasses = "";

  switch (variant) {
    case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
    case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
    case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
    case "icon": sizeClasses = "h-8 w-8"; break;
    default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
  }

  switch (size) {
    case "default": sizeClasses = "h-10 px-4 py-2"; break;
    case "sm": sizeClasses = "h-9 px-3"; break;
    case "lg": sizeClasses = "h-11 px-8"; break;
    case "icon": sizeClasses = "h-10 w-10"; break;
    default: sizeClasses = "h-10 px-4 py-2";
  }

  return (
    <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
      {children}
    </button>
  );
};
const CustomBadge = ({ children, className }) => (
  <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
    {children}
  </span>
);

const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
  const [isOpen, setIsOpen] = useState(false);

  // Định nghĩa các trạng thái cho phép chuyển đổi
  const allowedTransitions = {
    CREATED: ["PENDING", "DELETED"],
    PENDING: ["REVIEWING", "PUBLISHED", "REJECTED", "DELETED"],
    REVIEWING: ["PUBLISHED", "REJECTED", "DELETED"],
    PUBLISHED: ["REVIEWING", "SUSPENDED", "DELETED"],
    SUSPENDED: ["PUBLISHED", "DELETED"],
    DELETED: ["PUBLISHED"],
  };

  // Lọc các tùy chọn trạng thái dựa trên trạng thái hiện tại
  const filteredStatusOptions = statusOptions.filter((status) =>
    allowedTransitions[currentStatus]?.includes(status.id) || !currentStatus
  );

  const handleStatusChange = async (newStatusId) => {
    try {
      console.log("Cập nhật trạng thái truyện:", novelId, "với trạng thái mới:", newStatusId);
      await updateNovelState(novelId, newStatusId);
      onStatusChange(newStatusId);
      setIsOpen(false);
    } catch (err) {
      console.error("Lỗi khi cập nhật trạng thái truyện:", err);
    }
  };

  return (
    <div className="relative">
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium bg-gray-100 text-gray-900 hover:bg-gray-200 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-8 px-3"
      >
        Thay đổi trạng thái
      </button>
      {isOpen && (
        <div
          className="absolute z-50 mt-2 w-40 rounded-md border border-gray-300 bg-white shadow-lg right-0"
          onMouseLeave={() => setIsOpen(false)}
        >
          {filteredStatusOptions.length > 0 ? (
            filteredStatusOptions.map((status) => (
              <div
                key={status.id}
                onClick={() => handleStatusChange(status.id)}
                className={`px-3 py-2 text-sm cursor-pointer hover:bg-gray-100 transition-colors ${
                  status.id === currentStatus ? "bg-gray-100 text-gray-900 font-semibold" : "text-gray-700"
                }`}
              >
                {status.name}
              </div>
            ))
          ) : (
            <div className="px-3 py-2 text-sm text-gray-700">Không có trạng thái khả dụng</div>
          )}
        </div>
      )}
    </div>
  );
};

// --- Component Dropdown chuyển trạng thái ---
// const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   // Define allowed transitions based on current status
//   const allowedTransitions = {
//     CREATED: ["PENDING", "DELETED"],
//     PENDING: ["REVIEWING", "PUBLISHED", "REJECTED", "DELETED"],
//     REVIEWING: ["PUBLISHED", "REJECTED", "DELETED"],
//     PUBLISHED: ["REVIEWING", "SUSPENDED", "DELETED"],
//     SUSPENDED: ["PUBLISHED", "DELETED"],
//     DELETED: ["PUBLISHED"],
//   };

//   // Filter status options based on allowed transitions
//   const filteredStatusOptions = statusOptions.filter((status) =>
//     allowedTransitions[currentStatus]?.includes(status.id)
//   );

//   const handleStatusChange = async (newStatusId) => {
//     try {
//       console.log("Cập nhật trạng thái truyện:", novelId, "với trạng thái mới:", newStatusId);
//       await updateNovelState(novelId, newStatusId);
//       onStatusChange(newStatusId);
//       setIsOpen(false);
//     } catch (err) {
//       console.error("Lỗi khi cập nhật trạng thái truyện:", err);
//     }
//   };

//   return (
//     <div className="relative">
//       <button
//         onClick={() => setIsOpen(!isOpen)}
//         className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium bg-gray-100 text-gray-900 hover:bg-gray-200 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-8 px-3"
//       >
//         Thay đổi trạng thái
//       </button>
//       {isOpen && (
//         <div
//           className="absolute z-50 mt-2 w-40 rounded-md border border-gray-300 bg-white shadow-lg right-0"
//           onMouseLeave={() => setIsOpen(false)}
//         >
//           {filteredStatusOptions.length > 0 ? (
//             filteredStatusOptions.map((status) => (
//               <div
//                 key={status.id}
//                 onClick={() => handleStatusChange(status.id)}
//                 className={`px-3 py-2 text-sm cursor-pointer hover:bg-gray-100 transition-colors ${
//                   status.id === currentStatus ? "bg-gray-100 text-gray-900 font-semibold" : "text-gray-700"
//                 }`}
//               >
//                 {status.name}
//               </div>
//             ))
//           ) : (
//             <div className="px-3 py-2 text-sm text-gray-700">Không có trạng thái khả dụng</div>
//           )}
//         </div>
//       )}
//     </div>
//   );
// };

// --- Component chính ---
export default function NovelManagePage() {
  const user = useUser();
  const navigate = useNavigate();
  const [filters, setFilters] = useState({
    searchText: '',
    sortBy: 'createdAt',
    sortDirection: 'desc',
    novelProgressStatus: [],
    novelType: [],
    novelStates: [],
    novelVisibility: [],
    genres: [],
    worldScene: [],
    novelAttribute: [],
    totalChapterOfNovelFrom: -1,
    totalChapterOfNovelTo: -1,
    ratingFrom: -1,
    ratingTo: -1,
    mainCharacterTrait: [],
    authorName: '',
    sects: [],
    page: 1,
    size: 10,
  });
  const [novels, setNovels] = useState([]);
  const [categories, setCategories] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState(null);
  const [showCategoryPopup, setShowCategoryPopup] = useState(false);
  const [showStatusDropdown, setShowStatusDropdown] = useState(false);
  const categoryPopupRef = useRef();
  const statusDropdownRef = useRef();
  const [statusOptions, setStatusOptions] = useState([]);

  const handleFilterChange = (key, value) => {
    setFilters((prev) => {
      const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
      if (isEqual) return prev;
      return { ...prev, [key]: value, page: 1 };
    });
  };

  const handlePageChange = (newPage) => {
    setFilters((prev) => ({ ...prev, page: newPage }));
  };

  const handleStatusChange = (novelId, newStatusId) => {
    setNovels((prevNovels) =>
      prevNovels.map((novel) =>
        novel.id === novelId
          ? {
              ...novel,
              novelStates: newStatusId,
              novelStateLabel: statusOptions.find((status) => status.id === newStatusId)?.name || novel.novelStateLabel,
            }
          : novel
      )
    );
  };

  const handleNovelClick = (slug) => {
    navigate(`/truyen/${slug}`);
  };

  const handleSortByCreatedAt = () => {
    setFilters((prev) => ({
      ...prev,
      sortBy: 'createdAt',
      sortDirection: prev.sortBy === 'createdAt' && prev.sortDirection === 'asc' ? 'desc' : 'asc',
      page: 1,
    }));
  };

  useEffect(() => {
    const fetchNovels = async () => {
      try {
        console.log("Lấy danh sách truyện với bộ lọc:", filters);
        const response = await getFilteredNovels({ ...filters, page: filters.page });
        console.log("Fetched novels:", response);
        setNovels(response.data?.result?.data || []);
        setTotalPages(response.data?.result?.totalPages || 1);
      } catch (err) {
        console.error("Lỗi khi lấy danh sách truyện:", err);
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await getCategories();
        setCategories(response.data?.result || []);
      } catch (err) {
        console.error("Lỗi khi lấy danh sách thể loại:", err);
      }
    };

    const fetchNovelStates = async () => {
      try {
        const response = await getAllNovelState();
        console.log("Fetched novel states:", response);
        setStatusOptions(response?.result || statusOptions);
      } catch (err) {
        console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
      }
    };

    if (user?.id) {
      fetchNovels();
      fetchCategories();
      fetchNovelStates();
    }

    const handleClickOutside = (event) => {
      if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
        setShowCategoryPopup(false);
      }
      if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
        setShowStatusDropdown(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [filters, user?.id]);

  const getStatusBadgeClass = (status) => {
    switch (status) {
      case "Khởi tạo": return "bg-green-100 text-green-700";
      case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
      case "Tạm dừng": return "bg-red-100 text-red-700";
      case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
      case "Đã xuất bản": return "bg-gray-100 text-green-500";
      case "Bị từ chối": return "bg-gray-100 text-red-300";
      case "Đã xóa": return "bg-gray-100 text-red-700";
      default: return "bg-gray-500 text-gray-700";
    }
  };

  return (
    <CustomCard className="w-full max-w-4xl mx-auto bg-gray-800 ">
      <CustomCardHeader>
        <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
        <div className="flex gap-2">
          <div className="relative w-48">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
            <CustomInput
              type="search"
              placeholder="Tìm kiếm truyện..."
              value={filters.searchText}
              onChange={(e) => handleFilterChange('searchText', e.target.value)}
              className="w-full rounded-md pl-8 text-white"
            />
          </div>
          <CustomButton
            variant="outline"
            size="sm"
            className="text-white"
            onClick={() => {
              setFilters((prev) => ({
                ...prev,
                searchText: '',
                genres: [],
                novelStates: [],
                page: 1,
              }));
              setSelectedCategory(null);
              setSelectedStatus(null);
            }}
          >
            Bỏ lọc
          </CustomButton>
        </div>
      </CustomCardHeader>
      <CustomCardContent>
        <div className="overflow-x-auto min-h-[500px]">
          <table className="min-w-full divide-y divide-gray-200">
            <thead>
              <tr>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={handleSortByCreatedAt}>
                  Ngày đăng {filters.sortBy === 'createdAt' && filters.sortDirection === 'asc' ? ' 🔼' : ' 🔽'}
                </th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
                <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
                  Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
                  {showCategoryPopup && (
                    <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
                      <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
                        onClick={() => {
                          setSelectedCategory(null);
                          handleFilterChange('genres', []);
                          setShowCategoryPopup(false);
                        }}>
                        Tất cả thể loại
                      </div>
                      {categories.map((category) => (
                        <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
                          onClick={() => {
                            setSelectedCategory(category.name);
                            handleFilterChange('genres', [category.id]);
                            setShowCategoryPopup(false);
                          }}>
                          {category.name}
                        </div>
                      ))}
                    </div>
                  )}
                </th>
                <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
                  Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
                  {showStatusDropdown && (
                    <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
                      <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
                        onClick={() => {
                          setSelectedStatus(null);
                          handleFilterChange('novelStates', []);
                          setShowStatusDropdown(false);
                        }}>
                        Tất cả trạng thái
                      </div>
                      {statusOptions.map((status) => (
                        <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
                          onClick={() => {
                            setSelectedStatus(status.name);
                            handleFilterChange('novelStates', [status.id]);
                            setShowStatusDropdown(false);
                          }}>
                          {status.name}
                        </div>
                      ))}
                    </div>
                  )}
                </th>
                <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
                  Hành động
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {Array.isArray(novels) && novels.length > 0 ? (
                novels.map((novel) => (
                  <tr key={novel.id}>
                    <td className="px-4 py-3 whitespace-nowrap">
                      <span
                        className="font-medium text-sm text-blue-400 hover:text-blue-600 cursor-pointer"
                        onClick={() => handleNovelClick(novel.slug)}
                      >
                        {novel.name}
                      </span>
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
                    <td className="px-4 py-3 whitespace-nowrap">
                      <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
                        {novel.novelStateLabel || 'N/A'}
                      </CustomBadge>
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-right">
                      <StatusChangeDropdown
                        novelId={novel.id}
                        currentStatus={novel.novelStates}
                        statusOptions={statusOptions}
                        onStatusChange={(newStatusId) => handleStatusChange(novel.id, newStatusId)}
                      />
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
                    Không tìm thấy truyện nào.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </CustomCardContent>
      <div className="flex items-center justify-between p-4 border-t border-gray-200">
        <CustomButton
          variant="outline"
          className="flex items-center gap-2 text-sm bg-transparent"
          disabled={filters.page === 1}
          onClick={() => handlePageChange(filters.page - 1)}
        >
          <ArrowLeft className="h-4 w-4" />
          Trang trước
        </CustomButton>
        <div className="flex items-center gap-2">
          {Array.from({ length: totalPages }, (_, i) => (
            <CustomButton
              key={i}
              variant={filters.page === i + 1 ? "default" : "ghost"}
              className="h-8 w-8 p-0 text-sm"
              onClick={() => handlePageChange(i + 1)}
            >
              {i + 1}
            </CustomButton>
          ))}
        </div>
        <CustomButton
          variant="outline"
          className="flex items-center gap-2 text-sm bg-transparent"
          disabled={filters.page >= totalPages}
          onClick={() => handlePageChange(filters.page + 1)}
        >
          Trang sau
          <ArrowRight className="h-4 w-4" />
        </CustomButton>
      </div>
    </CustomCard>
  );
}

// import React, { useState, useEffect, useRef } from "react";
// import { useNavigate } from "react-router-dom";
// import { Search, ArrowLeft, ArrowRight } from "lucide-react";
// import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
// // import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );

// // --- Component Dropdown chuyển trạng thái ---
// const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   const handleStatusChange = async (newStatusId) => {
//     try {
//       console.log("Cập nhật trạng thái truyện:", novelId, "với trạng thái mới:", newStatusId);
//       await updateNovelState(novelId, newStatusId);
//       onStatusChange(newStatusId);
//       setIsOpen(false);
//     } catch (err) {
//       console.error("Lỗi khi cập nhật trạng thái truyện:", err);
//     }
//   };

//   return (
//     <div className="relative">
//       <button
//         onClick={() => setIsOpen(!isOpen)}
//         className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium bg-gray-100 text-gray-900 hover:bg-gray-200 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-8 px-3"
//       >
//         Thay đổi trạng thái
//       </button>
//       {isOpen && (
//         <div
//           className="absolute z-50 mt-2 w-40 rounded-md border border-gray-300 bg-white shadow-lg right-0"
//           onMouseLeave={() => setIsOpen(false)}
//         >
//           {statusOptions.map((status) => (
//             <div
//               key={status.id}
//               onClick={() => handleStatusChange(status.id)}
//               className={`px-3 py-2 text-sm cursor-pointer hover:bg-gray-100 transition-colors ${
//                 status.id === currentStatus ? "bg-gray-100 text-gray-900 font-semibold" : "text-gray-700"
//               }`}
//             >
//               {status.name}
//             </div>
//           ))}
//         </div>
//       )}
//     </div>
//   );
// };

// // --- Component chính ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const navigate = useNavigate();
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortDirection: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelStates: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 1, // Trang đầu tiên là 1
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   // const [statusOptions, setStatusOptions] = useState([
//   //   { id: "CREATED", name: "Khởi tạo" },
//   //   { id: "PENDING", name: "Chờ duyệt" },
//   //   { id: "SUSPENDED", name: "Tạm dừng" },
//   //   { id: "REVIEWING", name: "Đang kiểm duyệt" },
//   //   { id: "PUBLISHED", name: "Đã xuất bản" },
//   //   { id: "REJECTED", name: "Bị từ chối" },
//   //   { id: "DELETED", name: "Đã xóa" },
//   // ]);

//     const [statusOptions, setStatusOptions] = useState([]);

//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => {
//       const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
//       if (isEqual) return prev;
//       return { ...prev, [key]: value, page: 1 }; // Reset về trang 1 khi thay đổi bộ lọc
//     });
//   };

//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   const handleStatusChange = (novelId, newStatusId) => {
//     setNovels((prevNovels) =>
//       prevNovels.map((novel) =>
//         novel.id === novelId
//           ? {
//               ...novel,
//               novelStates: newStatusId,
//               novelStateLabel: statusOptions.find((status) => status.id === newStatusId)?.name || novel.novelStateLabel,
//             }
//           : novel
//       )
//     );
//   };

//   const handleNovelClick = (slug) => {
//     navigate(`/truyen/${slug}`);
//   };

//   const handleSortByCreatedAt = () => {
//     setFilters((prev) => ({
//       ...prev,
//       sortBy: 'createdAt',
//       sortDirection: prev.sortBy === 'createdAt' && prev.sortDirection === 'asc' ? 'desc' : 'asc',
//       page: 1, // Reset về trang 1 khi thay đổi sắp xếp
//     }));
//   };

//   useEffect(() => {
//     const fetchNovels = async () => {
//       try {
//         console.log("Lấy danh sách truyện với bộ lọc:", filters);
//         // Nếu API yêu cầu page bắt đầu từ 0, trừ 1 từ filters.page
//         const response = await getFilteredNovels({ ...filters, page: filters.page });
//         console.log("Fetched novels:", response);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách truyện:", err);
//       }
//     };

//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách thể loại:", err);
//       }
//     };

//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Fetched novel states:", response);
//         setStatusOptions(response?.result || statusOptions);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-green-500";
//       case "Bị từ chối": return "bg-gray-100 text-red-300";
//       case "Đã xóa": return "bg-gray-100 text-red-700";
//       default: return "bg-gray-500 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto bg-gray-800 ">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelStates: [],
//                 page: 1, // Reset về trang 1
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto min-h-[500px]">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={handleSortByCreatedAt}>
//                   Ngày đăng {filters.sortBy === 'createdAt' && filters.sortDirection === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelStates', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelStates', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
//                   Hành động
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <span
//                         className="font-medium text-sm text-blue-400 hover:text-blue-600 cursor-pointer"
//                         onClick={() => handleNovelClick(novel.slug)}
//                       >
//                         {novel.name}
//                       </span>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <StatusChangeDropdown
//                         novelId={novel.id}
//                         currentStatus={novel.novelStates}
//                         statusOptions={statusOptions}
//                         onStatusChange={(newStatusId) => handleStatusChange(novel.id, newStatusId)}
//                       />
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 1} // Vô hiệu hóa nếu đang ở trang 1
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Trang trước
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i + 1 ? "default" : "ghost"} // So sánh với i + 1 vì page bắt đầu từ 1
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i + 1)}
//             >
//               {i + 1} {/* Hiển thị số trang bắt đầu từ 1 */}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages} // Vô hiệu hóa nếu ở trang cuối
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Trang sau
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }




















// /---------------------------------------------------

// import React, { useState, useEffect, useRef } from "react";
// import { useNavigate } from "react-router-dom"; // Thêm useNavigate
// import { Search, ArrowLeft, ArrowRight } from "lucide-react";
// // import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
// import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );

// // --- Component Dropdown chuyển trạng thái ---
// const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   const handleStatusChange = async (newStatusId) => {
//     try {
//       // await updateNovelState(novelId, newStatusId);
//       onStatusChange(newStatusId);
//       setIsOpen(false);
//     } catch (err) {
//       console.error("Lỗi khi cập nhật trạng thái truyện:", err);
//     }
//   };

//   return (
//     <div className="relative">
//       <button
//         onClick={() => setIsOpen(!isOpen)}
//         className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium bg-gray-100 text-gray-900 hover:bg-gray-200 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-8 px-3"
//       >
//         Thay đổi trạng thái
//       </button>
//       {isOpen && (
//         <div
//           className="absolute z-50 mt-2 w-40 rounded-md border border-gray-300 bg-white shadow-lg right-0"
//           onMouseLeave={() => setIsOpen(false)}
//         >
//           {statusOptions.map((status) => (
//             <div
//               key={status.id}
//               onClick={() => handleStatusChange(status.id)}
//               className={`px-3 py-2 text-sm cursor-pointer hover:bg-gray-100 transition-colors ${
//                 status.id === currentStatus ? "bg-gray-100 text-gray-900 font-semibold" : "text-gray-700"
//               }`}
//             >
//               {status.name}
//             </div>
//           ))}
//         </div>
//       )}
//     </div>
//   );
// };

// // --- Component chính ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const navigate = useNavigate(); // Thêm useNavigate
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   const [statusOptions, setStatusOptions] = useState([
//     { id: "CREATED", name: "Khởi tạo" },
//     { id: "PENDING", name: "Chờ duyệt" },
//     { id: "SUSPENDED", name: "Tạm dừng" },
//     { id: "REVIEWING", name: "Đang kiểm duyệt" },
//     { id: "PUBLISHED", name: "Đã xuất bản" },
//     { id: "REJECTED", name: "Bị từ chối" },
//     { id: "DELETED", name: "Đã xóa" },
//   ]);

//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => {
//       const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
//       if (isEqual) return prev;
//       return { ...prev, [key]: value, page: 0 };
//     });
//   };

//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   const handleStatusChange = (novelId, newStatusId) => {
//     setNovels((prevNovels) =>
//       prevNovels.map((novel) =>
//         novel.id === novelId
//           ? {
//               ...novel,
//               novelState: newStatusId,
//               novelStateLabel: statusOptions.find((status) => status.id === newStatusId)?.name || novel.novelStateLabel,
//             }
//           : novel
//       )
//     );
//   };

//   // Hàm xử lý click vào tên truyện
//   const handleNovelClick = (novelId) => {
//     navigate(`/novels/${novelId}`); // Chuyển hướng đến trang chi tiết truyện
//   };

//   useEffect(() => {
//     const fetchNovels = async () => {
//       try {
//         const response = await getFilteredNovels({ ...filters });
//         console.log("Fetched novels:", response);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách truyện:", err);
//       }
//     };

//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách thể loại:", err);
//       }
//     };

//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Fetched novel states:", response);
//         setStatusOptions(response?.result || statusOptions);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-gray-500";
//       case "Bị từ chối": return "bg-gray-100 text-gray-300";
//       case "Đã xóa": return "bg-gray-100 text-gray-100";
//       default: return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelState: [],
//                 page: 0,
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto min-h-[500px]">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}>
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelState', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelState', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
//                   Hành động
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <span
//                         className="font-medium text-sm text-blue-400 hover:text-blue-600 cursor-pointer"
//                         onClick={() => handleNovelClick(novel.id)}
//                       >
//                         {novel.name}
//                       </span>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <StatusChangeDropdown
//                         novelId={novel.id}
//                         currentStatus={novel.novelState}
//                         statusOptions={statusOptions}
//                         onStatusChange={(newStatusId) => handleStatusChange(novel.id, newStatusId)}
//                       />
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Trang trước
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Trang sau
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }
























//----------------------------------


// import React, { useState, useEffect, useRef } from "react";
// import { Search, ArrowLeft, ArrowRight } from "lucide-react";
// // import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
// import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );

// // --- Component Dropdown chuyển trạng thái ---
// const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   // Hàm xử lý thay đổi trạng thái
//   const handleStatusChange = async (newStatusId) => {
//     try {
//       // await updateNovelState(novelId, newStatusId); // Gọi API cập nhật trạng thái
//       onStatusChange(newStatusId); // Cập nhật giao diện
//       setIsOpen(false); // Đóng dropdown
//     } catch (err) {
//       console.error("Lỗi khi cập nhật trạng thái truyện:", err);
//     }
//   };

//   return (
//     <div className="relative">
//       {/* Nút trigger dropdown */}
//       <button
//         onClick={() => setIsOpen(!isOpen)}
//         className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium bg-gray-100 text-gray-900 hover:bg-gray-200 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-500 h-8 px-3"
//       >
//         Thay đổi trạng thái
//       </button>

//       {/* Nội dung dropdown */}
//       {isOpen && (
//         <div
//           className="absolute z-50 mt-2 w-40 rounded-md border border-gray-300 bg-white shadow-lg right-0"
//           onMouseLeave={() => setIsOpen(false)}
//         >
//           {statusOptions.map((status) => (
//             <div
//               key={status.id}
//               onClick={() => handleStatusChange(status.id)}
//               className={`px-3 py-2 text-sm cursor-pointer hover:bg-gray-100 transition-colors ${
//                 status.id === currentStatus ? "bg-gray-100 text-gray-900 font-semibold" : "text-gray-700"
//               }`}
//             >
//               {status.name}
//             </div>
//           ))}
//         </div>
//       )}
//     </div>
//   );
// };

// // --- Component chính ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   const [statusOptions, setStatusOptions] = useState([
//     { id: "CREATED", name: "Khởi tạo" },
//     { id: "PENDING", name: "Chờ duyệt" },
//     { id: "SUSPENDED", name: "Tạm dừng" },
//     { id: "REVIEWING", name: "Đang kiểm duyệt" },
//     { id: "PUBLISHED", name: "Đã xuất bản" },
//     { id: "REJECTED", name: "Bị từ chối" },
//     { id: "DELETED", name: "Đã xóa" },
//   ]);

//   // Hàm xử lý thay đổi bộ lọc
//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => {
//       const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
//       if (isEqual) return prev;
//       return { ...prev, [key]: value, page: 0 };
//     });
//   };

//   // Hàm xử lý thay đổi trang
//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   // Hàm xử lý thay đổi trạng thái truyện trên giao diện
//   const handleStatusChange = (novelId, newStatusId) => {
//     setNovels((prevNovels) =>
//       prevNovels.map((novel) =>
//         novel.id === novelId
//           ? {
//               ...novel,
//               novelState: newStatusId,
//               novelStateLabel: statusOptions.find((status) => status.id === newStatusId)?.name || novel.novelStateLabel,
//             }
//           : novel
//       )
//     );
//   };

//   useEffect(() => {
//     // Lấy danh sách truyện
//     const fetchNovels = async () => {
//       try {
//         const response = await getFilteredNovels({ ...filters });
//         console.log("Fetched novels:", response);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách truyện:", err);
//       }
//     };

//     // Lấy danh sách thể loại
//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách thể loại:", err);
//       }
//     };

//     // Lấy danh sách trạng thái truyện
//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Fetched novel states:", response);
//         setStatusOptions(response?.result || statusOptions); // Fallback to static list if API fails
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     // Xử lý sự kiện click ngoài để đóng popup
//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   // Hàm trả về class cho badge trạng thái
//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-gray-500";
//       case "Bị từ chối": return "bg-gray-100 text-gray-300";
//       case "Đã xóa": return "bg-gray-100 text-red";
//       default: return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelState: [],
//                 page: 0,
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto min-h-[500px]">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}>
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelState', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelState', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
//                   Hành động
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{novel.name}</span></td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <StatusChangeDropdown
//                         novelId={novel.id}
//                         currentStatus={novel.novelState}
//                         statusOptions={statusOptions}
//                         onStatusChange={(newStatusId) => handleStatusChange(novel.id, newStatusId)}
//                       />
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Trang trước
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Trang sau
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }

//-------------------------------------
// import React, { useState, useEffect, useRef } from "react";
// import { Search, ArrowLeft, ArrowRight } from "lucide-react";
// // import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
// import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );
// const CustomDropdownMenu = ({ children }) => {
//   const [isOpen, setIsOpen] = useState(false);
//   const toggleOpen = () => setIsOpen(!isOpen);
//   let trigger = null, content = null;
//   React.Children.forEach(children, (child) => {
//     if (child.type === CustomDropdownMenuTrigger) {
//       trigger = React.cloneElement(child, { onClick: toggleOpen });
//     } else if (child.type === CustomDropdownMenuContent) {
//       content = React.cloneElement(child, { isOpen, setIsOpen });
//     }
//   });
//   return <div className="relative">{trigger}{content}</div>;
// };
// const CustomDropdownMenuTrigger = ({ children, onClick }) => <div onClick={onClick}>{children}</div>;
// const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
//   const alignClass = align === "end" ? "right-0" : "left-0";
//   return isOpen && (
//     <div className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`} onMouseLeave={() => setIsOpen(false)}>
//       {children}
//     </div>
//   );
// };
// const CustomDropdownMenuItem = ({ children, className, ...props }) => (
//   <div className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`} {...props}>
//     {children}
//   </div>
// );

// // --- Component Dropdown lọc trạng thái ---
// const StatusChangeDropdown = ({ statusOptions, filters, onFilterChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   const handleStatusChange = (newStatusId) => {
//     // Cập nhật bộ lọc với trạng thái mới
//     onFilterChange('novelState', newStatusId === '' ? [] : [newStatusId]); // Xóa bộ lọc nếu chọn "Tất cả trạng thái"
//     setIsOpen(false); // Đóng dropdown
//   };

//   return (
//     <CustomDropdownMenu>
//       <CustomDropdownMenuTrigger>
//         <CustomButton variant="outline" size="sm" className="bg-gray-100 text-gray-900">
//           Lọc theo trạng thái
//         </CustomButton>
//       </CustomDropdownMenuTrigger>
//       <CustomDropdownMenuContent align="end">
//         <CustomDropdownMenuItem
//           onClick={() => handleStatusChange('')}
//           className={filters.novelState.length === 0 ? "bg-gray-100 text-gray-900" : ""}
//         >
//           Tất cả trạng thái
//         </CustomDropdownMenuItem>
//         {statusOptions.map((status) => (
//           <CustomDropdownMenuItem
//             key={status.id}
//             onClick={() => handleStatusChange(status.id)}
//             className={filters.novelState.includes(status.id) ? "bg-gray-100 text-gray-900" : ""}
//           >
//             {status.name}
//           </CustomDropdownMenuItem>
//         ))}
//       </CustomDropdownMenuContent>
//     </CustomDropdownMenu>
//   );
// };

// // --- Component chính ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   const [statusOptions, setStatusOptions] = useState([]);

//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => {
//       const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
//       if (isEqual) return prev;
//       return { ...prev, [key]: value, page: 0 };
//     });
//   };

//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   useEffect(() => {
//     const fetchNovels = async () => {
//       try {
//         const response = await getFilteredNovels({ ...filters });
//         console.log("Fetched novels:", response);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách truyện:", err);
//       }
//     };

//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách thể loại:", err);
//       }
//     };

//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Fetched novel states:", response);
//         setStatusOptions(response?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-gray-500";
//       case "Bị từ chối": return "bg-gray-100 text-gray-300";
//       case "Đã xóa": return "bg-gray-100 text-gray-100";
//       default: return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelState: [],
//                 page: 0,
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto min-h-[500px]">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}>
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelState', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelState', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
//                   Hành động
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{novel.name}</span></td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <StatusChangeDropdown
//                         statusOptions={statusOptions}
//                         filters={filters}
//                         onFilterChange={handleFilterChange}
//                       />
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Trang trước
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Trang sau
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }














// import React, { useState, useEffect, useRef } from "react";
// import { Search, ArrowLeft, ArrowRight } from "lucide-react";
// // import { getFilteredNovels, getAllNovelState, updateNovelState } from '../../services/novelService.js';
// import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );
// const CustomDropdownMenu = ({ children }) => {
//   const [isOpen, setIsOpen] = useState(false);
//   const toggleOpen = () => setIsOpen(!isOpen);
//   let trigger = null, content = null;
//   React.Children.forEach(children, (child) => {
//     if (child.type === CustomDropdownMenuTrigger) {
//       trigger = React.cloneElement(child, { onClick: toggleOpen });
//     } else if (child.type === CustomDropdownMenuContent) {
//       content = React.cloneElement(child, { isOpen, setIsOpen });
//     }
//   });
//   return <div className="relative">{trigger}{content}</div>;
// };
// const CustomDropdownMenuTrigger = ({ children, onClick }) => <div onClick={onClick}>{children}</div>;
// const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
//   const alignClass = align === "end" ? "right-0" : "left-0";
//   return isOpen && (
//     <div className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`} onMouseLeave={() => setIsOpen(false)}>
//       {children}
//     </div>
//   );
// };
// const CustomDropdownMenuItem = ({ children, className, ...props }) => (
//   <div className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`} {...props}>
//     {children}
//   </div>
// );

// // --- Component Dropdown chuyển trạng thái ---
// const StatusChangeDropdown = ({ novelId, currentStatus, statusOptions, onStatusChange }) => {
//   const [isOpen, setIsOpen] = useState(false);

//   // Hàm xử lý thay đổi trạng thái
//   const handleStatusChange = async (newStatusId) => {
//     try {
//       // await updateNovelState(novelId, newStatusId); // Gọi API cập nhật trạng thái
//       // onStatusChange(newStatusId); // Cập nhật giao diện
//       // setIsOpen(false); // Đóng dropdown
//     } catch (err) {
//       console.error("Lỗi khi cập nhật trạng thái truyện:", err);
//     }
//   };

//   return (
//     <CustomDropdownMenu>
//       <CustomDropdownMenuTrigger>
//         <CustomButton variant="outline" size="sm" className="bg-gray-100 text-gray-900">
//           Thay đổi trạng thái
//         </CustomButton>
//       </CustomDropdownMenuTrigger>
//       <CustomDropdownMenuContent align="end">
//         {statusOptions.map((status) => (
//           <CustomDropdownMenuItem
//             key={status.id}
//             onClick={() => handleStatusChange(status.id)}
//             className={status.id === currentStatus ? "bg-gray-100 text-gray-900" : ""}
//           >
//             {status.name}
//           </CustomDropdownMenuItem>
//         ))}
//       </CustomDropdownMenuContent>
//     </CustomDropdownMenu>
//   );
// };

// // --- Component chính ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   const [statusOptions, setStatusOptions] = useState([]);

//   // Hàm xử lý thay đổi bộ lọc
//   // const handleFilterChange = (key, value) => {
//   //   setFilters((prev) => ({ ...prev, [key]: value, page: 0 }));
//   // };

//   const handleFilterChange = (key, value) => {
//   setFilters((prev) => {
//     // Nếu giá trị không thay đổi, không cần set lại (tránh update vô nghĩa)
//     const isEqual = JSON.stringify(prev[key]) === JSON.stringify(value);
//     if (isEqual) return prev;

//     return { ...prev, [key]: value, page: 0 };
//   });
// };

//   // Hàm xử lý thay đổi trang
//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   // Hàm xử lý thay đổi trạng thái truyện trên giao diện
//   const handleStatusChange = (novelId, newStatusId) => {
//     setNovels((prevNovels) =>
//       prevNovels.map((novel) =>
//         novel.id === novelId
//           ? {
//               ...novel,
//               novelState: newStatusId,
//               novelStateLabel: statusOptions.find((status) => status.id === newStatusId)?.name || novel.novelStateLabel,
//             }
//           : novel
//       )
//     );
//   };

//   useEffect(() => {
//     // Lấy danh sách truyện
//     const fetchNovels = async () => {
//       try {
//         const response = await getFilteredNovels({ ...filters });
//         console.log("Fetched novels:", response);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách truyện:", err);
//       }
//     };

//     // Lấy danh sách thể loại
//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách thể loại:", err);
//       }
//     };

//     // Lấy danh sách trạng thái truyện
//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Fetched novel states:", response);
//         setStatusOptions(response?.result || []);
//       } catch (err) {
//         console.error("Lỗi khi lấy danh sách trạng thái truyện:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     // Xử lý sự kiện click ngoài để đóng popup
//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   // Hàm trả về class cho badge trạng thái
//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-gray-500";
//       case "Bị từ chối": return "bg-gray-100 text-gray-300";
//       case "Đã xóa": return "bg-gray-100 text-gray-100";
//       default: return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelState: [],
//                 page: 0,
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto min-h-[500px]">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}>
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelState', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelState', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[120px]">
//                   Hành động
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{novel.name}</span></td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelStateLabel)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <StatusChangeDropdown
//                         novelId={novel.id}
//                         currentStatus={novel.novelState}
//                         statusOptions={statusOptions}
//                         onStatusChange={(newStatusId) => handleStatusChange(novel.id, newStatusId)}
//                       />
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Trang trước
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Trang sau
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }









//--------------------------------------------------------

















// // "use client"

// import React, { useState, useEffect, useRef } from "react";
// import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react";
// import { getFilteredNovels, getAllNovelState } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // --- Các component tùy chỉnh ---
// const CustomCard = ({ children, className }) => (
//   <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardHeader = ({ children, className }) => (
//   <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>
// );
// const CustomCardTitle = ({ children, className }) => (
//   <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// );
// const CustomCardContent = ({ children, className }) => (
//   <div className={`p-0 ${className}`}>{children}</div>
// );
// const CustomInput = ({ type, placeholder, className, ...props }) => (
//   <input
//     type={type}
//     placeholder={placeholder}
//     className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//     {...props}
//   />
// );
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default": variantClasses = "bg-blue-600 text-white hover:bg-blue-700"; break;
//     case "outline": variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"; break;
//     case "ghost": variantClasses = "hover:bg-gray-100 hover:text-gray-900"; break;
//     case "icon": sizeClasses = "h-8 w-8"; break;
//     default: variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default": sizeClasses = "h-10 px-4 py-2"; break;
//     case "sm": sizeClasses = "h-9 px-3"; break;
//     case "lg": sizeClasses = "h-11 px-8"; break;
//     case "icon": sizeClasses = "h-10 w-10"; break;
//     default: sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };
// const CustomBadge = ({ children, className }) => (
//   <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//     {children}
//   </span>
// );
// const CustomDropdownMenu = ({ children }) => {
//   const [isOpen, setIsOpen] = useState(false);
//   const toggleOpen = () => setIsOpen(!isOpen);
//   let trigger = null, content = null;
//   React.Children.forEach(children, (child) => {
//     if (child.type === CustomDropdownMenuTrigger) {
//       trigger = React.cloneElement(child, { onClick: toggleOpen });
//     } else if (child.type === CustomDropdownMenuContent) {
//       content = React.cloneElement(child, { isOpen, setIsOpen });
//     }
//   });
//   return <div className="relative">{trigger}{content}</div>;
// };
// const CustomDropdownMenuTrigger = ({ children, onClick }) => <div onClick={onClick}>{children}</div>;
// const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
//   const alignClass = align === "end" ? "right-0" : "left-0";
//   return isOpen && (
//     <div className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`} onMouseLeave={() => setIsOpen(false)}>
//       {children}
//     </div>
//   );
// };
// const CustomDropdownMenuItem = ({ children, className, ...props }) => (
//   <div className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`} {...props}>
//     {children}
//   </div>
// );

// // --- Main Component ---
// export default function NovelManagePage() {
//   const user = useUser();
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });
//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();

//   const [statusOptions, setStatusOptions] = useState([]);
//   // const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];

//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => ({ ...prev, [key]: value, page: 0 }));
//   };

//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({ ...prev, page: newPage }));
//   };

//   useEffect(() => {
//     const fetchNovels = async () => {
//       try {
//         const response = await getFilteredNovels({ ...filters });
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Error fetching novels:", err);
//       }
//     };

//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Error fetching categories:", err);
//       }
//     };

//     const fetchNovelStates = async () => {
//       try {
//         const response = await getAllNovelState();
//         console.log("Novel states:", response);
//         setStatusOptions(response.data?.result || []);
//         // setFilters((prev) => ({ ...prev, novelState: response.data || [] }));
//       } catch (err) {
//         console.error("Error fetching novel states:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//       fetchNovelStates();
//     }

//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   const getStatusBadgeClass = (status) => {
//     switch (status.name) {
//       case "Khởi tạo": return "bg-green-100 text-green-700";
//       case "Chờ duyệt": return "bg-yellow-100 text-yellow-700";
//       case "Tạm dừng": return "bg-red-100 text-red-700";
//       case "Đang kiểm duyệt": return "bg-gray-100 text-gray-700";
//       case "Đã xuất bản": return "bg-gray-100 text-gray-500";
//       case "Bị từ chối": return "bg-gray-100 text-gray-300";
//       case "Đã xóa": return "bg-gray-100 text-gray-100";
//       default: return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="flex gap-2">
//           <div className="relative w-48">
//             <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//             <CustomInput
//               type="search"
//               placeholder="Tìm kiếm truyện..."
//               value={filters.searchText}
//               onChange={(e) => handleFilterChange('searchText', e.target.value)}
//               className="w-full rounded-md pl-8 text-white"
//             />
//           </div>
//           <CustomButton
//             variant="outline"
//             size="sm"
//             className="text-white"
//             onClick={() => {
//               setFilters((prev) => ({
//                 ...prev,
//                 searchText: '',
//                 genres: [],
//                 novelState: [],
//                 page: 0,
//               }));
//               setSelectedCategory(null);
//               setSelectedStatus(null);
//             }}
//           >
//             Bỏ lọc
//           </CustomButton>
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">Tên truyện</th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}>
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">Tác giả</th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowCategoryPopup(true)}>
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div ref={categoryPopupRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedCategory(null);
//                           handleFilterChange('genres', []);
//                           setShowCategoryPopup(false);
//                         }}>
//                         Tất cả thể loại
//                       </div>
//                       {categories.map((category) => (
//                         <div key={category.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}>
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none" onClick={() => setShowStatusDropdown(true)}>
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div ref={statusDropdownRef} className="absolute z-50 mt-2 w-44 border border-gray-300 rounded shadow bg-white">
//                       <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700 font-semibold"
//                         onClick={() => {
//                           setSelectedStatus(null);
//                           handleFilterChange('novelState', []);
//                           setShowStatusDropdown(false);
//                         }}>
//                         Tất cả trạng thái
//                       </div>
//                       {statusOptions.map((status) => (
//                         <div key={status.id} className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status.name);
//                             handleFilterChange('novelState', [status.id]);
//                             setShowStatusDropdown(false);
//                           }}>
//                           {status.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[40px]"><span className="sr-only">Actions</span></th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{novel.name}</span></td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(novel.createdAt).toLocaleDateString()}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel.author.name || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel.genres?.map(g => g.name).join(', ') || 'N/A'}</td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelState)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <CustomDropdownMenu>
//                         <CustomDropdownMenuTrigger>
//                           <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                             <MoreHorizontal className="h-4 w-4 text-white" />
//                           </CustomButton>
//                         </CustomDropdownMenuTrigger>
//                         <CustomDropdownMenuContent align="end">
//                           <CustomDropdownMenuItem>View novel</CustomDropdownMenuItem>
//                           <CustomDropdownMenuItem>Edit novel</CustomDropdownMenuItem>
//                           <CustomDropdownMenuItem>Delete novel</CustomDropdownMenuItem>
//                         </CustomDropdownMenuContent>
//                       </CustomDropdownMenu>
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Previous
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Next
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }


























// ----------------------------


// // "use client"

// import React, { useState, useEffect, useRef } from "react";
// import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react";
// import { getFilteredNovels } from '../../services/novelService.js';
// import { useUser } from '../../stores/userStores.js';
// import { getCategories } from '../../services/categoryService.js';

// // Các component tùy chỉnh giữ nguyên như code gốc
// const CustomCard = ({ children, className }) => {
//   return <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>;
// };

// const CustomCardHeader = ({ children, className }) => {
//   return <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>;
// };
// const CustomCardTitle = ({ children, className }) => {
//   return <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>;
// };
// const CustomCardContent = ({ children, className }) => {
//   return <div className={`p-0 ${className}`}>{children}</div>;
// };
// const CustomInput = ({ type, placeholder, className, ...props }) => {
//   return (
//     <input
//       type={type}
//       placeholder={placeholder}
//       className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//       {...props}
//     />
//   );
// };
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//   const baseClasses =
//     "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
//   let variantClasses = "";
//   let sizeClasses = "";

//   switch (variant) {
//     case "default":
//       variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//       break;
//     case "outline":
//       variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900";
//       break;
//     case "ghost":
//       variantClasses = "hover:bg-gray-100 hover:text-gray-900";
//       break;
//     case "icon":
//       sizeClasses = "h-8 w-8";
//       break;
//     default:
//       variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
//   }

//   switch (size) {
//     case "default":
//       sizeClasses = "h-10 px-4 py-2";
//       break;
//     case "sm":
//       sizeClasses = "h-9 px-3";
//       break;
//     case "lg":
//       sizeClasses = "h-11 px-8";
//       break;
//     case "icon":
//       sizeClasses = "h-10 w-10";
//       break;
//     default:
//       sizeClasses = "h-10 px-4 py-2";
//   }

//   return (
//     <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//       {children}
//     </button>
//   );
// };

// const CustomBadge = ({ children, className }) => {
//     return (
//         <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//             {children}
//         </span>
//     );
// };

// const CustomDropdownMenu = ({ children }) => {
//   const [isOpen, setIsOpen] = useState(false);
//   const toggleOpen = () => setIsOpen(!isOpen);

//   let trigger = null;
//   let content = null;

//   React.Children.forEach(children, (child) => {
//     if (child.type === CustomDropdownMenuTrigger) {
//       trigger = React.cloneElement(child, { onClick: toggleOpen });
//     } else if (child.type === CustomDropdownMenuContent) {
//       content = React.cloneElement(child, { isOpen, setIsOpen });
//     }
//   });

//   return (
//     <div className="relative">
//       {trigger}
//       {content}
//     </div>
//   );
// };

// const CustomDropdownMenuTrigger = ({ children, onClick }) => {
//   return <div onClick={onClick}>{children}</div>;
// };

// const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
//   const alignClass = align === "end" ? "right-0" : "left-0";
//   return (
//     isOpen && (
//       <div
//         className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`}
//         onMouseLeave={() => setIsOpen(false)}
//       >
//         {children}
//       </div>
//     )
//   );
// };

// const CustomDropdownMenuItem = ({ children, className, ...props }) => {
//   return (
//     <div
//       className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`}
//       {...props}
//     >
//       {children}
//     </div>
//   );
// };

// export default function NovelManagePage() {
//   const user = useUser();

//   // Khởi tạo filter state với các trường từ yêu cầu
//   const [filters, setFilters] = useState({
//     searchText: '',
//     sortBy: 'createdAt',
//     sortOrder: 'desc',
//     novelProgressStatus: [],
//     novelType: [],
//     novelState: [],
//     novelVisibility: [],
//     genres: [],
//     worldScene: [],
//     novelAttribute: [],
//     totalChapterOfNovelFrom: -1,
//     totalChapterOfNovelTo: -1,
//     ratingFrom: -1,
//     ratingTo: -1,
//     mainCharacterTrait: [],
//     authorName: '',
//     sects: [],
//     page: 0,
//     size: 10,
//   });

//   const [novels, setNovels] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [totalPages, setTotalPages] = useState(1);

//   // Popup và dropdown state
//   const categoryPopupRef = useRef();
//   const statusDropdownRef = useRef();
//   const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//   const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//   const [selectedCategory, setSelectedCategory] = useState(null);
//   const [selectedStatus, setSelectedStatus] = useState(null);

//   const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];

//   // Hàm xử lý thay đổi filter
//   const handleFilterChange = (key, value) => {
//     setFilters((prev) => ({
//       ...prev,
//       [key]: value,
//       page: 0, // Reset về trang đầu khi thay đổi filter
//     }));
//   };

//   // Hàm xử lý phân trang
//   const handlePageChange = (newPage) => {
//     setFilters((prev) => ({
//       ...prev,
//       page: newPage,
//     }));
//   };

//   // Fetch dữ liệu novels và categories
//   useEffect(() => {
//     const fetchNovels = async () => {
//       try {
//         // const response = await getFilteredNovels({
//         //   ...filters,
//         //   currentPublisher: user?.id || null,
//         // });
//         const response = await getFilteredNovels({
//           ...filters
//         });
//         console.log("Fetched novels:", response.data);
//         setNovels(response.data?.result?.data || []);
//         setTotalPages(response.data?.result?.totalPages || 1);
//       } catch (err) {
//         console.error("Error fetching novels:", err);
//       }
//     };

//     const fetchCategories = async () => {
//       try {
//         const response = await getCategories();
//         setCategories(response.data?.result || []);
//       } catch (err) {
//         console.error("Error fetching categories:", err);
//       }
//     };

//     if (user?.id) {
//       fetchNovels();
//       fetchCategories();
//     }

//     const handleClickOutside = (event) => {
//       if (categoryPopupRef.current && !categoryPopupRef.current.contains(event.target)) {
//         setShowCategoryPopup(false);
//       }
//       if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//         setShowStatusDropdown(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => document.removeEventListener('mousedown', handleClickOutside);
//   }, [filters, user?.id]);

//   const getStatusBadgeClass = (status) => {
//     switch (status) {
//       case "Đã xuất bản":
//         return "bg-green-100 text-green-700";
//       case "Đang chờ duyệt":
//         return "bg-yellow-100 text-yellow-700";
//       case "Bị từ chối":
//         return "bg-red-100 text-red-700";
//       case "Bản nháp":
//         return "bg-gray-100 text-gray-700";
//       default:
//         return "bg-gray-100 text-gray-700";
//     }
//   };

//   return (
//     <CustomCard className="w-full max-w-4xl mx-auto">
//       <CustomCardHeader>
//         <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//         <div className="relative w-48">
//           <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//           <CustomInput
//             type="search"
//             placeholder="Tìm kiếm truyện..."
//             value={filters.searchText}
//             onChange={(e) => handleFilterChange('searchText', e.target.value)}
//             className="w-full rounded-md pl-8 text-white"
//           />
//         </div>
//       </CustomCardHeader>
//       <CustomCardContent>
//         <div className="overflow-x-auto">
//           <table className="min-w-full divide-y divide-gray-200">
//             <thead>
//               <tr>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">
//                   Tên truyện
//                 </th>
//                 <th
//                   className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                   onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}
//                 >
//                   Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
//                 </th>
//                 <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
//                   Tác giả
//                 </th>
//                 <th
//                   className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                   onClick={() => setShowCategoryPopup(true)}
//                 >
//                   Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
//                   {showCategoryPopup && (
//                     <div
//                       ref={categoryPopupRef}
//                       className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
//                     >
//                       {categories.map((category) => (
//                         <div
//                           key={category.id}
//                           className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedCategory(category.name);
//                             handleFilterChange('genres', [category.id]);
//                             setShowCategoryPopup(false);
//                           }}
//                         >
//                           {category.name}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th
//                   className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                   onClick={() => setShowStatusDropdown(true)}
//                 >
//                   Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
//                   {showStatusDropdown && (
//                     <div
//                       ref={statusDropdownRef}
//                       className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
//                     >
//                       {statusOptions.map((status) => (
//                         <div
//                           key={status}
//                           className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                           onClick={() => {
//                             setSelectedStatus(status);
//                             handleFilterChange('novelState', [status]);
//                             setShowStatusDropdown(false);
//                           }}
//                         >
//                           {status}
//                         </div>
//                       ))}
//                     </div>
//                   )}
//                 </th>
//                 <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[40px]">
//                   <span className="sr-only">Actions</span>
//                 </th>
//               </tr>
//             </thead>
//             <tbody className="divide-y divide-gray-200">
//               {Array.isArray(novels) && novels.length > 0 ? (
//                 novels.map((novel) => (
//                   <tr key={novel.id}>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <div className="flex items-center gap-3">
//                         <span className="font-medium text-sm text-white">{novel.name}</span>
//                       </div>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">
//                       {new Date(novel.createdAt).toLocaleDateString()}
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">
//                       {novel.author.name || 'N/A'}
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">
//                       {/* {novel.genres?.join(', ') || 'N/A'} */}
//                       {novel.genres?.map(g => g.name).join(', ') || 'N/A'}
//                       {/* {novel.genres?.[0]?.name || 'N/A'} */}

//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap">
//                       <CustomBadge className={getStatusBadgeClass(novel.novelState)}>
//                         {novel.novelStateLabel || 'N/A'}
//                       </CustomBadge>
//                     </td>
//                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                       <CustomDropdownMenu>
//                         <CustomDropdownMenuTrigger>
//                           <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                             <MoreHorizontal className="h-4 w-4 text-white" />
//                             <span className="sr-only">Actions</span>
//                           </CustomButton>
//                         </CustomDropdownMenuTrigger>
//                         <CustomDropdownMenuContent align="end">
//                           <CustomDropdownMenuItem>View novel</CustomDropdownMenuItem>
//                           <CustomDropdownMenuItem>Edit novel</CustomDropdownMenuItem>
//                           <CustomDropdownMenuItem>Delete novel</CustomDropdownMenuItem>
//                         </CustomDropdownMenuContent>
//                       </CustomDropdownMenu>
//                     </td>
//                   </tr>
//                 ))
//               ) : (
//                 <tr>
//                   <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                     Không tìm thấy truyện nào.
//                   </td>
//                 </tr>
//               )}
//             </tbody>
//           </table>
//         </div>
//       </CustomCardContent>
//       <div className="flex items-center justify-between p-4 border-t border-gray-200">
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page === 0}
//           onClick={() => handlePageChange(filters.page - 1)}
//         >
//           <ArrowLeft className="h-4 w-4" />
//           Previous
//         </CustomButton>
//         <div className="flex items-center gap-2">
//           {Array.from({ length: totalPages }, (_, i) => (
//             <CustomButton
//               key={i}
//               variant={filters.page === i ? "default" : "ghost"}
//               className="h-8 w-8 p-0 text-sm"
//               onClick={() => handlePageChange(i)}
//             >
//               {i + 1}
//             </CustomButton>
//           ))}
//         </div>
//         <CustomButton
//           variant="outline"
//           className="flex items-center gap-2 text-sm bg-transparent"
//           disabled={filters.page >= totalPages - 1}
//           onClick={() => handlePageChange(filters.page + 1)}
//         >
//           Next
//           <ArrowRight className="h-4 w-4" />
//         </CustomButton>
//       </div>
//     </CustomCard>
//   );
// }

