package io.github.some_example_name.model.Animal;

import java.util.HashMap;

public enum AnimalProduct {
    EGG("egg",1),
    LARGE_EGG("large egg",1),
    DUCK_EGG("duck egg",2),
    DUCK_FEATHER("duck feather",2),
    RABBIT_WOOL("rabbite wool",4),
    RABBIT_FOOT("rabbit foot",4),
    DINOSAUR_EGG("dinosaur egg",7),
    MILK("milk",1),
    LARGE_MILK("large milk",1),
    GOAT_MILK("goat milk",2),
    LARGE_GOATMILK("large goat milk",2),
    SHEEP_WOOL("sheep wool",3),
    TRUFFLE("truffle",1);
    ;
    private final String name;
    private final int productTime;

    AnimalProduct( String name,int productTime) {
this.name = name;
        this.productTime = productTime;

    }

    public int getProductTime() {
        return productTime;
    }

    public String getName() {
        return name;
    }

    //TODO

}
