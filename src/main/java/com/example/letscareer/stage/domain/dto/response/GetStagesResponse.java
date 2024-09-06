package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.dto.StageDTO;

import java.util.List;

public record GetStagesResponse(
        Long scheduleId,
        String company,
        String department,
        String url,
        String progress,
        boolean isAlways,
        List<StageDTO> stages
) {

    public static GetStagesResponse from(Schedule schedule, List<StageDTO> stages) {
        return new GetStagesResponse(
                schedule.getScheduleId(),
                schedule.getCompany(),
                schedule.getDepartment(),
                schedule.getUrl(),
                schedule.getProgress().getValue(),
                schedule.isAlways(),
                stages
        );
    }
}
