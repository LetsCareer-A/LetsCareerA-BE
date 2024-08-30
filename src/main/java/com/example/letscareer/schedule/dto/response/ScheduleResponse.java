package com.example.letscareer.schedule.dto.response;

import java.util.List;

public record ScheduleResponse(
    Long page,
    Integer size,
    Integer docCount,
    Integer midCount,
    Integer interviewCount,
    List<ScheduleDTO> schedules

) {
}