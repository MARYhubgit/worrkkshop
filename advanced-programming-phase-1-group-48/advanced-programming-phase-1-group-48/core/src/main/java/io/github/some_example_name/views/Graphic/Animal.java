package io.github.some_example_name.views.Graphic;

import io.github.some_example_name.model.Animal.AnimalKind;
import io.github.some_example_name.model.Weather.DateAndTime;

public class Animal {
    private AnimalKind animalKind;
    private String name;
    private static int friendship;
    private boolean productQuality;
    private boolean isPut;
    private boolean isFeed;
    private boolean isCollect;
    private int birth;
    private boolean hasProduct=false;

    public Animal(AnimalKind animalKind) {
        this.animalKind = animalKind;
        this.friendship = 0;
        this.productQuality = false;
        this.isPut = false;
        this.isFeed = false;
        this.isCollect = false;
        this.birth = DateAndTime.getTotalDays();
    }


    public void pet(){
        System.out.println("friendship: " + friendship);
        friendship+=15;
        isPut=true;
    }

    public boolean collectProduct() {
        if(hasProduct){
            isCollect=true;
            friendship+=10;
            if(friendship>100 && animalKind.getSecondProduct()!=null){
                productQuality=true;
            }
            hasProduct=false;
            new AnimalProducts(animalKind,productQuality);
            // اضافه کردن محصولات
            return true;
        }else {
            System.out.println("Nothing to collect");
            return false;
        }

    }

    public void hasProducted() {
        int time=0;
        if(productQuality){
            time=animalKind.getSecondProduct().getProductTime();
        }else {
            time=animalKind.getFirstProduct().getProductTime();
        }

        if((DateAndTime.getTotalDays()-birth)%time==0){
            hasProduct=true;
        }

    }
    public void feed(boolean inOut){
        isFeed=true;
        if(inOut){
            friendship+=8;
        }
    }

    public AnimalKind getAnimalKind() {
        return animalKind;
    }

    public String getName() {
        return name;
    }

    public static int getFriendship() {
        return friendship;
    }

    public boolean isProductQuality() {
        return productQuality;
    }

    public boolean isPut() {
        return isPut;
    }

    public boolean isFeed() {
        return isFeed;
    }

    public boolean isCollect() {
        return isCollect;
    }


}
