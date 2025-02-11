package com.example.Franchise.Management.System.exception_handler;

import com.example.Franchise.Management.System.exception.InvalidTokenException;
import com.example.Franchise.Management.System.exception.OutOfStockException;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> runtimeExceptionHandler(RuntimeException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> invalidTokenExceptionHandler(InvalidTokenException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Map<String, String>> outOfStockExceptionHandler(OutOfStockException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> unauthorizedExceptionHandler(UnauthorizedException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFoundExceptionHandler(UserNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}