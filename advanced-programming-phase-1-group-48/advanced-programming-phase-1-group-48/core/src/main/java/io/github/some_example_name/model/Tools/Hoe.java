package io.github.some_example_name.model.Tools;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.enums.HoeType;
import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;

public class Hoe extends Tool {
    private final HoeType type;
    public Hoe(HoeType type) {
        super(type.getName(),type.getDisplayChar(), type.getLevel(), type.getBaseEnergyCost());
        this.type = type;
    }


    @Override
    public boolean use(Player player, int x, int y) {
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
        player.decreaseEnergy(energyCost);
        WorldMap worldmap = game.getWorldMap();
        if (worldmap.getTileAt(x, y).getType() == TileType.EMPTY) {
            worldmap.replaceTileTypeIfMatch(x,y,TileType.EMPTY, TileType.TILLED_SOIL);
            System.out.println("You plowed the land.");
        }else {
            System.out.println("You can now use the Boe");
        }
        return true;
    }

    @Override
    public boolean canUseOn(int x, int y) {
        return true;
        // return tile.isTillable();
    }

    @Override

    public Tool upgrade() {

        HoeType next = HoeType.getNext(this.type);

        return next != null ? new Hoe(next) : null;

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

