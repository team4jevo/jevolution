package graphics;


import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * Created by LongJohn on 8/19/2016.
 */
public class Token extends StackPane {
    // visual representation of game object

    private int x, y ;
    private GraphicsEngine ge;
    private GameObject go;

    private Rectangle rt;
    int size;

    public Token (GameObject go,  int size, GraphicsEngine ge){
        this.ge = ge;
        this.go = go;
        go.setToken(this);
        this.x = go.getLogicX()*size;
        this.y = go.getLogicY()*size;
        this.size = size;
        rt = new Rectangle(size, size);
        Text tf = new Text (go.getType());

        setTranslateX (x);
        setTranslateY (y);
       // setOnMouseClicked(e-> System.out.println("token clicked ")); // this should be elsewhere
        rt.setFill(Color.GREEN);
        getChildren().add(rt);
        getChildren().add(tf);
        setOnMouseClicked(e -> {
           System.out.println ("token clicked");
            System.out.println (go.getLogicX()+" "+go.getLogicY() );

        });
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
