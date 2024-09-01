package com.example.letscareer.mid_review.dto.response;

import com.example.letscareer.mid_review.dto.FastDTO;

import java.util.List;

public record FastReviewsResponse(
        Integer page,
        Integer size,
        Integer cnt,
        List<FastDTO> fastReviews
) {
}
