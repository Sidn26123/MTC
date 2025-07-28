import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router";


function EditorRecommendNovelCard() {
    return (
        <>
            <div className="flex space-x-3 pt-3 px-3">
                <div className="flex-shrink-0"><a href="https://metruyencv.com/truyen/quy-dao-than-thoai"
                                                  title="Quỷ Đạo Thần Thoại" className="text-title font-semibold"><img
                    className="h-32 w-24 shadow-xl rounded"
                    src="https://static.cdnno.com/poster/quy-dao-than-thoai/300.jpg?1741928087"
                    alt="Quỷ Đạo Thần Thoại Poster" loading="lazy" /></a></div>
                <div className="space-y-2">
                    <div><a href="https://metruyencv.com/truyen/quy-dao-than-thoai" title="Quỷ Đạo Thần Thoại"
                            className="text-title font-semibold">Quỷ Đạo Thần Thoại</a></div>
                    <div className="text-gray-500 text-overflow-multiple-lines break-all"> Tác phẩm giới thiệu vắn tắt
                        Đây
                        là quỷ cùng thần thoại xen lẫn thế giới, nghe nhiều nên thuộc thần thoại điển cố bên trong diễn
                        sinh
                        ...
                    </div>
                    <div className="flex justify-between items-center space-x-2 pt-1">
                        <div className="flex grow-0 items-center space-x-1">
                            <svg className="w-4 h-4 text-gray-500" xmlns="http://www.w3.org/2000/svg"
                                 viewBox="0 0 24 24"
                                 fill="currentColor" aria-hidden="true" data-slot="icon">
                                <use href="#icon-7a0aee03d160b63202cf57690fd86d3c"></use>
                            </svg>
                            <a href="https://metruyencv.com/tac-gia/6279-thuc-chuc-de-trung-chi-de"
                               className="text-title">Thực
                                Chúc Đệ Trung Chi Đệ</a></div>
                        <a href="https://metruyencv.com/danh-sach/truyen-tien-hiep" title="Danh sách truyện Tiên Hiệp"
                           className="hidden md:flex shrink-0 outline outline-1 px-2 py-1 text-primary rounded"><span
                            className="text-xs">Tiên Hiệp</span></a></div>
                </div>
            </div>

        </>
    )
        ;
}

export default EditorRecommendNovelCard;