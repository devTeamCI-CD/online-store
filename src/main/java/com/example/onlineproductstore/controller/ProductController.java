package com.example.onlineproductstore.controller;

import com.example.onlineproductstore.dto.product.CreateProductRequestDto;
import com.example.onlineproductstore.dto.product.ProductDto;
import com.example.onlineproductstore.dto.product.ProductDtoWithoutCategoryIds;
import com.example.onlineproductstore.dto.product.ProductSearchParameters;
import com.example.onlineproductstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product API", description = "Controller for managing products in DB")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products",
            description = "Retrieve some set of products using pagination")
    @GetMapping
    public List<ProductDto> getAll(Pageable pageable) {
        return productService.getAll(pageable);
    }

    @Operation(summary = "Get product by ID",
            description = "Retrieve a product by its id")
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @Operation(summary = "Add a new product",
            description = "Perceive a new product to the database")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto create(@RequestBody @Valid CreateProductRequestDto productRequestDto) {
        return productService.save(productRequestDto);
    }

    @Operation(summary = "Update product by ID",
            description = "Modify an existing product using its id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto update(@PathVariable Long id,
                             @RequestBody @Valid CreateProductRequestDto productRequestDto) {
        return productService.update(id, productRequestDto);
    }

    @Operation(summary = "Delete product by ID",
            description = "Soft delete a product from the DB by its id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(summary = "Search products",
            description = "Search for any products based on specific params using pagination")
    @GetMapping("/search")
    public List<ProductDtoWithoutCategoryIds> search(ProductSearchParameters searchParameters,
                                                     Pageable pageable) {
        return productService.search(searchParameters, pageable);
    }
}
