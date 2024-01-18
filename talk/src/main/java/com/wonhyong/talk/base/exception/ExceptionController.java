package com.wonhyong.talk.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleNoElement(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, IllegalAccessException.class})
    public ResponseEntity<String> handleUserExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentExceptions(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleUnknownExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
