package com.example.letscareer.stage.dto.response;

import com.example.letscareer.self_intro.dto.SelfIntroDTO;
import com.example.letscareer.stage.dto.AppealCareerDTO;

import java.util.List;

public record GetDocumentStageResponse(
        Long stageId,
        String type,
        List<SelfIntroDTO> selfIntroductions,
        List<AppealCareerDTO> appealCareers
) {
}
