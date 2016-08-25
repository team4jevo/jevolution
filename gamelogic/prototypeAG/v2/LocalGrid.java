package jevosim;

import java.awt.Point;
import java.util.*;

public class LocalGrid {
    private GameObject[][] gameObjects;
    private boolean[][] nextState;
    private Random random;

    /**
     * Grid constructor.
     * 
     * @param x
     *            length of 2d array representing x axis. Value must be in range
     *            [5, 100]
     * @param y
     *            length of 2d array representing y axis. Value must be in range
     *            [5, 100]
     * @throws Exception
     *             Throws exception if invalid values have been provided
     */
    public LocalGrid(int x, int y) throws Exception {
        if (x < 5 || x > 100 || y < 5 || y > 100) {
            throw new Exception(
                    "Invalid parameters to Grid constructor provided. x and y must be in range [5, 100].");
        }
        this.gameObjects = new GameObject[y][x];
        this.nextState = new boolean[y][x];
        this.random = new Random();
    }

    public int getX() {
        return this.gameObjects[0].length;
    }

    public int getY() {
        return this.gameObjects.length;
    }

    /**
     * Method returns game object in certain position indicated by provided
     * parameters x and y
     * 
     * @param x
     * @param y
     * @return
     * @throws Exception
     */
    public GameObject getGameObject(int x, int y) throws Exception {
        if (x < 0 || x >= this.getX() || y < 0 || y >= this.getY()) {
            throw new Exception("Invalid indexes provided!");
        }
        return this.gameObjects[y][x];
    }

    /**
     * Method tries to add instance of GameObject at specified position. If cell
     * is taken, throws Exception.
     * 
     * @param object
     *            game object to be added in 2d array containing game objects
     * @param x
     *            x coordinate of the object. Corresponds to index [..][x] in 2d
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects[0].length - 1]
     * @param y
     *            y coordinate of the object. Corresponds to index [y][..] in 2d
     *            array of game objects. Provided value must be in range [0,
     *            gameObjects.length - 1]
     * @throws Exception
     *             throws exception if invalid x and y values are provided, cell
     *             is already taken or 2d array containing game objects is full
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
            this.gameObjects[objY][objX] = object;
        } else {
            throw new Exception("Cannot add more elements. Array is full.");
        }
    }

    /**
     * Prints grid in terminal. Food is represented by string "F", alive
     * SimpleCreature instance is represented by string "A", CreatureDependant
     * instance is represented by string "B", CreatureNonDependant is
     * represented by string "C" and dead creature is represented by string "D".
     */
    void printGrid() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                jevo.GameObject currObject = gameObjects[y][x];
                if (currObject == null) {
                    System.out.print(" ");
                } else if (!currObject.getGoType().equals("Food")) {
                    LocalCreature creature = (LocalCreature) currObject;
                    if (creature.getState()) {
                        if (creature.getGoType().equals("CreatureSimple")) {
                            System.out.print("A");
                        } else if (creature.getGoType()
                                .equals("CreatureDependant")) {
                            System.out.print("B");
                        } else {
                            System.out.print("C");
                        }
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    /**
     * Method sets all elements in 2D array (gameObjects) to null and clears map
     * containing creature types and number of alive creatures per type
     */
    public void reset() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                this.gameObjects[y][x] = null;
            }
        }
    }

    /**
     * Method checks if 2d array containing game objects is full
     * 
     * @return true if 2d array containing game objects is full, false otherwise
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
     * Method updates status of each creature according to rules that govern
     * whether creature in cell survives or not
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void update() throws IllegalArgumentException, IllegalAccessException {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                if (this.gameObjects[y][x] != null) {
                    jevo.GameObject currObject = this.gameObjects[y][x];
                    // If class of current game object is Creature class or one
                    // of the
                    // sub classes of Creature class
                    if (LocalCreature.class.isInstance(currObject)) {
                        LocalCreature cr = (LocalCreature) currObject;
                        boolean statusAfter = false;
                        // Get neighbors for creature, both local and non-local
                        // depending on level of locality of
                        // a particular group
                        ArrayList<LocalCreature> neighborCount;
                        int locality = 0;
                        int creaturesAggressiveness = 0;
                        if (CreatureSimple.class.isInstance(cr)) {
                            locality = CreatureSimple.getNeighborNonLocality();
                            creaturesAggressiveness = CreatureSimple
                                    .getAggressiveness();
                        }
                        if (CreatureNonDependant.class.isInstance(cr)) {
                            locality = CreatureNonDependant
                                    .getNeighborNonLocality();
                            creaturesAggressiveness = CreatureNonDependant
                                    .getAggressiveness();
                        }
                        if (CreatureDependant.class.isInstance(cr)) {
                            locality = CreatureDependant
                                    .getNeighborNonLocality();
                            creaturesAggressiveness = CreatureDependant
                                    .getAggressiveness();
                        }
                        neighborCount = this.getLivingNeighbors(x, y, locality);
                        if (CreatureSimple.class.isInstance(cr)) {
                            CreatureSimple creature = (CreatureSimple) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CreatureDependant.class.isInstance(cr)) {
                            CreatureDependant creature = (CreatureDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CreatureNonDependant.class.isInstance(cr)) {
                            CreatureNonDependant creature = (CreatureNonDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
                        }
                        // If most frequent creature around is of other type
                        String mostFrequent = mostFrequentCreatureAsNeighbor(
                                neighborCount);
                        if (!cr.getGoType().equals(mostFrequent)) {
                            // Get corresponding aggressiveness of most common
                            // neighbor type
                            int aggressiveness = 0;
                            LocalCreature newCreature = null;
                            if (CreatureSimple.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CreatureSimple
                                        .getAggressiveness();
                                newCreature = new CreatureSimple(x, y);
                            }
                            if (CreatureDependant.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CreatureDependant
                                        .getAggressiveness();
                                newCreature = new CreatureDependant(x, y);
                            }
                            if (CreatureNonDependant.class.getSimpleName()
                                    .equals(mostFrequent)) {
                                aggressiveness = CreatureNonDependant
                                        .getAggressiveness();
                                newCreature = new CreatureNonDependant(x, y);
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

    private String mostFrequentCreatureAsNeighbor(
            ArrayList<LocalCreature> liveNeighbors) {
        HashMap<String, Integer> neighborMap = new HashMap<>();
        for (LocalCreature creature : liveNeighbors) {
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
     * Method returns 2D array containing game objects
     * 
     * @return 2d array with game objects
     */
    public jevo.GameObject[][] getGameObjects() {
        return this.gameObjects;
    }

    /**
     * Method returns number of living creatures located in both local and
     * non-local cells relative to cell with coordinates (x, y)
     * 
     * @param x
     *            x coordinate of cell relative to which local and non-local
     *            cells are checked
     * @param y
     *            y coordinate of cell relative to which local and non-local
     *            cells are checked
     * @param locality
     *            Determines how many unique local and non-local cells should be
     *            checked for living creatures. Value provided must be in range
     *            [0, 8]. 0 checks only local cells, 8 checks only non-local
     *            cells. 4 checks 4 random local cells and 4 random non-local
     *            cells.
     * @return number of living creatures in local and non-local cells checked
     */
    private ArrayList<LocalCreature> getLivingNeighbors(int x, int y,
            int locality) {
        // List that contains local directions that should should be checked
        ArrayList<Integer> directions = new ArrayList<>();
        assert (locality >= 0
                && locality <= 8) : "Invalid input. Paramater (locality) should be in range [0, 8].";
        ArrayList<LocalCreature> ans = new ArrayList<>();
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
     * Method returns number of living creatures located in some or all local
     * cells around a cell with coordinates (x, y)
     * 
     * @param x
     *            x coordinate of cell relative to which local cells are checked
     * @param y
     *            y coordinate of cell relative to which local cells are checked
     * @param directions
     *            list containing directions that determine which local cells
     *            relative to the cell should be checked
     * @return number of living creatures
     */
    private ArrayList<LocalCreature> neighborsInDirections(int x, int y,
            ArrayList<Integer> directions) {
        ArrayList<LocalCreature> ans = new ArrayList<>();
        for (int i = 0; i < directions.size(); i++) {
            int currentDirection = directions.get(i);
            switch (currentDirection) {
            case 0:
                // Check right side
                if (x != this.getX() - 1
                        && this.gameObjects[y][x + 1] != null) {
                    jevo.GameObject currObject = this.gameObjects[y][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
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
                    jevo.GameObject currObject = this.gameObjects[y - 1][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 2:
                // Check top
                if (y > 0 && this.gameObjects[y - 1][x] != null) {
                    jevo.GameObject currObject = this.gameObjects[y - 1][x];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 3:
                // Check top left corner
                if (x > 0 && y > 0 && this.gameObjects[y - 1][x - 1] != null) {
                    jevo.GameObject currObject = this.gameObjects[y - 1][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
                        if (creature.getState()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 4:
                // Check left side
                if (x > 0 && this.gameObjects[y][x - 1] != null) {
                    jevo.GameObject currObject = this.gameObjects[y][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
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
                    jevo.GameObject currObject = this.gameObjects[y + 1][x - 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
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
                    jevo.GameObject currObject = this.gameObjects[y + 1][x];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
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
                    jevo.GameObject currObject = this.gameObjects[y + 1][x + 1];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
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
     * Method randomly checks (n) unique cells in the grid and returns number of
     * cells that contain living creature relative to cell with coordinates (x,
     * y)
     * 
     * @param x
     *            x coordinate of cell relative to which random non-local
     *            neighbors are checked
     * @param y
     *            y coordinate of cell relative to which random non-local
     *            neighbors are checked
     * @param n
     *            number of random cells that should be checked
     * @return number of living creatures in checked cells
     */
    private ArrayList<LocalCreature> nonLocalNeighbors(int x, int y, int n) {
        ArrayList<LocalCreature> ans = new ArrayList<>();
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
                        LocalCreature creature = (LocalCreature) this.gameObjects[randY][randX];
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
     * Method sets the next state for creatures, determined by the current
     * state. After executed, resets all elements in (nextState) 2D array to
     * false.
     */
    private void setNextState() {
        for (int y = 0; y < this.nextState.length; y++) {
            for (int x = 0; x < this.nextState[0].length; x++) {
                if (this.gameObjects[y][x] != null) {
                    jevo.GameObject currObject = this.gameObjects[y][x];
                    if (!currObject.getGoType().equals("Food")) {
                        LocalCreature creature = (LocalCreature) currObject;
                        creature.setState(this.nextState[y][x]);
                        this.gameObjects[y][x] = creature;
                    }
                }
            }
        }
        for (int i = 0; i < this.nextState.length; i++) {
            for (int j = 0; j < this.nextState[0].length; j++) {
                this.nextState[i][j] = false;
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        int x = 5, y = 5;
        LocalGrid grid = new LocalGrid(x, y);
        while (!grid.isFull()) {
            grid.addObject(new CreatureSimple(x, y));
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
