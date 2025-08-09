package io.github.some_example_name.views.Graphic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlantView {

    public static final float HEIGHT = 50f;
    private static final float WIDTH   = 50f;

    private float waterEffectTimer = 0f;
    private static final float WATER_EFFECT_DURATION = 2f;

    private float filterEffectTimer = 0f;
    private static final float FILTER_EFFECT_DURATION = 2f;
    // ۱. تصاویر ثابتی که پیش‌فرض بارگذاری می‌شوند
    private final Texture tillTex  = new Texture(Gdx.files.internal("plants/graphics/till.png"));
    private final Texture seedTex  = new Texture(Gdx.files.internal("plants/graphics/seed.png"));
    private final Texture waterTex = new Texture(Gdx.files.internal("plants/graphics/water.png"));
    private final Texture fertTex  = new Texture(Gdx.files.internal("plants/graphics/fert.png"));
    private final Texture harvTex  = new Texture(Gdx.files.internal("plants/graphics/harvest.png"));

    // ۲. تصویر نهایی گیاه
    private TextureRegion matureRegion;

    // ۳. تصویر مرحلهٔ رشد جاری
    private TextureRegion growthRegion;

    // ۴. مدل و موقعیت
    private final Plant    model;
    private final Vector2 position;
private boolean isCollect=false;
private boolean isActive=true;

    TextureRegion producTex;

    public PlantView(Plant model, float x, float y) {
        this.model    = model;
        this.position = new Vector2(x, y);
        this.producTex = new TextureRegion(new Texture("Content (unpacked)/AnimalProducts/egg.png"));
        loadMatureTexture();
    }

    private void loadMatureTexture() {
        String name = model.getCropType().getName().toLowerCase();
        matureRegion = new TextureRegion(
            new Texture(Gdx.files.internal("plants/carrot_mature.png"))
        );
    }

    public void update(float delta) {
        model.hasProducted();
        // مدل اندیس مرحله را محاسبه می‌کند و growthTexture را می‌سازد
        model.stateIn();

        if (waterEffectTimer > 0f) {
            waterEffectTimer -= delta;
        }
        if (filterEffectTimer > 0f) {
            filterEffectTimer -= delta;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    /** رسم گیاه بر اساس state مدل */
    public void render(Batch batch) {
        try {
            // ابتدا تنسور رشد جاری از مدل بگیر
            Texture growthTex = model.getGrowthTexture();

            switch (model.getState()) {
                case SEEDED:
                    batch.draw(growthTex, position.x, position.y);
                case GROWING:
                    // اگر growthTexture تنظیم شده باشد، رسمش کن
                    if (growthTex != null) {
                        batch.draw(
                            growthTex,
                            position.x, position.y
                        );
                    }
                    break;

                case MATURE:
                    // تصویر بلوغ
                    batch.draw(
                        matureRegion,
                        position.x, position.y
                    );
                    break;

            }
            if (waterEffectTimer > 0f) {
                batch.draw(
                    waterTex,
                    position.x-2, position.y + /* دلخواه ارتفاع بالای گیاه */ 10
                );
            }
            if (filterEffectTimer > 0f) {
                batch.draw(
                    fertTex,
                    position.x-2, position.y + /* دلخواه ارتفاع بالای گیاه */ 10
                );
            }

            if(isCollect && isActive){
                batch.draw(producTex,
                    position.x + WIDTH/2f - 16,    // وسط حیوان
                    position.y + HEIGHT,          // بالای سر حیوان
                    32, 32);

                if(model.getCropType().isOneTime()){
                    isActive=false;
                }
            }

        } catch (Exception e) {
            Gdx.app.error("PlantView", "render error", e);
        }


    }
    public void doWater() {
        // راه‌اندازی تایمرِ ۳ ثانیه‌ای
        waterEffectTimer = WATER_EFFECT_DURATION;

    }
    public void doFilter() {
        filterEffectTimer = FILTER_EFFECT_DURATION;
    }
public void collectProduct() {
        isCollect=model.collectProduct();
    System.out.println(isCollect);
}
    public Plant getModel() {
        return model;
    }
}


