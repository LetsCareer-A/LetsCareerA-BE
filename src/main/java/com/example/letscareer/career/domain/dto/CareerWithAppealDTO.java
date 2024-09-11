package com.example.letscareer.career.domain.dto;

public record CareerWithAppealDTO(
        Long careerId,
        String category,
        String title,
        String summary,
        boolean isAppeal
) {
}
