package com.example.onlineproductstore.dto.order;

import com.example.onlineproductstore.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private Set<OrderItemDto> orderItems;
}
