package com.sidn.metruyenchu.shared_library.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "Người dùng đã tồn tại!", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(0002, "Lỗi máy chủ nội bộ", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(0003, "Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR(9999, "Lỗi không xác định", HttpStatus.BAD_REQUEST),
    OBJECT_NOT_FOUND(1002, "Không tìm thấy đối tượng", HttpStatus.NOT_FOUND),
    OBJECT_ALREADY_EXISTED(1003, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_KEY(0001, "Message key không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username không hợp lệ. Username phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password không hợp lệ. Password phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng này không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Không có quyền để làm điều này", HttpStatus.FORBIDDEN),
    INVALID_BIRTHDAY(1008, "Tuổi của bạn phải lớn hơn {min}", HttpStatus.BAD_REQUEST),
    FEIGN_ERROR(1009, "Lỗi gọi API", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(1010, "Không tìm thấy", HttpStatus.NOT_FOUND),
    SERVICE_UNAVAILABLE(1011, "Dịch vụ không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),

    CHAPTER_STATUS_NAME_EXISTED(1009, "Trạng thái chương này da ton tai", HttpStatus.INTERNAL_SERVER_ERROR),
    CHAPTER_STATUS_NAME_NOT_EXISTED(1010, "Chapter status khong ton tai", HttpStatus.BAD_REQUEST),
    NOVEL_NOT_FOUND(2001, "Truyện không tồn tại", HttpStatus.NOT_FOUND),
    CHAPTER_NOT_FOUND(2002, "Chương không tồn tại", HttpStatus.NOT_FOUND),

    GENRE_NOT_FOUND(2003, "Thể loại không tồn tại", HttpStatus.NOT_FOUND),
    GENRE_ALREADY_EXISTS(2013, "Thể loại đã tồn tại", HttpStatus.BAD_REQUEST),
    GENRE_NAME_TOO_SHORT(2011, "Tên thể loại quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    GENRE_NAME_TOO_LONG(2012, "Tên thể loại quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    SECT_NOT_FOUND(2004, "Tông truyện không tồn tại", HttpStatus.NOT_FOUND),
    SECT_ALREADY_EXISTED(2014, "Tông truyện đã tồn tại", HttpStatus.BAD_REQUEST),
    SECT_NAME_TOO_SHORT(2015, "Tên tông truyện quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    SECT_NAME_TOO_LONG(2016, "Tên tông truyện quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    NOVEL_STATUS_NOT_FOUND(2005, "Trạng thái truyện không tồn tại", HttpStatus.NOT_FOUND),
    NOVEL_STATUS_ALREADY_EXISTED(2014, "Trạng thái truyện đã tồn tại", HttpStatus.BAD_REQUEST),
    NOVEL_STATUS_NAME_TOO_SHORT(2015, "Tên trạng thái truyện quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    NOVEL_STATUS_NAME_TOO_LONG(2016, "Tên trạng thái truyện quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    WORLD_SCENE_NOT_FOUND(2005, "Bối cảnh thế giới không tồn tại", HttpStatus.NOT_FOUND),
    WORLD_SCENE_ALREADY_EXISTED(2014, "Bối cảnh thế giới đã tồn tại", HttpStatus.BAD_REQUEST),
    WORLD_SCENE_NAME_TOO_SHORT(2015, "Tên bối cảnh thế giới quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    WORLD_SCENE_NAME_TOO_LONG(2016, "Tên bối cảnh thế giới quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    MAIN_CHARACTER_TRAIT_NOT_FOUND(2006, "Tính cách nhân vật chính không tồn tại", HttpStatus.NOT_FOUND),
    MAIN_CHARACTER_TRAIT_ALREADY_EXISTED(2014, "Tính cách nhân vật chính đã tồn tại", HttpStatus.BAD_REQUEST),
    MAIN_CHARACTER_TRAIT_NAME_TOO_SHORT(2015, "Tên tính cách nhân vật chính quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    MAIN_CHARACTER_TRAIT_NAME_TOO_LONG(2015, "Tên tính cách nhân vật chính quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

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

    INVALID_EMAIL(1009, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1010, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1011, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS(1012, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_TOO_SHORT(1013, "Tên đăng nhập quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    USERNAME_TOO_LONG(1014, "Tên đăng nhập quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(1015, "Mật khẩu quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_LONG(1016, "Mật khẩu quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_WEAK(1017, "Mật khẩu quá yếu. Hãy sử dụng ký tự viết hoa, chữ số và ký tự đặc biệt", HttpStatus.BAD_REQUEST),

    INVALID_OTP(1101, "Mã OTP không hợp lệ hoặc đã hết hạn", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1102, "Mã OTP đã hết hạn", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VERIFIED(1103, "Email chưa được xác minh", HttpStatus.UNAUTHORIZED),
    PHONE_NOT_VERIFIED(1104, "Số điện thoại chưa được xác minh", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1105, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(1106, "Refresh token không hợp lệ", HttpStatus.UNAUTHORIZED),
    RATE_LIMIT_EXCEEDED(1201, "Bạn đã gửi quá nhiều yêu cầu. Vui lòng thử lại sau", HttpStatus.TOO_MANY_REQUESTS),
    CHAPTER_TOO_SHORT(2016, "Chương quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    CHAPTER_TOO_LONG(2017, "Chương quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    NOVEL_TITLE_TOO_SHORT(2018, "Tên truyện quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    NOVEL_TITLE_TOO_LONG(2019, "Tên truyện quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_CHAPTER_ORDER(2020, "Số thứ tự chương không hợp lệ", HttpStatus.BAD_REQUEST),
    CHATBOT_SERVICE_UNAVAILABLE(4006, "Chatbot đang bảo trì. Vui lòng thử lại sau", HttpStatus.SERVICE_UNAVAILABLE),
    CHATBOT_INVALID_INPUT(4007, "Dữ liệu nhập vào không hợp lệ", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(8001, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),
    OPERATION_NOT_ALLOWED(8002, "Hành động này không được phép", HttpStatus.FORBIDDEN),
    ACCOUNT_LOCKED(8003, "Tài khoản của bạn đã bị khóa", HttpStatus.FORBIDDEN),
    ACCOUNT_SUSPENDED(8004, "Tài khoản của bạn đã bị tạm ngưng", HttpStatus.FORBIDDEN),

    FEEDBACK_NOT_FOUND(9001, "Feedback không tồn tại", HttpStatus.NOT_FOUND),
    FEEDBACK_ALREADY_EXISTS(9002, "Feedback đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_FEEDBACK_STATUS(9003, "Trạng thái feedback không hợp lệ", HttpStatus.BAD_REQUEST),
    FEEDBACK_STATUS_NOT_FOUND(9004, "Trạng thái feedback không tồn tại", HttpStatus.NOT_FOUND),
    FEEDBACK_STATUS_ALREADY_EXISTED(9005, "Trạng thái feedback đã tồn tại", HttpStatus.BAD_REQUEST),
    FEEDBACK_STATUS_NAME_TOO_SHORT(9006, "Tên trạng thái feedback quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    FEEDBACK_STATUS_NAME_TOO_LONG(9007, "Tên trạng thái feedback quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    FEEDBACK_TYPE_NOT_FOUND(9008, "Loại feedback không tồn tại", HttpStatus.NOT_FOUND),
    FEEDBACK_TYPE_ALREADY_EXISTED(9009, "Loại feedback đã tồn tại", HttpStatus.BAD_REQUEST),

    COMMENT_TOO_SHORT(3009, "Bình luận quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    COMMENT_TOO_LONG(3010, "Bình luận quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_COMMENT_STATUS(3011, "Trạng thái bình luận không hợp lệ", HttpStatus.BAD_REQUEST),
    COMMENT_STATUS_NOT_FOUND(3012, "Trạng thái bình luận không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_FEEDBACK_TYPE(3013, "Loại feedback không hợp lệ", HttpStatus.BAD_REQUEST),
    CHAPTER_NOT_BELONG_TO_NOVEL(2014, "Chương không thuộc về truyện này", HttpStatus.BAD_REQUEST),
    RATING_ALREADY_EXISTS(3005, "Đánh giá đã tồn tại", HttpStatus.BAD_REQUEST),

    REVIEW_NOT_FOUND(3006, "Đánh giá không tồn tại", HttpStatus.NOT_FOUND),
    COMMENT_ALREADY_DELETED(3007, "Bình luận đã bị xóa", HttpStatus.NOT_FOUND),
    COMMENT_STATUS_ALREADY_EXISTED(3014, "Trạng thái bình luận đã tồn tại", HttpStatus.BAD_REQUEST),

    // Coupon Usage
    COUPON_USAGE_NOT_FOUND(4000, "Không tìm thấy lượt sử dụng mã giảm giá", HttpStatus.NOT_FOUND),
    COUPON_USAGE_ALREADY_EXISTS(4001, "Người dùng đã sử dụng mã giảm giá này", HttpStatus.BAD_REQUEST),
    COUPON_USAGE_EXCEEDS_LIMIT(4002, "Mã giảm giá đã vượt giới hạn sử dụng", HttpStatus.BAD_REQUEST),

    // User Coupon
    USER_COUPON_NOT_FOUND(4003, "Không tìm thấy mã giảm giá của người dùng", HttpStatus.NOT_FOUND),
    USER_COUPON_ALREADY_OBTAINED(4004, "Người dùng đã nhận mã giảm giá này", HttpStatus.CONFLICT),
    USER_COUPON_EXPIRED(4005, "Mã giảm giá đã hết hạn", HttpStatus.BAD_REQUEST),
    USER_COUPON_ALREADY_USED_UP(4006, "Người dùng đã sử dụng hết lượt áp dụng mã", HttpStatus.BAD_REQUEST),

    // Vip Chapter Usage
    VIP_CHAPTER_USAGE_NOT_FOUND(4007, "Không tìm thấy lượt đọc chương VIP", HttpStatus.NOT_FOUND),
    VIP_CHAPTER_USAGE_DUPLICATE(4008, "Người dùng đã đọc chương này trong tháng", HttpStatus.CONFLICT),
    VIP_CHAPTER_USAGE_LIMIT_EXCEEDED(4009, "Đã vượt quá số chương miễn phí trong tháng", HttpStatus.BAD_REQUEST),

    // Vip Membership
    VIP_MEMBERSHIP_NOT_FOUND(4010, "Không tìm thấy thông tin VIP membership", HttpStatus.NOT_FOUND),
    VIP_MEMBERSHIP_ALREADY_ACTIVE(4011, "Người dùng đã có gói VIP đang hoạt động", HttpStatus.CONFLICT),
    VIP_MEMBERSHIP_PLAN_INVALID(4012, "Gói VIP không hợp lệ hoặc đã hết hạn", HttpStatus.BAD_REQUEST),

    // Vip Plan
    VIP_PLAN_NOT_FOUND(4013, "Không tìm thấy gói VIP", HttpStatus.NOT_FOUND),
    VIP_PLAN_NAME_ALREADY_EXISTS(4014, "Tên gói VIP đã tồn tại", HttpStatus.CONFLICT),
    VIP_PLAN_INVALID_DISCOUNT(4015, "Phần trăm giảm giá không hợp lệ", HttpStatus.BAD_REQUEST),

    COUPON_NOT_FOUND(4007, "Mã giảm giá không tồn tại", HttpStatus.NOT_FOUND),
    COUPON_ALREADY_EXISTS(4008, "Mã giảm giá đã tồn tại", HttpStatus.BAD_REQUEST),
    COUPON_ALREADY_COLLECTED(4009, "Người dùng đã nhận mã giảm giá này", HttpStatus.BAD_REQUEST),
    COUPON_IS_NOT_VALID(4010, "Mã giảm giá không hợp lệ hoặc đã hết hạn", HttpStatus.BAD_REQUEST),
    WALLET_INACTIVE(4011, "Ví không hoạt động", HttpStatus.BAD_REQUEST),
    WALLET_NOT_FOUND(4014, "Ví không tồn tại", HttpStatus.NOT_FOUND),
    CURRENCY_NOT_FOUND(4012, "Tiền tệ không tồn tại", HttpStatus.NOT_FOUND),
    CURRENCY_ALREADY_EXISTS(4013, "Tiền tệ đã tồn tại", HttpStatus.BAD_REQUEST),

    VIP_ACCESS_DENIED(5001, "Không có quyền truy cập VIP", HttpStatus.FORBIDDEN),
    INVALID_COUPON_CODE(5002, "Mã coupon không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT(5003, "Giá trị thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    USER_DONT_HAVE_VIP_ACCESS(5004, "Người dùng không có quyền truy cập VIP", HttpStatus.FORBIDDEN),

    NOTIFICATION_TEMPLATE_NOT_FOUND(1012, "Notification template not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_TEMPLATE_ALREADY_EXISTS(1013, "Notification template already exists", HttpStatus.BAD_REQUEST),
    NOTIFICATION_TEMPLATE_NAME_TOO_SHORT(1014, "Notification template name is too short. Must be at least {min} characters", HttpStatus.BAD_REQUEST),
    NOTIFICATION_BATCH_NOT_FOUND(1015, "Notification batch not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_BATCH_ALREADY_EXISTS(1016, "Notification batch already exists", HttpStatus.BAD_REQUEST),
    NOTIFICATION_BATCH_NAME_TOO_SHORT(1017, "Notification batch name is too short. Must be at least {min} characters", HttpStatus.BAD_REQUEST),
    NOTIFICATION_BATCH_NAME_TOO_LONG(1018, "Notification batch name is too long. Maximum {max} characters", HttpStatus.BAD_REQUEST),
    NOTIFICATION_BATCH_STATUS_NOT_FOUND(1019, "Notification batch status not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_BATCH_STATUS_ALREADY_EXISTS(1020, "Notification batch status already exists", HttpStatus.BAD_REQUEST),
    DUPLICATE_USER_NOTIFICATION_PREFERENCE(1021, "Người dùng đã có sở thích thông báo", HttpStatus.BAD_REQUEST),
    NOTIFICATION_PREFERENCE_NOT_FOUND(1022, "Sở thích thông báo không tồn tại", HttpStatus.NOT_FOUND),
    NOTIFICATION_PREFERENCE_ALREADY_EXISTS(1023, "Sở thích thông báo đã tồn tại", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(1024, "Thông báo không tồn tại", HttpStatus.NOT_FOUND),
    NOTIFICATION_ALREADY_EXISTS(1025, "Thông báo đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT_TYPE(1026, "Loại đối số không hợp lệ", HttpStatus.BAD_REQUEST),
    COUPON_USAGE_LIMIT_REACHED(4000, "Đã đạt giới hạn sử dụng mã giảm giá", HttpStatus.BAD_REQUEST),
    COUPON_USAGE_LIMIT_EXCEEDED(4001, "Số lần sử dụng mã giảm giá đã vượt quá giới hạn", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
