package jevo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.util.Duration;

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

    @FXML
    private ChoiceBox cbGameLogicSelector;



    @FXML
    private TableView<GameParameter> tblGoProperties;

    @FXML
    private TableColumn<GameParameter,String> colGoValue ;

    @FXML
    private TableColumn<GameParameter,String> colGoParameter ;

    GraphicsEngine ge = new GraphicsEngine();
    GameLogic gl;


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
    protected void handleNewGame (ActionEvent event) throws Exception {
        System.out.println ("new game");
        System.out.println (selectedSimulation);
        ge.setPaneField(paneField);
        ge.reset();
        ge.setGoPropTable (tblGoProperties, colGoParameter,colGoValue);

        switch (selectedSimulation.intValue()) {
            case 0:
                gl = new customgl.GameLogic(ge);
                break;
            case 1:
                gl = new gl21.GameLogic(ge);
                break;
            default:
                gl = new gl21.GameLogic(ge);
                break;
        }
        gl.newGame();

      //  setUserAgentStylesheet(STYLESHEET_CASPIAN);



    }
    @FXML
<<<<<<< HEAD
    protected void handleNextTurn (ActionEvent event) throws Exception{
=======
    protected void handleNextTurn (ActionEvent event) throws Exception {
>>>>>>> branch 'master' of https://github.com/team4jevo/jevolution.git
            int turns=0;
        try {
            turns = Integer.valueOf(tfTurns.getText());
        }catch (Exception e){
            turns=1;
            tfTurns.setText("1");
        }
        for (int i=0;i<turns;i++ ){
            gl.nextTurn();
        }

        System.out.println ("next turn");


    }

    private boolean paused = true;
    private Double turnRate = 0.05;



    @FXML
    protected void handlePlay (ActionEvent event) {

        if (paused) {
            System.out.println( "&&&&&&&&&&&&&&&&&&&&&&&& turn Rate "+turnRate);
            paused = false;
            playBtn.setText("||");
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.setRate (turnRate);
            timeline.play();


        } else {
            paused = true;
            timeline.stop();
            playBtn.setText(">");
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

    @FXML
    public void initialize(){


        System.out.println ("inicialize ");
        //paneField.setPadding(new Insets(2,2,2,2));
        cbGameLogicSelector.setItems(FXCollections.observableArrayList(
                "Jevolution Simulation", "simulation 2.2") // add your stuff here
        );

        cbGameLogicSelector.getSelectionModel().selectedIndexProperty().addListener (new
            ChangeListener<Number>() {
            public void changed (ObservableValue ov, Number value, Number new_value){
                System.out.println(new_value);
                selectedSimulation = new_value;
            }
        });

    }//ini




}//controller
