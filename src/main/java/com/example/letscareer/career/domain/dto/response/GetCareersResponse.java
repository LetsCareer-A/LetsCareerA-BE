package com.example.letscareer.career.domain.dto.response;

import com.example.letscareer.career.domain.dto.CareerDTO;
import com.example.letscareer.career.domain.model.Career;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetCareersResponse(
        int currentPage,
        int totalPages,
        List<CareerDTO> careers
) {
    public static GetCareersResponse from(Page<Career> careers, List<CareerDTO> careerDTOList) {
        return new GetCareersResponse(
                careers.getNumber() + 1,
                careers.getTotalPages(),
                careerDTOList
        );
    }
}
