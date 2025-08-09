// فایل: PlantController.java

package io.github.some_example_name.controllers;



import io.github.some_example_name.model.Plant.Crop;
import io.github.some_example_name.model.Plant.Enums.*;
import io.github.some_example_name.model.Plant.PlantFactory;
import io.github.some_example_name.model.Plant.PlantInstance;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.enums.Season;
import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;

import java.util.Optional;



public class PlantController {
    //گرفتن پلیر الان
    static Game game = GameManager.getCurrentGame();
    static Player player = game.getCurrentPlayerForPlay();
    static WorldMap worldmap = game.getWorldMap();
               static Tool currentTool = (Tool) player.getEquippedItem();

    public PlantController(Game game) {
        this.game = game;
    }

    public static boolean Plant(String seedName , String direction){
        // اینجا باید تغییر کرده و در inventory دنبال دانه گشت.
        SeedType seed = SeedType.RARE_SEED;
        // طبیعتا با زدن متد در worldmap باید این قسمت درست بشه.
        int x=0,y=0;
        TileType tile = worldmap.getTileAt(x, y).getType();
        if (tile != TileType.TILLED_SOIL && seed != null) {
            TreeType tree = TreeType.findBySeedName(seedName);
            CropType crop = CropType.findBySeedName(seedName);
            if(crop!=null) {
                PlantInstance plant;
                    plant = new Crop(crop);
//                currentTool.use(player, x, y);
                player.addPlantedInfo(x, y, plant);
                worldmap.replaceTileTypeIfMatch(x, y, TileType.TILLED_SOIL, TileType.CROP);
                player.getInventory().removeItem(seedName, 1);
                return true;
            }else if(tree!=null){
                PlantInstance plant = PlantFactory.creatTree (tree);
//                currentTool.use(player, x, y);
                player.addPlantedInfo(x, y, plant);
                worldmap.replaceTileTypeIfMatch(x,y,TileType.TILLED_SOIL,TileType.TREE);
                player.getInventory().removeItem(seedName, 1);
                return true;
            }
        }
        return false;
    }

    public static PlantInstance showPlantInfo(int x, int y) {
        return player.getPlantedAt(x, y);
    }

    public static boolean plow(String diretion){
        int x=0,y=0;
        TileType tile = worldmap.getTileAt(x, y).getType();
        //برای نام ابزار مطمئن نیستم.
        if(tile == TileType.EMPTY && currentTool.getName().equals("Heo") ){
            worldmap.replaceTileTypeIfMatch(x,y,TileType.EMPTY,TileType.TILLED_SOIL);
            return true;
        }else {
            return false;
        }
    }


    public static void startDayForPlant(Crop crop) {
        int today = DateAndTime.getTotalDays();
        int age = today - crop.getBirthday();
        int[] stage = crop.getType().getStages();
        int endOfStage = stage.length;
        if (!crop.isFirstHarvest()) {
            if (stage[0] == age) {
                crop.setStageOfGroth(1);
            } else if (age == stage[0] + stage[1]) {
                crop.setStageOfGroth(2);
            } else if (age == stage[0] + stage[1] + stage[2]) {
                crop.setStageOfGroth(3);
            } else if (age == stage[1] + stage[2] + stage[3] + stage[4] && crop.getStageOfGroth() == endOfStage) {
                crop.setStageOfGroth(4);
                crop.setReadyToHarvest(true);
            }
        } else if (!crop.getType().isOneTime()) {
            int sum = stage[1] + stage[2] + stage[3] + stage[4];
            if ((age - sum) % crop.getType().getTotalHarvestTime() == 0) {
                crop.setReadyToHarvest(true);
            }
        }

    }

    //متد برداشت محصول
    public static void harvest(String diretion) {
        Crop crop=null;//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (crop.isFirstHarvest() && crop.isFirstHarvest()) {
//            if(!crop.getType().isOneTime()){
//                //یعنی محصول دو برداشته است
                crop.harvest();
//            }else {
//
//            }
        }
    }

    //متد کود دادن
    public static boolean fertilizing(String filterizeName,String direction) {
        // اینجا باید یک متد صدا زده شود که در آن با گرفتن جهت گیاه مورد نظر را بر گرداند//+++++++++++++++++++++++++++++++++++++++++++++
Crop crop=null;
        if (crop.fertilizering(filterizeName))
            return true;
        return false;
    }

    //متد اب دادن
    public static void whatering(String direction) {
        // اینجا باید یک متد صدا زده شود که در آن با گرفتن جهت گیاه مورد نظر را بر گرداند//+++++++++++++++++++++++++++++++++++++++++++++
        Crop crop=null;
        crop.water();
    }

    //متد مربوط به دانه ای مخلوط
    public static void MixSeed(String seedName, String direction) {
        CropType crop =handleMixedSeedPlanting(seedName);
        Plant(crop.getName(),direction);
    }

    public static CropType handleMixedSeedPlanting(String mixSeedName) {
        // باید روی سازوکار این متد وقت بزارم.
        Season currentSeason = DateAndTime.getCurrentSeason();
        Optional<MixSeedType> optionalMix = MixSeedType.fromName(mixSeedName);

        if (optionalMix.isPresent()) {
            MixSeedType mix = optionalMix.get();
            if (mix.getSeason() == currentSeason) {
                return mix.getRandomPlant();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }



//    public  static void addPlantedInfo(int x, int y, PlantInstance plant) {
//        player.getPlantedList().add(new PlantedInfo(x, y, plant));
//    }
//
//    public static PlantInstance getPlantedAt(int x, int y) {
//        for (PlantedInfo info : getPlantedList()) {
//            if (info.getX() == x && info.getY() == y) {
//                return info.getPlant();
//            }
//        }
//        return null;
//    }
//
//    public static PlantInstance getPlantedName(int x, int y) {
//        for (PlantedInfo info : getPlantedList()) {
//            if (info.getX() == x && info.getY() == y) {
//                return info.getPlant();
//            }
//        }
//        return null;
//    }




//    public static void getInfo(String inputName) {
//        Stream.<Function<String, Optional<? extends PlantType>>>of(
//                        CropType::fromName,
//                        TreeType::fromName,
//                        ForCropType::fromName,
//                        ForTreeType::fromName
//                )
//                .map(f -> f.apply(inputName))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .findFirst()
//                .ifPresentOrElse(
//                        obj -> System.out.println(obj),
//                        () -> System.out.println("Not Found: " + inputName)
//                );
//    }



//    //متد کاشتن گیاه(اصلی)
//    public static void plantSeed(String seedName, int x, int y) {
//    //Place is the method that show is x , y near playe
//        if ((worldmap.getTileAt(x, y).getType() != TileType.TILLED_SOIL)) {
//            System.out.println("You can't plant here.");
//            return;
//        }
//
//
//
//        if (!player.getInventory().hasItem(seedName)) {
//            System.out.println("You don't have " + seedName + " in your inventory.");
//            return;
//        }
//
//        String cropNameToPlant = seedName;
//        Season currentSeason = DateAndTime.getCurrentSeason();
//        //ChangPlace is a method for change w to
//        CropType cropType = CropType.valueOf(cropNameToPlant.toUpperCase().replace(" ", "_"));
//        PlantInstance plant = PlantFactory.createPlant(cropType);
//        //changePlace(x,y);
//
//        if (plant == null) {
//            System.out.println("Invalid seed or crop: " + cropNameToPlant);
//            return;
//        }
//        player.getInventory().removeItem(seedName, 1);
//        addPlantedInfo(x, y, plant);
//        System.out.println("Planted " + cropNameToPlant + " at (" + x + "," + y + ")");
//    }


//    public static void showPlant(int x, int y) {
//        if (getPlantedAt(x, y) == null) {
//            System.out.println("no plant is here");
//        } else {
//            PlantInstance plant = getPlantedAt(x, y);
//            if (plant instanceof OneTimeCropInstance crop) {
//                String name = crop.getType().getName();
//                getInfo(name);
//            } else if (plant instanceof RegrowableCropInstance crop) {
//                String name = crop.getType().getName();
//                getInfo(name);
//            } else if (plant instanceof Tree tree) {
//                String name = tree.getType().getName();
//                getInfo(name);
//            }
//        }

    //}


    //
//
//
    public static void waterPlant(PlantInstance plant) {

        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        plant.water();
    }

    //
    public static void fertilizeTile(String fertilizerName, int x, int y) {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();


        // 4) چک موجود بودن کود
        if (!player.getInventory().hasItem(fertilizerName)) {
            System.out.println("You don't have " + fertilizerName + " in your inventory.");
            return;
        }

        FertilizerType type;
        try {
            type = FertilizerType.valueOf(fertilizerName
                    .trim()
                    .toUpperCase()
                    .replace("-", "_"));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid fertilizer type: " + fertilizerName);
            return;
        }
//        PlantInstance plant = getPlantedAt(x, y);
//        if (plant instanceof OneTimeCropInstance otc) {
//            otc.applyFertilizer(type);
//        } else if (plant instanceof RegrowableCropInstance rc) {
//            rc.applyFertilizer(type);
//        } else {
//            System.out.println("You can only fertilize crops, not " + plant.getClass().getSimpleName());
//            return;
//        }
        player.getInventory().removeItem(fertilizerName, 1);

        System.out.println("Applied " + fertilizerName + " to crop at ("
                + x + "," + y + ").");
    }


//    public static PlantingBehavior detectBehaviorAt(int x, int y) {
//        PlantInstance plant = getPlantedAt(x, y);  // یا از PlantController
//        if (plant == null) {
//            return PlantingBehavior.NONE;
//        }
//        if (plant instanceof Tree) {
//            return PlantingBehavior.TREE;
//        }
//        if (plant instanceof OneTimeCropInstance) {
//            return PlantingBehavior.ONE_TIME_CROP;
//        }
//        if (plant instanceof RegrowableCropInstance) {
//            return PlantingBehavior.REGROWABLE_CROP;
//        }
//        return PlantingBehavior.NONE;
//    }


//    public static boolean testOneTimeCropLifecycle(OneTimeCropInstance crop) {
//        while (!crop.isReadyToHarvest()) {
//            crop.growOneDay();
//            crop.resetWaterFlag();
//            crop.water();
//        }
//        return crop.isReadyToHarvest();
//    }
//
//
//    public static boolean testRegrowableCropLifecycle(RegrowableCropInstance crop) {
//
//        while (!crop.isReadyToHarvest()) {
//            crop.growOneDay();
//            crop.resetWaterFlag();
//            crop.water();            // هر روز آب می‌دهیم
//        }
//        if (!crop.isReadyToHarvest()) return false;
//        Item first = crop.harvest();
//        if (first == null) return false;
//
//        // * مورد دوم: صبر به اندازهٔ regrowthTime و برداشت دوم *
//        int regrowth = crop.getType().getRegrowthTime();
//        for (int i = 0; i < regrowth; i++) {
//            crop.growOneDay();
//            crop.resetWaterFlag();
//            crop.water();
//        }
//        // تا وقتی دوباره آماده نشده روز اضافه رشد کن
//        while (!crop.isReadyToHarvest()) {
//            crop.growOneDay();
//            crop.resetWaterFlag();
//            crop.water();
//        }
//        Item second = crop.harvest();
//        return second != null;
//    }
//
//    /// //////////////////TREE TEST HELPER
//    public static boolean testPeriodicFruit(Tree tree) {
//        // ۱– افزایش روزانه تا بلوغ
//        while (!tree.isMature()) {
//            tree.growOneDay();
//        }
//        // ۲– افزایش روزانه تا دوره برداشت
//        int cycle = tree.getType().getFruitCycle();
//        for (int i = 0; i < cycle; i++) {
//            tree.growOneDay();
//        }
//        // اکنون آمادهٔ برداشت اول
//        Item first = tree.harvest();
//        if (first == null || !first.getName().equals(tree.getType().getFruit())) {
//            return false;
//        }
//        // ۳– پس از برداشت اول، درخت حذف نمی‌شود و دوره دوباره شروع می‌شود
//        for (int i = 0; i < cycle; i++) {
//            tree.growOneDay();
//        }
//        Item second = tree.harvest();
//        return second != null && second.getName().equals(tree.getType().getFruit());
//    }
//
//    public static boolean testLightningStrike(Tree tree) {
//        tree.strikeByLightning();
//        Item dropped = tree.harvest();
//        return dropped != null && dropped.getName().equalsIgnoreCase("Coal");
//
//
//    }
//
//    public static void startForPlant() {
//        Game game = GameManager.getCurrentGame();
//        Player player = game.getCurrentPlayerForPlay();
//        Iterator<PlantedInfo> it = player.getPlantedList().iterator();
//        while (it.hasNext()) {
//            PlantedInfo info = it.next();
//            PlantInstance plant = info.getPlant();
//
//            // 1) رشد یک روز
//            plant.growOneDay();
//
//            // 4) ریست پرچمِ آب
//            plant.resetWaterFlag();
//        }
//    }
}
