package jevo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.control.TableColumn.CellEditEvent;


import java.util.*;

/**
 * Created by LongJohn on 8/19/2016.
 */
// class that is responcible for displaying tokens
public class GraphicsEngine {

    private TableView tblGoProperties;
    private TableColumn colGoParameter;
    private TableColumn colGoValue;
    private Controller controller;

    public GraphicsEngine (Controller controller) {
        this.controller = controller;
    }

    public Controller getController (){
        return controller;
    }

    public void setGoPropTable (TableView tblGoProperties, TableColumn colGoParameter, TableColumn colGoValue){
        this.tblGoProperties = tblGoProperties;

        this.colGoParameter = colGoParameter;
        this.colGoValue = colGoValue;
    }



    private Pane field ;
    private int tileSize = 30;
    private List<Token> tokens;
    public void setTileSize(int i){
        tileSize = i;
    }

    public void setPaneField (Pane field){
        this.field = field;
    }

    public void reset (){
        //erase previous tokens
 //       for (Token t :tokens) {
   //         t.die();
  //      }

        field.getChildren().clear();
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




}
