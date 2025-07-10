# 📚 Metruyenchu Clone - Web Đọc Truyện Chữ

## 🔍 Giới thiệu

Đây là một nền tảng đọc truyện chữ trực tuyến, được thiết kế theo hướng **dễ mở rộng**, và **thân thiện với người dùng**, lấy cảm hứng từ nền tảng như Metruyenchu, TruyenCV. Thiết kế cho 3 nhóm người dùng: staff, normal user và publisher

Trang web gồm các chức năng chính như sau:
- Đọc truyện chữ theo chương
- Tìm kiếm, lọc, sắp xếp truyện
- Theo dõi truyện, đánh giá, bình luận, yêu thích, đánh dấu, báo cáo vi phạm.
- Mua chương / truyện
- Chat với chatbot để tìm truyện / thông tin về web, term of service dựa trên ngôn ngữ tự nhiên
- Đăng truyện, quản lý truyện
- Quản lý tổng quát
- Thống kê


## 🏗️ Kiến trúc hệ thống

Hệ thống được xây dựng theo kiến trúc **Microservices**, chia thành các module chính:

![image](https://github.com/user-attachments/assets/be067d02-547d-46c7-bbdb-26ed4f80bb19)

<img width="663" height="908" alt="image" src="https://github.com/user-attachments/assets/00d772f1-3a7a-45b9-8d30-8eeebf87e95f" />


- **Frontend**: React + Zustand + TailwindCSS
- **Backend**: Spring Framework
- **AI module** : Django 
- **Database**:
  - PostgreSQL 
- **Storage**: Cloudinary (mã hoá nội dung .txt)
- **Message**: Kafka, OpenFeign (giao tiếp giữa các service cần kết quả ngay)


Cấu trúc thư mục service:


## ⚙️ Các chức năng chính

### 🧑‍💻 Người dùng

- Đăng ký / Đăng nhập / Phân quyền (User, Mod, Admin, Owner)
- Đọc truyện, lưu chương đã đọc
- Theo dõi / Yêu thích truyện
- Đánh giá, bình luận
- Mua VIP, nạp xu, mở khóa chương

### ✍️ Người đăng truyện

- Tạo truyện mới
- Thêm chương, chỉnh sửa nội dung
- Xem thống kê đọc, thu nhập
- Quản lý nội dung và phản hồi người dùng

### 🔐 Quản trị

- Duyệt truyện/chương
- Quản lý người dùng, báo cáo
- Quản lý thể loại, tag, đặc điểm nhân vật ...
- Thống kê theo ngày/tuần/tháng

## 📊 Thống kê

- Top truyện / chương theo lượt xem
- Phân tích hành vi người dùng
- Biểu đồ lượt xem, đánh giá, comment
- Truy vấn tùy chọn theo thời gian


