package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.some_example_name.model.Animal.AnimalKind;

public class AnimalProducts {
    private TextureRegion region;
    private int price;
    public AnimalProducts(AnimalKind kind,boolean quality) {
        if(quality){
            this.region = new TextureRegion(new Texture("Content (unpacked)/AnimalProducts/egg.png"));
            this.price = kind.getPriceOfSecondProduct();
        }else {
            this.region = new TextureRegion(new Texture("Content (unpacked)/AnimalProducts/egg.png"));
            this.price = kind.getPriceOfFirstProduct();
        }
    }

    public TextureRegion getRegion() {
        return region;
    }

    public int getPrice() {
        return price;
    }
}
