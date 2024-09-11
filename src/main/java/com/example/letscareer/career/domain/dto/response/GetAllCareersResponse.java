package com.example.letscareer.career.domain.dto.response;

import com.example.letscareer.career.domain.dto.CareerWithAppealDTO;

import java.util.List;

public record GetAllCareersResponse(
    List<CareerWithAppealDTO> careers
) {
}
