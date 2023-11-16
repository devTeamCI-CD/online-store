package com.example.onlineproductstore.dto.product;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDtoWithoutCategoryIds {
    private Long id;
    private String name;
    private String provider;
    private BigDecimal price;
    private String description;
    private String image;
}
