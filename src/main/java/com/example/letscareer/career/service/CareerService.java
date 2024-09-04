package com.example.letscareer.career.service;

import com.example.letscareer.career.domain.dto.response.GetAllCareersResponse;
import com.example.letscareer.career.domain.model.Career;
import com.example.letscareer.career.domain.dto.CareerDTO;
import com.example.letscareer.career.domain.dto.request.SaveCareerRequest;
import com.example.letscareer.career.domain.dto.response.GetCareerDetailResponse;
import com.example.letscareer.career.domain.dto.response.GetCareersResponse;
import com.example.letscareer.career.domain.repository.CareerRepository;
import com.example.letscareer.common.exception.enums.ErrorCode;
import com.example.letscareer.common.exception.model.BadRequestException;
import com.example.letscareer.common.exception.model.NotFoundException;
import com.example.letscareer.common.exception.model.ValidationException;
import com.example.letscareer.user.domain.User;
import com.example.letscareer.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

        Career career = Career.of(user, request);
        careerRepository.save(career);
    }

    public GetCareerDetailResponse getCareerDetail(Long userId, Long careerId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        Career career = careerRepository.findByCareerIdAndUser(careerId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAREER_NOT_FOUND_EXCEPTION));

        return GetCareerDetailResponse.from(career);
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

    @Cacheable(value = "allCareersCache", key = "#userId", unless = "#result == null || #result.careers.size() == 0")
    public GetAllCareersResponse getAllCareers(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        List<Career> careers = careerRepository.findByUser(user);

        List<CareerDTO> careerDTOS = careers.stream()
                .map(career -> new CareerDTO(
                        career.getCareerId(),
                        career.getCategory().getValue(),
                        career.getTitle(),
                        toSummary(career.getSituation())
                ))
                .collect(Collectors.toList());

        return new GetAllCareersResponse(careerDTOS);
    }

    private String toSummary(String content) {
        return content.length() > 25 ? content.substring(0, 25) + "..." : content;
    }
}
