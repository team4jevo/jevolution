package jevosim;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;

import metrics.GameObjectDB;
import metrics.GameObjectRecord;


/**
 * Created by LongJohn on 8/19/2016.
 */

// represents all entities of simulations.
// all entities of specific simulation should extend it
// to get displayed GameObject should contain pointer to a Token

public class GameObject extends jevo.GameObject {
    private static GameObjectDB godb; 
    private static int counter = 0;
    private int id;

    public GameObject() {
        super ();
        this.id = ++counter;
        if (godb == null) {
            godb = new GameObjectDB();
        }
        try {
            godb.addRecord(new GameObjectRecord(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public GameObject (String type) {
        super (type);
        this.id = ++counter;
        if (godb == null) {
            godb = new GameObjectDB();
        }
        try {
            godb.addRecord(new GameObjectRecord(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public GameObject (String type,int x,int y, boolean isRendered) {
        super (type, x, y, isRendered);
        this.id = ++counter;
        if (godb == null) {
            godb = new GameObjectDB();
        }
        try {
            godb.addRecord(new GameObjectRecord(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public GameObject (String type,int x,int y) {
        super (type, x, y);
        this.id = ++counter;
        if (godb == null) {
            godb = new GameObjectDB();
        }
        try {
            godb.addRecord(new GameObjectRecord(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public Hashtable<String, String> getRenderedStats (){
        Hashtable<String,String> ht = new Hashtable<>();
        HashMap<String, Object> parameters = null;
        try {
            parameters = godb.getRecord(this.id).getParameters();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (parameters != null) {
            for (String parameter : parameters.keySet()) {
                ht.put(parameter, String.valueOf(parameters.get(parameter)));
            }
        }
        return ht;
    }
    
    @Override
    public boolean modifyStat (String parameter, String newValue) { return false; }

}
