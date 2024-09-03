package com.example.letscareer.stage.dto;

import java.time.LocalDate;

public record ReviewDTO(
        Long reviewId,
        String free_review,
        String goal
) {
}
