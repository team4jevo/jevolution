package jevo;

import java.util.Hashtable;

/**
 * Created by LongJohn on 8/19/2016.
 */

// represents all entities of simulations.
// all entities of specific simulation should extend it
// to get displayed GameObject should contain pointer to a Token

public class GameObject { // 2DO make this class abstract
    String type="";
    int logicX, logicY; // coordinates in array
    private Token token; // pointer to a token
    private boolean isRendered = true; // 2DO  if token == null isRendered = false;

    public GameObject(){}
    public GameObject (String type){
        this.type = type;
    }

    public GameObject (String type,int x,int y, boolean isRendered){
        this.isRendered = isRendered ;
        this.type = type;
        this.logicX = x;
        this.logicY = y;
    }

    public GameObject (String type,int x,int y){

        this.type = type;
        this.logicX = x;
        this.logicY = y;
    }

    public void move(int logicXPath, int logicYPath){
        token.move(logicXPath,logicYPath);
        logicX +=logicXPath;
        logicY +=logicYPath;

    }


    // this method should be overritten in similar fashion in any children who have different set of parameters for displaying
    public Hashtable<String, String> getRenderedStats (){
        Hashtable<String,String> ht = new Hashtable<>();
        ht.put("Type",type); // should be obvious by Token
        ht.put("LogicX",logicX+""); //
        ht.put("LogicY",logicY+""); // actually X and Y are not so interesting and can be avoided
        return ht;
    }

    public void setRenderedStats (Hashtable<String, String> ht){ // should process same parameters as getRenderedStats (or not )
        // setters instead of = (on changes of x y tokens should move etc );
        // if LogicX/Y changed and GameObjects are stored in array[x][y] then gameObject should be moved to new array adress in GameLogic
        type = ht.get("Type");
        logicX = Integer.valueOf(ht.get("LogicX")); // exceptions
        logicY = Integer.valueOf(ht.get("LogicY"));
    }


    public void setToken(Token t){
        token = t;
    }

    public Token getToken (){
        return token;
    }

    public void removeToken (){
        token.die();
        token = null;
    }

    public String getGoType (){
        return this.type;
    }

    public int getLogicX(){
        return logicX;
    }
    public int getLogicY(){
        return logicY;
    }

    public void setLogicX(int x){
        logicX = x;
    }

    public void setLogicY(int y){
        logicY = y;
    }
}
