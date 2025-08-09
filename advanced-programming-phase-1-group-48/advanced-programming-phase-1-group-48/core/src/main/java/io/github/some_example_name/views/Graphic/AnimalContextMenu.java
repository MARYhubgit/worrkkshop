package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class AnimalContextMenu extends Window {
    public AnimalContextMenu(AnimalView animalView, Skin skin) {
        super("Actions", skin);

        TextButton petBtn     = new TextButton("Pet", skin);
        TextButton feedBtn    = new TextButton("Feed", skin);
        TextButton collectBtn = new TextButton("Collect Product", skin);
        TextButton releaseBtn = new TextButton("Release", skin);
        TextButton sellBtn    = new TextButton("Sell", skin);
        TextButton Ip= new TextButton("IP", skin);
        TextButton backBtn     = new TextButton("back", skin);

        petBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // 1) منطق نوازش مدل
                animalView.pet();
                // 2) جلوه‌ی بصری قلب
                TextureRegion heartRegion = new TextureRegion(
                    new Texture("Content (unpacked)/Action/heart.png")
                );
                // موقعیت دقیق قلب را از AnimalView بگیر
                Vector2 pos = animalView.getPosition();
                HeartEffect heart = new HeartEffect(heartRegion, pos.x, pos.y);
                // به همون Stage ای که منو روشه اضافه کن
                getStage().addActor(heart);
                // 3) حذف منو
                remove();
                event.stop();  // مانع انتشار به پردازش‌گرهای بعدی
            }
        });

        feedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animalView.feed();
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });
        collectBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animalView.collectProduct();
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });
        releaseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animalView.release();
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });
        sellBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animalView.sell();
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });
        Ip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
                    new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));
                AnimalInfoMenu menu = new AnimalInfoMenu(animalView,otherSkin);
                menu.pack();
                Vector2 uiPos = getStage().screenToStageCoordinates(new Vector2(
                    Gdx.input.getX(), Gdx.input.getY()
                ));
                menu.setPosition(uiPos.x, uiPos.y);
                getStage().addActor(menu);
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                event.stop();  // <— اینجوری Stage کلیک رو مصرف می‌کنه و GameView نمی‌فهمه‌
            }
        });

        add(petBtn).fillX().row();
        add(feedBtn).fillX().row();
        add(collectBtn).fillX().row();
        add(releaseBtn).fillX().row();
        add(sellBtn).fillX().row();
        add(Ip).fillX().row();
        add(backBtn).fillX();
        pack();
    }
}
