package com.example.letscareer.stage.dto.response;

import com.example.letscareer.stage.dto.StageDTO;

import java.util.List;

public record GetStagesResponse(
        Long scheduleId,
        String company,
        String department,
        String url,
        String progress,
        List<StageDTO> stages
) {
}
