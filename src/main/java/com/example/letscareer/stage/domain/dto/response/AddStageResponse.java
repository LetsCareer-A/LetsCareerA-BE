package com.example.letscareer.stage.domain.dto.response;

import java.time.LocalDate;

public record AddStageResponse(
        Long stageId,
        String type,
        String mid_name,
        String status,
        LocalDate date,
        int dday
) {
}