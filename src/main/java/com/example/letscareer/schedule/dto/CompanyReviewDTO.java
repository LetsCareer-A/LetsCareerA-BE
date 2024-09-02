package com.example.letscareer.schedule.dto;

import java.util.List;
import java.util.Map;

public record CompanyReviewDTO(
        String company,
        List<CompanyReviewDetailDTO> interviewReviews,  // 면접 리뷰
        List<CompanyReviewDetailDTO> midtermReviews  // 중간 리뷰
) {
}
