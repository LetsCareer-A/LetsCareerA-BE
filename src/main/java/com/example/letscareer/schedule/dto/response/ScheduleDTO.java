package com.example.letscareer.schedule.dto.response;

import com.example.letscareer.schedule.domain.Progress;

public record ScheduleDTO (
        Long scheduleId,
        Long stageId,
        String company,
        String department,
        String type,
        String deadline,
        Integer dday,
        Progress progress){
}
