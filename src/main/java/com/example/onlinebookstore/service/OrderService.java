package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.CompleteOrderDto;
import com.example.onlinebookstore.dto.order.OrderDto;
import com.example.onlinebookstore.dto.order.OrderItemDto;
import com.example.onlinebookstore.dto.order.OrderUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto completeOrder(CompleteOrderDto request);

    List<OrderDto> getAll(Pageable pageable);

    OrderDto updateStatus(Long orderId, OrderUpdateRequestDto request);

    List<OrderItemDto> getAllOrderItems(Long orderId, Pageable pageable);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
