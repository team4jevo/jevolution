package gamelogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents creature whose conditions of survival are more favorable
 * if creature has less alive neighbors.
 * @author aigars
 *
 */
public class CreatureNonDependant extends Creature implements SurvivalInstinct {
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();
    
    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception("Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CreatureNonDependant.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CreatureNonDependant.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception("Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CreatureNonDependant.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CreatureNonDependant.neighborNonLocality;
    }

    @Override
    public boolean survives(ArrayList<Creature> liveNeighbors) {
        boolean state = false;
        if (this.getStatus()) {
         // Creature favors less alive neighbors around but dies if they are above 3
            if (liveNeighbors.size() == 1 || liveNeighbors.size() == 2) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 2 neighbors can bring him back to life
            if (liveNeighbors.size() == 2) {
                state = true;
            }
        }
        int sameType = 0;
        // Determine number of creatures nearby of the same type
        for (Creature creature : liveNeighbors) {
            if (CreatureNonDependant.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p = 0
        double p = 0.0;
        if (sameType != 0 && CreatureNonDependant.cooperativeness != 0) {
            double mu = CreatureNonDependant.K * sameType * Math.sqrt(CreatureNonDependant.cooperativeness);
            p =  CreatureNonDependant.random.nextGaussian() * CreatureNonDependant.SIGMA + mu;
        }
        if (CreatureNonDependant.random.nextDouble() < p) {
            state = true;
        }
        return state;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    }

}
