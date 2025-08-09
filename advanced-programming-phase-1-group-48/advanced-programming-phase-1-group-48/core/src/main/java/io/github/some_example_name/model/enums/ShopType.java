package io.github.some_example_name.model.enums;


import io.github.some_example_name.model.enums.Building;
import io.github.some_example_name.model.enums.Season;
import io.github.some_example_name.model.shop.AnimalProduct;
import io.github.some_example_name.model.shop.BuildingProduct;
import io.github.some_example_name.model.shop.Product;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum ShopType {
    BLACKSMITH("Blacksmith", LocalTime.of(9, 0), LocalTime.of(16, 0),
        List.of(
            new Product("Copper Ore", 75, Integer.MAX_VALUE),
            new Product("Iron Ore", 150, Integer.MAX_VALUE),
            new Product("Gold Ore", 400, Integer.MAX_VALUE),
            new Product("Coal", 150, Integer.MAX_VALUE),

            new Product("Copper Tool", 2000, 1, Map.of("Copper Bar", 5)),
            new Product("Steel Tool", 5000, 1, Map.of("Iron Bar", 5)),
            new Product("Gold Tool", 10000, 1, Map.of("Gold Bar", 5)),
            new Product("Iridium Tool", 25000, 1, Map.of("Iridium Bar", 5)),

            new Product("Copper Trash Can", 1000, 1, Map.of("Copper Bar", 5)),
            new Product("Steel Trash Can", 2500, 1, Map.of("Iron Bar", 5)),
            new Product("Gold Trash Can", 5000, 1, Map.of("Gold Bar", 5)),
            new Product("Iridium Trash Can", 12500, 1, Map.of("Iridium Bar", 5))

        )),

    JOJA_MART("JojaMart", LocalTime.of(9, 0), LocalTime.of(23, 0),
        List.of(
            // General Goods
            new Product("Parsnip Seed", 20, 20),
            new Product("Fertilizer", 100, 10),
            new Product("Hay", 50, Integer.MAX_VALUE),
            new Product("Milk Pail", 1000, 1),
            new Product("Shears", 1000, 1),

            // Farm Animals
            new AnimalProduct("Chicken", 800, 2, Building.COOP),
            new AnimalProduct("Cow", 1500, 2, Building.BARN),
            new AnimalProduct("Goat", 4000, 2, Building.BIG_BARN),
            new AnimalProduct("Duck", 1200, 2, Building.BIG_COOP),
            new AnimalProduct("Sheep", 8000, 2, Building.DELUXE_BARN),
            new AnimalProduct("Rabbit", 8000, 2, Building.DELUXE_COOP),
            new AnimalProduct("Dinosaur", 14000, 2, Building.BIG_COOP),
            new AnimalProduct("Pig", 16000, 2, Building.DELUXE_BARN)

        )),
    PIERRE_STORE("GeneralStore", LocalTime.of(9, 0), LocalTime.of(17, 0),
        List.of(
            // کالاهای عمومی بدون وابستگی به فصل
            new Product("Rice", 200, Integer.MAX_VALUE),
            new Product("Wheat Flour", 100, Integer.MAX_VALUE),
            new Product("Sugar", 100, Integer.MAX_VALUE),
            new Product("Oil", 200, Integer.MAX_VALUE),
            new Product("Vinegar", 200, Integer.MAX_VALUE),
            new Product("Deluxe Retaining Soil", 150, Integer.MAX_VALUE),
            new Product("Basic Retaining Soil", 100, Integer.MAX_VALUE),

            // گل‌ها و درخت‌ها
            new Product("Apple Sapling", 4000, Integer.MAX_VALUE, Set.of(Season.FALL), Map.of(Season.FALL, 4000)),
            new Product("Apricot Sapling", 2000, Integer.MAX_VALUE, Set.of(Season.SPRING), Map.of(Season.SPRING, 2000)),
            new Product("Cherry Sapling", 3400, Integer.MAX_VALUE, Set.of(Season.SPRING), Map.of(Season.SPRING, 3400)),
            new Product("Orange Sapling", 4000, Integer.MAX_VALUE, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 4000)),
            new Product("Peach Sapling", 6000, Integer.MAX_VALUE, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 6000)),
            new Product("Pomegranate Sapling", 6000, Integer.MAX_VALUE, Set.of(Season.FALL), Map.of(Season.FALL, 6000)),

            // بذرهای گیاهان بر اساس فصل
            new Product("Parsnip Seeds", 20, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 20)),
            new Product("Bean Starter", 60, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 60)),
            new Product("Cauliflower Seeds", 80, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 80)),
            new Product("Potato Seeds", 50, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 50)),
            new Product("Tulip Bulb", 20, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 20)),
            new Product("Kale Seeds", 70, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 70)),
            new Product("Jazz Seeds", 30, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 30)),
            new Product("Garlic Seeds", 40, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 40)),
            new Product("Rice Shoot", 40, 5, Set.of(Season.SPRING), Map.of(Season.SPRING, 40)),

            new Product("Melon Seeds", 80, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 80)),
            new Product("Tomato Seeds", 50, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 50)),
            new Product("Blueberry Seeds", 80, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 80)),
            new Product("Pepper Seeds", 40, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 40)),
            new Product("Wheat Seeds", 10, 5, Set.of(Season.SUMMER, Season.FALL), Map.of(Season.SUMMER, 10, Season.FALL, 10)),
            new Product("Radish Seeds", 40, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 40)),
            new Product("Poppy Seeds", 100, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 100)),
            new Product("Spangle Seeds", 50, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 50)),
            new Product("Hops Starter", 60, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 60)),
            new Product("Corn Seeds", 150, 5, Set.of(Season.SUMMER, Season.FALL), Map.of(Season.SUMMER, 150, Season.FALL, 150)),
            new Product("Sunflower Seeds", 200, 5, Set.of(Season.SUMMER, Season.FALL), Map.of(Season.SUMMER, 200, Season.FALL, 200)),
            new Product("Red Cabbage Seeds", 100, 5, Set.of(Season.SUMMER), Map.of(Season.SUMMER, 100)),

            new Product("Eggplant Seeds", 20, 5, Set.of(Season.FALL), Map.of(Season.FALL, 20)),
            new Product("Pumpkin Seeds", 100, 5, Set.of(Season.FALL), Map.of(Season.FALL, 100)),
            new Product("Bok Choy Seeds", 50, 5, Set.of(Season.FALL), Map.of(Season.FALL, 50)),
            new Product("Yam Seeds", 60, 5, Set.of(Season.FALL), Map.of(Season.FALL, 60)),
            new Product("Cranberry Seeds", 240, 5, Set.of(Season.FALL), Map.of(Season.FALL, 240)),
            new Product("Fairy Seeds", 200, 5, Set.of(Season.FALL), Map.of(Season.FALL, 200)),
            new Product("Amaranth Seeds", 70, 5, Set.of(Season.FALL), Map.of(Season.FALL, 70)),
            new Product("Grape Starter", 60, 5, Set.of(Season.FALL), Map.of(Season.FALL, 60)),
            new Product("Artichoke Seeds", 30, 5, Set.of(Season.FALL), Map.of(Season.FALL, 30))
        )),
    CARPENTER("Carpenter", LocalTime.of(9, 0), LocalTime.of(20, 0),
        List.of(
            // مواد اولیه
            new Product("Wood", 10, Integer.MAX_VALUE),
            new Product("Stone", 20, Integer.MAX_VALUE),

            new BuildingProduct("Coop", 4000, 1, Building.COOP,
                Map.of("Wood", 300, "Stone", 100), "6x3"),

            new BuildingProduct("Big Coop", 10000, 1, Building.BIG_COOP,
                Map.of("Wood", 400, "Stone", 150), "6x3"),

            new BuildingProduct("Deluxe Coop", 20000, 1, Building.DELUXE_COOP,
                Map.of("Wood", 500, "Stone", 200), "6x3"),

            new BuildingProduct("Barn", 6000, 1, Building.BARN,
                Map.of("Wood", 350, "Stone", 150), "7x4"),

            new BuildingProduct("Big Barn", 12000, 1, Building.BIG_BARN,
                Map.of("Wood", 450, "Stone", 200), "7x4"),

            new BuildingProduct("Deluxe Barn", 25000, 1, Building.DELUXE_BARN,
                Map.of("Wood", 550, "Stone", 300), "7x4"),

            new BuildingProduct("Well", 1000, 1, Building.WELL,
                Map.of("Stone", 75), "3x3"),

            new BuildingProduct("Shipping Bin", 250, Integer.MAX_VALUE, Building.SHIPPING_BIN,
                Map.of("Wood", 150), "1x1")
        )),
    FISH_SHOP("FishShop", LocalTime.of(9, 0), LocalTime.of(17, 0),
        List.of(
            new Product("Fish Smoker (Recipe)", 10_000, 1),
            new Product("Trout Soup", 250, 1),
            new Product("Bamboo Pole", 500, 1),
            new Product("Training Rod", 25, 1),
            new Product("Fiberglass Rod", 1800, 1, 2),
            new Product("Iridium Rod", 7500, 1, 4)
        )),
    MARNIE_RANCH("Ranch", LocalTime.of(9, 0), LocalTime.of(16, 0),
        List.of(
            // ابزارها
            new Product("Hay", 50, Integer.MAX_VALUE),
            new Product("Milk Pail", 1000, 1),
            new Product("Shears", 1000, 1),

            // حیوانات
            new AnimalProduct("Chicken", 800, 2, Building.COOP),
            new AnimalProduct("Cow", 1500, 2, Building.BARN),
            new AnimalProduct("Goat", 4000, 2, Building.BIG_BARN),
            new AnimalProduct("Duck", 1200, 2, Building.BIG_COOP),
            new AnimalProduct("Sheep", 8000, 2, Building.DELUXE_BARN),
            new AnimalProduct("Rabbit", 8000, 2, Building.DELUXE_COOP),
            new AnimalProduct("Dinosaur", 14000, 2, Building.BIG_COOP),
            new AnimalProduct("Pig", 16000, 2, Building.DELUXE_BARN)
        ) ),
    STAR_DROP_SALOON("Saloon", LocalTime.of(12, 0), LocalTime.of(0, 0),
        List.of(
            new Product("Beer", 400, Integer.MAX_VALUE),
            new Product("Salad", 220, Integer.MAX_VALUE),
            new Product("Bread", 120, Integer.MAX_VALUE),
            new Product("Spaghetti", 240, Integer.MAX_VALUE),
            new Product("Pizza", 600, Integer.MAX_VALUE),
            new Product("Coffee", 300, Integer.MAX_VALUE),
            //رسپی
            new Product("Hashbrowns Recipe", 50, 1),
            new Product("Omelet Recipe", 100, 1),
            new Product("Pancakes Recipe", 100, 1),
            new Product("Bread Recipe", 100, 1),
            new Product("Tortilla Recipe", 100, 1),
            new Product("Pizza Recipe", 150, 1),
            new Product("Maki Roll Recipe", 300, 1),
            new Product("Triple Shot Espresso Recipe", 5000, 1),
            new Product("Cookie Recipe", 300, 1)
        ));

    private final String ownerName;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final List<Product> defaultProducts;

    ShopType(String ownerName, LocalTime open, LocalTime close, List<Product> defaultProducts) {
        this.ownerName = ownerName;
        this.openTime = open;
        this.closeTime = close;
        this.defaultProducts = defaultProducts;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }
    public List<Product> getDefaultProducts() {
        return defaultProducts;
    }

}
