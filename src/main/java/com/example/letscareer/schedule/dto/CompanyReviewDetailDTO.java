package com.example.letscareer.schedule.dto;

import java.time.LocalDate;

public record CompanyReviewDetailDTO(
        Long scheduleId,
        Long stageId,
        String department,
        LocalDate deadline,
        boolean isReviewed
) {
}
