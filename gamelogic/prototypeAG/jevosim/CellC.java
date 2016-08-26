package jevosim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates CellC object that implements <SurvivalInstinct> interface. Cell
 * objects of this type have survival conditions that are higher in value than
 * that those in original Game of Life. Class has additional static fields whose
 * values affect probability of survival and reincarnation (state change) of
 * Cell object. Static field <aggressiveness> affects the likelihood of CellC
 * object being replaced by other type of Cell object. Static field
 * <cooperativeness> affects the likelihood of CellC object's overridden method
 * <survives> to return true. Static field <neighborNonLocality> affects the
 * fraction of adjacent cells and random cells out of total of 8 cells that will
 * be checked for Cell objects with live states.
 * 
 * @author aigars
 *
 */
public class CellC extends Cell implements SurvivalInstinct {

    private static int agressiveness;
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();

    public CellC(int x, int y)
            throws IllegalArgumentException, IllegalAccessException {
        super("CD", x, y);
    }

    public static int getAggressiveness() {
        return agressiveness;
    }

    public static void setAggressiveness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellC.agressiveness = value;
    }

    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellC.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CellC.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception(
                    "Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CellC.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CellC.neighborNonLocality;
    }

    @Override
    public boolean survives(ArrayList<Cell> liveNeighbors) {
        boolean state = false;
        if (this.getState()) {
            // Creature favors more alive neighbors around but dies if they are
            // below 3
            if (liveNeighbors.size() == 3 || liveNeighbors.size() == 4) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 4 neighbors can bring him back to
            // life
            if (liveNeighbors.size() == 4) {
                state = true;
            }
        }
        int sameType = 0;
        // Determine number of creatures nearby of the same type
        for (Cell creature : liveNeighbors) {
            if (CellC.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of
        // cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p =
        // 0
        double p = 0.0;
        if (sameType != 0 && CellC.cooperativeness != 0) {
            double mu = CellB.K * sameType * Math.sqrt(CellC.cooperativeness);
            p = CellC.random.nextGaussian() * CellC.SIGMA + mu;
        }
        if (CellC.random.nextDouble() < p) {
            state = true;
        }
        return state;
    }

}