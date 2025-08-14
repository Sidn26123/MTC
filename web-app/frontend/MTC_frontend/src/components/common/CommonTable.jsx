// import { PageNavigator } from '../global/Navigators.jsx';
//
// const CommonTable = ({
//                          data,
//                          headers,
//                          renderRow,
//                          currentPage,
//                          pageSize,
//                          totalPages,
//                          totalElements,
//                          onPageChange,
//                          onPageSizeChange,
//                          toolbar
//                      }) => {
//     return (
//         <div className="relative overflow-x-auto shadow-md sm:rounded-lg bg-white dark:bg-gray-800 rounded-md p-3 min-h-[500px]">
//             {/* Toolbar (search, dropdown, button, ...) */}
//             {toolbar && (
//                 <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
//                     {toolbar}
//                 </div>
//             )}
//
//             {/* Table */}
//             <table className="w-full text-sm text-left text-gray-500">
//                 <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b">
//                 <tr>
//                     {headers.map((h, i) => (
//                         <th key={i} scope="col" className="px-6 py-3">{h}</th>
//                     ))}
//                 </tr>
//                 </thead>
//                 <tbody>
//                 {data && data.length > 0
//                     ? data.map((item, index) => renderRow(item, index))
//                     : (
//                         <tr>
//                             <td colSpan={headers.length} className="text-center py-4">
//                                 No data found
//                             </td>
//                         </tr>
//                     )
//                 }
//                 </tbody>
//             </table>
//
//             {/* Pagination */}
//             <div className="flex justify-end mt-4">
//                 <PageNavigator
//                     page={currentPage}
//                     pageSize={pageSize}
//                     totalPages={totalPages}
//                     totalElements={totalElements}
//                     onPageChange={onPageChange}
//                     onPageSizeChange={onPageSizeChange}
//                 />
//             </div>
//         </div>
//     );
// };

import { useState, useEffect } from "react";
import { PageNavigator } from '../global/Navigators.jsx';

const CommonTable = ({
                         data,
                         headers,
                         renderRow,
                         currentPage,
                         pageSize,
                         totalPages,
                         totalElements,
                         onPageChange,
                         onPageSizeChange,
                         toolbar,
                         onSearch,
                         sortable = false,
                         onSort,
                         loading = false,
                         searchPlaceholder = "Search..."
                     }) => {
    const [searchValue, setSearchValue] = useState("");

    // Debounce search
    useEffect(() => {
        const delay = setTimeout(() => {
            if (onSearch) onSearch(searchValue);
        }, 500);
        return () => clearTimeout(delay);
    }, [searchValue]);

    const handleSort = (headerKey) => {
        if (onSort) onSort(headerKey);
    };

    return (
        <div className="relative overflow-x-auto shadow-md sm:rounded-lg bg-white dark:bg-gray-800 rounded-md p-3 min-h-[500px]">
            {/* Toolbar */}
            <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
                {toolbar && <div className="flex flex-row gap-x-2">{toolbar}</div>}
                <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center ps-3 pointer-events-none">
                        <svg
                            className="w-5 h-5 text-gray-500"
                            aria-hidden="true"
                            fill="currentColor"
                            viewBox="0 0 20 20"
                        >
                            <path
                                fillRule="evenodd"
                                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89
                  3.476l4.817 4.817a1 1 0 01-1.414
                  1.414l-4.816-4.816A6 6 0 012 8z"
                                clipRule="evenodd"
                            />
                        </svg>
                    </div>
                    <input
                        type="text"
                        value={searchValue}
                        onChange={(e) => setSearchValue(e.target.value)}
                        className="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
                        placeholder={searchPlaceholder}
                    />
                </div>
            </div>

            {/* Table */}
            <table className="w-full text-sm text-left text-gray-500">
                <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b">
                <tr>
                    {/*{headers.map((h, i) => (*/}
                    {/*    <th*/}
                    {/*        key={i}*/}
                    {/*        scope="col"*/}
                    {/*        className="px-6 py-3 cursor-pointer"*/}
                    {/*        onClick={() => sortable && handleSort(h.key || h)}*/}
                    {/*    >*/}
                    {/*        {h.label || h}*/}
                    {/*        {sortable && <span className="ml-1">⇅</span>}*/}
                    {/*    </th>*/}
                    {/*))}*/}
                    {headers.map((h, i) => (
                        <th
                            key={i}
                            scope="col"
                            className="px-6 py-3 cursor-pointer"
                            onClick={() => sortable && handleSort(h.key ?? h.label ?? String(h))}
                        >
                            {typeof h === "string" ? h : h.label}
                            {sortable && <span className="ml-1">⇅</span>}
                        </th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {loading ? (
                    <tr>
                        <td colSpan={headers.length} className="text-center py-4">
                            Loading...
                        </td>
                    </tr>
                ) : data && data.length > 0 ? (
                    data.map((item, index) => renderRow(item, index))
                ) : (
                    <tr>
                        <td colSpan={headers.length} className="text-center py-4">
                            No data found
                        </td>
                    </tr>
                )}
                </tbody>
            </table>

            {/* Pagination */}
            <div className="flex justify-end mt-4">
                <PageNavigator
                    page={currentPage}
                    pageSize={pageSize}
                    totalPages={totalPages}
                    totalElements={totalElements}
                    onPageChange={onPageChange}
                    onPageSizeChange={onPageSizeChange}
                />
            </div>
        </div>
    );
};

export default CommonTable;

export {CommonTable};