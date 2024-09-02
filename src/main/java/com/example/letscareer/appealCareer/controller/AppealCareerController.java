package com.example.letscareer.appealCareer.controller;

import com.example.letscareer.appealCareer.dto.request.AddAppealCareersRequest;
import com.example.letscareer.appealCareer.service.AppealCareerService;
import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppealCareerController {

    private final AppealCareerService appealCareerService;

    @PostMapping("/schedules/{scheduleId}/stages/{stageId}/career")
    public ApiResponse addAppealCareers(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId,
            @RequestBody AddAppealCareersRequest request
    ) {
        try {
            appealCareerService.addAppealCareer(userId, scheduleId, stageId, request);
            return SuccessNonDataResponse.success(SuccessCode.APPEAL_CAREERS_ADD_SUCCESS);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
