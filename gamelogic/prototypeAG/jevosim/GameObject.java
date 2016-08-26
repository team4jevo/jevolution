package jevosim;

import java.util.Hashtable;
import javafx.scene.paint.Color;

/**
 * Returns general GameObject object customized for simulation evolution. String
 * parameter indicates type of this object used for later display. Integer
 * parameters <x> and <y> indicate object's location in two dimensional space.
 * Boolean parameter indicates whether object should be display in GUI or not.
 * 
 * @author aigars
 *
 */
public class GameObject extends jevo.GameObject {
    private static CellGrid cg;

    public GameObject() {
        super();
    }

    public GameObject(String type) {
        super(type);
    }

    public GameObject(String type, int x, int y, boolean isRendered) {
        super(type, x, y, isRendered);
    }

    public GameObject(String type, int x, int y) {
        super(type, x, y);
    }

    /**
     * Assigns reference of CellGrid object to static field <cg>. It is used to
     * replace GameObject object in GameObject[][] array with another GameObject
     * object.
     * 
     * @param cg
     *            CellGrid object containing two dimensional array of GameObject
     *            objects
     * 
     */
    public static void setCellGrid(CellGrid cg) {
        GameObject.cg = cg;
    }

    @Override
    public Hashtable<String, String> getRenderedStats() {
        Hashtable<String, String> ht = new Hashtable<>();
        String type = "";
        String coop = "";
        String aggr = "";
        if (CellA.class.isAssignableFrom(this.getClass())) {
            type = "GreenCell";
            coop = String.valueOf(CellA.getCooperativeness());
            aggr = String.valueOf(CellA.getAggressiveness());
        }
        if (CellB.class.isAssignableFrom(this.getClass())) {
            type = "OrangeCell";
            coop = String.valueOf(CellB.getCooperativeness());
            aggr = String.valueOf(CellB.getAggressiveness());
        }
        if (CellC.class.isAssignableFrom(this.getClass())) {
            type = "RedCell";
            coop = String.valueOf(CellC.getCooperativeness());
            aggr = String.valueOf(CellC.getAggressiveness());
        }
        ht.put("Type", type);
        ht.put("Cooperativeness", coop);
        ht.put("Aggresiveness", aggr);
        return ht;
    }

    @Override
    public boolean modifyStat(String parameter, String newValue) {
        boolean control = false;
        if (parameter.equals("Type")) {
            Cell thisCell = (Cell) this;
            int x = thisCell.getLogicX();
            int y = thisCell.getLogicY();
            if (newValue.equals("GreenCell")) {
                if (!CellA.class.isAssignableFrom(this.getClass())) {
                    x = thisCell.getLogicX();
                    y = thisCell.getLogicY();
                    try {
                        CellA cella = new CellA(x, y);
                        cella.setState(thisCell.getState());
                        cella.setToken(thisCell.getToken());
                        cella.getToken().setColor(Color.GREEN);
                        GameObject.cg.setGameObject(x, y, cella);
                        super.setType("GreenCell");
                        control = true;
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (newValue.equals("OrangeCell")) {
                if (!CellB.class.isAssignableFrom(this.getClass())) {
                    x = thisCell.getLogicX();
                    y = thisCell.getLogicY();
                    try {
                        CellB cellb = new CellB(x, y);
                        cellb.setState(thisCell.getState());
                        cellb.setToken(thisCell.getToken());
                        cellb.getToken().setColor(Color.ORANGE);
                        GameObject.cg.setGameObject(x, y, cellb);
                        super.setType("OrangeCell");
                        control = true;
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (newValue.equals("RedCell")) {
                if (!CellC.class.isAssignableFrom(this.getClass())) {
                    try {
                        CellC cellc = new CellC(x, y);
                        cellc.setState(thisCell.getState());
                        cellc.setToken(thisCell.getToken());
                        cellc.getToken().setColor(Color.RED);
                        GameObject.cg.setGameObject(x, y, cellc);
                        super.setType("RedCell");
                        control = true;
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return control;
    }

}
