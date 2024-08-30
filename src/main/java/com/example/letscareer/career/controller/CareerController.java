package com.example.letscareer.career.controller;

import com.example.letscareer.career.dto.request.SaveCareerRequest;
import com.example.letscareer.career.service.CareerService;
import com.example.letscareer.common.dto.ApiResponse;
import com.example.letscareer.common.dto.ErrorResponse;
import com.example.letscareer.common.dto.SuccessNonDataResponse;
import com.example.letscareer.common.exception.enums.ErrorCode;
import com.example.letscareer.common.exception.enums.SuccessCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.common.exception.model.ValidationException;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
public class CareerController {

    private final UserRepository userRepository;
    private final CareerService careerService;

    @PostMapping
    public ApiResponse saveCareer(@RequestHeader("userId") Long userId,
                                  @RequestBody SaveCareerRequest request) {

        try {
            careerService.saveCareer(userId, request);
            return SuccessNonDataResponse.success(SuccessCode.SAVE_CAREER_SUCCESS);
        } catch (NotFoundException | BadRequestException | ValidationException e) {
            return ErrorResponse.error(e.getErrorCode());
        }
    }
}