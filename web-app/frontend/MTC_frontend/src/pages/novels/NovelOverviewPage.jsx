import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router';
import MinialIntroduceNovelCard from "../../components/novel/MinialIntroduceNovelCard.jsx";
import { RatingDetail } from "../../components/feedbacks/RatingDetail.jsx";
import UserComment from "../../components/feedbacks/UserComment.jsx";
import NovelFan from "../../components/feedbacks/NovelFan.jsx";
import AdvertiseItem from "../../components/global/AdvertiseItem.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFlag } from '@fortawesome/free-regular-svg-icons';
import { UserReport } from '../../components/feedbacks/Report.jsx';
import ChapterListPanel from '../../components/novel/ChapterListPanel.jsx';
import {
    useCurrentNovel,
    usePublishedByPublisher,
    useSetCurrentNovel,
    useSetPublishedByPublisher,
} from '../../stores/novelStore.js';
import axios from 'axios';
import { getNovelBySlug } from '../../services/novelService.js';
import { FormattedContent } from '../../common/CommonComponents.jsx';
import { getCurrentPublisherChapter, getPublishedByPublisher } from '../../services/chapterService.js';
import { useChapterActions, useListChapterCurrentPublished } from '../../stores/chapterStore.js';
import { timeAgo } from '../../utils/DatetimeUtil.js';
import { useCurrentNovelPublisher, useSetCurrentNovelPublisher } from '../../stores/userStores.js';
import { getProfileById } from '../../services/userService.js';
import { sendRating } from '../../services/feedbackService.js';
import { fetchCommentPage, useCurrentNovelComments } from '../../stores/feedbackStore.js';
import { UserReply } from '../../components/feedbacks/Reply.jsx';

const NovelOverviewPage= () => {
    const [mode, setMode] = React.useState("rating");
    const [showReport, setShowReport] = React.useState(false);
    const { slug } = useParams();
    const currentNovel = useCurrentNovel();
    const setCurrentNovel = useSetCurrentNovel();
    const currentNovelComments = useCurrentNovelComments();
    console.log("currentNovelComments", currentNovelComments);
    useEffect(() => {
        if (!currentNovel || !currentNovel.slug || currentNovel.slug !== slug) {
            // Nếu chưa có novel hoặc slug không khớp thì fetch
             getNovelBySlug(slug).then(r => {
                    setCurrentNovel(r.data.result);
                 window.scrollTo({
                     top: 0,
                     behavior: 'smooth' // thêm animation cuộn mượt
                 });
                }

            )
        }

    }, [slug, currentNovel]);
    useEffect(() => {
        if (currentNovel && currentNovel.id) {
            fetchCommentPage(currentNovel.id).then(r => {

                    }
                );
        }
    }, [currentNovel]);
    // useEffect(() => {
    //
    // }, [slug])


    //Smooth scroll to top
    // useEffect(() => {
    //     window.scrollTo({
    //         top: 0,
    //         behavior: 'smooth' // thêm animation cuộn mượt
    //     });
    // }, [slug]);
    return (
        <>
            <div className={"px-20"}>
                <div id ="masthead" className={"mx-auto"}>
                    <a id ="topbox-one" href={""} target={"_blank"} rel={"noopener noreferrer"}>
                        <img className={"w-full"} src="https://static.cdnno.com/storage/topbox/598742f2b91aa3516f6cd96c1cc59ee8.webp" alt={"abc"}></img>

                    </a>
                </div>
                {
                    currentNovel.id ? (
                        <div className={"mt-5"} key={currentNovel.id}>
                            <NovelStat novel ={currentNovel}/>

                        </div>
                    ) : (
                        <></>
                    )
                }

                {/*<div className={"mt-5"}>*/}
                {/*    <div className={"mx-auto"} id="middle-two">*/}
                {/*        <img className={"w-full"}*/}
                {/*             src="https://static.cdnno.com/storage/topbox/598742f2b91aa3516f6cd96c1cc59ee8.webp"*/}
                {/*             alt={"abc"}></img>*/}

                {/*    </div>*/}
                {/*</div>*/}
                <AdvertiseItem />
                {/*Rating comment navigator*/}
                <div id = "tab-header">
                    <div className={'flex justify-center secondary-bg text-gray-600'}>
                        <div
                            onClick={() => setMode('rating')}
                            className={`inline-flex items-center py-2 px-3 text-xs space-x-2 border-x hover:cursor-pointer ${
                                mode === 'rating' ? 'bg-yellow-500 text-white' : 'primary-bg text-white'
                            }`}
                        >
                            <span>ĐÁNH GIÁ</span>
                            <span
                                className="bg-red-700 inline-flex items-center justify-center min-w-6 h-6 ms-2 px-2 text-xs font-semibold text-white rounded-full">
                                {currentNovel.totalRates}
                            </span>
                        </div>
                        <div
                            onClick={() => setMode('comment')}
                            className={`inline-flex items-center py-2 px-3 text-xs space-x-2 border-x hover:cursor-pointer ${
                                mode === 'comment' ? 'bg-yellow-500 text-white' : 'primary-bg text-white'
                            }`}
                        >
                            <span>BÌNH LUẬN</span>
                            <span className="bg-red-700 inline-flex items-center justify-center min-w-6 h-6 ms-2 px-2 text-xs font-semibold text-white rounded-full">
                                {currentNovel.totalComments}
                            </span>
                        </div>
                        <div
                            onClick={() => setMode('fan')}
                            className={`inline-flex items-center py-2 px-3 text-xs space-x-2 border-x hover:cursor-pointer ${
                                mode === 'fan' ? 'bg-yellow-500 text-white' : 'primary-bg text-white'
                            }`}
                        >
                            <span>HÂM MỘ</span>
                            <span className="bg-red-700 inline-flex items-center justify-center min-w-6 h-6 ms-2 px-2 text-xs font-semibold text-white rounded-full">
                                {/*{currentNovel.total}*/}
                            </span>
                        </div>
                    </div>
                    {mode === 'rating' ?
                        (
                            <div>
                                <NovelRating novel = {currentNovel}/>
                                <NovelRatingDetail />
                                <RatingDetail />
                            </div>
                        )
                        :
                        mode === 'comment' ?
                            (
                                <div>
                                    <CommentController comments = {currentNovelComments.data} totalComment = {currentNovelComments.totalElements}/>
                                </div>
                            )
                            :
                            (
                                <>
                                    <NovelFan />
                                </>
                            )

                    }

                    {/*<NovelRating />*/}
                    {/*<NovelRatingDetail />*/}
                    {/*<RatingDetail />*/}
                    {/*<CommentController />*/}
                    {/*<NovelFan />*/}
                </div>
            </div>
        </>
    );
}

export default NovelOverviewPage;

const NovelStat = (novel) => {
    novel = novel.novel;
    const data = {
        image: {
            src: novel.novelCoverImage,
            alt: novel.name,
        },
        name: novel.name,
    };

    const [showReport, setShowReport] = React.useState(false);
    const [showChapterList, setShowChapterList] = React.useState(false);

    const listChapterCurrentPublished = useListChapterCurrentPublished();
    const actions = useChapterActions();

    const setPublishedByPublisher = useSetPublishedByPublisher();
    const publishedByPublisher = usePublishedByPublisher();

    const setCurrentNovelPublisher = useSetCurrentNovelPublisher();
    const currentNovelPublisher = useCurrentNovelPublisher();


    useEffect(() => {
        if (novel && novel.slug) {
            getCurrentPublisherChapter(novel.id, 3).then(r => {
                actions.setListChapterRecentPublishedOfNovel(r.data.result.data);
                // return r.data.result;
            })

        }
        getPublishedByPublisher(novel.currentPublisher.id, 6).then(r => {
            setPublishedByPublisher(r.data.result);
        })

        getProfileById(novel.currentPublisher).then(r => {
            setCurrentNovelPublisher(r.data.result);
        })
    }, []);


    function getCurrentPublisherFullName(currentNovelPublisher) {
        return currentNovelPublisher.firstName + " " + currentNovelPublisher.lastName;
    }

    return (
        <>
                <div>
                    <div className={"flex flex-row "}>

                        {/*Cover part*/}
                        <div className={"flex flex-col items-center mr-5"}>
                            <div className={"flex"}>
                                <img className={"w-44 h-60 shadow-lg rounded mx-auto"}
                                     src={novel.novelCoverImage}
                                     alt={novel.name}></img>

                            </div>
                            {/*Report*/}
                            <div className={"flex flex-row items-center mt-2 hover:cursor-pointer mb-2" }
                                 onClick={() => setShowReport(true)}>
                                <div className={"mr-2"}>
                                    <FontAwesomeIcon icon={faFlag} />
                                </div>
                                <div>
                                    Báo cáo
                                </div>
                            </div>
                        </div>
                        {/*Stat*/}
                        <div className={"flex flex-col"}>
                            <div className={"font-bold text-2xl"}>
                                {novel.name}
                            </div>
                            {
                                (novel.author) ? (
                                        <div className={'mb-6 text-gray-500 text-sm mt-1'}>
                                            <a className={'text-gray-500'} title="Binh Pham Ma Thuat Su"
                                               href={`/tac-gia/${novel.author.id}`}>
                                                {novel.author.name}
                                            </a>
                                        </div>
                                    ) :
                                    <></>
                            }

                            <div className={'flex flex-row my-3 text-sm'}>
                                {/*Doc tiep*/}
                                <div
                                    className={'flex flex-row items-center justify-between mr-5 border-1 border-gray-400 rounded px-1 hover:border-yellow-500 hover:cursor-pointer hover:text-yellow-500'}>
                                    <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlnsXlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" viewBox="0 0 122.88 101.37"
                                         style={{width: "20px", height: "30px", fill: "gray"}} xmlSpace="preserve"
                                         className={"mr-2"}
                                    >
                                <g>
                                    <path d="M12.64,77.27l0.31-54.92h-6.2v69.88c8.52-2.2,17.07-3.6,25.68-3.66c7.95-0.05,15.9,1.06,23.87,3.76
                                        c-4.95-4.01-10.47-6.96-16.36-8.88c-7.42-2.42-15.44-3.22-23.66-2.52c-1.86,0.15-3.48-1.23-3.64-3.08
                                        C12.62,77.65,12.62,77.46,12.64,77.27L12.64,77.27z M103.62,19.48c-0.02-0.16-0.04-0.33-0.04-0.51c0-0.17,0.01-0.34,0.04-0.51V7.34
                                        c-7.8-0.74-15.84,0.12-22.86,2.78c-6.56,2.49-12.22,6.58-15.9,12.44V85.9c5.72-3.82,11.57-6.96,17.58-9.1
                                        c6.85-2.44,13.89-3.6,21.18-3.02V19.48L103.62,19.48z M110.37,15.6h9.14c1.86,0,3.37,1.51,3.37,3.37v77.66
                                        c0,1.86-1.51,3.37-3.37,3.37c-0.38,0-0.75-0.06-1.09-0.18c-9.4-2.69-18.74-4.48-27.99-4.54c-9.02-0.06-18.03,1.53-27.08,5.52
                                        c-0.56,0.37-1.23,0.57-1.92,0.56c-0.68,0.01-1.35-0.19-1.92-0.56c-9.04-4-18.06-5.58-27.08-5.52c-9.25,0.06-18.58,1.85-27.99,4.54
                                        c-0.34,0.12-0.71,0.18-1.09,0.18C1.51,100.01,0,98.5,0,96.64V18.97c0-1.86,1.51-3.37,3.37-3.37h9.61l0.06-11.26
                                        c0.01-1.62,1.15-2.96,2.68-3.28l0,0c8.87-1.85,19.65-1.39,29.1,2.23c6.53,2.5,12.46,6.49,16.79,12.25
                                        c4.37-5.37,10.21-9.23,16.78-11.72c8.98-3.41,19.34-4.23,29.09-2.8c1.68,0.24,2.88,1.69,2.88,3.33h0V15.6L110.37,15.6z M68.13,91.82
                                        c7.45-2.34,14.89-3.3,22.33-3.26c8.61,0.05,17.16,1.46,25.68,3.66V22.35h-5.77v55.22c0,1.86-1.51,3.37-3.37,3.37
                                        c-0.27,0-0.53-0.03-0.78-0.09c-7.38-1.16-14.53-0.2-21.51,2.29C79.09,85.15,73.57,88.15,68.13,91.82L68.13,91.82z M58.12,85.25
                                        V22.46c-3.53-6.23-9.24-10.4-15.69-12.87c-7.31-2.8-15.52-3.43-22.68-2.41l-0.38,66.81c7.81-0.28,15.45,0.71,22.64,3.06
                                        C47.73,78.91,53.15,81.64,58.12,85.25L58.12,85.25z"/>
                                </g>
                            </svg><span>
                                Đọc tiếp
                            </span>
                                </div>
                                {/*Đánh dấu*/}
                                <div
                                    className={"flex flex-row relative items-center justify-between mr-5 border-1 border-gray-400 rounded px-1 hover:border-yellow-500 hover:cursor-pointer hover:text-yellow-500"}>
                                    <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlnsXlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" viewBox="0 0 122.88 101.37"
                                         style={{width: "20px", height: "30px", fill: "gray"}} xmlSpace="preserve"
                                         className={"mr-2"}
                                    >
                                <g>
                                    <path d="M12.64,77.27l0.31-54.92h-6.2v69.88c8.52-2.2,17.07-3.6,25.68-3.66c7.95-0.05,15.9,1.06,23.87,3.76
                                        c-4.95-4.01-10.47-6.96-16.36-8.88c-7.42-2.42-15.44-3.22-23.66-2.52c-1.86,0.15-3.48-1.23-3.64-3.08
                                        C12.62,77.65,12.62,77.46,12.64,77.27L12.64,77.27z M103.62,19.48c-0.02-0.16-0.04-0.33-0.04-0.51c0-0.17,0.01-0.34,0.04-0.51V7.34
                                        c-7.8-0.74-15.84,0.12-22.86,2.78c-6.56,2.49-12.22,6.58-15.9,12.44V85.9c5.72-3.82,11.57-6.96,17.58-9.1
                                        c6.85-2.44,13.89-3.6,21.18-3.02V19.48L103.62,19.48z M110.37,15.6h9.14c1.86,0,3.37,1.51,3.37,3.37v77.66
                                        c0,1.86-1.51,3.37-3.37,3.37c-0.38,0-0.75-0.06-1.09-0.18c-9.4-2.69-18.74-4.48-27.99-4.54c-9.02-0.06-18.03,1.53-27.08,5.52
                                        c-0.56,0.37-1.23,0.57-1.92,0.56c-0.68,0.01-1.35-0.19-1.92-0.56c-9.04-4-18.06-5.58-27.08-5.52c-9.25,0.06-18.58,1.85-27.99,4.54
                                        c-0.34,0.12-0.71,0.18-1.09,0.18C1.51,100.01,0,98.5,0,96.64V18.97c0-1.86,1.51-3.37,3.37-3.37h9.61l0.06-11.26
                                        c0.01-1.62,1.15-2.96,2.68-3.28l0,0c8.87-1.85,19.65-1.39,29.1,2.23c6.53,2.5,12.46,6.49,16.79,12.25
                                        c4.37-5.37,10.21-9.23,16.78-11.72c8.98-3.41,19.34-4.23,29.09-2.8c1.68,0.24,2.88,1.69,2.88,3.33h0V15.6L110.37,15.6z M68.13,91.82
                                        c7.45-2.34,14.89-3.3,22.33-3.26c8.61,0.05,17.16,1.46,25.68,3.66V22.35h-5.77v55.22c0,1.86-1.51,3.37-3.37,3.37
                                        c-0.27,0-0.53-0.03-0.78-0.09c-7.38-1.16-14.53-0.2-21.51,2.29C79.09,85.15,73.57,88.15,68.13,91.82L68.13,91.82z M58.12,85.25
                                        V22.46c-3.53-6.23-9.24-10.4-15.69-12.87c-7.31-2.8-15.52-3.43-22.68-2.41l-0.38,66.81c7.81-0.28,15.45,0.71,22.64,3.06
                                        C47.73,78.91,53.15,81.64,58.12,85.25L58.12,85.25z"/>
                                </g>
                            </svg>
                                    <span>
                                Đánh dấu
                            </span>
                                </div>
                                {/*Mục lục*/}
                                <div
                                    className={"flex flex-row relative items-center justify-between mr-5 border-1 border-gray-400 rounded px-1 hover:border-yellow-500 hover:cursor-pointer hover:text-yellow-500"}
                                    onClick={() => setShowChapterList(true)}>
                                    <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlnsXlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" viewBox="0 0 122.88 101.37"
                                         style={{width: "20px", height: "30px", fill: "gray"}} xmlSpace="preserve"
                                         className={"mr-2"}
                                    >cd
                                <g>
                                    <path d="M12.64,77.27l0.31-54.92h-6.2v69.88c8.52-2.2,17.07-3.6,25.68-3.66c7.95-0.05,15.9,1.06,23.87,3.76
                                        c-4.95-4.01-10.47-6.96-16.36-8.88c-7.42-2.42-15.44-3.22-23.66-2.52c-1.86,0.15-3.48-1.23-3.64-3.08
                                        C12.62,77.65,12.62,77.46,12.64,77.27L12.64,77.27z M103.62,19.48c-0.02-0.16-0.04-0.33-0.04-0.51c0-0.17,0.01-0.34,0.04-0.51V7.34
                                        c-7.8-0.74-15.84,0.12-22.86,2.78c-6.56,2.49-12.22,6.58-15.9,12.44V85.9c5.72-3.82,11.57-6.96,17.58-9.1
                                        c6.85-2.44,13.89-3.6,21.18-3.02V19.48L103.62,19.48z M110.37,15.6h9.14c1.86,0,3.37,1.51,3.37,3.37v77.66
                                        c0,1.86-1.51,3.37-3.37,3.37c-0.38,0-0.75-0.06-1.09-0.18c-9.4-2.69-18.74-4.48-27.99-4.54c-9.02-0.06-18.03,1.53-27.08,5.52
                                        c-0.56,0.37-1.23,0.57-1.92,0.56c-0.68,0.01-1.35-0.19-1.92-0.56c-9.04-4-18.06-5.58-27.08-5.52c-9.25,0.06-18.58,1.85-27.99,4.54
                                        c-0.34,0.12-0.71,0.18-1.09,0.18C1.51,100.01,0,98.5,0,96.64V18.97c0-1.86,1.51-3.37,3.37-3.37h9.61l0.06-11.26
                                        c0.01-1.62,1.15-2.96,2.68-3.28l0,0c8.87-1.85,19.65-1.39,29.1,2.23c6.53,2.5,12.46,6.49,16.79,12.25
                                        c4.37-5.37,10.21-9.23,16.78-11.72c8.98-3.41,19.34-4.23,29.09-2.8c1.68,0.24,2.88,1.69,2.88,3.33h0V15.6L110.37,15.6z M68.13,91.82
                                        c7.45-2.34,14.89-3.3,22.33-3.26c8.61,0.05,17.16,1.46,25.68,3.66V22.35h-5.77v55.22c0,1.86-1.51,3.37-3.37,3.37
                                        c-0.27,0-0.53-0.03-0.78-0.09c-7.38-1.16-14.53-0.2-21.51,2.29C79.09,85.15,73.57,88.15,68.13,91.82L68.13,91.82z M58.12,85.25
                                        V22.46c-3.53-6.23-9.24-10.4-15.69-12.87c-7.31-2.8-15.52-3.43-22.68-2.41l-0.38,66.81c7.81-0.28,15.45,0.71,22.64,3.06
                                        C47.73,78.91,53.15,81.64,58.12,85.25L58.12,85.25z"/>
                                </g>
                            </svg>
                            <span>
                                Mục lục
                            </span>
                            <span className={"absolute -right-4 -top-4"}>
                                <span className="background-primary inline-flex items-center justify-center min-w-6 h-6 ms-2 px-1 text-[10px] text-white rounded-full">
                                    {novel.totalChapters}
                                </span>
                            </span>
                                </div>
                                {/*Đánh giá*/}
                                <div
                                    className={'flex flex-row relative items-center justify-between mr-5 border-1 border-gray-400 rounded px-1 hover:border-yellow-500 hover:cursor-pointer hover:text-yellow-500'}>
                                    <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlnsXlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" viewBox="0 0 122.88 101.37"
                                         style={{ width: '20px', height: '30px', fill: 'gray' }} xmlSpace="preserve"
                                         className={'mr-2'}
                                    >
                                        <g>
                                            <path d="M12.64,77.27l0.31-54.92h-6.2v69.88c8.52-2.2,17.07-3.6,25.68-3.66c7.95-0.05,15.9,1.06,23.87,3.76
                                        c-4.95-4.01-10.47-6.96-16.36-8.88c-7.42-2.42-15.44-3.22-23.66-2.52c-1.86,0.15-3.48-1.23-3.64-3.08
                                        C12.62,77.65,12.62,77.46,12.64,77.27L12.64,77.27z M103.62,19.48c-0.02-0.16-0.04-0.33-0.04-0.51c0-0.17,0.01-0.34,0.04-0.51V7.34
                                        c-7.8-0.74-15.84,0.12-22.86,2.78c-6.56,2.49-12.22,6.58-15.9,12.44V85.9c5.72-3.82,11.57-6.96,17.58-9.1
                                        c6.85-2.44,13.89-3.6,21.18-3.02V19.48L103.62,19.48z M110.37,15.6h9.14c1.86,0,3.37,1.51,3.37,3.37v77.66
                                        c0,1.86-1.51,3.37-3.37,3.37c-0.38,0-0.75-0.06-1.09-0.18c-9.4-2.69-18.74-4.48-27.99-4.54c-9.02-0.06-18.03,1.53-27.08,5.52
                                        c-0.56,0.37-1.23,0.57-1.92,0.56c-0.68,0.01-1.35-0.19-1.92-0.56c-9.04-4-18.06-5.58-27.08-5.52c-9.25,0.06-18.58,1.85-27.99,4.54
                                        c-0.34,0.12-0.71,0.18-1.09,0.18C1.51,100.01,0,98.5,0,96.64V18.97c0-1.86,1.51-3.37,3.37-3.37h9.61l0.06-11.26
                                        c0.01-1.62,1.15-2.96,2.68-3.28l0,0c8.87-1.85,19.65-1.39,29.1,2.23c6.53,2.5,12.46,6.49,16.79,12.25
                                        c4.37-5.37,10.21-9.23,16.78-11.72c8.98-3.41,19.34-4.23,29.09-2.8c1.68,0.24,2.88,1.69,2.88,3.33h0V15.6L110.37,15.6z M68.13,91.82
                                        c7.45-2.34,14.89-3.3,22.33-3.26c8.61,0.05,17.16,1.46,25.68,3.66V22.35h-5.77v55.22c0,1.86-1.51,3.37-3.37,3.37
                                        c-0.27,0-0.53-0.03-0.78-0.09c-7.38-1.16-14.53-0.2-21.51,2.29C79.09,85.15,73.57,88.15,68.13,91.82L68.13,91.82z M58.12,85.25
                                        V22.46c-3.53-6.23-9.24-10.4-15.69-12.87c-7.31-2.8-15.52-3.43-22.68-2.41l-0.38,66.81c7.81-0.28,15.45,0.71,22.64,3.06
                                        C47.73,78.91,53.15,81.64,58.12,85.25L58.12,85.25z" />
                                        </g>
                                    </svg>
                                    <span>
                                Đánh giá
                            </span>
                                    <span className={'absolute -right-4 -top-4'}>
                                <span
                                    className="background-primary inline-flex items-center justify-center min-w-6 h-6 ms-2 px-1 text-[10px] text-white rounded-full">
                                    {novel.totalRates}
                                </span>
                            </span>
                                </div>
                                {/*Thảo luận*/}
                                <div
                                    className={'flex flex-row relative items-center justify-between mr-5 border-1 border-gray-400 rounded px-1 hover:border-yellow-500 hover:cursor-pointer hover:text-yellow-500'}>
                                    <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlnsXlink="http://www.w3.org/1999/xlink"
                                         x="0px" y="0px" viewBox="0 0 122.88 101.37"
                                         style={{ width: '20px', height: '30px', fill: 'gray' }} xmlSpace="preserve"
                                         className={'mr-2'}
                                    >
                                        <g>
                                            <path d="M12.64,77.27l0.31-54.92h-6.2v69.88c8.52-2.2,17.07-3.6,25.68-3.66c7.95-0.05,15.9,1.06,23.87,3.76
                                        c-4.95-4.01-10.47-6.96-16.36-8.88c-7.42-2.42-15.44-3.22-23.66-2.52c-1.86,0.15-3.48-1.23-3.64-3.08
                                        C12.62,77.65,12.62,77.46,12.64,77.27L12.64,77.27z M103.62,19.48c-0.02-0.16-0.04-0.33-0.04-0.51c0-0.17,0.01-0.34,0.04-0.51V7.34
                                        c-7.8-0.74-15.84,0.12-22.86,2.78c-6.56,2.49-12.22,6.58-15.9,12.44V85.9c5.72-3.82,11.57-6.96,17.58-9.1
                                        c6.85-2.44,13.89-3.6,21.18-3.02V19.48L103.62,19.48z M110.37,15.6h9.14c1.86,0,3.37,1.51,3.37,3.37v77.66
                                        c0,1.86-1.51,3.37-3.37,3.37c-0.38,0-0.75-0.06-1.09-0.18c-9.4-2.69-18.74-4.48-27.99-4.54c-9.02-0.06-18.03,1.53-27.08,5.52
                                        c-0.56,0.37-1.23,0.57-1.92,0.56c-0.68,0.01-1.35-0.19-1.92-0.56c-9.04-4-18.06-5.58-27.08-5.52c-9.25,0.06-18.58,1.85-27.99,4.54
                                        c-0.34,0.12-0.71,0.18-1.09,0.18C1.51,100.01,0,98.5,0,96.64V18.97c0-1.86,1.51-3.37,3.37-3.37h9.61l0.06-11.26
                                        c0.01-1.62,1.15-2.96,2.68-3.28l0,0c8.87-1.85,19.65-1.39,29.1,2.23c6.53,2.5,12.46,6.49,16.79,12.25
                                        c4.37-5.37,10.21-9.23,16.78-11.72c8.98-3.41,19.34-4.23,29.09-2.8c1.68,0.24,2.88,1.69,2.88,3.33h0V15.6L110.37,15.6z M68.13,91.82
                                        c7.45-2.34,14.89-3.3,22.33-3.26c8.61,0.05,17.16,1.46,25.68,3.66V22.35h-5.77v55.22c0,1.86-1.51,3.37-3.37,3.37
                                        c-0.27,0-0.53-0.03-0.78-0.09c-7.38-1.16-14.53-0.2-21.51,2.29C79.09,85.15,73.57,88.15,68.13,91.82L68.13,91.82z M58.12,85.25
                                        V22.46c-3.53-6.23-9.24-10.4-15.69-12.87c-7.31-2.8-15.52-3.43-22.68-2.41l-0.38,66.81c7.81-0.28,15.45,0.71,22.64,3.06
                                        C47.73,78.91,53.15,81.64,58.12,85.25L58.12,85.25z" />
                                        </g>
                                    </svg>
                                    <span>
                                Thảo luận
                            </span>
                                    <span className={'absolute -right-4 -top-4'}>
                                <span
                                    className="background-primary inline-flex items-center justify-center min-w-6 h-6 ms-2 px-1 text-[10px] text-white rounded-full">
                                    {novel.totalComments}
                                </span>
                            </span>
                                </div>
                            </div>
                            {/*Stat*/}
                            <div
                                className="mb-6 flex justify-center divide-x divide-gray-500 divide-auto text-title md:justify-start md:mb-8 text-sm">
                                <div className={'px-3 md:pl-0'}>
                                <div className={"font-semibold text-md text-center"}>
                                        22
                                    </div>
                                    <div>
                                        Chương/tuần
                                    </div>
                                </div>
                                {/*Lượt đọc*/}
                                <div className={"px-3"}>
                                    <div className={"font-semibold text-md text-center"}>
                                        {novel.totalViews}
                                    </div>
                                    <div>
                                        Lượt đọc
                                    </div>
                                </div>
                                {/*Đề cử*/}
                                <div className={"px-3"}>
                                    <div className={"font-semibold text-md text-center"}>
                                        {novel.totalPromotions}
                                    </div>
                                    <div>
                                        Đề cử
                                    </div>
                                </div>
                                {/*Cất giữ*/}
                                <div className={"px-3"}>
                                    <div className={"font-semibold text-md text-center"}>
                                        {novel.totalBookmarks}
                                    </div>
                                    <div>
                                        Cất giữ
                                    </div>
                                </div>
                            </div>


                            <CategoryBar novel={novel}/>
                            {/*<CategoryItem type={"genre"} items={novel.genres}/>*/}
                        </div>

                    </div>
                    <div className={"pb-3"}>
                        <div className={"flex justify-between items-center secondary-bg text-gray-600"}>
                            <h2 className="inline-flex items-center py-2 px-3 text-xs">CHƯƠNG MỚI</h2>
                            <div className="flex items-center space-x-1 text-gray-800 pr-3 hover:cursor-pointer">
                                <span className="text-xs text-yellow-primary">
                                    xem tất cả
                                </span>
                            </div>
                        </div>
                    </div>


                    {/*Recent publish*/}
                    <div className={"pb-3 border-dot-b border-b border-gray-600 border-dotted"}>
                        <div className={"flex flex-row justify-between"}>
                            {listChapterCurrentPublished.map((chapter, index) => (
                                <>
                                    <div className={'flex flex-col w-1/3 mr-5'} key = {chapter.id}>
                                        <Link to={`/truyen/${novel.slug}/chuong-${chapter.chapterIdx}`} className={'hover:cursor-pointer'}>
                                            <div className={'flex flex-row yellow-text-color'}>
                                                {chapter.name}
                                            </div>
                                            <div className={'text-sm '}>
                                                {timeAgo(chapter.publishedAt)}
                                            </div>
                                        </Link>
                                    </div>
                                </>
                            ))
                            }

                            {/*<div className={"flex flex-col w-1/3 mr-5"}>*/}
                            {/*    <Link to="/novel/chapter/1" className={"hover:cursor-pointer"}>*/}
                            {/*        <div className={"flex flex-row yellow-text-color"}>*/}
                            {/*            Chuong 1*/}
                            {/*        </div>*/}
                            {/*        <div className={"text-sm"}>*/}
                            {/*            5 gio truoc*/}
                            {/*        </div>*/}
                            {/*    </Link>*/}
                            {/*</div>*/}
                            {/*<div className={"flex flex-col w-1/3 mr-5"}>*/}
                            {/*    <Link to="/novel/chapter/1" className={"hover:cursor-pointer"}>*/}
                            {/*        <div className={"flex flex-row yellow-text-color"}>*/}
                            {/*            Chuong 1*/}
                            {/*        </div>*/}
                            {/*        <div className={"text-sm"}>*/}
                            {/*            5 gio truoc*/}
                            {/*        </div>*/}
                            {/*    </Link>*/}
                            {/*</div>*/}
                        </div>
                    </div>

                    <div className={"pb-3"}>
                        <div className={"flex justify-between items-center secondary-bg text-gray-600"}>
                            <h2 className="inline-flex items-center py-2 px-3 text-xs">GIỚI THIỆU</h2>
                            <div className="flex items-center space-x-1 text-gray-800 pr-3 hover:cursor-pointer">
                                {/*<span className="text-xs primary-text-color">*/}
                                {/*    xem tất cả*/}
                                {/*</span>*/}
                            </div>
                        </div>
                    </div>
                    <div>
                        <div className={"text-gray-600 dark:text-gray-300 py-4 px-2 md:px-1 text-base break-words"}>
                            {/*Là Đan Đế trọng sinh? Là dung hợp linh hồn? Bị đánh cắp linh căn, linh huyết, linh cốt ba không*/}
                            {/*thiếu niên — — Long Trần, nương tựa theo trong trí nhớ luyện đan thần thuật, tu hành thần bí*/}
                            {/*công pháp Cửu Tinh Bá Thể Quyết, đẩy ra sương mù dày đặc, giải khai cái bẫy động trời.*/}
                            {/*<br/>*/}
                            {/*<br/>*/}
                            {/*Tay cầm thiên địa càn khôn, chân đạp nhật nguyệt tinh thần, thông đồng các loại mỹ nữ, trấn áp*/}
                            {/*ác quỷ tà thần.*/}
                            <FormattedContent content={novel.description} />
                        </div>

                    </div>
                    <div>
                        <div className={"secondary-bg text-gray-600 p-4 rounded text-sm"}>
                            <span className="font-bold">Thông báo: </span>
                            <FormattedContent content={novel.publisherNote} />

                        </div>
                    </div>
                    <div className={"pb-3 pt-3"}>
                        <div className={"flex justify-between items-center secondary-bg text-gray-600"}>
                            <h2 className="inline-flex items-center py-2 px-3 text-xs">CÙNG ĐĂNG BỞI {getCurrentPublisherFullName(currentNovelPublisher)}</h2>

                        </div>
                        <div className={"flex flex-row justify-start items-center mt-2"}>
                            {/*{publishedByPublisher ? publishedByPublisher.map(((novel, idx) => (*/}
                            {/*    <>*/}
                            {/*        <div className={'w-44 h-60 mr-2 mb-2'}>*/}
                            {/*            <MinialIntroduceNovelCard data={novel} />*/}

                            {/*        </div>*/}
                            {/*    </>*/}
                            {/*))): <></>}*/}
                            {/*{publishedByPublisher ? publishedByPublisher.map(((novel, idx) => () }*/}
                            {Array.isArray(publishedByPublisher.data) && publishedByPublisher.data !== null ? publishedByPublisher.data.map((published, index) => (
                                <>
                                <div className={'w-44 h-60 mr-2 mb-2'}>
                                    <MinialIntroduceNovelCard data={published} />
                                </div>
                                </>
                            )) : (<></>)
                            }
                        </div>

                    </div>
                    {showReport && (
                        <UserReport onClose={()=>setShowReport(false)}/>
                    )}
                    {showChapterList && (
                        <ChapterListPanel onClose={() => setShowChapterList(false)}/>
                    )}
                </div>

        </>
    )
}

const NovelRating = (novel) => {
    const [mainCharacterRateContent, setMainCharacterRateContent] = useState("");
    const [novelContentRateContent, setNovelContentRateContent] = useState("");
    const [worldContentRateContent, setWorldContentRateContent] = useState("");
    const [ratingDetailContent, setRatingDetailContent] = useState("");
    const [ratingValue, setRatingValue] = useState(5);
    const [isOnlyRating, setIsOnlyRating] = useState(false);

    const handleChangeSlider = (value) => {
        setRatingValue(parseFloat(value));
    };

    const handleCheckboxChange = (e) => {
        setIsOnlyRating(e.target.checked);
    };

    const handleRating = async () => {
        const ratingData = {
            rating: ratingValue,
            ...(isOnlyRating
                ? {}
                : {
                    // mainCharacter: mainCharacterRateContent,
                    // novelContent: novelContentRateContent,
                    // worldContent: worldContentRateContent,
                    content: ratingDetailContent,
                    ratingInNovelId: novel.id,
                    lastReadChapterId: novel.id
                })
        };

        try {
            await sendRating(ratingData);
            alert("Đánh giá thành công!");
        } catch (error) {
            console.error("Gửi đánh giá thất bại", error);
        }
    };

    return (
        <div className="grid grid-cols-4 gap-y-6 md:gap-y-0 md:gap-x-4 mt-6 mx-2">
            <div className="col-span-4 space-y-6">
                <div className="py-4 px-4 mb-4 bg-white rounded-lg border border-gray-200 dark:bg-black dark:border-gray-700 space-y-6">
                    <div className="space-y-6 p-2">
                        <div className="flex flex-col space-y-2 w-full">
                            <h3>
                                Chấm điểm nội dung truyện:{" "}
                                <span className="font-bold primary-text-color">{ratingValue}</span> điểm
                            </h3>
                            <input
                                type="range"
                                min="3"
                                max="5"
                                value={ratingValue}
                                step={0.5}
                                className="slider w-full"
                                onChange={(e) => handleChangeSlider(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="space-y-6 p-2">
                        <div className="flex items-center">
                            <input
                                type="checkbox"
                                checked={isOnlyRating}
                                onChange={handleCheckboxChange}
                                className="w-4 h-4 text-primary bg-gray-100 border-gray-300 rounded"
                            />
                            <span className="ml-3 text-sm space-x-1 text-gray-500">
                                <span className="font-medium">Tôi chỉ muốn chấm điểm</span> (không viết đánh giá)
                            </span>
                        </div>

                        {!isOnlyRating && (
                            <div className="space-y-4">
                                <input
                                    type="text"
                                    value={mainCharacterRateContent}
                                    onChange={(e) => setMainCharacterRateContent(e.target.value)}
                                    className="px-3 w-full text-sm border p-2 border-gray-600 dark:text-white placeholder-gray-400"
                                    placeholder="Nhân vật chính như nào?"
                                />
                                <input
                                    type="text"
                                    value={novelContentRateContent}
                                    onChange={(e) => setNovelContentRateContent(e.target.value)}
                                    className="px-3 w-full text-sm border p-2 border-gray-600 dark:text-white placeholder-gray-400"
                                    placeholder="Cốt truyện như nào?"
                                />
                                <input
                                    type="text"
                                    value={worldContentRateContent}
                                    onChange={(e) => setWorldContentRateContent(e.target.value)}
                                    className="px-3 w-full text-sm border p-2 border-gray-600 dark:text-white placeholder-gray-400"
                                    placeholder="Bố cục thế giới như nào?"
                                />
                                <textarea
                                    rows="2"
                                    value={ratingDetailContent}
                                    onChange={(e) => setRatingDetailContent(e.target.value)}
                                    className="px-3 w-full text-sm border p-2 border-gray-600 dark:text-white placeholder-gray-400"
                                    placeholder="Nội dung bài đánh giá (ít nhất 100 từ)..."
                                />
                            </div>
                        )}
                    </div>
                    <div className="flex justify-center">
                        <button
                            onClick={handleRating}
                            className="inline-flex justify-center px-4 py-2 border shadow-sm text-sm font-medium rounded-md primary-text-color bg-inherit focus:outline-none w-1/3 primary-btn-outline"
                        >
                            GỬI ĐÁNH GIÁ
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

function NovelRatingDetail() {
    return (
        <>
            <div>
                <div className={"col-span-4 md:col-span-4 space-y-6 px-2"}>
                    <div className="flex justify-between items-center">
                        <div className="flex items-center"><input
                                                                  type="checkbox"
                                                                  className="w-4 h-4 text-primary bg-gray-100"/><span
                            className="ml-3" id="annual-billing-label"><span className="font-medium text-primary">Hiện tất cả</span></span>
                        </div>
                        <div><h3 data-x-text="`${book.review_count} đánh giá`" className="font-semibold">15 đánh
                            giá</h3></div>
                        <div className="relative inline-block text-left"><select data-x-bind="ReviewSort"
                                                                                 className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 text-black sm:text-sm rounded-md dark:bg-black dark:text-white">
                            <option value="-like_count">Lượt thích</option>
                            <option value="-id">Mới nhất</option>
                            <option value="id">Cũ nhất</option>
                        </select></div>
                    </div>
                </div>
            </div>
        </>
    )
}


function CommentController({ comments, totalComment }) {
    const [showReport, setShowReport] = useState(false);
    const [totalCommentShow, setTotalCommentShow] = useState(10);

    if (!totalComment || totalComment <= 0) {
        totalComment = 0;
    }

    function getTotalNextCommentToShow() {
        const remaining = totalComment - totalCommentShow;
        return remaining > 10 ? 10 : remaining;
    }

    return (
        <>
            <div className="mt-3">
                <CommentPart totalComment={totalComment} />
                {comments.comments &&
                    comments.comments.map((comment, index) => (
                        index < totalCommentShow && (
                            <div key={index} id={index.toString()}>
                                <UserComment comment={comment} />
                            </div>
                        )
                    ))
                }
                <span>Xem {getTotalNextCommentToShow()} bình luận</span>
            </div>
        </>
    );
}

function CommentPart({totalComment}){
    return (
        <>
            <div>
                <div className="mb-6">
                    <div className="py-2 px-4 mb-4 bg-black rounded-lg rounded-t-lg border border-gray-500">
                        <textarea
                            rows="6"
                            className="px-0 w-full text-gray-300 focus:ring-0 focus:outline-none placeholder-gray-400 bg-black"
                            placeholder="Thảo luận ..."
                            required
                            style={{overflow: "hidden", overflowWrap: "break-word", resize: "none", textAlign: "start", height: "160px"}}
                        ></textarea>
                    </div>
                    <div className="flex items-center justify-between">
                        <div className="relative inline-block text-left">
                            <select
                                className="mt-1 block w-full pl-3 pr-6 py-2 text-base border-gray-300 text-black sm:text-sm rounded-md dark:bg-black dark:text-white">
                                <option value="-sticky,commentable_id,-id">Liên quan</option>
                                <option value="-sticky,-id">Mới nhất</option>
                                <option value="-sticky,-like_count">Lượt thích</option>
                                <option value="-sticky,id">Cũ nhất</option>
                            </select>
                        </div>
                        <div className="font-bold">{totalComment} thảo luận</div>
                        <div
                            className="select-none hover:cursor-pointer inline-flex justify-center px-4 py-2 shadow-sm text-sm font-medium rounded-md primary-text-color primary-btn-outline bg-black focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 btn-outline-primary w-16 h-9 disabled:bg-gray-500">
                            GỬI
                        </div>
                    </div>
                </div>

            </div>
        </>
    )
}

const CategoryItem = ({ type, items }) => {
    const typeColors = {
        genre: "border-blue-400 text-blue-400",
        worldScene: "border-green-400 text-green-400",
        mainCharacterTrait: "border-purple-400 text-purple-400",
        sect: "border-red-400 text-red-400",
    };

    const colorClass = typeColors[type] || "border-gray-400 text-gray-400"; // fallback nếu type không hợp lệ

    return (
        <div className="flex flex-row leading-10 md:leading-normal space-x-4 hover:cursor-pointer">
            {items.map((item) => (
                <a
                    key={item.id}
                    className={`inline-flex border rounded px-2 py-1 ${colorClass}`}
                >
                    <span className={`text-xs ${colorClass}`}>{item.name}</span>
                </a>
            ))}
        </div>
    );
};

const CategoryBar = ({novel}) => {

    return (
        <>
            <div className={"flex flex-row leading-10 md:leading-normal space-x-1"}>
                {novel.genres ? (
                    <CategoryItem type={"genre"} items={novel.genres} />
                ) : (<></>)}
                {novel.mainCharacterTraits ? (
                    <CategoryItem type={"mainCharacterTrait"} items={novel.mainCharacterTraits} />
                ) : (<></>)}
                {novel.worldScenes ? (
                    <CategoryItem type={"worldScene"} items={novel.worldScenes} />
                ) : (<></>)}
                {novel.sects ? (
                    <CategoryItem type={"sect"} items={novel.sects} />
                ) : (<></>)}

            </div>
        </>
    )
}