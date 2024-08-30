package com.example.letscareer.always.dto;

public record AlwaysDTO(
        Long alwaysId,
        Long stageId,
        String company,
        String department,
        String status
) {
}
