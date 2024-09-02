package com.example.letscareer.schedule.dto.response;

import com.example.letscareer.schedule.dto.CompanyReviewDTO;

import java.util.List;

public record CompanyReviewListResponse(
        Integer page,
        Integer size,
        List<CompanyReviewDTO> companies

) {
}
