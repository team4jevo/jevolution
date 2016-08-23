package gamelogic;



/**
 * GameObject class is an abstract class which is inherited by Food and Creature classes
 * in order to allow creation of 2D array that stores instances of Food and Creature classes.
 * @author Aigars Gridjusko
 *
 */

public abstract class LocalGameObject {
    public abstract void setXCoord(int x);
    public abstract void setYCoord(int y);
    public abstract int getXCoord();
    public abstract int getYCoord();
    public abstract String getType();
}
