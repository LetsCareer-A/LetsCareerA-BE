package com.example.letscareer.career.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record CareerDTO(
        Long careerId,
        @Enumerated(EnumType.STRING)
        String category,
        String title,
        String summary
) {
}
