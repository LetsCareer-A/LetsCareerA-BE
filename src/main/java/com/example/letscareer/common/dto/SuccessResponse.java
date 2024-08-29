package com.example.letscareer.common.dto;

import com.example.letscareer.common.exception.enums.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse extends ApiResponse{
    private final int code;
    private final String message;

    public static SuccessResponse success(SuccessCode successCode) {
        return new SuccessResponse(successCode.getHttpStatus().value(), successCode.getMessage());
    }
}