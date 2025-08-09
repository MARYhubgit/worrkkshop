package io.github.some_example_name.model.user;

public class EnergySystem {
    private int energy = 200;
    private boolean unlimited = false;
    private boolean passedOut = false;

    public void consume(int amount) {}

    public void resetDailyEnergy() {}

    public void setUnlimited(boolean value) {
        this.unlimited = value;
    }

    public void setEnergy(int value) {
        this.energy = value;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isPassedOut() {
        return passedOut;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void decreaseEnergy(int amount) {}
}
