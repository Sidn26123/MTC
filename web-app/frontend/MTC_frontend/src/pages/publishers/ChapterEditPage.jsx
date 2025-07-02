import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router';

const ChapterEditPage = () => {
    return (
        <>
            <div>
                <div>
                    <div className={'flex flex-col'}>
                        <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                            <h2>Truyen Dau Tien</h2>
                            <span>Chuong 1 So</span>
                            <div className={"flex flex-col gap-y-2 mt-5"}>
                                <span>STT</span>
                                <input
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>
                            <div className={"mt-5"}>
                                <textarea
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3 min-h-48 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>

                            <div className={"flex flex-col gap-y-2 mt-5"}>
                                <span className={"text-sm text-gray-500"}>STT</span>
                                <input
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>
                            <div>
                                <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
                                    <div className={"w-1/2"}>
                                        <button
                                            className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}

                                        >
                                            <Link to={"/bookhub/books/1/chapters"}>
                                                <div className={"flex justify-center items-center gap-x-2"}>
                                                    <span className={"ml-2"}>Nhập Lại</span>
                                                    <FontAwesomeIcon icon={faDeleteLeft} />
                                                </div>
                                            </Link>
                                        </button>
                                    </div>
                                    <div className={'w-1/2'}>
                                        <button
                                            className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}

                                        >
                                            <Link to={"/bookhub/books/1/chapters"}>
                                                <div className={"flex justify-center items-center gap-x-2"}>
                                                    <span className={"ml-2"}>Đăng Chương</span>
                                                    <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                </div>
                                            </Link>

                                        </button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ChapterEditPage;