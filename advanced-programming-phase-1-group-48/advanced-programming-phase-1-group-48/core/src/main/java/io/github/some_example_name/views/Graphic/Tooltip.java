package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Tooltip extends Container<Label> {
    private final BitmapFont bitmapFont;
    private final Label.LabelStyle labelStyle;

    public Tooltip(Skin skin) {
        bitmapFont = new BitmapFont();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;

        super.setActor(new Label("", labelStyle));
        this.setVisible(false);
        this.setBackground(skin.getDrawable("window"));
    }

    public void show(String text, float x, float y) {
        this.getActor().setText(text);
        this.setPosition(x, y);
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }
}

