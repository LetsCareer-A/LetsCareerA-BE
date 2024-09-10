package com.example.letscareer.schedule.domain.dto;

public record FastDTO(
        Long stageId,
        Long scheduleId,
        String company,
        String department,
        String type
) {
}
