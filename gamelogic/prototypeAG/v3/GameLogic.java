package jevosim;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;

import javafx.scene.paint.Color;
import metrics.GameObjectDB;
import metrics.GameObjectRecord;


/**
 * Created by LongJohn on 8/19/2016.
 */


public class GameLogic extends jevo.GameLogic {
    int tmpX = 0;
    LocalGrid grid;
    private Random random;
    private int currentCreatureSetup;

    public GameLogic(jevo.GraphicsEngine ge) {
        super(ge);
        this.random = new Random();
        this.currentCreatureSetup = 0;
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
    
    @Override
    public Hashtable<String, String> getRenderedStats() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("Width", super.width + ""); //
        ht.put("Height", super.height + "");
        ht.put("Green cooperativeness [0,10]", + CreatureSimple.getCooperativeness() + "");
        ht.put("Green aggressiveness [0,8]", + CreatureSimple.getAggressiveness() + "");
        ht.put("Orange aggressiveness [0,8]", + CreatureNonDependant.getAggressiveness() + "");
        ht.put("Orange cooperativeness [0,10]", + CreatureNonDependant.getCooperativeness() + "");
        ht.put("Red aggressiveness [0,8]", + CreatureDependant.getAggressiveness() + "");
        ht.put("Red cooperativeness [0,10]", + CreatureDependant.getCooperativeness() + "");
        ht.put("Game type [0, 6]", String.valueOf(this.currentCreatureSetup));
        return ht;
    }
    
    @Override
    public boolean modifyStat (String parameter, String newValue)  {
        // TODO Add control so user cannot break it that easily
        boolean re = false;
        switch (parameter) {
        case "Width":
            this.width = Integer.parseInt(newValue);
            re = true;
            if (super.width < 5) {
                super.width = 5;
            }
            if (super.width > 50) {
                super.width = 50;
            }
            break;
        case "Height":
            super.height = Integer.parseInt(newValue);
            re = true;
            if (super.height < 5) {
                super.height = 5;
            }
            if (super.height > 50) {
                super.height = 50;
            }
            break;
        case "Type":
            break;
        case "Green cooperativeness [0,10]":
            try {
                CreatureSimple.setCooperativeness(Integer.parseInt(newValue));
                re = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Green aggressiveness [0,8]":
            try {
                CreatureSimple.setAggressiveness(Integer.parseInt(newValue));
                re = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Orange cooperativeness [0,10]":
            try {
                CreatureNonDependant.setCooperativeness(Integer.parseInt(newValue));
                re = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Orange aggressiveness [0,8]":
            try {
                CreatureNonDependant.setAggressiveness(Integer.parseInt(newValue));
                re = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Red cooperativeness [0,10]":
            try {
                CreatureDependant.setCooperativeness(Integer.parseInt(newValue));
                re = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Red aggressiveness [0,8]":
            try {
                CreatureDependant.setAggressiveness(Integer.parseInt(newValue));
                re = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case "Game type [0, 6]":
            this.currentCreatureSetup = Integer.parseInt(newValue);
            re = true;
            break;
        default:
            break;
        }
        return re;
    }

    public void initializeGrid(int xSize, int ySize, int creatureSetup) throws Exception {
        // TODO finish this method
        this.currentCreatureSetup = creatureSetup;
        this.grid = new LocalGrid(xSize, ySize);
        switch (creatureSetup) {
        case 0:
            this.initializeGrid(xSize, ySize);
            break;
        case 1:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureSimple cs = new CreatureSimple(x, y);
                    cs.setState(random.nextBoolean());
                    this.grid.addObject(cs);
                }
            }
            break;
        case 2:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureNonDependant cnd = new CreatureNonDependant(x, y);
                    cnd.setState(random.nextBoolean());
                    this.grid.addObject(cnd);
                }
            }
            break;
        case 3:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CreatureDependant cd = new CreatureDependant(x, y);
                    cd.setState(random.nextBoolean());
                    this.grid.addObject(cd);
                }
            }
            break;        
        case 4:
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
        case 5:
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
        case 6:
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
                GameObject currentGameObject = this.grid.getGameObject(x, y);
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
                            if (CreatureSimple.class.isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.GREEN);
                            }
                            if (CreatureNonDependant.class.isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.ORANGE);
                            }
                            if (CreatureDependant.class.isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.RED);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public int getTokenSize (){
        //token size in pixels;
        return 15;
    }
    
     @Override
    public void loadFile (File file) throws Exception {
         // TODO Finish this method
         System.out.print ("fielLoad " + file);
    }
     
     @Override
    public void saveFile (File file) throws Exception {
         // TODO Finish this method
         System.out.print ("fielSave " + file);
    }
     

    public void newGame() throws Exception {
        // Game settings
        initializeGrid(super.width, super.height, this.currentCreatureSetup);
        //CreatureSimple.setAggressiveness(1);
        //CreatureDependant.setAggressiveness(2);
        //CreatureDependant.setCooperativeness(4);
        //CreatureNonDependant.setNeighborNonLocality(1);
        
        // Set tokens of starting creatures
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                jevo.GameObject currentGameObject = this.grid.getGameObject(x, y);
                if (LocalCreature.class.isInstance(currentGameObject)) {
                    LocalCreature creature = (LocalCreature) currentGameObject;
                    if (creature.getState()) {
                        creature.setToken(super.getGe().createToken(creature));
                        if (CreatureSimple.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.GREEN);
                        }
                        if (CreatureNonDependant.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.ORANGE);
                        }
                        if (CreatureDependant.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.RED);
                        }
                    }
                }
            }
        }
    }

}
