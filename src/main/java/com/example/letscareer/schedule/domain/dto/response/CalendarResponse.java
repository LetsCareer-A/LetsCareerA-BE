package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.ScheduleDTO;

import java.util.List;

public record CalendarResponse(
        Integer docCount,
        Integer midCount,
        Integer interviewCount,
        List<ScheduleDTO> schedules
) {
}
