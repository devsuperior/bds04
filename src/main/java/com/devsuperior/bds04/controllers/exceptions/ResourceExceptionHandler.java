package com.devsuperior.bds04.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError error = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        error.setTimeStamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage("Um ou mais campos não foram preenchidos corretamente, tente novamente");
        error.setPath(request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(status).body(error);
    }

}
