import React from "react";
import { DefaultNavigator } from '../../components/global/Navigators.jsx';

function AnalyticsNovelPage() {
    return (
        <>
            <div>
                <div className={"flex flex-col gap-x-5 mt-3 rounded-md bg-background-light p-3 text-cus-gray"}>
                    <div>
                        <input
                            className={'w-full border border-gray-500 rounded-md p-2 pr-5 text-gray-200  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                            placeholder={"Tìm kiếm"}
                        />
                    </div>
                    <div className={"mt-3"}>
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
    );
}

export default AnalyticsNovelPage;