package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.category.CategoryResponseDto;
import com.example.onlineproductstore.dto.category.CreateCategoryRequestDto;
import com.example.onlineproductstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toResponseDto(Category category);

    Category toEntity(CreateCategoryRequestDto categoryDto);
}
