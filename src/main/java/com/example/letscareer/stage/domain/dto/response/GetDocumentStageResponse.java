package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.stage.domain.dto.AppealCareerDTO;

import java.util.List;

public record GetDocumentStageResponse(
        Long stageId,
        String type,
        List<SelfIntroDTO> selfIntroductions,
        List<AppealCareerDTO> appealCareers
) {
}
