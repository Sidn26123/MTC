# 📚 Metruyenchu Clone - Web Đọc Truyện Chữ

## 🔍 Giới thiệu

Đây là một nền tảng đọc truyện chữ trực tuyến, được thiết kế theo hướng **dễ mở rộng**, và **thân thiện với người dùng**, lấy cảm hứng từ nền tảng như Metruyenchu, TruyenCV.

Trang web cho phép người dùng:
- Đọc truyện chữ theo chương
- Tìm kiếm, lọc, sắp xếp truyện
- Theo dõi truyện, đánh giá, bình luận, yêu thích, đánh dấu
- Tương tác xã hội (thích, báo cáo)
- Mua chương / truyện
- Chat với chatbot để tìm truyện / thông tin về web, term of service


## 🏗️ Kiến trúc hệ thống

Hệ thống được xây dựng theo kiến trúc **Microservices**, chia thành các module chính:

- **Frontend**: React + Zustand + TailwindCSS
- **Backend**: Spring Boot
- **AI module** (tuỳ chọn): Django 
- **Database**:
  - PostgreSQL cho dữ liệu chính
  - MongoDB cho log, thống kê
- **Storage**: Cloud (mã hoá nội dung .txt)
- **Message Queue**: Kafka(cho log)

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
- Quản lý thể loại, tag, đặc điểm nhân vật
- Thống kê theo ngày/tuần/tháng

## 📊 Thống kê

- Top truyện / chương theo lượt xem
- Phân tích hành vi người dùng
- Biểu đồ lượt xem, đánh giá, comment
- Truy vấn tùy chọn theo thời gian

## 📦 Cài đặt

### Backend

```bash
cd backend/
./mvnw spring-boot:run
