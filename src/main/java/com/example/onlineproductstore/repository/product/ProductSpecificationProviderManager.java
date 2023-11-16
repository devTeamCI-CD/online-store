package com.example.onlineproductstore.repository.product;

import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.repository.SpecificationProvider;
import com.example.onlineproductstore.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductSpecificationProviderManager implements SpecificationProviderManager<Product> {
    private final List<SpecificationProvider<Product>> productSpecificationProviders;

    @Override
    public SpecificationProvider<Product> getSpecificationProvider(String key) {
        return productSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Can't find correct specification provider for key: " + key));
    }
}
