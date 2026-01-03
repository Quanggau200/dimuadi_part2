package org.example.be_dimuadi.Exception;

import org.example.be_dimuadi.Service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public ResponseEntity<String> handleException(RuntimeException runtimeException) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getMessage());
    }

}
