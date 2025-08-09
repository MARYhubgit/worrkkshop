package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.some_example_name.model.Player.Player;

public class MapView extends Table {
    public MapView(Player player, Skin skin) {
        super(skin);
        this.add(new Label("Map View - Under Development", skin)).expand().center();
    }
}
