package org.example.be_dimuadi.Dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private int code;
    private String message;
    private String status;
   private T data;
   private LocalDateTime timestamp;
   public ApiResponse(int code, String message, String status, T data) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
   }




}
