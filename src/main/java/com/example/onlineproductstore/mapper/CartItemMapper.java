package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.shoppingcart.CartItemDto;
import com.example.onlineproductstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = ProductMapper.class)
public interface CartItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "product", source = "productId", qualifiedByName = "productFromId")
    CartItem toModel(CartItemDto cartItemDto);
}
