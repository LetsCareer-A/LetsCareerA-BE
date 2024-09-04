package com.example.letscareer.schedule.domain.dto;

import java.time.LocalDate;

public record CompanyReviewDetailDTO(
        Long scheduleId,
        Long stageId,
        Long reviewId,
        String department,
        LocalDate deadline,
        boolean isReviewed
) {
}
