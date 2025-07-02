package com.sidn.metruyenchu.shared_library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiFeignResponse {
//    [400] during [POST] to [http://localhost:8092/novel/novels/getNovelById] [NovelClient#getNovel(CheckNovelExistedRequest)]: [{"code":2001,"message":"Truyện không tồn tại"}]
    HttpStatus httpStatus; //400
    HttpMethod httpMethod; //POST
    String url; //http://localhost:8092/novel/novels/getNovelById
    Object body; //[{"code":2001,"message":"Truyện không tồn tại"}]
    String origin; //NovelClient#getNovel(CheckNovelExistedRequest)

    public ApiFeignResponse extractDataErrorOfFeignCall(String str) {
        // Ví dụ đầu vào:
        // "[400] during [POST] to [http://localhost:8092/novel/novels/getNovelById] [NovelClient#getNovel(CheckNovelExistedRequest)]: [{\"code\":2001,\"message\":\"Truyện không tồn tại\"}]"
        ApiFeignResponse response = new ApiFeignResponse();
        try {
            // Tách phần status code: giữa cặp [ ]
            int firstBracketClose = str.indexOf("]");
            String statusCodeStr = str.substring(1, firstBracketClose);
            response.httpStatus = HttpStatus.valueOf(Integer.parseInt(statusCodeStr));

            // Tách phần method: giữa "[POST]"
            int methodStart = str.indexOf("[", firstBracketClose + 1);
            int methodEnd = str.indexOf("]", methodStart + 1);
            String methodStr = str.substring(methodStart + 1, methodEnd);
            response.httpMethod = HttpMethod.valueOf(methodStr);

            // Tách phần URL: sau từ "to" nằm giữa [ ]
            int urlStart = str.indexOf("[", methodEnd + 1);
            int urlEnd = str.indexOf("]", urlStart + 1);
            // Vì chuỗi có dạng " to [URL]", ta có thể lấy URL từ dấu [ ]
            response.url = str.substring(urlStart + 1, urlEnd);

            // Tách phần origin: phần cuối cùng nằm trong [ ]
            int lastOpenBracket = str.lastIndexOf("[");
            int lastCloseBracket = str.lastIndexOf("]");
            response.origin = str.substring(lastOpenBracket + 1, lastCloseBracket);

            // Tách phần body: sau dấu ":" cuối cùng
            int colonIndex = str.lastIndexOf(":");
            String bodyStr = str.substring(colonIndex + 1).trim();
            // Nếu cần parse body JSON, có thể sử dụng Jackson:
            ObjectMapper mapper = new ObjectMapper();
            try {
                response.body = mapper.readValue(bodyStr, Object.class);
            } catch (IOException e) {
                response.body = bodyStr;
            }

        } catch (Exception e) {
            System.err.println("Error parsing feign response string: " + e.getMessage());
        }
        return response;
    }
}

