import React, { useState } from 'react';
import { useEffect, useRef } from "react";
import DateTimePicker from 'react-datetime-picker';
const FullScreenWrapper = () => {
    return (
        <>

        </>
    )
}


const SimpleDropdown = ({ dropdown }) => {
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useOutsideClick(() => setIsOpen(false)); // Hook đóng dropdown khi click ngoài

    return (
        <div ref={dropdownRef} className={"w-full relative"}>
            <button
                id="dropdownRadioButton"
                onClick={() => setIsOpen(!isOpen)}
                className="inline-flex w-full justify-between items-center text-gray-500 border border-gray-500 font-medium rounded-lg text-sm px-3 py-1.5 focus:outline-none hover:ring-1 focus:ring-1 focus:ring-gray-500 "
                type="button"
            >
                <span>Last 30 days</span>
                <svg
                    className="w-2.5 h-2.5 ms-2.5"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 10 6"
                >
                    <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="m1 1 4 4 4-4"
                    />
                </svg>
            </button>

            {isOpen && (
                <div className={"w-full"}>
                    <div
                        id="dropdownRadio"
                        className="z-10 absolute divide-y rounded-lg shadow-sm bg-cus-gray mt-2"
                        data-popper-reference-hidden=""
                        data-popper-escaped=""
                        data-popper-placement="top"
                    >
                        <ul className="space-y-1 text-sm text-gray-700" aria-labelledby="dropdownRadioButton">
                            <li>
                                <div className="flex items-center p-2 rounded-sm hover:bg-gray-600">
                                    <input
                                        id="filter-radio-example-1"
                                        type="radio"
                                        value=""
                                        name="filter-radio"
                                        className="w-4 hidden h-4 text-blue-600 bg-gray-100 border-gray-300"
                                    />
                                    <label
                                        htmlFor="filter-radio-example-1"
                                        className="w-full ms-2 text-sm font-medium text-gray-900 rounded-sm dark:text-gray-300 mr-4"
                                    >
                                        Last day
                                    </label>

                                </div>
                            </li>
                            <li>
                                <div className="flex items-center p-2 rounded-sm hover:bg-gray-600">
                                    <input
                                        id="filter-radio-example-1"
                                        type="radio"
                                        value=""
                                        name="filter-radio"
                                        className="w-4 hidden h-4 text-blue-600 bg-gray-100 border-gray-300"
                                    />
                                    <label
                                        htmlFor="filter-radio-example-1"
                                        className="w-full ms-2 text-sm font-medium text-gray-900 rounded-sm dark:text-gray-300"
                                    >
                                        Last day
                                    </label>

                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

            )}
        </div>
    );
};


const CategoryDropdown = ({ dropdown }) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedItem, setSelectedItem] = useState(null); // Thêm state cho item được chọn
    const dropdownRef = useOutsideClick(() => setIsOpen(false));

    const handleSelect = (item) => {
        setSelectedItem(item);
        setIsOpen(false);
    };

    return (
        <div ref={dropdownRef} className="w-full relative">
            <button
                id="dropdownRadioButton"
                onClick={() => setIsOpen(!isOpen)}
                className="inline-flex w-full justify-between items-center text-gray-500 border border-gray-500 font-medium rounded-lg text-sm px-3 py-1.5 focus:outline-none hover:ring-1 focus:ring-1 focus:ring-gray-500"
                type="button"
            >
                <span>{selectedItem ? selectedItem.name : "Chọn thể loại"}</span>
                <svg
                    className="w-2.5 h-2.5 ms-2.5"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 10 6"
                >
                    <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="m1 1 4 4 4-4"
                    />
                </svg>
            </button>

            {isOpen && (
                <div className="w-full ">
                    <div
                        id="dropdownRadio"
                        className="z-10 absolute divide-y rounded-lg shadow-sm bg-cus-gray mt-2 "
                    >
                        <ul className="space-y-1 text-sm text-gray-700" aria-labelledby="dropdownRadioButton">
                            {dropdown.map((item) => (
                                <li key={item.id}>
                                    <div
                                        className="flex items-center p-2 rounded-sm hover:bg-gray-600 cursor-pointer"
                                        onClick={() => handleSelect(item)}
                                    >
                                        <input
                                            type="radio"
                                            checked={selectedItem?.id === item.id}
                                            readOnly
                                            className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 hidden"
                                        />
                                        <label className="w-full ms-2 text-sm font-medium text-gray-900 dark:text-gray-300 mr-4">
                                            {item.name}
                                        </label>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            )}
        </div>
    );
};


const BorderedBox = ({ children }) => {
    return (
        <div className={"border border-gray-300 rounded-lg p-4"}>
            {children}
        </div>
    )
}

const useOutsideClick = (callback) => {
    const ref = useRef();

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (ref.current && !ref.current.contains(event.target)) {
                callback();
            }
        };
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [callback]);

    return ref;
};

const DateTimePicker1 = () => {

    return (
        <>
            <div>
                <div className={""}>
                    <form className="max-w-[8rem] mx-auto">
                        <label htmlFor="time" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Select
                            time:</label>
                        <div className="relative border-b border-gray-500">
                            <div
                                className="absolute inset-y-0 end-0 top-0 flex items-center pe-3.5 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                     xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
                                    <path fillRule="evenodd"
                                          d="M2 12C2 6.477 6.477 2 12 2s10 4.477 10 10-4.477 10-10 10S2 17.523 2 12Zm11-4a1 1 0 1 0-2 0v4a1 1 0 0 0 .293.707l3 3a1 1 0 0 0 1.414-1.414L13 11.586V8Z"
                                          clipRule="evenodd" />
                                </svg>
                            </div>
                            <input type="time" id="time"
                                   className="bg-gray-50 border leading-none border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   min="09:00" max="18:00" value="00:00" required />
                        </div>
                        <div>

                            <div className="relative max-w-sm">
                                <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                    <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                         xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                        <path
                                            d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
                                    </svg>
                                </div>
                                <input id="datepicker-actions" datepicker datepicker-buttons datepicker-autoselect-today
                                       type="text"
                                       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                       placeholder="Select date" />
                            </div>

                        </div>
                    </form>

                </div>

            </div>
        </>
    )
}


const CustomDatePicker = ({value, onChange}) =>{
    // const [value, onChange] = useState(new Date());

    return (
        <>
            <div className={"p-5"}>
                <DateTimePicker onChange={onChange} value={value} />

            </div>
        </>
    )
}
const FormattedContent = ({ content }) => {
    if (!content) return null; // Kiểm tra nếu content không tồn tại
    const lines = content.split(/[\n|\\n]+/).filter(line => line.trim() !== '');
    return (
        <>
            {lines.map((line, index) => (
                <React.Fragment key={index}>
                    {line}
                    <br />
                    <br />
                </React.Fragment>
            ))}
        </>
    );
};

export { FullScreenWrapper, SimpleDropdown, useOutsideClick, CustomDatePicker, CategoryDropdown, FormattedContent };