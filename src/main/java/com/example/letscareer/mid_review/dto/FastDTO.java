package com.example.letscareer.mid_review.dto;

public record FastDTO(
        Long stageId,
        Long reviewId,
        String company,
        String department,
        String type,
        Integer plusDay
) {
}
