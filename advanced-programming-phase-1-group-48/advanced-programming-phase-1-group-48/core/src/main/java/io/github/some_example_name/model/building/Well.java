package io.github.some_example_name.model.building;


import io.github.some_example_name.model.game.Position;

public class Well extends Buildings {
    public Well(Position topLeft) {
        super("Well", topLeft, 1, 1);
    }

    @Override
    public void interact() {

    }

}
