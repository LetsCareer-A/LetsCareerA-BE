package com.example.letscareer.common.exception.model;

import com.example.letscareer.common.exception.enums.ErrorCode;

public class BadRequestException extends LetsException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
