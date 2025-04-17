package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterUpdateAmountFieldRequest;
import com.sidn.metruyenchu.novelservice.enums.ChapterStatGenericList;
import com.sidn.metruyenchu.novelservice.service.ChapterStatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/chapter-stat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterStatController {
    ChapterStatService chapterStatService;
    //increase generic stat
    @PutMapping("/{chapterId}/comment/increase")
    ApiResponse<Void> increaseGenericStat(
            @PathVariable("chapterId") String chapterId,
            @ModelAttribute("genericStat") String genericStat
    ) {
        //String to ChapterStatGenericList
        ChapterStatGenericList genericStatEnum = ChapterStatGenericList.valueOf(genericStat);

        return ApiResponse.<Void>builder()
                .result(chapterStatService.increaseStat(chapterId, genericStatEnum))
                .build();
    }
    //decrease generic stat
    @PutMapping("/{chapterId}/comment/decrease")
    ApiResponse<Void> decreaseGenericStat(
            @PathVariable("chapterId") String chapterId,
            @ModelAttribute("genericStat") String genericStat
    ) {
        //String to ChapterStatGenericList
        ChapterStatGenericList genericStatEnum = ChapterStatGenericList.valueOf(genericStat);

        return ApiResponse.<Void>builder()
                .result(chapterStatService.decreaseStat(chapterId, genericStatEnum))
                .build();
    }


    //change amount to unlock
    @PutMapping("/{chapterId}/amountToUnlock")
    ApiResponse<Void> changeAmountToUnlock(
            @PathVariable("chapterId") String chapterId,
            @ModelAttribute Integer amount
            ) {
        return ApiResponse.<Void>builder()
                .result(chapterStatService.changeStat(chapterId,ChapterStatGenericList.AMOUNT_TO_UNLOCK, amount))
                .build();
    }
}


