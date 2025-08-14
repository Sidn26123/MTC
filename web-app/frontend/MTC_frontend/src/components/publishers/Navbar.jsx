import { Link, useNavigate } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
import useUserStore from '../../stores/userStores.js';
import useUIStore, { useToggleNovelFilterPanel } from '../../stores/UIStore.js';
import { useSetNovelStatus } from '../../stores/selectors/novelFilterSelector.js';
import { useSetListNovel } from '../../stores/novelStore.js';
import { useMyWallet } from '../../stores/paymentStore.js';
import { useSetUnreadCount, useUnreadCount } from '../../stores/notificationStore.js';
import { useHasScope } from '../../services/authoriazationService.js';
import { logOut } from '../../services/authenticationService.js';
import { showSuccess } from '../../utils/ToastUtils.js';

const Navbar = () => {
    const navigate = useNavigate();
    const [isDropdownOpen, setDropdownOpen] = useState(false);
    const [showFilter, setShowFilter] = useState(false);
    const [showLoginPanel, setShowLoginPanel] = useState(0);
    const user = useUserStore((state) => state.user);
    const setUser = useUserStore((state) => state.setUser);
    const myWallet = useMyWallet();
    const canViewAdmin = useHasScope("ROLE_ADMIN");
    const unreadCount = useUnreadCount();
    const setUnreadCount = useSetUnreadCount();
    const handleLogout = (event)=> {
        event.preventDefault();
        setUser(null);
        logOut();
        showSuccess("Đăng xuất thành công");
    }
    return (
        <>
            <div>
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
                                <img
                                    className="w-8 h-8 rounded-full"
                                    src="http://localhost:8889/api/v1/file/files/media/download/3557eebf-d947-4716-88ac-2ad9b7295a88.png"
                                    alt="user"
                                />
                            </>
                        ) : (
                            <>
                                <img
                                    className="w-8 h-8 rounded-full"
                                    alt="user"
                                />
                            </>
                        )}
                    </button>

                    {/* Dropdown Menu (Mở rộng sang trái) */}
                    {/*{isDropdownOpen && (*/}
                    {/*    <div*/}

                    {/*        className="absolute z-10 bg-gray-800 -left-[160px] top-full mt-2 w-48 rounded-md shadow-lg origin-top-left">*/}
                    {isDropdownOpen && (
                        <div
                            className="absolute z-20 -left-[200px] top-full mt-2 w-56 origin-top-right rounded-xl bg-white dark:bg-gray-800 shadow-lg ring-1 ring-gray-200 dark:ring-gray-700 transition-all duration-200">
                            {user.username ? (
                                <div className="px-4 py-3">
                                    <div
                                        className={
                                            'flex flex-row items-center space-x-2'
                                        }
                                    >
                                        {user.avatarPath ? (
                                            <>
                                                <img
                                                    className="w-10 h-10 rounded-full"
                                                    src="http://localhost:8889/api/v1/file/files/media/download/3557eebf-d947-4716-88ac-2ad9b7295a88.png"
                                                    alt="user"
                                                />
                                            </>
                                        ) : (
                                            <>
                                                <img
                                                    className="w-10 h-10 rounded-full"
                                                    alt="user"
                                                />
                                            </>
                                        )}
                                        <div
                                            className={
                                                'flex flex-col items-start'
                                            }
                                        >
                                            <Link to="/profile">
                                                    <span className="block text-sm text-gray-900 dark:text-white">
                                                        {user.username}
                                                    </span>
                                            </Link>
                                            <div
                                                className={
                                                    'flex flex-row py-2'
                                                }
                                            >
                                                    <span
                                                        className="block text-sm text-gray-500 truncate dark:text-gray-400">
                                                        Cấp {user.level}
                                                    </span>
                                                <Link
                                                    to={'/thong-bao'}
                                                    title="Xem tất cả thông báo"
                                                    className="bg-red-700 inline-flex items-center justify-center w-6 h-6 ms-2 text-xs font-semibold text-white rounded-full"
                                                    data-x-text="$store.account.userData.unread_notifications_count"
                                                >
                                                    {unreadCount}
                                                </Link>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ) : (
                                <>
                                    <ul className="py-2">
                                        <li>
                                            <a
                                                className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400"
                                                onClick={() =>
                                                    setShowLoginPanel(1)
                                                }
                                            >
                                                Đăng nhập
                                            </a>
                                        </li>

                                        <li>
                                            <a
                                                onClick={() =>
                                                    setShowLoginPanel(2)
                                                }
                                                className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400"
                                            >
                                                Đăng ký
                                            </a>
                                        </li>
                                    </ul>
                                </>
                            )}

                            <ul className="py-2 list-disc pl-4">
                                {!canViewAdmin && (
                                    <li>
                                        <Link
                                            to={'/admin/dashboard'}
                                            className="block px-4 py-2 text-gray-700 hover:bg-gray-400"
                                        >
                                            Admin
                                        </Link>
                                    </li>
                                )}
                                {/*<div className={'px-8'}>*/}
                                {/*    <li>*/}
                                {/*        <Link*/}
                                {/*            to={'/nang-cap-tai-khoan'}*/}
                                {/*            className="block px-4 py-2 text-gray-700 hover:bg-gray-400 text-sm"*/}
                                {/*        >*/}
                                {/*            Nâng cấp tài khoản*/}
                                {/*        </Link>*/}
                                {/*    </li>*/}
                                {/*    <li>*/}
                                {/*        <Link*/}
                                {/*            to={'/tu-truyen'}*/}
                                {/*            className="block px-4 py-2 text-gray-700 hover:bg-gray-400 text-sm"*/}
                                {/*        >*/}
                                {/*            Tủ truyện*/}
                                {/*        </Link>*/}
                                {/*    </li>*/}
                                {/*    <li>*/}
                                {/*        <Link*/}
                                {/*            to={'/lich-su-giao-dich'}*/}
                                {/*            className="block px-4 py-2 text-gray-700 hover:bg-gray-400 text-sm"*/}
                                {/*        >*/}
                                {/*            Lịch sử giao dịch*/}
                                {/*        </Link>*/}
                                {/*    </li>*/}
                                {/*    <li>*/}
                                {/*        <Link*/}
                                {/*            to={'/cai-dat'}*/}
                                {/*            className="block px-4 py-2 text-gray-700 hover:bg-gray-400 text-sm"*/}
                                {/*        >*/}
                                {/*            Cài đặt*/}
                                {/*        </Link>*/}
                                {/*    </li>*/}
                                {/*    <li>*/}
                                {/*        <Link*/}
                                {/*            to={'/yeu-cau-ho-tro'}*/}
                                {/*            className="block px-4 py-2 text-gray-700 hover:bg-gray-400 text-sm"*/}
                                {/*        >*/}
                                {/*            Yêu cầu hỗ trợ*/}
                                {/*        </Link>*/}
                                {/*    </li>*/}
                                {/*</div>*/}

                                <ul className="px-8 list-disc pl-[10px] marker:mr-[4px] text-sm text-gray-200 space-y-1">
                                    {/*<li className="ml-3 border-b-1 border-gray-500/25 py-[3px]">*/}
                                    {/*    <Link*/}
                                    {/*        to="/nang-cap-tai-khoan"*/}
                                    {/*        className="block"*/}
                                    {/*    >*/}
                                    {/*        Nâng cấp tài khoản*/}
                                    {/*    </Link>*/}
                                    {/*</li>*/}
                                    <li className="ml-3 border-b-1 border-gray-500/25 py-[3px]">
                                        <Link
                                            to="/tu-truyen"
                                            className="block"
                                        >
                                            Tủ truyện
                                        </Link>
                                    </li>
                                    <li className="ml-3 border-b-1 border-gray-500/25 py-[3px]">
                                        <Link
                                            to="/lich-su-giao-dich"
                                            className="block"
                                        >
                                            Lịch sử giao dịch
                                        </Link>
                                    </li>
                                    <li className="ml-3 border-b-1 border-gray-500/25 py-[3px]">
                                        <Link
                                            to="/cai-dat"
                                            className="block"
                                        >
                                            Cài đặt
                                        </Link>
                                    </li>
                                    <li className="ml-3 py-[3px]">
                                        {' '}
                                        {/* dòng cuối không có border */}
                                        <Link
                                            to="/yeu-cau-ho-tro"
                                            className="block"
                                        >
                                            Yêu cầu hỗ trợ
                                        </Link>
                                    </li>
                                </ul>

                                <div className="flex flex-col list-none">
                                    <div>
                                        <li className="block px-4 py-2 text-gray-200 hover:bg-gray-400">Túi</li>
                                    </div>

                                    <div className="flex flex-col w-full px-4">
                                        <div className="flex flex-col justify-between space-x-6">
                                            {/* Xu */}
                                            <li className="flex items-center space-x-2">
                                                <FontAwesomeIcon icon={faCoins} />
                                                <span>:</span>
                                                <span>
                                                      {(myWallet?.find(w => w.currency.code === "XU")?.balance ?? 0).toLocaleString()}
                                                    </span>
                                            </li>

                                            {/* Xu khoá */}
                                            <li className="flex items-center space-x-2">
                                                <svg
                                                    className="w-5 h-5 text-primary"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                    viewBox="0 0 24 24"
                                                    fill="currentColor"
                                                >
                                                    <path
                                                        fillRule="evenodd"
                                                        d="M1.5 6.375c0-1.036.84-1.875 1.875-1.875h17.25c1.035 0 1.875.84 1.875 1.875v3.026a.75.75 0 0 1-.375.65 2.249 2.249 0 0 0 0 3.898.75.75 0 0 1 .375.65v3.026c0 1.035-.84 1.875-1.875 1.875H3.375A1.875 1.875 0 0 1 1.5 17.625v-3.026a.75.75 0 0 1 .374-.65 2.249 2.249 0 0 0 0-3.898.75.75 0 0 1-.374-.65V6.375Zm15-1.125a.75.75 0 0 1 .75.75v.75a.75.75 0 0 1-1.5 0V6a.75.75 0 0 1 .75-.75Zm.75 4.5a.75.75 0 0 0-1.5 0v.75a.75.75 0 0 0 1.5 0v-.75Zm-.75 3a.75.75 0 0 1 .75.75v.75a.75.75 0 0 1-1.5 0v-.75a.75.75 0 0 1 .75-.75Zm.75 4.5a.75.75 0 0 0-1.5 0V18a.75.75 0 0 0 1.5 0v-.75ZM6 12a.75.75 0 0 1 .75-.75H12a.75.75 0 0 1 0 1.5H6.75A.75.75 0 0 1 6 12Zm.75 2.25a.75.75 0 0 0 0 1.5h3a.75.75 0 0 0 0-1.5h-3Z"
                                                        clipRule="evenodd"
                                                    ></path>
                                                </svg>
                                                <span>:</span>
                                                <span>
                                                      {myWallet?.find(w => w.currency.code === "XUK")?.balance ?? 0}
                                                    </span>
                                            </li>
                                        </div>
                                    </div>
                                </div>
                                <li className={'list-none'}>
                                    <Link
                                        to={'/nap-tien'}
                                        className="block px-4 py-2 text-gray-700 hover:bg-gray-400"
                                    >
                                        Nạp
                                    </Link>
                                </li>

                                <li className={'list-none'}>
                                        <span
                                            onClick={() => navigate('/chatbot')}
                                            className="block px-4 py-2 text-gray-700 hover:bg-gray-400"
                                        >
                                            Chatbot
                                        </span>
                                </li>
                                {user.username && (
                                    <li className={'list-none'}>
                                        <a
                                            onClick={(event) =>
                                                handleLogout(event)
                                            }
                                            className="block px-4 py-2 text-gray-700 hover:cursor-pointer hover:bg-gray-400"
                                        >
                                            Đăng xuất
                                        </a>
                                    </li>
                                )}
                            </ul>
                        </div>
                    )}
                </div>

            </div>
        </>
    )
}

export default Navbar;