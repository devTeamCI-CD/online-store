package com.example.onlineproductstore.service.impl;

import com.example.onlineproductstore.dto.product.CreateProductRequestDto;
import com.example.onlineproductstore.dto.product.ProductDto;
import com.example.onlineproductstore.dto.product.ProductDtoWithoutCategoryIds;
import com.example.onlineproductstore.dto.product.ProductSearchParameters;
import com.example.onlineproductstore.exception.EntityNotFoundException;
import com.example.onlineproductstore.mapper.ProductMapper;
import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.repository.SpecificationBuilder;
import com.example.onlineproductstore.repository.product.ProductRepository;
import com.example.onlineproductstore.service.ProductService;
import com.example.onlineproductstore.updatesubscription.observable.ProductObservable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SpecificationBuilder<Product,
            ProductSearchParameters> productSpecificationBuilder;
    private final ProductObservable productObservable;

    @Override
    public ProductDto save(CreateProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAllWithCategories(pageable)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        Product productToFind = productRepository.findByIdWithCategories(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find Product with id: " + id));
        return productMapper.toDto(productToFind);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto update(Long id, CreateProductRequestDto productRequestDto) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find Product with id: " + id);
        }
        Product productForUpdate = productMapper.toModel(productRequestDto);
        productForUpdate.setId(id);
        productObservable.notifyObservers(productForUpdate);
        return productMapper.toDto(productRepository.save(productForUpdate));
    }

    @Override
    public List<ProductDtoWithoutCategoryIds> search(ProductSearchParameters searchParameters,
                                                     Pageable pageable) {
        Specification<Product> productSpecification =
                productSpecificationBuilder.build(searchParameters);
        return productRepository.findAll(productSpecification, pageable).stream()
                .map(productMapper::toDtoWithoutCategories)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDtoWithoutCategoryIds> getByCategoryId(Long id, Pageable pageable) {
        return productRepository.findAllByCategoryId(id, pageable)
                .stream()
                .map(productMapper::toDtoWithoutCategories)
                .toList();
    }
}
