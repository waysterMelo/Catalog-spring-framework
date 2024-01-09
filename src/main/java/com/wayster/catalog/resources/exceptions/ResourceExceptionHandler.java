package com.wayster.catalog.resources.exceptions;

import com.wayster.catalog.services.servicesExceptions.DatabaseException;
import com.wayster.catalog.services.servicesExceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {


    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    HttpStatus notFound = HttpStatus.NOT_FOUND;
    StandardError error = new StandardError();


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
        error.setTimestamp(Instant.now());
        error.setStatus(notFound.value());
        error.setError("Resource Not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(notFound).body(error);
    }


    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
        error.setTimestamp(Instant.now());
        error.setStatus(badRequest.value());
        error.setError("Database Exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(badRequest).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrors> validation(MethodArgumentNotValidException methodEx, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrors err = new ValidationErrors();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage("Validation exception");
        err.setPath(request.getRequestURI());

        for (FieldError f  : methodEx.getBindingResult().getFieldErrors()){
                err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);

    }


}
