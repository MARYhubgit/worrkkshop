package io.github.some_example_name.model.Animal;


import io.github.some_example_name.model.Animal.AnimalProduct;

public enum AnimalKind {
    COW("cow", "barn", 4, 1500,
        AnimalProduct.MILK, 125,
        AnimalProduct.LARGE_MILK, 190),

    GOAT("goat", "barn", 8, 4000,
        AnimalProduct.GOAT_MILK, 225,
        AnimalProduct.LARGE_GOATMILK, 345),

    SHEEP("sheep", "barn", 12, 8000,
        AnimalProduct.SHEEP_WOOL, 340, null, 0),

    PIG("pig", "barn", 4, 16000,
        AnimalProduct.TRUFFLE, 625, null, 0),

    CHICKEN("chicken", "coop", 4, 800,
        AnimalProduct.EGG, 50,
        AnimalProduct.LARGE_EGG, 95),

    DUCK("duck", "coop", 8, 1200,
        AnimalProduct.DUCK_EGG, 95,
        AnimalProduct.DUCK_FEATHER, 250),

    RABBIT("rabbit", "coop", 12, 8000,
        AnimalProduct.RABBIT_WOOL, 340,
        AnimalProduct.RABBIT_FOOT, 565),

    DINOSAUR("dinosaur", "coop", 8, 14000,
        AnimalProduct.DINOSAUR_EGG, 350, null, 0);

    private final String type;
    private final String animalKind;
    private final int capacityOfHome;
    private final int priceOfBuy;
    private final AnimalProduct firstProduct;
    private final int priceOfFirstProduct;
    private final AnimalProduct secondProduct;
    private final int priceOfSecondProduct;

    AnimalKind(String type, String animalKind, int capacityOfHome, int priceOfBuy,
               AnimalProduct firstProduct, int priceOfFirstProduct,
               AnimalProduct secondProduct, int priceOfSecondProduct) {
        this.type = type;
        this.animalKind = animalKind;
        this.capacityOfHome = capacityOfHome;
        this.priceOfBuy = priceOfBuy;
        this.firstProduct = firstProduct;
        this.priceOfFirstProduct = priceOfFirstProduct;
        this.secondProduct = secondProduct;
        this.priceOfSecondProduct = priceOfSecondProduct;
    }

    public int getPriceOfBuy() {
        return priceOfBuy;
    }

    public String getAnimalKind() {
        return animalKind;
    }

    public AnimalProduct getFirstProduct() {
        return firstProduct;
    }

    public int getPriceOfFirstProduct() {
        return priceOfFirstProduct;
    }

    public AnimalProduct getSecondProduct() {
        return secondProduct;
    }

    public int getPriceOfSecondProduct() {
        return priceOfSecondProduct;
    }

    public String getType() {
        return type;
    }
}
