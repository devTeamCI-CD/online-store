package com.example.onlineproductstore.repository.product;

import com.example.onlineproductstore.dto.product.ProductSearchParameters;
import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.repository.SpecificationBuilder;
import com.example.onlineproductstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductSpecificationBuilder
        implements SpecificationBuilder<Product, ProductSearchParameters> {
    private final SpecificationProviderManager<Product> productSpecificationProviderManager;

    @Override
    public Specification<Product> build(ProductSearchParameters searchParams) {
        Specification<Product> specification = Specification.where(null);
        if (isParameterPresent(searchParams.provider())) {
            specification = addSpecification(specification, "provider", searchParams.provider());
        }
        if (isParameterPresent(searchParams.name())) {
            specification = addSpecification(specification, "name", searchParams.name());
        }
        return specification;
    }

    private boolean isParameterPresent(String[] param) {
        return param != null && param.length > 0;
    }

    private Specification<Product> addSpecification(Specification<Product> specification,
                                                 String key, String[] params) {
        return specification.and(productSpecificationProviderManager
                .getSpecificationProvider(key).getSpecification(params));
    }
}
