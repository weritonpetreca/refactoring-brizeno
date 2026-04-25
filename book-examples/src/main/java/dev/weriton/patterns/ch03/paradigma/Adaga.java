package dev.weriton.patterns.ch03.paradigma;

public class Adaga implements Arma {

    @Override
    public int getDano() {
        return 10;
    }

    @Override
    public int getBonusVelocidade() {
        return 3;
    }
}
