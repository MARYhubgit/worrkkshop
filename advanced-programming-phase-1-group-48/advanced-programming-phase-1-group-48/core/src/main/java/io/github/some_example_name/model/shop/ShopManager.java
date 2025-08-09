package io.github.some_example_name.model.shop;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {
    private Map<ShopType, Shop> shops = new HashMap<>();

    public ShopManager() {
        initializeShops();
    }


    private void initializeShops() {
        for (ShopType type : ShopType.values()) {
            Shop shop = new Shop(
                    type,
                    type.getOwnerName(),
                    type.getOpenTime(),
                    type.getCloseTime()
            );

            for (Product defaultProduct : type.getDefaultProducts()) {
                shop.addProduct(new Product(defaultProduct));
            }

            shops.put(type, shop);
        }
    }


    public void registerShop(Shop shop) {
        shops.put(shop.getType(), shop);
    }

    public Shop getShop(ShopType type) {
        return shops.get(type);
    }

    public void resetDailyStock() {
        for (Shop shop : shops.values()) {
            shop.getAllProducts().forEach(Product::resetDaily);
        }
    }
}

