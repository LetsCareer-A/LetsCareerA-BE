package com.example.letscareer.career.service;

import com.example.letscareer.career.domain.dto.converter.CareerConverter;
import com.example.letscareer.career.domain.dto.response.GetAllCareersResponse;
import com.example.letscareer.career.domain.model.Career;
import com.example.letscareer.career.domain.dto.CareerDTO;
import com.example.letscareer.career.domain.dto.request.SaveCareerRequest;
import com.example.letscareer.career.domain.dto.response.GetCareerDetailResponse;
import com.example.letscareer.career.domain.dto.response.GetCareersResponse;
import com.example.letscareer.career.domain.repository.CareerRepository;
import com.example.letscareer.common.exception.enums.ErrorCode;
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

@Service
@RequiredArgsConstructor
public class CareerService {

    @Autowired
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveCareer(Long userId, SaveCareerRequest request) {
        User user = getUser(userId);

        if(request.title() == null || request.title().isEmpty()) {
            throw new ValidationException(ErrorCode.CAREER_TITLE_IS_EMPTY);
        }

        Career career = Career.of(user, request);
        careerRepository.save(career);
    }

    @Transactional
    public GetCareerDetailResponse getCareerDetail(Long userId, Long careerId) {
        User user = getUser(userId);
        Career career = getCareer(careerId, user);
        return GetCareerDetailResponse.from(career);
    }

    @Transactional
    public GetCareersResponse getCareers(Long userId, int page, int size) {
        User user = getUser(userId);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Career> careers = careerRepository.findByUser(user, pageable);
        List<CareerDTO> careerDTOS = CareerConverter.convertToCareerDTOList(careers);

        return GetCareersResponse.from(careers, careerDTOS);
    }

    @Transactional
    @Cacheable(value = "allCareersCache", key = "#userId", unless = "#result == null || #result.careers.size() == 0")
    public GetAllCareersResponse getAllCareers(Long userId) {
        User user = getUser(userId);
        List<Career> careers = careerRepository.findByUser(user);
        List<CareerDTO> careerDTOS = CareerConverter.convertToCareerDTOList(careers);
        return new GetAllCareersResponse(careerDTOS);
    }

    private User getUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION));
    }

    private Career getCareer(Long careerId, User user) {
        return careerRepository.findByCareerIdAndUser(careerId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CAREER_NOT_FOUND_EXCEPTION));
    }
}
