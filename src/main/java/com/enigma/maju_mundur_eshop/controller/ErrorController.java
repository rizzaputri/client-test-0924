package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    // Response Status Error
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<?>> responseStatusExceptionHandler(
            ResponseStatusException e
    ) {
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(e.getStatusCode().value())
                .message(e.getReason())
                .build();
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    // Validation Error
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> constraintViolationExceptionHandler(
            ConstraintViolationException e
    ) {
        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Error Database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<?>> dataIntegrityViolationExceptionHandler(
            DataIntegrityViolationException e
    ) {
        CommonResponse.CommonResponseBuilder<Object> builder = CommonResponse.builder();
        HttpStatus httpStatus;

        if (e.getMessage().contains("foreign key constraint")) {
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message("Cannot delete data because other table depent on it");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")){
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message("Data already exist");
            httpStatus = HttpStatus.CONFLICT;
        } else {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message("Internal Server Error");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        return ResponseEntity
                .status(httpStatus)
                .body(builder.build());
    }
}
