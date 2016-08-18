package gamelogic;

import java.util.*;

public class Grid {
    private GameObject[][] gameObjects;
    private Random random;
    private boolean[][] nextState;
    private HashMap<String, Integer> aliveCreatures;

    Grid(int x, int y) {
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
     *            -> object to be added
     * @param x
     *            -> x coordinate (array index)
     * @param y
     *            -> y coordinate (array index)
     * @throws Exception
     */
    public void addObject(GameObject object, int x, int y) throws Exception {
        // If x or y is out of range, throw exception
        if (x < 0 || x >= this.getX() || y < 0 || y >= this.getY()) {
            throw new Exception("Array index out of bounds!");
        }
        if (!this.isFull()) {
            if (this.gameObjects[y][x] != null) {
                throw new Exception("Cannot add element in x=" + x + ", y=" + y
                        + ". Cell taken.");
            }
            if (!object.getType().equals("Food")) {
                Creature creature = (Creature) object;
                if (creature.getStatus()) {
                    if (aliveCreatures.containsKey(creature.getType())) {
                        aliveCreatures.put(creature.getType(),
                                aliveCreatures.get(creature.getType()) + 1);
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
     * creature is represented by string "A", dead creature is represented by
     * string "D".
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
                        } else if (creature.getType().equals(
                                "CreatureDependant")) {
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
     * Method sets all elements in 2D array (gameObjects) to null.
     */
    void reset() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                this.gameObjects[y][x] = null;
            }
        }
        this.aliveCreatures.clear();
    }

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
     * Method updates status of each creature according to rules of Game of Life
     * (currently).
     */
    public void update() {
        for (int y = 0; y < this.getY(); y++) {
            for (int x = 0; x < this.getX(); x++) {
                if (this.gameObjects[y][x] != null) {
                    GameObject currObject = this.gameObjects[y][x];
                    if (!currObject.getType().equals("Food")) {
                        Creature cr = (Creature) currObject;
                        boolean statusAfter = false;
                        boolean statusBefore = cr.getStatus();
                        int neighborCount = this.getLivingNeighbors(x, y);
                        if (currObject.getType().equals("CreatureSimple")) {
                            CreatureSimple creature = (CreatureSimple) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (currObject.getType().equals(
                                "CreatureDependant")) {
                            CreatureDependant creature = (CreatureDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
                        } else if (currObject.getType().equals(
                                "CreatureNonDependant")) {
                            CreatureNonDependant creature = (CreatureNonDependant) currObject;
                            statusAfter = creature.survives(neighborCount);
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
    
    /**
     * Method returns 2D array containing game objects.
     */
    public GameObject[][] getGameObjects() {
        return this.gameObjects;
    }
    
    /**
     * Method returns HashMap with key that represents type of creature and
     * value that represents number of alive creatures.
     * @return
     */
    public HashMap<String, Integer> getNumberOfAliveCreatures() {
        return this.aliveCreatures;
    }

    /**
     * Method returns number of living creatures nearby around certain location
     * (x, y)
     * 
     * @param x
     * @param y
     * @return
     */
    private int getLivingNeighbors(int x, int y) {
        int count = 0;
        // Check right side
        if (x != this.getX() - 1 && this.gameObjects[y][x + 1] != null) {
            GameObject currObject = this.gameObjects[y][x + 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check cells on bottom right
        if (x != this.getX() - 1 && y != this.getY() - 1
                && this.gameObjects[y + 1][x + 1] != null) {
            GameObject currObject = this.gameObjects[y + 1][x + 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check cells on bottom
        if (y != this.getY() - 1 && this.gameObjects[y + 1][x] != null) {
            GameObject currObject = this.gameObjects[y + 1][x];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check cells on left bottom corner
        if (x > 0 && y != this.getY() - 1
                && this.gameObjects[y + 1][x - 1] != null) {
            GameObject currObject = this.gameObjects[y + 1][x - 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check left side
        if (x > 0 && this.gameObjects[y][x - 1] != null) {
            GameObject currObject = this.gameObjects[y][x - 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check top
        if (y > 0 && this.gameObjects[y - 1][x] != null) {
            GameObject currObject = this.gameObjects[y - 1][x];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check top left corner
        if (x > 0 && y > 0 && this.gameObjects[y - 1][x - 1] != null) {
            GameObject currObject = this.gameObjects[y - 1][x - 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        // Check top right corner
        if (x < this.getX() - 1 && y > 0
                && this.gameObjects[y - 1][x + 1] != null) {
            GameObject currObject = this.gameObjects[y - 1][x + 1];
            if (!currObject.getType().equals("Food")) {
                Creature creature = (Creature) currObject;
                if (creature.getStatus()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Method returns ArrayList of Food objects that neighbor certain cell.
     */
    ArrayList<Food> getNeighboringFood(int x, int y) {
        ArrayList<Food> result = new ArrayList<Food>();
        // Check right side
        if (x != this.getX() - 1 && this.gameObjects[y][x + 1] != null) {
            GameObject currObject = this.gameObjects[y][x + 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check cells on bottom right
        if (x != this.getX() - 1 && y != this.getY() - 1
                && this.gameObjects[y + 1][x + 1] != null) {
            GameObject currObject = this.gameObjects[y + 1][x + 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check cells on bottom
        if (y != this.getY() - 1 && this.gameObjects[y + 1][x] != null) {
            GameObject currObject = this.gameObjects[y + 1][x];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check cells on left bottom corner
        if (x > 0 && y != this.getY() - 1
                && this.gameObjects[y + 1][x - 1] != null) {
            GameObject currObject = this.gameObjects[y + 1][x - 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check left side
        if (x > 0 && this.gameObjects[y][x - 1] != null) {
            GameObject currObject = this.gameObjects[y][x - 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check top
        if (y > 0 && this.gameObjects[y - 1][x] != null) {
            GameObject currObject = this.gameObjects[y - 1][x];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check top left corner
        if (x > 0 && y > 0 && this.gameObjects[y - 1][x - 1] != null) {
            GameObject currObject = this.gameObjects[y - 1][x - 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        // Check top right corner
        if (x < this.getX() - 1 && y > 0
                && this.gameObjects[y - 1][x + 1] != null) {
            GameObject currObject = this.gameObjects[y - 1][x + 1];
            if (currObject.getType().equals("Food")) {
                Food food = (Food) currObject;
                result.add(food);
            }
        }
        return result;
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
        /*
         * for (int i = 0; i < 16; i++) { grid.addObject(new Creature()); }
         */

        grid.addObject(new Creature(), 1, 1);
        grid.addObject(new Creature(), 1, 2);
        grid.addObject(new Creature(), 2, 1);
        grid.addObject(new Creature(), 2, 2);
        grid.addObject(new Creature(), 3, 3);
        grid.addObject(new Creature(), 3, 4);
        grid.addObject(new Creature(), 4, 3);
        grid.addObject(new Creature(), 4, 4);

        System.out
                .println("----------------- Initial state -------------------");
        grid.printGrid();
        grid.update();
        System.out.println("----------------- Post update -------------------");
        grid.printGrid();
        grid.update();
        System.out
                .println("----------------- Post update 2 -------------------");
        grid.printGrid();
        System.out
                .println("----------------- Post update 3 --------------------");
    }

}
