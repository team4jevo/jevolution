package gamelogic;

import java.util.HashMap;
import java.util.Random;

class TestGrid {
    
    static void printAliveCreatures(HashMap<String, Integer> map) {
        for (String type : map.keySet()) {
            System.out.print(type + ":" + map.get(type) + "\t");
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
                    cs.setLocation(x, y);
                    cs.setStatus(random.nextBoolean());
                    grid.addObject(cs, x, y);
                    break;
                case 1:
                    CreatureDependant cd = new CreatureDependant();
                    cd.setLocation(x, y);
                    cd.setStatus(random.nextBoolean());
                    grid.addObject(cd, x, y);
                    break;
                case 2:
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setLocation(x, y);
                    cnd.setStatus(random.nextBoolean());
                    grid.addObject(cnd, x, y);
                    break;
                default:
                    break;
                }
            }
        }
        HashMap<String, Integer> aliveCreatures = grid.getNumberOfAliveCreatures();
        System.out.println("--------------- Initial state --------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        grid.printGrid();
        grid.update();
        System.out.println("--------------- Post update 1 --------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        grid.printGrid();
        grid.update();
        System.out.println("--------------- Post update 2 --------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        grid.printGrid();
        grid.update();
        System.out.println("--------------- Post update 3 --------------");
        TestGrid.printAliveCreatures(aliveCreatures);
        grid.printGrid();
    }

}
