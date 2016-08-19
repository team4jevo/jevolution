package gamelogic;


import java.util.HashMap;
import java.util.Random;


/**
 * Wrapper class for game logic. Pass Grid object in constructor
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
    
    /**
     * Initializes GameObject 2D array with according to indicated type parameter.
     * type parameter should have following values:
     *     1) default - evenly generates three type of creatures
     *     2) classic
     *     3) dependant
     *     4) nondependant
     *     5) mix 
     * @param type
     * @throws Exception
     */
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
        case "dependant":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    CreatureDependant cd = new CreatureDependant();
                    cd.setStatus(random.nextBoolean());
                    cd.setLocation(j, i);
                    this.grid.addObject(cd, j, i);
                }
            }
            break;
        case "nondependant":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setStatus(random.nextBoolean());
                    cnd.setLocation(j, i);
                    this.grid.addObject(cnd, j, i);
                }
            }
            break;
        case "mix":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    int n = random.nextInt(2);
                    switch(n) {
                    case 0:
                        CreatureDependant cd = new CreatureDependant();
                        cd.setStatus(random.nextBoolean());
                        cd.setLocation(j, i);
                        this.grid.addObject(cd, j, i);
                        break;
                    case 1:
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
            break;
        default:
            break;
        }
    }
    
    /**
     * Method for retrieving length of the grid.
     * @return
     */
    public int getBoardLength() {
        return this.grid.getX();
    }
    
    /**
     * Method for retrieving width of the grid.
     * @return
     */
    public int getBoardWidth() {
        return this.grid.getY();
    }
    
    /**
     * Introduces next generation in simulation.
     */
    public void nextTurn() {
        this.grid.update();
    }
    
    /**
     * Returns 2D GameObjects array containing objects of the simulation 
     * @return
     */
    public GameObject[][] getGridObjects() {
        return this.grid.getGameObjects();
    }
    
    /**
     * Returns map with creature type and number of creatures alive
     * @return
     */
    public HashMap<String, Integer> getNumberOfAliveCreatures() {
        return this.grid.getNumberOfAliveCreatures();
    }
    
}