package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.shoppingcart.CartItemDto;
import com.example.onlineproductstore.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.onlineproductstore.dto.shoppingcart.CreateCartItemRequestDto;
import com.example.onlineproductstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlineproductstore.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addToCart(CreateCartItemRequestDto request);

    CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto request);

    void deleteCartItem(Long cartItemId);

    Optional<ShoppingCart> findCartByCurrentUser(Long userId);

    void cleanUp(ShoppingCart shoppingCart);
}
