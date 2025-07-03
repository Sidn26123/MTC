package com.sidn.metruyenchu.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "Người dùng đã tồn tại!", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR(9999, "Lỗi không xác định", HttpStatus.BAD_REQUEST),
    INVALID_KEY(0001, "Message key không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username không hợp lệ. Username phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password không hợp lệ. Password phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng này không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Không có quyền để làm điều này", HttpStatus.FORBIDDEN),
    INVALID_BIRTHDAY(1008, "Tuổi của bạn phải lớn hơn {min}", HttpStatus.BAD_REQUEST),
    CHAPTER_STATUS_NAME_EXISTED(1009, "Trạng thái chương này da ton tai", HttpStatus.INTERNAL_SERVER_ERROR),
    CHAPTER_STATUS_NAME_NOT_EXISTED(1010, "Chapter status khong ton tai", HttpStatus.BAD_REQUEST),
    NOVEL_NOT_FOUND(2001, "Truyện không tồn tại", HttpStatus.NOT_FOUND),
    CHAPTER_NOT_FOUND(2002, "Chương không tồn tại", HttpStatus.NOT_FOUND),
    GENRE_NOT_FOUND(2003, "Thể loại không tồn tại", HttpStatus.NOT_FOUND),
    SECT_NOT_FOUND(2004, "Tông truyện không tồn tại", HttpStatus.NOT_FOUND),
    WORLD_SCENE_NOT_FOUND(2005, "Bối cảnh thế giới không tồn tại", HttpStatus.NOT_FOUND),
    MAIN_CHARACTER_TRAIT_NOT_FOUND(2006, "Tính cách nhân vật chính không tồn tại", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_FOUND(2007, "Tác giả không tồn tại", HttpStatus.NOT_FOUND),
    NOVEL_ALREADY_EXISTS(2008, "Truyện đã tồn tại", HttpStatus.BAD_REQUEST),
    CHAPTER_ALREADY_EXISTS(2009, "Chương đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_NOVEL_STATUS(2010, "Trạng thái truyện không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CHAPTER_STATUS(2011, "Trạng thái chương không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_MODERATION_STATUS(2012, "Trạng thái kiểm duyệt không hợp lệ", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(3001, "Bình luận không tồn tại", HttpStatus.NOT_FOUND),
    RATING_NOT_FOUND(3002, "Đánh giá không tồn tại", HttpStatus.NOT_FOUND),
    REPORT_NOT_FOUND(3003, "Báo cáo không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_RATING_VALUE(3004, "Giá trị đánh giá không hợp lệ. Phải từ {min} đến {max}", HttpStatus.BAD_REQUEST),
    COMMENT_ALREADY_EXISTS(3005, "Bình luận đã tồn tại", HttpStatus.BAD_REQUEST),
    REPORT_ALREADY_EXISTS(3006, "Báo cáo đã tồn tại", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_OTHER_USER_COMMENT(3007, "Không thể xóa bình luận của người khác", HttpStatus.FORBIDDEN),
    CANNOT_REPORT_OWN_CONTENT(3008, "Không thể tự báo cáo nội dung của mình", HttpStatus.BAD_REQUEST),
    SEARCH_QUERY_TOO_SHORT(4001, "Từ khóa tìm kiếm quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    SEARCH_ENGINE_ERROR(4002, "Lỗi hệ thống tìm kiếm", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_RESULTS_FOUND(4003, "Không tìm thấy kết quả phù hợp", HttpStatus.NOT_FOUND),
    INVALID_SEARCH_FILTER(4004, "Bộ lọc tìm kiếm không hợp lệ", HttpStatus.BAD_REQUEST),
    CHATBOT_PROCESSING_ERROR(4005, "Lỗi xử lý chatbot AI", HttpStatus.INTERNAL_SERVER_ERROR),
    RECOMMENDATION_ENGINE_ERROR(5001, "Lỗi hệ thống đề xuất", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_RECOMMENDATION_FOUND(5002, "Không tìm thấy truyện đề xuất phù hợp", HttpStatus.NOT_FOUND),
    INVALID_USER_BEHAVIOR_DATA(5003, "Dữ liệu hành vi người dùng không hợp lệ", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(6001, "Số dư tài khoản không đủ", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_METHOD(6002, "Phương thức thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    TRANSACTION_NOT_FOUND(6003, "Giao dịch không tồn tại", HttpStatus.NOT_FOUND),
    PAYMENT_GATEWAY_ERROR(6004, "Lỗi hệ thống thanh toán", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TRANSACTION_AMOUNT(6005, "Số tiền giao dịch không hợp lệ", HttpStatus.BAD_REQUEST),
    DUPLICATE_TRANSACTION(6006, "Giao dịch trùng lặp", HttpStatus.BAD_REQUEST),
    PAYMENT_VERIFICATION_FAILED(6007, "Xác minh thanh toán thất bại", HttpStatus.FORBIDDEN),
    FILE_NOT_FOUND(7001, "Tệp tin không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_FILE_TYPE(7002, "Loại tệp không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(7003, "Tải tệp lên thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_SIZE_EXCEEDED(7004, "Dung lượng tệp vượt quá giới hạn {max} MB", HttpStatus.BAD_REQUEST),
    FILE_STORAGE_ERROR(7005, "Lỗi hệ thống lưu trữ tệp", HttpStatus.INTERNAL_SERVER_ERROR),

    ROLE_NOT_EXISTED(1002, "Role không tồn tại", HttpStatus.INTERNAL_SERVER_ERROR),


    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
