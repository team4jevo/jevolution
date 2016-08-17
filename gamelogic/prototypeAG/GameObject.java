package jevolution;

import java.awt.Point;


/**
 * GameObject class is an abstract class which is inherited by Food and Creature classes
 * in order to allow creation of 2D array that stores instances of Food and Creature classes.
 * @author Aigars Gridjusko
 *
 */

abstract class GameObject {
    protected Point location;
    abstract void setLocation(int x, int y);
    abstract Point getLocation();
    abstract String getType();
}
