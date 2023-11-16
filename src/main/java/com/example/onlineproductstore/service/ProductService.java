package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.product.CreateProductRequestDto;
import com.example.onlineproductstore.dto.product.ProductDto;
import com.example.onlineproductstore.dto.product.ProductDtoWithoutCategoryIds;
import com.example.onlineproductstore.dto.product.ProductSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto save(CreateProductRequestDto productRequestDto);

    List<ProductDto> getAll(Pageable pageable);

    ProductDto getById(Long id);

    void deleteById(Long id);

    ProductDto update(Long id, CreateProductRequestDto productRequestDto);

    List<ProductDtoWithoutCategoryIds> search(ProductSearchParameters searchParameters,
                                              Pageable pageable);

    List<ProductDtoWithoutCategoryIds> getByCategoryId(Long id, Pageable pageable);
}
