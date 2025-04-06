package org.example.aviafly.exception;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.aviafly.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getMessage(), ErrorResponse.ErrorCode.VALIDATION_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException e, HttpServletResponse response) {
        response.setStatus(e.getErrorCode().getHttpStatus().value());
        return new ErrorResponse(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse(e.getMessage(), ErrorResponse.ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(Exception e) {
        log.error("Unexpected exception.", e);
        return new ErrorResponse(e.getMessage(), ErrorResponse.ErrorCode.INTERNAL_ERROR);
    }
}
