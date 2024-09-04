package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.DateScheduleDTO;

import java.util.List;

public record DateClickScheduleResponse(
        Integer totalCount,
        Integer plusCount,
        List<DateScheduleDTO> schedules
) {
}
