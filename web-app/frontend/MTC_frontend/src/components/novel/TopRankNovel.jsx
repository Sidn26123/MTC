import React, { useEffect } from 'react';
import {faLayerGroup, faUser} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { getTopPromotionNovels } from '../../services/novelService.js';
import { Link, useNavigate } from 'react-router';
import { getNovelUrlWithSlug } from '../../utils/URLUtils.js';

function TopRankNovel() {
    const [data, setData] = React.useState([]);

    useEffect(() => {
        getTopPromotionNovels().then((r) => {
            setData(r.data.result);
        });
    }, [])


    return (
        <>
            <div className={'w-full rounded-md p-2'}>
                {/*<NovelDetailCard/>*/}
                {/*<NovelCard/>*/}
                {/*<NovelCard isBol={1}/>*/}
                {/*<NovelCard/>*/}
                <div data-x-data="realtime" id="real-time">
                    <div
                        title="Đang đọc theo thời gian thực"
                        className="box-title flex justify-between items-center"
                    >
                        <h2 className="uppercase">Thời gian thực</h2>
                        <svg
                            className="w-4 h-4"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 20 20"
                            fill="currentColor"
                            aria-hidden="true"
                            data-slot="icon"
                        >
                            <use href="#icon-45dfdae75c66f6db1091e91efb93d2f4"></use>
                        </svg>
                    </div>
                    <div
                        data-x-show="isLoading"
                        className="flex justify-center items-center min-h-[490px]"
                        style={{ display: 'none' }}
                    >
                        <svg
                            className="inline animate-spin w-8 h-8 text-primary"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke-width="1.5"
                            stroke="currentColor"
                            aria-hidden="true"
                            data-slot="icon"
                        >
                            <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"
                            ></path>
                        </svg>
                    </div>
                    {/*<template data-x-if="list.length > 0">*/}
                    {/*    <div>*/}
                    {/*        <template data-x-for="(item, index) in list">*/}
                    {/*            <div className="box-content divide-y divide-dotted divide-auto">*/}
                    {/*                <div data-x-show="index === 0" className="flex space-x-2 pt-3 px-3"><img*/}
                    {/*                    data-x-bind="RankIcon(index)" className="w-6 h-6" alt="rank-1" />*/}
                    {/*                    <div className="w-full relative"><a data-x-bind="BookUrl(item.book)"*/}
                    {/*                                                        data-x-text="item.book.name" target="_blank"*/}
                    {/*                                                        className="text-title font-semibold"></a>*/}
                    {/*                        <div className="flex item-center space-x-2 text-primary my-1"><span*/}
                    {/*                            data-x-text="item.totalPromotions"*/}
                    {/*                            className="text-sm font-semibold mr-1"></span> người đang đọc*/}
                    {/*                        </div>*/}
                    {/*                        <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">*/}
                    {/*                            <div className="flex items-center space-x-1">*/}
                    {/*                                <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg"*/}
                    {/*                                     fill="none" viewBox="0 0 24 24" stroke-width="1.5"*/}
                    {/*                                     stroke="currentColor" aria-hidden="true" data-slot="icon">*/}
                    {/*                                    <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>*/}
                    {/*                                </svg>*/}
                    {/*                                <span data-x-bind="BookAuthor(item.book)"*/}
                    {/*                                      className="text-xs truncate"></span></div>*/}
                    {/*                            <div className="flex items-center space-x-1">*/}
                    {/*                                <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg"*/}
                    {/*                                     fill="none" viewBox="0 0 24 24" stroke-width="1.5"*/}
                    {/*                                     stroke="currentColor" aria-hidden="true" data-slot="icon">*/}
                    {/*                                    <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>*/}
                    {/*                                </svg>*/}
                    {/*                                <span data-x-text="item.book.genres[0].name"*/}
                    {/*                                      className="text-xs truncate"></span></div>*/}
                    {/*                        </div>*/}
                    {/*                    </div>*/}
                    {/*                    <div className="book-cover"><a data-x-bind="BookUrl(item.book)" target="_blank"*/}
                    {/*                                                   className="book-cover-link"><img*/}
                    {/*                        data-x-bind="BookPoster(item.book)" /></a><span*/}
                    {/*                        className="book-cover-shadow"></span></div>*/}
                    {/*                </div>*/}
                    {/*                <div data-x-show="index > 0" className="flex pt-3 px-3"><img data-x-show="index < 3"*/}
                    {/*                                                                             className="w-6 h-6"*/}
                    {/*                                                                             data-x-bind="RankIcon(index)" />*/}
                    {/*                    <div data-x-show="index > 2" data-x-text="index+1"*/}
                    {/*                         className="w-6 text-title text-center"></div>*/}
                    {/*                    <a data-x-bind="BookUrl(item.book)" data-x-text="item.book.name" target="_blank"*/}
                    {/*                       className="w-full text-title truncate mx-2"></a>*/}
                    {/*                    <div data-x-text="item.totalPromotions" className="text-gray-500"></div>*/}
                    {/*                </div>*/}
                    {/*            </div>*/}
                    {/*        </template>*/}
                    {/*    </div>*/}
                    {/*</template>*/}
                    <div>
                        <template>
                            <div className="box-content divide-y divide-dotted divide-auto">
                                <div
                                    data-x-show="index === 0"
                                    className="flex space-x-2 pt-3 px-3"
                                >
                                    <img
                                        data-x-bind="RankIcon(index)"
                                        className="w-6 h-6"
                                        alt="rank-1"
                                    />
                                    <div className="w-full relative">
                                        <a
                                            data-x-bind="BookUrl(item.book)"
                                            data-x-text="item.book.name"
                                            target="_blank"
                                            className="text-title font-semibold"
                                        ></a>
                                        <div className="flex item-center space-x-2 text-primary my-1">
                                            <span
                                                data-x-text="item.totalPromotions"
                                                className="text-sm font-semibold mr-1"
                                            ></span>{' '}
                                            lượt đề cử
                                        </div>
                                        <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">
                                            <div className="flex items-center space-x-1">
                                                <svg
                                                    className="w-4 h-4"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                    fill="none"
                                                    viewBox="0 0 24 24"
                                                    strokeWidth="1.5"
                                                    stroke="currentColor"
                                                    aria-hidden="true"
                                                    data-slot="icon"
                                                >
                                                    <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>
                                                </svg>
                                                <span
                                                    data-x-bind="BookAuthor(item.book)"
                                                    className="text-xs truncate"
                                                ></span>
                                            </div>
                                            <div className="flex items-center space-x-1">
                                                <svg
                                                    className="w-4 h-4"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                    fill="none"
                                                    viewBox="0 0 24 24"
                                                    stroke-width="1.5"
                                                    stroke="currentColor"
                                                    aria-hidden="true"
                                                    data-slot="icon"
                                                >
                                                    <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>
                                                </svg>
                                                <span
                                                    data-x-text="item.book.genres[0].name"
                                                    className="text-xs truncate"
                                                ></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="book-cover">
                                        <a
                                            data-x-bind="BookUrl(item.book)"
                                            target="_blank"
                                            className="book-cover-link"
                                        >
                                            <img data-x-bind="BookPoster(item.book)" />
                                        </a>
                                        <span className="book-cover-shadow"></span>
                                    </div>
                                </div>
                                <div
                                    data-x-show="index > 0"
                                    className="flex pt-3 px-3"
                                >
                                    <img
                                        data-x-show="index < 3"
                                        className="w-6 h-6"
                                        data-x-bind="RankIcon(index)"
                                    />
                                    <div
                                        data-x-show="index > 2"
                                        data-x-text="index+1"
                                        className="w-6 text-title text-center"
                                    ></div>
                                    <a
                                        data-x-bind="BookUrl(item.book)"
                                        data-x-text="item.book.name"
                                        target="_blank"
                                        className="w-full text-title truncate mx-2"
                                    ></a>
                                    <div
                                        data-x-text="item.totalPromotions"
                                        className="text-gray-500"
                                    ></div>
                                </div>
                            </div>
                        </template>
                        <div>
                            {data.map((item, index) => {
                                return (
                                    <RankedBookItem
                                        key={index}
                                        index={index}
                                        item={item}
                                    />
                                    )
                                }
                            )}
                        </div>
                        {/*First item*/}
                        {/*<div className="box-content divide-y divide-dotted divide-auto">*/}
                        {/*    <div data-x-show="index === 0" className="flex space-x-2 pt-3 px-3"><img*/}
                        {/*        data-x-bind="RankIcon(index)" className="w-6 h-6" alt="rank-1"*/}
                        {/*        src="https://static.cdnno.com/static/rank-index-1.png" />*/}
                        {/*        <div className="w-full relative"><a data-x-bind="BookUrl(item.book)"*/}
                        {/*                                            data-x-text="item.book.name" target="_blank"*/}
                        {/*                                            className="text-title font-semibold"*/}
                        {/*                                            href="https://metruyencv.com/truyen/cao-vo-cung-binh-tinh-mot-chut-khac-van-goi-ta-tai-ach-cap"*/}
                        {/*                                            title="Cao Võ: Cũng Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp">Cao*/}
                        {/*            Võ: Cũng Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp</a>*/}
                        {/*            <div className="flex item-center space-x-2 text-primary my-1"><span*/}
                        {/*                data-x-text="item.totalPromotions"*/}
                        {/*                className="text-sm font-semibold mr-1">38</span> người đang đọc*/}
                        {/*            </div>*/}
                        {/*            <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-bind="BookAuthor(item.book)" className="text-xs truncate">Hoan Nhạc Tiểu Đông</span>*/}
                        {/*                </div>*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-text="item.book.genres[0].name" className="text-xs truncate">Đô Thị</span>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*        <div className="book-cover"><a data-x-bind="BookUrl(item.book)" target="_blank"*/}
                        {/*                                       className="book-cover-link"*/}
                        {/*                                       href="https://metruyencv.com/truyen/cao-vo-cung-binh-tinh-mot-chut-khac-van-goi-ta-tai-ach-cap"*/}
                        {/*                                       title="Cao Võ: Cũng Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp"><img*/}
                        {/*            data-x-bind="BookPoster(item.book)"*/}
                        {/*            src="https://static.cdnno.com/poster/cao-vo-cung-binh-tinh-mot-chut-khac-van-goi-ta-tai-ach-cap/150.jpg?1751539055"*/}
                        {/*            alt="Cao Võ: Cũng Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp" /></a><span*/}
                        {/*            className="book-cover-shadow"></span></div>*/}
                        {/*    </div>*/}
                        {/*    <div data-x-show="index > 0" className="flex pt-3 px-3" style={{ display: 'none' }}><img*/}
                        {/*        data-x-show="index < 3" className="w-6 h-6" data-x-bind="RankIcon(index)"*/}
                        {/*        src="https://static.cdnno.com/static/rank-index-1.png" />*/}
                        {/*        <div data-x-show="index > 2" data-x-text="index+1"*/}
                        {/*             className="w-6 text-title text-center" style={{ display: 'none' }}>1*/}
                        {/*        </div>*/}
                        {/*        <a data-x-bind="BookUrl(item.book)" data-x-text="item.book.name" target="_blank"*/}
                        {/*           className="w-full text-title truncate mx-2"*/}
                        {/*           href="https://metruyencv.com/truyen/cao-vo-cung-binh-tinh-mot-chut-khac-van-goi-ta-tai-ach-cap"*/}
                        {/*           title="Cao Võ: Cũng Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp">Cao Võ: Cũng*/}
                        {/*            Bình Tĩnh Một Chút, Khác Vẫn Gọi Ta Tai Ách Cấp</a>*/}
                        {/*        <div data-x-text="item.totalPromotions" className="text-gray-500">38</div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                        {/*/!*Second item*!/*/}
                        {/*<div className="box-content divide-y divide-dotted divide-auto">*/}
                        {/*    <div data-x-show="index === 0" className="flex space-x-2 pt-3 px-3" style={{ display: 'none' }}>*/}
                        {/*        <img data-x-bind="RankIcon(index)" className="w-6 h-6" alt="rank-1"*/}
                        {/*             src="https://static.cdnno.com/static/rank-index-2.png" />*/}
                        {/*        <div className="w-full relative"><a data-x-bind="BookUrl(item.book)"*/}
                        {/*                                            data-x-text="item.book.name" target="_blank"*/}
                        {/*                                            className="text-title font-semibold"*/}
                        {/*                                            href="https://metruyencv.com/truyen/som-dang-luc-the-gioi-tro-choi-bat-dau-thong-gia-nu-de"*/}
                        {/*                                            title="Sớm Đăng Lục Thế Giới Trò Chơi, Bắt Đầu Thông Gia Nữ Đế">Sớm*/}
                        {/*            Đăng Lục Thế Giới Trò Chơi, Bắt Đầu Thông Gia Nữ Đế</a>*/}
                        {/*            <div className="flex item-center space-x-2 text-primary my-1"><span*/}
                        {/*                data-x-text="item.totalPromotions"*/}
                        {/*                className="text-sm font-semibold mr-1">25</span> người đang đọc*/}
                        {/*            </div>*/}
                        {/*            <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-bind="BookAuthor(item.book)" className="text-xs truncate">Tiêu Sở Hoàn Một Thụy</span>*/}
                        {/*                </div>*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-text="item.book.genres[0].name" className="text-xs truncate">Huyền Huyễn</span>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*        <div className="book-cover"><a data-x-bind="BookUrl(item.book)" target="_blank"*/}
                        {/*                                       className="book-cover-link"*/}
                        {/*                                       href="https://metruyencv.com/truyen/som-dang-luc-the-gioi-tro-choi-bat-dau-thong-gia-nu-de"*/}
                        {/*                                       title="Sớm Đăng Lục Thế Giới Trò Chơi, Bắt Đầu Thông Gia Nữ Đế"><img*/}
                        {/*            data-x-bind="BookPoster(item.book)"*/}
                        {/*            src="https://static.cdnno.com/poster/som-dang-luc-the-gioi-tro-choi-bat-dau-thong-gia-nu-de/150.jpg?1741672088"*/}
                        {/*            alt="Sớm Đăng Lục Thế Giới Trò Chơi, Bắt Đầu Thông Gia Nữ Đế" /></a><span*/}
                        {/*            className="book-cover-shadow"></span></div>*/}
                        {/*    </div>*/}
                        {/*    <div data-x-show="index > 0" className="flex pt-3 px-3"><img data-x-show="index < 3"*/}
                        {/*                                                                 className="w-6 h-6"*/}
                        {/*                                                                 data-x-bind="RankIcon(index)"*/}
                        {/*                                                                 src="https://static.cdnno.com/static/rank-index-2.png" />*/}
                        {/*        <div data-x-show="index > 2" data-x-text="index+1"*/}
                        {/*             className="w-6 text-title text-center" style={{ display: 'none' }}>2*/}
                        {/*        </div>*/}
                        {/*        <a data-x-bind="BookUrl(item.book)" data-x-text="item.book.name" target="_blank"*/}
                        {/*           className="w-full text-title truncate mx-2"*/}
                        {/*           href="https://metruyencv.com/truyen/som-dang-luc-the-gioi-tro-choi-bat-dau-thong-gia-nu-de"*/}
                        {/*           title="Sớm Đăng Lục Thế Giới Trò Chơi, Bắt Đầu Thông Gia Nữ Đế">Sớm Đăng Lục Thế Giới*/}
                        {/*            Trò Chơi, Bắt Đầu Thông Gia Nữ Đế</a>*/}
                        {/*        <div data-x-text="item.totalPromotions" className="text-gray-500">25</div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}

                        {/*<div className="box-content divide-y divide-dotted divide-auto">*/}
                        {/*    <div data-x-show="index === 0" className="flex space-x-2 pt-3 px-3" style={{ display: 'none' }}>*/}
                        {/*        <img data-x-bind="RankIcon(index)" className="w-6 h-6" alt="rank-1"*/}
                        {/*             src="https://static.cdnno.com/static/rank-index-3.png" />*/}
                        {/*        <div className="w-full relative"><a data-x-bind="BookUrl(item.book)"*/}
                        {/*                                            data-x-text="item.book.name" target="_blank"*/}
                        {/*                                            className="text-title font-semibold"*/}
                        {/*                                            href="https://metruyencv.com/truyen/vot-thi-nhan"*/}
                        {/*                                            title="Vớt Thi Nhân">Vớt Thi Nhân</a>*/}
                        {/*            <div className="flex item-center space-x-2 text-primary my-1"><span*/}
                        {/*                data-x-text="item.totalPromotions"*/}
                        {/*                className="text-sm font-semibold mr-1">24</span> người đang đọc*/}
                        {/*            </div>*/}
                        {/*            <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-bind="BookAuthor(item.book)" className="text-xs truncate">Thuần Khiết Tích Tiểu Long</span>*/}
                        {/*                </div>*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-text="item.book.genres[0].name" className="text-xs truncate">Đô Thị</span>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*        <div className="book-cover"><a data-x-bind="BookUrl(item.book)" target="_blank"*/}
                        {/*                                       className="book-cover-link"*/}
                        {/*                                       href="https://metruyencv.com/truyen/vot-thi-nhan"*/}
                        {/*                                       title="Vớt Thi Nhân"><img*/}
                        {/*            data-x-bind="BookPoster(item.book)"*/}
                        {/*            src="https://static.cdnno.com/poster/vot-thi-nhan/150.jpg?1727948140"*/}
                        {/*            alt="Vớt Thi Nhân" /></a><span className="book-cover-shadow"></span></div>*/}
                        {/*    </div>*/}
                        {/*    <div data-x-show="index > 0" className="flex pt-3 px-3"><img data-x-show="index < 3"*/}
                        {/*                                                                 className="w-6 h-6"*/}
                        {/*                                                                 data-x-bind="RankIcon(index)"*/}
                        {/*                                                                 src="https://static.cdnno.com/static/rank-index-3.png" />*/}
                        {/*        <div data-x-show="index > 2" data-x-text="index+1"*/}
                        {/*             className="w-6 text-title text-center" style={{ display: 'none' }}>3*/}
                        {/*        </div>*/}
                        {/*        <a data-x-bind="BookUrl(item.book)" data-x-text="item.book.name" target="_blank"*/}
                        {/*           className="w-full text-title truncate mx-2"*/}
                        {/*           href="https://metruyencv.com/truyen/vot-thi-nhan" title="Vớt Thi Nhân">Vớt Thi*/}
                        {/*            Nhân</a>*/}
                        {/*        <div data-x-text="item.totalPromotions" className="text-gray-500">24</div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                        {/*<div className="box-content divide-y divide-dotted divide-auto">*/}
                        {/*    <div data-x-show="index === 0" className="flex space-x-2 pt-3 px-3" style={{ display: 'none' }}>*/}
                        {/*        <img data-x-bind="RankIcon(index)" className="w-6 h-6" alt="rank-1" />*/}
                        {/*        <div className="w-full relative"><a data-x-bind="BookUrl(item.book)"*/}
                        {/*                                            data-x-text="item.book.name" target="_blank"*/}
                        {/*                                            className="text-title font-semibold"*/}
                        {/*                                            href="https://metruyencv.com/truyen/tu-hai-nhi-bat-dau-nhap-dao"*/}
                        {/*                                            title="Từ Hài Nhi Bắt Đầu Nhập Đạo">Từ Hài Nhi Bắt*/}
                        {/*            Đầu Nhập Đạo</a>*/}
                        {/*            <div className="flex item-center space-x-2 text-primary my-1"><span*/}
                        {/*                data-x-text="item.totalPromotions"*/}
                        {/*                className="text-sm font-semibold mr-1">19</span> người đang đọc*/}
                        {/*            </div>*/}
                        {/*            <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-948116e15f65921d47d0b205070bf6d6"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-bind="BookAuthor(item.book)"*/}
                        {/*                          className="text-xs truncate">Cổ Hi</span></div>*/}
                        {/*                <div className="flex items-center space-x-1">*/}
                        {/*                    <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none"*/}
                        {/*                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"*/}
                        {/*                         aria-hidden="true" data-slot="icon">*/}
                        {/*                        <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306"></use>*/}
                        {/*                    </svg>*/}
                        {/*                    <span data-x-text="item.book.genres[0].name" className="text-xs truncate">Huyền Huyễn</span>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*        <div className="book-cover"><a data-x-bind="BookUrl(item.book)" target="_blank"*/}
                        {/*                                       className="book-cover-link"*/}
                        {/*                                       href="https://metruyencv.com/truyen/tu-hai-nhi-bat-dau-nhap-dao"*/}
                        {/*                                       title="Từ Hài Nhi Bắt Đầu Nhập Đạo"><img*/}
                        {/*            data-x-bind="BookPoster(item.book)"*/}
                        {/*            src="https://static.cdnno.com/poster/tu-hai-nhi-bat-dau-nhap-dao/150.jpg?1714738581"*/}
                        {/*            alt="Từ Hài Nhi Bắt Đầu Nhập Đạo" /></a><span className="book-cover-shadow"></span>*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*    <div data-x-show="index > 0" className="flex pt-3 px-3"><img data-x-show="index < 3"*/}
                        {/*                                                                 className="w-6 h-6"*/}
                        {/*                                                                 data-x-bind="RankIcon(index)"*/}
                        {/*                                                                 style={{ display: 'none' }} />*/}
                        {/*        <div data-x-show="index > 2" data-x-text="index+1"*/}
                        {/*             className="w-6 text-title text-center">4*/}
                        {/*        </div>*/}
                        {/*        <a data-x-bind="BookUrl(item.book)" data-x-text="item.book.name" target="_blank"*/}
                        {/*           className="w-full text-title truncate mx-2"*/}
                        {/*           href="https://metruyencv.com/truyen/tu-hai-nhi-bat-dau-nhap-dao"*/}
                        {/*           title="Từ Hài Nhi Bắt Đầu Nhập Đạo">Từ Hài Nhi Bắt Đầu Nhập Đạo</a>*/}
                        {/*        <div data-x-text="item.totalPromotions" className="text-gray-500">19</div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                    </div>
                </div>
            </div>
        </>
    );
}

export default TopRankNovel;
const RankedBookItem = ({ index, item }) => {
    const isTop = index === 0;
    const isTop3 = index < 3;
    const rankIcon = `https://static.cdnno.com/static/rank-index-${index + 1}.png`;
    const navigate = useNavigate();

    function handleNavigate(novelSlug) {
        navigate(`/truyen/${novelSlug}`);
    }
    return (
        <div className="box-content divide-y divide-dotted divide-auto" onClick={() => handleNavigate(item.novelSlug)}>
            {isTop && item ? (
                <div className="flex space-x-2 pt-3 px-3">
                    <img
                        className="w-6 h-6"
                        src={rankIcon}
                        alt={`rank-${index + 1}`}
                    />
                    <div className="w-full relative">
                        <div
                            onClick={()=>handleNavigate(item.novelSlug)}
                            className="text-title font-semibold hover:cursor"
                            title={item.novelName}
                            rel="noreferrer"
                        >
                            {item.novelName}
                        </div>
                        <div className="flex items-center space-x-2 text-primary my-1">
                            <span className="text-sm font-semibold mr-1">
                                {item.totalPromotions}
                            </span>{' '}
                            lượt đề cử
                        </div>
                        <div className="text-gray-500 absolute bottom-0 left-0 space-y-1">
                            <div className="flex items-center space-x-1">
                                <svg
                                    className="w-4 h-4"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth={1.5}
                                    stroke="currentColor"
                                >
                                    <use href="#icon-948116e15f65921d47d0b205070bf6d6" />
                                </svg>
                                <span className="text-xs truncate">
                                    {/*{item.author}*/}
                                </span>
                            </div>
                            <div className="flex items-center space-x-1">
                                <svg
                                    className="w-4 h-4"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    strokeWidth={1.5}
                                    stroke="currentColor"
                                >
                                    <use href="#icon-b1ef988df00d1cd64eec3c265d6ac306" />
                                </svg>
                                <span className="text-xs truncate">
                                    {/*{item.book.genres[0]?.name}*/}
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="book-cover relative w-36 h-48">
                        <a
                            href={item.novelCoverImage}
                            target="_blank"
                            rel="noreferrer"
                            className="book-cover-link"
                            title={item.novelName}
                        >
                            <img src={item.novelCoverImage} alt={item.novelName} />
                        </a>
                        <span className="book-cover-shadow absolute inset-0" />
                    </div>
                </div>
            ) : (
                <div className="flex pt-3 px-3 items-center">
                    {isTop3 ? (
                        <img
                            className="w-6 h-6"
                            src={rankIcon}
                            alt={`rank-${index + 1}`}
                        />
                    ) : (
                        <div className="w-6 text-title text-center">
                            {index + 1}
                        </div>
                    )}
                    <a
                        href={item.novelCoverImage}
                        target="_blank"
                        className="w-full text-title truncate mx-2"
                        title={item.novelName}
                        rel="noreferrer"
                    >
                        {item.novelName}
                    </a>
                    <div className="text-gray-500">{item.totalPromotions}</div>
                </div>
            )}
        </div>
    );
};

function NovelDetailCard({ isBol }) {
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
                        <FontAwesomeIcon icon={faUser} className={"mr-2"} />
                        <span>
                            Nguyen Van A
                        </span>
                    </div>
                    <div className={"flex flex-row items-center text-gray-400"}>
                        <FontAwesomeIcon icon={faLayerGroup} className={"mr-2"} />
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

function NovelCard() {
    return (
        <>
            <div>
                <div className={`flex flex-1 flex-row border-b border-gray-500 border-dotted py-2 items-center`}>
                    <div className="w-1/10 text-sm">
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
