package com.example.letscareer.mid_review.domain.dto.response;

import com.example.letscareer.mid_review.domain.model.MidReview;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.model.Stage;
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
    public static MidReviewDetailResponse from(Schedule schedule, Stage stage, MidReview midReview) {
        return new MidReviewDetailResponse(
                schedule.getCompany(),
                schedule.getDepartment(),
                stage.getType().getValue(),
                stage.getDate(),
                midReview.getFreeReview(),
                midReview.getGoal());
    }
}
