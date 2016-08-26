package jevosim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates CellB object that implements <SurvivalInstinct> interface. Cell
 * objects of this type have survival conditions that are lower in value than
 * that those in original Game of Life. Class has additional static fields whose
 * values affect probability of survival and reincarnation (state change) of
 * Cell object. Static field <aggressiveness> affects the likelihood of CellB
 * object being replaced by other type of Cell object. Static field
 * <cooperativeness> affects the likelihood of CellB object's overridden method
 * <survives> to return true. Static field <neighborNonLocality> affects the
 * fraction of adjacent cells and random cells out of total of 8 cells that will
 * be checked for Cell objects with live states.
 * 
 * @author aigars
 *
 */
public class CellB extends Cell implements SurvivalInstinct {

    private static int agressiveness;
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();

    public CellB(int x, int y)
            throws IllegalArgumentException, IllegalAccessException {
        super("CND", x, y);
    }

    public static int getAggressiveness() {
        return agressiveness;
    }

    public static void setAggressiveness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellB.agressiveness = value;
    }

    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception(
                    "Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CellB.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CellB.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception(
                    "Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CellB.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CellB.neighborNonLocality;
    }

    @Override
    public boolean survives(ArrayList<Cell> liveNeighbors) {
        boolean state = false;
        if (this.getState()) {
            // Creature favors less alive neighbors around but dies if they are
            // above 3
            if (liveNeighbors.size() == 1 || liveNeighbors.size() == 2) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 2 neighbors can bring him back to
            // life
            if (liveNeighbors.size() == 2) {
                state = true;
            }
        }
        int sameType = 0;
        // Determine number of creatures nearby of the same type
        for (Cell creature : liveNeighbors) {
            if (CellB.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of
        // cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p =
        // 0
        double p = 0.0;
        if (sameType != 0 && CellB.cooperativeness != 0) {
            double mu = CellB.K * sameType * Math.sqrt(CellB.cooperativeness);
            p = CellB.random.nextGaussian() * CellB.SIGMA + mu;
        }
        if (CellB.random.nextDouble() < p) {
            state = true;
        }
        return state;
    }

}
