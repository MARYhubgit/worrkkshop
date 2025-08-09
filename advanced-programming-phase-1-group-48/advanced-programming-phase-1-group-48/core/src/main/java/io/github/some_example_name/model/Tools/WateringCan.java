package io.github.some_example_name.model.Tools;


import io.github.some_example_name.controllers.PlantController;
import io.github.some_example_name.model.Plant.PlantInstance;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.enums.WateringCanType;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;

public class WateringCan extends Tool {
    private WateringCanType type;
    private int currentWater;

    public WateringCan(WateringCanType type) {
        super(type.getName(),type.getDisplayChar(), type.getLevel(), type.getBaseEnergyCost());
        this.type = type;
        this.currentWater = type.getCapacity();
    }

    public boolean fillFrom(int x , int y) {
        return false;
    }

    @Override
    public boolean use(Player player, int x , int y) {
        if (!canUseOn(x, y)) return false;
        int energyCost = type.getBaseEnergyCost();
        if (player.getSkill("Farming").getLevel() == 4) {
            energyCost = Math.max(0, energyCost - 1);
        }
        Game game = GameManager.getCurrentGame();
        Buff buff = player.getActiveBuff();

        if (buff != null && !buff.isExpired(game.getCurrentHour())
                && buff.getType() == Buff.Type.SKILL_ENERGY_REDUCTION
                && "Farming".equals(buff.getTargetSkill())) {
            energyCost = Math.max(0, energyCost - 1);
        }

        if (player.getEnergy() < energyCost) return false;

        player.decreaseEnergy(energyCost);
        if (player.getWaterAmount() > 0) {
            PlantInstance plant = player.getPlantedAt( x, y);
            PlantController.waterPlant(plant);
            player.decreaseWater(1);
            System.out.println("Watering " + player.getName() + " at " + x + ", " + y + " at " + energyCost);
            return true;
        }else {
            WorldMap worldmap = game.getWorldMap();
            if(worldmap.getTileAt(x, y).getType() == TileType.LAKE){
                player.refillWater();
                System.out.println("Watering Can is full of water");
            }

        }
        return false;
    }

    @Override
    public boolean canUseOn(int x, int y) {
        return false;
    }

    @Override

    public Tool upgrade() {

        WateringCanType next = WateringCanType.getNext(this.type);

        return next != null ? new WateringCan(next) : null;

    }
    public int getCurrentWater() {
        return currentWater;
    }

    public void refill() {
        this.currentWater = type.getCapacity();
    }

    public WateringCanType getType() {
        return type;
    }

    @Override
    public void interact() {

    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public boolean isEdible() {
        return false;
    }
}
