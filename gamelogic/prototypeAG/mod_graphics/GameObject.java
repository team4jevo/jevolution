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
        ///add fallowing 3 lines in the end of all methods that update parameters that should be displayed in table
 //       if (connectedToTable){
  //          token.getGe().getController().displayGoStatsInTable(this);
  //      }

    }
/*


    // this method should be overritten in similar fashion in any children who have different set of parameters for displaying
    public Hashtable<String, String[]> getRenderedStats (){
        Hashtable<String,String[]> ht = new Hashtable<>();
        // name of parameter - is displayed in table and used in modifyStat
        // sa[0] value
        // sa[1] description
        String[] sa = new String[2];
        sa[0]= type;
        sa[1]= "type of object";
        ht.put("Type",sa);

        sa[0]=logicX+"";
        sa[1]="description X";
        ht.put("X",sa); //

        sa[0]=logicX+"";
        sa[1]="description X";
        ht.put("Y",sa); // actually X and Y are not so interesting and can be avoided
        return ht;
    }
*/


    // this method should be overritten in similar fashion in any children who have different set of parameters for displaying
    public Hashtable<String, String> getRenderedStats () {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("Type", type);
        ht.put("X", logicX + ""); //
        ht.put("Y", logicY + ""); // actually X and Y are not so interesting and can be avoided
        return ht;

    }

    private boolean connectedToTable = false;
    public void setTableConnection (){
        connectedToTable = true;
        System.out.println ("set table connection");
    }

    public void breakTableConnection (){
        connectedToTable = false;
       // token.getGe().getController().displayGoStatsInTable(this);
        System.out.println ("break table connection");
    }



    // override this method in similar fashion according to needs of your gl
    public boolean modifyStat (String parameter, String newValue)  {
        // true if new value accepted; false if not; // if not accepted, no changes made
        boolean re = false;
        // parameter is a string that had been passed to ui in getRenderedStats method
        switch (parameter) {
            // switch should have same case set as passed in getRenderedStats()
            case "Type" :
                try {
                    // check for new value validity (type, range) shuld be made here. UI tier does not do that
                    if (newValue.length() < 5 ){
                        // use methods if you expect to see some changes instantly, just variable = newValue  is not going to make changes in graphics or in gamelogic
                        setType(newValue);
                        re = true;
                    }
                } catch (Exception e){
                    System.err.print(e);
                }

            case "X" :         // if you are going to actually use this method, you should check if X and Y are in field
                try {          // this does not change position in the array
                    logicX = Integer.valueOf(newValue);
                    token.moveTo (logicX, logicY);  // update token
                    re = true;
                } catch (Exception e){
                    System.err.print(e);
                }

                break;
            case "Y" :         //if you do not want user to be able to edit this parameter, but only see it, just return false
                try {
                    logicX = Integer.valueOf(newValue);
                    token.moveTo (logicX, logicY);
                    re = true;
                } catch (Exception e){
                    System.err.print(e);
                }
                break;
        }




        return re;
    }


    public void setType (String type){

        this.type = type;
        token.setText(type);
 //       if (connectedToTable){
  //          token.getGe().getController().displayGoStatsInTable(this);
  //      }

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
