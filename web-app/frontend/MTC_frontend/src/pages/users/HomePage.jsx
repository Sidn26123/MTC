import React from "react";
import {Link} from "react-router";
import JustReadNovel from "../../components/novel/JustReadNovel.jsx";
import EditorRecommend from "../../components/novel/EditorRecommend.jsx";
import JustPublishChapterNovel from "../../components/novel/JustPublishedChapterNovel.jsx";
import JustPublishedChapterNovel from "../../components/novel/JustPublishedChapterNovel.jsx";
import TopRankNovel from "../../components/novel/TopRankNovel.jsx";
import AdvertiseItem from '../../components/global/AdvertiseItem.jsx';
import JustFinishedNovelPanel from '../../components/novel/JustFinishedNovelPanel.jsx';
import ReviewList from '../../components/novel/ReviewList.jsx';
import ReaderConfigModal from '../../components/common/ReadingConfigModel.jsx';
function Home() {
    const [showModal, setShowModal] = React.useState(true);


    return (
        <>
            <div className={"mt-5"}>
                {/*Quang cao*/}
                {/*<div className={"bg-gray-700 min-h-[120px]"}>*/}
                {/*    <AdvertiseItem />*/}
                {/*</div>*/}
                <div>
                    <JustReadNovel />
                </div>
                <div>
                    <EditorRecommend />
                </div>
                <div className="flex flex-row flex-1 mt-2 justify-center">
                    <div className={"flex"}>
                        <TopRankNovel/>
                    </div>
                </div>
                <div>
                    {/*<JustPublishedChapterNovel/>*/}
                </div>
                <div>
                    <JustFinishedNovelPanel />
                </div>
                <div>
                    <ReviewList />
                </div>
            </div>

        </>
        )

}
export default Home;