package customgl;
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

public class GameLogic extends jevo.GameLogic {
    int tmpX = 0;
    LocalGrid grid;
    private Random random;
    private String currentCreatureSetup;

    public GameLogic(jevo.GraphicsEngine ge) {
        super(ge);
        this.random = new Random();
        this.currentCreatureSetup = "default";
    }

    public void initializeGrid(int xSize, int ySize) throws Exception {
        this.grid = new LocalGrid(xSize, ySize);
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                switch (this.random.nextInt(3)) {
                case 0:
                    CreatureSimple cs = new CreatureSimple(x, y);
                    cs.setState(random.nextBoolean());
                    this.grid.addObject(cs);
                    break;
                case 1:
                    CreatureNonDependant cnd = new CreatureNonDependant(x, y);
                    cnd.setState(random.nextBoolean());
                    this.grid.addObject(cnd);
                    break;
                case 2:
                    CreatureDependant cd = new CreatureDependant(x, y);
                    cd.setState(random.nextBoolean());
                    this.grid.addObject(cd);
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
        this.grid = new LocalGrid(xSize, ySize);
        switch (creatureSetup) {
        case "default":
            this.initializeGrid(xSize, ySize);
            break;
        case "simple":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureSimple cs = new CreatureSimple(x, y);
                    cs.setState(random.nextBoolean());
                    this.grid.addObject(cs);
                }
            }
            break;
        case "nondependant":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureNonDependant cnd = new CreatureNonDependant(x, y);
                    cnd.setState(random.nextBoolean());
                    this.grid.addObject(cnd);
                }
            }
            break;
        case "dependant":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureDependant cd = new CreatureDependant(x, y);
                    cd.setState(random.nextBoolean());
                    this.grid.addObject(cd);
                }
            }
            break;        
        case "mix1":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    switch (this.random.nextInt(2)) {
                    case 0:
                        CreatureSimple cs = new CreatureSimple(x, y);
                        cs.setState(random.nextBoolean());
                        this.grid.addObject(cs);
                        break;
                    case 1:
                        CreatureNonDependant cnd = new CreatureNonDependant(x, y);
                        cnd.setState(random.nextBoolean());
                        this.grid.addObject(cnd);
                        break;
                    default:
                        break;
                    }
                }
            }
            break;
        case "mix2":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    switch (this.random.nextInt(2)) {
                    case 0:
                        CreatureSimple cs = new CreatureSimple(x, y);
                        cs.setState(random.nextBoolean());
                        this.grid.addObject(cs);
                        break;
                    case 1:
                        CreatureDependant cd = new CreatureDependant(x, y);
                        cd.setState(random.nextBoolean());
                        this.grid.addObject(cd);
                        break;
                    default:
                        break;
                    }
                }
            }
            break;
        case "mix3":
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    switch (this.random.nextInt(2)) {
                    case 0:
                        CreatureDependant cd = new CreatureDependant(x, y);
                        cd.setState(random.nextBoolean());
                        this.grid.addObject(cd);
                        break;
                    case 1:
                        CreatureNonDependant cnd = new CreatureNonDependant(x, y);
                        cnd.setState(random.nextBoolean());
                        this.grid.addObject(cnd);
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

    public void nextTurn() throws Exception {

        System.out.println("next turn in game logic ");
        this.grid.update();
        for (int y = 0; y < this.grid.getY(); y++) {
            for(int x = 0; x < this.grid.getX(); x++) {
                jevo.GameObject currentGameObject = this.grid.getGameObject(x, y);
                if (LocalCreature.class.isInstance(currentGameObject)) {
                    LocalCreature creature = (LocalCreature) currentGameObject;
                    if (!creature.getState()) {
                        // If creature has token, remove it
                        if (creature.getToken() != null) {
                            creature.removeToken();
                        }
                    } else {
                        // If creature does not have token, add it
                        if (creature.getToken() == null) {
                            creature.setToken(super.getGe().createToken(creature));
                        }
                    }
                }
            }
        }
    }

    public void newGame() throws Exception {
        // Game settings
        initializeGrid(15, 15, "default");
        CreatureSimple.setAggressiveness(1);
        CreatureDependant.setAggressiveness(2);
        CreatureDependant.setCooperativeness(4);
        CreatureNonDependant.setNeighborNonLocality(1);
        
        // set starting creatures
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                jevo.GameObject currentGameObject = this.grid.getGameObject(x, y);
                if (LocalCreature.class.isInstance(currentGameObject)) {
                    LocalCreature creature = (LocalCreature) currentGameObject;
                    if (creature.getState()) {
                        currentGameObject.setToken(super.getGe().createToken(currentGameObject));
                    }
                }
            }
        }
    }

}
