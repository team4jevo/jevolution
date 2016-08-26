package jevosim;

import java.util.ArrayList;

/**
 * Purpose of this interface is to unify types of <GameObject>
 * objects that could express survival instinct. This interface makes implementing
 * classes to define survival criteria based on number and type of <Cell>
 * objects identified as <Cell> objects neighbors.
 * 
 * @author aigars
 *
 */
interface SurvivalInstinct {
    boolean survives(ArrayList<Cell> liveNeighbors);
}
