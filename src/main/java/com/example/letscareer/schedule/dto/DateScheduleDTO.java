package com.example.letscareer.schedule.dto;

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
