package com.rdvn.ilan.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import com.rdvn.ilan.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice//Bu sınıf sadece kendi içindeki metodlarla ilgilenmez; uygulamadaki controller'larda oluşan exception'ları merkezi olarak dinler.
public class GlobalExceptionHandler {

    @ExceptionHandler(AdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAdNotFoundException(AdNotFoundException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAdStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAdStatusException(InvalidAdStatusException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
