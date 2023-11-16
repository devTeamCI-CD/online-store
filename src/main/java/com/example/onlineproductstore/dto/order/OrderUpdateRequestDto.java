package com.example.onlineproductstore.dto.order;

import com.example.onlineproductstore.enums.Status;
import lombok.Data;

@Data
public class OrderUpdateRequestDto {
    private Status status;
}
