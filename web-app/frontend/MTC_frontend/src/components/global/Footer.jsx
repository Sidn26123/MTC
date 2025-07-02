import React from "react"
const Footer = () => {
    return (
        <footer className="mt-5 p-5 text-center text-gray-500 border-t border-auto mx-auto max-w-screen-lg">
            <div className="text-center">
                Mê Truyện Chữ là nền tảng mở trực tuyến, miễn phí đọc truyện chữ được đóng góp nội dung từ các tác giả viết truyện và các dịch giả convert, dịch truyện, rất nhiều truyện hay và nổi bật được cập nhật nhanh nhất với đủ các thể loại tiên hiệp, kiếm hiệp, huyền ảo ...
            </div>
            <img
                className="w-16 h-16 mx-auto my-2"
                src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png"
                alt="Logo"
            />
            <div className="flex flex-col space-y-2 sm:flex-row sm:justify-center sm:space-x-6 sm:space-y-0">
                <a href="https://metruyencv.com/thong-tin/dieu-khoan-dich-vu" target="_blank" rel="noopener noreferrer">
                    Điều khoản dịch vụ
                </a>
                <a href="https://metruyencv.com/thong-tin/chinh-sach-bao-mat" target="_blank" rel="noopener noreferrer">
                    Chính sách bảo mật
                </a>
                <a href="https://metruyencv.com/thong-tin/ve-ban-quyen" target="_blank" rel="noopener noreferrer">
                    Về bản quyền
                </a>
                <a href="https://metruyencv.com/thong-tin/hoi-dap" target="_blank" rel="noopener noreferrer">
                    Hướng dẫn sử dụng
                </a>
            </div>
        </footer>
    );
};

export default Footer;
