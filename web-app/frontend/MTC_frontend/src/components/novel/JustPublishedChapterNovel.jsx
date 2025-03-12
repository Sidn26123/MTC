
import React from "react";
import {Link} from "react-router";

function JustPublishedChapterNovel() {
    return (
        <>
            <div>
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
                                    className="pl-6 py-4 w-1/10 font-medium whitespace-nowrap base-text-color truncate">
                                    <div className="w-20 overflow-hidden whitespace-nowrap truncate">
                                        Đây là một đoạn văn bản rất dài và sẽ bị cắt ngắn...
                                    </div>
                                </th>
                                <td className="px-1 py-4 w-5/10 hover:text-yellow-500 hover:cursor-pointer">
                                    Tên truyện
                                </td>
                                <td className="px-1 py-4 w-2/10">
                                    Tac gia
                                </td>
                                <td className="px-1 py-4 w-1/10">
                                    Chuong 2278
                                </td>
                                <td className="px-1 py-4 w-/10">
                                    2 phut truoc
                                </td>
                            </tr>
                            <tr className="border-b dark:border-gray-700 border-gray-200">
                                <th scope="row"
                                    className="pl-6 py-4 w-1/10 font-medium whitespace-nowrap base-text-color truncate">
                                    <div className="w-20 overflow-hidden whitespace-nowrap truncate">
                                        Đây là một đoạn văn bản rất dài và sẽ bị cắt ngắn...
                                    </div>
                                </th>
                                <td className="px-1 py-4 w-4/10 hover:text-yellow-500 hover:cursor-pointer">
                                    Tên truyện
                                </td>
                                <td className="px-1 py-4 w-1/10">
                                    Tac gia
                                </td>
                                <td className="px-1 py-4 w-1/10">
                                    Chuong 2278
                                </td>
                                <td className="px-1 py-4 w-/10">
                                    2 phut truoc
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </>
    );
}

export default JustPublishedChapterNovel;