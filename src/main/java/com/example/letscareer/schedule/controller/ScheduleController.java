package com.example.letscareer.schedule.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.ErrorCode;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.dto.response.ScheduleResponse;
import com.example.letscareer.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ApiResponse getSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("month") int month,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            ScheduleResponse scheduleResponse = scheduleService.getSchedules(userId, month, page, size);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, scheduleResponse);
        } catch (NotFoundException e) {
            return ErrorResponse.error(ErrorCode.USER_NOT_FOUND_EXCEPTION);
        } catch (Exception e) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
