package com.example.letscareer.career.service;

import com.example.letscareer.career.domain.Career;
import com.example.letscareer.career.dto.request.SaveCareerRequest;
import com.example.letscareer.career.dto.response.GetCareerDetailResponse;
import com.example.letscareer.career.repository.CareerRepository;
import com.example.letscareer.common.exception.enums.ErrorCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.common.exception.model.ValidationException;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareerService {

    @Autowired
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;

    public void saveCareer(Long userId, SaveCareerRequest request) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        if(request.title() == null || request.title().isEmpty()) {
            throw new ValidationException(ErrorCode.CAREER_TITLE_IS_EMPTY);
        }

        try {
            Career career = Career.builder()
                    .user(user)
                    .category(request.category())
                    .title(request.title())
                    .situation(request.situation())
                    .task(request.task())
                    .action(request.action())
                    .result(request.result())
                    .build();

            careerRepository.save(career);
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    public GetCareerDetailResponse getCareerDetail(Long userId, Long careerId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        Career career = careerRepository.findByCareerIdAndUser(careerId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAREER_NOT_FOUND_EXCEPTION));

        return new GetCareerDetailResponse(
                career.getCareerId(),
                career.getCategory().getValue(),
                career.getTitle(),
                career.getSituation(),
                career.getTask(),
                career.getAction(),
                career.getResult()
        );
    }
}
