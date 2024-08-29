package com.example.letscareer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {


    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공입니다."); //예



    private final HttpStatus httpStatus;
    private final String message;
}
