package graphics;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class GraphicsEngine {
    // class that is responcible for displaying tokens

    private Pane field ;
    private int tileSize = 30;
    private List<Token> tokens;

    public void setPaneField (Pane field){
        this.field = field;
    }

    public void reset (){
        //erase previous tokens
        this.field.getChildren().clear();
        tokens = new ArrayList<>();
    }

    public List<Token> getTokens(){
        return tokens;
    }

    public Token createToken (GameObject go){
        Token t = new Token (go, tileSize, this);
        tokens.add (t);
        field.getChildren().add(t);
        return t;
    }


    public void drawToken (GameObject go, int logicX, int logicY){
       // Token t = new Token(logicX* tileSize, logicY*tileSize, tileSize);
        //field.getChildren().add(t);
    }

}
