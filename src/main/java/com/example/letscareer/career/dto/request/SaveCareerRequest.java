package com.example.letscareer.career.dto.request;

import com.example.letscareer.career.domain.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SaveCareerRequest(
        @Enumerated(EnumType.STRING)
        Category category,
        String title,
        String situation,
        String task,
        String action,
        String result
) {

}
