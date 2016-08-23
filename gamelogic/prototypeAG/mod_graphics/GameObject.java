package graphics;


/**
 * Created by LongJohn on 8/19/2016.
 */
public class GameObject {
    String type="";
    int logicX, logicY; // coordinates in array
    private Token token; // pointer to a token

    public GameObject (String type){
        this.type = type;
    }

    public GameObject (String type,int x,int y){
        this.type = type;
        this.logicX = x;
        this.logicY = y;
    }

    public void setToken(Token t){
        token = t;
    }

    public Token getToken (){
        return token;
    }

    public void removeToken (){
        //token remove token from stage
        // delete it from token array
        // it should be implemented in Graphics tier
        token.die();
       token = null;

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
    
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
