package com.example.letscareer.stage.dto;

public record DateStageDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String type,
        Integer dday,
        String progress
) {
}
