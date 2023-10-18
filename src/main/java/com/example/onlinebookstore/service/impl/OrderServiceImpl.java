package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.CompleteOrderDto;
import com.example.onlinebookstore.dto.order.OrderDto;
import com.example.onlinebookstore.dto.order.OrderItemDto;
import com.example.onlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.order.OrderItemRepository;
import com.example.onlinebookstore.repository.order.OrderRepository;
import com.example.onlinebookstore.service.OrderService;
import com.example.onlinebookstore.service.ShoppingCartService;
import com.example.onlinebookstore.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto completeOrder(CompleteOrderDto request) {
        User user = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartService.findCartByCurrentUser(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user:" + user.getUsername()));
        Order order = orderRepository.save(orderMapper.toOrder(request, shoppingCart));
        List<OrderItem> orderItemList = orderItemRepository.saveAll(order.getOrderItems());
        order.setOrderItems(Set.copyOf(orderItemList));
        shoppingCartService.cleanUp(shoppingCart);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAll(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        return orderRepository
                .findAllForCurrentUserWithUserAndOrderItems(user.getId(), pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto updateStatus(Long orderId, OrderUpdateRequestDto request) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by id: " + orderId));
        order.setStatus(request.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> getAllOrderItems(Long orderId, Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        return orderItemRepository
                .findAllByOrderIdAndCurrentUser(orderId, pageable, user.getId())
                .stream()
                .map(orderItemMapper::toOrderItemDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        User user = userService.getAuthenticatedUser();
        return orderItemRepository
                .findByIdAndOrderIdAndCurrentUser(orderId, itemId, user.getId())
                .map(orderItemMapper::toOrderItemDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find Order Item by id: " + itemId));
    }
}
