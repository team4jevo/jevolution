package jevosim;

/**
 * Creates Cell object that represents more specific game object containing
 * additional private field <isAlive> that represents state of the cell.
 * 
 * @author aigars
 *
 */
public class Cell extends GameObject {
    // Standard deviation when generating probability of positive state change
    // of a Cell object
    static final double SIGMA = 0.01;
    // Parameter used when calculation probability of positive state change of a
    // Cell object
    static final double K = 0.03162277660168379;
    private boolean isAlive;

    public Cell(String type, int x, int y)
            throws IllegalArgumentException, IllegalAccessException {
        super(type, x, y);
        this.isAlive = true;
    }

    public boolean getState() {
        return isAlive;
    }

    public void setState(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
