package jevosim;


public class LocalCreature extends jevo.GameObject {
    static final double SIGMA = 0.01;
    static final double K = 0.03162277660168379;
    private boolean isAlive;

    public LocalCreature(String type, int x, int y) {
        super (type, x, y);
        this.isAlive = true;
    }

    public boolean getState() {
        return isAlive;
    }

    public void setState(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
