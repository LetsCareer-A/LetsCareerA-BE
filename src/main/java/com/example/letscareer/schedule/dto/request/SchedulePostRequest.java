package com.example.letscareer.schedule.dto.request;

import com.example.letscareer.stage.domain.Type;
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
