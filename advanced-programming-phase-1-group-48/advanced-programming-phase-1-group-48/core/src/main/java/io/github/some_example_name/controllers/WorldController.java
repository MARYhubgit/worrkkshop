package io.github.some_example_name.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.model.WarpZone;

import java.util.ArrayList;
import java.util.List;

public class WorldController {

    private PlayerController playerController;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<WarpZone> warpZones = new ArrayList<>();

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        map = new TmxMapLoader().load("Content (unpacked)/Maps/Farm.tmx");
        //map = new TmxMapLoader().load("Content (unpacked)/Maps/FarmHouse.tmx");
        warpZones = loadWarpZones(map);
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(float deltaTime) {
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();
        camera.position.set(playerX, playerY, 0);
        camera.update();
        //System.out.println("Player position: x=" + playerX + " y=" + playerY);
        // System.out.println("warpZones.size = " + warpZones.size());
        for (WarpZone warp : warpZones) {
            //System.out.println("Checking warp zone at x=" + warp.x + " y=" + warp.y + " w=" + warp.width + " h=" + warp.height);
            if (warp.contains(playerX, playerY)) {
                //System.out.println("Player entered warp zone!");
                //System.out.println("Switching to map: " + warp.targetMap);
                //System.out.println("Warp target position: " + warp.targetX + ", " + warp.targetY);

                switchMap(warp.targetMap, warp.targetX, warp.targetY);

                break;
            }
        }


        renderer.setView(camera);
        renderer.render();

    }

    private String extractMapName(TiledMap map) {
        // مثال: Content (unpacked)/Maps/Farm.tmx → Farm
        String mapPath = map.toString();
        if (mapPath.contains("/")) {
            mapPath = mapPath.substring(mapPath.lastIndexOf("/") + 1);
        }
        if (mapPath.endsWith(".tmx")) {
            mapPath = mapPath.substring(0, mapPath.length() - 4);
        }
        return mapPath;
    }


    public String checkMapTransition() {
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();
        MapLayer objectLayer = map.getLayers().get("AlwaysFront2");
        if (objectLayer == null) return null;

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                if (object.getName() != null && rect.contains(playerX, playerY)) {
                    return object.getName();
                }
            }
        }

        return null;
    }

    public void loadMap(String path) {
        if (map != null) map.dispose();
        map = new TmxMapLoader().load(path);
        renderer.setMap(map);
    }

    public List<WarpZone> loadWarpZones(TiledMap map) {
        List<WarpZone> warps = new ArrayList<>();

        //System.out.println("Loading warps from map...");

        for (MapLayer layer : map.getLayers()) {
            if (layer.getObjects() == null || layer.getObjects().getCount() == 0) continue;

            for (MapObject obj : layer.getObjects()) {
                MapProperties props = obj.getProperties();

                if (!props.containsKey("Warp")) continue;

                //System.out.println("Warp Object Found: " + props);

                String[] warpData = props.get("Warp").toString().split(" ");
                if (warpData.length != 3) {
                    //System.out.println("Invalid warp data format: " + props.get("Warp"));
                    continue;
                }

                String targetMap = warpData[0];
                int targetX = Integer.parseInt(warpData[1]);
                int targetY = Integer.parseInt(warpData[2]);

                float x = Float.parseFloat(props.get("x").toString());
                float y = Float.parseFloat(props.get("y").toString());
                float width = props.containsKey("width") ? Float.parseFloat(props.get("width").toString()) : 32f;
                float height = props.containsKey("height") ? Float.parseFloat(props.get("height").toString()) : 32f;

                // System.out.println("Warp loaded at x=" + x + " y=" + y + " to map=" + targetMap + " at x=" + targetX + " y=" + targetY);

                WarpZone warp = new WarpZone(x, y, width, height, targetMap, targetX, targetY);
                warps.add(warp);
            }
        }

        // System.out.println("Total warps loaded: " + warpZones.size());

        return warps;
    }

    public void switchMap(String mapName, int spawnX, int spawnY) {
        map.dispose();
        renderer.dispose();

        map = new TmxMapLoader().load("Content (unpacked)/Maps/" + mapName + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        warpZones = loadWarpZones(map);

        playerController.getPlayer().setPosX(spawnX);
        playerController.getPlayer().setPosY(spawnY);
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

}
