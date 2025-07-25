// "use client"

import React, { useState, useEffect, useRef } from "react";
import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react";
import { getFilteredNovels } from '../../services/novelService.js';
import { useUser } from '../../stores/userStores.js';
import { getCategories } from '../../services/categoryService.js';

// Các component tùy chỉnh giữ nguyên như code gốc
const CustomCard = ({ children, className }) => {
  return <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>;
};

const CustomCardHeader = ({ children, className }) => {
  return <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>{children}</div>;
};
const CustomCardTitle = ({ children, className }) => {
  return <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>;
};
const CustomCardContent = ({ children, className }) => {
  return <div className={`p-0 ${className}`}>{children}</div>;
};
const CustomInput = ({ type, placeholder, className, ...props }) => {
  return (
    <input
      type={type}
      placeholder={placeholder}
      className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
      {...props}
    />
  );
};
const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
  const baseClasses =
    "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50";
  let variantClasses = "";
  let sizeClasses = "";

  switch (variant) {
    case "default":
      variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
      break;
    case "outline":
      variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900";
      break;
    case "ghost":
      variantClasses = "hover:bg-gray-100 hover:text-gray-900";
      break;
    case "icon":
      sizeClasses = "h-8 w-8";
      break;
    default:
      variantClasses = "bg-blue-600 text-white hover:bg-blue-700";
  }

  switch (size) {
    case "default":
      sizeClasses = "h-10 px-4 py-2";
      break;
    case "sm":
      sizeClasses = "h-9 px-3";
      break;
    case "lg":
      sizeClasses = "h-11 px-8";
      break;
    case "icon":
      sizeClasses = "h-10 w-10";
      break;
    default:
      sizeClasses = "h-10 px-4 py-2";
  }

  return (
    <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
      {children}
    </button>
  );
};

const CustomBadge = ({ children, className }) => {
    return (
        <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
            {children}
        </span>
    );
};

const CustomDropdownMenu = ({ children }) => {
  const [isOpen, setIsOpen] = useState(false);
  const toggleOpen = () => setIsOpen(!isOpen);

  let trigger = null;
  let content = null;

  React.Children.forEach(children, (child) => {
    if (child.type === CustomDropdownMenuTrigger) {
      trigger = React.cloneElement(child, { onClick: toggleOpen });
    } else if (child.type === CustomDropdownMenuContent) {
      content = React.cloneElement(child, { isOpen, setIsOpen });
    }
  });

  return (
    <div className="relative">
      {trigger}
      {content}
    </div>
  );
};

const CustomDropdownMenuTrigger = ({ children, onClick }) => {
  return <div onClick={onClick}>{children}</div>;
};

const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
  const alignClass = align === "end" ? "right-0" : "left-0";
  return (
    isOpen && (
      <div
        className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`}
        onMouseLeave={() => setIsOpen(false)}
      >
        {children}
      </div>
    )
  );
};

const CustomDropdownMenuItem = ({ children, className, ...props }) => {
  return (
    <div
      className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`}
      {...props}
    >
      {children}
    </div>
  );
};

export default function NovelManagePage() {
  const user = useUser();

  // Khởi tạo filter state với các trường từ yêu cầu
  const [filters, setFilters] = useState({
    searchText: '',
    sortBy: 'createdAt',
    sortOrder: 'desc',
    novelProgressStatus: [],
    novelType: [],
    novelState: [],
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
    page: 0,
    size: 10,
  });

  const [novels, setNovels] = useState([]);
  const [categories, setCategories] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  // Popup và dropdown state
  const categoryPopupRef = useRef();
  const statusDropdownRef = useRef();
  const [showCategoryPopup, setShowCategoryPopup] = useState(false);
  const [showStatusDropdown, setShowStatusDropdown] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState(null);

  const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];

  // Hàm xử lý thay đổi filter
  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({
      ...prev,
      [key]: value,
      page: 0, // Reset về trang đầu khi thay đổi filter
    }));
  };

  // Hàm xử lý phân trang
  const handlePageChange = (newPage) => {
    setFilters((prev) => ({
      ...prev,
      page: newPage,
    }));
  };

  // Fetch dữ liệu novels và categories
  useEffect(() => {
    const fetchNovels = async () => {
      try {
        // const response = await getFilteredNovels({
        //   ...filters,
        //   currentPublisher: user?.id || null,
        // });
        const response = await getFilteredNovels({
          ...filters
        });
        console.log("Fetched novels:", response.data);
        setNovels(response.data?.result?.data || []);
        setTotalPages(response.data?.result?.totalPages || 1);
      } catch (err) {
        console.error("Error fetching novels:", err);
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await getCategories();
        setCategories(response.data?.result || []);
      } catch (err) {
        console.error("Error fetching categories:", err);
      }
    };

    if (user?.id) {
      fetchNovels();
      fetchCategories();
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
      case "Đã xuất bản":
        return "bg-green-100 text-green-700";
      case "Đang chờ duyệt":
        return "bg-yellow-100 text-yellow-700";
      case "Bị từ chối":
        return "bg-red-100 text-red-700";
      case "Bản nháp":
        return "bg-gray-100 text-gray-700";
      default:
        return "bg-gray-100 text-gray-700";
    }
  };

  return (
    <CustomCard className="w-full max-w-4xl mx-auto">
      <CustomCardHeader>
        <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
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
      </CustomCardHeader>
      <CustomCardContent>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead>
              <tr>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]">
                  Tên truyện
                </th>
                <th
                  className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                  onClick={() => handleFilterChange('sortOrder', filters.sortOrder === 'asc' ? 'desc' : 'asc')}
                >
                  Ngày đăng {filters.sortOrder === 'asc' ? ' 🔼' : ' 🔽'}
                </th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                  Tác giả
                </th>
                <th
                  className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                  onClick={() => setShowCategoryPopup(true)}
                >
                  Thể loại {selectedCategory ? `: ${selectedCategory}` : ''}
                  {showCategoryPopup && (
                    <div
                      ref={categoryPopupRef}
                      className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
                    >
                      {categories.map((category) => (
                        <div
                          key={category.id}
                          className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
                          onClick={() => {
                            setSelectedCategory(category.name);
                            handleFilterChange('genres', [category.id]);
                            setShowCategoryPopup(false);
                          }}
                        >
                          {category.name}
                        </div>
                      ))}
                    </div>
                  )}
                </th>
                <th
                  className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                  onClick={() => setShowStatusDropdown(true)}
                >
                  Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
                  {showStatusDropdown && (
                    <div
                      ref={statusDropdownRef}
                      className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
                    >
                      {statusOptions.map((status) => (
                        <div
                          key={status}
                          className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
                          onClick={() => {
                            setSelectedStatus(status);
                            handleFilterChange('novelState', [status]);
                            setShowStatusDropdown(false);
                          }}
                        >
                          {status}
                        </div>
                      ))}
                    </div>
                  )}
                </th>
                <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[40px]">
                  <span className="sr-only">Actions</span>
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {Array.isArray(novels) && novels.length > 0 ? (
                novels.map((novel) => (
                  <tr key={novel.id}>
                    <td className="px-4 py-3 whitespace-nowrap">
                      <div className="flex items-center gap-3">
                        <span className="font-medium text-sm text-white">{novel.name}</span>
                      </div>
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white">
                      {new Date(novel.createdAt).toLocaleDateString()}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">
                      {novel.authorName || 'N/A'}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-white">
                      {novel.genres?.join(', ') || 'N/A'}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap">
                      <CustomBadge className={getStatusBadgeClass(novel.novelState)}>
                        {novel.novelState || 'N/A'}
                      </CustomBadge>
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-right">
                      <CustomDropdownMenu>
                        <CustomDropdownMenuTrigger>
                          <CustomButton variant="ghost" size="icon" className="h-8 w-8">
                            <MoreHorizontal className="h-4 w-4 text-white" />
                            <span className="sr-only">Actions</span>
                          </CustomButton>
                        </CustomDropdownMenuTrigger>
                        <CustomDropdownMenuContent align="end">
                          <CustomDropdownMenuItem>View novel</CustomDropdownMenuItem>
                          <CustomDropdownMenuItem>Edit novel</CustomDropdownMenuItem>
                          <CustomDropdownMenuItem>Delete novel</CustomDropdownMenuItem>
                        </CustomDropdownMenuContent>
                      </CustomDropdownMenu>
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
          disabled={filters.page === 0}
          onClick={() => handlePageChange(filters.page - 1)}
        >
          <ArrowLeft className="h-4 w-4" />
          Previous
        </CustomButton>
        <div className="flex items-center gap-2">
          {Array.from({ length: totalPages }, (_, i) => (
            <CustomButton
              key={i}
              variant={filters.page === i ? "default" : "ghost"}
              className="h-8 w-8 p-0 text-sm"
              onClick={() => handlePageChange(i)}
            >
              {i + 1}
            </CustomButton>
          ))}
        </div>
        <CustomButton
          variant="outline"
          className="flex items-center gap-2 text-sm bg-transparent"
          disabled={filters.page >= totalPages - 1}
          onClick={() => handlePageChange(filters.page + 1)}
        >
          Next
          <ArrowRight className="h-4 w-4" />
        </CustomButton>
      </div>
    </CustomCard>
  );
}



// // "use client"

// import React, { useState, useEffect, useRef } from "react"
// import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react" // Vẫn sử dụng lucide-react cho các icon
// import { getFilteredNovels, getNovels } from '../../services/novelService.js';
// import { useCurrentNovelPublisher, useUser } from '../../stores/userStores.js';
// import { useAdminStore, useAdminActions } from '../../stores/adminStore.js';
// import { useSetListNovel } from '../../stores/novelStore.js';
// import { useFilterManageStore } from '../../stores/novelFilterManageStores.js';


// // Component Card tùy chỉnh
// const CustomCard = ({ children, className }) => {
//     // return <div className={`bg-white rounded-lg shadow-sm border border-gray-200 ${className}`}>{children}</div>
//     return <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// }

// // Component CardHeader tùy chỉnhy
// const CustomCardHeader = ({ children, className }) => {
//     return (
//         <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>
//             {children}
//         </div>
//     )
// }

// // Component CardTitle tùy chỉnh
// const CustomCardTitle = ({ children, className }) => {
//     return <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
// }

// // Component CardContent tùy chỉnh
// const CustomCardContent = ({ children, className }) => {
//     return <div className={`p-0 ${className}`}>{children}</div>
// }

// // Component Input tùy chỉnh
// const CustomInput = ({ type, placeholder, className, ...props }) => {
//     return (
//         <input
//             type={type}
//             placeholder={placeholder}
//             className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
//             {...props}
//         />
//     )
// }

// // Component Avatar tùy chỉnh
// const CustomAvatar = ({ src, alt, fallback, className }) => {
//     return (
//         <div className={`relative flex h-8 w-8 shrink-0 overflow-hidden rounded-full ${className}`}>
//             {src ? (
//                 <img className="aspect-square h-full w-full" alt={alt} src={src || "/placeholder.svg"} />
//             ) : (
//                 <div className="flex h-full w-full items-center justify-center rounded-full bg-gray-200 text-gray-600 text-xs font-medium">
//                     {fallback}
//                 </div>
//             )}
//         </div>
//     )
// }

// // Component Badge tùy chỉnh
// const CustomBadge = ({ children, className }) => {
//     return (
//         <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
//             {children}
//         </span>
//     )
// }

// // Component Button tùy chỉnh
// const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
//     const baseClasses =
//         "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50"
//     let variantClasses = ""
//     let sizeClasses = ""

//     switch (variant) {
//         case "default":
//             variantClasses = "bg-blue-600 text-black hover:bg-blue-700"
//             break
//         case "outline":
//             variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"
//             break
//         case "ghost":
//             variantClasses = "hover:bg-gray-100 hover:text-gray-900"
//             break
//         case "icon":
//             sizeClasses = "h-8 w-8"
//             break
//         default:
//             variantClasses = "bg-blue-600 text-white hover:bg-blue-700"
//     }

//     switch (size) {
//         case "default":
//             sizeClasses = "h-10 px-4 py-2"
//             break
//         case "sm":
//             sizeClasses = "h-9 px-3"
//             break
//         case "lg":
//             sizeClasses = "h-11 px-8"
//             break
//         case "icon":
//             sizeClasses = "h-10 w-10"
//             break
//         default:
//             sizeClasses = "h-10 px-4 py-2"
//     }

//     return (
//         <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
//             {children}
//         </button>
//     )
// }

// // Component DropdownMenu tùy chỉnh (đơn giản hóa)
// const CustomDropdownMenu = ({ children }) => {
//     const [isOpen, setIsOpen] = useState(false)

//     const toggleOpen = () => setIsOpen(!isOpen)

//     // Tìm DropdownMenuTrigger và DropdownMenuContent trong children
//     let trigger = null
//     let content = null

//     React.Children.forEach(children, (child) => {
//         if (child.type === CustomDropdownMenuTrigger) {
//             trigger = React.cloneElement(child, { onClick: toggleOpen })
//         } else if (child.type === CustomDropdownMenuContent) {
//             content = React.cloneElement(child, { isOpen, setIsOpen })
//         }
//     })

//     return (
//         <div className="relative">
//             {trigger}
//             {content}
//         </div>
//     )
// }

// const CustomDropdownMenuTrigger = ({ children, onClick }) => {
//     return <div onClick={onClick}>{children}</div>
// }

// const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
//     const alignClass = align === "end" ? "right-0" : "left-0"
//     return (
//         isOpen && (
//             <div
//                 className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`}
//                 onMouseLeave={() => setIsOpen(false)} // Đóng khi chuột rời khỏi menu
//             >
//                 {children}
//             </div>
//         )
//     )
// }

// const CustomDropdownMenuItem = ({ children, className, ...props }) => {
//     return (
//         <div
//             className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`}
//             {...props}
//         >
//             {children}
//         </div>
//     )
// }

// export default function NovelManagePage() {

//     const user = useUser();

//     const filters = useFilterManageStore();

//     // const [novels, useSetListNovel] = useState([]);
//     const [novels, setNovels] = useState([]); 
//     const [categories, setCategories] = useState([]); 

//     const [filters, setFilters] = useState({
//         search: '',
//         category: null,
//         status: null,
//         sortDirection: 'desc',
//         currentPublisher: "",
//         // currentPublisher: user?.id || null,
//     });

//     const [sortDirection, setSortDirection] = useState('desc');


//     const handleSortByDate = () => {
//         setFilters(prev => ({
//             ...prev,
//             sortDirection: prev.sortDirection === 'asc' ? 'desc' : 'asc'
//         }));
//     };

//     const handleSearchChange = (e) => {
//         setFilters(prev => ({ ...prev, search: e.target.value }));
//     };

//     // category popup state
//     const popupRef = useRef();
//     const [showCategoryPopup, setShowCategoryPopup] = useState(false);
//     const [selectedCategory, setSelectedCategory] = useState(null);

//     const categoryOptions = ['Tiểu thuyết', 'Truyện tranh', 'Kinh dị', 'Hài hước'];

//     // status dropdown state
//     const statusDropdownRef = useRef();
//     const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//     const [selectedStatus, setSelectedStatus] = useState(null);

//     const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];



//     useEffect(() => {
//         // if (!user?.id) return;

//         // if (!filters.currentPublisher) return;

//         const fetchNovels = async () => {
//             try {
//                 // const filterss = {}
//                 const response = await getFilteredNovels(filters);
//                 // const response = await getNovels();
//                 console.log(response.data); // 👈 kiểm tra xem có `result` không
//                 // setNovels(response.data.result);
//                 console.log(response.data?.result?.data);
//                 setNovels(response.data?.result?.data || []);

//             } catch (err) {
//                 console.error(err); 
//             }
//         };

//         const fetchCategories = async () => {
//             try {
//                 const response = await getCategories();
//                 console.log(response.data);
//                 // useSetCategories(response.data.result);
//                 // setCategories(response.data.result);
//                 setCategories(response.data?.result || []);
//             } catch (err) {
//                 console.error(err);
//             }
//         };

//         fetchNovels();
//         fetchCategories();

//         const handleClickOutside = (event) => {
//             if (popupRef.current && !popupRef.current.contains(event.target)) {
//                 setShowCategoryPopup(false);
//             }
//             if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
//                 setShowStatusDropdown(false);
//             }
//         };

//         document.addEventListener('mousedown', handleClickOutside);
//         return () => {
//             document.removeEventListener('mousedown', handleClickOutside);
//         };


//     }, [filters]);





//     const transactions = [
//         {
//             id: "1",
//             name: "Võ Tòng",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for PayPal logo
//             date: "Nov 23, 01:00 PM",
//             price: "Kim Dung",
//             category: "Cổ trang",
//             status: "Success",
//         },
//         {
//             id: "2",
//             name: "Tống Giang",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Apple logo
//             date: "Nov 23, 01:00 PM",
//             price: "Kim Dung",
//             category: "Cổ trang",
//             status: "Pending",
//         },
//         {
//             id: "3",
//             name: "Thủy hử",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for KKR logo
//             date: "Nov 23, 01:00 PM",
//             price: "Lưu Tư",
//             category: "Xuyên không",
//             status: "Success",
//         },
//         {
//             id: "4",
//             name: "Tôn Ngộ Không",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Facebook logo
//             date: "Nov 23, 01:00 PM",
//             price: "Hồng Bích",
//             category: "Phá đảo",
//             status: "Success",
//         },
//         {
//             id: "5",
//             name: "Hồng Lâu Mộng",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Amazon logo
//             date: "Nov 23, 01:00 PM",
//             price: "Kim Dung",
//             category: "Cổ trang",
//             status: "Failed",
//         },
//     ]

//     const getStatusBadgeClass = (status) => {
//         switch (status) {
//             case "Success":
//                 return "bg-green-100 text-green-700"
//             case "Pending":
//                 return "bg-yellow-100 text-yellow-700"
//             case "Failed":
//                 return "bg-red-100 text-red-700"
//             default:
//                 return ""
//         }
//     }

//     return (
//         <CustomCard className="w-full max-w-4xl mx-auto">
//             <CustomCardHeader>
//                 <CustomCardTitle>Quản lý Truyện</CustomCardTitle>
//                 <div className="relative w-48">
//                     <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
//                     <CustomInput type="search" placeholder="Search..." className="w-full rounded-md pl-8 text-white" />
//                 </div>
//             </CustomCardHeader>
//             <CustomCardContent>
//                 <div className="overflow-x-auto">
//                     {" "}
//                     {/* Đảm bảo bảng responsive */}
//                     <table className="min-w-full divide-y divide-gray-200">
//                         <thead className="">
//                             <tr>
//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]"
//                                 >
//                                     Tên truyện
//                                 </th>
//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                                     onClick={handleSortByDate}
//                                 >
//                                     Ngày đăng
//                                     {sortDirection === 'asc' ? ' 🔼' : ' 🔽'}
//                                 </th>
//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"
//                                 >
//                                     Tác giả
//                                 </th>
//                                 {/* <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
//                                 >
//                                     Category
//                                 </th> */}
//                                 <th
//                                     scope="col"
//                                     className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                                     onClick={() => setShowCategoryPopup(true)}
//                                 >
//                                     Category {selectedCategory ? `: ${selectedCategory}` : ''}

//                                     {showCategoryPopup && (
//                                         <div
//                                             ref={popupRef}
//                                             className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
//                                         >
//                                             {categories.map((category) => (
//                                                 <div
//                                                     key={category.id || category}
//                                                     className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                                                     // onClick={() => {
//                                                     //     setSelectedCategory(category);
//                                                     //     setShowCategoryPopup(false);
//                                                     // }}
//                                                     onClick={() => {
//                                                         setSelectedCategory(category.name);
//                                                         setFilters(prev => ({ ...prev, category }));
//                                                         setShowCategoryPopup(false);
//                                                     }}
//                                                 >
//                                                     {category.name}
//                                                 </div>
//                                             ))}
//                                         </div>
//                                     )}
//                                 </th>

//                                 {/* <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
//                                 >
//                                     Status
//                                 </th> */}
//                                 <th
//                                     scope="col"
//                                     className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                                     onClick={() => setShowStatusDropdown(true)}
//                                 >
//                                     Status {selectedStatus ? `: ${selectedStatus}` : ''}

//                                     {showStatusDropdown && (
//                                         <div
//                                             ref={statusDropdownRef}
//                                             className="absolute z-50 mt-2 w-40 border border-gray-300 rounded shadow bg-white"
//                                         >
//                                             {statusOptions.map((status) => (
//                                                 <div
//                                                     key={status}
//                                                     className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
//                                                     // onClick={() => {
//                                                     //     setSelectedStatus(status);
//                                                     //     setShowStatusDropdown(false);
//                                                     // }}
//                                                     onClick={() => {
//                                                         setSelectedStatus(status);
//                                                         setFilters(prev => ({ ...prev, status }));
//                                                         setShowStatusDropdown(false);
//                                                     }}
//                                                 >
//                                                     {status}
//                                                 </div>
//                                             ))}
//                                         </div>
//                                     )}
//                                 </th>

//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[40px]"
//                                 >
//                                     <span className="sr-only">Actions</span>
//                                 </th>
//                             </tr>
//                         </thead>
//                         <tbody className=" divide-y divide-gray-200">
//                             {/* {novels.map((novel) => ( */}
//                             {Array.isArray(novels) && novels.map((novel) => (
//                                 <tr key={novel.id}>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <div className="flex items-center gap-3">
   
//                                             <span className="font-medium text-sm text-white">{novel?.name}</span>
//                                         </div>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel?.createdAt}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{novel?.updatedAt}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{novel?.description}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <CustomBadge className={getStatusBadgeClass(novel?.displayName)}>{novel?.displayName}</CustomBadge>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-right"> 
//                                         <CustomDropdownMenu>
//                                             <CustomDropdownMenuTrigger>
//                                                 <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                                                     <MoreHorizontal className="h-4 w-4 text-white" />
//                                                     <span className="sr-only">Actions</span>
//                                                 </CustomButton>
//                                             </CustomDropdownMenuTrigger>
//                                             <CustomDropdownMenuContent align="end">
//                                                 <CustomDropdownMenuItem>View transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Edit transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Delete transaction</CustomDropdownMenuItem>
//                                             </CustomDropdownMenuContent>
//                                         </CustomDropdownMenu>
//                                     </td>
//                                 </tr>
//                             ))}
//                         </tbody>
//                         {/* <tbody className=" divide-y divide-gray-200">
//                             {transactions.map((transaction) => (
//                                 <tr key={transaction.id}>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <div className="flex items-center gap-3">
   
//                                             <span className="font-medium text-sm text-white">{transaction.name}</span>
//                                         </div>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{transaction.date}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{transaction.price}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{transaction.category}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <CustomBadge className={getStatusBadgeClass(transaction.status)}>{transaction.status}</CustomBadge>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-right"> 
//                                         <CustomDropdownMenu>
//                                             <CustomDropdownMenuTrigger>
//                                                 <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                                                     <MoreHorizontal className="h-4 w-4 text-white" />
//                                                     <span className="sr-only">Actions</span>
//                                                 </CustomButton>
//                                             </CustomDropdownMenuTrigger>
//                                             <CustomDropdownMenuContent align="end">
//                                                 <CustomDropdownMenuItem>View transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Edit transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Delete transaction</CustomDropdownMenuItem>
//                                             </CustomDropdownMenuContent>
//                                         </CustomDropdownMenu>
//                                     </td>
//                                 </tr>
//                             ))}
//                         </tbody> */}
//                         {/* <CustomAvatar
//                                                 src={transaction.logo}
//                                                 alt={transaction.name}
//                                                 fallback={transaction.name.charAt(0)}
//                                                 className="h-6 w-6"
//                                             /> */}
//                         {/* <tbody className=" divide-y divide-gray-200">
//                             {novels.map((novel) => (
//                                 <tr key={novel.id}>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <div className="flex items-center gap-3">
                                            
//                                             <span className="font-medium text-sm text-gray-900">{novel.name}</span>
//                                         </div>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-600">{novel.date}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-800 font-medium">{novel.price}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-600">{novel.category}</td>
//                                     <td className="px-4 py-3 whitespace-nowrap">
//                                         <CustomBadge className={getStatusBadgeClass(novel.status)}>{novel.status}</CustomBadge>
//                                     </td>
//                                     <td className="px-4 py-3 whitespace-nowrap text-right">
//                                         <CustomDropdownMenu>
//                                             <CustomDropdownMenuTrigger>
//                                                 <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                                                     <MoreHorizontal className="h-4 w-4 text-gray-500" />
//                                                     <span className="sr-only">Actions</span>
//                                                 </CustomButton>
//                                             </CustomDropdownMenuTrigger>
//                                             <CustomDropdownMenuContent align="end">
//                                                 <CustomDropdownMenuItem>View transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Edit transaction</CustomDropdownMenuItem>
//                                                 <CustomDropdownMenuItem>Delete transaction</CustomDropdownMenuItem>
//                                             </CustomDropdownMenuContent>
//                                         </CustomDropdownMenu>
//                                     </td>
//                                 </tr>
//                             ))}
//                         </tbody> */}
//                     </table>
//                 </div>
//             </CustomCardContent>
//             <div className="flex items-center justify-between p-4 border-t border-gray-200">
//                 <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent">
//                     <ArrowLeft className="h-4 w-4" />
//                     Previous
//                 </CustomButton>
//                 <div className="flex items-center gap-2">
//                     <CustomButton variant="default" className="h-8 w-8 p-0 text-sm">
//                         1
//                     </CustomButton>
//                     <CustomButton variant="ghost" className="h-8 w-8 p-0 text-sm">
//                         2
//                     </CustomButton>
//                     <CustomButton variant="ghost" className="h-8 w-8 p-0 text-sm">
//                         3
//                     </CustomButton>
//                 </div>
//                 <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent">
//                     Next
//                     <ArrowRight className="h-4 w-4" />
//                 </CustomButton>
//             </div>
//         </CustomCard>
//     )
// }



// // import React from "react";

// // function NovelManagePage() {
// //   const data = [
// //     { id: 1, user: "Abram Schliefer", position: "Sales Assistant", office: "Edinburgh", age: 57, startDate: "25 Apr, 2027", salary: "$89,500" },
// //     { id: 2, user: "Charlotte Anderson", position: "Marketing Manager", office: "London", age: 42, startDate: "12 Mar, 2025", salary: "$105,000" },
// //     { id: 3, user: "Ethan Brown", position: "Software Engineer", office: "San Francisco", age: 30, startDate: "01 Jan, 2024", salary: "$120,000" },
// //     { id: 4, user: "Isabella Davis", position: "UI/UX Designer", office: "Austin", age: 29, startDate: "18 Jul, 2025", salary: "$92,000" },
// //     { id: 5, user: "James Wilson", position: "Data Analyst", office: "Chicago", age: 28, startDate: "20 Sep, 2025", salary: "$80,000" },
// //   ];

// //   return (
// //     <div className="p-4 bg-gray-50 rounded-lg shadow">
// //       <div className="flex justify-between items-center mb-4">
// //         <h2 className="text-lg font-semibold text-gray-800">Data Table 1</h2>
// //         <div className="flex space-x-4">
// //           <div className="flex items-center">
// //             <label className="mr-2 text-gray-600">Show</label>
// //             <select className="p-1 border rounded">
// //               <option>10</option>
// //               <option>25</option>
// //               <option>50</option>
// //               <option>100</option>
// //             </select>
// //             <span className="ml-2 text-gray-600">entries</span>
// //           </div>
// //           <div>
// //             <input
// //               type="text"
// //               placeholder="Search..."
// //               className="p-1 border rounded"
// //             />
// //           </div>
// //         </div>
// //       </div>
// //       <table className="w-full bg-white border-collapse">
// //         <thead>
// //           <tr className="bg-gray-100">
// //             <th className="p-2 text-left text-gray-600 border-b">User</th>
// //             <th className="p-2 text-left text-gray-600 border-b">Position</th>
// //             <th className="p-2 text-left text-gray-600 border-b">Office</th>
// //             <th className="p-2 text-left text-gray-600 border-b">Age</th>
// //             <th className="p-2 text-left text-gray-600 border-b">Start Date</th>
// //             <th className="p-2 text-left text-gray-600 border-b">Salary</th>
// //           </tr>
// //         </thead>
// //         <tbody>
// //           {data.map((item) => (
// //             <tr key={item.id} className="hover:bg-gray-50">
// //               <td className="p-2 border-b flex items-center">
// //                 <img
// //                   src={`https://via.placeholder.com/40`}
// //                   alt={item.user}
// //                   className="w-8 h-8 rounded-full mr-2"
// //                 />
// //                 {item.user}
// //               </td>
// //               <td className="p-2 border-b">{item.position}</td>
// //               <td className="p-2 border-b">{item.office}</td>
// //               <td className="p-2 border-b">{item.age}</td>
// //               <td className="p-2 border-b">{item.startDate}</td>
// //               <td className="p-2 border-b">{item.salary}</td>
// //             </tr>
// //           ))}
// //         </tbody>
// //       </table>
// //     </div>
// //   );
// // }

// // export default NovelManagePage;