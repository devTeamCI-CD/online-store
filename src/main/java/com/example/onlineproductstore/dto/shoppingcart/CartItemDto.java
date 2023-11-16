package com.example.onlineproductstore.dto.shoppingcart;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private String productName;
    private Long productId;
    private int quantity;
}
