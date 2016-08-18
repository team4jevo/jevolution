package gamelogic;

/**
 * Class represents creature whose conditions of survival are the original
 * conditions of survival from Game of Life.
 * @author aigars
 *
 */
public class CreatureSimple extends Creature implements SurvivalInstinct {

    @Override
    public boolean survives(int nNeighbors) {
        boolean status = false;
        // Default Game of Life survival conditions
        if (this.getStatus()) {
            if (nNeighbors == 2 || nNeighbors == 3) {
                status = true;
            }
        } else {
            // If creature is dead but has exactly 3 alive neighbors
            if (nNeighbors == 3) {
                // Creature revives
                status = true;
            }
        }
        return status;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
