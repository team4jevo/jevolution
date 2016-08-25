package jevosim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents creature whose conditions of survival are more favorable
 * if creature has more alive neighbors.
 * @author aigars
 *
 */
public class CreatureDependant extends LocalCreature implements SurvivalInstinct {

    private static int agressiveness;
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();
    
    public CreatureDependant(int x, int y) throws IllegalArgumentException, IllegalAccessException {
        super("CD", x, y);
    }
    
    public static int getAggressiveness() {
        return agressiveness;
    }

    public static void setAggressiveness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception("Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CreatureDependant.agressiveness = value;
    }

    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception("Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CreatureDependant.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CreatureDependant.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception("Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CreatureDependant.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CreatureDependant.neighborNonLocality;
    }
    
    @Override
    public boolean survives(ArrayList<LocalCreature> liveNeighbors) {
        boolean state = false;
        if (this.getState()) {
         // Creature favors more alive neighbors around but dies if they are below 3
            if (liveNeighbors.size() == 3 || liveNeighbors.size() == 4) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 4 neighbors can bring him back to life
            if (liveNeighbors.size() == 4) {
                state = true;
            }
        }
        int sameType = 0;
        // Determine number of creatures nearby of the same type
        for (LocalCreature creature : liveNeighbors) {
            if (CreatureDependant.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p = 0
        double p = 0.0;
        if (sameType != 0 && CreatureDependant.cooperativeness != 0) {
            double mu = CreatureNonDependant.K * sameType * Math.sqrt(CreatureDependant.cooperativeness);
            p =  CreatureDependant.random.nextGaussian() * CreatureDependant.SIGMA + mu;
        }
        if (CreatureDependant.random.nextDouble() < p) {
            state = true;
        }
        return state;
    }

}