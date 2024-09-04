package com.example.letscareer.stage.domain.dto.request;

import com.example.letscareer.stage.domain.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UpdateStageStatusRequest(
        @Enumerated(EnumType.STRING)
        Status status
) {
}
