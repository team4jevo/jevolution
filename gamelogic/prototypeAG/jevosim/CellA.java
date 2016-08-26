package jevosim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates CellA object that implements <SurvivalInstinct> interface. Cell
 * objects of this type have survival conditions as in original Game of Life
 * simulation. Class has additional static fields whose values affect
 * probability of survival and reincarnation (state change) of Cell object.
 * Static field <aggressiveness> affects the likelihood of CellA object being
 * replaced by other type of Cell object. Static field <cooperativeness> affects
 * the likelihood of CellA object's overridden method <survives> to return true.
 * Static field <neighborNonLocality> affects fraction of adjacent cells and
 * random cells out of total of 8 cells that will be checked for Cell objects
 * with live states.
 * 
 * @author aigars
 *
 */
public class CellA extends Cell implements SurvivalInstinct {
    private static int aggressiveness;
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();

    public CellA(int x, int y)
            throws IllegalArgumentException, IllegalAccessException {
        super("CS", x, y);
    }

    public static int getAggressiveness() {
        return aggressiveness;
    }

    public static void setAggressiveness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellA.aggressiveness = value;
    }

    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellA.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CellA.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception(
                    "Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CellA.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CellA.neighborNonLocality;
    }

    @Override
    public boolean survives(ArrayList<Cell> liveNeighbors) {
        boolean state = false;
        // Default Game of Life survival conditions
        if (this.getState()) {
            if (liveNeighbors.size() == 2 || liveNeighbors.size() == 3) {
                state = true;
            }
        } else {
            // If creature is dead but has exactly 3 alive neighbors
            if (liveNeighbors.size() == 3) {
                // Creature revives
                state = true;
            }
        }
        int sameType = 0;
        // Determine number of creatures nearby of the same type
        for (Cell creature : liveNeighbors) {
            if (CellA.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of
        // cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p =
        // 0
        double p = 0.0;
        if (sameType != 0 && CellA.cooperativeness != 0) {
            double mu = CellA.K * sameType * Math.sqrt(CellA.cooperativeness);
            p = CellA.random.nextGaussian() * CellA.SIGMA + mu;
        }
        if (CellA.random.nextDouble() < p) {
            state = true;
        }

        return state;
    }

}
