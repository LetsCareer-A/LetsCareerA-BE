package com.example.letscareer.stage.dto.response;

import com.example.letscareer.stage.dto.AppealCareerDTO;
import com.example.letscareer.stage.dto.IntReviewDTO;

import java.util.List;

public record GetInterviewStageResponse(
        boolean reviewAvailable,
        IntReviewDTO review,
        List<AppealCareerDTO> appealCareers
) {
}
