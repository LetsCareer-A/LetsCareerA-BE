package com.example.letscareer.int_review.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.int_review.domain.dto.request.PostIntReviewRequest;
import com.example.letscareer.int_review.domain.dto.response.IntReviewDetailResponse;
import com.example.letscareer.int_review.service.IntReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IntReviewController {

    private final IntReviewService intReviewService;

    @GetMapping("/review/int")
    public ApiResponse getIntReview(
            @RequestHeader("userId") Long userId,
            @RequestParam Long scheduleId,
            @RequestParam Long stageId,
            @RequestParam Long intReviewId
    ){
        try {
            IntReviewDetailResponse intReviewDetailResponse = intReviewService.getIntReview(userId, scheduleId, stageId, intReviewId);
            return SuccessResponse.success(SuccessCode.INT_REVIEW_GET_SUCCESS, intReviewDetailResponse);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PostMapping("/schedules/{scheduleId}/stages/{stageId}/reviews/interview")
    public ApiResponse postIntReview(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId,
            @RequestBody PostIntReviewRequest request
    ) {
        try {
            intReviewService.postIntReview(userId, scheduleId, stageId, request);
            return SuccessNonDataResponse.success(SuccessCode.INT_REVIEW_SAVE_SUCCESS);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
