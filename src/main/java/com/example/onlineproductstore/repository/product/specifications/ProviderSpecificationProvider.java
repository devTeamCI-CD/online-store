package com.example.onlineproductstore.repository.product.specifications;

import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProviderSpecificationProvider implements SpecificationProvider<Product> {
    @Override
    public String getKey() {
        return "provider";
    }

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("provider").in(Arrays.stream(params).toArray());
    }
}
