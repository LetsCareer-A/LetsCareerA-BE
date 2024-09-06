package com.example.letscareer.stage.domain.dto;


import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.stage.domain.model.Type;

public record StageDTO(
        Long stageId,
        int order,
        String type,
        String mid_name,
        String status,
        String date,
        String dday
) {
    public static StageDTO from(Stage stage, boolean isAlways, String dday) {
        return new StageDTO(
                stage.getStageId(),
                stage.getOrder(),
                stage.getType().getValue(),
                stage.getType().equals(Type.MID) ? stage.getMidName() : "",
                stage.getStatus().getValue(),
                isAlways ? "" : stage.getDate().toString(),
                dday
        );
    }
}
