package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.product.CreateProductRequestDto;
import com.example.onlineproductstore.dto.product.ProductDto;
import com.example.onlineproductstore.dto.product.ProductDtoWithoutCategoryIds;
import com.example.onlineproductstore.model.Category;
import com.example.onlineproductstore.model.Product;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    @Mapping(target = "categoryIds", ignore = true)
    ProductDto toDto(Product product);

    @AfterMapping
    default void setCategoryIds(@MappingTarget ProductDto productDto, Product product) {
        if (product.getCategories() != null) {
            productDto.setCategoryIds(product.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }

    @Mapping(target = "categories", ignore = true)
    Product toEntity(CreateProductRequestDto productRequestDto);

    default Set<Category> mapCategorySet(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    default Set<Long> mapCategoryToIds(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @Named("productFromId")
    default Product productFromId(Long id) {
        return Optional
                .ofNullable(id)
                .map(Product::new)
                .orElse(null);
    }

    Product toModel(CreateProductRequestDto requestDto);

    ProductDtoWithoutCategoryIds toDtoWithoutCategories(Product product);
}
