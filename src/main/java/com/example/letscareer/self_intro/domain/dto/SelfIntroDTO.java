package com.example.letscareer.self_intro.domain.dto;

import com.example.letscareer.self_intro.domain.model.SelfIntro;

public record SelfIntroDTO(
        String title,
        int sequence,
        String content) {

    public static SelfIntroDTO from(SelfIntro selfIntro) {
        return new SelfIntroDTO(
                selfIntro.getTitle(),
                selfIntro.getSequence(),
                selfIntro.getContent()
        );
    }
}
