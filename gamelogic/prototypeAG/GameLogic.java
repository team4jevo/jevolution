package gamelogic;


import java.util.HashMap;
import java.util.Random;


/**
 * Wrapper class for game logic.
 * @author student
 *
 */
public class GameLogic {
    private Grid grid;
    private Random random;
    
    public GameLogic(Grid grid) {
        this.random = new Random();
        this.grid = grid;
        try {
            this.initializeGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initializeGrid() throws Exception {
        for (int i = 0; i < this.grid.getY(); i++) {
            for (int j = 0; j < this.grid.getX(); j++) {
                int n = random.nextInt(3);
                switch(n) {
                case 0:
                    CreatureSimple cs = new CreatureSimple();
                    cs.setStatus(random.nextBoolean());
                    cs.setLocation(j, i);
                    this.grid.addObject(cs, j, i);
                    break;
                case 1:
                    CreatureDependant cd = new CreatureDependant();
                    cd.setStatus(random.nextBoolean());
                    cd.setLocation(j, i);
                    this.grid.addObject(cd, j, i);
                    break;
                case 2:
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setStatus(random.nextBoolean());
                    cnd.setLocation(j, i);
                    this.grid.addObject(cnd, j, i);
                    break;
                default:
                    break;
                }
            }
        }
    }
    
    public void initializeGrid(String type) throws Exception {
        this.grid.reset();
        switch (type) {
        case "default":
            this.initializeGrid();
            break;
        case "classic":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    CreatureSimple cs = new CreatureSimple();
                    cs.setStatus(random.nextBoolean());
                    cs.setLocation(j, i);
                    this.grid.addObject(cs, j, i);
                }
            }
            break;
        default:
            break;
        }
        
        
    }
    
    public int getBoardLength() {
        return this.grid.getX();
    }
    
    public int getBoardWidth() {
        return this.grid.getY();
    }
    
    public void nextTurn() {
        this.grid.update();
    }
    
    public GameObject[][] getGridObjects() {
        return this.grid.getGameObjects();
    }
    
    public HashMap<String, Integer> getNumberOfAliveCreatures() {
        return this.grid.getNumberOfAliveCreatures();
    }
    
}
