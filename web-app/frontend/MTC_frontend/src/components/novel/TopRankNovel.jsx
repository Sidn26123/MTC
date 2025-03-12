import React from "react";
import {faLayerGroup, faUser} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function TopRankNovel() {
    return (
        <>
            <div className={"w-full rounded-md p-2"}>
                <NovelDetailCard/>
                <NovelCard/>
                <NovelCard isBol={1}/>
                <NovelCard/>
            </div>
        </>
    );
}

export default TopRankNovel;

function NovelDetailCard({isBol}){
    return (
        <>
            <div className={"flex flex-row border-b border-gray-500 border-dotted pb-2"}>
                <div className={"w-1/10 mr-2"}>
                    <RankNumber rank={1} />
                </div>
                <div className={"flex flex-col w-6/10 gap-y-1"}>
                    <span className={"font-bold text-lg"}>
                        Tran Hoi Truong Sinh
                    </span>
                    <span className={"text-xs"}>
                        200 nguoi dang doc
                    </span>
                    <div className={"flex flex-row items-center mt-4 text-gray-400 text-xs"}>
                        <FontAwesomeIcon icon={faUser} className={"mr-2"}/>
                        <span>
                            Nguyen Van A
                        </span>
                    </div>
                    <div className={"flex flex-row items-center text-gray-400"}>
                        <FontAwesomeIcon icon={faLayerGroup} className={"mr-2"}/>
                        <span>
                            Do thi
                        </span>

                    </div>
                </div>
                <div className={"w-3/10"}>
                </div>
            </div>
        </>
    )
}

function NovelCard(){
    return (
        <>
            <div>
                <div className={`flex flex-1 flex-row border-b border-gray-500 border-dotted py-2 items-center`}>
                    <div className = "w-1/10 text-sm" >
                        <RankNumber rank={2} />

                    </div>
                    <span className={"w-8/10 truncate"}>Muaiskakdsjksakdjsad</span>
                    <span className={"w-1/10"}>192</span>
                </div>
            </div>
        </>
    )
}

function RankNumber({ rank }) {
    let rankLabel;
    if (rank === 1) {
        rankLabel = "1st";
    } else if (rank === 2) {
        rankLabel = "2nd";
    } else if (rank === 3) {
        rankLabel = "3rd";
    } else {
        rankLabel = `${rank}th`;
    }

    return <div className={""}>{rankLabel}</div>;
}
