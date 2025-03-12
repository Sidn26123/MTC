import {useState} from 'react'
import "../../styles/styles.css";
import {Link} from "react-router";

function Navbar() {
    const [isDropdownOpen, setDropdownOpen] = useState(false);

    return (

        <>
            <nav className="border-gray-200 background-color">

                <div className="flex flex-wrap items-center justify-between mx-auto p-1">
                    <Link to="/" className="flex items-center rtl:space-x-reverse">
                        <img src="https://flowbite.com/docs/images/logo.svg" className="h-8" alt="Flowbite Logo"/>
                        <span
                            className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Metruyenchu</span>
                    </Link>

                    <div className="flex">
                        <button type="button" data-collapse-toggle="navbar-search" aria-controls="navbar-search"
                                aria-expanded="false"
                                className="md:hidden text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 focus:outline-none focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 rounded-lg text-sm p-2.5 me-1">
                            <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 20 20">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                      stroke-width="2"
                                      d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                            <span className="sr-only">Search</span>
                        </button>
                        <div className="relative hidden md:block">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                     xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                          stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                                <span className="sr-only">Search icon</span>
                            </div>
                            <input type="text" id="search-navbar"
                                   className="block w-full p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   placeholder="Search..."/>
                        </div>
                        <button data-collapse-toggle="navbar-search" type="button"
                                className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
                                aria-controls="navbar-search" aria-expanded="false">
                            <span className="sr-only">Open main menu</span>
                            <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 17 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                      stroke-width="2"
                                      d="M1 1h15M1 7h15M1 13h15"/>
                            </svg>
                        </button>
                    </div>


                    {/* Container cha (relative) */}
                    <div className="relative">
                        {/* Button toggle dropdown */}
                        <button
                            type="button"
                            onClick={() => setDropdownOpen(!isDropdownOpen)}
                            className="flex text-sm bg-gray-800 rounded-full focus:ring-4 focus:ring-gray-300 dark:focus:ring-gray-600"
                        >
                            <span className="sr-only">Open user menu</span>
                            <img className="w-8 h-8 rounded-full" src="https://flowbite.com/docs/images/logo.svg"
                                 alt="user"/>
                        </button>

                        {/* Dropdown Menu (Mở rộng sang trái) */}
                        {isDropdownOpen && (
                            <div
                                className="absolute bg-gray-800 -left-30 top-full mt-2 w-48 rounded-md shadow-lg origin-top-left">
                                <div className="px-4 py-3">
                                    <Link to="/profile">
                                        <span className="block text-sm text-gray-900 dark:text-white">Sidn2612</span>
                                    </Link>

                                    <span
                                        className="block text-sm text-gray-500 truncate dark:text-gray-400">Cấp 1</span>
                                </div>
                                <ul className="py-2">
                                    <li><Link to={"/nang-cap-tai-khoan"}
                                           className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Nâng cấp tài khoản</Link>
                                    </li>
                                    <li><Link to={"/tu-truyen"}
                                           className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Tủ truyện</Link>
                                    </li>
                                    <li><Link to={"/lich-su-giao-dich"}
                                           className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Lịch sử giao dịch</Link></li>
                                    <li><Link to={"/cai-dat"}
                                           className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Cài đặt</Link></li>
                                    <div className="flex flex-col ">
                                        <div>
                                            <li className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Túi</li>
                                        </div>
                                        <div className="flex flex-col w-full px-4">
                                            <div className="flex flex-row justify-between">
                                                <li>A:</li>
                                                <li className="pr-10">B:</li>
                                            </div>
                                            <div className="flex flex-row justify-between">
                                                <li>C:</li>
                                                <li className="pr-10">D:</li>
                                            </div>
                                        </div>
                                    </div>
                                    <li><Link to={"/nap-tien"} className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Nạp</Link>
                                    </li>
                                    <li><a href="#" className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Đăng truyện</a>
                                    </li>
                                    <li><a href="#" className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Đăng xuất</a></li>
                                </ul>
                            </div>
                        )}
                    </div>
                </div>

            </nav>

        </>

    );
}

export default Navbar;
