package com.example.letscareer.common.exception.model;

import com.example.letscareer.common.exception.enums.ErrorCode;

public class NotFoundException extends LetsException{
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
