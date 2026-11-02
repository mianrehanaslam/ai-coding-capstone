package com.example.capstone.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(TaskNotFoundException exception) {
        return response(HttpStatus.NOT_FOUND, "Task not found");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> handleValidation(Exception exception) {
        return response(HttpStatus.BAD_REQUEST, "Request validation failed");
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
