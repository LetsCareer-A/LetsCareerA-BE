package com.example.letscareer.career.dto.response;

import com.example.letscareer.career.dto.CareerDTO;

import java.util.List;

public record GetCareersResponse(
        int currentPage,
        int totalPages,
        List<CareerDTO> careers
) {
}
