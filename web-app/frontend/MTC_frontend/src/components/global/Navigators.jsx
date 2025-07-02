import React, { useEffect, useState } from 'react';
import { SimpleDropdown } from '../../common/CommonComponents.jsx';
import { faBackwardStep, faChevronLeft, faChevronRight, faForwardStep } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useSetListNovelPage } from '../../stores/novelStore.js';
import { useSetPage } from '../../stores/selectors/novelFilterSelector.js';

const DefaultNavigator = () => {
    return (
        <>
            <div>
                <div className={"flex flex-row justify-end items-center gap-x-1 w-full"}>
                    <div></div>
                    <div className={"flex flex-row justify-between gap-x-2 items-center"}>
                        <span>Số dòng trên trang</span>
                        <div className={"w-36"}>
                            <SimpleDropdown />

                        </div>
                        <span>1-2 cua 2</span>
                        <div className={"gap-x-2 flex flex-row text-sm"}>
                            <FontAwesomeIcon icon={faBackwardStep} />
                            <FontAwesomeIcon icon={faChevronLeft} />
                            <FontAwesomeIcon icon={faChevronRight} />
                            <FontAwesomeIcon icon={faForwardStep} />
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}


// const PageNavigator = ({
//                            page = 1,
//                            totalPages = 1,
//                            pageSize = 10,
//                            onPageChange = () => {},
//                            onPageSizeChange = () => {},
//                            onBackward = () => {},
//                            onForward = () => {},
//                            onFirst = () => {},
//                            onLast = () => {},
//                        }) => {
//     const [currentPage, setCurrentPage] = useState(page);
//     const [inputPage, setInputPage] = useState(page.toString());
//     const [currentPageSize, setCurrentPageSize] = useState(pageSize.toString());
//
//     // Update internal state when props change
//     useEffect(() => {
//         setCurrentPage(page);
//         setInputPage(page.toString());
//     }, [page]);
//
//     useEffect(() => {
//         setCurrentPageSize(pageSize.toString());
//     }, [pageSize]);
//
//     // Handle page input change
//     const handlePageInputChange = (e) => {
//         setInputPage(e.target.value);
//     };
//
//     // Handle page size input change
//     const handlePageSizeInputChange = (e) => {
//         setCurrentPageSize(e.target.value);
//     };
//
//     // Navigate to first page
//     const handleFirstPage = () => {
//         if (currentPage > 1) {
//             onFirst?.();
//             onPageChange(1);
//         }
//     };
//
//     // Navigate to previous page
//     const handlePreviousPage = () => {
//         if (currentPage > 1) {
//             onBackward?.();
//             onPageChange(currentPage - 1);
//         }
//     };
//
//     // Navigate to next page
//     const handleNextPage = () => {
//         if (currentPage < totalPages) {
//             onForward?.();
//             onPageChange(currentPage + 1);
//         }
//     };
//
//     // Navigate to last page
//     const handleLastPage = () => {
//         if (currentPage < totalPages) {
//             onLast?.();
//             onPageChange(totalPages);
//         }
//     };
//
//     // Handle page input keydown event (Enter key)
//     const handlePageInputKeyDown = (e) => {
//         if (e.key === 'Enter') {
//             const newPage = parseInt(inputPage, 10);
//             if (!isNaN(newPage) && newPage >= 1 && newPage <= totalPages) {
//                 onPageChange(newPage);
//             } else {
//                 // Reset to current page if invalid input
//                 setInputPage(currentPage.toString());
//             }
//         }
//     };
//
//     // Handle page size input keydown event (Enter key)
//     const handlePageSizeInputKeyDown = (e) => {
//         if (e.key === 'Enter') {
//             const newPageSize = parseInt(currentPageSize, 10);
//             if (!isNaN(newPageSize) && newPageSize > 0) {
//                 onPageSizeChange(newPageSize);
//             } else {
//                 // Reset to current page size if invalid input
//                 setCurrentPageSize(pageSize.toString());
//             }
//         }
//     };
//
//     // Handle blur events to reset invalid inputs
//     const handlePageInputBlur = () => {
//         const newPage = parseInt(inputPage, 10);
//         if (isNaN(newPage) || newPage < 1 || newPage > totalPages) {
//             setInputPage(currentPage.toString());
//         }
//     };
//
//     const handlePageSizeInputBlur = () => {
//         const newPageSize = parseInt(currentPageSize, 10);
//         if (isNaN(newPageSize) || newPageSize < 1) {
//             setCurrentPageSize(pageSize.toString());
//         }
//     };
const PageNavigator = ({
                           page = 1,
                           pageSize = 10,
                           totalPages = 0,
                           totalElements = 0,
                           onPageChange,
                           onPageSizeChange
                       }) => {
    const [inputPage, setInputPage] = useState(page.toString());
    const [currentPage, setCurrentPage] = useState(page);
    const [currentPageSize, setCurrentPageSize] = useState(pageSize.toString());

    // const changePage = onPageChange;
    const changePage = useSetPage();
    const changePageSize = onPageSizeChange;

    // Update state when props change
    useEffect(() => {
        setCurrentPage(page);
        setInputPage(page.toString());
    }, [page]);

    useEffect(() => {
        setCurrentPageSize(pageSize.toString());
    }, [pageSize]);

    // Page navigation handlers
    const handleFirstPage = () => {
        if (currentPage > 1) {
            changePage(1);
        }
    };

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            changePage(currentPage - 1);
        }
    };

    const handleNextPage = () => {
        console.log(currentPage, totalPages);
        if (currentPage < totalPages) {
            changePage(currentPage + 1);
        }
    };

    const handleLastPage = () => {
        if (currentPage < totalPages) {
            changePage(totalPages);
        }
    };

    // Page input handlers
    const handlePageInputChange = (e) => {
        // Allow only numbers
        const value = e.target.value.replace(/[^0-9]/g, '');
        setInputPage(value);
    };

    const handlePageInputKeyDown = (e) => {
        if (e.key === 'Enter') {
            handlePageInputBlur();
        }
    };

    const handlePageInputBlur = () => {
        let newPage = parseInt(inputPage, 10);

        // Validate the page number
        if (isNaN(newPage) || newPage < 1) {
            newPage = 1;
        } else if (newPage > totalPages) {
            newPage = totalPages;
        }

        setInputPage(newPage.toString());
        if (newPage !== currentPage) {
            onPageChange(newPage);
        }
    };

    // Page size input handlers
    const handlePageSizeInputChange = (e) => {
        // Allow only numbers
        const value = e.target.value.replace(/[^0-9]/g, '');
        setCurrentPageSize(value);
    };

    const handlePageSizeInputKeyDown = (e) => {
        if (e.key === 'Enter') {
            handlePageSizeInputBlur();
        }
    };

    const handlePageSizeInputBlur = () => {
        let newPageSize = parseInt(currentPageSize, 10);

        // Validate the page size
        if (isNaN(newPageSize) || newPageSize < 1) {
            newPageSize = 10; // Default page size
        } else if (newPageSize > 100) {
            newPageSize = 100; // Max page size
        }

        setCurrentPageSize(newPageSize.toString());
        if (newPageSize !== pageSize) {
            onPageSizeChange(newPageSize);
        }
    };
    return (
        <div className="flex flex-row text-sm justify-center items-center gap-x-5 text-gray-500">
            {/* First page button */}
            <FontAwesomeIcon
                icon={faBackwardStep}
                className={`text-md ${currentPage > 1 ? 'hover:text-primary hover:cursor-pointer' : 'opacity-50'}`}
                onClick={handleFirstPage}
                aria-label="Go to first page"
            />

            {/* Previous page button */}
            <FontAwesomeIcon
                icon={faChevronLeft}
                className={`text-md ${currentPage > 1 ? 'hover:text-primary hover:cursor-pointer' : 'opacity-50'}`}
                onClick={handlePreviousPage}
                aria-label="Go to previous page"
            />

            {/* Page input with total pages indicator */}
            <div className="relative">
                <input
                    type="text"
                    value={inputPage}
                    onChange={handlePageInputChange}
                    onKeyDown={handlePageInputKeyDown}
                    onBlur={handlePageInputBlur}
                    className="border border-primary border-gray-500 rounded h-8 w-20 pl-4 bg-inherit text-black dark:text-white placeholder:text-gray-500 text-sm"
                    aria-label="Current page"
                />
                <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
                    <span className="text-gray-500 text-sm">/{totalPages}</span>
                </div>
            </div>

            {/* Next page button */}
            <FontAwesomeIcon
                icon={faChevronRight}
                className={`text-md ${currentPage < totalPages ? 'hover:text-primary hover:cursor-pointer' : 'opacity-50'}`}
                onClick={handleNextPage}
                aria-label="Go to next page"
            />

            {/* Last page button */}
            <FontAwesomeIcon
                icon={faForwardStep}
                className={`text-md ${currentPage < totalPages ? 'hover:text-primary hover:cursor-pointer' : 'opacity-50'}`}
                onClick={handleLastPage}
                aria-label="Go to last page"
            />

            {/* Page size input */}
            <div className="flex flex-row gap-x-1 items-center">
                <input
                    type="text"
                    value={currentPageSize}
                    onChange={handlePageSizeInputChange}
                    onKeyDown={handlePageSizeInputKeyDown}
                    onBlur={handlePageSizeInputBlur}
                    className="border border-primary border-gray-500 rounded h-8 w-12 pl-4 bg-inherit text-black dark:text-white placeholder:text-gray-500 text-sm"
                    aria-label="Items per page"
                />
                <span className="text-sm ml-1">/ page</span>
            </div>
        </div>
    );
};

// export default PageNavigator;



export { DefaultNavigator, PageNavigator };