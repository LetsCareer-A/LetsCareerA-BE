package com.example.letscareer.self_intro.dto.request;

import com.example.letscareer.self_intro.dto.SelfIntroDTO;

import java.util.List;

public record SaveSelfIntroRequest(
        List<SelfIntroDTO> selfIntros
) {
}
