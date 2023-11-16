package com.example.onlineproductstore.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class CreateProductRequestDto {
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    @Size(max = 255)
    private String provider;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Size(min = 1, max = 255)
    private String description;
    @NotEmpty
    @NotNull
    private String image;
    @NotEmpty
    private Set<Long> categoryIds;
}
