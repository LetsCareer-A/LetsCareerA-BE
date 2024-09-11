package com.example.letscareer.career.controller;

import com.example.letscareer.career.domain.dto.request.SaveCareerRequest;
import com.example.letscareer.career.service.CareerService;
import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.dto.SuccessResponse;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.common.exception.model.ValidationException;
import com.example.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CareerController {

    private final UserRepository userRepository;
    private final CareerService careerService;

    @PostMapping("/careers")
    public ApiResponse saveCareer(@RequestHeader("userId") Long userId,
                                  @RequestBody SaveCareerRequest request) {
        try {
            careerService.saveCareer(userId, request);
            return SuccessNonDataResponse.success(SuccessCode.SAVE_CAREER_SUCCESS);
        } catch (NotFoundException | BadRequestException | ValidationException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/careers/{careerId}")
    public ApiResponse getCareerDetail(@RequestHeader("userId") Long userId,
                                 @PathVariable Long careerId) {
        try {
            return SuccessResponse.success(SuccessCode.GET_CAREER_DETAIL_SUCCESS, careerService.getCareerDetail(userId, careerId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/careers")
    public ApiResponse getCareers(@RequestHeader("userId") Long userId,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "15") int size,
                                  @RequestParam(value = "category", defaultValue = "ACTIVITY") List<String> category) {
        try {
            return SuccessResponse.success(SuccessCode.GET_CAREER_SUCCESS, careerService.getCareers(userId, page, size, category));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }

    @GetMapping("/schedules/{scheduleId}/stages/{stageId}/careers/all")
    public ApiResponse getAllCareers(@RequestHeader("userId") Long userId,
                                     @PathVariable Long scheduleId,
                                     @PathVariable Long stageId) {
        try {
            return SuccessResponse.success(SuccessCode.GET_ALL_CAREERS_SUCCESS, careerService.getAllCareers(userId, scheduleId, stageId));
        } catch (NotFoundException | BadRequestException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}
