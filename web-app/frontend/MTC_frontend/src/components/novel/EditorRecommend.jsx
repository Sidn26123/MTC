import React from "react";
import EditorRecommendNovelCard from "./EditorRecommendNovelCard.jsx";
import {faCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function EditorRecommend() {


    return (
        <>
            <div className={"flex flex-col"}>
                <div className={"flex justify-between flex-wrap mt-2"}>
                    <EditorRecommendNovelCard/>
                    <EditorRecommendNovelCard/>
                    <EditorRecommendNovelCard/>
                    <EditorRecommendNovelCard/>


                </div>
                <div className={"flex flex-row justify-center flex-wrap mt-2"}>
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="10" cy="10" r="5" fill="#FFD700"/>
                    </svg>
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="10" cy="10" r="5" fill="#FFD700"/>
                    </svg>
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="10" cy="10" r="5" fill="#FFD700"/>
                    </svg>
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="10" cy="10" r="5" fill="#FFD700"/>
                    </svg>
                </div>
            </div>

        </>
    );
}

export default EditorRecommend;