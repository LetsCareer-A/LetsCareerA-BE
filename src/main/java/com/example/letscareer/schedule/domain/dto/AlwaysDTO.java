package com.example.letscareer.schedule.domain.dto;

public record AlwaysDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String status
) {
}
