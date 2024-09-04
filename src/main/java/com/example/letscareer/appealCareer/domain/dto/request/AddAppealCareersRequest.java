package com.example.letscareer.appealCareer.domain.dto.request;

import java.util.List;

public record AddAppealCareersRequest(
        List<Long> careers
) {
}
