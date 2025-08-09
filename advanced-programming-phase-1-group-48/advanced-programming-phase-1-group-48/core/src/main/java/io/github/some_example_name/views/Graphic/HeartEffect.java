package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class HeartEffect extends Image {
    public HeartEffect(TextureRegion heartRegion, float x, float y) {
        super(heartRegion);
        setPosition(x, y + AnimalView.HEIGHT);
        setOrigin(Align.center);
        setScale(0f);
        addAction(Actions.sequence(
            Actions.scaleTo(1f, 1f, 0.2f),
            Actions.delay(0.5f),
            Actions.moveBy(0f, 30f, 0.5f),
            Actions.fadeOut(0.5f),
            Actions.removeActor()
        ));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // ۱) نگهداری رنگ قبلی batch
        Color prev = batch.getColor().cpy();
        // ۲) کشیدن قلب با آلفای خودش
        super.draw(batch, parentAlpha);
        // ۳) بازگردانی رنگِ قبلی تا بقیهٔ Actors بدون تغییر کشیده بشوند
        batch.setColor(prev);
    }
}
