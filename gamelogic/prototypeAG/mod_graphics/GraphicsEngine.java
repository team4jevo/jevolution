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

    public void setGoPropTable (TableView tblGoProperties, TableColumn colGoParameter, TableColumn colGoValue){
        this.tblGoProperties = tblGoProperties;

        this.colGoParameter = colGoParameter;
        this.colGoValue = colGoValue;
    }



    private Pane field ;
    private int tileSize = 25;
    private List<Token> tokens;

    public void setPaneField (Pane field){
        this.field = field;
    }

    public void reset (){
        //erase previous tokens
<<<<<<< HEAD
        this.field.getChildren().clear();
=======
 //       for (Token t :tokens) {
   //         t.die();
  //      }

        field.getChildren().clear();
>>>>>>> branch 'master' of https://github.com/team4jevo/jevolution.git
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


//    public void drawToken (GameObject go, int logicX, int logicY){
       // Token t = new Token(logicX* tileSize, logicY*tileSize, tileSize);
        //field.getChildren().add(t);
//    }

    private GameObject tmpGoForTable;
    public void displayGoStatsInTable (GameObject go){
        tmpGoForTable = go ;
        System.out.println (go.getRenderedStats());
        processTable();
    }

    // move this shit to Graphics Engine
    public void processTable (){

        tblGoProperties.setEditable(true);
        tblGoProperties.setEditable(true);
        colGoParameter.setCellValueFactory(new PropertyValueFactory<>("property"));


        colGoValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        colGoValue.setCellFactory(TextFieldTableCell.<GameParameter>forTableColumn());
    /*    colGoValue.setOnEditCommit(e-> {

                System.out.println("some shit edited");

                });*/

        colGoValue.setOnEditCommit(
                new EventHandler<CellEditEvent<String,String>>() {
                    @Override
                    public void handle(CellEditEvent<String,String> t) {
                        System.out.print("editcommit "+t.getNewValue());
                        String newValue = t.getNewValue();
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        ht.put("Type",newValue); // should be obvious by Token
                        ht.put("LogicX","11"); //
                        ht.put("LogicY","11");
                        tmpGoForTable.setRenderedStats(ht);

         //               (GameParameter) t.getTableView().getItems().get(
         //                       t.getTablePosition().getRow())
          //                      ).setValue(t.getNewValue());

                       // System.out.println(s);
                    }
                }


        );



        tblGoProperties.setItems (getParam());
    }

    public ObservableList<GameParameter> getParam (){
        ObservableList<GameParameter> gamePros = FXCollections.observableArrayList();

        Hashtable<String,String> ht = tmpGoForTable.getRenderedStats();
        Enumeration<String> key = ht.keys();
        while (key.hasMoreElements()) {
            String k = key.nextElement();
            String v = ht.get(k);
            gamePros.add(new GameParameter (k, v));
        }



        return gamePros;
    }








}
