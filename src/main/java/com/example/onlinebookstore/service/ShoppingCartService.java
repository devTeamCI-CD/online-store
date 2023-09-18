package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.CartItemDto;
import com.example.onlinebookstore.dto.order.CartItemUpdateRequestDto;
import com.example.onlinebookstore.dto.order.CreateCartItemRequestDto;
import com.example.onlinebookstore.dto.order.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addToCart(CreateCartItemRequestDto request);

    CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto request);

    void deleteCartItem(Long cartItemId);
}
