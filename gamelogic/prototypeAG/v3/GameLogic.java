package jevosim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;

import javafx.scene.paint.Color;

public class GameLogic extends jevo.GameLogic {
    int tmpX = 0;
    CellGrid grid;
    private Random random;
    private int cellLayout;
    private HashMap<Class<?>, Integer> aliveCells;

    public GameLogic(jevo.GraphicsEngine ge) {
        super(ge);
        this.random = new Random();
        this.cellLayout = 0;
        this.aliveCells = null;
    }

    public void initializeGrid(int xSize, int ySize) throws Exception {
        this.grid = new CellGrid(xSize, ySize);
        GameObject.setCellGrid(this.grid);
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                switch (this.random.nextInt(3)) {
                case 0:
                    CellA cs = new CellA(x, y);
                    cs.setState(random.nextBoolean());
                    this.grid.addObject(cs);
                    break;
                case 1:
                    CellB cnd = new CellB(x, y);
                    cnd.setState(random.nextBoolean());
                    this.grid.addObject(cnd);
                    break;
                case 2:
                    CellC cd = new CellC(x, y);
                    cd.setState(random.nextBoolean());
                    this.grid.addObject(cd);
                    break;
                default:
                    break;
                }
            }
        }
    }

    public void initializeGrid(int xSize, int ySize, int creatureSetup)
            throws Exception {
        this.cellLayout = creatureSetup;
        this.grid = new CellGrid(xSize, ySize);
        GameObject.setCellGrid(this.grid);
        switch (creatureSetup) {
        case 0:
            this.initializeGrid(xSize, ySize);
            break;
        case 1:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CellA cs = new CellA(x, y);
                    cs.setState(random.nextBoolean());
                    this.grid.addObject(cs);
                }
            }
            break;
        case 2:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CellB cnd = new CellB(x, y);
                    cnd.setState(random.nextBoolean());
                    this.grid.addObject(cnd);
                }
            }
            break;
        case 3:
            for (int y = 0; y < this.grid.getY(); y++) {
                for (int x = 0; x < this.grid.getX(); x++) {
                    CellC cd = new CellC(x, y);
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
                        CellA cs = new CellA(x, y);
                        cs.setState(random.nextBoolean());
                        this.grid.addObject(cs);
                        break;
                    case 1:
                        CellB cnd = new CellB(x, y);
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
                        CellA cs = new CellA(x, y);
                        cs.setState(random.nextBoolean());
                        this.grid.addObject(cs);
                        break;
                    case 1:
                        CellC cd = new CellC(x, y);
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
                        CellC cd = new CellC(x, y);
                        cd.setState(random.nextBoolean());
                        this.grid.addObject(cd);
                        break;
                    case 1:
                        CellB cnd = new CellB(x, y);
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
        this.aliveCells = this.grid.getAliveCells();
    }

    @Override
    public Hashtable<String, String> getRenderedStats() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("Width", super.width + ""); //
        ht.put("Height", super.height + "");
        ht.put("Green cooperativeness [0,10]",
                +CellA.getCooperativeness() + "");
        ht.put("Green aggressiveness [0,8]", +CellA.getAggressiveness() + "");
        ht.put("Green neighbor non-locality [0,8]",
                +CellA.getNeighborNonLocality() + "");
        ht.put("Orange aggressiveness [0,8]", +CellB.getAggressiveness() + "");
        ht.put("Orange cooperativeness [0,10]",
                +CellB.getCooperativeness() + "");
        ht.put("Orange neighbor non-locality [0,8]",
                +CellB.getNeighborNonLocality() + "");
        ht.put("Red aggressiveness [0,8]", +CellC.getAggressiveness() + "");
        ht.put("Red cooperativeness [0,10]", +CellC.getCooperativeness() + "");
        ht.put("Red neighbor non-locality [0,8]",
                +CellC.getNeighborNonLocality() + "");
        ht.put("Game type [0, 6]", String.valueOf(this.cellLayout));
        
        if (aliveCells == null || aliveCells.size() == 0) {
            ht.put("Green alive", "0");
            ht.put("Orange alive", "0");
            ht.put("Red alive", "0");
        } else {
            ht.put("Green alive", String.valueOf(aliveCells.get(CellA.class)));
            ht.put("Orange alive", String.valueOf(aliveCells.get(CellB.class)));
            ht.put("Red alive", String.valueOf(aliveCells.get(CellC.class)));
        }
        
        return ht;
    }

    @Override
    public boolean modifyStat(String parameter, String newValue)
            throws NumberFormatException, Exception {
        boolean re = false;
        int val = 0;
        switch (parameter) {
        case "Width":
            this.width = Integer.parseInt(newValue);
            if (super.width < 5) {
                super.width = 5;
            } else if (super.width > 50) {
                super.width = 50;
            }
            re = true;
            break;
        case "Height":
            super.height = Integer.parseInt(newValue);
            if (super.height < 5) {
                super.height = 5;
            } else if (super.height > 50) {
                super.height = 50;
            }
            re = true;
            break;
        case "Green cooperativeness [0,10]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 10) {
                val = 10;
            }
            CellA.setCooperativeness(val);
            re = true;
            break;
        case "Green aggressiveness [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellA.setAggressiveness(val);
            re = true;
            break;
        case "Green neighbor non-locality [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellA.setNeighborNonLocality(val);
            re = true;
            break;
        case "Orange cooperativeness [0,10]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 10) {
                val = 10;
            }
            CellB.setCooperativeness(val);
            re = true;
            break;
        case "Orange aggressiveness [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellB.setAggressiveness(val);
            re = true;
            break;
        case "Orange neighbor non-locality [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellB.setNeighborNonLocality(val);
            re = true;
            break;
        case "Red cooperativeness [0,10]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 10) {
                val = 10;
            }
            CellC.setCooperativeness(val);
            re = true;
            break;
        case "Red aggressiveness [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellC.setAggressiveness(val);
            re = true;
            break;
        case "Red neighbor non-locality [0,8]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 8) {
                val = 8;
            }
            CellC.setNeighborNonLocality(val);
            re = true;
            break;
        case "Game type [0, 6]":
            val = Integer.parseInt(newValue);
            if (val < 0) {
                val = 0;
            } else if (val > 6) {
                val = 6;
            }
            this.cellLayout = val;
            re = true;
            break;
        default:
            break;
        }
        return re;
    }

    public void nextTurn() throws Exception {
        System.out.println("next turn in game logic ");
        this.grid.update();
        //super.getGe().getController().displayGlStatsInTable(this);
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                GameObject currentGameObject = this.grid.getGameObject(x, y);
                if (Cell.class.isInstance(currentGameObject)) {
                    Cell creature = (Cell) currentGameObject;
                    if (!creature.getState()) {
                        // If creature has token, remove it
                        if (creature.getToken() != null) {
                            creature.removeToken();
                        }
                    } else {
                        // If creature does not have token, add it
                        if (creature.getToken() == null) {
                            creature.setToken(
                                    super.getGe().createToken(creature));
                            if (CellA.class
                                    .isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.GREEN);
                            }
                            if (CellB.class
                                    .isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.ORANGE);
                            }
                            if (CellC.class
                                    .isAssignableFrom(creature.getClass())) {
                                creature.getToken().setColor(Color.RED);
                            }
                        }
                    }
                }
            }
        }
    }

    private String getExternalSetup() {
        StringBuilder ans = new StringBuilder();
        // Add grid size
        ans.append("<gridsettings>" + "\n");
        ans.append("width\t" + this.grid.getX() + "\n");
        ans.append("height\t" + this.grid.getY() + "\n");
        // Add game setup
        ans.append("<gamesettings>\n");
        ans.append("gametype\t" + this.cellLayout + "\n");
        ans.append(CellA.class.getSimpleName().toLowerCase()
                + "_aggressiveness\t" + CellA.getAggressiveness() + "\n");
        ans.append(CellB.class.getSimpleName().toLowerCase()
                + "_aggressiveness\t" + CellB.getAggressiveness() + "\n");
        ans.append(CellC.class.getSimpleName().toLowerCase()
                + "_aggressiveness\t" + CellC.getAggressiveness() + "\n");
        ans.append(CellA.class.getSimpleName().toLowerCase()
                + "_cooperativeness\t" + CellA.getCooperativeness() + "\n");
        ans.append(CellB.class.getSimpleName().toLowerCase()
                + "_cooperativeness\t" + CellB.getCooperativeness() + "\n");
        ans.append(CellC.class.getSimpleName().toLowerCase()
                + "_cooperativeness\t" + CellC.getCooperativeness() + "\n");
        ans.append(CellA.class.getSimpleName().toLowerCase() + "_nonlocality\t"
                + CellA.getNeighborNonLocality() + "\n");
        ans.append(CellB.class.getSimpleName().toLowerCase() + "_nonlocality\t"
                + CellB.getNeighborNonLocality() + "\n");
        ans.append(CellC.class.getSimpleName().toLowerCase() + "_nonlocality\t"
                + CellC.getNeighborNonLocality() + "\n");
        ans.append("<gameobjects>\n");
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                try {
                    GameObject currentObject = this.grid.getGameObject(x, y);
                    Cell lc = (Cell) currentObject;
                    String type = currentObject.getClass().getSimpleName()
                            .toLowerCase();
                    ans.append("type" + "\t" + type + "\n");
                    ans.append("x\t" + x + "\n");
                    ans.append("y\t" + y + "\n");
                    ans.append("state\t" + lc.getState() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ans.toString();
    }

    private void setExternalSetup(String externalSetup)
            throws NumberFormatException, Exception {
        if (externalSetup.length() == 0) {
            System.out.println("File is empty, no external string\n");
        } else {
            // System.out.println(externalSetup);
            String[] array = externalSetup.split("<[a-z]*>");
            if (array.length != 0) {
                System.out.println("Array contains external settings");
                // Set grid settings
                String[] gridSettings = array[1].split("\n");
                for (String gridSetting : gridSettings) {
                    System.out.println(gridSetting);
                    String[] gridSettingArray = gridSetting.split("\t");
                    if (gridSettingArray[0].equals("width")) {
                        super.width = Integer.parseInt(gridSettingArray[1]);
                    }
                    if (gridSettingArray[0].equals("height")) {
                        super.height = Integer.parseInt(gridSettingArray[1]);
                    }
                }
                // Set game settings
                String[] gameSettings = array[2].split("\n");
                for (String gameSetting : gameSettings) {
                    String[] gameSettingArray = gameSetting.split("\t");
                    String s = gameSettingArray[0];
                    if (s.equals("gametype")) {
                        this.cellLayout = Integer.parseInt(gameSettingArray[1]);
                    } else if (s
                            .equals(CellA.class.getSimpleName().toLowerCase()
                                    + "_aggressiveness")) {
                        CellA.setAggressiveness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellB.class.getSimpleName().toLowerCase()
                                    + "_aggressiveness")) {
                        CellB.setAggressiveness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellC.class.getSimpleName().toLowerCase()
                                    + "_aggressiveness")) {
                        CellC.setAggressiveness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellA.class.getSimpleName().toLowerCase()
                                    + "_cooperativeness")) {
                        CellA.setCooperativeness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellB.class.getSimpleName().toLowerCase()
                                    + "_cooperativeness")) {
                        CellB.setCooperativeness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellC.class.getSimpleName().toLowerCase()
                                    + "_cooperativeness")) {
                        CellC.setCooperativeness(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellA.class.getSimpleName().toLowerCase()
                                    + "_nonlocality")) {
                        CellA.setNeighborNonLocality(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellB.class.getSimpleName().toLowerCase()
                                    + "_nonlocality")) {
                        CellB.setNeighborNonLocality(
                                Integer.parseInt(gameSettingArray[1]));
                    } else if (s
                            .equals(CellC.class.getSimpleName().toLowerCase()
                                    + "_nonlocality")) {
                        CellC.setNeighborNonLocality(
                                Integer.parseInt(gameSettingArray[1]));
                    }
                }
                // Reinitialize grid
                this.grid = new CellGrid(super.width, super.height);
                GameObject.setCellGrid(this.grid);
                // Clear tokens
                super.getGe().reset();
                // Set game objects
                String[] gameObjects = array[3].split("\n");
                // Determine step between game object data
                int step = 4;
                // Ignore first element since it is ""
                for (int i = 1; i < gameObjects.length; i += step) {
                    String type = gameObjects[i].split("\t")[1];
                    int x = Integer.parseInt(gameObjects[i + 1].split("\t")[1]);
                    int y = Integer.parseInt(gameObjects[i + 2].split("\t")[1]);
                    boolean state = Boolean
                            .parseBoolean(gameObjects[i + 3].split("\t")[1]);
                    if (type.equals(
                            CellA.class.getSimpleName().toLowerCase())) {
                        CellA cs = new CellA(x, y);
                        cs.setState(state);
                        this.grid.addObject(cs);
                        if (cs.getState()) {
                            cs.setToken(super.getGe().createToken(cs));
                            cs.getToken().setColor(Color.GREEN);
                        }
                    } else if (type.equals(
                            CellB.class.getSimpleName().toLowerCase())) {
                        CellB cnd = new CellB(x, y);
                        cnd.setState(state);
                        this.grid.addObject(cnd);
                        if (cnd.getState()) {
                            cnd.setToken(super.getGe().createToken(cnd));
                            cnd.getToken().setColor(Color.ORANGE);
                        }
                    } else if (type.equals(
                            CellC.class.getSimpleName().toLowerCase())) {
                        CellC cd = new CellC(x, y);
                        cd.setState(state);
                        this.grid.addObject(cd);
                        if (cd.getState()) {
                            cd.setToken(super.getGe().createToken(cd));
                            cd.getToken().setColor(Color.RED);
                        }
                    }
                }
                // Display loaded settings
                super.getGe().getController().displayGlStatsInTable(this);
                System.out.println("Successful setup from external file");
            } else {
                System.out
                        .println("Layout of file does not match the expected");
            }
        }
    }

    @Override
    public int getTokenSize() {
        // token size in pixels;
        return 15;
    }

    @Override
    public void loadFile(File file) throws Exception {
        // Test if file is readable
        if (file.canRead()) {
            // Create StringBuilder that will gather the data
            StringBuilder sb = new StringBuilder();
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line + "\n");
            }
            fileReader.close();
            bf.close();
            setExternalSetup(sb.toString());
            System.out.print("fileLoad " + file + "\n");
        } else {
            System.out.println("Unable to read file " + file + "\n");
        }
    }

    @Override
    public void saveFile(File file) throws Exception {
        // Test if file is writable
        if (file.canWrite()) {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(getExternalSetup());
            printWriter.flush();
            printWriter.close();
            System.out.print("fileSave " + file + "\n");
        } else {
            System.out.println("Unable to write file " + file + "\n");
        }
    }

    public void newGame() throws Exception {
        // Game settings
        initializeGrid(super.width, super.height, this.cellLayout);
        // Set tokens of starting creatures
        for (int y = 0; y < this.grid.getY(); y++) {
            for (int x = 0; x < this.grid.getX(); x++) {
                jevo.GameObject currentGameObject = this.grid.getGameObject(x,
                        y);
                if (Cell.class.isInstance(currentGameObject)) {
                    Cell creature = (Cell) currentGameObject;
                    if (creature.getState()) {
                        creature.setToken(super.getGe().createToken(creature));
                        if (CellA.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.GREEN);
                        }
                        if (CellB.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.ORANGE);
                        }
                        if (CellC.class.isAssignableFrom(creature.getClass())) {
                            creature.getToken().setColor(Color.RED);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        GameLogic gl = new GameLogic(
                new jevo.GraphicsEngine(new jevo.Controller()));
        try {
            gl.initializeGrid(10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(gl.getExternalSetup());
    }

}
