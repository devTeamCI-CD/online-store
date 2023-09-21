package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.enums.Status;
import lombok.Data;

@Data
public class OrderUpdateRequestDto {
    private Status status;
}
