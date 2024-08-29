package com.example.letscareer.common.exception.model;

import com.example.letscareer.common.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class LetsException extends RuntimeException{
    private final ErrorCode errorCode;

    public LetsException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
