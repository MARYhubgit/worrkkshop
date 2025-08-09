package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.views.GameView;

import io.github.some_example_name.model.Animal.AnimalKind;

import java.util.List;
import java.util.ArrayList;


public class AnimalView extends Actor {
    public static final float HEIGHT = 50f;
    private static final float WIDTH   = 50f;
    private static final float OFFSET_X = (WIDTH  - 32f) / 2f; // اگر وسط کاشی باشد
    private static final float OFFSET_Y = (HEIGHT - 32f) / 2f;


    private Vector2 position;         // موقعیت فعلی
    private Vector2 targetPosition;   // مقصد فعلی
    private Vector2 direction;        // بردار واحد جهت حرکت
    private float speed = 50f;


    private float x, y;
    private TextureRegion texture;
//    private float tileWidth, tileHeight;
    private Rectangle bounds;
    private TextureRegion region;
    private Animal animalModel;


    private boolean isShepherded = false;
    private Vector2 shepherdTarget;
    boolean isActive;
    TextureRegion producTex;

    private Animation<TextureRegion> feedAnim;
    private float feedTimer = 0f;
    private boolean isFeeding = false;
private boolean isCollect=false;

    public AnimalView(float x, float y, Animal animalModel) {
        this.animalModel = animalModel;
        this.x = x;
        this.y = y;
//        this.tileWidth  = tileWidth;
//        this.tileHeight = tileHeight;
        this.region = new TextureRegion(new Texture("Content (unpacked)/Animals/"+animalModel.getAnimalKind().getType()+".png"));
        this.bounds = new Rectangle(x, y, WIDTH, HEIGHT);
        this.position = new Vector2(x, y);
        this.producTex = new TextureRegion(new Texture("Content (unpacked)/AnimalProducts/egg.png"));
        chooseNewTarget();
    }


    public boolean containsPoint(float px, float py) {
        return bounds.contains(px, py);
    }

    public void render(Batch batch) {
        batch.draw(region,position.x, position.y,
            bounds.width, bounds.height);

        if(isCollect){
            batch.draw(producTex,
                position.x + WIDTH/2f - 16,    // وسط حیوان
                position.y + HEIGHT,          // بالای سر حیوان
                32, 32);
        }


        if (isFeeding) {
            Gdx.app.log("ANIMAL_FEED", "rendering feed frame, timer=" + feedTimer);
            TextureRegion frame = feedAnim.getKeyFrame(feedTimer, false);
            batch.draw(frame,
                position.x + WIDTH/2f - 16,    // وسط حیوان
                position.y + HEIGHT,          // بالای سر حیوان
                32, 32);
        }

    }

    private void chooseNewTarget() {
        // یک زاویهٔ تصادفی از ۰ تا 2π
        float angle = MathUtils.random(0f, MathUtils.PI2);
        // نقطهٔ جدید در فاصلهٔ ۵ واحد
        float dx = MathUtils.cos(angle) * 5f;
        float dy = MathUtils.sin(angle) * 5f;
        targetPosition = new Vector2(position.x + dx, position.y + dy);
        // بردار جهت واحد
        direction = targetPosition.cpy().sub(position).nor();
    }

    // فراخوانی وقتی چوپانی‌اش می‌کنی
    public void startShepherding(Vector2 target) {
        this.isShepherded = true;
        this.shepherdTarget = target.cpy();
    }
    public void stopShepherding() {
        this.isShepherded = false;
    }


    public void update(float delta) {
        animalModel.hasProducted();
        if(isShepherded){
            // سمت هدف حرکت کن
            Vector2 dir = shepherdTarget.cpy().sub(position).nor();
            float distance = position.dst(shepherdTarget);
            if (distance > speed * delta) {
                position.mulAdd(dir, speed * delta);
            } else {
                position.set(shepherdTarget);
                stopShepherding();
                chooseNewTarget();      // بعد از رسیدن دوباره پیاده‌روی تصادفی
            }
        }else{
            // فاصلهٔ باقی‌مانده تا مقصد
            float distanceRemaining = position.dst(targetPosition);

            // اگر هنوز نرسیده‌ایم:
            if (distanceRemaining > speed * delta) {
                // یک گام کوچک بردار با توجه به سرعت و delta
                position.mulAdd(direction, speed * delta);
            } else {
                // اگر با این گام برسد یا رد کند، مستقیماً مقصد را بگذار
                position.set(targetPosition);
                // و یک مقصد جدید انتخاب کن
                chooseNewTarget();
            }
        }
        // به‌روز‌رسانی bounds برای تشخیص کلیک و رندر
        bounds.setPosition(position.x, position.y);

        if (isFeeding) {
            feedTimer += delta;
            float extraTime = 1.5f;
            if (feedTimer > feedAnim.getAnimationDuration()+extraTime) {
                isFeeding = false;
            }
        }



    }


    public Rectangle getBounds() {
        return bounds;
    }

    public void feed() {
        animalModel.feed(false);
        isFeeding = true;
        feedTimer = 0f;
        Gdx.app.log("ANIMAL_FEED", "started feeding at " + position);
    }

    public void collectProduct() {
        isCollect=animalModel.collectProduct();
    }
    public void sell() {
        // مثلاً animal.sell() یا فراخوانی کنترلر برای فروش
    }

    // متدهای action:
    public void pet() {
        animalModel.pet();
//        TextureRegion heartRegion = new TextureRegion(new Texture("ui/heart.png"));
//        HeartEffect heart = new HeartEffect(heartRegion, position.x, position.y);
//        GameView.getUiStage().addActor(heart);
    }

    public void release() {
        System.out.println("in release");
        GameView.releaseAllBarnAnimals();
    }
    // getterها:
    public float getX() { return x; }
    public float getY() { return y; }
    public TextureRegion getTexture() { return texture; }
    public Vector2 getPosition() {
        return position.cpy();
    }

    public Animal getAnimalModel() {
        return animalModel;
    }

    public void setFeedAnimation(Animation<TextureRegion> anim) {
        this.feedAnim = anim;
    }
}
