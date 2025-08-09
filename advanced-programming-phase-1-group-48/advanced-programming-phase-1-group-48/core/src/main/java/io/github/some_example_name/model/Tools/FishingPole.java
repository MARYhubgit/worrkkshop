package io.github.some_example_name.model.Tools;


import io.github.some_example_name.model.Animal.Fish;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.Skill;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.enums.FishingPoleType;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;

public class FishingPole extends Tool {
    private FishingPoleType type;

    public FishingPole(FishingPoleType type) {
        super(type.getName(), type.getDisplayChar(), type.getLevel(), type.getBaseEnergyCost());
        this.type = type;
    }


    @Override
    public boolean use(Player player, int x, int y) {
        int energyCost = type.getBaseEnergyCost();
        Game game = GameManager.getCurrentGame();
        Buff buff = player.getActiveBuff();

        if (buff != null && !buff.isExpired(game.getCurrentHour())
                && buff.getType() == Buff.Type.SKILL_ENERGY_REDUCTION
                && "Fishing".equals(buff.getTargetSkill())) {
            energyCost = Math.max(0, energyCost - 1);
        }

        Skill fishing = player.getSkill("Fishing");
        if (fishing != null && fishing.getLevel() == 4) {
            energyCost = Math.max(0, energyCost - 1);
        }
        if (player.getEnergy() < energyCost) return false;

        player.decreaseEnergy(energyCost);
//         // ـــ انجام ماهی‌گیری: گرفتن لیست ماهی‌ها
//            Season season = player.getGame().getCurrentSeason();
//            double skillLevel = fishing != null ? fishing.getLevel() : 0;
//            double maxSkill   = Skill.MAX_LEVEL;
//            WeatherType weather = player.getGame().getCurrentWeather();
//
//            List<Fish> caught = FishingController.catchFishes(
//                season, skillLevel, maxSkill, weather);
//
//            اضافه کردن به اینونتوری
//            for (Fish fish : caught) {
//                player.getInventory().addItem(fish);
//            }
        return true;
    }

    @Override
    public boolean canUseOn(int x, int y ) {
        return false;
    }

    @Override
    public Tool upgrade() {
        switch (type) {
            case TRAINING -> { return new FishingPole(FishingPoleType.BAMBOO); }
            case BAMBOO -> { return new FishingPole(FishingPoleType.FIBERGLASS); }
            case FIBERGLASS -> { return new FishingPole(FishingPoleType.IRIDIUM); }
            default -> { return null; }
        }

    }

    public FishingPoleType getType() {
        return type;
    }

    public boolean canCatchFishType(Fish fish) {
        return type.canCatchAllFish();
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
