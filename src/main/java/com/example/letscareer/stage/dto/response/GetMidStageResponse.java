package com.example.letscareer.stage.dto.response;

import com.example.letscareer.stage.dto.ReviewDTO;

public record GetMidStageResponse(
        boolean reviewAvailable,
        ReviewDTO review
) {
}
