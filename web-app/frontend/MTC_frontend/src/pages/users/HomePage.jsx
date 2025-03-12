import React from "react";
import {Link} from "react-router";
import JustReadNovel from "../../components/novel/JustReadNovel.jsx";
import EditorRecommend from "../../components/novel/EditorRecommend.jsx";
import JustPublishChapterNovel from "../../components/novel/JustPublishedChapterNovel.jsx";
import JustPublishedChapterNovel from "../../components/novel/JustPublishedChapterNovel.jsx";
import TopRankNovel from "../../components/novel/TopRankNovel.jsx";
function Home() {
    return (
        <>
            <div className={"px-20 mt-5"}>
                {/*Quang cao*/}
                <div className={"bg-gray-700 min-h-[120px]"}></div>
                <div>
                    <JustReadNovel />
                </div>
                <div>
                    <EditorRecommend />
                </div>
                <div className={"flex flex-row flex-1 mt-2"}>
                    <div className={"flex w-1/2 mr-2"}>
                        <TopRankNovel/>

                    </div>
                    <div className={"flex w-1/2"}>
                        <TopRankNovel/>

                    </div>
                </div>
                <div>
                    <JustPublishedChapterNovel/>
                </div>

            </div>

        </>
        )

}
export default Home;