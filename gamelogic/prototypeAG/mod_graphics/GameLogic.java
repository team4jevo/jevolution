package graphics;
import java.util.Random;

/**
 * Created by LongJohn on 8/19/2016.
 */

/*
 * interface this class should have following stuff methods Constructor
 * (GraphicEngine ge) should take in pointer to graphic engine object game
 * engine usage examples field [x][y] = new GameObject("f",x,y); field
 * [x][y].setToken (ge.createToken (field [x][y])); GameObject.setToken connects
 * game object ( creature food etc in logical simulation) with token -
 * representation on UI level each game object that is to be displayed in UI
 * have to be connected to a token game object and token have pointers to each
 * other, that allows them to call methods in both deirections field
 * [x][y].getToken().move(1, -1); field [x][y].move(1, -1); this can be
 * rewritten ( move method can be added to GameObject move method moves token to
 * new position
 * 
 * field [x][y].removeToken(); removes token is not done yet note that in
 * GameLogic everywhere x and y are Logical coordinates, not graphical in pixels
 * 
 * newGame () should start new game (currently on newGame remains of previous
 * nextTurn () controller calls it each time when new turn is needed amount of
 * actions made in one turn is up to a GameLogic - it can be single action of
 * one organism or processing all of the organisms or anything else
 * 
 */

public class GameLogic {
    GraphicsEngine ge;
    int tmpX = 0;
    gamelogic2.LocalGrid grid;
    private Random random;
    private String currentCreatureSetup;

    public GameLogic(GraphicsEngine ge) {
        this.random = new Random();
        this.currentCreatureSetup = "default";
        this.ge = ge;
    }

    public void initializeGrid(int xSize, int ySize) throws Exception {
        this.grid = new gamelogic2.LocalGrid(xSize, ySize);
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                switch (this.random.nextInt(3)) {
                case 0:
                    this.grid.addObject(new gamelogic2.CreatureSimple(x, y));
                    break;
                case 1:
                    this.grid.addObject(new gamelogic2.CreatureNonDependant(x, y));
                    break;
                case 2:
                    this.grid.addObject(new gamelogic2.CreatureDependant(x, y));
                    break;
                default:
                    break;
                }
            }
        }
    }

    public void initializeGrid(int xSize, int ySize, String creatureSetup) throws Exception {
        // TODO finish this method
        this.currentCreatureSetup = creatureSetup;
        this.grid = new gamelogic2.LocalGrid(xSize, ySize);
        switch (creatureSetup) {
        case "default":
            this.initializeGrid(xSize, ySize);
            break;
        default:
            break;
        }
    }

    public void nextTurn() {

        System.out.println("next turn in game logic ");
        /*
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                
                if (this.grid.getGameObject(x, y) == null) {
                    System.out.println("found empty cell ");
                    
                     * if (Math.random()<0.01){ System.out.println
                     * ("adding object"); field [x][y] = new
                     * GameObject("f",x,y); field [x][y].setToken
                     * (ge.createToken (field [x][y])); }
                     
                } else if ((Math.random() < 0.5)) {
                    try {
                        System.out.println("moving token");

                        field[x][y].getToken().move(1, -1);
                        field[x + 1][y - 1] = field[x][y];
                        field[x][y] = null;

                    } catch (Exception e) {
                        System.out.println("some other time");
                    }

                } else if (Math.random() > 0.5) {
                    System.out.println("removing token");
                    field[x][y].removeToken();
                    field[x][y] = null;

                }

            }
        }
        */

    }

    public void newGame() throws Exception {
        this.initializeGrid(30, 30, "default");
        // set starting creatures
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                graphics.GameObject currentGameObject = this.grid.getGameObject(x, y);
                currentGameObject.setToken(ge.createToken(currentGameObject));
            }
        }
    }

}
