package com._7.hr.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleResourceNotFound(
                        ResourceNotFoundException ex,
                        WebRequest req) {
                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                req.getDescription(false),
                                ex.getMessage());

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(TenantAlreadyExistsException.class)
        public ResponseEntity<ErrorDetails> handleTenantAlreadyExistsException(
                        TenantAlreadyExistsException ex,
                        WebRequest req) {
                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                req.getDescription(false),
                                ex.getMessage());
                return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Object> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException ex,
                        WebRequest req) {
                Map<String, Object> body = new HashMap<>();

                body.put("status", HttpStatus.BAD_REQUEST.value());
                body.put("timeStamp", LocalDateTime.now());
                body.put("path", req.getDescription(false));

                Map<String, String> error = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

                body.put("error", error);
                body.put("message", "validation failed");
                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(EmployeeAlreadyExistsException.class)
        public ResponseEntity<ErrorDetails> handleEmployeeAlreadyExistsException(
                        EmployeeAlreadyExistsException ex,
                        WebRequest req) {
                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                req.getDescription(false),
                                ex.getMessage());

                return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleGlobalException(
                        Exception ex,
                        WebRequest req) {
                ErrorDetails errorDetails = new ErrorDetails(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                req.getDescription(false),
                                ex.getMessage());
                return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
