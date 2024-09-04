package com.example.letscareer.schedule.domain.dto.request;

import com.example.letscareer.stage.domain.model.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record SchedulePostRequest(
        String company,
        String department,

        @Enumerated(EnumType.STRING)
        Type type,
        String midname,
        Boolean always,
        LocalDate date,
        String url
) {
}
