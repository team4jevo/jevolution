package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class TestGrid {
    
    static void printAliveCreatures(HashMap<String, ArrayList<LocalCreature>> map) {
        for (String type : map.keySet()) {
            System.out.print(type + ":" + map.get(type).size() + "\t");
        }
        System.out.println();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Grid grid = new Grid(10, 10);
        Random random = new Random();
        for (int y = 0; y < grid.getY(); y++) {
            for (int x = 0; x < grid.getX(); x++) {
                int type = random.nextInt(3);
                switch (type) {
                case 0:
                    CreatureSimple cs = new CreatureSimple();
                    cs.setXCoord(x);
                    cs.setYCoord(y);
                    cs.setStatus(random.nextBoolean());
                    grid.addObject(cs, x, y);
                    break;
                case 1:
                    CreatureDependant cd = new CreatureDependant();
                    cd.setXCoord(x);
                    cd.setYCoord(y);
                    cd.setStatus(random.nextBoolean());
                    grid.addObject(cd, x, y);
                    break;
                case 2:
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setXCoord(x);
                    cnd.setYCoord(y);
                    cnd.setStatus(random.nextBoolean());
                    grid.addObject(cnd, x, y);
                    break;
                default:
                    break;
                }
            }
        }
        HashMap<String, ArrayList<LocalCreature>> aliveCreatures = grid.getNumberOfAliveCreatures();
        System.out.println("-------------------------- Initial state -------------------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        System.out.println("------------------------------------------------------------------");
        grid.printGrid();
        for (int i = 0; i < 100; i++) {
            grid.update();
        }
        System.out.println("-------------------------- After 100 steps -----------------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        System.out.println("------------------------------------------------------------------");
        grid.printGrid();
    }

}