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

import { PageNavigator } from '../global/Navigators.jsx';

import React, { useState, useEffect } from 'react';
import { Filter, ChevronUp, ChevronDown, X } from 'lucide-react';

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
                         searchPlaceholder = "Search...",
                         updateData = null, // function to call for both filter and sort updates
                         currentSort = null, // { key: 'columnKey', direction: 'asc' | 'desc' }
                         filterOptions = {} // { filterKey: [{ label: 'Option 1', value: 'value1' }] }
                     }) => {
    const [searchValue, setSearchValue] = useState("");
    const [showFilterModal, setShowFilterModal] = useState(false);
    const [currentFilterKey, setCurrentFilterKey] = useState(null);
    const [activeFilters, setActiveFilters] = useState({});

    // Debounce search
    useEffect(() => {
        const delay = setTimeout(() => {
            if (onSearch) onSearch(searchValue);
        }, 500);
        return () => clearTimeout(delay);
    }, [searchValue]);

    const handleSort = (headerKey) => {
        if (updateData) {
            // Determine new sort direction
            let newDirection = 'asc';
            if (currentSort && currentSort.key === headerKey) {
                newDirection = currentSort.direction === 'asc' ? 'desc' : 'asc';
            }

            // updateData({
            //     size: pageSize,
            //     page: 1,
            //     sortBy: headerKey,
            //     sortDirection: newDirection,
            // });
        } else if (onSort) {
            onSort(headerKey);
        }
    };

    const handleFilterClick = (filterKey, event) => {
        event.stopPropagation(); // Prevent sort when clicking filter
        setCurrentFilterKey(filterKey);
        setShowFilterModal(true);
    };

    const handleFilterChange = (optionValue, checked) => {
        const newFilters = { ...activeFilters };

        if (!newFilters[currentFilterKey]) {
            newFilters[currentFilterKey] = [];
        }

        if (checked) {
            if (!newFilters[currentFilterKey].includes(optionValue)) {
                newFilters[currentFilterKey].push(optionValue);
            }
        } else {
            newFilters[currentFilterKey] = newFilters[currentFilterKey].filter(val => val !== optionValue);
            if (newFilters[currentFilterKey].length === 0) {
                delete newFilters[currentFilterKey];
            }
        }
        // console.log("Updated filters:", newFilters);
        if (updateData){
            updateData({
                size: 10,
                page: 2,
                sortBy: "createdAt",
                sortDirection: "DESC",
            });
        }

        setActiveFilters(newFilters);
    };

    const applyFilters = () => {
        console.log({
            size: 10,
            page: 1,
            // sortBy: currentSort?.key,
            // sortDirection: currentSort?.direction,
            ...activeFilters
        });
        if (updateData) {
            updateData({
                size: pageSize,
                page: 1,
                sortBy: currentSort?.key,
                sortDirection: currentSort?.direction,
                ...activeFilters
            });
        }
        setShowFilterModal(false);
        setCurrentFilterKey(null);
    };

    const clearCurrentFilter = () => {
        const newFilters = { ...activeFilters };
        delete newFilters[currentFilterKey];
        setActiveFilters(newFilters);

        if (updateData) {
            // updateData({
            //     size: pageSize,
            //     page: 1,
            //     sortBy: currentSort?.key,
            //     sortDirection: currentSort?.direction,
            // });
        }
        setShowFilterModal(false);
        setCurrentFilterKey(null);
    };

    const getSortIcon = (headerKey) => {
        if (!currentSort || currentSort.key !== headerKey) {
            return <span className="ml-1 text-gray-400">⇅</span>;
        }

        return currentSort.direction === 'asc' ?
            <ChevronUp className="ml-1 w-4 h-4 inline" /> :
            <ChevronDown className="ml-1 w-4 h-4 inline" />;
    };

    const hasActiveFilter = (filterKey) => {
        return activeFilters[filterKey] && activeFilters[filterKey].length > 0;
    };

    const getCurrentFilterConfig = () => {
        const header = headers.find(h => h.filter?.key === currentFilterKey);
        return header?.filter;
    };

    const getCurrentFilterOptions = () => {
        return filterOptions[currentFilterKey] || [];
    };

    return (
        <div className="relative overflow-x-auto shadow-md sm:rounded-lg rounded-md p-3 min-h-[500px]">
            {/* Toolbar */}
            <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
                <div className="flex flex-row gap-x-2 items-center">
                    {toolbar && <div className="flex flex-row gap-x-2">{toolbar}</div>}
                </div>

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
                                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
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

            {/* Filter Modal */}
            {showFilterModal && currentFilterKey && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                    <div className="bg-white rounded-lg p-6 w-full max-w-md mx-4 max-h-[80vh] overflow-y-auto">
                        <div className="flex justify-between items-center mb-4">
                            <h3 className="text-lg font-semibold text-gray-900">
                                {getCurrentFilterConfig()?.label || 'Bộ lọc'}
                            </h3>
                            <button
                                onClick={() => {
                                    setShowFilterModal(false);
                                    setCurrentFilterKey(null);
                                }}
                                className="text-gray-400 hover:text-gray-600"
                            >
                                <X className="w-5 h-5" />
                            </button>
                        </div>

                        <div className="space-y-2">
                            {getCurrentFilterOptions().map((option) => (
                                <label key={option.value} className="flex items-center">
                                    <input
                                        type="checkbox"
                                        checked={activeFilters[currentFilterKey]?.includes(option.value) || false}
                                        onChange={(e) => handleFilterChange(option.value, e.target.checked)}
                                        className="rounded border-gray-300 text-blue-600 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                                    />
                                    <span className="ml-2 text-sm text-gray-700">{option.label}</span>
                                </label>
                            ))}
                        </div>

                        <div className="flex justify-end gap-3 mt-6 pt-4 border-t">
                            <button
                                onClick={clearCurrentFilter}
                                className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50"
                            >
                                Xóa bộ lọc
                            </button>
                            <button
                                onClick={applyFilters}
                                className="px-4 py-2 text-sm font-medium text-white bg-blue-600 border border-transparent rounded-md hover:bg-blue-700"
                            >
                                Áp dụng
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Table */}
            <table className="w-full text-sm text-left text-gray-500">
                <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b">
                <tr>
                    {headers.map((h, i) => {
                        const headerKey = h.key ?? h.label ?? String(h);
                        const hasFilter = h.filter && h.filter.key;

                        return (
                            <th
                                key={i}
                                scope="col"
                                className={`px-6 py-3 ${sortable ? 'cursor-pointer hover:bg-gray-100' : ''}`}
                                onClick={() => sortable && !hasFilter && handleSort(headerKey)}
                            >
                                <div className="flex items-center justify-between">
                                    <div
                                        className={`flex items-center ${sortable && !hasFilter ? '' : 'cursor-default'}`}
                                        onClick={() => sortable && hasFilter && handleSort(headerKey)}
                                    >
                                        {typeof h === "string" ? h : h.label}
                                        {sortable && getSortIcon(headerKey)}
                                    </div>

                                    {/* Filter Icon */}
                                    {hasFilter && (
                                        <button
                                            onClick={(e) => handleFilterClick(h.filter.key, e)}
                                            className={`ml-2 p-1 rounded hover:bg-gray-200 transition-colors ${
                                                hasActiveFilter(h.filter.key)
                                                    ? 'text-blue-600 bg-blue-50'
                                                    : 'text-gray-400 hover:text-gray-600'
                                            }`}
                                            title={`Lọc ${h.filter.label || h.label}`}
                                        >
                                            <Filter className="w-4 h-4" />
                                            {hasActiveFilter(h.filter.key) && (
                                                <span className="absolute -top-1 -right-1 bg-blue-500 text-white text-xs rounded-full w-4 h-4 flex items-center justify-center">
                                                        {activeFilters[h.filter.key].length}
                                                    </span>
                                            )}
                                        </button>
                                    )}
                                </div>
                            </th>
                        );
                    })}
                </tr>
                </thead>
                <tbody>
                {loading ? (
                    <tr>
                        <td colSpan={headers.length} className="text-center py-4">
                            <div className="flex items-center justify-center">
                                <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
                                <span className="ml-2">Loading...</span>
                            </div>
                        </td>
                    </tr>
                ) : data && data.length > 0 ? (
                    data.map((item, index) => renderRow(item, index))
                ) : (
                    <tr>
                        <td colSpan={headers.length} className="text-center py-4">
                            <div className="text-gray-500">
                                <svg className="mx-auto h-12 w-12 text-gray-400" stroke="currentColor" fill="none" viewBox="0 0 48 48">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M34 40h10v-4a6 6 0 00-10.712-3.714M34 40H14m20 0v-4a9.971 9.971 0 00-.712-3.714M14 40H4v-4a6 6 0 0110.713-3.714M14 40v-4c0-1.313.253-2.6.713-3.714m0 0A9.971 9.971 0 0124 24a9.971 9.971 0 018.287 11.286" />
                                </svg>
                                <p className="mt-2">Không tìm thấy dữ liệu</p>
                            </div>
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
//                          toolbar,
//                          onSearch,
//                          sortable = false,
//                          onSort,
//                          loading = false,
//                          searchPlaceholder = "Search..."
//                      }) => {
//     const [searchValue, setSearchValue] = useState("");
//
//     // Debounce search
//     useEffect(() => {
//         const delay = setTimeout(() => {
//             if (onSearch) onSearch(searchValue);
//         }, 500);
//         return () => clearTimeout(delay);
//     }, [searchValue]);
//
//     const handleSort = (headerKey) => {
//         if (onSort) onSort(headerKey);
//     };
//
//
//     return (
//         <div className="relative overflow-x-auto shadow-md sm:rounded-lg  rounded-md p-3 min-h-[500px]">
//             {/* Toolbar */}
//             <div className="flex flex-col sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
//                 {toolbar && <div className="flex flex-row gap-x-2">{toolbar}</div>}
//                 <div className="relative">
//                     <div className="absolute inset-y-0 left-0 flex items-center ps-3 pointer-events-none">
//                         <svg
//                             className="w-5 h-5 text-gray-500"
//                             aria-hidden="true"
//                             fill="currentColor"
//                             viewBox="0 0 20 20"
//                         >
//                             <path
//                                 fillRule="evenodd"
//                                 d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89
//                   3.476l4.817 4.817a1 1 0 01-1.414
//                   1.414l-4.816-4.816A6 6 0 012 8z"
//                                 clipRule="evenodd"
//                             />
//                         </svg>
//                     </div>
//                     <input
//                         type="text"
//                         value={searchValue}
//                         onChange={(e) => setSearchValue(e.target.value)}
//                         className="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
//                         placeholder={searchPlaceholder}
//                     />
//                 </div>
//             </div>
//
//             {/* Table */}
//             <table className="w-full text-sm text-left text-gray-500">
//                 <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b">
//                 <tr>
//                     {/*{headers.map((h, i) => (*/}
//                     {/*    <th*/}
//                     {/*        key={i}*/}
//                     {/*        scope="col"*/}
//                     {/*        className="px-6 py-3 cursor-pointer"*/}
//                     {/*        onClick={() => sortable && handleSort(h.key || h)}*/}
//                     {/*    >*/}
//                     {/*        {h.label || h}*/}
//                     {/*        {sortable && <span className="ml-1">⇅</span>}*/}
//                     {/*    </th>*/}
//                     {/*))}*/}
//                     {headers.map((h, i) => (
//                         <th
//                             key={i}
//                             scope="col"
//                             className="px-6 py-3 cursor-pointer"
//                             onClick={() => sortable && handleSort(h.key ?? h.label ?? String(h))}
//                         >
//                             {typeof h === "string" ? h : h.label}
//                             {sortable && <span className="ml-1">⇅</span>}
//                         </th>
//                     ))}
//                 </tr>
//                 </thead>
//                 <tbody>
//                 {loading ? (
//                     <tr>
//                         <td colSpan={headers.length} className="text-center py-4">
//                             Loading...
//                         </td>
//                     </tr>
//                 ) : data && data.length > 0 ? (
//                     data.map((item, index) => renderRow(item, index))
//                 ) : (
//                     <tr>
//                         <td colSpan={headers.length} className="text-center py-4">
//                             No data found
//                         </td>
//                     </tr>
//                 )}
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
//
// export default CommonTable;

export {CommonTable};