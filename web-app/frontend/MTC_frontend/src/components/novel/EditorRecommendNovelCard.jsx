import React, { useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router";
import { getNovelUrlWithSlug } from '../../utils/URLUtils.js';
import { getProfileById } from '../../services/userService.js';
import { findItemInFilterStore } from '../../services/novelFilterService.js';
import { getNovelBySlug } from '../../services/novelService.js';


function EditorRecommendNovelCard({novel}) {
    const [fullNovel, setFullNovel] = React.useState(novel);
    useEffect(() => {
        getNovelBySlug(novel.slug).then((response) => {
            // Do something with the novel info if needed
            setFullNovel(response.data.result);
        })
    }, []);



    return (
        <>
            <div className="flex space-x-3 pt-3 px-3">
                <div className="flex-shrink-0">
                    <Link
                        to={
                            getNovelUrlWithSlug(novel.slug)
                        }
                        title= {novel.name}
                        className="text-title font-semibold"
                    >
                        <img
                            className="h-32 w-24 shadow-xl rounded"
                            src= {novel.novelCoverImage}
                            alt={novel.name}
                            loading="lazy"
                        />
                    </Link>
                </div>
                <div className="space-y-2">
                    <div>
                        <Link
                            to= {"/truyen/" + novel.slug}
                            title="Quỷ Đạo Thần Thoại"
                            className="text-title font-semibold"
                        >
                            Quỷ Đạo Thần Thoại
                        </Link>
                    </div>
                    <div className="text-gray-500 text-overflow-multiple-lines break-all">
                        {' '}
                        <p className="text-sm">
                            {novel.description}
                        </p>
                    </div>
                    <div className="flex justify-between items-center space-x-2 pt-1">
                        <div className="flex grow-0 items-center space-x-1">
                            <svg
                                className="w-4 h-4 text-gray-500"
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 24 24"
                                fill="currentColor"
                                aria-hidden="true"
                                data-slot="icon"
                            >
                                <use href="#icon-7a0aee03d160b63202cf57690fd86d3c"></use>
                            </svg>
                            <Link
                                to="/tac-gia/1"
                                className="text-title"
                            >
                                {novel.author.name}
                            </Link>
                        </div>
                        {/*<Link*/}
                        {/*    href="https://metruyencv.com/danh-sach/truyen-tien-hiep"*/}
                        {/*    title="Danh sách truyện Tiên Hiệp"*/}
                        {/*    className="hidden md:flex shrink-0 outline outline-1 px-2 py-1 text-primary rounded"*/}
                        {/*>*/}
                            <span className="text-xs">{fullNovel.genres && fullNovel.genres[0] && fullNovel.genres[0].name}</span>
                        {/*</Link>*/}
                    </div>
                </div>
            </div>
        </>
    );
}

export default EditorRecommendNovelCard;