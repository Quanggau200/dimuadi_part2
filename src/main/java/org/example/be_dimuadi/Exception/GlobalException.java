package org.example.be_dimuadi.Exception;
import org.example.be_dimuadi.Dto.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                400,
                ex.getMessage(),
                ex.getErrorCode(),
                null
        ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(400, "Dữ liệu không hợp lệ ",ErrorCode.INVALID_DATA , errors)
        );
    }




}
