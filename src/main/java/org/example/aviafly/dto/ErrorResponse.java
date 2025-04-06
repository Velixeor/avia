package org.example.aviafly.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


public record ErrorResponse(String message, ErrorCode errorCode) {
    @RequiredArgsConstructor
    @Getter
    public enum ErrorCode {
        BAD_REQUEST(HttpStatus.BAD_REQUEST),
        VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
        INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        NOT_FOUND(HttpStatus.NOT_FOUND),
        ACCESS_DENIED(HttpStatus.FORBIDDEN)
        ;

        private final HttpStatus httpStatus;
    }
}
