package com.example.letscareer.stage.domain.dto.request;

import com.example.letscareer.stage.domain.model.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record AddStageRequest(
        @Enumerated(EnumType.STRING)
        Type type,
        String mid_name,
        LocalDate date
) {
}
