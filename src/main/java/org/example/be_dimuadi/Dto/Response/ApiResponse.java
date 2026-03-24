package org.example.be_dimuadi.Dto.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.example.be_dimuadi.Exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({"status", "data", "timestamp"})
public class ApiResponse<T> {
    private Status status;
    private T data;
    private LocalDateTime timestamp;;

    public ApiResponse(Integer code, String message, ErrorCode label, T data) {
        this.status = new Status(code, message, label , UUID.randomUUID().toString().substring(0,5)
        );
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private Integer code;
        private String messages;
        private ErrorCode label;
        private String requestId;
    }

}

