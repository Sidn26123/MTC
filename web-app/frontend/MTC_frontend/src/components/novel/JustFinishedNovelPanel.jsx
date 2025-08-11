import React, { useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, FreeMode } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/free-mode';
import {
    useJustFinishedNovelListData,
    useSetJustFinishedNovelList,
    useSetPublishedByPublisher,
} from '../../stores/novelStore.js';
import { getJustPublishedChapters } from '../../services/chapterService.js';
import { useNavigate } from 'react-router';


const JustFinishedNovelPanel = () => {
    const navigate = useNavigate();
    const justFinishedNovelList = useJustFinishedNovelListData();
    const setJustFinishedNovelList = useSetJustFinishedNovelList();
    useEffect(() => {
        // if (!justFinishedNovelList || justFinishedNovelList.length === 0) {
            getJustPublishedChapters(10).then((response) => {
                setJustFinishedNovelList(
                    response.data.result
                )
            })
        // }
    }, []);

    // console.log("JustFinishedNovelPanel", justFinishedNovelList);


    return (
        <div id="completed">
            <a
                href="https://metruyencv.com/danh-sach/truyen-full"
                title="Xem thêm danh sách truyện full hoàn thành"
                className="box-title flex justify-between items-center mb-4"
            >
                <h2 className="text-lg font-semibold">MỚI HOÀN THÀNH</h2>
                <svg className="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                    <use href="#icon-45dfdae75c66f6db1091e91efb93d2f4" />
                </svg>
            </a>

            <Swiper
                modules={[Autoplay, FreeMode]}
                freeMode={true}
                autoplay={{
                    delay: 3000,
                    disableOnInteraction: false,
                }}
                spaceBetween={10}
                slidesPerView={2}
                breakpoints={{
                    640: { slidesPerView: 3 },
                    768: { slidesPerView: 4 },
                    1024: { slidesPerView: 5 },
                }}
            >
                {justFinishedNovelList && justFinishedNovelList.length && justFinishedNovelList.map((item, index) => {
                    return (
                        <SwiperSlide key={index}>

                            <div className="flex flex-col space-y-2 mx-auto w-full" onClick={()=> navigate(`/truyen/${item.novel.slug}`)}>
                                <a href={item.novel.link} title={item.novel.name} className="mx-auto block">
                                    <img
                                        src={item.novel.novelCoverImage}
                                        alt={item.novel.name}
                                        className="w-full aspect-[3/4] shadow-xl rounded"
                                    />
                                </a>
                                <a href={item.novel.link} name={item.novel.name} className="text-center block">
                                    <span className="text-title text-xs font-semibold">{item.novel.name}</span>
                                </a>
                            </div>
                        </SwiperSlide>
                        )

                    }
                )}
            </Swiper>
        </div>
    );
};

export default JustFinishedNovelPanel;