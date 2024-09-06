package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.model.Type;

import java.time.LocalDate;

public record AddStageResponse(
        Long stageId,
        String type,
        String mid_name,
        String status,
        LocalDate date,
        int dday
) {
    public static AddStageResponse from(Stage stage, Integer dday) {
        return new AddStageResponse(
                stage.getStageId(),
                stage.getType().getValue(),
                stage.getType().equals(Type.MID) ? stage.getMidName() : "",
                stage.getStatus().getValue(),
                stage.getDate(),
                dday
        );
    }
}
