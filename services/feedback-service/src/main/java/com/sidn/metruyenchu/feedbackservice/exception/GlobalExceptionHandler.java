package com.sidn.metruyenchu.feedbackservice.exception;

import com.sidn.metruyenchu.feedbackservice.dto.ApiFeignResponse;
import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(final RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();
        log.error(exception.getMessage(), exception);
        apiResponse.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.UNKNOWN_ERROR.getMessage());


        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);

        String message = "Dữ liệu không hợp lệ hoặc thiếu khóa ngoại (foreign key).";
        Throwable rootCause = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(ex);
        String details = rootCause != null ? rootCause.getMessage() : ex.getMessage();

        ApiResponse response = ApiResponse.builder()
                .code(ErrorCode.DATA_INTEGRITY_VIOLATION.getCode())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(final AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());


        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException exception) {

        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolations = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolations.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());
        }
        catch (IllegalArgumentException e) {
//            errorCode = ErrorCode.INVALID_KEY;
        }

//        errorCode = ErrorCode.valueOf(enumKey);

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());


        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = feign.FeignException.class)
    ResponseEntity<ApiResponse> handleFeignException(feign.FeignException exception){
        log.info("log {}", exception.getMessage());
        ApiFeignResponse apiFeignResponse = new ApiFeignResponse();
        apiFeignResponse = apiFeignResponse.extractDataErrorOfFeignCall(exception.getMessage());


        ErrorCode errorCode = ErrorCode.FEIGN_ERROR;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        log.info(minValue);
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<ApiResponse> handleFeignException(FeignException ex) {
//        log.error("FeignClient error: {}", ex.contentUTF8());
//
//        ApiResponse apiResponse = new ApiResponse();
//
//        // Bạn có thể parse lại body nếu muốn lấy message chi tiết
//        String message = "Lỗi khi gọi service khác";
//        if (ex.status() == 404) {
//            message = "Không tìm thấy tài nguyên từ service liên quan";
//            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());  // nếu có mã riêng
//        } else {
//            apiResponse.setCode(ErrorCode.SERVICE_UNAVAILABLE.getCode());  // mã lỗi tùy bạn định nghĩa
//        }
//
//        apiResponse.setMessage(message);
//
//        // Tùy theo mức độ nghiêm trọng mà chọn 4xx hay 5xx
//        return ResponseEntity.status(ex.status()).body(apiResponse);
//    }

}