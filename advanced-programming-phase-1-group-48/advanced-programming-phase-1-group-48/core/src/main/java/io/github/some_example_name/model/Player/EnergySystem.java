package io.github.some_example_name.model.Player;

public class EnergySystem {
    private int maxEnergy = 200;
    private double energy = 200;
    private boolean unlimited = false;
    private boolean passedOut = false;

    public double consume(double requested) {
        if (unlimited || requested <= 0) return requested;

        double consumed = Math.min(energy, requested);
        energy -= consumed;

        if (energy == 0) {
            passedOut = true;
        }

        return consumed;
    }



    public void resetDailyEnergy() {
        if (passedOut) {
            energy = (int) (maxEnergy * 0.75);
        } else {
            energy = maxEnergy;
        }
        passedOut = false;
    }
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy =200 ;
    }

    public int getMaxEnergy() {
        return  maxEnergy;
    }
    public void setUnlimited(boolean value) {
        this.unlimited = value;
    }


    public void setEnergy(int value) {
        this.maxEnergy = value;
        this.energy = value;
    }

    public int getEnergy() {
        return (int)energy;
    }



    public boolean isPassedOut() {
        return passedOut;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void decreaseEnergy(int amount) {
        consume(amount);
    }

    public void increaseEnergy(int amount) {
        if (unlimited || amount > maxEnergy) return;
        energy = Math.min(maxEnergy, energy + amount);
    }
}
