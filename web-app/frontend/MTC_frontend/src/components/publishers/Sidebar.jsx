import React from 'react';
import { Link } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBug, faChartLine, faLifeRing, faPenToSquare, faSwatchbook } from '@fortawesome/free-solid-svg-icons';

const publisherRoutesPrefix = "/bookhub";


const Sidebar = () => {

    const [choseMode, setChoseMode] = React.useState(-1);

    return (
        <div className="flex flex-col justify-start min-h-screen background-color-lighter ">
            <div className="flex items-center w-full p-4 mb-2">
                <Link to= {`${publisherRoutesPrefix}/dashboard`} className="flex items-center rtl:space-x-reverse">
                    <img src="https://flowbite.com/docs/images/logo.svg" className="h-8" alt="Flowbite Logo" />

                </Link>
                <span
                    className="self-center text-2xl ml-2 font-semibold whitespace-nowrap dark:text-white">Metruyenchu</span>
                {/*<i className="v-icon v-theme--dark v-icon--size-default v-icon--clickable header-action d-none nav-unpin tabler-circle"*/}
                {/*   role="button" aria-hidden="false" style={{ display: 'none' }}></i>*/}
                {/*<i className="v-icon v-theme--dark v-icon--size-default v-icon--clickable header-action d-none nav-pin d-lg-block tabler-circle-dot"*/}
                {/*   role="button" aria-hidden="false"></i>*/}
                {/*<i className="v-icon v-theme--dark v-icon--size-default v-icon--clickable header-action d-lg-none tabler-x"*/}
                {/*   role="button" aria-hidden="false"></i>*/}
            </div>
            <div className={"px-4"}>
                {/*<div className={" flex flex-col items-start justify-start w-full text-gray-500 gap-y-2"}>*/}
                {/*    <span className={"text-sm font-medium mb-1"}>TRUYỆN CỦA TÔI</span>*/}
                {/*    <div className={'flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md'}>*/}
                {/*        <Link to= {`${publisherRoutesPrefix}/published`} className="block w-full">*/}

                {/*        <div className={"flex p-2 w-full"}>*/}
                {/*                <FontAwesomeIcon icon={faSwatchbook} className={"mr-2 text-gray-300"} />*/}
                {/*                <span className={"text-gray-300 font-normal"}>Đã đăng</span>*/}
                {/*        </div>*/}
                {/*        </Link>*/}

                {/*    </div>*/}
                {/*    <div className={'flex flex-col items-start justify-start w-full hover:bg-gray-100/25 rounded-md'}>*/}
                {/*        <div className={"m-2"}>*/}
                {/*            <Link to= {`${publisherRoutesPrefix}/new`}>*/}
                {/*                <FontAwesomeIcon icon={faPenToSquare} className={"mr-2 text-gray-300"} />*/}
                {/*                <span className={"text-gray-300 font-normal"}>Thêm mới</span>*/}
                {/*            </Link>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*    <div className={'flex flex-col items-start justify-start w-full hover:bg-gray-100/25 rounded-md'}>*/}
                {/*        <div className={"m-2"}>*/}
                {/*            <Link to= {`${publisherRoutesPrefix}/analytic`}>*/}
                {/*                <FontAwesomeIcon icon={faChartLine} className={"mr-2 text-gray-300"} />*/}
                {/*                <span className={"text-gray-300 font-normal"}>Thống kê</span>*/}
                {/*            </Link>*/}
                {/*        </div>*/}
                {/*    </div>*/}

                {/*</div>*/}
                {/*<div className={" flex flex-col items-start justify-start w-full text-gray-500 gap-y-2 mt-8"}>*/}
                {/*    <span className={"text-sm font-medium mb-1"}>TRUYỆN CỦA TÔI</span>*/}
                {/*    <div className={'flex flex-col items-start justify-start w-full hover:bg-gray-100/25 rounded-md'}>*/}
                {/*        <div className={'m-2'}>*/}
                {/*            <Link to= {`${publisherRoutesPrefix}/bao-cao`}>*/}
                {/*                <FontAwesomeIcon icon={faBug} className={'mr-2 text-gray-300'} />*/}
                {/*                <span className={'text-gray-300 font-normal'}>Xử lý báo cáo</span>*/}
                {/*            </Link>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*    <div className={'flex flex-col items-start justify-start w-full hover:bg-gray-100/25 rounded-md'}>*/}
                {/*        <div className={'m-2'}>*/}
                {/*            <Link to= {`${publisherRoutesPrefix}/ho-tro`}>*/}
                {/*                <FontAwesomeIcon icon={faLifeRing} className={'mr-2 text-gray-300'} />*/}
                {/*                <span className={'text-gray-300 font-normal'}>Yêu cầu hỗ trợ</span>*/}
                {/*            </Link>*/}
                {/*        </div>*/}
                {/*    </div>*/}

                {/*</div>*/}
                <div className="flex flex-col items-start justify-start w-full text-gray-500 gap-y-2">
                    <span className="text-sm font-medium mb-1">TRUYỆN CỦA TÔI</span>

                    <div className="flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md">
                        <Link to={`${publisherRoutesPrefix}/published`} className="block w-full">
                            <div className="flex p-2 w-full">
                                <FontAwesomeIcon icon={faSwatchbook} className="mr-2 text-gray-300" />
                                <span className="text-gray-300 font-normal">Đã đăng</span>
                            </div>
                        </Link>
                    </div>

                    <div className="flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md">
                        <Link to={`${publisherRoutesPrefix}/new`} className="block w-full">
                            <div className="flex p-2 w-full">
                                <FontAwesomeIcon icon={faPenToSquare} className="mr-2 text-gray-300" />
                                <span className="text-gray-300 font-normal">Thêm mới</span>
                            </div>
                        </Link>
                    </div>

                    <div className="flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md">
                        <Link to={`${publisherRoutesPrefix}/analytic`} className="block w-full">
                            <div className="flex p-2 w-full">
                                <FontAwesomeIcon icon={faChartLine} className="mr-2 text-gray-300" />
                                <span className="text-gray-300 font-normal">Thống kê</span>
                            </div>
                        </Link>
                    </div>
                </div>

                <div className="flex flex-col items-start justify-start w-full text-gray-500 gap-y-2 mt-8">
                    <span className="text-sm font-medium mb-1">HỖ TRỢ & BÁO CÁO</span>

                    <div className="flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md">
                        <Link to={`${publisherRoutesPrefix}/bao-cao`} className="block w-full">
                            <div className="flex p-2 w-full">
                                <FontAwesomeIcon icon={faBug} className="mr-2 text-gray-300" />
                                <span className="text-gray-300 font-normal">Xử lý báo cáo</span>
                            </div>
                        </Link>
                    </div>

                    <div className="flex flex-col w-full items-start justify-start hover:bg-gray-100/25 rounded-md">
                        <Link to={`${publisherRoutesPrefix}/ho-tro`} className="block w-full">
                            <div className="flex p-2 w-full">
                                <FontAwesomeIcon icon={faLifeRing} className="mr-2 text-gray-300" />
                                <span className="text-gray-300 font-normal">Yêu cầu hỗ trợ</span>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>


        </div>
    );
};

export default Sidebar;
