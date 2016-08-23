package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Wrapper class for Grid object.
 * 
 * @author Aigars Gridjusko
 *
 */
public class GameLogic {
    private Grid grid;
    private Random random;
    private String gameType;

    public GameLogic(Grid grid, String gameType) {
        this.random = new Random();
        this.grid = grid;
        this.gameType = gameType;
        try {
            this.initializeGrid(this.gameType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeGrid() throws Exception {
        this.grid.reset();
        for (int i = 0; i < this.grid.getY(); i++) {
            for (int j = 0; j < this.grid.getX(); j++) {
                int n = random.nextInt(3);
                switch (n) {
                case 0:
                    CreatureSimple cs = new CreatureSimple();
                    cs.setStatus(random.nextBoolean());
                    cs.setXCoord(j);
                    cs.setYCoord(i);
                    this.grid.addObject(cs, j, i);
                    break;
                case 1:
                    CreatureDependant cd = new CreatureDependant();
                    cd.setStatus(random.nextBoolean());
                    cd.setXCoord(j);
                    cd.setYCoord(i);
                    this.grid.addObject(cd, j, i);
                    break;
                case 2:
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setStatus(random.nextBoolean());
                    cnd.setXCoord(j);
                    cnd.setYCoord(i);
                    this.grid.addObject(cnd, j, i);
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * Fills 2D array containing game objects with game objects according to
     * indicated parameter. Type parameter should have following values: 1)
     * default - evenly generates three types of creatures; classic - generates
     * creatures whose survival is governed by classic rules of Game of Life;
     * dependant - generates creatures who need more living neighbors to survive
     * and revive; nondependant - generates creatures who need less living
     * neighbors to survive and revive; mix1 - generates evenly both dependant
     * and non-dependant creatures; mix2 - generates evenly both dependant and
     * simple creatures; mix3 - generates evenly both non-dependant and simple
     * creatures.
     * 
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
                    cs.setXCoord(j);
                    cs.setYCoord(i);
                    this.grid.addObject(cs, j, i);
                }
            }
            break;
        case "dependant":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    CreatureDependant cd = new CreatureDependant();
                    cd.setStatus(random.nextBoolean());
                    cd.setXCoord(j);
                    cd.setYCoord(i);
                    this.grid.addObject(cd, j, i);
                }
            }
            break;
        case "nondependant":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    CreatureNonDependant cnd = new CreatureNonDependant();
                    cnd.setStatus(random.nextBoolean());
                    cnd.setXCoord(j);
                    cnd.setYCoord(i);
                    this.grid.addObject(cnd, j, i);
                }
            }
            break;
        case "mix1":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    int n = random.nextInt(2);
                    switch (n) {
                    case 0:
                        CreatureDependant cd = new CreatureDependant();
                        cd.setStatus(random.nextBoolean());
                        cd.setXCoord(j);
                        cd.setYCoord(i);
                        this.grid.addObject(cd, j, i);
                        break;
                    case 1:
                        CreatureNonDependant cnd = new CreatureNonDependant();
                        cnd.setStatus(random.nextBoolean());
                        cnd.setXCoord(j);
                        cnd.setYCoord(i);
                        this.grid.addObject(cnd, j, i);
                        break;
                    default:
                        break;
                    }
                }
            }
            break;
        case "mix2":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    int n = random.nextInt(2);
                    switch (n) {
                    case 0:
                        CreatureDependant cd = new CreatureDependant();
                        cd.setStatus(random.nextBoolean());
                        cd.setXCoord(j);
                        cd.setYCoord(i);
                        this.grid.addObject(cd, j, i);
                        break;
                    case 1:
                        CreatureSimple cs = new CreatureSimple();
                        cs.setStatus(random.nextBoolean());
                        cs.setXCoord(j);
                        cs.setYCoord(i);
                        this.grid.addObject(cs, j, i);
                        break;
                    default:
                        break;
                    }
                }
            }
            break;
        case "mix3":
            for (int i = 0; i < this.grid.getY(); i++) {
                for (int j = 0; j < this.grid.getX(); j++) {
                    int n = random.nextInt(2);
                    switch (n) {
                    case 0:
                        CreatureNonDependant cnd = new CreatureNonDependant();
                        cnd.setStatus(random.nextBoolean());
                        cnd.setXCoord(j);
                        cnd.setYCoord(i);
                        this.grid.addObject(cnd, j, i);
                        break;
                    case 1:
                        CreatureSimple cs = new CreatureSimple();
                        cs.setStatus(random.nextBoolean());
                        cs.setXCoord(j);
                        cs.setYCoord(i);
                        this.grid.addObject(cs, j, i);
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
     * 
     * @return length of the grid representing x axis
     */
    public int getBoardLength() {
        return this.grid.getX();
    }

    /**
     * Method for retrieving width of the grid.
     * 
     * @return length of grid representing y axis
     */
    public int getBoardWidth() {
        return this.grid.getY();
    }

    /**
     * Determines and sets next state of every creature.
     */
    public void nextTurn() {
        this.grid.update();
    }

    /**
     * Returns 2D game object array containing objects of the simulation
     * 
     * @return 2d game object array
     */
    public LocalGameObject[][] getGridObjects() {
        return this.grid.getGameObjects();
    }
    
    public String getGameType() {
        return this.gameType;
    }
    
    public void setGameType(String gameType) throws Exception {
        this.gameType = gameType;
        this.initializeGrid(this.gameType);
    }
    
    public void newGame() throws Exception {
        this.initializeGrid(this.gameType);
    }

    /**
     * Returns map with creature type and number of creatures alive
     * 
     * @return
     */
    public HashMap<String, ArrayList<LocalCreature>> getNumberOfAliveCreatures() {
        return this.grid.getNumberOfAliveCreatures();
    }

    public void setNonLocality(String simpleClassName, int value) throws Exception {
        if (CreatureSimple.class.getSimpleName().equals(simpleClassName)) {
            CreatureSimple.setNeighborNonLocality(value);
        }
        if (CreatureDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureDependant.setNeighborNonLocality(value);
        }
        if (CreatureNonDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureNonDependant.setNeighborNonLocality(value);
        }
    }
    
    public void setCooperativeness(String simpleClassName, int value) throws Exception {
        if (CreatureSimple.class.getSimpleName().equals(simpleClassName)) {
            CreatureSimple.setCooperativeness(value);
        }
        if (CreatureDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureDependant.setCooperativeness(value);
        }
        if (CreatureNonDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureNonDependant.setCooperativeness(value);
        }
    }
    
    public void setAggressiveness(String simpleClassName, int value) throws Exception {
        if (CreatureSimple.class.getSimpleName().equals(simpleClassName)) {
            CreatureSimple.setAggressiveness(value);
        }
        if (CreatureDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureDependant.setAggressiveness(value);
        }
        if (CreatureNonDependant.class.getSimpleName().equals(simpleClassName)) {
            CreatureNonDependant.setAggressiveness(value);
        }
    }

}