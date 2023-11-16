package com.example.onlineproductstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull
    private Long productId;
    @Positive
    private int quantity;
}
