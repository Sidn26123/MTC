import React, { useEffect, useState } from 'react';
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
    getChapterContentByChapterId,
    getCurrentChapterContent,
    setChapterContent,
} from '../../services/chapterService.js';
import { useLocation, useNavigate, useParams } from 'react-router';
import { useCurrentNovel, useCurrentNovelSlug } from '../../stores/novelStore.js';
import { getProfileById } from '../../services/userService.js';



const ReadingPage = () => {
    const navigate = useNavigate();
    const { slug, id } = useParams();
    const curChapterContent = useCurrentChapterContent();
    const currentChapter = useCurrentChapter();
    const actions = useChapterActions();
    let novelSlug = useCurrentNovelSlug();
    const currentNovel = useCurrentNovel();
    // let chapterIdx = useCurrentChapterIdx();
    let chapterIdx = id.split('-')[1];
    const [error, setError] = useState(null);

    useEffect(() => {
        getCurrentChapterContent({novelSlug: slug, chapterIdx: chapterIdx}).then((r) => {
            console.log(r);
            if (r.status === 400){
                setError(r.data.message);
                return
            }
            if (r.data.result.length === 0) {
                navigate("/404");
                return;
            }
            console.log("data: ", r.data);

            actions.setCurrentChapter({
                chapterId: "bca",
                content:  r.data.result.content
            });
        });
    }, [novelSlug, chapterIdx]);

    //Update lai novel neu khong co
    // useEffect(() => {
    //    if (currentNovel.id === undefined){
    //
    //     }
    // }, []);


    //sample data, loại bỏ sau khi chuyển qua zustand
    const sampleBook = { kind: 1 };
    const sampleChapter = { id: 123 };

    const handleReviewSubmit = (score) => {
        console.log("Review Score Submitted:", score);
    };

    const handleTranslationSubmit = (score) => {
        console.log("Translation Score Submitted:", score);
    };
    const sampleUser = { isLoggedIn: true, balance: 100000 };
    const sampleDonations = [
        {
            user: { name: "NVhung23" },
            amount: 5000,
            created_at: "2024-03-01T15:56",
            chapter: { name: "Chương 501: Hoàng cung thay đổi, thủ vọng giả buông xuống!" },
        },
        {
            user: { name: "Nhất Diệp" },
            amount: 5000,
            created_at: "2024-02-15T10:30",
            chapter: { name: "Chương 67: Lãnh chúa phòng nhỏ thăng cấp! (cầu đặt mua)" },
        },
    ];

    const handleDonate = (amount) => {
        console.log("Đã tặng:", amount);
    };

    // const comment = {
    //     onRemove: () => alert("Xóa bình luận"),
    //     onReport: () => setShowReport(true),
    //     onSticky: (status) => alert(status ? "Ghim bình luận" : "Gỡ ghim bình luận"),
    //     canSticky: false,
    //     isOwner: false,
    // }


    const [functionMode, setFunctionMode] = useState("none");
    const [showReport, setShowReport] = useState(false);
    function changeFunctionModel(mode) {
        if (mode === functionMode) {
            setFunctionMode("none");
        }
        else{
            setFunctionMode(mode);

        }
    }

    function test(){
        alert("Báo cáo bình luận");
    }

    function closeReport(){
        setFunctionMode("none");
        setShowReport(false);
    }

    // function handleGoNextChapter() {
    //     increaseChapterIdx();
    // }
    //
    // function handleGoPreviousChapter() {
    //     decreaseChapterIdx();
    // }

    return (
        <>
            <div>
                <AdvertiseItem/>
                <div className={"mx-2 mt-4"}>
                    <h2 className={"text-lg text-center text-balance"}>
                        <div className="text-title font-semibold" >
                            {currentNovel.name}</div>
                    </h2>
                    <h3 className="text-xs text-center text-gray-500"> {currentNovel.author.name} </h3>
                    <div className="flex justify-center space-x-2 items-center px-2 mt-4">
                        <div data-x-bind="GoPrevious"
                             className="flex items-center justify-end text-title hover:cursor-pointer"
                             // onClick={() => handleGoPreviousChapter()}
                             data-x-ref="previousId">
                            <div className="w-6 h-6 text-primary yellow-text-color">
                                <FontAwesomeIcon icon={faCircleChevronLeft}/>
                            </div>
                        </div>
                        <div><h2 className="text-center text-gray-600 dark:text-gray-400 text-balance"> Chương 1760: Huyết Võng biến mất </h2></div>
                        <div className="flex items-center justify-start text-title hover:cursor-pointer"
                             data-x-ref="nextId"
                            // onClick={() => handleGoNextChapter()}
                        >
                            <div className="w-6 h-6 text-primary yellow-text-color">
                                <FontAwesomeIcon icon={faCircleChevronRight}/>
                            </div>
                        </div>
                    </div>
                    <div className={"mt-4 flex flex-row justify-center gap-x-3"}>
                        <div
                            className={"border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px]"}>
                            <div className={"px-3"}>
                                <FontAwesomeIcon icon={faGear} className={"mr-2"}/>
                                <span>
                                    Cài đặt
                                </span>
                            </div>

                        </div>
                        <div
                            className={"border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px] flex justify-center items-center"}>

                            {/*<div className={"px-3"}>*/}
                            {/*    <FontAwesomeIcon icon={faListUl} className={"mr-2"}/>*/}
                            {/*    <span>*/}
                            {/*        Mục lục*/}
                            {/*    </span>*/}
                            {/*</div>*/}
                            {/*<svg className="inline animate-spin w-5 h-5"*/}
                            {/*     xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5"*/}
                            {/*     stroke="currentColor" aria-hidden="true" data-slot="icon" style={{display: 'true'}}>*/}
                            {/*    <path strokeLinecap="round" strokeLinejoin="round"*/}
                            {/*          d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"></path>*/}
                            {/*</svg>*/}
                            <FontAwesomeIcon icon={faGear} className={"px-1"}/>
                        </div>
                        <div
                            className={"border-1 border-gray-300 hover:text-yellow-500 hover:border-yellow-500 hover:cursor-pointer rounded-md p-[2px]"}>
                            <div className={"px-3"}>
                                <FontAwesomeIcon icon={faBookmark} className={"mr-2"}/>
                                <span>
                                    Đánh dấu
                                </span>
                            </div>

                        </div>
                    </div>
                </div>
                {error && (
                    <>
                        <div className={"flex items-center justify-center m-5 text-warning"}>
                            {error}

                        </div>
                    </>
                )}
                {!error && (
                    <>
                        {/*<ChapterListPanel />*/}
                        {curChapterContent !== undefined && (
                            <Content content={curChapterContent}/>

                        )
                        }
                        {/*    */}
                        {/*Thong bao cua truyen*/}
                        <div className={"pt-4"}>
                            <NotificationOfNovel />
                        </div>
                        <div>
                            <NovelAdvertise />
                            <AdvertiseItem />
                        </div>
                        <div>
                            <FunctionBar changeMode = {changeFunctionModel}/>
                        </div>
                        <div className={"mt-5"}>
                            {/*Review Panel*/}
                            {functionMode === "review" && (
                                <div>
                                    <ReviewPanel
                                        book={sampleBook}
                                        chapter={sampleChapter}
                                        onSubmitReview={handleReviewSubmit}
                                        onSubmitTranslation={handleTranslationSubmit}
                                    />
                                </div>
                            )}
                            {/*Gift Panel*/}
                            {functionMode === "gift" && (
                                <div>
                                    <GiftPanel
                                        bookId={1}
                                        chapterId={123}
                                        user={sampleUser}
                                        donations={sampleDonations}
                                        onDonate={handleDonate}
                                    />
                                </div>
                            )}
                            {functionMode === "report" && (
                                <div className={"relative"}>
                                    <NovelReport onClose={() => setFunctionMode("review")}/>
                                </div>
                            )}
                            <div className={'mt-5'}>
                                <CommentBox />
                            </div>
                        </div>

                        <div>
                            {/*<UserComment comment={comment}*/}



                        </div>
                        {showReport && (
                            <div>
                                <UserReport onClose={() => closeReport()} />
                            </div>
                        )
                        }
                    </>
                )}



            </div>
        </>
    )
}
export default ReadingPage;


const NotificationOfNovel = () => {
    return (
        <>
            <div>
                <div className="border border-primary border-dotted p-4 text-lg mx-2 lg:mx-0 rounded"><span
                    className="font-bold">Thông báo: </span> ĐỀ CỬ TRUYỆN:1. ANH LINH THỜI ĐẠI, THẬP LIÊN GIỮ GỐC (Sắp
                    Hoàn Thành)2. Ta Dựa Vào Chiều Dài Tu Tiên (Còn Tiếp)3. Kỹ Năng Của Ta Có Đặc Hiệu (Đã Hoàn Thành)
                </div>
            </div>
        </>
    )
}


const NovelAdvertise = () => {
    return (
        <>
            <div>
                <div className="space-y-4"><p
                    data-x-html="textlink" className="break-words">-----<br/>
                    <br/>
                    Một triều mộng tỉnh, Hứa Khinh Chu xuyên qua huyền huyễn thế giới, thức tỉnh giải ưu hệ thống, từ đó
                    ngày đi một thiện, đạp vào một đầu thay thế nhân giải ưu tiêu sầu trường sinh lộ.<br/>
                    "Truyện hay, thể loại nhẹ nhàng, mời đọc <a
                        href="https://metruyencv.com/truyen/thinh-tien-sinh-cuu-ta" target="_blank"
                        className="font-bold text-primary">Thỉnh Tiên Sinh Cứu Ta</a></p><p data-x-html="textad"
                                                                                            className="break-words"></p>
                </div>
            </div>
        </>
    )
}

const FunctionBar = ({changeMode}) => {
    // const ItemArray = new Array()

    return (
        <>
            <div>
                <div
                    className={"grid grid-flow-col justify-stretch w-full border border-auto py-4 px-2 divide-x divide-auto space-x-4 border-gray-500 yellow-text-color"}>
                    <button className={"space-y-2"}>
                        <div className={'hover:text-yellow-500'}>
                            <span className={'flex justify-center items-center'}>
                                <FontAwesomeIcon icon={faBackward} />
                            </span>
                            <span>Chương trước</span>
                        </div>

                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'} onClick={() => changeMode("review")}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faStar} />
                        </span>
                        <span>Đánh giá</span>
                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'} onClick={() => changeMode("gift")}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faGift} />
                        </span>
                        <span>Tặng quà</span>
                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'} onClick={() => changeMode("report")}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faFlag} />
                        </span>
                        <span>Báo cáo</span>
                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faTicket} />
                        </span>
                        <span>Đề cử</span>
                    </button>
                    <button className={'space-y-2 hover:text-yellow-500'}>
                        <span className={'flex justify-center items-center'}>
                            <FontAwesomeIcon icon={faForward} />
                        </span>
                        <span>Chương sau</span>
                    </button>
                </div>
            </div>
        </>
    )
}

const CommentBox = () => {
    const [content, setContent] = useState("");
    const [sort, setSort] = useState("-sticky,-id");

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
            style={{ overflow: "hidden", overflowWrap: "break-word", resize: "none", textAlign: "start", height: "160px" }}
        ></textarea>
            </div>
            <div className="flex items-center justify-between">
                <div className="relative inline-block text-left">
                    <select
                        value={sort}
                        onChange={(e) => setSort(e.target.value)}
                        className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 text-black sm:text-sm rounded-md dark:bg-black dark:text-white"
                    >
                        <option value="-sticky,commentable_id,-id">Liên quan</option>
                        <option value="-sticky,-id">Mới nhất</option>
                        <option value="-sticky,-like_count">Lượt thích</option>
                        <option value="-sticky,id">Cũ nhất</option>
                    </select>
                </div>
                <div className="font-bold">141 thảo luận</div>
                <button
                    className="inline-flex justify-center px-4 py-2 border border-primary shadow-sm text-sm font-medium rounded-md text-primary bg-inherit focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 btn-outline-primary w-16 h-9 disabled:bg-gray-500"
                >
                    GỬI
                </button>
            </div>
        </div>
    );
};

const Content = ({ content }) => {
    const lines = content.split(/[\n|\\n]+/).filter(line => line.trim() !== '');
    return (
        <>
            {lines.map((line, index) => (
                <React.Fragment key={index}>
                    {line}
                    <br />
                    <br />
                </React.Fragment>
            ))}
        </>
    );
};