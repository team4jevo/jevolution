package gamelogic;

import java.util.ArrayList;


interface SurvivalInstinct {
    boolean survives(ArrayList<LocalCreature> liveNeighbors);
}
