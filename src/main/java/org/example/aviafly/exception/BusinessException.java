package org.example.aviafly.exception;


import org.example.aviafly.dto.ErrorResponse;


public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public ErrorResponse.ErrorCode getErrorCode() {
        return ErrorResponse.ErrorCode.INTERNAL_ERROR;
    }
}
