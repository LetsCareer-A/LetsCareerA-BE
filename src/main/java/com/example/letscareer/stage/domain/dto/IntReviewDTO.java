package com.example.letscareer.stage.domain.dto;

import com.example.letscareer.int_review.domain.model.IntReview;

public record IntReviewDTO(
        Long reviewId,
        String details,
        String qa,
        String feel
) {
    public static IntReviewDTO from(IntReview intReview) {
        return new IntReviewDTO(
                intReview.getIntReviewId(),
                intReview.getMethod(),
                intReview.getQuestions(),
                intReview.getFeelings()
        );
    }
}
