package com.example.letscareer.schedule.dto;

import java.util.List;
import java.util.Map;

public record CompanyReviewDTO(
        String company,
        Map<String, List<CompanyReviewDetailDTO>> reviewTypes
) {
}
