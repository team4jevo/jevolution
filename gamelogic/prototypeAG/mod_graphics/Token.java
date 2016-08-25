package jevo;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.geometry.Insets;
import javafx.print.Collation;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
        sh.setFill(Color.rgb(80,80,80));
        getChildren().add(sh);
       // getChildren().add(tf);
        setOnMouseClicked(e -> {
           System.out.println ("token clicked");

            ge.getController().displayGoStatsInTable(go);
            // give them to gui table
            // connect gui table to go, so that if stats are changed in table they are changed in go
        });
    }


    public void moveTo (int newX, int newY) { // moves to new coordinates
        x = newX*size;
        y = newY*size;
        setTranslateX (x);
        setTranslateY (y);
    }

    public void move (int xPath, int yPath){
        x += xPath*size;
        y += yPath*size;
        setTranslateX (x);
        setTranslateY (y);
    }

    public void moveAnim (int xPath, int yPath){
            TranslateTransition t = new TranslateTransition(Duration.millis(2000), sh);
            t.setByX(xPath*size);
            t.setByY(yPath*size);
            t.setAutoReverse(true);
            t.play();
    }


    public void die (){
        System.out.println ("token die");
        //remove from list also
        ge.getTokens().remove(this);
        ((Pane) Token.this.getParent()).getChildren().remove (Token.this);
    }

}
