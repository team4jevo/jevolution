import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class Token extends StackPane {
    // visual representation of game object

    private int x, y ;
    private GraphicsEngine ge;


    private Rectangle rt;
    int size;

    public Token (GameObject go,  int size, GraphicsEngine ge){
        this.ge = ge;
        go.setToken(this);
        this.x = go.getLogicX()*size;
        this.y = go.getLogicY()*size;
        this.size = size;
        rt = new Rectangle(size, size);
        setTranslateX (x);
        setTranslateY (y);
       // setOnMouseClicked(e-> System.out.println("token clicked ")); // this should be elsewhere
        rt.setFill(Color.GREEN);
        getChildren().add(rt);
    }

    public void move (int xPath, int yPath){
        x += xPath*size;
        y += yPath*size;
        setTranslateX (x);
        setTranslateY (y);
    }

    public void die (){
        System.out.println ("token die");
        //remove from list also
        ge.getTokens().remove(this);
        ((Pane) Token.this.getParent()).getChildren().remove (Token.this);
    }

}
