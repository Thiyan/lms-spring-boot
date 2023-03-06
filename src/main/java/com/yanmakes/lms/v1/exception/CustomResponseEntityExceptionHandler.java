package com.yanmakes.lms.v1.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Object> handleNoContentException(NoContentException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        //Log
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getFieldError().getDefaultMessage(),
                request.getDescription(false),
                ex.getErrorCount(),
                ex.getFieldError().getField());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
