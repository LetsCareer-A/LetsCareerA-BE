package com.example.letscareer.self_intro.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.self_intro.domain.dto.request.SaveSelfIntroRequest;
import com.example.letscareer.self_intro.service.SelfIntroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SelfIntroController {

    private final SelfIntroService selfIntroService;

    @PutMapping("/schedules/{scheduleId}/stages/{stageId}/self-intro")
    public ApiResponse saveSelfIntro(
            @RequestHeader("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long stageId,
            @RequestBody SaveSelfIntroRequest request
    ) {
        try {
            selfIntroService.saveSelfIntro(userId, scheduleId, stageId, request);
            return SuccessNonDataResponse.success(SuccessCode.SELF_INTRO_SAVE_SUCCESS);
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
