package com.example.onlineproductstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.onlineproductstore.dto.order.CompleteOrderDto;
import com.example.onlineproductstore.dto.order.OrderDto;
import com.example.onlineproductstore.mapper.OrderItemMapper;
import com.example.onlineproductstore.mapper.OrderMapper;
import com.example.onlineproductstore.model.Order;
import com.example.onlineproductstore.model.OrderItem;
import com.example.onlineproductstore.model.ShoppingCart;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.repository.order.OrderItemRepository;
import com.example.onlineproductstore.repository.order.OrderRepository;
import com.example.onlineproductstore.service.impl.OrderServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserService userService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCompleteOrder() {
        // Given
        User user = new User();
        user.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        Order order = new Order();
        order.setId(1L);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(1);
        order.setOrderItems(Collections.singleton(orderItem));
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        CompleteOrderDto completeOrderDto = new CompleteOrderDto();

        List<OrderItem> savedOrderItems = Collections.singletonList(orderItem);
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartService.findCartByCurrentUser(user.getId()))
                .thenReturn(Optional.of(shoppingCart));
        when(orderMapper.toOrder(completeOrderDto, shoppingCart)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderItemRepository.saveAll(order.getOrderItems())).thenReturn(savedOrderItems);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        // When
        OrderDto result = orderService.completeOrder(completeOrderDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    // Similar tests can be written for other methods in OrderServiceImpl
}
