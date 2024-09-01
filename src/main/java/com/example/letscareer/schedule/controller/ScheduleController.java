package com.example.letscareer.schedule.controller;

import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.schedule.dto.request.SchedulePostRequest;
import com.example.letscareer.schedule.dto.response.AlwaysResponse;
import com.example.letscareer.schedule.dto.response.DateClickScheduleResponse;
import com.example.letscareer.schedule.dto.response.FastReviewListResponse;
import com.example.letscareer.schedule.dto.response.ScheduleResponse;
import com.example.letscareer.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
            }catch (NotFoundException | BadRequestException e) {
                return ErrorResponse.error(e.getErrorCode());
            }
    }

    @GetMapping("/date")
    public ApiResponse getDateSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ){
        try{
            DateClickScheduleResponse response = scheduleService.getDateSchedules(userId, date);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, response);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
    @GetMapping("/always")
    public ApiResponse getSchedules(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            AlwaysResponse alwaysResponse = scheduleService.getAlwaysList(userId, page, size);
            return SuccessResponse.success(SuccessCode.SCHEDULE_SUCCESS, alwaysResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
    @GetMapping("/reviews/fast")
    public ApiResponse getFastReviews(
            @RequestHeader("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        try {
            FastReviewListResponse fastReviewListResponse = scheduleService.getFastReviews(userId, page, size);
            return SuccessResponse.success(SuccessCode.FAST_REVIEW_LIST_SUCEESS, fastReviewListResponse);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @PostMapping
    public ApiResponse postNewSchedule(
            @RequestHeader("userId") Long userId,
            @RequestBody SchedulePostRequest request
            ){
        try{
            scheduleService.postSchedule(userId, request);
            return SuccessNonDataResponse.success(SuccessCode.POST_SCHEDULE_SUCCESS);
        }catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
