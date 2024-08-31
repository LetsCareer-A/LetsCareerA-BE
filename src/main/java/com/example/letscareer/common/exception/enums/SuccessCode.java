package com.example.letscareer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {


    SCHEDULE_SUCCESS(HttpStatus.OK, "일정 찾기 성공입니다."),
    POST_SCHEDULE_SUCCESS(HttpStatus.OK, "새 일정 추가 성공입니다."),
    TODO_SUCCESS(HttpStatus.OK, "투두 찾기 성공입니다."),
    SAVE_CAREER_SUCCESS(HttpStatus.CREATED, "커리어 등록 성공"),
    GET_CAREER_DETAIL_SUCCESS(HttpStatus.OK, "커리어 상세 조회 성공");

    private final HttpStatus httpStatus;
    private final String message;
}
