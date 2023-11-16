package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.order.OrderItemDto;
import com.example.onlineproductstore.model.CartItem;
import com.example.onlineproductstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "price", source = "product.price")
    OrderItem toOrderItem(CartItem orderItemDto);

    @Mapping(target = "productId", source = "product.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
