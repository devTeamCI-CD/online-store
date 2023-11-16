package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlineproductstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);
}
