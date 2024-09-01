package com.example.letscareer.appealCareer.dto.request;

import java.util.List;

public record AddAppealCareersRequest(
        List<Long> careers
) {
}
