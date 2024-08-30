package com.example.letscareer.career.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record GetCareerDetailResponse(
        Long careerId,
        @Enumerated(EnumType.STRING)
        String category,
        String title,
        String situation,
        String task,
        String action,
        String result
) {
}
