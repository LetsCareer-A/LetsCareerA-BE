package com.example.letscareer.self_intro.domain.dto.request;

import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;

import java.util.List;

public record SaveSelfIntroRequest(
        List<SelfIntroDTO> selfIntros
) {
}
