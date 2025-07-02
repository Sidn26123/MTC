import React from "react";
import {Link} from "react-router";

function JustReadNovel() {
    return (
        <>
            <div className={"flex flex-col gap-y-5"}>
                <div className={"flex flex-row justify-between items-center pt-5"}>
                    <span>TRUYỆN VỪA ĐỌC</span>
                    <Link to={"/#"}>--</Link>
                </div>
                {/*Data table*/}
                <div className="relative overflow-x-auto shadow-md sm:rounded-lg select-none">
                    <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                        <tbody>
                        <tr className="border-b dark:border-gray-700 border-gray-200">
                            <th scope="row"
                                className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                Một giờ trước
                            </th>
                            <td className="px-6 py-4 w-4/8 hover:text-yellow-500 hover:cursor-pointer">
                                Tên truyện
                            </td>
                            <td className="px-6 py-4 w-1/8">
                                Đã đọc
                            </td>

                            <td className="px-6 py-4 text-right w-1/8">
                                <a href="#"
                                   className="font-medium text-blue-600 dark:text-blue-500 hover:underline">X</a>
                            </td>
                        </tr>
                        <tr className="border-b dark:border-gray-700 border-gray-200">
                            <th scope="row"
                                className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                Một giờ trước
                            </th>
                            <td className="px-6 py-4 w-4/8">
                                Tên truyện
                            </td>
                            <td className="px-6 py-4 w-1/8">
                                Đã đọc
                            </td>

                            <td className="px-6 py-4 text-right w-1/8">
                                <a href="#"
                                   className="font-medium text-blue-600 dark:text-blue-500 hover:underline">X</a>
                            </td>
                        </tr>
                        <tr className="border-b dark:border-gray-700 border-gray-200">
                            <th scope="row"
                                className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                Một giờ trước
                            </th>
                            <td className="px-6 py-4 w-4/8">
                                Tên truyện
                            </td>
                            <td className="px-6 py-4 w-1/8">
                                Đã đọc
                            </td>

                            <td className="px-6 py-4 text-right w-1/8">
                                <a href="#"
                                   className="font-medium text-blue-600 dark:text-blue-500 hover:underline">X</a>
                            </td>
                        </tr>
                        <tr className="border-b dark:border-gray-700 border-gray-200">
                            <th scope="row"
                                className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                Một giờ trước
                            </th>
                            <td className="px-6 py-4 w-4/8">
                                Tên truyện
                            </td>
                            <td className="px-6 py-4 w-1/8">
                                Đã đọc
                            </td>

                            <td className="px-6 py-4 text-right w-1/8">
                                <a href="#"
                                   className="font-medium text-blue-600 dark:text-blue-500 hover:underline">X</a>
                            </td>
                        </tr>
                        <tr className="border-b dark:border-gray-700 border-gray-200">
                            <th scope="row"
                                className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                Một giờ trước
                            </th>
                            <td className="px-6 py-4 w-4/8">
                                Tên truyện
                            </td>
                            <td className="px-6 py-4 w-1/8">
                                Đã đọc
                            </td>

                            <td className="px-6 py-4 text-right w-1/8">
                                <a href="#"
                                   className="font-medium text-blue-600 dark:text-blue-500 hover:underline">X</a>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default JustReadNovel;