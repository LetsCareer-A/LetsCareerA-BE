package com.example.letscareer.mid_review.dto.response;

import jakarta.persistence.Lob;

import java.time.LocalDate;

public record MidReviewDetailResponse(
        String company,
        String department,
        String type,
        LocalDate deadline,
        @Lob
        String freeReview,

        @Lob
        String goal
) {
}
