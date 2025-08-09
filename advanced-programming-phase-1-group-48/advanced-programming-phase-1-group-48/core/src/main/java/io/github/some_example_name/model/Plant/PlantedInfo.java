package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.items.Item;

public class PlantedInfo {
    private int x, y;
    private PlantInstance plant;

    public PlantedInfo(int x, int y, PlantInstance plant) {
        this.x = x;
        this.y = y;
        this.plant = plant;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public PlantInstance getPlant() { return plant; }

    public static void harvestPlantAt(int x, int y) {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        PlantInstance plant = player.getPlantedAt(x, y);
//        if (plant == null || !plant.isReadyToHarvest()) {
//            System.out.println("Nothing to harvest at (" + x + "," + y + ")");
//            return;
//        }

        Item harvestedItem = plant.harvest();
        if (harvestedItem == null) {
            System.out.println("Harvest failed.");
            return;
        }
        player.getInventory().addItem(harvestedItem);
        player.getPlantedList().removeIf(info -> info.getX() == x && info.getY() == y);

        System.out.println("Harvested " + harvestedItem.getName() + " from (" + x + "," + y + ")");
    }
}
