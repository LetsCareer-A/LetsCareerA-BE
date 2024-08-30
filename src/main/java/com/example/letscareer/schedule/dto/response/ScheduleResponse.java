package com.example.letscareer.schedule.dto.response;

import java.util.List;

public record ScheduleResponse(
    Integer page,
    Integer size,
    Integer docCount,
    Integer midCount,
    Integer interviewCount,
    List<StageDTO> schedules

) {
}
