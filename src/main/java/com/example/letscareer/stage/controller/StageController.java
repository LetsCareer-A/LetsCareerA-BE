package com.example.letscareer.stage.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.stage.domain.dto.request.AddStageRequest;
import com.example.letscareer.stage.domain.dto.request.UpdateStageStatusRequest;
import com.example.letscareer.stage.domain.dto.response.AddStageResponse;
import com.example.letscareer.stage.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PostMapping("/schedules/{scheduleId}/stages")
    public ApiResponse addStage(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @RequestBody AddStageRequest request
            ) {

        try {
            AddStageResponse response = stageService.addStage(userId, scheduleId, request);
            return SuccessResponse.success(SuccessCode.STAGE_ADD_SUCCESS, response);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PutMapping("/schedules/{scheduleId}/stages/{stageId}")
    public ApiResponse updateStageStatus(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId,
            @RequestBody UpdateStageStatusRequest request
    ) {
        try {
            stageService.updateStageStatus(userId, scheduleId, stageId, request);
            return SuccessNonDataResponse.success(SuccessCode.STAGE_UPDATE_SUCCESS);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/schedules/{scheduleId}/stages")
    public ApiResponse getStages(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId
    ) {
        try {
            return SuccessResponse.success(SuccessCode.STAGES_GET_SUCCESS , stageService.getStages(userId, scheduleId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/schedules/{scheduleId}/stages/{stageId}/document")
    public ApiResponse getDocumentStage(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId
    ) {
        try {
            return SuccessResponse.success(SuccessCode.DOC_STAGES_GET_SUCCESS , stageService.getDocumentStage(userId, scheduleId, stageId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/schedules/{scheduleId}/stages/{stageId}/mid")
    public ApiResponse getMidStage(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId
    ) {
        try {
            return SuccessResponse.success(SuccessCode.MID_STAGES_GET_SUCCESS , stageService.getMidStage(userId, scheduleId, stageId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/schedules/{scheduleId}/stages/{stageId}/interview")
    public ApiResponse getInterviewStage(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId
    ) {
        try {
            return SuccessResponse.success(SuccessCode.INT_STAGES_GET_SUCCESS , stageService.getInterviewStage(userId, scheduleId, stageId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
