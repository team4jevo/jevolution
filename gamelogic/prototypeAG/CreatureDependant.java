package gamelogic;

/**
 * Class represents creature whose conditions of survival are more favorable
 * if creature has more alive neighbors.
 * @author aigars
 *
 */
public class CreatureDependant extends Creature implements SurvivalInstinct {
    
    @Override
    public boolean survives(int nNeighbors) {
        boolean state = false;
        if (this.getStatus()) {
         // Creature favors more alive neighbors around but dies if they are below 3
            if (nNeighbors == 3 || nNeighbors == 4) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 4 neighbors can bring him back to life
            if (nNeighbors == 4) {
                state = true;
            }
        }
        return state;
    }

}
