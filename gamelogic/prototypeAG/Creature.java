package gamelogic;

import java.awt.Point;
import java.util.Random;


class Creature extends GameObject {
    static final double SIGMA = 0.01;
    static final double K = 0.03162277660168379;
    private boolean isAlive;

    Creature() {
        this.isAlive = true;
    }

    public void setStatus(boolean status) {
        this.isAlive = status;
    }

    public boolean getStatus() {
        return this.isAlive;
    }

    @Override
    public void setLocation(int x, int y) {
        this.location = new Point(x, y);
    }

    @Override
    public Point getLocation() {
        return this.location;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

}
