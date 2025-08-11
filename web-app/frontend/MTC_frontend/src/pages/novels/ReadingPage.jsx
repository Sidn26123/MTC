import React, { useEffect, useMemo, useState } from 'react';
import AdvertiseItem from "../../components/global/AdvertiseItem.jsx";
import {
    faBackward,
    faCircleChevronLeft,
    faCircleChevronRight, faFlag, faForward,
    faGear, faGift,
    faListUl, faStar, faTicket,
} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBookmark} from "@fortawesome/free-regular-svg-icons";
import ChapterListPanel from "../../components/novel/ChapterListPanel.jsx";
import UserComment from '../../components/feedbacks/UserComment.jsx';
import { Reply, UserReply } from '../../components/feedbacks/Reply.jsx';
import { ReviewPanel } from '../../components/feedbacks/RatingDetail.jsx';
import GiftPanel from '../../components/feedbacks/Gifts.jsx';
import { NovelReport, UserReport } from '../../components/feedbacks/Report.jsx';
import {
    useChapterActions,
    useChapterStore, useCurrentChapter,
    useCurrentChapterContent,
    useCurrentChapterIdx, useDecreaseChapterIdx, useIncreaseChapterIdx,
} from '../../stores/chapterStore.js';
import {
    checkCanReadChapter, checkCanReadChapterOrNovel, checkChapterReadable, getChapterByNovelSlugAndIdx,
    getChapterContentByChapterId,
    getCurrentChapterContent, navigateToChapter,
    startReadChapter,
} from '../../services/chapterService.js';
import { useLocation, useNavigate, useParams } from 'react-router';
import { useCurrentNovel, useCurrentNovelSlug } from '../../stores/novelStore.js';
import { getProfileById } from '../../services/userService.js';
import { getUserIdFromContext, isLoggedIn } from '../../services/authenticationService.js';
import ReaderConfigModal from '../../components/common/ReadingConfigModel.jsx';
import LockedChapterNotice from '../../components/payments/LockedChapterNotice.jsx';
import { useSetCurrentNovelReadingChapter } from '../../stores/bookshelfStore.js';
import { useMyWallet, useSetMyWallet, useUpdateWalletBalance } from '../../stores/paymentStore.js';
import { showError, showSuccess } from '../../utils/ToastUtils.js';
import { promotion } from '../../services/paymentService.js';



// const ReadingPage = () => {
//     const navigate = useNavigate();
//     const { slug, id } = useParams();
//     const curChapterContent = useCurrentChapterContent();
//     const currentChapter = useCurrentChapter();
//     const actions = useChapterActions();
//     let novelSlug = useCurrentNovelSlug();
//     const currentNovel = useCurrentNovel();
//     // let chapterIdx = useCurrentChapterIdx();
//     // const [chapterIdx, setChapterIdx] = useState(id.split('-')[1]);
//     const chapterIdx = useMemo(() => parseInt(id.split('-')[1]), [id]);
//     const [error, setError] = useState(null);
//     const [showModal, setShowModal] = useState(false);
//     const [canRead, setCanRead] = useState(null); // null = checking, true = can read, false = cannot read
//     const [purchased, setPurchased] = useState(true);
//     const setCurrentChapter = useSetCurrentNovelReadingChapter();
//
//
//     useEffect(() => {
//         console.log("Islogged: ", isLoggedIn());
//
//         let isMounted = true;
//         getChapterByNovelSlugAndIdx(slug, chapterIdx).then(r => {
//             if (!isMounted) return;
//             const chapterData = r.data.result;
//             console.log('Chapter Data:', chapterData);
//
//             actions.setCurrentChapter({...chapterData}); // Gọi hàm cập nhật Zustand
//
//             checkChapterReadable(chapterData.id).then((response) => {
//                 if (!isMounted) return;
//                 setCanRead(response.data.result);
//             });
//             // if (chapterData.amountToUnlock > 0 && )
//             if (chapterData.amountToUnlock > 0) {
//                 checkCanReadChapterOrNovel("novel", currentNovel.id).then((canReadResponse) => {
//                     if (!isMounted) return;
//                     setPurchased(canReadResponse.data.result || false);
//                     if (!canReadResponse.data.result){
//                         checkCanReadChapterOrNovel("chapter", chapterData.id).then((canReadResponse) => {
//                             if (!isMounted) return;
//                             setPurchased(canReadResponse.data.result || false);
//                         });
//                     }
//                 });
//
//             }
//
//         });
//
//         return () => {
//             isMounted = false;
//         };
//     }, [slug, chapterIdx]);
//
//
//     useEffect(() => {
//         console.log("b");
//         if (!currentChapter || purchased === false) return;
//
//         getCurrentChapterContent({
//             novelSlug: slug,
//             chapterIdx: chapterIdx,
//         }).then((r) => {
//             console.log('r.data:', r.data);
//             if (r.status === 400) {
//                 setError(r.data.message);
//                 return;
//             }
//             if (r.data.result.length === 0) {
//                 navigate('/404');
//                 return;
//             }
//             console.log("Chapter content fetched:");
//
//             // setChapterContent(r.data.result).then(r => {});
//
//             actions.setCurrentChapter({
//                 chapterId: r.data.result.id,
//                 content: r.data.result.content,
//             });
//
//             // setCurrentChapter({
//             //     ...currentChapter,
//             //     chapterId: r.data.result.id,
//             //     content: r.data.result.content
//             // })
//
//             // startRead logic dời vào đây
//             if (currentNovel?.id) {
//                 startReadChapter(currentNovel.id, {
//                     userId: getUserIdFromContext(),
//                     chapterId: r.data.result.id,
//                     chapterIdx: chapterIdx,
//                     novelId: currentNovel.id,
//                 }).then(r => {});
//             }
//         });
//     }, [currentChapter.id, chapterIdx, purchased]);
//
//     const sampleBook = { kind: 1 };
//     const sampleChapter = { id: 123 };
//
//     const handleReviewSubmit = (score) => {
//         console.log('Review Score Submitted:', score);
//     };
//
//     const handleTranslationSubmit = (score) => {
//         console.log('Translation Score Submitted:', score);
//     };
//
//     const [functionMode, setFunctionMode] = useState('none');
//     const [showReport, setShowReport] = useState(false);
//     function changeFunctionModel(mode) {
//         if (mode === functionMode) {
//             setFunctionMode('none');
//         } else {
//             setFunctionMode(mode);
//         }
//     }
//
//     function closeReport() {
//         setFunctionMode('none');
//         setShowReport(false);
//     }
//
//     function handleGoPreviousChapter() {
//         const newIdx = parseInt(chapterIdx) - 1;
//         navigate(`/truyen/${slug}/chuong-${newIdx}`);
//     }
//
//     function handleGoNextChapter() {
//         const newIdx = parseInt(chapterIdx) + 1;
//         navigate(`/truyen/${slug}/chuong-${newIdx}`);
//     }
//
//     // useEffect(() => {
//     //     if (!currentNovel || !currentChapter || !chapterIdx) return;
//     //
//     //     console.log('Current Novel:', currentNovel);
//     //
//     //     startReadChapter(currentNovel.id, {
//     //         userId: getUserIdFromContext(),
//     //         chapterId: currentChapter.chapterId,
//     //         chapterIdx: chapterIdx,
//     //         currentChapterIdx: chapterIdx,
//     //         novelId: currentNovel.id,
//     //     }).then(r => {
//     //         console.log('Start reading chapter response:', r);
//     //     });
//     // }, [currentNovel, currentChapter, chapterIdx]);
//
//     // Show loading while checking permission
//     if (canRead === null) {
//         return (
//             <div className="flex justify-center items-center h-64">
//                 <div>Đang kiểm tra quyền đọc...</div>
//             </div>
//         );
//     }
//
//     // Show ComponentA if user cannot read
//     if (canRead === false && purchased === false || canRead === true && purchased === false) {
//         return <LockedChapterNotice itemId={currentChapter.id} itemType="CHAPTER" amount = {currentChapter.amountToUnlock}/>;
//     }




const ReadingPage = () => {
    const navigate = useNavigate();
    const { slug, id } = useParams();

    const chapterIdx = useMemo(() => parseInt(id.split('-')[1]), [id]);

    const curChapterContent = useCurrentChapterContent();
    const currentChapter = useCurrentChapter();
    const actions = useChapterActions();
    const currentNovel = useCurrentNovel();

    const [error, setError] = useState(null);
    const [canRead, setCanRead] = useState(null);
    const [purchased, setPurchased] = useState(true);

    const [functionMode, setFunctionMode] = useState('none');
    const [showReport, setShowReport] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [readingChapterProgress, setReadingChapterProgress] = useState(0);
    const startTime = useMemo(() => new Date(), []);


    function calDurationInReading(){
        const endTime = new Date();
        const duration = Math.floor((endTime - startTime) / 1000); // tính bằng giây
        return duration;
    }
    // Hàm kiểm tra quyền đọc và mua
    const checkReadPermission = async (chapterData) => {
        const canReadRes = await checkChapterReadable(chapterData.id);
        setCanRead(canReadRes.data.result);

        if (chapterData.amountToUnlock > 0 && !isLoggedIn()){
            setPurchased(false);
            setError("Bạn cần đăng nhập để đọc chương này.");
            return;
        }

        if (chapterData.amountToUnlock > 0) {
            let purchasedRes = await checkCanReadChapterOrNovel("novel", currentNovel.id);
            if (!purchasedRes.data.result) {
                purchasedRes = await checkCanReadChapterOrNovel("chapter", chapterData.id);
            }
            setPurchased(purchasedRes.data.result || false);
        }
    };

    const handleNavigateNewChapter = async (isNext) => {
        var novelId = currentNovel?.id;
        var chapterId = currentChapter?.id;

        await navigateToChapter(novelId, chapterId, {
            currentChapterIdx: currentChapter?.chapterIdx,
            userId: getUserIdFromContext(),
            isNext: isNext,
            novelId: novelId,
            chapterId: chapterId,
            progress: readingChapterProgress,
            duration: calDurationInReading(),
        })
    }

    // Lấy dữ liệu chapter
    useEffect(() => {
        let ignore = false;
        const fetchChapter = async () => {
            try {
                const r = await getChapterByNovelSlugAndIdx(slug, chapterIdx);
                if (ignore) return;

                const chapterData = r.data.result;
                actions.setCurrentChapter({ ...chapterData });
                await checkReadPermission(chapterData);
            } catch (err) {
                console.error("Fetch chapter error:", err);
                if (!ignore) setError("Không thể tải chương.");
            }
        };
        fetchChapter();
        return () => { ignore = true; };
    }, [slug, chapterIdx]);

    // Lấy nội dung chapter
    useEffect(() => {
        if (!currentChapter?.id || purchased === false) return;

        let ignore = false;
        const fetchContent = async () => {
            try {
                const r = await getCurrentChapterContent({ novelSlug: slug, chapterIdx });
                if (ignore) return;

                if (r.status === 400) {
                    setError(r.data.message);
                    return;
                }
                if (!r.data.result || r.data.result.length === 0) {
                    navigate('/404');
                    return;
                }

                actions.setCurrentChapter({
                    chapterId: r.data.result.id,
                    content: r.data.result.content,
                });

                if (currentNovel?.id) {
                    await startReadChapter(currentNovel.id, {
                        userId: getUserIdFromContext(),
                        chapterId: r.data.result.id,
                        currentChapterIdx: currentChapter?.chapterIdx,
                        novelId: currentNovel.id,
                    });
                }
            } catch (err) {
                console.error("Fetch content error:", err);
            }
        };
        fetchContent();
        return () => { ignore = true; };
    }, [currentChapter.id, chapterIdx, purchased]);

    const changeFunctionModel = (mode) => {
        setFunctionMode(mode === functionMode ? 'none' : mode);
    };

    const closeReport = () => {
        setFunctionMode('none');
        setShowReport(false);
    };

    const handleGoPreviousChapter = () => {
        handleNavigateNewChapter(false);
        navigate(`/truyen/${slug}/chuong-${chapterIdx - 1}`);
    };

    const handleGoNextChapter = () => {
        handleNavigateNewChapter(true);
        navigate(`/truyen/${slug}/chuong-${chapterIdx + 1}`);
    };

    // Loading UI
    if (canRead === null) {
        return (
            <div className="flex justify-center items-center h-64">
                <div>Đang kiểm tra quyền đọc...</div>
            </div>
        );
    }

    // Lock UI
    if (!purchased) {
        return <LockedChapterNotice itemId={currentChapter.id} itemType="CHAPTER" amount={currentChapter.amountToUnlock} />;
    }

    // useEffect(() => {
    //     const handleKeyDown = (event) => {
    //         if (event.key === "ArrowLeft") {
    //             event.preventDefault();
    //             handleGoPreviousChapter();
    //         } else if (event.key === "ArrowRight") {
    //             event.preventDefault();
    //             handleGoNextChapter();
    //         }
    //     };
    //
    //     window.addEventListener("keydown", handleKeyDown);
    //     return () => window.removeEventListener("keydown", handleKeyDown);
    // }, [slug, chapterIdx]); // mỗi lần đổi chương, re-attach listener


    return (
        <>
            {canRead}
            {currentNovel && (
                <div>
                    <AdvertiseItem />
                    <div className={'mx-2 mt-4'}>
                        <h2 className={'text-lg text-center text-balance'} onClick={() => navigate(`/truyen/${currentNovel.slug}`)}>
                            <div className="text-title font-semibold">
                                {currentNovel.name}
                            </div>
                        </h2>
                        <h3 className="text-xs text-center text-gray-500">
                            {' '}
                            {currentNovel.author &&
                                currentNovel.author.name}{' '}
                        </h3>
                        <div className="flex justify-center space-x-2 items-center px-2 mt-4">
                            <div
                                data-x-bind="GoPrevious"
                                className="flex items-center justify-end text-title hover:cursor-pointer"
                                onClick={() => handleGoPreviousChapter()}
                                data-x-ref="previousId"
                            >
                                <div className="w-6 h-6 text-primary yellow-text-color">
                                    <FontAwesomeIcon
                                        icon={faCircleChevronLeft}
                                    />
                                </div>
                            </div>
                            <div>
                                <h2 className="text-center text-gray-600 dark:text-gray-400 text-balance">
                                    {' '}
                                    {currentChapter.name}{' '}
                                </h2>
                            </div>
                            <div
                                className="flex items-center justify-start text-title hover:cursor-pointer"
                                data-x-ref="nextId"
                                onClick={() => handleGoNextChapter()}
                            >
                                <div className="w-6 h-6 text-primary yellow-text-color">
                                    <FontAwesomeIcon
                                        icon={faCircleChevronRight}
                                    />
                                </div>
                            </div>
                        </div>
                        <div
                            className={
                                'mt-4 flex flex-row justify-center gap-x-3'
                            }
                        >
                            <div
                                className={
                                    'border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px]'
                                }
                            >
                                <div className={'px-3'}>
                                    <FontAwesomeIcon
                                        icon={faGear}
                                        className={'mr-2'}
                                    />
                                    <span>Cài đặt</span>
                                </div>
                            </div>
                            <div
                                className={
                                    'border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px] flex justify-center items-center'
                                }
                            >
                                <FontAwesomeIcon
                                    icon={faGear}
                                    className={'px-1'}
                                />
                            </div>
                            <div
                                className={
                                    'border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px]'
                                }
                            >
                                <div className={'px-3'}>
                                    <FontAwesomeIcon
                                        icon={faBookmark}
                                        className={'mr-2'}
                                    />
                                    <span>Đánh dấu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    {error && (
                        <>
                            <div
                                className={
                                    'flex items-center justify-center m-5 text-warning'
                                }
                            >
                                {error}
                            </div>
                        </>
                    )}
                    {!error && (
                        <>
                            {/*<ChapterListPanel />*/}
                            <span className={"font-bold font-palatino text-[24px]"}>
                                                            {currentChapter.name}

                            </span>
                            <br />
                            <br />
                            {curChapterContent !== undefined && (
                                <Content content={curChapterContent} />
                            )}
                            {/*    */}
                            {/*Thong bao cua truyen*/}
                            <div className={'pt-4'}>
                                <NotificationOfNovel />
                            </div>
                            <div>
                                <NovelAdvertise />
                                <AdvertiseItem />
                            </div>
                            <div>
                                <FunctionBar changeMode={changeFunctionModel} />
                            </div>
                            <div className={'mt-5'}>
                                {/*Review Panel*/}
                                {functionMode === 'review' && (
                                    <div>
                                        <ReviewPanel
                                            book={sampleBook}
                                            chapter={sampleChapter}
                                            onSubmitReview={handleReviewSubmit}
                                            onSubmitTranslation={
                                                handleTranslationSubmit
                                            }
                                        />
                                    </div>
                                )}
                                {/*Gift Panel*/}
                                {/*{functionMode === 'gift' && (*/}
                                {/*    <div>*/}
                                {/*        <GiftPanel*/}
                                {/*            bookId={1}*/}
                                {/*            chapterId={123}*/}
                                {/*            user={sampleUser}*/}
                                {/*            donations={sampleDonations}*/}
                                {/*            onDonate={handleDonate}*/}
                                {/*        />*/}
                                {/*    </div>*/}
                                {/*)}*/}
                                {functionMode === 'report' && (
                                    <div className={'relative'}>
                                        <NovelReport
                                            onClose={() =>
                                                setFunctionMode('review')
                                            }
                                        />
                                    </div>
                                )}
                                <div className={'mt-5'}>
                                    <CommentBox />
                                </div>
                            </div>
                            <div>{/*<UserComment comment={comment}*/}</div>
                            {showReport && (
                                <div>
                                    <UserReport onClose={() => closeReport()} />
                                </div>
                            )}
                        </>
                    )}
                    <ReaderConfigModal isOpen={showModal} onClose={() => setShowModal(false)}/>
                </div>
            )}
        </>
    );
};
export default ReadingPage;

const NotificationOfNovel = () => {
    return (
        <>
            <div>
                <div className="border border-primary border-dotted p-4 text-lg mx-2 lg:mx-0 rounded">
                    <span className="font-bold">Thông báo: </span> ĐỀ CỬ
                    TRUYỆN:1. ANH LINH THỜI ĐẠI, THẬP LIÊN GIỮ GỐC (Sắp Hoàn
                    Thành)2. Ta Dựa Vào Chiều Dài Tu Tiên (Còn Tiếp)3. Kỹ Năng
                    Của Ta Có Đặc Hiệu (Đã Hoàn Thành)
                </div>
            </div>
        </>
    );
};

const NovelAdvertise = () => {
    return (
        <>
            <div>
                <div className="space-y-4">
                    <p data-x-html="textlink" className="break-words">
                        -----
                        <br />
                        <br />
                        Một triều mộng tỉnh, Hứa Khinh Chu xuyên qua huyền huyễn
                        thế giới, thức tỉnh giải ưu hệ thống, từ đó ngày đi một
                        thiện, đạp vào một đầu thay thế nhân giải ưu tiêu sầu
                        trường sinh lộ.
                        <br />
                        "Truyện hay, thể loại nhẹ nhàng, mời đọc{' '}
                        <a
                            href="https://metruyencv.com/truyen/thinh-tien-sinh-cuu-ta"
                            target="_blank"
                            className="font-bold text-primary"
                        >
                            Thỉnh Tiên Sinh Cứu Ta
                        </a>
                    </p>
                    <p data-x-html="textad" className="break-words"></p>
                </div>
            </div>
        </>
    );
};

const FunctionBar = ({ changeMode }) => {
    // const ItemArray = new Array()
    const navigate = useNavigate();

    const wallets = useMyWallet();
    const setWallets = useSetMyWallet();
    const updateWallet = useUpdateWalletBalance();
    function handleGoNextChapter() {
        let nextChapterIdx = parseInt(useCurrentChapterIdx()) + 1;
        // navigate('truyen')
    }

    function handleGoPreviousChapter() {
    }


    // function handlePromotion() {
    //     console.log("A");
    //     console.log(wallets);
    //     wallets.map((wallet => {
    //         if (wallet.currency.code === "XU"){
    //             if (wallet.balance < 1){
    //                 showError('Bạn không đủ xu để đề cử');
    //             }
    //             else{
    //                 promotion().then(r => {})
    //             }
    //         }
    //     }));
    // }

    function handlePromotion() {
        const xuWallet = wallets.find(wallet => wallet.currency.code === "XUK");

        if (!xuWallet) {
            showError("Không tìm thấy ví XUK");
            return;
        }

        if (xuWallet.balance < 1) {
            showError("Bạn không đủ xu để đề cử");
            return;
        }

        promotion()
            .then(() => {
                showSuccess("Đề cử thành công");

                // ✅ Gợi ý: cập nhật balance ở local UI nếu muốn phản hồi nhanh
                updateWallet("XUK", -1);

                // Hoặc: Gọi lại API get wallets để sync lại state chính xác
            })
            .catch((err) => {
                console.log("Error during promotion:", err);
                showError("Có lỗi xảy ra khi đề cử");
            });
    }

    return (
        <>
            <div>
                <div
                    className={
                        'grid grid-flow-col justify-stretch w-full border border-auto py-4 px-2 divide-x divide-auto space-x-4 border-gray-500 yellow-text-color'
                    }
                >
                    <div className={'space-y-2'} onClick={handleGoPreviousChapter}>
                        <div className={'hover:text-yellow-500'}>
                            <span
                                className={'flex justify-center items-center'}
                            >
                                <FontAwesomeIcon icon={faBackward} />
                            </span>
                            <span>Chương trước</span>
                        </div>
                    </div>
                    <button
                        className={'space-y-2 hover:text-yellow-500'}
                        onClick={() => changeMode('review')}
                    >
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faStar} />
                        </span>
                        <span>Đánh giá</span>
                    </button>

                    <button
                        className={'space-y-2 hover:text-yellow-500'}
                        onClick={() => changeMode('report')}
                    >
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faFlag} />
                        </span>
                        <span>Báo cáo</span>
                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'} onClick={handlePromotion}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faTicket} />
                        </span>
                        <span>Đề cử</span>
                    </button>
                    <div className={'space-y-2 hover:text-yellow-500'} onClick={handleGoNextChapter}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faForward} />
                        </span>
                        <span>Chương sau</span>
                    </div>
                </div>
            </div>
        </>
    );
};

const CommentBox = () => {
    const [content, setContent] = useState('');
    const [sort, setSort] = useState('-sticky,-id');

    return (
        <div className="mb-6">
            <div className="py-2 px-4 mb-4 bg-white rounded-lg rounded-t-lg border border-gray-200 dark:bg-black dark:border-gray-700">
                <textarea
                    rows="6"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    className="px-0 w-full text-gray-900 border-0 focus:ring-0 focus:outline-none dark:text-white dark:placeholder-gray-400 dark:bg-black"
                    placeholder="Thảo luận ..."
                    required
                    style={{
                        overflow: 'hidden',
                        overflowWrap: 'break-word',
                        resize: 'none',
                        textAlign: 'start',
                        height: '160px',
                    }}
                ></textarea>
            </div>
            <div className="flex items-center justify-between">
                <div className="relative inline-block text-left">
                    <select
                        value={sort}
                        onChange={(e) => setSort(e.target.value)}
                        className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 text-black sm:text-sm rounded-md dark:bg-black dark:text-white"
                    >
                        <option value="-sticky,commentable_id,-id">
                            Liên quan
                        </option>
                        <option value="-sticky,-id">Mới nhất</option>
                        <option value="-sticky,-like_count">Lượt thích</option>
                        <option value="-sticky,id">Cũ nhất</option>
                    </select>
                </div>
                <div className="font-bold"> thảo luận</div>
                <button className="inline-flex justify-center px-4 py-2 border border-primary shadow-sm text-sm font-medium rounded-md text-primary bg-inherit focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 btn-outline-primary w-16 h-9 disabled:bg-gray-500">
                    GỬI
                </button>
            </div>
        </div>
    );
};

const Content = ({ content }) => {
    const lines = content.replace(/\\n/g, '\n').split('\n').filter(line => line.trim() !== '');
    return (
        <>
            {/*{lines.map((line, index) => (*/}
            {/*    <div className={"leading-relaxed"}>*/}

            {/*    </div>*/}
            {/*    <React.Fragment key={index}>*/}
            {/*        {line}*/}
            {/*        <br />*/}
            {/*        <br />*/}
            {/*    </React.Fragment>*/}
            {/*))}*/}
            {lines.map((line, index) => (
                <div key={index} className="mb-4 text-lg font-palatino text-[20px] leading-relaxed">
                    {line}
                </div>
            ))}
        </>
    );
};