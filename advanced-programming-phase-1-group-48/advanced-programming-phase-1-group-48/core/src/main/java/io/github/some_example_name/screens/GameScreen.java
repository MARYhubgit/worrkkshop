package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    public GameScreen(Main game) {
        System.out.println("GameScreen constructor");
        this.game = game;
    }

    @Override
    public void show() {
        batch = Main.getBatch();
        // دوربین و ویوپورت
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        // بارگذاری Assetها (مثلاً تکسچرها، مش‌ها یا tiled map)
        GameAssetManager assets = GameAssetManager.getGameAssetManager();
        if (assets == null) {
            System.out.println("AssetManager is null");
        }

        Texture playerTexture = assets.getCharacter1_idle_animation().getKeyFrame(0);

    }

    @Override
    public void render(float delta) {
        // پاک‌سازی صفحه
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // دوربین را به‌روز کن
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // رسم زمین، ساختمان‌ها، گیاهان و بازیکن:
        // assets.getTexture("farm-tile").draw(batch, x, y);
        // …
        batch.end();

        // اگر Stage یا InputProcessor داری:
        // stage.act(delta);
        // stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        // در اینجا نیازی به batch.dispose نیست چون Shared است
        // اما اگر tiledMap یا stage دارید باید dispose کنید
    }
}
