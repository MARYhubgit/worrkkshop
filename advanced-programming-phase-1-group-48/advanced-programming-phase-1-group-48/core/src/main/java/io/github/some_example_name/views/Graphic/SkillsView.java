package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.Skill;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;

import java.util.Map;

public class SkillsView extends Group {
    private final Player player;
    private final Skin skin;
    private final Table skillsTable;
    private final ScrollPane scrollPane;
    Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
        new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));

    public SkillsView(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;

        // اندازه‌گیری کلی برای اطمینان از فضای کافی
        this.setSize(Gdx.graphics.getWidth() / 2f, 300);

        skillsTable = new Table(otherSkin);
        skillsTable.align(Align.topLeft).pad(10);

        updateSkillsTable();

        scrollPane = new ScrollPane(skillsTable, otherSkin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        Table mainTable = new Table(otherSkin);
        mainTable.setFillParent(true);
        mainTable.align(Align.top);
        mainTable.add(scrollPane).expand().fill().pad(10);

        this.addActor(mainTable);
    }

    private void updateSkillsTable() {
        skillsTable.clear();

        for (Map.Entry<String, Skill> entry : player.getSkills().entrySet()) {
            Skill skill = entry.getValue();
            String skillName = skill.getName().toLowerCase();

            // آیکون مهارت
            Texture texture = new Texture("Skill/" + skillName + ".png");
            Image skillImage = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
            skillImage.setScaling(Scaling.fit);
            skillImage.setSize(64, 64);

            // اطلاعات XP و Level
            Label infoLabel = new Label("Lv " + skill.getLevel() + " | XP " + skill.getXp(), otherSkin);
            infoLabel.setAlignment(Align.left);

            // Tooltip توضیحی
            String tooltipText = getSkillDescription(skill.getName());
            Tooltip<Label> tooltip = new Tooltip<>(new Label(tooltipText, otherSkin));

            // کانتینر افقی (آیکون + متن)
            Table container = new Table(otherSkin);
            container.align(Align.left);
            container.add(skillImage).size(64).padRight(10);
            container.add(infoLabel).align(Align.left).expandX().left();

            container.addListener(tooltip);

            // افزودن به جدول اصلی
            skillsTable.add(container).pad(10).expandX().fillX().row();
        }
    }

    private String getSkillDescription(String skillName) {
        return switch (skillName.toLowerCase()) {
            case "farming" -> "🌾 Farming: Increase crop yield and efficiency.\nLevel 1: Basic farming.\nLevel 2: +10% speed.\nLevel 3: Less water needed.";
            case "mining" -> "⛏ Mining: Break rocks faster.\nLevel 1: Basic mining.\nLevel 2: +10% mining speed.\nLevel 3: Chance for rare ores.";
            case "foraging" -> "🍃 Foraging: Gather resources.\nLevel 1: Basic foraging.\nLevel 2: Find more items.\nLevel 3: Higher quality resources.";
            case "fishing" -> "🎣 Fishing: Catch fish efficiently.\nLevel 1: Basic fishing.\nLevel 2: +10% catch speed.\nLevel 3: Rare fish appear.";
            default -> "Skill: " + skillName + "\nLevel up to unlock bonuses!";
        };
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void refresh() {
        updateSkillsTable();
    }
}
