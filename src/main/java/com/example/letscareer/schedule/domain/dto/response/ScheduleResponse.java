package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.StageDTO;

import java.util.List;

public record ScheduleResponse(
    Integer page,
    Integer size,
    Long total,
    List<StageDTO> schedules

) {
}
