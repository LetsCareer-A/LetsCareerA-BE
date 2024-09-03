package com.example.letscareer.stage.dto.response;

import com.example.letscareer.stage.dto.MidReviewDTO;

public record GetMidStageResponse(
        boolean reviewAvailable,
        MidReviewDTO review
) {
}
