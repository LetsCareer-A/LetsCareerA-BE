package com.example.letscareer.stage.domain.dto;

import com.example.letscareer.mid_review.domain.model.MidReview;

public record MidReviewDTO(
        Long reviewId,
        String free_review,
        String goal
) {
    public static MidReviewDTO from(MidReview midReview) {
        return new MidReviewDTO(
                midReview.getMidReviewId(),
                midReview.getFreeReview(),
                midReview.getGoal()
        );
    }
}
