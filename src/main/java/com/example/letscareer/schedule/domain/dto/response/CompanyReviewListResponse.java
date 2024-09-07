package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.CompanyReviewDTO;

import java.util.List;

public record CompanyReviewListResponse(
        Integer page,
        Integer size,
        Long total,
        List<CompanyReviewDTO> companies
) {
}
