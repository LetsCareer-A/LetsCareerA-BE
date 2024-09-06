package com.example.letscareer.int_review.domain.dto.response;

import com.example.letscareer.int_review.domain.model.IntReview;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.model.Stage;
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
    public static IntReviewDetailResponse from(Schedule schedule, Stage stage, IntReview intReview) {
        return new IntReviewDetailResponse(
                schedule.getCompany(),
                schedule.getDepartment(),
                stage.getType().getValue(),
                stage.getDate(),
                intReview.getMethod(),
                intReview.getQuestions(),
                intReview.getFeelings());
    }
}
