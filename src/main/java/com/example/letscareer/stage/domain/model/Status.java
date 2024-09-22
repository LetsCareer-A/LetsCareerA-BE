package com.example.letscareer.stage.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    DO("준비 진행 중"), FIN ("진행 완료"), FAIL("불합경"), PASS("합격");
    private final String value;
}
