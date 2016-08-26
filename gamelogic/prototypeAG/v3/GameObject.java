package jevosim;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;

import javafx.scene.paint.Color;
import metrics.GameObjectDB;
import metrics.GameObjectRecord;

/**
 * Created by LongJohn on 8/19/2016.
 */

// represents all entities of simulations.
// all entities of specific simulation should extend it
// to get displayed GameObject should contain pointer to a Token

public class GameObject extends jevo.GameObject {
    private static int counter = 0;
    private int id;
    private static CellGrid cg;

    public GameObject() {
        super();
        this.id = ++counter;
    }

    public GameObject(String type) {
        super(type);
        this.id = ++counter;
    }

    public GameObject(String type, int x, int y, boolean isRendered) {
        super(type, x, y, isRendered);
        this.id = ++counter;
    }

    public GameObject(String type, int x, int y) {
        super(type, x, y);
        this.id = ++counter;
    }

    public int getId() {
        return this.id;
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
    
    public static void setCellGrid(CellGrid cg) {
        GameObject.cg = cg;
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
