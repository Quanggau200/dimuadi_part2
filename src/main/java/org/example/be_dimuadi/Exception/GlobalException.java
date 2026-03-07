package org.example.be_dimuadi.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public ResponseEntity<String> handleException(RuntimeException runtimeException) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getMessage());
    }



}
