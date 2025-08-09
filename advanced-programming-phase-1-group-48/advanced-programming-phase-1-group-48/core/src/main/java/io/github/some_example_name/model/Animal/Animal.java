package io.github.some_example_name.model.Animal;



import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.items.ItemFactory;

import java.util.ArrayList;
import java.util.List;

import static io.github.some_example_name.model.Animal.AnimalProduct.*;


public class Animal {
    private String name;
    private static int friendship;
    private boolean isCaressed;
    private int lastPettedTotalDays;
    private boolean isFedToday;

    public static boolean isHasProducedToday() {
        return hasProducedToday;
    }

    private static boolean hasProducedToday;
    private AnimalKind kind;

    public static AnimalProduct getTodaysProduct() {
        return todaysProduct;
    }

    private static AnimalProduct todaysProduct;
    private boolean isOutsideAtNight;
    private static int count = 0;
    private static double productQuality;
    private boolean productCollected;


    public Animal(String name, AnimalKind kind) {
        this.kind = kind;
        this.name = name;
        this.friendship = 0;
        this.lastPettedTotalDays = -1;

    }
    public void collectProduce() {
        collectProducts();
    }


    public void setTodaysProductQuality(double quality) {
        this.productQuality = quality;
    }

    public double getTodaysProductQuality() {
        return productQuality;
    }

    public AnimalKind getKind() {
        return this.kind;
    }

    public void setCount(int count) {
        count += count;
    }

    public String getName() {
        return name;
    }

    public static int getFriendship() {
        return friendship;
    }

    public static void setFriendship(int friendship) {
        Animal.friendship = friendship;
    }

    public boolean canProduceSecondaryProduct() {
        if (friendship < 100) return false;
        double random = Math.random(); // [0,1)
        double chanceValue = (random * 150 + friendship) / 1500.0;
        return random < chanceValue;
    }


//    public void produceToday() {
//        // Determine product
//        //List<AnimalProduct> products = new ArrayList<>(kind.getProductPrices().keySet());
//        AnimalProduct mainProduct = products.get(0);
//        if (products.size() > 1 && canProduceSecondaryProduct()) {
//            todaysProduct = products.get(1);
//        } else {
//            todaysProduct = mainProduct;
//        }
//        double R = Math.random();
//        double quality = (R * 0.5 + 0.5) * (friendship / 1000.0);
//        setTodaysProductQuality(quality);
//
//        hasProducedToday = true;
//        System.out.printf("%s produced %s with quality %.2f%n", name,
//                todaysProduct.name().toLowerCase().replace('_',' '), quality);
//
//    }

    public void pet() {
        int today = DateAndTime.getTotalDays();

        if (lastPettedTotalDays == today) {
            throw new IllegalStateException(String.format("Animal '%s' has already been petted today.", name));
        }

        friendship = Math.min(friendship + 15, 1000);
        isCaressed = true;
        lastPettedTotalDays = today;

        System.out.printf("Animal '%s' has been petted! (+15) → Friendship: %d%n", name, friendship);
    }
    public static void collectProduct(Animal animal) {}
    public void feed() {
        if (!isFedToday) {
            friendship = Math.min(friendship + 8, 1000);
            isFedToday = true;
            System.out.println(name + " has been fed.");
        } else {
            System.out.println(name + " is already fed today.");
        }
    }

    private boolean collectProducts() {
        if (todaysProduct == null || hasProducedToday == false) {
            System.out.println("No product to collect from " + name + " today.");
            return false;
        } else if (todaysProduct == SHEEP_WOOL || todaysProduct == GOAT_MILK
                || todaysProduct == LARGE_GOATMILK || todaysProduct == MILK || todaysProduct == LARGE_MILK) {
            return milkOrShear();
        } else {
            friendship = Math.min(friendship + 5, 1000);
            hasProducedToday = false;
            productCollected = true;
            System.out.println("Collected product from " + name + ".");
            return true;
        }
    }

    public boolean milkOrShear() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        if ((this.kind == AnimalKind.COW && player.getEquippedItem().equals(ItemFactory.createItem("MILK PAIL",player.getInventory()))) ||
                (this.kind == AnimalKind.SHEEP && player.getEquippedItem().equals(ItemFactory.createItem("SHEAR",player.getInventory())))){
            friendship = Math.min(friendship + 5, 1000);
            hasProducedToday = false;
            System.out.println("Collected product from " + name + ".");
            return false;
        } else {
            System.out.println("You need the correct tool to collect product from " + name + ".");
            return true;
        }
    }

    public void applyNeglectPenalty() {
        int today = DateAndTime.getTotalDays();

        if (lastPettedTotalDays != today) {
            int penalty = 0;

            if (friendship > 0) {
                penalty = (int) Math.max(0, 10 - (200.0 / friendship));
            }

            friendship = Math.max(0, friendship - penalty);

        }
    }

    public void applyHungerPenalty() {
        if (!isFedToday) {
            friendship = Math.max(0, friendship - 20);
            System.out.println(name + " was not fed today. -20 friendship.");
        }
    }

    public void applyColdNightPenalty() {
        if (isOutsideAtNight) {
            friendship = Math.max(0, friendship - 20);
            System.out.println(name + " stayed outside at night. -20 friendship.");
        }
    }


    private boolean hasProuductedToday() {
        if (count % todaysProduct.getProductTime() == 0 && !hasProducedToday) {
            hasProducedToday = true;
            collectProducts();
        }
        return false;
    }

    public double prouductPrice( Animal animal ) {
        double q = getTodaysProductQuality();
        double multiplier=0;
        if      (q <= 0.5)   multiplier = 1.0;   // Normal
        else if (q <= 0.7)   multiplier = 1.25;  // Silver
        else if (q <= 0.9)   multiplier = 1.5;   // Gold
        else                 multiplier = 2.0;

        return q*multiplier;
    }

    public void sellAnimal() {
        double factor = 0.3 + (friendship / 1000.0);
        int basePrice = kind.getPriceOfBuy();
        double sellPrice = factor * (basePrice);
        System.out.printf("Sold %s for %.2fg%n", name, sellPrice);
        // remove from player's list or other cleanup
    }


    public void endOfDayUpdate() {
        pet();
        feed();
        milkOrShear();
        applyHungerPenalty();
        applyColdNightPenalty();
        applyNeglectPenalty();
        // reset flags:
        isFedToday = false;
        isCaressed = false;
        isOutsideAtNight = false;
        hasProducedToday = false;

    }

}
