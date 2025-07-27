import React from "react";
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, FreeMode } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/free-mode';

const novelList = [
    {
        title: 'Giải Trí: Ai Nói Ngư Dân Liền Không Thể Làm Nghệ Thuật?',
        link: 'https://metruyencv.com/truyen/giai-tri-ai-noi-ngu-dan-lien-khong-the-lam-nghe-thuat',
        image: 'https://static.cdnno.com/poster/giai-tri-ai-noi-ngu-dan-lien-khong-the-lam-nghe-thuat/300.jpg?1750320198',
    },
    {
        title: 'Dị Năng: Song Thiên Phú, Lôi Điện Pháp Vương Chỉ Muốn Bày Nát',
        link: 'https://metruyencv.com/truyen/di-nang-song-thien-phu-loi-dien-phap-vuong-chi-muon-bay-nat',
        image: 'https://static.cdnno.com/poster/di-nang-song-thien-phu-loi-dien-phap-vuong-chi-muon-bay-nat/300.jpg?1747020845',
    },
    {
        title: 'Thiên Mệnh Cẩm Y Vệ: Bắt Đầu Diệt Môn Bát Đại Tấn Thương',
        link: 'https://metruyencv.com/truyen/thien-menh-cam-y-ve-bat-dau-diet-mon-bat-dai-tan-thuong',
        image: 'https://static.cdnno.com/poster/thien-menh-cam-y-ve-bat-dau-diet-mon-bat-dai-tan-thuong/300.jpg?1749560579',
    },
    {
        title: 'Bạn Gái Mang Thai Ta Dựa Vào Bắt Cá Xong Bợ Đỡ Mẹ Vợ',
        link: 'https://metruyencv.com/truyen/ban-gai-mang-thai-ta-dua-vao-bat-ca-xong-bo-do-me-vo',
        image: 'https://static.cdnno.com/poster/ban-gai-mang-thai-ta-dua-vao-bat-ca-xong-bo-do-me-vo/300.jpg?1749477044',
    },
    {
        title: 'Tông Môn Ta Tối Cường Ngươi Cùng Ta Giảng Đạo Lý',
        link: 'https://metruyencv.com/truyen/tong-mon-ta-toi-cuong-nguoi-cung-ta-giang-dao-ly',
        image: 'https://static.cdnno.com/poster/tong-mon-ta-toi-cuong-nguoi-cung-ta-giang-dao-ly/300.jpg?1751349000',
    },
    {
        title: 'Tận Thế Thức Tỉnh, Đánh Quái Tiểu Đội Lên Lên Lên',
        link: 'https://metruyencv.com/truyen/tan-the-thuc-tinh-danh-quai-tieu-doi-len-len-len',
        image: 'https://static.cdnno.com/poster/tan-the-thuc-tinh-danh-quai-tieu-doi-len-len-len/300.jpg?1733918627',
    },
    {
        title: 'Đoạn Tuyệt Quan Hệ Về Sau, Ta Triệu Hoán Thú Tất Cả Đều Là Hắc Ám Sinh Vật',
        link: 'https://metruyencv.com/truyen/doan-tuyet-quan-he-ve-sau-ta-trieu-hoan-thu-tat-ca-deu-la-hac-am-sinh-vat',
        image: 'https://static.cdnno.com/poster/doan-tuyet-quan-he-ve-sau-ta-trieu-hoan-thu-tat-ca-deu-la-hac-am-sinh-vat/300.jpg?1733045996',
    },
    {
        title: 'Phàm Nhân: Đánh Quái Thăng Cấp, Ta Hưởng Trường Sinh!',
        link: 'https://metruyencv.com/truyen/pham-nhan-danh-quai-thang-cap-ta-huong-truong-sinh',
        image: 'https://static.cdnno.com/poster/pham-nhan-danh-quai-thang-cap-ta-huong-truong-sinh/300.jpg?1712642621',
    },
    {
        title: 'Nạn Đói Lớn, Ta Nhà Kho Nuôi Cổ Đại Nữ Đế',
        link: 'https://metruyencv.com/truyen/nan-doi-lon-ta-nha-kho-nuoi-co-dai-nu-de',
        image: 'https://static.cdnno.com/poster/nan-doi-lon-ta-nha-kho-nuoi-co-dai-nu-de/300.jpg?1727625339',
    },
    {
        title: 'Dragon Ball: Đột Phá Cực Hạn Người Saiya',
        link: 'https://metruyencv.com/truyen/dragon-ball-dot-pha-cuc-han-nguoi-saiya',
        image: 'https://static.cdnno.com/poster/dragon-ball-dot-pha-cuc-han-nguoi-saiya/300.jpg?1753193506',
    },
];

const JustFinishedNovelCard = () => {
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
                {novelList.map((novel, index) => (
                    <SwiperSlide key={index}>
                        <div className="flex flex-col space-y-2 mx-auto w-full">
                            <a href={novel.link} title={novel.title} className="mx-auto block">
                                <img
                                    src={novel.image}
                                    alt={novel.title}
                                    className="w-full aspect-[3/4] shadow-xl rounded"
                                />
                            </a>
                            <a href={novel.link} title={novel.title} className="text-center block">
                                <span className="text-title text-xs font-semibold">{novel.title}</span>
                            </a>
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default JustFinishedNovelCard;