import React, { useEffect } from 'react';
import EditorRecommendNovelCard from "./EditorRecommendNovelCard.jsx";
import {faCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import 'swiper/css';
import 'swiper/css/pagination'; // Nếu dùng phân trang
import 'swiper/css/navigation';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper/modules';
import { getBestNovels } from '../../services/novelService.js';
import { useBestNovel, useSetBestNovel } from '../../stores/novelStore.js';
import { findItemInFilterStore } from '../../services/novelFilterService.js'; // Nếu có nút điều hướng
function EditorRecommend() {
    const bestNovel = useBestNovel();
    const setBestNovel = useSetBestNovel();

    useEffect(() => {
        getBestNovels(10).then((response) => {
            setBestNovel(response.data.result);
        });

    }, []);
    return (
        <div id="editor-swiper" className="swiper-custom-wrapper relative pb-10">
            <Swiper
                modules={[Pagination]}
                spaceBetween={40}
                slidesPerView={1}
                pagination={{ clickable: true }}
                className="pb-7 !pt-2"
            >
                {/* Chia thành từng nhóm 3 item 1 slide */}
                {chunkArray(bestNovel, 3).map((group, slideIndex) => (
                    <SwiperSlide key={slideIndex}>
                        <div className="grid grid-cols-1 gap-y-4 pt-[50px]" role="group" aria-label={`${slideIndex + 1}`}>
                            {group.map((novel) => (
                                <EditorRecommendNovelCard key={novel.id} novel={novel} />
                            ))}
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
    // return (
    //     <>
    //
    //         <div id="editor-swiper" className="swiper-custom-wrapper relative pb-10">
    //             <Swiper
    //                 modules={[Pagination]}
    //                 spaceBetween={40}
    //                 slidesPerView={1}
    //                 pagination={{ clickable: true }}
    //                 className="pb-7 !pt-2"
    //             >
    //                 {[...Array(5)].map((_, slideIndex) => (
    //                     <SwiperSlide key={slideIndex}>
    //                         <div className="grid grid-cols-1 gap-y-4 pt-[50px]" role="group" aria-label={`${slideIndex + 1} / 5`}>
    //                             <EditorRecommendNovelCard />
    //                             <EditorRecommendNovelCard />
    //                             <EditorRecommendNovelCard />
    //                         </div>
    //                     </SwiperSlide>
    //                 ))}
    //             </Swiper>
    //         </div>
    //     </>
    // );
}
function chunkArray(arr, size) {
    return Array.from({ length: Math.ceil(arr.length / size) }, (_, i) =>
        arr.slice(i * size, i * size + size)
    );
}
export default EditorRecommend;
