package com.example.letscareer.stage.domain.dto.response;

import com.example.letscareer.stage.domain.dto.MidReviewDTO;

public record GetMidStageResponse(
        boolean reviewAvailable,
        MidReviewDTO review
) {
}
