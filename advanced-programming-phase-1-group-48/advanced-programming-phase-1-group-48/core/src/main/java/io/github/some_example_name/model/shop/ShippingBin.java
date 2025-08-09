package io.github.some_example_name.model.shop;

import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.ItemQuality;
import io.github.some_example_name.model.enums.SimpleItemInfo;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;
import io.github.some_example_name.views.Graphic.GameHUD;

import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private final Map<String, Integer> items = new HashMap<>();
    private final Game game;

    public ShippingBin(Game game) {
        this.game = game;
    }

    public Result sellItem(Player player, String itemName, int quantity) {
        items.put(itemName.toLowerCase(), items.getOrDefault(itemName, 0) + quantity);

        return Result.success("Added " + quantity + "x " + itemName + " to shipping bin.");
    }

    public void processShippingBin(Player player) {
        int totalEarnings = 0;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String itemName = entry.getKey();
            int qty = entry.getValue();

            SimpleItemInfo info = SimpleItemInfo.fromName(itemName); // فرض: چنین متدی داری
            if (info == null) continue;

            int basePrice = info.getSellPrice();
            ItemQuality quality = ItemQuality.fromItemName(itemName);
            double multiplier = getQualityMultiplier(quality);

            totalEarnings += (int)(basePrice * multiplier * qty);
        }

        GameHUD.showMessage.accept(" You earned " + totalEarnings + " gold from shipped items!");
        player.addMoney(totalEarnings);
        items.clear();
    }


    private double getQualityMultiplier(ItemQuality quality) {
        switch (quality) {
            case REGULAR: return 1.0;
            case SILVER: return 1.25;
            case GOLD: return 1.5;
            case IRIDIUM: return 2.0;
            default: return 1.0;
        }
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}
