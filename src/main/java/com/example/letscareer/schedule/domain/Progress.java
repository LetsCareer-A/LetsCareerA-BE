package com.example.letscareer.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Progress {
    PASS("최종 합격"), FAIL("최종 불합격"), DO("진행중");
    private final String value;
}
