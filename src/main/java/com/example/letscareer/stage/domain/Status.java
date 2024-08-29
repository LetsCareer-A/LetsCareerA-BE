package com.example.letscareer.stage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    DO("준비 진행 중"), FIN ("진행 완료");
    private final String value;
}
