package com.example.onlineproductstore.updatesubscription.observable;

import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.updatesubscription.observer.product.ProductObserver;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductObservable {
    private final List<ProductObserver> observers = new ArrayList<>();

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Product product) {
        for (ProductObserver observer : observers) {
            observer.update(product);
        }
    }
}
