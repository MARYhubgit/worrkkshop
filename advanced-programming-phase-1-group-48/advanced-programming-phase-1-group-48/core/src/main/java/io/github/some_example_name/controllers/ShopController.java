package io.github.some_example_name.controllers;


import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.Animal.AnimalCreator;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Tools.FishingPole;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.building.BuildingFactory;
import io.github.some_example_name.model.building.Buildings;
import io.github.some_example_name.model.enums.BackpackType;
import io.github.some_example_name.model.game.Position;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;
import io.github.some_example_name.model.shop.AnimalProduct;
import io.github.some_example_name.model.shop.BuildingProduct;
import io.github.some_example_name.model.shop.Product;
import io.github.some_example_name.model.shop.Shop;

import java.util.Map;

public class ShopController {

    public static String showAllProducts(Shop shop) {
        StringBuilder sb = new StringBuilder();
        for (Product p : shop.getAllProducts()) {
            sb.append(p.getName()).append(" - ").append(p.getPrice()).append("g").append("\n");
        }
        return sb.toString();
    }

    public static String showAvailableProducts(Shop shop) {
        StringBuilder sb = new StringBuilder();
        for (Product p : shop.getAvailableProducts()) {
            sb.append(p.getName())
                .append(" - ").append(p.getPrice()).append("g")
                .append(" (Remaining: ").append(p.getRemainingToday()).append(")")
                .append("\n");
        }
        return sb.toString();
    }

    public static String handlePurchase(String[] parts, Shop shop, Player player) {
        if (parts.length < 2) return "Invalid purchase";

        String name = parts[1];
        int count = 1;

        if (parts.length >= 4 && parts[2].equals("-n")) {
            try {
                count = Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                return "Invalid purchase";
            }
        }

        Product p = shop.getProduct(name);
        if (p == null) return "\"Product not found.\"";
        if (!p.isAvailable() || p.getRemainingToday() < count) return "Insufficient stock.";
        if (player.getMoney() < p.getPrice() * count) return "You don't have enough money.";

        for (Map.Entry<String, Integer> req : p.getRequiredIngredients().entrySet()) {
            if (!player.getInventory().hasItem(req.getKey(), req.getValue())) {
                return "You need " + req.getValue() + " " + req.getKey() + " to purchase this product.";
            }
        }

        player.decreaseMoney(p.getPrice() * count);
        p.purchase(count);
        for (Map.Entry<String, Integer> req : p.getRequiredIngredients().entrySet()) {
            player.getInventory().removeItem(req.getKey(), req.getValue());
        }

        for (int i = 0; i < count; i++) {
            player.getInventory().addItem(ItemFactory.createItem(p.getName(), player.getInventory()));
        }
        return count + " unit(s) of " + p.getName() + " purchased.";
    }

    public static String cheatAddMoney(int amount, Player player) {

        player.addMoney(amount);
        return "Cheat applied. $" + amount + " added.";

    }

    public static String handleBuild(String[] parts, Shop shop, Player player) {
        if (parts.length < 5 || !parts[3].equals("-l")) return "Invalid build command.";

        String buildingName = parts[2];
        String[] location = parts[4].split(",");
        if (location.length != 2) return "Invalid location format.";

        int x, y;
        try {
            x = Integer.parseInt(location[0].trim());
            y = Integer.parseInt(location[1].trim());
        } catch (NumberFormatException e) {
            return "Invalid coordinates.";
        }

        Product p = shop.getProduct(buildingName);
        if (!(p instanceof BuildingProduct)) return "This product is not a building.";
        BuildingProduct bp = (BuildingProduct) p;

        if (!bp.isAvailable() || bp.getRemainingToday() < 1) return "Insufficient stock.";
        if (player.getMoney() < bp.getPrice()) return "You don't have enough money.";

        for (Map.Entry<String, Integer> req : bp.getRequiredIngredients().entrySet()) {
            if (!player.getInventory().hasItem(req.getKey(), req.getValue())) {
                return "You need " + req.getValue() + " " + req.getKey() + " to build this.";
            }
        }
// زهراااااااااااااااااااااااااااااااااا
        // بررسی محل ساخت
//        if (!player.getFarm().canPlaceBuilding(x, y, bp.getWidth(), bp.getHeight())) {
//            return "Invalid location or space is not empty.";
//        }

        // ساخت موفق
        player.decreaseMoney(bp.getPrice());
        bp.purchase(1);
        for (Map.Entry<String, Integer> req : bp.getRequiredIngredients().entrySet()) {
            player.getInventory().removeItem(req.getKey(), req.getValue());
        }

        // زهرااااااااااااااااااااااا
//        player.getFarm().placeBuilding(x, y, bp.getBuildingType());
        Buildings building = BuildingFactory.create(buildingName, new Position(x, y));
        player.addBuilding(building);

        return bp.getName() + " built successfully at (" + x + "," + y + ").";
    }

    public static String handleBuyAnimal(String[] parts, Shop shop, Player player) {
        String kindName = parts[3];
        String uniqueName = parts[5];

        Product prod = shop.getProduct(kindName);
        if (!(prod instanceof AnimalProduct)) {
            return "We don't sell animal type '" + kindName + "' here.";
        }
        AnimalProduct ap = (AnimalProduct) prod;


        int price = ap.getPrice();
        if (player.getMoney() < price) {
            return "Not enough money. You need " + price + " but have " + player.getMoney() + ".";
        }

        if (player.getBroughtAnimal().containsKey(uniqueName)) {
            return "You already have an animal named '" + uniqueName + "'. Choose another name.";
        }


        player.decreaseMoney(price);
        ap.purchase(1);

        try {

            Animal animal = AnimalCreator.createAnimal(kindName, uniqueName);
            return "Successfully bought a "
                + kindName
                + " named '" + uniqueName + "'.";
        } catch (IllegalStateException ex) {
            player.addMoney(price);
            ap.refundPurchase(1);
            return ex.getMessage();
        }
    }

    public static String upgradeTools(String[] parts, Shop shop, Player player) {
        if (parts.length < 3) {
            return "Invalid command. Please specify which tool to upgrade. ";
        }

        String toolName = parts[2];
        BackpackType current = player.getBackpack();

        if (toolName.contains("Pack")) {
            if(!shop.getOwnerName().equalsIgnoreCase("GeneralStore")) {
                return "You are not in a GeneralStore.";
            }
            Product p = shop.getProduct(toolName);
            if (p == null) return "\"Product not found.\"";
            if (!p.isAvailable() || p.getRemainingToday() < 1) return "Insufficient stock.";
            if (player.getMoney() < p.getPrice() * 1) return "You don't have enough money.";

            if (toolName.equalsIgnoreCase("Large_Pack")) {

                player.decreaseMoney(p.getPrice());
                p.purchase(1);
                player.upgradeBackpack(BackpackType.MEDIUM);
                return "Backpack upgraded to MEDIUM! Now you have 24 slots.";
            }
            if (toolName.equalsIgnoreCase("Deluxe_Pack")) {

                player.decreaseMoney(p.getPrice());
                p.purchase(1);
                player.upgradeBackpack(BackpackType.DELUXE);
                return "Backpack upgraded to DELUXE! Unlimited capacity unlocked.";
            }
            return "your backpack is already upgraded.";
        }
        else if (toolName.endsWith("Rod") || toolName.contains("Rod")) {
            if (!shop.getOwnerName().equals("FishShop")) {
                return "Fishing rod upgrades are only available at Willy's Fish Shop.";
            }
            System.out.println(toolName);
            Product p = shop.getProduct(toolName);
            if (p == null) return "Product not found.";
            if (!p.isAvailable() || p.getRemainingToday() < 1) return "Insufficient stock.";
            if (player.getMoney() < p.getPrice()) return "You don't have enough money.";

            player.decreaseMoney(p.getPrice());
            p.purchase(1);

            Item equipped = player.getEquippedItem();
            if (!(equipped instanceof FishingPole)) {
                return "You have no fishing rod equipped!";
            }
            FishingPole currentRod = (FishingPole) equipped;
            Tool nextRod = currentRod.upgrade();
            if (nextRod == null) {
                return "This fishing rod cannot be upgraded further.";
            }
            player.setEquippedItem(nextRod);
            return "Fishing rod upgraded to " + nextRod.getName() + "!";
        }
        else {
            if(!shop.getOwnerName().equalsIgnoreCase("Blacksmith")) {
                return "You are not in a blacksmith.";
            }
            Item equipped = player.getEquippedItem();
            if (equipped == null || !(equipped instanceof Tool)) {
                return ("You have no tool equipped!");
            } else {
                Tool currentTool = (Tool) equipped;
                Tool nextTool = currentTool.upgrade();
                if (nextTool == null) {
                    return "This tool cannot be upgraded further.";
                }

                String upgradeProductName = parts[2];
                String upgradeShopProductName = null;
                if (upgradeProductName.contains("basic")) {
                    upgradeShopProductName = "Copper_Tool";
                } else if (upgradeProductName.contains("copper")) {
                    upgradeShopProductName = "Steel_Tool";
                } else if (upgradeProductName.contains("iron")) {
                    upgradeShopProductName = "Gold_Tool";
                } else if (upgradeProductName.contains("gold")) {
                    upgradeShopProductName = "Iridium_Tool";
                } else if (upgradeProductName.contains("iridium")) {
                    return "Your tool is already at maximum upgrade (Iridium).";
                } else {
                    return "Unknown tool type for upgrade.";
                }

                Product upgradeProduct = shop.getProduct(upgradeShopProductName);
                if (upgradeProduct == null) return "Upgrade product not found in this shop.";



                if (player.getMoney() < upgradeProduct.getPrice()) {
                    return "You don't have enough money to upgrade.";
                }

                player.decreaseMoney(upgradeProduct.getPrice());
                upgradeProduct.purchase(1);


                player.setEquippedItem(nextTool);
                return "Tool upgraded to " + nextTool.getName() + "!";

            }
        }
    }
}
