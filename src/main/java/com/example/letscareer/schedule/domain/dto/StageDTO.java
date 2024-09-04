package com.example.letscareer.schedule.domain.dto;

import com.example.letscareer.schedule.domain.model.Progress;

import java.time.LocalDate;

public record StageDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        LocalDate deadline,
        int dday,
        Progress progress){
}
