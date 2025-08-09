package io.github.some_example_name.model.shop;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private ShopType type;
    private String ownerName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Map<String, Product> products;

    public Shop(ShopType type, String ownerName, LocalTime open, LocalTime close) {
        this.type = type;
        this.ownerName = ownerName;
        this.openTime = open;
        this.closeTime = close;
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();

        for (Product p : getAllProducts()) {
            if (p.isAvailable() && p.isAvailableInSeason()) {
                availableProducts.add(p);
            }
        }
        return availableProducts;
    }

    public boolean isOpen(LocalTime now) {
        return now.isAfter(openTime) && now.isBefore(closeTime);
    }

    public Product getProduct(String name) {
        for (Product p : getAvailableProducts()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public ShopType getType() {
        return type;
    }
    public String getOwnerName() {
        return ownerName;
    }
}

