package jevo;

import javafx.geometry.Insets;
import javafx.print.Collation;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class Token extends StackPane {
    // visual representation of game object

    private int x, y ;
    private Text tf;


    public GraphicsEngine getGe() {
        return ge;
    }

    public void setColor (Color newColor){
        sh.setFill(newColor);
    }

    public void setTokenShape (Shape newShape) {
        sh = newShape; // this method sucks cus it ignores gui level
    }

    public void setText (String newText){
        tf.setText(newText);
    }

    private GraphicsEngine ge;
    private GameObject go;

    private Shape sh;
    int size;

    public Token (GameObject go,  int size, GraphicsEngine ge){
        setPadding(new Insets(1,1,1,1));
        this.ge = ge;//GraphicalEngine
        this.go = go;//GameObject
        go.setToken(this);
        this.x = go.getLogicX()*size;
        this.y = go.getLogicY()*size;
        this.size = size;
        sh = new Rectangle(size-2, size-2);

        tf = new Text (go.getGoType());

        setTranslateX (x);
        setTranslateY (y);
       // setOnMouseClicked(e-> System.out.println("token clicked ")); // this should be elsewhere
        sh.setFill(Color.GREEN);
        getChildren().add(sh);
        getChildren().add(tf);
        setOnMouseClicked(e -> {
           System.out.println ("token clicked");
           // System.out.println (go.getLogicX()+" "+go.getLogicY() );

            // get stats from go
           // System.out.println (go.getRenderedStats());
            ge.displayGoStatsInTable(go);
            // give them to gui table
            // connect gui table to go, so that if stats are changed in table they are changed in go
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
