
import React, { useState, useEffect, useRef } from "react"
import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react"
import { getFilteredNovels, getNovels } from '../../services/novelService.js';
import { useCurrentNovelPublisher, useUser } from '../../stores/userStores.js';
import { useAdminStore, useAdminActions } from '../../stores/adminStore.js';
import { useSetListNovel } from '../../stores/novelStore.js';
import { getAllUsers, updateUserRole } from '../../services/userService.js';

// Component Card tùy chỉnh
const CustomCard = ({ children, className }) => {
    return <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
}

// Component CardHeader tùy chỉnh
const CustomCardHeader = ({ children, className }) => {
    return (
        <div className={`flex flex-row items-center justify-between p-4 border-b border-gray-200 ${className}`}>
            {children}
        </div>
    )
}

// Component CardTitle tùy chỉnh
const CustomCardTitle = ({ children, className }) => {
    return <h2 className={`text-lg font-semibold text-white ${className}`}>{children}</h2>
}

// Component CardContent tùy chỉnh
const CustomCardContent = ({ children, className }) => {
    return <div className={`p-0 ${className}`}>{children}</div>
}

// Component Input tùy chỉnh
const CustomInput = ({ type, placeholder, className, ...props }) => {
    return (
        <input
            type={type}
            placeholder={placeholder}
            className={`w-full rounded-md border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
            {...props}
        />
    )
}

// Component Avatar tùy chỉnh
const CustomAvatar = ({ src, alt, fallback, className }) => {
    return (
        <div className={`relative flex h-8 w-8 shrink-0  rounded-full ${className}`}>
            {src ? (
                <img className="aspect-square h-full w-full" alt={alt} src={src || "/placeholder.svg"} />
            ) : (
                <div className="flex h-full w-full items-center justify-center rounded-full bg-gray-200 text-gray-600 text-xs font-medium">
                    {fallback}
                </div>
            )}
        </div>
    )
}

// Component Badge tùy chỉnh
const CustomBadge = ({ children, className }) => {
    return (
        <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${className}`}>
            {children}
        </span>
    )
}

// Component Button tùy chỉnh
const CustomButton = ({ children, variant = "default", size = "default", className, ...props }) => {
    const baseClasses =
        "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50"
    let variantClasses = ""
    let sizeClasses = ""

    switch (variant) {
        case "default":
            variantClasses = "bg-blue-600 text-black hover:bg-blue-700"
            break
        case "outline":
            variantClasses = "border border-gray-300 hover:bg-gray-100 hover:text-gray-900"
            break
        case "ghost":
            variantClasses = "hover:bg-gray-100 hover:text-gray-900"
            break
        case "icon":
            sizeClasses = "h-8 w-8"
            break
        default:
            variantClasses = "bg-blue-600 text-white hover:bg-blue-700"
    }

    switch (size) {
        case "default":
            sizeClasses = "h-10 px-4 py-2"
            break
        case "sm":
            sizeClasses = "h-9 px-3"
            break
        case "lg":
            sizeClasses = "h-11 px-8"
            break
        case "icon":
            sizeClasses = "h-10 w-10"
            break
        default:
            sizeClasses = "h-10 px-4 py-2"
    }

    return (
        <button className={`${baseClasses} ${variantClasses} ${sizeClasses} ${className}`} {...props}>
            {children}
        </button>
    )
}

// Component DropdownMenu tùy chỉnh (đơn giản hóa)
const CustomDropdownMenu = ({ children }) => {
    const [isOpen, setIsOpen] = useState(false)

    const toggleOpen = () => setIsOpen(!isOpen)

    let trigger = null
    let content = null

    React.Children.forEach(children, (child) => {
        if (child.type === CustomDropdownMenuTrigger) {
            trigger = React.cloneElement(child, { onClick: toggleOpen })
        } else if (child.type === CustomDropdownMenuContent) {
            content = React.cloneElement(child, { isOpen, setIsOpen })
        }
    })

    return (
        <div className="relative">
            {trigger}
            {content}
        </div>
    )
}

const CustomDropdownMenuTrigger = ({ children, onClick }) => {
    return <div onClick={onClick}>{children}</div>
}

const CustomDropdownMenuContent = ({ children, isOpen, setIsOpen, align = "end" }) => {
    const alignClass = align === "end" ? "right-0" : "left-0"
    return (
        isOpen && (
            <div
                // className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg ${alignClass}`}
                // onMouseLeave={() => setIsOpen(false)}
                className={`absolute z-50 mt-2 w-40 rounded-md border p-1 shadow-lg bg-gray-300 ${alignClass}`}
                onMouseLeave={() => setIsOpen(false)}
            >
                {children}
            </div>
        )
    )
}

const CustomDropdownMenuItem = ({ children, className, ...props }) => {
    return (
        <div
            className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm text-black outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`}
            {...props}
        >
            {children}
        </div>
    )
}

export default function AccountManagePage() {
    const user = useUser();
    const [users, setUsers] = useState([]);

    // const [filters, setFilters] = useState({
    //     search: '',
    //     category: null,
    //     status: null,
    //     sortDirection: 'desc',
    //     currentPublisher: user?.id || null,
    // });

    const [sortDirection, setSortDirection] = useState('desc');

    // const handleSortByDate = () => {
    //     setFilters(prev => ({
    //         ...prev,
    //         sortDirection: prev.sortDirection === 'asc' ? 'desc' : 'asc'
    //     }));
    // };

    // const handleSearchChange = (e) => {
    //     setFilters(prev => ({ ...prev, search: e.target.value }));
    // };

    const popupRef = useRef();
    const [showRolePopup, setShowRolePopup] = useState(false);
    const [selectedRole, setSelectedRole] = useState(null);

    const RoleOptions = ['USER', 'ADMIN'];

    const statusDropdownRef = useRef();
    const [showStatusDropdown, setShowStatusDropdown] = useState(false);
    const [selectedStatus, setSelectedStatus] = useState(null);

    const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];

    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); // nếu API trả tổng số trang


    const handlePrevPage = () => {
  if (currentPage > 1) {
    setCurrentPage(prev => prev - 1);
  }
};

const handleNextPage = () => {
  if (currentPage < totalPages) {
    setCurrentPage(prev => prev + 1);
  }
};

const handlePageSelect = (page) => {
  setCurrentPage(page);
};

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getAllUsers(currentPage);
                console.log("Fetched users:", response);
                setUsers(response.data.result.data);
                
                setUsers(response.data.result.data);

                setTotalPages(response.data.result.totalPages);

            } catch (err) {
                console.error(err);
            }
        };
        fetchUser();

        const handleClickOutside = (event) => {
            if (popupRef.current && !popupRef.current.contains(event.target)) {
                setShowRolePopup(false);
            }
            if (statusDropdownRef.current && !statusDropdownRef.current.contains(event.target)) {
                setShowStatusDropdown(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    // }, [filters]);
    }, [currentPage]);

    const getStatusBadgeClass = (status) => {
        switch (status) {
            case "Hoạt động":
                return "bg-green-100 text-green-700"
            case "Success":
                return "bg-green-100 text-green-700"
            case "Pending":
                return "bg-yellow-100 text-yellow-700"
            case "Failed":
                return "bg-red-100 text-red-700"
            default:
                return ""
        }
    }

    const handlePromoteToAdmin = async (userId) => {
        try {
            // Giả định có một hàm dịch vụ để cập nhật vai trò người dùng
            console.log(`Nâng cấp người dùng ${userId} thành ADMIN`);
            const roleUpdate = 'ADMIN';
            await updateUserRole(userId, roleUpdate);
            console.log(`Nâng cấp người dùng ${userId} thành ADMIN`);
            // Cập nhật trạng thái cục bộ sau khi nâng cấp thành công
            setUsers(prevUsers =>
                prevUsers.map(u =>
                    u.id === userId ? { ...u, roles: [{ name: 'ADMIN' }] } : u
                )
            );
        } catch (err) {
            console.error('Lỗi khi nâng cấp người dùng:', err);
        }
    };

    return (
        <CustomCard className="w-full max-w-4xl mx-auto">
            <CustomCardHeader>
                <CustomCardTitle>Quản lý Tài Khoản</CustomCardTitle>
                <div className="relative w-48">
                    <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
                    {/* <CustomInput type="search" placeholder="Tìm kiếm..." className="w-full rounded-md pl-8 text-white" onChange={handleSearchChange} /> */}
                    <CustomInput type="search" placeholder="Tìm kiếm..." className="w-full rounded-md pl-8 text-white" />
                </div>
            </CustomCardHeader>
            <CustomCardContent>
                {/* <div className="overflow-x-auto">
                 */}
                 <div className="overflow-x-auto overflow-visible relative z-0">

                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="">
                            <tr>
                                <th
                                    scope="col"
                                    className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider w-[200px]"
                                >
                                    Tên tài khoản
                                </th>
                                {/* <th
                                    scope="col"
                                    className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                                    onClick={handleSortByDate}
                                >
                                    Ngày đăng ký
                                    {sortDirection === 'asc' ? ' 🔼' : ' 🔽'}
                                </th> */}
                                <th
                                    scope="col"
                                    className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"
                                >
                                    Email
                                </th>
                                <th
                                    scope="col"
                                    className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                                    onClick={() => setShowRolePopup(true)}
                                >
                                    Vai trò {selectedRole ? `: ${selectedRole}` : ''}
                                    {showRolePopup && (
                                        <div
                                            ref={popupRef}
                                            className="absolute z-50 mt-2 w-40 border bg-white border-gray-300 rounded shadow"
                                        >
                                            {RoleOptions.map((role) => (
                                                <div
                                                    key={role}
                                                    className="px-4 py-2 hover:bg-gray-200 cursor-pointer text-gray-700"
                                                    onClick={() => {
                                                        setSelectedRole(role);
                                                        // setFilters(prev => ({ ...prev, role }));
                                                        setShowRolePopup(false);
                                                    }}
                                                >
                                                    {role}
                                                </div>
                                            ))}
                                        </div>
                                    )}
                                </th>
                                {/* <th
                                    scope="col"
                                    className="relative px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
                                    onClick={() => setShowStatusDropdown(true)}
                                >
                                    Trạng thái {selectedStatus ? `: ${selectedStatus}` : ''}
                                    {showStatusDropdown && (
                                        <div
                                            ref={statusDropdownRef}
                                            className="absolute z-50 mt-2 w-40 border bg-white border-gray-300 rounded shadow"
                                        >
                                            {statusOptions.map((status) => (
                                                <div
                                                    key={status}
                                                    className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-gray-700"
                                                    onClick={() => {
                                                        setSelectedStatus(status);
                                                        // setFilters(prev => ({ ...prev, status }));
                                                        setShowStatusDropdown(false);
                                                    }}
                                                >
                                                    {status}
                                                </div>
                                            ))}
                                        </div>
                                    )}
                                </th> */}
                                <th
                                    scope="col"
                                    className="px-4 py-3 text-right text-xs font-medium text-gray-300 uppercase tracking-wider w-[40px]"
                                >
                                    Hành động
                                </th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-200">
                            {Array.isArray(users) && users.length > 0 ? (
                                users.map((user) => (
                                    <tr key={user.id}>
                                        <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{user.username}</span></td>
                                        {/* <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(user.birthDate).toLocaleDateString() || 'N/A'}</td> */}
                                        <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{user.email || 'N/A'}</td>
                                        <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{user.roles[0]?.name || 'N/A'}</td>
                                        {/* <td className="px-4 py-3 whitespace-nowrap">
                                            <CustomBadge className={getStatusBadgeClass(user.firstName)}>
                                                {user.firstName || 'N/A'}
                                            </CustomBadge>
                                        </td> */}
                                        <td className="px-4 py-3 whitespace-nowrap text-right">
                                            {user.roles[0]?.name !== 'ADMIN' ? (
                                                <CustomDropdownMenu>
                                                    <CustomDropdownMenuTrigger>
                                                        <CustomButton variant="ghost" size="icon" className="h-8 w-8">
                                                            <MoreHorizontal className="h-4 w-4 text-white" />
                                                        </CustomButton>
                                                    </CustomDropdownMenuTrigger>
                                                    <CustomDropdownMenuContent align="end">
                                                        <CustomDropdownMenuItem onClick={() => handlePromoteToAdmin(user.id)}>
                                                            Chuyển thành Admin
                                                        </CustomDropdownMenuItem>
                                                    </CustomDropdownMenuContent>
                                                </CustomDropdownMenu>
                                            ) : null}
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
                                        Không tìm thấy người dùng nào.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </CustomCardContent>
            {/* <div className="flex items-center justify-between p-4 border-t border-gray-200">
                <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent">
                    <ArrowLeft className="h-4 w-4" />
                    Trước
                </CustomButton>
                <div className="flex items-center gap-2">
                    <CustomButton variant="default" className="h-8 w-8 p-0 text-sm">
                        1
                    </CustomButton>
                    <CustomButton variant="ghost" className="h-8 w-8 p-0 text-sm">
                        2
                    </CustomButton>
                    <CustomButton variant="ghost" className="h-8 w-8 p-0 text-sm">
                        3
                    </CustomButton>
                </div>
                <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent">
                    Tiếp
                    <ArrowRight className="h-4 w-4" />
                </CustomButton>
            </div> */}
            <div className="flex items-center justify-between p-4 border-t border-gray-200">
                <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent" onClick={handlePrevPage} disabled={currentPage === 1}>
                    <ArrowLeft className="h-4 w-4" />
                    Trước
                </CustomButton>

                <div className="flex items-center gap-2">
                    {Array.from({ length: totalPages }, (_, index) => (
                        <CustomButton
                            key={index + 1}
                            variant={currentPage === index + 1 ? 'default' : 'ghost'}
                            className="h-8 w-8 p-0 text-sm"
                            onClick={() => handlePageSelect(index + 1)}
                        >
                            {index + 1}
                        </CustomButton>
                    ))}
                </div>

                <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent" onClick={handleNextPage} disabled={currentPage === totalPages}>
                    Tiếp
                    <ArrowRight className="h-4 w-4" />
                </CustomButton>
            </div>

        </CustomCard>
    )
}










// // "use client"

// import React, { useState, useEffect, useRef, use } from "react"
// import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react" // Vẫn sử dụng lucide-react cho các icon
// import { getFilteredNovels, getNovels  } from '../../services/novelService.js';
// import { useCurrentNovelPublisher, useUser } from '../../stores/userStores.js';
// import { useAdminStore, useAdminActions } from '../../stores/adminStore.js';
// import { useSetListNovel } from '../../stores/novelStore.js';
// import { getAllUsers } from '../../services/userService.js';


// // Component Card tùy chỉnh
// const CustomCard = ({ children, className }) => {
//     // return <div className={`bg-white rounded-lg shadow-sm border border-gray-200 ${className}`}>{children}</div>
//     return <div className={`rounded-lg border border-gray-200 ${className}`}>{children}</div>
// }

// // Component CardHeader tùy chỉnh
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

// export default function AccountManagePage() {

//     const user = useUser();

//     const [users, useSetListUser] = useState([]);

//     const [filters, setFilters] = useState({
//         search: '',
//         category: null,
//         status: null,
//         sortDirection: 'desc',
//         currentPublisher: user?.id || null,
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
//     const [showRolePopup, setShowRolePopup] = useState(false);
//     const [selectedRole, setSelectedRole] = useState(null);

//     const RoleOptions = ['USER', 'ADMIN'];

//     // status dropdown state
//     const statusDropdownRef = useRef();
//     const [showStatusDropdown, setShowStatusDropdown] = useState(false);
//     const [selectedStatus, setSelectedStatus] = useState(null);

//     const statusOptions = ['Đang chờ duyệt', 'Đã xuất bản', 'Bản nháp', 'Bị từ chối'];



//     useEffect(() => {
//         // if (!user?.id) return;

//         // if (!filters.currentPublisher) return;

//         const fetchUser = async () => {
//             try {
//                 const response = await getAllUsers();
//                 console.log("Fetched users:", response);
//                 useSetListUser(response.data.result);
//             } catch (err) {
//                 console.error(err);
//             }
//         };
//         fetchUser();


//         const handleClickOutside = (event) => {
//             if (popupRef.current && !popupRef.current.contains(event.target)) {
//                 setShowRolePopup(false);
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
//             name: "Adam Smith",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for PayPal logo
//             date: "Nov 23, 01:00 PM",
//             price: "adam@gmail.com",
//             category: "USER",
//             status: "Hoạt động",
//         },
//         {
//             id: "2",
//             name: "Messi",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Apple logo
//             date: "Nov 23, 01:00 PM",
//             price: "leo@gmail.com",
//             category: "USER",
//             status: "Hoạt động",
//         },
//         {
//             id: "3",
//             name: "Ronaldo",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for KKR logo
//             date: "Nov 23, 01:00 PM",
//             price: "cr7@gmail.com",
//             category: "USER",
//             status: "Hoạt động",
//         },
//         {
//             id: "4",
//             name: "Si da",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Facebook logo
//             date: "Nov 23, 01:00 PM",
//             price: "sida@gmail.com",
//             category: "ADMIN",
//             status: "Hoạt động",
//         },
//         {
//             id: "5",
//             name: "Donald Trump",
//             logo: "/placeholder.svg?height=24&width=24", // Placeholder for Amazon logo
//             date: "Nov 23, 01:00 PM",
//             price: "donaldtrump@gmail.com",
//             category: "USER",
//             status: "Hoạt động",
//         },
//     ]

//     const getStatusBadgeClass = (status) => {
//         switch (status) {
//             case "Hoạt động":
//                 return "bg-green-100 text-green-700"
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
//                 <CustomCardTitle>Quản lý Tài Khoản</CustomCardTitle>
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
//                                     Tên tài khoản
//                                 </th>
//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider cursor-pointer select-none"
//                                     onClick={handleSortByDate}
//                                 >
//                                     Ngày đăng ký
//                                     {sortDirection === 'asc' ? ' 🔼' : ' 🔽'}
//                                 </th>
//                                 <th
//                                     scope="col"
//                                     className="px-4 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"
//                                 >
//                                     Email
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
//                                     onClick={() => setShowRolePopup(true)}
//                                 >
//                                     {/* Role  */}
//                                     Role {selectedRole ? `: ${selectedRole}` : ''}

//                                     {showRolePopup && (
//                                         <div
//                                             ref={popupRef}
//                                             className="absolute z-50 mt-2 w-40 border bg-white border-gray-300 rounded shadow"
//                                         >
//                                             {RoleOptions.map((role) => (
//                                                 <div
//                                                     key={role}
//                                                     className="px-4 py-2 hover:bg-gray-200 cursor-pointer text-gray-700"
//                                                     // onClick={() => {
//                                                     //     setSelectedCategory(category);
//                                                     //     setShowCategoryPopup(false);
//                                                     // }}
//                                                     onClick={() => {
//                                                         setSelectedRole(role);
//                                                         setFilters(prev => ({ ...prev, role }));
//                                                         setShowRolePopup(false);
//                                                     }}
//                                                 >
//                                                     {role}
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
//                                             className="absolute z-50 mt-2 w-40 border bg-white border-gray-300 rounded shadow"
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
//                         <tbody className="divide-y divide-gray-200">
//                             {Array.isArray(users) && users.length > 0 ? (
//                                 users.map((user) => (
//                                     <tr key={user.id}>
//                                         <td className="px-4 py-3 whitespace-nowrap"><span className="font-medium text-sm text-white">{user.username}</span></td>
//                                         <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{new Date(user.birthDate).toLocaleDateString()|| 'N/A'}</td>
//                                         <td className="px-4 py-3 whitespace-nowrap text-sm text-white font-medium">{user.email || 'N/A'}</td>
//                                         <td className="px-4 py-3 whitespace-nowrap text-sm text-white">{user.roles[0]?.name || 'N/A'}</td>
//                                         <td className="px-4 py-3 whitespace-nowrap">
//                                             <CustomBadge className={getStatusBadgeClass(user.firstName)}>
//                                                 {user.firstName || 'N/A'}
//                                             </CustomBadge>
//                                         </td>
//                                         <td className="px-4 py-3 whitespace-nowrap text-right">
//                                             <CustomDropdownMenu>
//                                                 <CustomDropdownMenuTrigger>
//                                                     <CustomButton variant="ghost" size="icon" className="h-8 w-8">
//                                                         <MoreHorizontal className="h-4 w-4 text-white" />
//                                                     </CustomButton>
//                                                 </CustomDropdownMenuTrigger>
//                                                 <CustomDropdownMenuContent align="end">
//                                                     <CustomDropdownMenuItem>View novel</CustomDropdownMenuItem>
//                                                     <CustomDropdownMenuItem>Edit novel</CustomDropdownMenuItem>
//                                                     <CustomDropdownMenuItem>Delete novel</CustomDropdownMenuItem>
//                                                 </CustomDropdownMenuContent>
//                                             </CustomDropdownMenu>
//                                         </td>
//                                     </tr>
//                                 ))
//                             ) : (
//                                 <tr>
//                                     <td colSpan="6" className="px-4 py-3 text-center text-sm text-white">
//                                         Không tìm thấy truyện nào.
//                                     </td>
//                                 </tr>
//                             )}
//                         </tbody>
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
