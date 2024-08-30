package com.example.letscareer.stage.dto;

import java.util.List;

public record DateClickStageResponse(
        Integer totalCount,
        Integer plusCount,
        List<DateStageDTO> schedules
) {
}
