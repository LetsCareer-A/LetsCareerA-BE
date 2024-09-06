package com.example.letscareer.stage.domain.dto;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;

public record AppealCareerDTO(
        Long careerId,
        String category,
        String title
) {
    public static AppealCareerDTO from(AppealCareer appealCareer) {
        return new AppealCareerDTO(
                appealCareer.getCareer().getCareerId(),
                appealCareer.getCareer().getCategory().getValue(),
                appealCareer.getCareer().getTitle()
        );
    }
}
