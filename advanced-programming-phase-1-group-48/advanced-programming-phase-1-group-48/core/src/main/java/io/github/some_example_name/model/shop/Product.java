package io.github.some_example_name.model.shop;



import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.enums.Season;

import java.util.Map;
import java.util.Set;

public class Product {
    private final String name;
    private final int basePrice;
    private final int stockLimitPerDay;
    private final int requiredFishingLevel;
    private final Map<String, Integer> requiredIngredients;
    private final Set<Season> availableSeasons;
    private final Map<Season, Integer> seasonalPrices;

    private int remainingToday;

    public Product(String name, int basePrice, int stockLimitPerDay) {
        this(name, basePrice, stockLimitPerDay, 0,
            Map.of(), Set.of(), Map.of());
    }

    // 2) سازنده با آیتم‌های لازم (requiredIngredients)
    public Product(String name,
                   int basePrice,
                   int stockLimitPerDay,
                   Map<String, Integer> requiredIngredients) {
        this(name, basePrice, stockLimitPerDay, 0,
            requiredIngredients, Set.of(), Map.of());
    }

    // 3) سازنده با فیلتر فصل و قیمت فصلی
    public Product(String name,
                   int basePrice,
                   int stockLimitPerDay,
                   Set<Season> availableSeasons,
                   Map<Season, Integer> seasonalPrices) {
        this(name, basePrice, stockLimitPerDay, 0,
            Map.of(), availableSeasons, seasonalPrices);
    }

    // 4) سازنده اصلی که همهٔ فیلدها را مقداردهی می‌کند
    public Product(String name,
                   int basePrice,
                   int stockLimitPerDay,
                   int requiredFishingLevel,
                   Map<String, Integer> requiredIngredients,
                   Set<Season> availableSeasons,
                   Map<Season, Integer> seasonalPrices) {
        this.name = name;
        this.basePrice = basePrice;
        this.stockLimitPerDay = stockLimitPerDay;
        this.requiredFishingLevel = requiredFishingLevel;
        this.requiredIngredients = requiredIngredients;
        this.availableSeasons = availableSeasons;
        this.seasonalPrices = seasonalPrices;
        this.remainingToday = stockLimitPerDay;
    }

    public Product(String name,
                   int basePrice,
                   int stockLimitPerDay,
                   int requiredFishingLevel) {
        this(
            name,
            basePrice,
            stockLimitPerDay,
            requiredFishingLevel,
            Map.of(),
            Set.of(),
            Map.of()
        );
    }

    // 5) سازندهٔ کپی
    public Product(Product other) {
        this(other.name,
            other.basePrice,
            other.stockLimitPerDay,
            other.requiredFishingLevel,
            other.requiredIngredients,
            other.availableSeasons,
            other.seasonalPrices);
    }
    public int getRequiredFishingLevel() {
        return requiredFishingLevel;
    }

    public boolean isAvailable() {
        return remainingToday > 0;
    }

    public boolean purchase(int quantity) {
        if (quantity <= remainingToday) {
            remainingToday -= quantity;
            return true;
        }
        return false;
    }
    public void refundPurchase(int quantity) {
        remainingToday += quantity;
    }

    public void resetDaily() {
        remainingToday = stockLimitPerDay;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        Season season = DateAndTime.getInstance().getCurrentSeason();
        return seasonalPrices.getOrDefault(season, basePrice);
    }

    public boolean isAvailableInSeason() {
        Season season = DateAndTime.getInstance().getCurrentSeason();
        return availableSeasons == null || availableSeasons.isEmpty() || availableSeasons.contains(season);
    }

    public int getStockLimitPerDay() {
        return stockLimitPerDay;
    }

    public int getRemainingToday() {
        return remainingToday;
    }

    public Map<String, Integer> getRequiredIngredients() {
        return requiredIngredients;
    }
}
