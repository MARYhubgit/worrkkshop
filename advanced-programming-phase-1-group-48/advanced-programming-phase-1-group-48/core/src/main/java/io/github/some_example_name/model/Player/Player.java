package io.github.some_example_name.model.Player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.NPC.NPC;
import io.github.some_example_name.model.NPC.Quest;
import io.github.some_example_name.model.Plant.PlantInstance;
import io.github.some_example_name.model.Plant.PlantedInfo;
import io.github.some_example_name.model.Player.inventory.Refrigerator;
import io.github.some_example_name.model.Reaction.Reaction;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.artisan.ArtisanWork;
import io.github.some_example_name.model.building.Buildings;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.crafting.CraftingRecipe;
import io.github.some_example_name.model.enums.*;
import io.github.some_example_name.model.farm.Farm;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.Position;
import io.github.some_example_name.model.intraction.Friendship;
import io.github.some_example_name.model.intraction.TradeRequest;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;
import io.github.some_example_name.model.items.Tree;
import io.github.some_example_name.model.shop.ShippingBin;
import io.github.some_example_name.model.shop.Shop;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.user.User;
import io.github.some_example_name.model.Player.inventory.Backpack;
import io.github.some_example_name.model.Player.inventory.TrashCan;


import java.util.*;

public class Player {
    private Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());
    private Sprite playerSprite = new Sprite(playerTexture);
    private float posX = 0;
    private float posY = 0;
    private float time = 0;
    private float speed = 5;
    private Buff activeBuff;
    private boolean isMarried = false;
    private String spouseUsername = null;
    private int energyPenaltyDays = 0;
    private WateringCanType wateringCanType = WateringCanType.BASIC;
    private int currentWaterAmount = wateringCanType.getCapacity();
    private String gender;
    private Tool equippedTool;
    private ToolActionState toolState = ToolActionState.IDLE;
    private float toolActionTimer = 0f;
    private Sprite equippedToolSprite;
    private Reaction currentReaction;
    private float reactionTimer;
    private String currentReactionMessage;
    private long reactionMessageStartTime;
    private final Set<String> learnedCraftingRecipes = new HashSet<>();
    private ShippingBin shippingBin ;

    public float getSpeed() {
        return speed;
    }

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }



    public void setFishSkill(double fishSkill) {
        this.fishSkill = fishSkill;
    }

    private double fishSkill;

    public double getFishSkill() {
        return fishSkill;
    }

    private String name;
    private EnergySystem energySystem;
    private Wallet wallet;
    private Shop shop;
    private Position position;
    private Farm farm;
    private EnergySystem energy;
    private User user;
    private Backpack backpack;
    private TrashCan trashCan;
    private Inventory inventory;
    private static Map<String, Animal> broughtAnimals = new HashMap<>();
    private Map<String, Skill> skills;
    private ArrayList<Animal> animals;
    private ArrayList<Friendship> friendships;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<Quest> quests;
    private ArrayList<HashMap<NPC, Integer>> npcs;
    private ArrayList<HashMap<Player, Friendship>> friend;
    private List<String> notifications = new ArrayList<>();
    private Set<FoodRecipe> learnedRecipes = new HashSet<>();
    private Item equippedItem;
    private static Map<Buildings, Integer> myBuildings = new HashMap<>();
    private Refrigerator refrigerator = new Refrigerator();
    private Set<String> learnedRecipesForHome = new HashSet<>();
    // نگه داری گیاهان هر شخص با مختصاتی که دارن.
    private static List<PlantedInfo> plantedList = new ArrayList<>();
    private final List<ArtisanWork> artisanWorks = new ArrayList<>();
    private Set<CraftingRecipe> learnedCrafyingRecipes = new HashSet<>();



    public Player(String name, Position position, Farm farm, User user) {
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.name = name;
        this.position = position;
        this.farm = farm;
        this.energy = energy;
        this.user = user;
        this.skills = new HashMap<>();
        this.shippingBin = new ShippingBin(GameManager.getCurrentGame());
        skills.put("Farming", new Skill("Farming"));
        skills.put("Mining", new Skill("Mining"));
        skills.put("Foraging", new Skill("Foraging"));
        skills.put("Fishing", new Skill("Fishing"));
        this.gender = gender;
        this.backpack = new Backpack(BackpackType.SMALL);
        this.trashCan = new TrashCan(TrashCanType.BASIC);

        if (backpack != null && trashCan != null) {
            this.inventory = new Inventory(backpack, trashCan);
        }
        ItemFactory.createItem("basic_hoe", inventory);
        ItemFactory.createItem("basic_pickaxe", inventory);
        ItemFactory.createItem("basic_axe", inventory);
        ItemFactory.createItem("Basic_Watering_Can", inventory);
        ItemFactory.createItem("Scythe", inventory);
        this.friend = friend;
        this.friendships = friendships;
        this.tradeRequests = tradeRequests;
        this.quests = quests;
        this.npcs = npcs;
        this.shop = null;
        this.wallet = new Wallet();

        this.energySystem = new EnergySystem();
        this.wallet = new Wallet();
        this.animals = new ArrayList<>();
        this.friendships = new ArrayList<>();
        this.tradeRequests = new ArrayList<>();
        this.quests = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    //end of constructor

    //نمیدونم این تیکه از کد چیه و خب در ادامه به چیزایی بر میخورم که یه ذره هم ازش بلد نیستم.
    public static List<PlantedInfo> getPlantedList() {
        return plantedList;
    }
    //برای کاشتن گیاه در نقشه این متد به کار میره
    public static void addPlantedInfo(int x, int y, PlantInstance plant) {
        plantedList.add(new PlantedInfo(x, y, plant));
    }
    // برای حذف گیاه از نقشه به کار میره
    public static void removePlantedInfo(int x, int y) {
        plantedList.removeIf(info -> info.getX() == x && info.getY() == y);
    }

    // برای دستور فهمیدن اطلاعات گیاه روی نقشه به کار میره
    public static PlantInstance getPlantedAt(int x, int y) {
        for (PlantedInfo info : getPlantedList()) {
            if (info.getX() == x && info.getY() == y) {
                return info.getPlant();
            }
        }
        return null;
    }

    public static Tree getPlantedTreeAt(int x, int y) {
        PlantInstance p = getPlantedAt(x,y);
        return (p instanceof Tree) ? (Tree)p : null;
    }

    public static boolean removePlantedTreeAt(int x, int y) {
        PlantInstance p = getPlantedAt(x, y);
        if (p instanceof Tree) {
            return plantedList.removeIf(info ->
                info.getX() == x &&
                    info.getY() == y &&
                    info.getPlant() instanceof Tree
            );
        }
        return false;
    }






    public void setEquippedItem(Item item) {
        this.equippedItem = item;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }


    public Inventory getInventory() {

        return inventory;

    }
    public void learnRecipeForHome(String recipeName) {
        learnedRecipesForHome.add(recipeName.toLowerCase());
    }

    public boolean hasLearnedRecipeForHome(String recipeName) {
        return learnedRecipesForHome.contains(recipeName.toLowerCase());
    }

    public Player getCurrentPlayer() {
        return this;
    }
    public Skill getSkill(String name) {
        if (!skills.containsKey(name)) {
            throw new IllegalArgumentException("Skill '" + name + "' does not exist.");
        }
        return skills.get(name);
    }

    public String getName() {
        return name;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean hasLearnedRecipe(String itemName) {
        return learnedRecipes.contains(itemName.toLowerCase());
    }

    public void learnRecipe(FoodRecipe recipe) {
        learnedRecipes.add(recipe);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }


    public ArrayList<Friendship> getFriendships() {
        return friendships;
    }

    public ArrayList<TradeRequest> getTradeRequests() {
        return tradeRequests;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public ArrayList<HashMap<NPC, Integer>> getNpcs() {
        return npcs;
    }

    private TileType previousTileType = TileType.CABIN; // فرض اولیه

    public TileType getPreviousTileType() {
        return previousTileType;
    }

    public void setPreviousTileType(TileType type) {
        this.previousTileType = type;
    }


    public EnergySystem getEnergySystem() {
        return energySystem;
    }

    public int getEnergy() {
        return energySystem.getEnergy();
    }

    public void decreaseEnergy(int amount) {
        energySystem.decreaseEnergy(amount);
        if (energySystem.isPassedOut()) {
            // dont do the work
        }
    }

    public void setEnergy(int value) {
        energySystem.setEnergy(value);
    }

    public void increaseEnergy(int amount) {
        energySystem.increaseEnergy(amount);
    }


    public void setUltimate() {
        energySystem.setUnlimited(true);
    }


    public boolean isAdjacentTo(Player other) {
        int dx = Math.abs(this.position.getRow() - other.position.getCol());
        int dy = Math.abs(this.position.getRow() - other.position.getCol());
        return dx <= 1 && dy <= 1;
    }


    public String getGender() {
        return gender;
    }


    public boolean isMarried() {
        return isMarried;
    }


    public void addNotification(String message) {
        notifications.add(message);
    }


    public List<String> getNotifications() {

        return new ArrayList<>(notifications);

    }


    public static Map<Buildings, Integer> getMyBuildings() {
        return myBuildings;
    }


    public static void addBuilding(Buildings building) {
        myBuildings.merge(building, 1, Integer::sum);
    }


    public Refrigerator getRefrigerator() {
        return refrigerator;
    }


    public static void addBroughtAnimal(String name, Animal animal) {
        broughtAnimals.put(name, animal);
    }


    public static Map<String, Animal> getBroughtAnimal() {
        return broughtAnimals;
    }


    public static Map<Buildings, Integer> getMyBuilding() {
        return myBuildings;
    }


    public void resetDailyEnergy() {
        energySystem.resetDailyEnergy();
    }


    public boolean knowsRecipe(FoodRecipe recipe) {
        return learnedRecipes.contains(recipe);
    }


    public Set<FoodRecipe> getLearnedRecipes() {
        return Collections.unmodifiableSet(learnedRecipes);
    }


    public void setActiveBuff(Buff buff) {
        this.activeBuff = buff;
    }


    public Buff getActiveBuff() {
        return activeBuff;
    }

    public int getMaxEnergy() {
        return energySystem.getMaxEnergy();
    }
    public void addMoney(int amount) {
        wallet.add(amount);
    }
    public void setMaxEnergy() {
        energySystem.setMaxEnergy(200);
    }
    public int getWaterAmount() {
        return currentWaterAmount;
    }
    public void upgradeBackpack(BackpackType newType) {
        backpack.upgrade(newType);//mary ***************
    }

    public int getMoney() {
        return wallet.getGold();
    }

    public void decreaseMoney(int amount) {
        wallet.deduct(amount);
    }//mary **********

    public BackpackType getBackpack() {
        return backpack.getType();
    }

    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType newType) {
        this.wateringCanType = newType;
        this.currentWaterAmount = newType.getCapacity();
    }

    public boolean decreaseWater(int amount) {
        if (currentWaterAmount >= amount) {
            currentWaterAmount -= amount;
            return true;
        }
        return false;
    }

    public void refillWater() {
        currentWaterAmount = wateringCanType.getCapacity();
    }
    public List<ArtisanWork> getArtisanWorks() {
        return artisanWorks;
    }

    public void addArtisanWork(ArtisanWork work) {
        artisanWorks.add(work);
    }


    private final Set<String> metToday = new HashSet<>();


    public boolean hasMetToday(String npc) {
        return metToday.contains(npc);
    }

    public void markMetToday(String npc) {
        metToday.add(npc);
    }

    public void resetDailyNPCFlags() {
        metToday.clear();
    }
    private final Set<String> giftedToday = new HashSet<>();

    public boolean hasGiftedToday(String npc) {
        return giftedToday.contains(npc.toLowerCase());
    }

    public void markGiftedToday(String npc) {
        giftedToday.add(npc.toLowerCase());
    }

    public void resetDailyGiftFlags() {
        giftedToday.clear();
    }
    // مقداردهی اولیه
    private final Map<String, Integer> friendshipPoints = new HashMap<>();

    public void addFriendshipPoints(String npcName, int points) {
        npcName = npcName.toLowerCase();
        int current = friendshipPoints.getOrDefault(npcName, 0);
        current = Math.min(799, current + points);
        friendshipPoints.put(npcName, current);
    }

    public int getFriendshipPoints(String npcName) {
        return friendshipPoints.getOrDefault(npcName.toLowerCase(), 0);
    }

    public int getFriendshipLevel(String npcName) {
        return getFriendshipPoints(npcName) / 200;
    }

    public Map<String, Integer> getAllFriendships() {
        return friendshipPoints;
    }

    // در Player.java
    private final List<Quest> activeQuests = new ArrayList<>();
    private final Set<Quest> completedQuests = new HashSet<>();

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public void addActiveQuest(Quest quest) {
        activeQuests.add(quest);
    }

    public void completeQuest(Quest quest) {
        completedQuests.add(quest);
        activeQuests.remove(quest);
    }

    public String getUsername() {

        return name;

    }

    public Map<String, Skill> getSkills() {

        return skills;

    }
    public void setEquippedTool(Tool tool) {

        this.equippedTool = tool;

        if (tool != null) {

            Texture texture = new Texture("item/" + tool.getName() + ".png");

            this.equippedToolSprite = new Sprite(texture);

            this.equippedToolSprite.setSize(texture.getWidth(), texture.getHeight());

            this.equippedToolSprite.setOriginCenter();

        } else {

            this.equippedToolSprite = null;

        }

    }



    public Sprite getEquippedToolSprite() {

        return equippedToolSprite;

    }



    public Tool getEquippedTool() {
        return equippedTool;
    }

    public void useTool() {

        if (toolState == ToolActionState.IDLE && equippedTool != null) {

            toolState = ToolActionState.USING;

            toolActionTimer = 0.3f; // مدت زمان انیمیشن

        }

    }



    public void updateTool(float delta) {

        if (toolState == ToolActionState.USING) {

            toolActionTimer -= delta;

            if (toolActionTimer <= 0) {

                toolState = ToolActionState.COOLDOWN;

                toolActionTimer = 0.5f; // مدت زمان cooldown

            }

        } else if (toolState == ToolActionState.COOLDOWN) {

            toolActionTimer -= delta;

            if (toolActionTimer <= 0) {

                toolState = ToolActionState.IDLE;

            }

        }

    }

    public ToolActionState getToolState() {

        return toolState;

    }

    public Rectangle getBounds() {
        return new Rectangle(posX, posY, 32, 32);
    }

    public void learnCraftingRecipe(String recipeName) {
        learnedCraftingRecipes.add(recipeName.toLowerCase());
    }

    public boolean hasLearnedCraftingRecipe(String recipeName) {
        return learnedCraftingRecipes.contains(recipeName.toLowerCase());
    }
    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public void setShippingBin(ShippingBin shippingBin) {
        this.shippingBin = shippingBin;
    }

    public void setReaction(Reaction reaction) {
        this.currentReaction = reaction;
    }

    public Reaction getCurrentReaction() {
        return currentReaction;
    }

    public void updateReaction(float delta) {
        if (currentReaction != null) {
            currentReaction.update(delta);
            if (currentReaction.isExpired()) {
                currentReaction = null;
            }
        }
    }

    public void setReactionMessage(String message) {
        this.currentReactionMessage = message;
        this.reactionMessageStartTime = System.currentTimeMillis();
    }

    public String getReactionMessage() {
        if (System.currentTimeMillis() - reactionMessageStartTime > 5000) {
            currentReactionMessage = null;
        }
        return currentReactionMessage;
    }

}
