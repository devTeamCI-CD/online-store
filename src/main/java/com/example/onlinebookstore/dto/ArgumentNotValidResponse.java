package com.example.onlinebookstore.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ArgumentNotValidResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String[] errors;
}
