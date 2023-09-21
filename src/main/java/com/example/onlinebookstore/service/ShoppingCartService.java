package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addToCart(CreateCartItemRequestDto request);

    CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto request);

    void deleteCartItem(Long cartItemId);

    Optional<ShoppingCart> findCartByCurrentUser(Long userId);

    void cleanUp(ShoppingCart shoppingCart);
}
