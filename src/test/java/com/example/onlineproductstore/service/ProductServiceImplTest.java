package com.example.onlineproductstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.onlineproductstore.dto.product.CreateProductRequestDto;
import com.example.onlineproductstore.dto.product.ProductDto;
import com.example.onlineproductstore.dto.product.ProductSearchParameters;
import com.example.onlineproductstore.mapper.ProductMapper;
import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.repository.SpecificationBuilder;
import com.example.onlineproductstore.repository.product.ProductRepository;
import com.example.onlineproductstore.service.impl.ProductServiceImpl;
import com.example.onlineproductstore.updatesubscription.observable.ProductObservable;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private SpecificationBuilder<Product, ProductSearchParameters> productSpecificationBuilder;

    @Mock
    private ProductObservable productObservable;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {
        // Given
        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto();
        Product product = new Product();
        product.setId(1L);
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);

        when(productMapper.toModel(createProductRequestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        // When
        ProductDto result = productService.save(createProductRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAllProducts() {
        // Given
        Pageable pageable = Pageable.unpaged();
        Product product = new Product();
        product.setId(1L);
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);

        when(productRepository.findAllWithCategories(pageable))
                .thenReturn(Collections.singletonList(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        // When
        List<ProductDto> result = productService.getAll(pageable);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    // Similar tests can be written for other methods in ProductServiceImpl
}
