package gamelogic;

/**
 * Class represents creature whose conditions of survival are more favorable
 * if creature has less alive neighbors.
 * @author aigars
 *
 */
class CreatureNonDependant extends Creature implements SurvivalInstinct {

    @Override
    public boolean survives(int nNeighbors) {
        boolean state = false;
        if (this.getStatus()) {
         // Creature favors less alive neighbors around but dies if they are above 3
            if (nNeighbors == 1 || nNeighbors == 2) {
                state = true;
            }
        } else {
            // If creature is dead, exactly 2 neighbors can bring him back to life
            if (nNeighbors == 2) {
                state = true;
            }
        }
        return state;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    }

}
