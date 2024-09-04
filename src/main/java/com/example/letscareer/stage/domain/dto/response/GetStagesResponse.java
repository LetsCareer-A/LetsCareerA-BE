package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.stage.domain.dto.StageDTO;

import java.util.List;

public record GetStagesResponse(
        Long scheduleId,
        String company,
        String department,
        String url,
        String progress,
        boolean isAlways,
        List<StageDTO> stages
) {
}
