package com.example.letscareer.career.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    ACTIVITY("대외활동"), COMPETITION("공모전"), TASK("실무"), CERTIFICATION("자격증"), PROJECT("프로젝트"), OTHER("기타");
    private final String value;
}
