import React from "react";
import EditorRecommendNovelCard from "./EditorRecommendNovelCard.jsx";
import {faCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import 'swiper/css';
import 'swiper/css/pagination'; // Nếu dùng phân trang
import 'swiper/css/navigation';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper/modules'; // Nếu có nút điều hướng
function EditorRecommend() {


    return (
        <>
            {/*<div className="flex flex-col">*/}
            {/*    <div*/}
            {/*        id="editor-swiper"*/}
            {/*        className="swiper swiper-initialized swiper-horizontal swiper-pointer-events swiper-backface-hidden"*/}
            {/*    >*/}
            {/*        <div*/}
            {/*            className="box-content swiper-wrapper pb-7"*/}
            {/*            id="swiper-wrapper-7ddd6101df593adc"*/}
            {/*            aria-live="polite"*/}
            {/*            style={{*/}
            {/*                transitionDuration: "0ms",*/}
            {/*                transform: "translate3d(0px, 0px, 0px)",*/}
            {/*            }}*/}
            {/*        >*/}
            {/*            {[...Array(5)].map((_, index) => (*/}
            {/*                <div*/}
            {/*                    key={index}*/}
            {/*                    className={`swiper-slide grid grid-cols-1 gap-y-4 ${*/}
            {/*                        index === 0*/}
            {/*                            ? "swiper-slide-active"*/}
            {/*                            : index === 1*/}
            {/*                                ? "swiper-slide-next"*/}
            {/*                                : ""*/}
            {/*                    }`}*/}
            {/*                    style={{ width: "492px", marginRight: "40px" }}*/}
            {/*                    role="group"*/}
            {/*                    aria-label={`${index + 1} / 5`}*/}
            {/*                >*/}
            {/*                    <EditorRecommendNovelCard />*/}
            {/*                    <EditorRecommendNovelCard />*/}
            {/*                    <EditorRecommendNovelCard />*/}
            {/*                </div>*/}
            {/*            ))}*/}
            {/*        </div>*/}

            {/*        <div*/}
            {/*            className="swiper-pagination swiper-pagination-bullets swiper-pagination-horizontal"*/}
            {/*            style={{ bottom: "0px" }}*/}
            {/*        >*/}
            {/*            {[...Array(4)].map((_, idx) => (*/}
            {/*                <span*/}
            {/*                    key={idx}*/}
            {/*                    className={`swiper-pagination-bullet ${*/}
            {/*                        idx === 0 ? "swiper-pagination-bullet-active" : ""*/}
            {/*                    }`}*/}
            {/*                    {...(idx === 0 ? { "aria-current": "true" } : {})}*/}
            {/*                />*/}
            {/*            ))}*/}
            {/*        </div>*/}

            {/*        <span*/}
            {/*            className="swiper-notification"*/}
            {/*            aria-live="assertive"*/}
            {/*            aria-atomic="true"*/}
            {/*        ></span>*/}
            {/*    </div>*/}
            {/*<div className={"flex justify-between flex-wrap mt-2"}>*/}
            {/*    <EditorRecommendNovelCard />*/}
            {/*    <EditorRecommendNovelCard />*/}
            {/*    <EditorRecommendNovelCard />*/}
            {/*    <EditorRecommendNovelCard />*/}


            {/*</div>*/}
            {/*<div className={"flex flex-row justify-center flex-wrap mt-2"}>*/}
            {/*    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">*/}
            {/*        <circle cx="10" cy="10" r="5" fill="#FFD700" />*/}
            {/*    </svg>*/}
            {/*    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">*/}
            {/*        <circle cx="10" cy="10" r="5" fill="#FFD700" />*/}
            {/*    </svg>*/}
            {/*    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">*/}
            {/*        <circle cx="10" cy="10" r="5" fill="#FFD700" />*/}
            {/*    </svg>*/}
            {/*    <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">*/}
            {/*        <circle cx="10" cy="10" r="5" fill="#FFD700" />*/}
            {/*    </svg>*/}
            {/*</div>*/}

            {/*</div>*/}
            <div id="editor-swiper" className="swiper-custom-wrapper relative pb-10">
                <Swiper
                    modules={[Pagination]}
                    spaceBetween={40}
                    slidesPerView={1}
                    pagination={{ clickable: true }}
                    className="pb-7 !pt-2"
                >
                    {[...Array(5)].map((_, slideIndex) => (
                        <SwiperSlide key={slideIndex}>
                            <div className="grid grid-cols-1 gap-y-4 pt-[50px]" role="group" aria-label={`${slideIndex + 1} / 5`}>
                                <EditorRecommendNovelCard />
                                <EditorRecommendNovelCard />
                                <EditorRecommendNovelCard />
                            </div>
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </>
    );
}

export default EditorRecommend;
