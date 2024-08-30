package com.example.letscareer.schedule.dto.response;

import com.example.letscareer.schedule.dto.DateScheduleDTO;

import java.util.List;

public record DateClickScheduleResponse(
        Integer totalCount,
        Integer plusCount,
        List<DateScheduleDTO> schedules
) {
}
