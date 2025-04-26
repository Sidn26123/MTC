import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faAngleLeft,
    faAngleRight,
    faAnglesLeft,
    faAnglesRight,
    faBell,
    faChevronRight,
    faX
} from "@fortawesome/free-solid-svg-icons";

function MyBookShelfPage() {
    const [tab, setTab] = React.useState(1);


    return (
        <>
            <div className={"flex flex-col"}>
                <div className={"px-20 mt-5"}>
                    {/*Quang cao*/}
                    <div className={"bg-gray-700 min-h-[120px]"}>

                    </div>
                </div>

                <div className={"flex flex-row justify-between items-center pt-5 px-20"}>
                    <div className={"flex flex-row justify-left items-center"}>
                        <div className={"background-color-lighter mr-1 px-2 rounded-md hover:cursor-pointer select-none"} onClick={(e)=>setTab(0)}>Truyện
                            đang đọc
                        </div>
                        <div className={"background-color-lighter px-2 rounded-md hover:cursor-pointer select-none"} onClick={(e)=>setTab(1)}>Truyện đánh
                            dấu
                        </div>

                    </div>
                </div>
                <div className={"min-h-[500px] px-20"}>
                    {tab === 0 ? <ReadingNovels/> : <BookmarkNovels/>}
                </div>
            </div>

        </>
    )
}

export default MyBookShelfPage;

function ReadingNovels() {
    return (
        <>
            <div>
                <div className={""}>
                    <div className={""}>
                        <BookShelfNovelCard/>
                    </div>
                    <div className={"flex justify-center mt-2"}>
                        <NavigationBar />

                    </div>
                </div>
            </div>
        </>
    )

}

function NavigationBar() {
    return (
        <>
            <div>
                <div className={"flex w-[120[x] gap-x-2 items-center"}>

                    <FontAwesomeIcon icon={faAnglesLeft} className={"yellow-text-color"}/>
                    <FontAwesomeIcon icon={faAngleLeft} className={"yellow-text-color"}/>
                    <div className={"flex items-center text-sm mx-2 p-1 border-1 hover:border-yellow-500 rounded-md"}>
                        <input type={"number"} defaultValue={1} className={"w-[40px] focus:outline-none px-1"}/>
                        <span className={"text-gray-500 pr-1"}>/3</span>
                    </div>
                    <FontAwesomeIcon icon={faAngleRight} className={"yellow-text-color"}/>
                    <FontAwesomeIcon icon={faAnglesRight} className={"yellow-text-color"}/>
                </div>
            </div>
        </>
    )
}

function BookShelfNovelCard() {
    return (
        <>
            <div className={"relative overflow-x-auto shadow-md sm:rounded-lg select-none"}>
                <div className={"w-full flex flex-1 flex-row border-b border-gray-500 border-dotted items-center"}>
                    <div className={"w-1/10"}></div>
                    <div className={"w-7/10"}>
                        <div className={"flex flex-col"}>
                            <div className={"truncate"}>
                                Vo hiep truoc bat dau nhat cai hoang dung lam dau bep nu
                            </div>
                            <div className={"text-gray-500 text-sm my-2"}>
                                Đã đọc: 10/100
                            </div>
                        </div>
                    </div>
                    <div className={"w-1/10 text-gray-500 text-xs"}>
                        4 phút trước
                    </div>
                    <div className={"w-1/10 justify-end"}>
                        <FontAwesomeIcon icon={faX} className={"mr-3 hover:cursor-pointer"}/>
                        <FontAwesomeIcon icon={faBell} />
                    </div>
                </div>

            </div>
        </>
    )
}

function BookmarkNovels(){
    return (
        <>
            <div>
                <div className={""}>
                    <div className={""}>
                        <BookmarkNovelCard/>
                    </div>
                    <div className={"flex justify-center mt-2"}>
                        <NavigationBar />

                    </div>
                </div>
            </div>
        </>
    )
}

function BookmarkNovelCard(){
    return (
        <>
            <div>
                <div className={"flex flex-row border-b border-gray-500 border-dotted items-center"}>
                    <div className={"w-1/10"}></div>
                    <div className={"flex flex-col w-7/10"}>
                        <div>
                            Cửu Tinh Bá Thể Quyết
                        </div>
                        <div className={"text-gray-500 text-sm my-2"}>
                            Mới: Chương 6565
                        </div>
                    </div>
                    <div className={"w-1/10 text-xs text-gray-500"}>
                        10:10:20 19:02:2025
                    </div>
                    <div className={"w-1/10 text-right"}>
                        <FontAwesomeIcon icon={faX} className={"mr-3 ml-auto hover:cursor-pointer"}/>

                    </div>

                </div>
            </div>
        </>
    )
}