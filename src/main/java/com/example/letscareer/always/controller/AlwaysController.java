package com.example.letscareer.always.controller;

import com.example.letscareer.always.dto.response.AlwaysResponse;
import com.example.letscareer.always.service.AlwaysService;
import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/always")
public class AlwaysController {
    private final AlwaysService alwaysService;
    @GetMapping
    public ApiResponse getSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            AlwaysResponse alwaysResponse = alwaysService.getAlwaysList(userId, page, size);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, alwaysResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
