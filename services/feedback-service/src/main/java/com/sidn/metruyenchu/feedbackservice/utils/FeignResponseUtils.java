package com.sidn.metruyenchu.feedbackservice.utils;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CheckChapterExistedRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CheckNovelExistedRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class FeignResponseUtils {
    public static NovelResponse getNovelResponseFromFeignCall(ApiResponse<NovelResponse> response) {
        //Neu response null thi throw exception
        if (response == null){
            throw new AppException(ErrorCode.FEIGN_ERROR);
        }

        return response.getResult();
    }

    /**
     * Gọi feign để lấy thông tin của một novel từ novel service
     * Kiểm tra các điều kiện, đảm bảo đầu ra là novel hợp lệ
     * @param novelClient Feign client
     * @param novelId Id của novel cần lấy thông tin - String
     * @return ApiResponse<NovelResponse> chứa thông tin của novel
     */
    public static ApiResponse<NovelResponse> callFeignGetNovelInfo(NovelClient novelClient, String novelId) {
        Optional<ApiResponse<NovelResponse>> novelResponse = novelClient.getNovel(
                CheckNovelExistedRequest.builder()
                        .id(novelId)
                        .build()
        );

        novelResponse.ifPresentOrElse(
                response -> {
                    if (response.getResult().getId() == null){
                        throw new AppException(ErrorCode.NOVEL_NOT_FOUND);
                    }
                    //Nếu response code khác 200 thì throw exception
                    else if (response.getCode() != 0){
                        log.info("Response code: " + response.getCode());
                        throw new AppException(ErrorCode.FEIGN_ERROR);
                    }
                },
                () -> {
                    throw new AppException(ErrorCode.NOVEL_NOT_FOUND);
                }
        );

        return novelResponse.orElse(null);
    }

    public static ApiResponse<ChapterResponse> callFeignGetChapterInfo(NovelClient novelClient, String chapterId) {
        Optional<ApiResponse<ChapterResponse>> chapterResponse = novelClient.getChapter(
                chapterId
        );

        chapterResponse.ifPresentOrElse(
                response -> {
                    if (response.getResult().getId() == null){
                        throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
                    }
//                    //Nếu response code khác 200 thì throw exception
//                    else if (response.getCode() != 200){
//                        throw new AppException(ErrorCode.FEIGN_ERROR);
//                    }
                },
                () -> {
                    throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
                }
        );

        return chapterResponse.orElse(null);
    }

//    public static  ApiResponse<Boolean> checkUserExist
}
