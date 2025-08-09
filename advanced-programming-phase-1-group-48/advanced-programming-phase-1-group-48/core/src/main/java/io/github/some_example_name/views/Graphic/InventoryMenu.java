package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Group;
import io.github.some_example_name.model.Player.Player;

public class InventoryMenu extends Group {
    //    private final Skin skin;
    private final Player player;

    private final Table rootTable;
    private final Table contentTable;
    private final TextButton btnInventory, btnSkills, btnSocial, btnMap;

    private final InventoryView inventoryView;
    private final SkillsView skillsView;
    private final SocialScreen socialScreen;
    private final MapView mapView;

    Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
        new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));




    public InventoryMenu(Player player, Skin skin) {
        this.player = player;


        rootTable = new Table(otherSkin);
        rootTable.setFillParent(true);
        rootTable.align(Align.top);

        // 🔹 Header Buttons
        btnInventory = new TextButton("Inventory", otherSkin);
        btnSkills = new TextButton("Skills", otherSkin);
        btnSocial = new TextButton("Social", otherSkin);
        btnMap = new TextButton("Map", otherSkin);

        Table tabTable = new Table();
        tabTable.add(btnInventory).pad(5);
        tabTable.add(btnSkills).pad(5);
        tabTable.add(btnSocial).pad(5);
        tabTable.add(btnMap).pad(5);

        // 🔹 Content Table
        contentTable = new Table();
        contentTable.align(Align.top);

        // 🔹 Create Views
        inventoryView = new InventoryView(player, otherSkin);
        skillsView = new SkillsView(player, otherSkin);
        socialScreen = new SocialScreen(player, otherSkin);
        mapView = new MapView(player, otherSkin);

        showInventoryView();

        // 🔹 Button Listeners
        btnInventory.addListener(e -> {
            if (!btnInventory.isChecked()) showInventoryView();
            return true;
        });

        btnSkills.addListener(e -> {
            if (!btnSkills.isChecked()) showSkillsView();
            return true;
        });

        btnSocial.addListener(e -> {
            if (!btnSocial.isChecked()) showSocialView();
            return true;
        });

        btnMap.addListener(e -> {
            if (!btnMap.isChecked()) showMapView();
            return true;
        });

        // 🔹 Assemble
        rootTable.add(tabTable).padTop(10).row();
        rootTable.add(contentTable).expand().fill().pad(10);

        this.addActor(rootTable);
        rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void clearAndSetView(Actor view) {
        contentTable.clearChildren();
        contentTable.add(view).expand().fill().height(Gdx.graphics.getHeight() * 0.4f); // برای اینکه فضای کافی بگیره
    }


    private void showInventoryView() {
        inventoryView.refresh();
        clearAndSetView(inventoryView);
    }

    private void showSkillsView() {
        skillsView.refresh();
        clearAndSetView(skillsView);
    }

    private void showSocialView() {
        ScrollPane scrollPane = new ScrollPane(socialScreen, otherSkin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false); // اسکرول فقط عمودی فعال
        scrollPane.setForceScroll(false, true);
        scrollPane.setFillParent(true); // مهم

        clearAndSetView(scrollPane);
    }



    private void showMapView() {
        clearAndSetView(mapView);
    }
}
