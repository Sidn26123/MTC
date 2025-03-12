import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router";


function EditorRecommendNovelCard() {
    return (
        <>
            <div className={"flex flex-1 min-w-[400px] bg-gray-100 p-2 rounded-md background-color-lighter ml-2 mb-2 "}>
                <div className={"flex w-1/5 "}>
                    <img src="https://flowbite.com/docs/images/logo.svg" className="h-8" alt="Flowbite Logo"/>
                </div>
                <div className={"flex flex-wrap w-4/5 flex-col"}>
                    <div className={"font-bold"}>
                        Trận Hỏi Trường Sinh
                    </div>

                    <div className={"text-sm py-2"}>
                        Hu
                    </div>
                    <div className={"flex flex-row justify-between"}>
                        <div className={"flex flex-row"}>
                            <span className={"pr-2"}><FontAwesomeIcon icon={faUser} /></span>
                            <span className={"text-bold"}>Nguyen Van A</span>
                        </div>
                        <div>
                            <Link to={"/truyen/tran-hoi-truong-sinh"} className={"text-blue-500"}>
                                <div className={"text-sm font-normal text-yellow-500 border-1 border-yellow-500 rounded-md px-1 py-0.5"}>
                                    Đô thị
                                </div>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default EditorRecommendNovelCard;