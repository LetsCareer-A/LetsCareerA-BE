package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.stage.domain.dto.AppealCareerDTO;
import com.example.letscareer.stage.domain.model.Stage;

import java.util.List;

public record GetDocumentStageResponse(
        Long stageId,
        String type,
        List<SelfIntroDTO> selfIntroductions,
        List<AppealCareerDTO> appealCareers
) {
    public static GetDocumentStageResponse from(Stage stage, List<SelfIntroDTO> selfIntroductions, List<AppealCareerDTO> appealCareers) {
        return new GetDocumentStageResponse(
                stage.getStageId(),
                stage.getType().getValue(),
                selfIntroductions,
                appealCareers
        );
    }
}
