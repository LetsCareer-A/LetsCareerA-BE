package com.example.letscareer.stage.dto;

import java.time.LocalDate;

public record StageDTO(
        Long stageId,
        int order,
        String type,
        String mid_name,
        String status,
        LocalDate date,
        int dday
) {
}
