package jevo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.setUserAgentStylesheet;

/**
 * Created by LongJohn on 8/19/2016.
 */
//when new game logic object is created graphic engine is passed to it in constructor
public class Controller {
    // this class processes user events and calls needed methods of GameLogic

    //private String selectedSimulation = "simulation 2.1";
    private Number selectedSimulation = 0 ;
    private Stage stage;



    @FXML
    private ChoiceBox cbGameLogicSelector;



    @FXML
    private TableView<GameParameter> tblGoProperties;

    @FXML
    private TableColumn<GameParameter,String> colGoValue ;

    @FXML
    private TableColumn<GameParameter,String> colGoParameter ;

    GraphicsEngine ge = new GraphicsEngine(this);
    GameLogic gl = new gl21.GameLogic(ge);


    Timeline timeline = new Timeline (new KeyFrame(
            Duration.millis(20),
            ae -> {
                System.out.println ("tick");
                try {
                    gl.nextTurn();
                }catch (Exception e){
                    System.err.print(e);
                }
            }
    ));


    @FXML
    private Pane paneField;

    @FXML
    private Button playBtn;

    @FXML
    protected TextField tfTurns ;

    @FXML
    protected TextField tfSpeed ;


    @FXML
    protected Button btnNewGame;
    private boolean simulationInProgress = false;

    @FXML
    protected void handleNewGame (ActionEvent event) throws Exception {
        System.out.println ("new game");

        if (simulationInProgress){ // simulatiion is running. stop it and prepare for the new one
            ge.reset();
            simulationInProgress = false;
            btnNewGame.setText("Start Simulation");
            displayGlStatsInTable(gl);
            // load parameters to new game logic
            timeline.stop();

        }else { // simulation starts
            simulationInProgress = true;
            btnNewGame.setText("Set New Simulation");
            ge.setTileSize(gl.getTokenSize());
            ge.reset();
            gl.newGame();
        }

    }


    private void prepareForSimulation () {
        btnNewGame.setText("Begin New Simulation");
        ge.reset();
        simulationInProgress = false;
        ge.setPaneField(paneField);

    }


    @FXML
    protected void handleNextTurn (ActionEvent event) throws Exception {
        try {
            int turns = 0;
            try {
                turns = Integer.valueOf(tfTurns.getText());
            } catch (Exception e) {
                turns = 1;
                tfTurns.setText("1");
            }
            for (int i = 0; i < turns; i++) {
                gl.nextTurn();
            }
        } catch (Exception e ) {}
           // System.out.println ("next turn");

    }





    @FXML
    protected void handleSave (ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Simulation");
        File file = fileChooser.showOpenDialog(stage);
        System.out.println ("file " + file);

        try {
            gl.saveFile(file);
        } catch (Exception e) {
            System.err.print (e);
        }
    }


    @FXML
    protected void handleLoad (ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Simulation");
        File file = fileChooser.showOpenDialog(stage);
        try {
            gl.loadFile(file);
        } catch (Exception e) {
            System.out.println ("not loaded");
            System.err.print (e);
        }
    }


    private boolean paused = true;
    private Double turnRate = 0.05;



    @FXML
    protected void handlePlay (ActionEvent event) throws Exception {
        try {
            if (paused) {
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& turn Rate " + turnRate);
                paused = false;
                playBtn.setText("||");
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.setRate(turnRate);
                timeline.play();


            } else {
                paused = true;
                timeline.stop();
                playBtn.setText(">");
            }
        }catch (Exception e ){

        }

    }// end handle play

    @FXML
    protected void handleSpeedChange (ActionEvent event){
        System.out.println("TextField Action");
        try {
            turnRate = Double.valueOf(tfSpeed.getText())*0.05;
            if (turnRate > 1.5 ){
                turnRate = 1.5;
                tfSpeed.setText("30.0");
            }

        } catch (Exception e) {
            System.out.println ("exception in speedchange");
            turnRate = 0.05;
            tfSpeed.setText("1.0");
        }
        System.out.println (")))))))))))))))))))))))))))))))rate changed = "+turnRate);
        timeline.setRate(turnRate);

    }

///////////////////////////////////////////////////////////////////////////////////////////////////// tabele

    private int tableObject; // 0 go ; 1 gl

    private GameObject tmpGoForTable;
    private GameLogic tmpGlForTable;


    public void displayGoStatsInTable (GameObject go){

            tmpGoForTable = go;
         //   tmpGoForTable.setTableConnection();
      //  }
        tableObject = 0;
        processTableGo();
    }

    public void displayGlStatsInTable (GameLogic gl){
        tmpGlForTable = gl ;
        tableObject = 1;
        processTableGl();
    }

    public void processTableGo (){
        tableObject = 0;
        colGoValue.setCellFactory(TextFieldTableCell.<GameParameter>forTableColumn());
        tblGoProperties.setItems (getParam(tmpGoForTable.getRenderedStats()));
    }

    public void processTableGl (){
        tableObject = 1;
        colGoValue.setCellFactory(TextFieldTableCell.<GameParameter>forTableColumn());
        tblGoProperties.setItems (getParam(tmpGlForTable.getRenderedStats()));
    }




    public ObservableList<GameParameter> getParam (Hashtable<String,String> ht){
        System.out.println ("getparam in controller");
        ObservableList<GameParameter> gamePros = FXCollections.observableArrayList();

        Enumeration<String> key = ht.keys();
        while (key.hasMoreElements()) {
            String k = key.nextElement();
            String v = ht.get(k);
            gamePros.add(new GameParameter (k, v));
        }
        return gamePros;
    }





    private void setUpTable () {
        tblGoProperties.setEditable(true);
        tblGoProperties.setEditable(true);
        colGoParameter.setCellValueFactory(new PropertyValueFactory<>("property"));

        colGoValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colGoValue.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GameParameter, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<GameParameter, String> e) {
                if (tableObject==0) {
                    System.out.println ("edditing go");
                    if (!tmpGoForTable.modifyStat((String) colGoParameter.getCellData(e.getTablePosition().getRow()), e.getNewValue())) {
                        processTableGo();
                    }

                }else if (tableObject==1 ){
                    System.out.println ("edditing gl");
                    if (!tmpGlForTable.modifyStat((String) colGoParameter.getCellData(e.getTablePosition().getRow()), e.getNewValue())) {
                        processTableGo();
                    }
                }
            }
        });
    }





///////////////////////////////////////////////////////////////////////////////////////////////////// tabele


    @FXML
    public void initialize(){
        ge.setPaneField(paneField);
        tmpGlForTable = gl;
        setUpTable ();
        processTableGl();

        cbGameLogicSelector.setItems(FXCollections.observableArrayList(
                "JevoSim", "simulation 2.2") // add your stuff here
        );

        cbGameLogicSelector.getSelectionModel().selectedIndexProperty().addListener (new
            ChangeListener<Number>() {
            public void changed (ObservableValue ov, Number value, Number new_value){
                System.out.println(new_value);
                selectedSimulation = new_value;
                switch (selectedSimulation.intValue()) {
                    case 0:
                        gl = new jevosim.GameLogic(ge);
                        break;
                    case 1:
                        gl = new gl21.GameLogic(ge);
                        break;
                    default:
                        gl = new gl21.GameLogic(ge);
                        break;
                }
                displayGlStatsInTable(gl);
            }
        });
    }//ini
}//controller

