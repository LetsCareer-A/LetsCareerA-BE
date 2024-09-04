package com.example.letscareer.schedule.domain.dto;

public record DateScheduleDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        Integer dday,
        String progress
) {
}
