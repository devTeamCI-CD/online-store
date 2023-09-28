package com.example.onlinebookstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemUpdateRequestDto {
    @Positive
    private int quantity;
}
