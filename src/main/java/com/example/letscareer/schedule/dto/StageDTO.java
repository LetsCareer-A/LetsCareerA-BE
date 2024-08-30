package com.example.letscareer.schedule.dto;

import com.example.letscareer.schedule.domain.Progress;

import java.util.Date;

public record StageDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        Date deadline,
        int dday,
        Progress progress){
}
