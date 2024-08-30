package com.example.letscareer.schedule.dto.response;

import com.example.letscareer.schedule.domain.Progress;

public record StageDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        String deadline,
        int dday,
        Progress progress){
}
