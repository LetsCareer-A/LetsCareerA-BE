package com.example.letscareer.int_review.domain.dto.response;

import jakarta.persistence.Lob;

import java.time.LocalDate;

public record IntReviewDetailResponse(
        String company,
        String department,
        String type,
        LocalDate deadline,
        @Lob
        String details,
        @Lob
        String qa,
        @Lob
        String feelings
) {
}
