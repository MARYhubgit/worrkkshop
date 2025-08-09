package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.model.Plant.Enums.CropType;
import io.github.some_example_name.model.Weather.DateAndTime;

public class Plant {
public enum State {
    SEEDED,
    GROWING,
    MATURE;
}
    private CropType cropType;
    private int birthDay;
    private Texture growthTexture;
    private State state;
    private boolean hasProduct=false;
    private boolean isCollect=false;

    public Plant(CropType cropType) {
        this.cropType = cropType;
        birthDay = DateAndTime.getTotalDays()+1;
        state = State.SEEDED;
    }



    public void stateIn() {
        int today     = DateAndTime.getTotalDays();
        int daysSince = today - birthDay;    // چند روز از کاشت گذشته
        int[] durations = cropType.getStages(); // {1,2,2,2}

        // 1) بسازیم یک آرایهٔ تجمعی:
        int[] thresholds = new int[durations.length];
        int sum = 0;
        for (int i = 0; i < durations.length; i++) {
            sum += durations[i];
            thresholds[i] = sum;
        }
        // حالا thresholds = {1,3,5,7}

        // 2) پیدا کنیم daysSince رو در کدوم مرحله است
        int stageIndex = thresholds.length;  // اگر فراتر از همه بودیم → بالغ
        for (int i = 0; i < thresholds.length; i++) {
            if (daysSince <= thresholds[i]) {
                stageIndex = i;
                break;
            }
        }

        // 3) بارگذاری تکسچر مطابق stageIndex
        if (stageIndex < thresholds.length) {
            growthTexture = new Texture(
                Gdx.files.internal("plants/growth/" + stageIndex + ".png")
            );
        } else {
            // بالغ
            String name = cropType.getName().toLowerCase();
            growthTexture = new Texture(
                Gdx.files.internal("plants/carrot_mature.png")
            );
        }
    }

    public boolean collectProduct() {
        if(hasProduct){
            isCollect=true;
            hasProduct=false;
            return true;
        }else {
            System.out.println("Nothing to collect");
            return false;
        }

    }
    public void hasProducted() {
        int time=0;
        if(cropType.isOneTime()){
            time=cropType.getTotalHarvestTime();
        }else {
            time=cropType.getRegrowthTime();
        }

        if((DateAndTime.getTotalDays()-(birthDay+cropType.getTotalHarvestTime()))%time==0){
            hasProduct=true;
        }

    }


    public Texture getGrowthTexture() {
        return growthTexture;
    }

    public State getState() {
        return state;
    }

    public CropType getCropType() {
        return cropType;
    }
}
