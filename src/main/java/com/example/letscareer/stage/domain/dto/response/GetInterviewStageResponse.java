package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.stage.domain.dto.AppealCareerDTO;
import com.example.letscareer.stage.domain.dto.IntReviewDTO;

import java.util.List;

public record GetInterviewStageResponse(
        boolean reviewAvailable,
        IntReviewDTO review,
        List<AppealCareerDTO> appealCareers
) {
}
