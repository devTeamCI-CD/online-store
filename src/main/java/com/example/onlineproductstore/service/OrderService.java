package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.order.CompleteOrderDto;
import com.example.onlineproductstore.dto.order.OrderDto;
import com.example.onlineproductstore.dto.order.OrderItemDto;
import com.example.onlineproductstore.dto.order.OrderUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto completeOrder(CompleteOrderDto request);

    List<OrderDto> getAll(Pageable pageable);

    OrderDto updateStatus(Long orderId, OrderUpdateRequestDto request);

    List<OrderItemDto> getAllOrderItems(Long orderId, Pageable pageable);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
