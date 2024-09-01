package com.example.letscareer.mid_review.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.mid_review.dto.request.PostMidReviewRequest;
import com.example.letscareer.mid_review.dto.response.FastReviewsResponse;
import com.example.letscareer.mid_review.service.MidReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MidReviewController {

    private final MidReviewService midReviewService;

    @PostMapping("/schedules/{scheduleId}/stages/{stageId}/reviews/mid")
    public ApiResponse postMidReview(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId,
            @RequestBody PostMidReviewRequest request
    ) {
        try {
            midReviewService.postMidReview(userId, scheduleId, stageId, request);
            return SuccessNonDataResponse.success(SuccessCode.MID_REVIEW_SAVE_SUCCESS);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
