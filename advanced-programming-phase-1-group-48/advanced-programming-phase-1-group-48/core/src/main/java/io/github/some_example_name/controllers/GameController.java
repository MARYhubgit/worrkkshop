package io.github.some_example_name.controllers;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.views.GameView;
import io.github.some_example_name.views.Graphic.ActorState;
import io.github.some_example_name.views.Graphic.BarnView;
import io.github.some_example_name.views.Graphic.CoopView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
//    private WeaponController weaponController;
private boolean isBarnStage = false;
private boolean isCoopStage = false;

    private Vector2 lastFarmPosition = new Vector2(200, 300);

    public void setView(GameView view) {
        this.view = view;
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        playerController = new PlayerController(player);
        worldController = new WorldController(playerController);
//        weaponController = new WeaponController(new Weapon());
    }

    public void updateGame(float delta) {
        try {
            String trigger = worldController.checkMapTransition();

            if ("enter_house".equals(trigger)) {
                loadHouseMap();
            } else if ("exit_house".equals(trigger)) {
                loadFarmMap();
            }

            if (view != null) {
                worldController.update(delta);
                playerController.update();
//        weaponController.update();

                // بررسی برخورد بازیکن با Barn
                for (BarnView barn : view.getBarns()) {
                    if (barn.getBounds().overlaps(playerController.getPlayer().getBounds())) {
                        // اگه بازیکن با انبار برخورد کرد:
                        Player player = playerController.getPlayer();
                        saveStage();
                        loadBarnMap();
                        view.setStage(barn.getStage());
                        isBarnStage = true;
                        lastFarmPosition.set(player.getPosX(), player.getPosY());
                        break; // فقط یکی کافیه
                    }
                }
                if (isBarnStage) {
                    view.setCurrentArea(GameView.Area.BARN);
                    Rectangle exitZone = new Rectangle(100, 100, 32, 32); // موقعیت خروج از طویله
                    if (exitZone.overlaps(playerController.getPlayer().getBounds())) {
                        view.setCurrentArea(GameView.Area.FARM);
                         // همه‌ی Actorها رو از Stage پاک می‌کنه
                        loadFarmMap();
                        view.setStage(view.getStage_copy());// برگرد به Stage اصلی فارم
                        isBarnStage = false;
                        playerController.getPlayer().setPosX(lastFarmPosition.x);
                        playerController.getPlayer().setPosY(lastFarmPosition.y);

                    }
                }

                for (CoopView barn : view.getCoops()) {
                    if (barn.getBounds().overlaps(playerController.getPlayer().getBounds())) {
                        // اگه بازیکن با انبار برخورد کرد:
                        Player player = playerController.getPlayer();
                        saveStage();
                        loadCoopMap();
                        view.setStage(barn.getStage());
                        isCoopStage = true;
                        lastFarmPosition.set(player.getPosX(), player.getPosY());
                        break; // فقط یکی کافیه
                    }
                }
                if (isCoopStage) {
                    view.setCurrentArea(GameView.Area.Coop);
                    Rectangle exitZone = new Rectangle(100, 100, 32, 32); // موقعیت خروج از طویله
                    if (exitZone.overlaps(playerController.getPlayer().getBounds())) {
                        view.setCurrentArea(GameView.Area.FARM);
                        // همه‌ی Actorها رو از Stage پاک می‌کنه
                        loadFarmMap();
                        view.setStage(view.getStage_copy());// برگرد به Stage اصلی فارم
                        isBarnStage = false;
                        playerController.getPlayer().setPosX(lastFarmPosition.x);
                        playerController.getPlayer().setPosY(lastFarmPosition.y);

                    }
                }

            }
        }catch (Exception e) {
            System.out.println("vvvvv"+ e.getMessage());
        }

    }
    public void loadBarnMap() {
        try {
            ScreenUtils.clear(0, 0, 0, 1);
            //C:\Users\Dotcom\Desktop\New folder (2)\advanced-programming-phase-1-group-48\assets\Content (unpacked)\Maps
            worldController.loadMap("Content (unpacked)/Maps/FarmHouse.tmx");
            //playerController.getPlayer().setPos(START_X, START_Y);
        }catch (Exception e) {
            System.out.println("mmmmmm"+ e.getMessage());
        }

    }

    public void loadCoopMap() {
        try {
            ScreenUtils.clear(0, 0, 0, 1);
            //C:\Users\Dotcom\Desktop\New folder (2)\advanced-programming-phase-1-group-48\assets\Content (unpacked)\Maps
            worldController.loadMap("Content (unpacked)/Maps/FarmHouse.tmx");
            //playerController.getPlayer().setPos(START_X, START_Y);
        }catch (Exception e) {
            System.out.println("mmmmmm"+ e.getMessage());
        }

    }

    public void loadHouseMap() {
        worldController.loadMap("Content (unpacked)/Maps/FarmHouse.tmx");
        //playerController.getPlayer().setPos(START_X, START_Y);
    }

    public void loadFarmMap() {
        worldController.loadMap("Content (unpacked)/Maps/Farm.tmx");
        //playerController.getPlayer().setPos(EXIT_X, EXIT_Y);
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    //    public WeaponController getWeaponController() {
//        return weaponController;
//    }
    Map<Stage, List<ActorState>> savedStates = new HashMap<>();
    List<ActorState> currentStates = new ArrayList<>();
    public void saveStage(){
        for (Actor actor : view.getStage().getActors()) {
            ActorState state = new ActorState(actor.getX(), actor.getY(), actor.isVisible());
            state.posX = actor.getX();
            state.posY = actor.getY();
            state.isVisible = actor.isVisible(); // یا هر شرطی برای فعال بودن
            // ذخیره بقیه وضعیت‌ها اگر لازم بود
            currentStates.add(state);

            // غیرفعال کردن اکتور
            actor.setVisible(false);
            actor.setTouchable(Touchable.disabled); // اگر لازم بود
        }
        savedStates.put(view.getStage(), currentStates);
    }
}
