package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.StageDTO;

import java.util.List;

public record CalendarResponse(
        Integer docCount,
        Integer midCount,
        Integer interviewCount,
        List<StageDTO> schedules
) {
}
