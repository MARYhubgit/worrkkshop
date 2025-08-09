package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.Plant.Enums.CropType;
import io.github.some_example_name.model.Plant.Enums.FertilizerType;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;
import io.github.some_example_name.model.items.Item;

public class Crop implements PlantInstance {

    static Game game = GameManager.getCurrentGame();
    static Player player = game.getCurrentPlayerForPlay();
    static WorldMap worldmap = game.getWorldMap();

    private CropType type;
    private final int birthday;
    private int stageOfGroth;
    private boolean firstHarvest;
    private boolean readyToHarvest;
    private boolean fertilizeringToDay;
    private boolean whateringToDay;
    private boolean alive;
    private int dayWhitoutWhater;
    private int dayWhitoutFertilizer;
    private FertilizerType fertilizerType;

    public Crop(CropType type) {
        this.type = type;
        this.birthday = DateAndTime.getTotalDays();
        this.stageOfGroth = 0;
        this.firstHarvest = false;
        this.readyToHarvest = false;
        this.fertilizeringToDay = false;
        this.whateringToDay = false;
        this.alive = true;
        this.dayWhitoutWhater = 0;
        this.dayWhitoutFertilizer = 0;
        this.fertilizerType = null;
    }

    public CropType getType() {return type;}
    public void setType(CropType type) {this.type = type;}
    public int getBirthday() {return birthday;}
    public int getStageOfGroth() {return stageOfGroth;}
    public void setStageOfGroth(int stageOfGroth) {this.stageOfGroth = stageOfGroth;}
    public boolean isFirstHarvest() {return firstHarvest;}
    public void setFirstHarvest(boolean firstHarvest) {this.firstHarvest = firstHarvest;}
    public boolean isReadyToHarvest() {return readyToHarvest;}
    public void setReadyToHarvest(boolean readyToHarvest) {this.readyToHarvest = readyToHarvest;}
    public boolean isAlive() {return alive;}
    public void setAlive(boolean alive) {this.alive = alive;}
    public boolean isWhateringToDay() {return whateringToDay;}
    public void setWhateringToDay(boolean whateringToDay) {this.whateringToDay = whateringToDay;}
    public boolean isFertilizeringToDay() {return fertilizeringToDay;}
    public void setFertilizeringToDay(boolean fertilizeringToDay) {this.fertilizeringToDay = fertilizeringToDay;}
    public int getDayWhitoutWhater() {return dayWhitoutWhater;}
    public void setDayWhitoutWhater(int dayWhitoutWhater) {this.dayWhitoutWhater = dayWhitoutWhater;}
    public int getDayWhitoutFertilizer() {return dayWhitoutFertilizer;}
    public void setDayWhitoutFertilizer(int dayWhitoutFertilizer) {this.dayWhitoutFertilizer = dayWhitoutFertilizer;}
    public FertilizerType getFertilizerType() {return fertilizerType;}
    public void setFertilizerType(FertilizerType fertilizerType) {this.fertilizerType = fertilizerType;}

    public boolean fertilizering(String fertilizerName) {
        FertilizerType type;
        try {
            type = FertilizerType.valueOf(fertilizerName
                    .trim()
                    .toUpperCase()
                    .replace("-", "_"));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid fertilizer type: " + fertilizerName);
            return false;
        }
        if (type!=null) {
            fertilizeringToDay = true;
            fertilizerType = type;
            fertilizeringToDay = true;
            player.getInventory().removeItem(fertilizerName, 1);
            return true;
        }else{
            return false;
        }


    }


    public void water() {
        whateringToDay=true;
        // برای ابزار ها باید اینجا تغییر داشته باشه.
    }

    public Item harvest() {
        if(type.isOneTime()){
            alive = false;
            int x=0,y=0;
            player.removePlantedInfo(x,y);
            //اضافه کردن به اینونتوری با ریترن کردن ایتم
            // ترو کردن اولین برداشت
            // چک بشه که اصلا محصولی داریم یا نه(readyToHarvest)
        }
        // این قسمت رو میتونی برای درخت ها هم استفاده داشته باشی
        else {
            // ترو کردن اولین برداشت
            //فالس کردن داشتن محصول
            //اضافه کردندبه اینونتوری
            // چک بشه که اصلا محصولی داریم یا نه(readyToHarvest)
        }
        // اگه خروجی نال بود میفهمیم که حصولی برای برداشت نبوده
        return null;
    }

    //متدی که در آخر روز برای هر گیاه باید صدا زده بشه
    //جای کار داره و بنا به داک باید پر بشه
    public boolean isPlantAlive() {
//        if (fertilizerType == FertilizerType.DELUXE_RETAINING_SOIL) {
//            whateringToDay = true;
//        }
//        if (!whateringToDay) {
//            daysWithoutWater++;
//            // manteqh 2 rooz ab nakhordan
//            if(daysWithoutWater ==2) {
//                alive =false;
//            }// می‌میرد
//        } else {
//            age++;
//            if (fertilizerType == FertilizerType.SPEED_GRO) {
//                age++; // رشد سریع‌تر
//            }
//            whateringToDay = false;
//            daysWithoutWater=0;
//
//        }
        return alive;
    }




    //    String name;
//    String source;
//    String stages;
//    Integer totalHarvestTime;
//    boolean oneTime;
//    Integer regrowthTime;      // null if one-time
//    int baseSellPrice;
//    boolean isEdible;
//    Integer energy;            // null if not edible
//    Integer baseHealth;        // null if not edible
//    String season;
//    boolean canBecomeGiant;
//
//
//
//    public Crop(String name, String source, String stages,
//                Integer totalHarvestTime, boolean oneTime,
//                Integer regrowthTime, int baseSellPrice,
//                boolean isEdible, Integer energy, Integer baseHealth,
//                String season, boolean canBecomeGiant) {
//        this.name = name;
//        this.source = source;
//        this.stages = stages;
//        this.totalHarvestTime = totalHarvestTime;
//        this.oneTime = oneTime;
//        this.regrowthTime = regrowthTime;
//        this.baseSellPrice = baseSellPrice;
//        this.isEdible = isEdible;
//        this.energy = energy;
//        this.baseHealth = baseHealth;
//        this.season = season;
//        this.canBecomeGiant = canBecomeGiant;
//    }
//


//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Name: ").append(name).append("\n");
//        sb.append("Source: ").append(source).append("\n");
//        sb.append("Stages: ").append(stages).append("\n");
//        sb.append("Total Harvest Time: ").append(totalHarvestTime).append("\n");
//        sb.append("One Time: ").append(oneTime).append("\n");
//        sb.append("Regrowth Time: ").append(regrowthTime != null ? regrowthTime : "-").append("\n");
//        sb.append("Base Sell Price: ").append(baseSellPrice).append("\n");
//        sb.append("Is Edible: ").append(isEdible).append("\n");
//        sb.append("Base Energy: ").append(energy != null ? energy : "-").append("\n");
//        sb.append("Base Health: ").append(baseHealth != null ? baseHealth : "-").append("\n");
//        sb.append("Season: ").append(season).append("\n");
//        sb.append("Can Become Giant: ").append(canBecomeGiant);
//        return sb.toString();
//    }
}



