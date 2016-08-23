package gamelogic2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents creature whose conditions of survival are the original
 * conditions of survival from Game of Life.
 * @author aigars
 *
 */
public class CreatureSimple extends LocalCreature implements SurvivalInstinct {

    private static int agressiveness;
    private static int cooperativeness;
    private static int neighborNonLocality;
    private static Random random = new Random();
    
    public CreatureSimple(int x, int y) {
        super(CreatureSimple.class.getSimpleName(), x, y);
    }
    
    public static int getAggressiveness() {
        return agressiveness;
    }

    public static void setAggressiveness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception("Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CreatureSimple.agressiveness = value;
    }
    
    public static void setCooperativeness(int value) throws Exception {
        if (value < 0 || value > 10) {
            throw new Exception("Invalid value provided. Value of cooperativeness must be in range [0, 10].");
        }
        CreatureSimple.cooperativeness = value;
    }

    public static int getCooperativeness() {
        return CreatureSimple.cooperativeness;
    }

    public static void setNeighborNonLocality(int value) throws Exception {
        if (value < 0 || value > 8) {
            throw new Exception("Invalid value provided. Value of locality must be in range [0, 8].");
        }
        CreatureSimple.neighborNonLocality = value;
    }

    public static int getNeighborNonLocality() {
        return CreatureSimple.neighborNonLocality;
    }

    @Override
    public boolean survives(ArrayList<LocalCreature> liveNeighbors) {
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
        for (LocalCreature creature : liveNeighbors) {
            if (CreatureSimple.class.isInstance(creature)) {
                sameType++;
            }
        }
        // Calculate probability of survival depending on level of cooperativeness and number of
        // alive neighbors around of the same type
        // If no neighbors of same type are around or cooperativeness is 0, p = 0
        double p = 0.0;
        if (sameType != 0 && CreatureSimple.cooperativeness != 0) {
            double mu = CreatureSimple.K * sameType * Math.sqrt(CreatureSimple.cooperativeness);
            p =  CreatureSimple.random.nextGaussian() * CreatureSimple.SIGMA + mu;
        }
        if (CreatureSimple.random.nextDouble() < p) {
            state = true;
        }
        
        return state;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
