package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.model.building.Barn;

import java.util.ArrayList;

public class BarnView {
    public static final float HEIGHT = 100f;
    private static final float WIDTH   = 100f;
    private Vector2 position;         // موقعیت فعلی
    private float x, y;
    private TextureRegion texture;
    //    private float tileWidth, tileHeight;
    private Rectangle bounds;
    private TextureRegion region;
    private Barn barnModel;
    private Stage barnStage;

    ArrayList<AnimalView> animals = new ArrayList<>();
    public BarnView(float x, float y, Barn barnModel) {
        barnStage = new Stage(new ScreenViewport());
        this.barnModel = barnModel;
        this.x = x;
        this.y = y;
        System.out.println("barnModel: " + barnModel.getBarnType().getName()+"+++++");
        this.region = new TextureRegion(new Texture("Content (unpacked)/Buildings/"+barnModel.getBarnType().getName()+".png"));
        this.bounds = new Rectangle(x, y, WIDTH, HEIGHT);
        this.position = new Vector2(x, y);
    }


    public void update(float delta) {

    }


    public Rectangle getBounds() {
        return bounds;
    }
public void addAnimal(AnimalView animal) {
        animals.add(animal);
        barnStage.addActor(animal);
}
    public Stage getStage() {
        return barnStage;
    }
    public void dispose() {
        barnStage.dispose(); // بسیار مهم
    }
    public boolean containsPoint(float px, float py) {
        return bounds.contains(px, py);
    }

    public void render(Batch batch) {
        batch.draw(region,position.x, position.y,
            bounds.width, bounds.height);
    }

    public Vector2 getPosition() {
        return position;
    }
}
