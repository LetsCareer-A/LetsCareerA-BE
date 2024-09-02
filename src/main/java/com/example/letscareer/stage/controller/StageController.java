package com.example.letscareer.stage.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.stage.dto.request.AddStageRequest;
import com.example.letscareer.stage.dto.response.AddStageResponse;
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
}
