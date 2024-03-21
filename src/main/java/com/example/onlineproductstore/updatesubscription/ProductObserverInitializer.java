package com.example.onlineproductstore.updatesubscription;

import com.example.onlineproductstore.updatesubscription.observable.ProductObservable;
import com.example.onlineproductstore.updatesubscription.observer.product.impl.PriceChangeObserver;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductObserverInitializer {

    private final ProductObservable productObservable;

    @Autowired
    public ProductObserverInitializer(ProductObservable productObservable) {
        this.productObservable = productObservable;
    }

    @PostConstruct
    public void init() {
        productObservable.addObserver(new PriceChangeObserver());
    }
}
