package org.example.aviafly.exception;


import org.example.aviafly.dto.ErrorResponse;


public class NotFoundException extends BusinessException {
    public NotFoundException(Class<?> entityClass, Integer id) {
        super(entityClass.getSimpleName() + " with id " + id + " not found");
    }

    @Override
    public ErrorResponse.ErrorCode getErrorCode() {
        return ErrorResponse.ErrorCode.NOT_FOUND;
    }
}
