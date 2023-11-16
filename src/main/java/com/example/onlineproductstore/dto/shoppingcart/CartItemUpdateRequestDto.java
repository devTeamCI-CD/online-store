package com.example.onlineproductstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemUpdateRequestDto {
    @Positive
    private int quantity;
}
