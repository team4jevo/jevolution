package jevosim;

import java.awt.Point;
import java.util.*;

/**
 * Creates <CellGrid> object containing methods that comprise core game logic of
 * simulation type of evolution. It has two two dimensional arrays as private
 * instance variables that control the location and inner state of <Cell>
 * objects. <CellGrid> object has methods that can add, return single
 * <GameObject> objects, return whole two dimensional array of <GameObject>
 * objects, return map with <Cell> type objects and corresponding number of
 * objects that have a positive inner state.
 * 
 * @author aigars
 *
 */
public class CellGrid {
    private GameObject[][] gameObjects;
    private boolean[][] nextState;
    private Random random;
    private HashMap<Class<?>, Integer> aliveCells;

    /**
     * Creates CellGrid object.
     * 
     * @param x
     *            length of 2d array representing x axis. Value must be in range
     *            [5, 100].
     * @param y
     *            length of 2d array representing y axis. Value must be in range
     *            [5, 100].
     * @throws Exception
     *             Throws exception if invalid values have been provided.
     */
    public CellGrid(int x, int y) throws Exception {
        if (x < 5 || x > 100 || y < 5 || y > 100) {
            throw new Exception(
                    "Invalid parameters to Grid constructor provided. x and y must be in range [5, 100].");
        }
        this.gameObjects = new GameObject[y][x];
        this.nextState = new boolean[y][x];
        this.random = new Random();
        this.aliveCells = new HashMap<Class<?>, Integer>();
    }

    /**
     * Returns length of array that represents horizontal x axis in a two
     * dimensional space
     * 
     * @return length of array
     */
    public int getX() {
        return this.gameObjects[0].length;
    }

    /**
     * Returns length of array that represents vertical y axis in a two
     * dimensional space
     * 
     * @return length of array
     */
    public int getY() {
        return this.gameObjects.length;
    }

    /**
     * Returns <GameObject> object located in two dimensional <GameObject> array
     * at specified x and y indices.
     * 
     * @param x
     *            index of object's location in array representing horizontal x
     *            axis.
     * @param y
     *            index of object's location in array representing vertical y
     *            axis.
     * @return <GameObject> object at specified x, y position in two dimensional
     *         <GameObject> array.
     * @throws Exception
     *             thrown if passed parameters are out of bounds of two
     *             dimensional <GameObject> array.
     */
    public GameObject getGameObject(int x, int y) throws Exception {
        if (x < 0 || x >= this.getX() || y < 0 || y >= this.getY()) {
            throw new Exception("Invalid indexes provided!");
        }
        return this.gameObjects[y][x];
    }

    public HashMap<Class<?>, Integer> getAliveCells() {
        return this.aliveCells;
    }

    /**
     * Adds <GameObject> object at specified position in two dimensional
     * <GameObject> array.
     * 
     * @param object
     *            <GameObject> object to be added in two dimensional
     *            <GameObject> array.
     * @param x
     *            x coordinate of the object. Corresponds to index [..][x] in 2d
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects[0].length - 1].
     * @param y
     *            y coordinate of the object. Corresponds to index [y][..] in 2d
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects.length - 1].
     * @throws Exception
     *             thrown exception if invalid x and y values are provided, cell
     *             is already taken or 2d array containing game objects is full.
     */
    public void addObject(GameObject object) throws Exception {
        // Get x and y coordinates from object
        int objX = object.getLogicX();
        int objY = object.getLogicY();
        // If x or y is out of range, throw exception
        if (objX < 0 || objX >= this.getX() || objY < 0
                || objY >= this.getY()) {
            throw new Exception("Array index out of bounds!");
        }
        if (!this.isFull()) {
            if (this.gameObjects[objY][objX] != null) {
                throw new Exception("Cannot add element in x=" + objX + ", y="
                        + objY + ". Cell taken.");
            }
            Cell cell = (Cell) object;
            if (cell.getState()) {
                if (aliveCells.containsKey(cell.getClass())) {
                    aliveCells.put(cell.getClass(),
                            aliveCells.get(cell.getClass()) + 1);
                } else {
                    aliveCells.put(cell.getClass(), 1);
                }
            }
            this.gameObjects[objY][objX] = object;
        } else {
            throw new Exception("Cannot add more elements. Array is full.");
        }
    }

    /**
     * Reinitializes 2D <GameObject> array, reinitializes map containing Cell
     * objects whose state is positive (true) and reinitializes 2D <boolean>
     * array that stores states of <GameObject> objects in next generation.
     */
    public void reset() {
        this.gameObjects = new GameObject[this.getY()][this.getX()];
        this.aliveCells = new HashMap<>();
        this.nextState = new boolean[this.getY()][this.getX()];
    }

    /**
     * Replaces <GameObject> object ar specified <x> and <y> position in 2D
     * <GameObject> array with passed <GameObject> object.
     * 
     * @param x
     *            coordinate of the object. Corresponds to index [..][x] in 2D
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects[0].length - 1].
     * 
     * @param y
     *            y coordinate of the object. Corresponds to index [y][..] in 2D
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects.length - 1].
     * @param newGameObject
     *            <GameObject> object to be places at specified <x> and <y>
     *            position in 2D <GameObject> array.
     */
    public void setGameObject(int x, int y, GameObject newGameObject) {
        this.gameObjects[y][x] = newGameObject;
    }

    /**
     * Checks if 2D array containing game objects is full.
     * 
     * @return true if 2D array containing game objects is full, false
     *         otherwise.
     */
    private boolean isFull() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                if (this.gameObjects[y][x] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Updates status of each Cell object according to rules that govern the
     * survival of cell.
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void update()
            throws IllegalArgumentException, IllegalAccessException {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                if (this.gameObjects[y][x] != null) {
                    GameObject currObject = this.gameObjects[y][x];
                    // If class of current game object is Creature class or one
                    // of the
                    // sub classes of Creature class
                    if (Cell.class.isInstance(currObject)) {
                        Cell cr = (Cell) currObject;
                        boolean statusAfter = false;
                        boolean statusBefore = cr.getState();
                        // Get neighbors for creature, both local and non-local
                        // depending on level of locality of
                        // a particular group
                        ArrayList<Cell> neighborCount;
                        int locality = 0;
                        int creaturesAggressiveness = 0;
                        if (CellA.class.isInstance(cr)) {
                            locality = CellA.getNeighborNonLocality();
                            creaturesAggressiveness = CellA.getAggressiveness();
                        }
                        if (CellB.class.isInstance(cr)) {
                            locality = CellB.getNeighborNonLocality();
                            creaturesAggressiveness = CellB.getAggressiveness();
                        }
                        if (CellC.class.isInstance(cr)) {
                            locality = CellC.getNeighborNonLocality();
                            creaturesAggressiveness = CellC.getAggressiveness();
                        }
                        neighborCount = this.getLivingNeighbors(x, y, locality);
                        if (CellA.class.isInstance(cr)) {
                            CellA creature = (CellA) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CellC.class.isInstance(cr)) {
                            CellC creature = (CellC) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CellB.class.isInstance(cr)) {
                            CellB creature = (CellB) currObject;
                            statusAfter = creature.survives(neighborCount);
                        }
                        // If most frequent creature around is of other type
                        String mostFrequent = mostFrequentCreatureAsNeighbor(
                                neighborCount);
                        if (!cr.getGoType().equals(mostFrequent)) {
                            // Get corresponding aggressiveness of most common
                            // neighbor type
                            int aggressiveness = 0;
                            Cell newCreature = null;
                            if (CellA.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CellA.getAggressiveness();
                                newCreature = new CellA(x, y);
                            }
                            if (CellC.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CellC.getAggressiveness();
                                newCreature = new CellC(x, y);
                            }
                            if (CellB.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CellB.getAggressiveness();
                                newCreature = new CellB(x, y);
                            }
                            // If creature is less aggressive than most frequent
                            // neighbors
                            if (creaturesAggressiveness < aggressiveness) {
                                // Calculate probability that it will turn
                                double p = (aggressiveness
                                        - creaturesAggressiveness) / 10.0;
                                // If random value in range [0.0, 1.0) is less
                                // than probability
                                if (random.nextDouble() < p) {
                                    // Replace creature
                                    newCreature.setState(statusAfter);
                                    newCreature.setLogicX(x);
                                    newCreature.setLogicY(y);
                                    this.gameObjects[y][x] = newCreature;
                                    if (!statusBefore) {
                                        if (statusAfter) {
                                            aliveCells.put(
                                                    newCreature.getClass(),
                                                    aliveCells
                                                            .get(newCreature
                                                                    .getClass())
                                                            + 1);
                                        }
                                    } else {
                                        if (!statusAfter) {
                                            aliveCells
                                                    .put(cr.getClass(),
                                                            aliveCells
                                                                    .get(cr.getClass())
                                                                    - 1);
                                        } else {
                                            aliveCells
                                                    .put(cr.getClass(),
                                                            aliveCells
                                                                    .get(cr.getClass())
                                                                    - 1);
                                            aliveCells.put(
                                                    newCreature.getClass(),
                                                    aliveCells
                                                            .get(newCreature
                                                                    .getClass())
                                                            + 1);
                                        }
                                    }
                                } else {
                                    if (!statusBefore) {
                                        if (statusAfter) {
                                            aliveCells
                                                    .put(cr.getClass(),
                                                            aliveCells
                                                                    .get(cr.getClass())
                                                                    + 1);
                                        }
                                    } else {
                                        if (!statusAfter) {
                                            aliveCells
                                                    .put(cr.getClass(),
                                                            aliveCells
                                                                    .get(cr.getClass())
                                                                    - 1);
                                        }
                                    }
                                }
                            } else {
                                if (!statusBefore) {
                                    if (statusAfter) {
                                        aliveCells.put(cr.getClass(),
                                                aliveCells.get(cr.getClass())
                                                        + 1);
                                    }
                                } else {
                                    if (!statusAfter) {
                                        aliveCells.put(cr.getClass(),
                                                aliveCells.get(cr.getClass())
                                                        - 1);
                                    }
                                }
                            }
                        } else {
                            if (!statusBefore) {
                                if (statusAfter) {
                                    aliveCells.put(cr.getClass(),
                                            aliveCells.get(cr.getClass()) + 1);
                                }
                            } else {
                                if (!statusAfter) {
                                    aliveCells.put(cr.getClass(),
                                            aliveCells.get(cr.getClass()) - 1);
                                }
                            }
                        }
                        this.nextState[y][x] = statusAfter;
                    }
                }
            }
        }
        this.setNextState();
    }

    /**
     * Returns <String> that represents type of <Cell> object that is most
     * frequent as neighbor out of total of 8 <Cell> objects.
     * 
     * @param liveNeighbors
     *            <ArrayList> of <Cells> which are screened as neighbors and
     *            whose state is positive (true).
     * @return String representing <Cell> type that is the most frequent among
     *         screened neighbors.
     */
    private String mostFrequentCreatureAsNeighbor(
            ArrayList<Cell> liveNeighbors) {
        HashMap<String, Integer> neighborMap = new HashMap<>();
        for (Cell creature : liveNeighbors) {
            String type = creature.getGoType();
            if (!neighborMap.containsKey(type)) {
                neighborMap.put(type, 1);
            } else {
                neighborMap.put(type, neighborMap.get(type) + 1);
            }
        }
        // Determine most frequent creature as neighbor
        int max = 0;
        String mostFrequent = null;
        for (String type : neighborMap.keySet()) {
            if (neighborMap.get(type) > max) {
                max = neighborMap.get(type);
                mostFrequent = type;
            }
        }
        return mostFrequent;
    }

    /**
     * Returns 2D array containing game objects
     * 
     * @return 2d array with game objects
     */
    public GameObject[][] getGameObjects() {
        return this.gameObjects;
    }

    /**
     * Returns number of living creatures located in both local and non-local
     * cells relative to cell with coordinates (x, y)
     * 
     * @param x
     *            x coordinate of <Cell> object relative to which local and
     *            non-local cells are checked
     * @param y
     *            y coordinate of <Cell> object relative to which local and
     *            non-local cells are checked
     * @param locality
     *            Determines how many unique local and non-local cells should be
     *            checked. Value provided must be in range [0, 8]. 0 checks only
     *            local cells, 8 checks only non-local cells. 4 checks 4 random
     *            local cells and 4 random non-local cells.
     * @return <ArrayList> object containing neighboring <Cell> objects whose
     *         state is positive.
     */
    private ArrayList<Cell> getLivingNeighbors(int x, int y, int locality) {
        // List that contains local directions that should should be checked
        ArrayList<Integer> directions = new ArrayList<>();
        assert (locality >= 0
                && locality <= 8) : "Invalid input. Paramater (locality) should be in range [0, 8].";
        ArrayList<Cell> ans = new ArrayList<>();
        // Randomly determine local unique cells which should be checked
        while (directions.size() < 8 - locality) {
            int randomDirection = random.nextInt(8);
            if (!directions.contains(randomDirection)) {
                directions.add(randomDirection);
            }
        }
        // Determine number of local living neighbors
        ans.addAll(neighborsInDirections(x, y, directions));
        // Determine number of non-local living neighbors
        ans.addAll(nonLocalNeighbors(x, y, locality));
        return ans;
    }

    /**
     * Returns <ArrayList> of <Cell> objects with positive state that are a
     * fraction of or all 8 adjacent <Cell> objects around a <Cell> object with
     * coordinates (x, y).
     * 
     * @param x
     *            x coordinate of cell relative to which local cells are
     *            checked.
     * @param y
     *            y coordinate of cell relative to which local cells are
     *            checked.
     * @param directions
     *            list containing directions that determine which local cells
     *            relative to the cell should be checked.
     * @return number of living creatures.
     */
    private ArrayList<Cell> neighborsInDirections(int x, int y,
            ArrayList<Integer> directions) {
        ArrayList<Cell> ans = new ArrayList<>();
        for (int i = 0; i < directions.size(); i++) {
            int currentDirection = directions.get(i);
            switch (currentDirection) {
            case 0:
                // Check right side
                if (x != this.getX() - 1
                        && this.gameObjects[y][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 1:
                // Check top right corner
                if (x < this.getX() - 1 && y > 0
                        && this.gameObjects[y - 1][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 2:
                // Check top
                if (y > 0 && this.gameObjects[y - 1][x] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 3:
                // Check top left corner
                if (x > 0 && y > 0 && this.gameObjects[y - 1][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 4:
                // Check left side
                if (x > 0 && this.gameObjects[y][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 5:
                // Check cells on left bottom corner
                if (x > 0 && y != this.getY() - 1
                        && this.gameObjects[y + 1][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 6:
                // Check cells on bottom
                if (y != this.getY() - 1
                        && this.gameObjects[y + 1][x] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 7:
                // Check cells on bottom right
                if (x != this.getX() - 1 && y != this.getY() - 1
                        && this.gameObjects[y + 1][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            default:
                break;
            }
        }
        return ans;
    }

    /**
     * Returns <ArrayList> of <Cell> objects that have positive inner state.
     * Method randomly checks <n> unique cells in the 2D <GameObject> grid and
     * returns list of cells relative to cell with coordinates (x, y).
     * 
     * @param x
     *            x coordinate of cell relative to which random non-local
     *            neighbors are checked.
     * @param y
     *            y coordinate of cell relative to which random non-local
     *            neighbors are checked.
     * @param n
     *            number of random cells that should be checked.
     * @return number of living creatures in checked cells.
     */
    private ArrayList<Cell> nonLocalNeighbors(int x, int y, int n) {
        ArrayList<Cell> ans = new ArrayList<>();
        ArrayList<Point> control = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (true) {
                int randY = random.nextInt(this.getY());
                int randX = random.nextInt(this.getX());
                // If random location is not one of the local cells or cell
                // itself
                if (!((randX == x && randY == y)
                        || (randX == x && randY == y - 1)
                        || (randX == x && randY == y + 1)
                        || (randX == x + 1 && randY == y)
                        || (randX == x - 1 && randY == y)
                        || (randX == x + 1 && randY == y + 1)
                        || (randX == x - 1 && randY == y - 1)
                        || (randX == x - 1 && randY == y + 1)
                        || (randX == x + 1 && randY == y - 1))) {
                    // Current point
                    Point currentPoint = new Point(randX, randY);
                    // If there is no such point in control list
                    if (!control.contains(currentPoint)) {
                        // Add this point to control list
                        control.add(currentPoint);
                        // Get creature
                        Cell creature = (Cell) this.gameObjects[randY][randX];
                        // If creature is alive, add 1 to count
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                        // Break from loop to look for next random neighbor
                        break;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * Sets next state for <Cell> objects in 2D <GameOBject> array determined by
     * the current state of the <Cell> object and surviving conditions.
     */
    private void setNextState() {
        for (int y = 0; y < this.nextState.length; y++) {
            for (int x = 0; x < this.nextState[0].length; x++) {
                if (this.gameObjects[y][x] != null) {
                    GameObject currObject = this.gameObjects[y][x];
                    if (!currObject.getGoType().equals("Food")) {
                        Cell creature = (Cell) currObject;
                        if (creature.getState() != this.nextState[y][x]) {
                            creature.setState(this.nextState[y][x]);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        int x = 5, y = 5;
        CellGrid grid = new CellGrid(x, y);
        while (!grid.isFull()) {
            grid.addObject(new CellA(x, y));
        }

        // Test nonLocalNeighbors method
        for (int i = 0; i < 9; i++) {
            System.out.println(grid.nonLocalNeighbors(1, 1, i).size());
        }
        System.out.println();

        // Test neighborsInDirections method
        ArrayList<Integer> direction = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            direction.add(i);
            System.out.println(
                    grid.neighborsInDirections(1, 1, direction).size());
        }
    }

}
