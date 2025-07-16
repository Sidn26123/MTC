"use client"

import React, { useState } from "react"
import { Search, MoreHorizontal, ArrowLeft, ArrowRight } from "lucide-react" // Vẫn sử dụng lucide-react cho các icon

// Component Card tùy chỉnh
const CustomCard = ({ children, className }) => {
  return <div className={`bg-white rounded-lg shadow-sm border border-gray-200 ${className}`}>{children}</div>
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
  return <h2 className={`text-lg font-semibold text-gray-800 ${className}`}>{children}</h2>
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
      className={`w-full rounded-md bg-white border border-gray-300 pl-8 pr-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${className}`}
      {...props}
    />
  )
}

// Component Avatar tùy chỉnh
const CustomAvatar = ({ src, alt, fallback, className }) => {
  return (
    <div className={`relative flex h-8 w-8 shrink-0 overflow-hidden rounded-full ${className}`}>
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
      variantClasses = "bg-blue-600 text-white hover:bg-blue-700"
      break
    case "outline":
      variantClasses = "border border-gray-300 bg-white hover:bg-gray-100 hover:text-gray-900"
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

  // Tìm DropdownMenuTrigger và DropdownMenuContent trong children
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
        className={`absolute z-50 mt-2 w-40 rounded-md border bg-white p-1 shadow-lg ${alignClass}`}
        onMouseLeave={() => setIsOpen(false)} // Đóng khi chuột rời khỏi menu
      >
        {children}
      </div>
    )
  )
}

const CustomDropdownMenuItem = ({ children, className, ...props }) => {
  return (
    <div
      className={`relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-gray-100 focus:text-gray-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 ${className}`}
      {...props}
    >
      {children}
    </div>
  )
}

export default function TransactionsTable() {
  const transactions = [
    {
      id: "1",
      name: "Bought PYPL",
      logo: "/placeholder.svg?height=24&width=24", // Placeholder for PayPal logo
      date: "Nov 23, 01:00 PM",
      price: "$2,567.88",
      category: "Finance",
      status: "Success",
    },
    {
      id: "2",
      name: "Bought AAPL",
      logo: "/placeholder.svg?height=24&width=24", // Placeholder for Apple logo
      date: "Nov 23, 01:00 PM",
      price: "$2,567.88",
      category: "Finance",
      status: "Pending",
    },
    {
      id: "3",
      name: "Sell KKST",
      logo: "/placeholder.svg?height=24&width=24", // Placeholder for KKR logo
      date: "Nov 23, 01:00 PM",
      price: "$2,567.88",
      category: "Finance",
      status: "Success",
    },
    {
      id: "4",
      name: "Bought FB",
      logo: "/placeholder.svg?height=24&width=24", // Placeholder for Facebook logo
      date: "Nov 23, 01:00 PM",
      price: "$2,567.88",
      category: "Finance",
      status: "Success",
    },
    {
      id: "5",
      name: "Sell AMZN",
      logo: "/placeholder.svg?height=24&width=24", // Placeholder for Amazon logo
      date: "Nov 23, 01:00 PM",
      price: "$2,567.88",
      category: "Finance",
      status: "Failed",
    },
  ]

  const getStatusBadgeClass = (status) => {
    switch (status) {
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

  return (
    <CustomCard className="w-full max-w-4xl mx-auto shadow-sm">
      <CustomCardHeader>
        <CustomCardTitle>Latest Transactions</CustomCardTitle>
        <div className="relative w-48">
          <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-400" />
          <CustomInput type="search" placeholder="Search..." className="w-full rounded-md bg-white pl-8 text-sm" />
        </div>
      </CustomCardHeader>
      <CustomCardContent>
        <div className="overflow-x-auto">
          {" "}
          {/* Đảm bảo bảng responsive */}
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th
                  scope="col"
                  className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider w-[200px]"
                >
                  Name
                </th>
                <th
                  scope="col"
                  className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Date
                </th>
                <th
                  scope="col"
                  className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Price
                </th>
                <th
                  scope="col"
                  className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Category
                </th>
                <th
                  scope="col"
                  className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Status
                </th>
                <th
                  scope="col"
                  className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider w-[40px]"
                >
                  <span className="sr-only">Actions</span>
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {transactions.map((transaction) => (
                <tr key={transaction.id}>
                  <td className="px-4 py-3 whitespace-nowrap">
                    <div className="flex items-center gap-3">
                      <CustomAvatar
                        src={transaction.logo}
                        alt={transaction.name}
                        fallback={transaction.name.charAt(0)}
                        className="h-6 w-6"
                      />
                      <span className="font-medium text-sm text-gray-900">{transaction.name}</span>
                    </div>
                  </td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-600">{transaction.date}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-800 font-medium">{transaction.price}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-600">{transaction.category}</td>
                  <td className="px-4 py-3 whitespace-nowrap">
                    <CustomBadge className={getStatusBadgeClass(transaction.status)}>{transaction.status}</CustomBadge>
                  </td>
                  <td className="px-4 py-3 whitespace-nowrap text-right">
                    <CustomDropdownMenu>
                      <CustomDropdownMenuTrigger>
                        <CustomButton variant="ghost" size="icon" className="h-8 w-8">
                          <MoreHorizontal className="h-4 w-4 text-gray-500" />
                          <span className="sr-only">Actions</span>
                        </CustomButton>
                      </CustomDropdownMenuTrigger>
                      <CustomDropdownMenuContent align="end">
                        <CustomDropdownMenuItem>View transaction</CustomDropdownMenuItem>
                        <CustomDropdownMenuItem>Edit transaction</CustomDropdownMenuItem>
                        <CustomDropdownMenuItem>Delete transaction</CustomDropdownMenuItem>
                      </CustomDropdownMenuContent>
                    </CustomDropdownMenu>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </CustomCardContent>
      <div className="flex items-center justify-between p-4 border-t border-gray-200">
        <CustomButton variant="outline" className="flex items-center gap-2 text-sm bg-transparent">
          <ArrowLeft className="h-4 w-4" />
          Previous
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
          Next
          <ArrowRight className="h-4 w-4" />
        </CustomButton>
      </div>
    </CustomCard>
  )
}



// import React from "react";

// function NovelManagePage() {
//   const data = [
//     { id: 1, user: "Abram Schliefer", position: "Sales Assistant", office: "Edinburgh", age: 57, startDate: "25 Apr, 2027", salary: "$89,500" },
//     { id: 2, user: "Charlotte Anderson", position: "Marketing Manager", office: "London", age: 42, startDate: "12 Mar, 2025", salary: "$105,000" },
//     { id: 3, user: "Ethan Brown", position: "Software Engineer", office: "San Francisco", age: 30, startDate: "01 Jan, 2024", salary: "$120,000" },
//     { id: 4, user: "Isabella Davis", position: "UI/UX Designer", office: "Austin", age: 29, startDate: "18 Jul, 2025", salary: "$92,000" },
//     { id: 5, user: "James Wilson", position: "Data Analyst", office: "Chicago", age: 28, startDate: "20 Sep, 2025", salary: "$80,000" },
//   ];

//   return (
//     <div className="p-4 bg-gray-50 rounded-lg shadow">
//       <div className="flex justify-between items-center mb-4">
//         <h2 className="text-lg font-semibold text-gray-800">Data Table 1</h2>
//         <div className="flex space-x-4">
//           <div className="flex items-center">
//             <label className="mr-2 text-gray-600">Show</label>
//             <select className="p-1 border rounded">
//               <option>10</option>
//               <option>25</option>
//               <option>50</option>
//               <option>100</option>
//             </select>
//             <span className="ml-2 text-gray-600">entries</span>
//           </div>
//           <div>
//             <input
//               type="text"
//               placeholder="Search..."
//               className="p-1 border rounded"
//             />
//           </div>
//         </div>
//       </div>
//       <table className="w-full bg-white border-collapse">
//         <thead>
//           <tr className="bg-gray-100">
//             <th className="p-2 text-left text-gray-600 border-b">User</th>
//             <th className="p-2 text-left text-gray-600 border-b">Position</th>
//             <th className="p-2 text-left text-gray-600 border-b">Office</th>
//             <th className="p-2 text-left text-gray-600 border-b">Age</th>
//             <th className="p-2 text-left text-gray-600 border-b">Start Date</th>
//             <th className="p-2 text-left text-gray-600 border-b">Salary</th>
//           </tr>
//         </thead>
//         <tbody>
//           {data.map((item) => (
//             <tr key={item.id} className="hover:bg-gray-50">
//               <td className="p-2 border-b flex items-center">
//                 <img
//                   src={`https://via.placeholder.com/40`}
//                   alt={item.user}
//                   className="w-8 h-8 rounded-full mr-2"
//                 />
//                 {item.user}
//               </td>
//               <td className="p-2 border-b">{item.position}</td>
//               <td className="p-2 border-b">{item.office}</td>
//               <td className="p-2 border-b">{item.age}</td>
//               <td className="p-2 border-b">{item.startDate}</td>
//               <td className="p-2 border-b">{item.salary}</td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// }

// export default NovelManagePage;