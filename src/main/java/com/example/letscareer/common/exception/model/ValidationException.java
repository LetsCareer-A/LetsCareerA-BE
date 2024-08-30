package com.example.letscareer.common.exception.model;

import com.example.letscareer.common.exception.enums.ErrorCode;

public class ValidationException extends LetsException {
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
