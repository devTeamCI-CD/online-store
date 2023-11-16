package com.example.onlineproductstore.service.impl;

import com.example.onlineproductstore.dto.order.CompleteOrderDto;
import com.example.onlineproductstore.dto.order.OrderDto;
import com.example.onlineproductstore.dto.order.OrderItemDto;
import com.example.onlineproductstore.dto.order.OrderUpdateRequestDto;
import com.example.onlineproductstore.exception.EntityNotFoundException;
import com.example.onlineproductstore.mapper.OrderItemMapper;
import com.example.onlineproductstore.mapper.OrderMapper;
import com.example.onlineproductstore.model.Order;
import com.example.onlineproductstore.model.OrderItem;
import com.example.onlineproductstore.model.ShoppingCart;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.repository.order.OrderItemRepository;
import com.example.onlineproductstore.repository.order.OrderRepository;
import com.example.onlineproductstore.service.OrderService;
import com.example.onlineproductstore.service.ShoppingCartService;
import com.example.onlineproductstore.service.UserService;
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
