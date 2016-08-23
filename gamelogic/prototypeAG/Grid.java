package gamelogic;

import java.awt.Point;
import java.util.*;

public class Grid {
    private GameObject[][] gameObjects;
    private boolean[][] nextState;
    private Random random;
    private HashMap<String, Integer> aliveCreatures;

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
    Grid(int x, int y) throws Exception {
        if (x < 5 || x > 100 || y < 5 || y > 100) {
            throw new Exception("Invalid parameters to Grid constructor provided. x and y must be in range [5, 100].");
        }
        this.gameObjects = new GameObject[y][x];
        this.nextState = new boolean[y][x];
        this.random = new Random();
        this.aliveCreatures = new HashMap<>();
    }

    public int getX() {
        return this.gameObjects[0].length;
    }

    public int getY() {
        return this.gameObjects.length;
    }

    /**
     * Methods adds new object in random place into array of objects. In case
     * array is full, throws Exception.
     * 
     * @param object
     *            game object to be added in 2d array containing game objects
     */
    void addObject(GameObject object) throws Exception {
        if (!this.isFull()) {
            int x = random.nextInt(this.getX());
            int y = random.nextInt(this.getY());
            while (this.gameObjects[y][x] != null) {
                x = random.nextInt(this.getX());
                y = random.nextInt(this.getY());
            }
            object.setLocation(x, y);
            this.gameObjects[y][x] = object;
        } else {
            throw new Exception("Cannot add more elements. Array is full.");
        }
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
    public void addObject(GameObject object, int x, int y) throws Exception {
        // If x or y is out of range, throw exception
        if (x < 0 || x >= this.getX() || y < 0 || y >= this.getY()) {
            throw new Exception("Array index out of bounds!");
        }
        if (!this.isFull()) {
            if (this.gameObjects[y][x] != null) {
                throw new Exception("Cannot add element in x=" + x + ", y=" + y + ". Cell taken.");
            }
            if (!object.getType().equals("Food")) {
                Creature creature = (Creature) object;
                if (creature.getStatus()) {
                    if (aliveCreatures.containsKey(creature.getType())) {
                        aliveCreatures.put(creature.getType(), aliveCreatures.get(creature.getType()) + 1);
                    } else {
                        aliveCreatures.put(creature.getType(), 1);
                    }
                }
            }
            object.setLocation(x, y);
            this.gameObjects[y][x] = object;
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
                GameObject currObject = gameObjects[y][x];
                if (currObject == null) {
                    System.out.print(" ");
                } else if (!currObject.getType().equals("Food")) {
                    Creature creature = (Creature) currObject;
                    if (creature.getStatus()) {
                        if (creature.getType().equals("CreatureSimple")) {
                            System.out.print("A");
                        } else if (creature.getType().equals("CreatureDependant")) {
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
    void reset() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                this.gameObjects[y][x] = null;
            }
        }
        this.aliveCreatures.clear();
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
     */
    public void update() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                if (this.gameObjects[y][x] != null) {
                    GameObject currObject = this.gameObjects[y][x];
                    // If class of current game object is Creature class or one of the
                    // sub classes of Creature class
                    if (Creature.class.isInstance(currObject)) {
                        Creature cr = (Creature) currObject;
                        boolean statusAfter = false;
                        boolean statusBefore = cr.getStatus();
                        // Get neighbors for creature, both local and non-local depending on level of locality of
                        // a particular group
                        ArrayList<Creature> neighborCount;
                        int locality = 0;
                        if (CreatureSimple.class.isInstance(currObject)) {
                            locality = CreatureSimple.getNeighborNonLocality();
                        }
                        if (CreatureNonDependant.class.isInstance(currObject)) {
                            locality = CreatureNonDependant.getNeighborNonLocality();
                        }
                        if (CreatureDependant.class.isInstance(currObject)) {
                            locality = CreatureDependant.getNeighborNonLocality();
                        }
                        neighborCount = this.getLivingNeighbors(x, y, locality);
                        if (CreatureSimple.class.isInstance(currObject)) {
                            CreatureSimple creature = (CreatureSimple) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CreatureDependant.class.isInstance(currObject)) {
                            CreatureDependant creature = (CreatureDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (CreatureNonDependant.class.isInstance(currObject)) {
                            CreatureNonDependant creature = (CreatureNonDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
                        }
                        // Check if creature will turn to other team
                        String newType = creatureTurnsTo(neighborCount);
                        // If new class name if different from current class name of creature
                        if (!cr.getType().equals(newType)) {
                            Creature newCreature = null;
                            if (CreatureSimple.class.getClass().getSimpleName().equals(newType)) {
                                newCreature = new CreatureSimple();
                            }
                            if (CreatureDependant.class.getClass().getSimpleName().equals(newType)) {
                                newCreature = new CreatureDependant();
                            }
                            if (CreatureNonDependant.class.getClass().getSimpleName().equals(newType)) {
                                newCreature = new CreatureNonDependant();
                            }
                            newCreature.setStatus(statusAfter);
                            newCreature.setLocation(x, y);
                            this.gameObjects[y][x] = newCreature;
                            cr = newCreature;
                        }
                        // Update number of alive creatures
                        if (statusBefore) {
                            if (!statusAfter) {
                                aliveCreatures.put(cr.getType(), aliveCreatures.get(cr.getType()) - 1);
                            }
                        } else {
                            if (statusAfter) {
                                aliveCreatures.put(cr.getType(), aliveCreatures.get(cr.getType()) + 1);
                            }
                        }
                        this.nextState[y][x] = statusAfter;
                    }
                }
            }
        }
        this.setNextState();
    }
    
    private String creatureTurnsTo(ArrayList<Creature> livingNeighbors) {
        String ans = "";
        return ans;
    }

    /**
     * Method returns 2D array containing game objects
     * 
     * @return 2d array with game objects
     */
    public GameObject[][] getGameObjects() {
        return this.gameObjects;
    }

    /**
     * Method returns map with key as string that represents type of creature
     * and value as integer that represents number of alive creatures per
     * creature type.
     * 
     * @return map with creature type and number of alive creatures per creature
     *         type
     */
    public HashMap<String, Integer> getNumberOfAliveCreatures() {
        return this.aliveCreatures;
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
    private ArrayList<Creature> getLivingNeighbors(int x, int y, int locality) {
        // List that contains local directions that should should be checked
        ArrayList<Integer> directions = new ArrayList<>();
        assert (locality >= 0 && locality <= 8) : "Invalid input. Paramater (locality) should be in range [0, 8].";
        ArrayList<Creature> ans = new ArrayList<>();
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
    private ArrayList<Creature> neighborsInDirections(int x, int y, ArrayList<Integer> directions) {
        ArrayList<Creature> ans = new ArrayList<>();
        for (int i = 0; i < directions.size(); i++) {
            int currentDirection = directions.get(i);
            switch (currentDirection) {
            case 0:
                // Check right side
                if (x != this.getX() - 1 && this.gameObjects[y][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y][x + 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 1:
                // Check top right corner
                if (x < this.getX() - 1 && y > 0 && this.gameObjects[y - 1][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x + 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 2:
                // Check top
                if (y > 0 && this.gameObjects[y - 1][x] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 3:
                // Check top left corner
                if (x > 0 && y > 0 && this.gameObjects[y - 1][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y - 1][x - 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 4:
                // Check left side
                if (x > 0 && this.gameObjects[y][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y][x - 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 5:
                // Check cells on left bottom corner
                if (x > 0 && y != this.getY() - 1 && this.gameObjects[y + 1][x - 1] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x - 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 6:
                // Check cells on bottom
                if (y != this.getY() - 1 && this.gameObjects[y + 1][x] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
                            ans.add(creature);
                        }
                    }
                }
                break;
            case 7:
                // Check cells on bottom right
                if (x != this.getX() - 1 && y != this.getY() - 1 && this.gameObjects[y + 1][x + 1] != null) {
                    GameObject currObject = this.gameObjects[y + 1][x + 1];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        if (creature.getStatus()) {
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
    private ArrayList<Creature> nonLocalNeighbors(int x, int y, int n) {
        ArrayList<Creature> ans = new ArrayList<>();
        ArrayList<Point> control = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (true) {
                int randY = random.nextInt(this.getY());
                int randX = random.nextInt(this.getX());
                // If random location is not one of the local cells or cell
                // itself
                if (!((randX == x && randY == y) ||
                    (randX == x && randY == y - 1) ||
                    (randX == x && randY == y + 1) ||
                    (randX == x + 1 && randY == y) ||
                    (randX == x - 1 && randY == y) ||
                    (randX == x + 1 && randY == y + 1) ||
                    (randX == x - 1 && randY == y - 1) ||
                    (randX == x - 1 && randY == y + 1) ||
                    (randX == x + 1 && randY == y - 1))){
                    // Current point
                    Point currentPoint = new Point(randX, randY);
                    // If there is no such point in control list
                    if (!control.contains(currentPoint)) {
                        // Add this point to control list
                        control.add(currentPoint);
                        // Get creature
                        Creature creature = (Creature) this.gameObjects[randY][randX];
                        // If creature is alive, add 1 to count
                        if (creature.getStatus()) {
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
                    GameObject currObject = this.gameObjects[y][x];
                    if (!currObject.getType().equals("Food")) {
                        Creature creature = (Creature) currObject;
                        creature.setStatus(this.nextState[y][x]);
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
        Grid grid = new Grid(x, y);
        while (!grid.isFull()) {
            grid.addObject(new CreatureSimple());
        }
        
        // Test  nonLocalNeighbors  method
        for (int i = 0; i < 9; i++) {
            System.out.println(grid.nonLocalNeighbors(1, 1, i));
        }
        System.out.println();
        
        // Test  neighborsInDirections  method
        ArrayList<Integer> direction = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            direction.add(i);
            System.out.println(grid.neighborsInDirections(1, 1, direction));
        }
    }

}
