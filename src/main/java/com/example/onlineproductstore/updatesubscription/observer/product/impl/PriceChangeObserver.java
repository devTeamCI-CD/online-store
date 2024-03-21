package com.example.onlineproductstore.updatesubscription.observer.product.impl;

import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.updatesubscription.observer.product.ProductObserver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeObserver implements ProductObserver {
    private static final String PATH = "src/main/resources/price_changes.txt";

    @Override
    public void update(Product product) {
        Path logPath = Path.of(PATH);
        String logMessage = "Price of product "
                + product.getName()
                + " has changed to "
                + product.getPrice();

        try {
            Files.writeString(logPath, logMessage + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file", e);
        }
    }
}
