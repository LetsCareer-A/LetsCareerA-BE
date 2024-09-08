package com.example.letscareer.schedule.domain.dto;

import com.example.letscareer.stage.domain.model.Status;

import java.time.LocalDate;

public record ScheduleDTO(
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        LocalDate deadline,
        int dday,
        String status){
}
