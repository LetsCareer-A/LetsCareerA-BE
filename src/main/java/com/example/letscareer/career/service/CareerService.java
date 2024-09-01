package com.example.letscareer.career.service;

import com.example.letscareer.career.domain.Career;
import com.example.letscareer.career.dto.CareerDTO;
import com.example.letscareer.career.dto.request.SaveCareerRequest;
import com.example.letscareer.career.dto.response.GetCareerDetailResponse;
import com.example.letscareer.career.dto.response.GetCareersResponse;
import com.example.letscareer.career.repository.CareerRepository;
import com.example.letscareer.common.exception.enums.ErrorCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.common.exception.model.ValidationException;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerService {

    @Autowired
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;

    @Transactional
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

    public GetCareersResponse getCareers(Long userId, int page, int size) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Career> careers = careerRepository.findByUser(user, pageable);
        List<CareerDTO> careerDTOS = careers.getContent().stream()
                .map(career -> new CareerDTO(
                        career.getCareerId(),
                        career.getCategory().getValue(),
                        career.getTitle(),
                        toSummary(career.getSituation())
                ))
                .collect(Collectors.toList());

        return new GetCareersResponse(
                careers.getNumber() + 1,
                careers.getTotalPages(),
                careerDTOS
        );
    }

    private String toSummary(String content) {
        return content.length() > 25 ? content.substring(0, 25) + "..." : content;
    }
}
