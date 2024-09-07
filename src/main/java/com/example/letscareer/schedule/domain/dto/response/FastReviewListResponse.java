package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.FastDTO;

import java.util.List;

public record FastReviewListResponse(
        Integer page,
        Integer size,
        Integer total,
        List<FastDTO> fastReviews
) {
}
