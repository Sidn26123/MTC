package com.sidn.metruyenchu.fileservice.utils;

import com.sidn.metruyenchu.fileservice.dto.request.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FuncUtils {
    public static Pageable getPageable(PageableRequest request) {
        Sort sort = Sort.by(request.getDirection(), request.getSort());

        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }

    public static Pageable getPageable(){
        return getPageable(
                PageableRequest.builder()
                        .sort("createdAt")
                        .direction(Sort.Direction.DESC)
                        .build()
        );
    }

    public static Pageable getPageableDefault(){
        PageableRequest pageableRequest =new PageableRequest();

        return getPageable(
                pageableRequest
        );
    }
}
