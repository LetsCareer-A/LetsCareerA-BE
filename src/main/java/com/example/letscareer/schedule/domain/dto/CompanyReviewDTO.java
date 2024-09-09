package com.example.letscareer.schedule.domain.dto;

import java.util.List;

public record CompanyReviewDTO(
        String company,
        String department,

        List<CompanyReviewDetailDTO> interviewReviews,  // 면접 리뷰
        List<CompanyReviewDetailDTO> midtermReviews  // 중간 리뷰
) {
}
