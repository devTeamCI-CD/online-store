package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.order.CompleteOrderDto;
import com.example.onlineproductstore.dto.order.OrderDto;
import com.example.onlineproductstore.model.Order;
import com.example.onlineproductstore.model.OrderItem;
import com.example.onlineproductstore.model.ShoppingCart;
import java.math.BigDecimal;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "orderItems", source = "shoppingCart.cartItems")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    Order toOrder(CompleteOrderDto orderDto, ShoppingCart shoppingCart);

    @AfterMapping
    default void setTotalAndOrder(@MappingTarget Order order) {
        BigDecimal total = order.getOrderItems().stream()
                .peek(orderItem -> orderItem.setOrder(order))
                .map(this::calculateTotalForItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);
    }

    private BigDecimal calculateTotalForItem(OrderItem item) {
        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
        return item.getPrice().multiply(quantity);
    }
}
