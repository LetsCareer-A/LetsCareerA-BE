package com.example.letscareer.career.domain.dto.response;

import com.example.letscareer.career.domain.dto.CareerDTO;

import java.util.List;

public record GetAllCareersResponse(
    List<CareerDTO> careers
) {
}
