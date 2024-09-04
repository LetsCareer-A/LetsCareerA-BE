package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.FastDTO;

import java.util.List;

public record FastReviewListResponse(
        Integer page,
        Integer size,
        Integer cnt,
        List<FastDTO> fastReviews
) {
}
