package com.example.letscareer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {


    SCHEDULE_SUCCESS(HttpStatus.OK, "일정 찾기 성공입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
