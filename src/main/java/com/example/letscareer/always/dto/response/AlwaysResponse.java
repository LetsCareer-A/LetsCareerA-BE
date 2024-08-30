package com.example.letscareer.always.dto.response;

import com.example.letscareer.always.dto.AlwaysDTO;

import java.util.List;

public record AlwaysResponse(
        Integer page,
        Integer size,
        List<AlwaysDTO> always
) {
}
