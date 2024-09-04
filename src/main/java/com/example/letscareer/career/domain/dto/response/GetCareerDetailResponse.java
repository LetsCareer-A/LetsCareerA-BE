package com.example.letscareer.career.domain.dto.response;

import com.example.letscareer.career.domain.model.Career;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record GetCareerDetailResponse(
        Long careerId,
        @Enumerated(EnumType.STRING)
        String category,
        String title,
        String situation,
        String task,
        String action,
        String result
) {
        public static GetCareerDetailResponse from(Career career) {
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
