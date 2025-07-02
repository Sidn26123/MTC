import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDeleteLeft, faPlus } from '@fortawesome/free-solid-svg-icons';
import {faPenToSquare, faTrashCan} from '@fortawesome/free-regular-svg-icons';
import { SimpleDropdown } from '../../common/CommonComponents.jsx';
import { DefaultNavigator } from '../../components/global/Navigators.jsx';
import { Link } from 'react-router';

const ChapterList = () => {
    return (
        <>
            <div>
                <div className={'flex flex-col'}>
                    <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                        {/*Head part*/}
                        <div className={"flex flex-row justify-between items-center"}>
                            <div className={"flex flex-col"}>
                                <span className={"text-lg"}>Danh sach chuong</span>
                                <span className={"text-gray-500"}>Truyen dau tien</span>
                            </div>
                            <div className={"flex flex-row gap-x-2"}>
                                <Link to={'/bookhub/books/1/update'}>
                                    <button
                                        className={'bg-cus-gray w-full text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}

                                    >
                                        <div className={'flex justify-center items-center gap-x-2'}>
                                            <span className={'min-w-20'}>Sửa</span>
                                        </div>
                                    </button>
                                </Link>

                                <Link to={"/bookhub/books/1/upload-chapters"}>
                                    <button
                                        className={'bg-cus-gray w-full text-white rounded-md p-2 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}

                                    >
                                        <div className={'flex justify-center items-center gap-x-2'}>
                                            <span className={'min-w-20'}>Thêm</span>
                                        </div>
                                    </button>
                                </Link>
                            </div>
                        </div>

                        {/*Search navigator*/}
                        <div className={"mt-4"}>
                            <div
                                className="flex flex-column sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4 ">
                                <div className={"flex flex-row gap-x-2"}>
                                    <SimpleDropdown />
                                </div>


                                <label htmlFor="table-search" className="sr-only">Search</label>
                                <div className="relative">
                                    <div
                                        className="absolute inset-y-0 left-0 rtl:inset-r-0 rtl:right-0 flex items-center ps-3 pointer-events-none">
                                        <svg className="w-5 h-5 text-gray-500 " aria-hidden="true"
                                             fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                            <path fillRule="evenodd"
                                                  d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                                  clipRule="evenodd"></path>
                                        </svg>
                                    </div>
                                    <input type="text" id="table-search"
                                           className="block p-2 ps-10 text-sm text-gray-400 border border-gray-500 rounded-lg w-80 "
                                           placeholder="Search for items" />
                                </div>
                            </div>

                        </div>
                        {/*Table part*/}
                        <div>
                            <table className="w-full text-sm text-left ">
                                <thead
                                    className="text-sm text-gray-500  border-b">
                                <tr>

                                    <th scope="col" className="px-6 py-3">
                                        STT
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        TEN CHUONG
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        XUAT BAN LUC
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        SO TU
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        LƯỢT ĐỌC
                                    </th>
                                    <th scope="col" className="px-6 py-3">

                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr className=" border-b border-gray-500 hover:bg-gray-500/10 ">

                                    <th scope="row"
                                        className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        Apple MacBook Pro 17"
                                    </th>
                                    <td className="px-6 py-4">
                                        Silver
                                    </td>
                                    <td className="px-6 py-4">
                                        Laptop
                                    </td>
                                    <td className="px-6 py-4">
                                        <SpecItem />
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                            <div className={"mt-3"}>
                                <DefaultNavigator />
                            </div>
                        </div>

                    </div>

                </div>
            </div>
        </>
    )
}

export default ChapterList;


const SpecItem = () => {
    return (
        <>
            <div>
                <div className={"flex flex-row justify-end items-center gap-x-1"}>
                    <div className={"bg-gray-300 p-1 rounded-md px-2 ml-3 pl-[10px] hover:cursor-pointer hover:bg-gray-400"}>
                        <Link to={"/bookhub/chapters/1/edit"}>
                            <FontAwesomeIcon icon={faPenToSquare} />
                        </Link>
                    </div>
                    <div className={"bg-red-500 p-1 rounded-md px-2 ml-3 hover:cursor-pointer hover:bg-red-600"}>
                        <Link to={"/bookhub/books/1/upload-chapters"}>
                            <FontAwesomeIcon icon={faTrashCan} />
                        </Link>
                    </div>
                </div>
            </div>
        </>
    )
}