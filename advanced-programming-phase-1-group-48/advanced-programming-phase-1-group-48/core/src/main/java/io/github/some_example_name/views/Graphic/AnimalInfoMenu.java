package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.model.Animal.AnimalKind;

public class AnimalInfoMenu extends Window {
    public AnimalInfoMenu(AnimalView animalView, Skin skin) {
        super("Animal Info", skin);
Animal animal=animalView.getAnimalModel();
        // ۱) یک Table برای اطلاعات
        Table infoTable = new Table(skin);
        infoTable.add(new Label("Type:", skin)).left();
        infoTable.add(new Label(animal.getAnimalKind().name(), skin)).row();

        infoTable.add(new Label("Friendship:", skin)).left();
        infoTable.add(new Label(String.valueOf(animal.getFriendship()), skin)).row();

        infoTable.add(new Label("Product Quality:", skin)).left();
        infoTable.add(new Label(animal.isProductQuality() ? "High" : "Normal", skin)).row();

        infoTable.add(new Label("Fed:", skin)).left();
        infoTable.add(new Label(animal.isFeed() ? "Yes" : "No", skin)).row();

        infoTable.add(new Label("Collected:", skin)).left();
        infoTable.add(new Label(animal.isCollect() ? "Yes" : "No", skin)).row();
        TextButton backBtn     = new TextButton("back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AnimalContextMenu menu = new AnimalContextMenu(animalView, skin);
                menu.pack();
                Vector2 uiPos = getStage().screenToStageCoordinates(new Vector2(
                    Gdx.input.getX(), Gdx.input.getY()
                ));
                menu.setPosition(uiPos.x, uiPos.y);
                getStage().addActor(menu);
                remove();
                event.stop();
            }
        });
        // اضافه کردن infoTable به Window
        add(infoTable).pad(10).row();
        add(backBtn).fillX().row();
    }
}
