package io.github.some_example_name.model.Reaction;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Reaction {
    private TextureRegion texture; // تصویر ایموجی (می‌تونه null باشه اگر فقط متن باشه)
    private String message;        // پیام متنی واکنش
    private float timer;           // تایمر برای حذف خودکار

    public Reaction(TextureRegion texture, String message) {
        this.texture = texture;
        this.message = message;
        this.timer = 5f;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public String getMessage() {
        return message;
    }

    public void update(float delta) {
        timer -= delta;
    }

    public boolean isExpired() {
        return timer <= 0;
    }
}
