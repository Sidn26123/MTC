package com.sidn.metruyenchu.paymentservice.exception;

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

    NOVEL_IS_PENDING(8005, "Truyện đang chờ duyệt", HttpStatus.FORBIDDEN),
    NOVEL_IS_REJECTED(8006, "Truyện đã bị từ chối", HttpStatus.FORBIDDEN),
    NOVEL_IS_DELETED(8007, "Truyện đã bị xóa", HttpStatus.FORBIDDEN),
    NOVEL_IS_PUBLISHED(8008, "Truyện đã được xuất bản", HttpStatus.FORBIDDEN),
    NOVEL_NOT_IN_CREATED_STATE(8009, "Truyện không ở trạng thái đã tạo", HttpStatus.FORBIDDEN),
    REQUEST_NOT_FOUND(8010, "Yêu cầu không tồn tại", HttpStatus.NOT_FOUND),
    PUBLISH_REQUEST_ACTION_LOG_NOT_FOUND(8011, "Lỗi không tìm thấy hành động yêu cầu xuất bản", HttpStatus.NOT_FOUND),
    NOVEL_NOT_HAVE_ENOUGH_CHAPTER(8012, "Truyện không có đủ chương để xuất bản", HttpStatus.BAD_REQUEST),

    CATEGORY_NOT_FOUND(9001, "Danh mục không tồn tại", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS(9002, "Danh mục đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_TOO_SHORT(9003, "Tên danh mục quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_TOO_LONG(9004, "Tên danh mục quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(9005, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS(9006, "Sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_TOO_SHORT(9007, "Tên sản phẩm quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    CURRENCY_NOT_FOUND(9008, "Tiền tệ không tồn tại", HttpStatus.NOT_FOUND),
    CURRENCY_ALREADY_EXISTS(9009, "Tiền tệ đã tồn tại", HttpStatus.BAD_REQUEST),

    WALLET_NOT_FOUND(9010, "Ví không tồn tại", HttpStatus.NOT_FOUND),
    WALLET_ALREADY_EXISTS(9011, "Ví đã tồn tại", HttpStatus.BAD_REQUEST),

    TRANSACTIONS_NOT_FOUND(9012, "Giao dịch không tồn tại", HttpStatus.NOT_FOUND),
    TRANSACTIONS_ALREADY_EXISTS(9013, "Giao dịch đã tồn tại", HttpStatus.BAD_REQUEST),

    PAYMENT_METHOD_NOT_FOUND(9014, "Phương thức thanh toán không tồn tại", HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_ALREADY_EXISTS(9015, "Phương thức thanh toán đã tồn tại", HttpStatus.BAD_REQUEST),

    CONTENT_PURCHASE_NOT_FOUND(9016, "Nội dung mua không tồn tại", HttpStatus.NOT_FOUND),
    CONTENT_PURCHASE_ALREADY_EXISTS(9017, "Nội dung mua đã tồn tại", HttpStatus.BAD_REQUEST),



//    // 1. General Errors
//    UNKNOWN_ERROR(9999, "Lỗi không xác định", HttpStatus.BAD_REQUEST),
//    INVALID_KEY(1, "Message key không hợp lệ", HttpStatus.BAD_REQUEST),
//
//    // 2. User & Authentication Errors
//    USER_EXISTED(1001, "Người dùng đã tồn tại!", HttpStatus.INTERNAL_SERVER_ERROR),
//    USER_NOT_EXISTED(1005, "Người dùng này không tồn tại", HttpStatus.NOT_FOUND),
//    INVALID_USERNAME(1003, "Username không hợp lệ. Username phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
//    INVALID_PASSWORD(1004, "Password không hợp lệ. Password phải có từ {min} ký tự trở lên", HttpStatus.BAD_REQUEST),
//    INVALID_EMAIL(1009, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
//    EMAIL_ALREADY_EXISTS(1010, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
//    PHONE_NUMBER_INVALID(1011, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
//    PHONE_NUMBER_ALREADY_EXISTS(1012, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
//    USERNAME_TOO_SHORT(1013, "Tên đăng nhập quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
//    USERNAME_TOO_LONG(1014, "Tên đăng nhập quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
//    PASSWORD_TOO_SHORT(1015, "Mật khẩu quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
//    PASSWORD_TOO_LONG(1016, "Mật khẩu quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
//    PASSWORD_TOO_WEAK(1017, "Mật khẩu quá yếu. Hãy sử dụng ký tự viết hoa, chữ số và ký tự đặc biệt", HttpStatus.BAD_REQUEST),
//    INVALID_OTP(1101, "Mã OTP không hợp lệ hoặc đã hết hạn", HttpStatus.BAD_REQUEST),
//    OTP_EXPIRED(1102, "Mã OTP đã hết hạn", HttpStatus.BAD_REQUEST),
//    EMAIL_NOT_VERIFIED(1103, "Email chưa được xác minh", HttpStatus.UNAUTHORIZED),
//    PHONE_NOT_VERIFIED(1104, "Số điện thoại chưa được xác minh", HttpStatus.UNAUTHORIZED),
//    TOKEN_EXPIRED(1105, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
//    INVALID_REFRESH_TOKEN(1106, "Refresh token không hợp lệ", HttpStatus.UNAUTHORIZED),
//    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
//    UNAUTHORIZED(1007, "Không có quyền để làm điều này", HttpStatus.FORBIDDEN),
//    ACCOUNT_LOCKED(8003, "Tài khoản của bạn đã bị khóa", HttpStatus.FORBIDDEN),
//    ACCOUNT_SUSPENDED(8004, "Tài khoản của bạn đã bị tạm ngưng", HttpStatus.FORBIDDEN),
//
//    // 3. Novel & Chapter Errors
//    NOVEL_NOT_FOUND(2001, "Truyện không tồn tại", HttpStatus.NOT_FOUND),
//    NOVEL_ALREADY_EXISTS(2008, "Truyện đã tồn tại", HttpStatus.BAD_REQUEST),
//    NOVEL_TITLE_TOO_SHORT(2018, "Tên truyện quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
//    NOVEL_TITLE_TOO_LONG(2019, "Tên truyện quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
//    CHAPTER_NOT_FOUND(2002, "Chương không tồn tại", HttpStatus.NOT_FOUND),
//    CHAPTER_ALREADY_EXISTS(2009, "Chương đã tồn tại", HttpStatus.BAD_REQUEST),
//    CHAPTER_TOO_SHORT(2016, "Chương quá ngắn. Phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
//    CHAPTER_TOO_LONG(2017, "Chương quá dài. Tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
//
//    // 4. Genre, Sect, World Scene, Main Character Trait Errors
//    GENRE_NOT_FOUND(2003, "Thể loại không tồn tại", HttpStatus.NOT_FOUND),
//    GENRE_ALREADY_EXISTS(2013, "Thể loại đã tồn tại", HttpStatus.BAD_REQUEST),
//    SECT_NOT_FOUND(2004, "Tông truyện không tồn tại", HttpStatus.NOT_FOUND),
//    SECT_ALREADY_EXISTED(2014, "Tông truyện đã tồn tại", HttpStatus.BAD_REQUEST),
//    WORLD_SCENE_NOT_FOUND(2005, "Bối cảnh thế giới không tồn tại", HttpStatus.NOT_FOUND),
//    MAIN_CHARACTER_TRAIT_NOT_FOUND(2006, "Tính cách nhân vật chính không tồn tại", HttpStatus.NOT_FOUND),
//    MAIN_CHARACTER_TRAIT_ALREADY_EXISTED(2014, "Tính cách nhân vật chính đã tồn tại", HttpStatus.BAD_REQUEST),
//
//    // 5. Comment, Rating, Report Errors
//    COMMENT_NOT_FOUND(3001, "Bình luận không tồn tại", HttpStatus.NOT_FOUND),
//    RATING_NOT_FOUND(3002, "Đánh giá không tồn tại", HttpStatus.NOT_FOUND),
//    REPORT_NOT_FOUND(3003, "Báo cáo không tồn tại", HttpStatus.NOT_FOUND),
//
//    // 6. Search & Recommendation Errors
//    SEARCH_QUERY_TOO_SHORT(4001, "Từ khóa tìm kiếm quá ngắn", HttpStatus.BAD_REQUEST),
//    SEARCH_ENGINE_ERROR(4002, "Lỗi hệ thống tìm kiếm", HttpStatus.INTERNAL_SERVER_ERROR),
//    RECOMMENDATION_ENGINE_ERROR(5001, "Lỗi hệ thống đề xuất", HttpStatus.INTERNAL_SERVER_ERROR),
//
//    // 7. Payment Errors
//    INSUFFICIENT_BALANCE(6001, "Số dư tài khoản không đủ", HttpStatus.BAD_REQUEST),
//    INVALID_PAYMENT_METHOD(6002, "Phương thức thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
//    TRANSACTION_NOT_FOUND(6003, "Giao dịch không tồn tại", HttpStatus.NOT_FOUND),
//
//    // 8. File Errors
//    FILE_NOT_FOUND(7001, "Tệp tin không tồn tại", HttpStatus.NOT_FOUND),
//    INVALID_FILE_TYPE(7002, "Loại tệp không hợp lệ", HttpStatus.BAD_REQUEST),
//    FILE_UPLOAD_FAILED(7003, "Tải tệp lên thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
//
//    // 9. Access & Permission Errors
//    ACCESS_DENIED(8001, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),
//    OPERATION_NOT_ALLOWED(8002, "Hành động này không được phép", HttpStatus.FORBIDDEN),
//    RATE_LIMIT_EXCEEDED(1201, "Bạn đã gửi quá nhiều yêu cầu. Vui lòng thử lại sau", HttpStatus.TOO_MANY_REQUESTS);

    INVALID_ARGUMENT_TYPE(0003, "Kiểu dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
