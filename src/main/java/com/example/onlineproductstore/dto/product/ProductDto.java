package com.example.onlineproductstore.dto.product;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDto {
    private Long id;
    private String name;
    private String provider;
    private BigDecimal price;
    private String description;
    private String image;
    private Set<Long> categoryIds;
}
