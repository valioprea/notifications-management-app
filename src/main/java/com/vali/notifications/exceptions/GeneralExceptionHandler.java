package com.vali.notifications.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (ex instanceof CustomDatabaseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database access exception.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please contact developers if error persists");
    }
}
