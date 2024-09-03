package com.example.letscareer.stage.dto;


public record StageDTO(
        Long stageId,
        int order,
        String type,
        String mid_name,
        String status,
        String date,
        String dday
) {
}
