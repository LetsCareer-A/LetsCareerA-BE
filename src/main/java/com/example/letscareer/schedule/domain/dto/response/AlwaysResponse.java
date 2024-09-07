package com.example.letscareer.schedule.domain.dto.response;

import com.example.letscareer.schedule.domain.dto.AlwaysDTO;

import java.util.List;

public record AlwaysResponse(
        Integer page,
        Integer size,
        Long total,
        List<AlwaysDTO> always
) {
}
