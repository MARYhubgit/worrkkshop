package io.github.some_example_name.model.Tools;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.enums.PickaxeType;
import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;
import io.github.some_example_name.model.items.ItemFactory;

public class Pickaxe extends Tool {
    private PickaxeType type;

    public Pickaxe(PickaxeType type) {
        super(type.getName(), type.getDisplayChar(), type.getLevel(), type.getBaseEnergyCost());
        this.type = type;
    }

    @Override
    public boolean use(Player player, int x , int y) {
        if (!canUseOn(x, y)) return false;
        int energyCost = type.getBaseEnergyCost();
        if (player.getSkill("Mining").getLevel() == 4) {
            energyCost = Math.max(0, energyCost - 1);
        }
        Game game = GameManager.getCurrentGame();
        Buff buff = player.getActiveBuff();

        if (buff != null && !buff.isExpired(game.getCurrentHour())
                && buff.getType() == Buff.Type.SKILL_ENERGY_REDUCTION
                && "Mining".equals(buff.getTargetSkill())) {
            energyCost = Math.max(0, energyCost - 1);
        }

        if (player.getEnergy() < energyCost){
            System.out.println("you don't have enough energy");
            return false;
        }

        player.decreaseEnergy(energyCost);
        WorldMap worldmap = game.getWorldMap();

        if (worldmap.getTileAt(x, y).getType() == TileType.ROCK) {
            player.getSkill("Mining").gainXp(10);
            worldmap.replaceTileTypeIfMatch(x,y,TileType.ROCK, TileType.QUARRY);
            System.out.println("The rock has been cut");
            ItemFactory.createItem("Rock",player.getInventory());
            } else if (worldmap.getTileAt(x, y).getType() == TileType.TILLED_SOIL) {
            worldmap.replaceTileTypeIfMatch(x,y,TileType.TILLED_SOIL,TileType.EMPTY);
            System.out.println("The tilled soil land destroyed");
        }else {System.out.println("You can now use the Pickaxe");}
        return true;
    }

    @Override
    public boolean canUseOn(int x, int y) {
        return true;
    }



    @Override

    public Tool upgrade() {

        PickaxeType next = PickaxeType.getNext(this.type);

        return next != null ? new Pickaxe(next) : null;

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

