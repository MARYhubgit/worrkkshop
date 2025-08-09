package io.github.some_example_name.model.Reaction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class EmojiManager {
    private static final int EMOJI_COUNT = 153;
    private static Array<TextureRegion> emojis = new Array<>();

    public static void loadEmojis() {
        for (int i = 0; i < EMOJI_COUNT; i++) {
            String path = "Emoji/Emojis" + String.format("%03d", i) + ".png";
            Texture texture = new Texture(Gdx.files.internal(path));
            emojis.add(new TextureRegion(texture));
        }
    }

    public static TextureRegion getEmoji(int index) {
        if (index < 0 || index >= emojis.size) return null;
        return emojis.get(index);
    }
}

