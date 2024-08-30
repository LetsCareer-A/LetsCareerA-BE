package com.example.letscareer.always.dto.response;

import com.example.letscareer.always.domain.Always;

import java.util.List;

public record AlwaysResponse(
        Integer page,
        Integer size,
        List<Always> always
) {
}
