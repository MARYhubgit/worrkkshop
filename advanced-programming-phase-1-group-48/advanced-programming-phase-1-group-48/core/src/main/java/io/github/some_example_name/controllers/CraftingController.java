package io.github.some_example_name.controllers;

import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.crafting.CraftingRecipe;
import io.github.some_example_name.model.enums.SimpleItemInfo;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.SimpleItem;
import io.github.some_example_name.views.Graphic.GameHUD;

public class CraftingController {
    public static void attemptCraft(Player player, CraftingRecipe recipe) {
        Inventory inv = player.getInventory();

        // ۱. بررسی یادگیری دستور ساخت
        if (!recipe.isUnlockedFor(player)) {
            GameHUD.showMessage.accept("❌ You haven't learned this recipe yet.");
            return;
        }

        // ۲. بررسی موجود بودن مواد اولیه
        for (var entry : recipe.getIngredients().entrySet()) {
            String itemName = entry.getKey();
            int requiredAmount = entry.getValue();

            if (!inv.hasItem(itemName, requiredAmount)) {
                GameHUD.showMessage.accept("❌ Not enough " + itemName + "!");
                return;
            }
        }

        // ۳. حذف مواد اولیه
        for (var entry : recipe.getIngredients().entrySet()) {
            inv.removeItem(entry.getKey(), entry.getValue());
        }

        // ۴. ساخت آیتم بر اساس SimpleItemInfo
        String resultName = recipe.getItemName();
        Item craftedItem = null;

        for (SimpleItemInfo info : SimpleItemInfo.values()) {
            if (info.getName().equalsIgnoreCase(resultName)) {
                craftedItem = new SimpleItem(
                    info.getName(),
                    info.getDisplayChar(),
                    info.getSellPrice(),
                    info.getEnergy(),
                    info.getType()
                );
                break;
            }
        }

        if (craftedItem == null) {
            GameHUD.showMessage.accept("❌ Crafted item \"" + resultName + "\" not found in SimpleItemInfo.");
            return;
        }

        inv.addItem(craftedItem);  // استفاده از متدی که Item می‌گیرد
        GameHUD.showMessage.accept("✅ Crafted: " + resultName);
    }
}
