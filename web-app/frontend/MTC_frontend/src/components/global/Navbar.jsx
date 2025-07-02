import { useEffect, useState } from 'react';
import "../../styles/styles.css";
import {Link} from "react-router";
import { NovelFilter } from './NovelFilter.jsx';
import LoginPanel from './LoginPanel.jsx';
import useUserStore from '../../stores/userStores.js';
import { showSuccess } from '../../utils/ToastUtils.js';
import { logOut } from '../../services/authenticationService.js';
import { toast } from 'react-toastify';
import RegisterPage from '../../pages/users/RegisterPage.jsx';
import RegisterPanel from './RegisterPanel.jsx';
import { useHasScope } from '../../services/authoriazationService.js';
import useUIStore, { useToggleNovelFilterPanel } from '../../stores/UIStore.js';
import { getNovelProgressStatus } from '../../services/novelFilterService.js';
import { useSetNovelStatus } from '../../stores/selectors/novelFilterSelector.js';
import { getNovels } from '../../services/novelService.js';
import { useSetListNovel } from '../../stores/novelStore.js';

function Navbar() {
    const [isDropdownOpen, setDropdownOpen] = useState(false);
    const [showFilter, setShowFilter] = useState(false);
    const [showLoginPanel, setShowLoginPanel] = useState(0);
    const user = useUserStore((state) => state.user);
    const setUser = useUserStore((state) => state.setUser);
    const useIsNovelFilterPanelOpen = useUIStore((state) => state.isNovelFilterPanelOpen);
    const toggleNovelFilterPanel = useToggleNovelFilterPanel();
    const setNovelStatus = useSetNovelStatus();
    const setListNovel = useSetListNovel();

    const handleLogout = (event)=> {
        event.preventDefault();
        setUser(null);
        logOut();
        showSuccess("Đăng xuất thành công");
    }
    const canViewAdmin = useHasScope("ROLE_ADMIN");

    const openChat = useUIStore((state) => state.openChat);

    // useEffect(() => {
    //     // setNovelStatus(getNovelProgressStatus());
    //
    // }, []);
    useEffect(() => {
        const fetchData = async () => {
            const result = await getNovelProgressStatus(); // Giả sử gọi API
            const novel = await getNovels();
            setListNovel(novel.data.result); // Gọi action của Zustand
        };

        fetchData().then(r => {});
    }, []);
    // getNovelProgressStatus().then(r => console.log("Novel progress status: ", r.data.result));
    return (

        <>
            <nav className="border-gray-200 background-color">

                <div className="flex flex-wrap items-center justify-between mx-auto p-1">
                    <Link to="/" className="flex items-center rtl:space-x-reverse">
                        <img src="https://flowbite.com/docs/images/logo.svg" className="h-8" alt="Flowbite Logo"/>
                        <span
                            className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Metruyenchu</span>
                    </Link>

                    {/*Search bar*/}
                    <div className="flex">
                        <Link to = "/truyen" data-collapse-toggle="navbar-search" aria-controls="navbar-search"
                                aria-expanded="false"
                                className=" text-gray-500 dark:text-gray-400 hover:bg-gray-100 rounded-lg text-sm p-2.5 me-1">
                            <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 20 20">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"
                                      strokeWidth="2"
                                      d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                            <span className="sr-only">Search</span>
                        </Link>
                        <div className="relative hidden md:block">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                     xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"
                                          strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                                <span className="sr-only">Search icon</span>
                            </div>
                            <input type="text" id="search-navbar"
                                   className="block w-full p-2   ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   placeholder="Search..."/>
                        </div>
                        <button data-collapse-toggle="navbar-search" type="button"
                                className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg hover:bg-gray-100 focus:outline-none focus:ring-1 focus:ring-gray-200 "
                                aria-controls="navbar-search" aria-expanded="false"
                                // onClick={() => setShowFilter(true)}
                                onClick={() => toggleNovelFilterPanel()}
                        >
                            <span className="sr-only">Open main menu</span>
                            <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 17 14">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"
                                      strokeWidth="2"
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
                            {user.avatarPath ? (
                                <>
                                    <img className="w-8 h-8 rounded-full"
                                         src="http://localhost:8889/api/v1/file/files/media/download/3557eebf-d947-4716-88ac-2ad9b7295a88.png"
                                         alt="user" />
                                </>
                            ) : (
                                <>
                                    <img className="w-8 h-8 rounded-full"

                                         alt="user" /></>
                            )}

                        </button>

                        {/* Dropdown Menu (Mở rộng sang trái) */}
                        {isDropdownOpen && (
                            <div

                                className="absolute z-10 bg-gray-800 -left-[160px] top-full mt-2 w-48 rounded-md shadow-lg origin-top-left">
                                {user.username ? (
                                    <div className="px-4 py-3">
                                        <Link to="/profile">
                                            <span
                                                className="block text-sm text-gray-900 dark:text-white">{user.username}</span>
                                        </Link>

                                        <span
                                            className="block text-sm text-gray-500 truncate dark:text-gray-400">Cấp {user.level}</span>
                                    </div>
                                ):
                                (
                                   <>
                                       <ul className="py-2">
                                           <li>
                                               <a
                                                      className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400"
                                                    onClick={() => setShowLoginPanel(1)}>Đăng nhập</a></li>

                                           <li>
                                               <a
                                                   onClick={() => setShowLoginPanel(2)}
                                                  className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400">Đăng ký</a></li>
                                       </ul>
                                   </>
                                )}

                                <ul className="py-2">
                                    {canViewAdmin && (
                                        <li><Link to={'/admin/dashboard'}
                                                  className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Admin</Link>
                                        </li>
                                    )}

                                    <li><Link to={'/nang-cap-tai-khoan'}
                                              className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Nâng cấp tài
                                        khoản</Link>
                                    </li>
                                    <li><Link to={'/tu-truyen'}
                                              className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Tủ
                                        truyện</Link>
                                    </li>
                                    <li><Link to={'/lich-su-giao-dich'}
                                              className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Lịch sử giao
                                        dịch</Link></li>
                                    <li><Link to={'/cai-dat'}
                                              className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Cài đặt</Link>
                                    </li>
                                    <div className="flex flex-col ">
                                        <div>
                                            <li className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Túi</li>
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
                                    <li><Link to={'/nap-tien'}
                                              className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Nạp</Link>
                                    </li>
                                    <li><Link to = {'/bookhub/dashboard'} className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Đăng
                                        truyện</Link>
                                    </li>
                                    <li><span onClick = {() => {openChat(); setDropdownOpen(false)}} className="block px-4 py-2 text-gray-700 hover:bg-gray-400">Chatbot</span>
                                    </li>
                                    {user.username && (
                                        <li><a
                                            onClick={(event) => handleLogout(event)}
                                            className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400">Đăng
                                            xuất</a></li>
                                    )}

                                </ul>
                            </div>
                        )}
                    </div>
                </div>
                <div>
                {/*{showFilter && (*/}
                    {/*    <NovelFilter onClose={() => setShowFilter(false)}/>*/}

                    {/*)}*/}
                    {showLoginPanel === 1 && (
                        <LoginPanel onClose={() => setShowLoginPanel(0)}/>
                    )}
                    {showLoginPanel === 2 && (
                        <RegisterPanel onClose={() => setShowLoginPanel(0)} isRegister={true}/>
                    )}
                    {useIsNovelFilterPanelOpen && (
                        <NovelFilter onClose={() => toggleNovelFilterPanel()}/>
                    )}

                </div>
                {/*<div>*/}
                {/*<NovelFilter /> */}
                {/*    <div*/}
                {/*        className={"bg-gray-50/50 transition-opacity z-40 fixed top-0 left-0 w-full h-full outline-none overflow-x-hidden"}>*/}
                {/*        <div className={"h-full max-w-screen-lg mx-auto relative w-auto pointer-events-none"}>*/}
                {/*            <div className={"min-h-screen max-h-full overflow-hidden border-none shadow-lg relative flex flex-col w-full pointer-events-auto bg-clip-padding bg-gray-50  outline-none"}>*/}
                {/*                /!*<NovelFilter />*!/*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</div>*/}
            </nav>

        </>

);
}

export default Navbar;
