package com.library.librarymanagement.exception;

import com.library.librarymanagement.request.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private GlobalExceptionHandler() {
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public static ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {
        GlobalExceptionHandler.LOGGER.error(exception.getMessage(), exception);

        var apiResponse = new ApiResponse<Object>();

        apiResponse.setCode(1);

        final var fieldError = exception.getFieldError();
        if (fieldError != null) {
            apiResponse.setMessage(fieldError.getDefaultMessage());
        }

        return ResponseEntity.ok().body(apiResponse);
    }

    @ExceptionHandler
    public static ResponseEntity<ApiResponse<Object>> handleThrowable(final Throwable throwable) {
        GlobalExceptionHandler.LOGGER.error(throwable.getMessage(), throwable);

        var apiResponse = new ApiResponse<Object>();
        apiResponse.setCode(-1);
        apiResponse.setMessage(throwable.getMessage());
        return ResponseEntity.ok().body(apiResponse);
    }
}
