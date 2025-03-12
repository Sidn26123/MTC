package com.sidn.metruyenchu.feedbackservice.utils;

import com.sidn.metruyenchu.feedbackservice.dto.request.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class GeneralUtils {
    public static Pageable getPageable(PaginationRequest paginationRequest) {
        if (paginationRequest == null) {
            return PageRequest.of(0, 10);
        }
        if (paginationRequest.getDirection() == null) {
            return PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize());
        }

        String sortStr = paginationRequest.getSort();
        String sortDirection = paginationRequest.getDirection();
        if (sortDirection.equalsIgnoreCase("asc")) {
            Sort sort = Sort.by(Sort.Order.asc(sortStr));
            return PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), sort);
        }

        else if (sortDirection.equalsIgnoreCase("desc")) {
            Sort sort = Sort.by(Sort.Order.desc(sortStr));
            return PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), sort);
        }

        return PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize());

    }
}
