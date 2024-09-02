package com.example.letscareer.schedule.dto.response;

import com.example.letscareer.schedule.dto.AlwaysDTO;

import java.util.List;

public record AlwaysResponse(
        Integer page,
        Integer size,
        List<AlwaysDTO> always
) {
}
