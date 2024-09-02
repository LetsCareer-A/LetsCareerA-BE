package com.example.letscareer.stage.dto.request;

import com.example.letscareer.stage.domain.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UpdateStageStatusRequest(
        @Enumerated(EnumType.STRING)
        Status status
) {
}
