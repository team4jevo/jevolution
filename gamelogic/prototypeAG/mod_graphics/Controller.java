package jevo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by LongJohn on 8/19/2016.
 */
//when new game logic object is created graphic engine is passed to it in constructor
public class Controller {
    // this class processes user events and calls needed methods of GameLogic









    @FXML
    private TableView<GameParameter> tblGoProperties;

    @FXML
    private TableColumn<GameParameter,String> colGoValue ;

    @FXML
    private TableColumn<GameParameter,String> colGoParameter ;

    GraphicsEngine ge = new GraphicsEngine();
    GameLogic gl; //= new GameLogic (ge);


    Timeline timeline = new Timeline (new KeyFrame(
            Duration.millis(20),
            ae -> {
                System.out.println ("tick");
                try {
                    gl.nextTurn ();
                } catch (Exception e) {
                    e.printStackTrace();
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
        ge.setPaneField(paneField);
        ge.reset();
        ge.setGoPropTable (tblGoProperties, colGoParameter,colGoValue);
        gl = new customgl.GameLogic(ge);
        gl.newGame();


    }
    @FXML
    protected void handleNextTurn (ActionEvent event){
            int turns=0;
        try {
            turns = Integer.valueOf(tfTurns.getText());
        }catch (Exception e){
            turns=1;
            tfTurns.setText("1");
        }
        for (int i=0;i<turns;i++ ){
            try {
                gl.nextTurn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println ("next turn");


    }

    private boolean paused = true;
    private Double turnRate = 0.05;



    @FXML
    protected void handlePlay (ActionEvent event) {
        System.out.println ("handle play TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
//        Double turnRate=0.05;


        if (paused) {
            System.out.println( "turn Rate "+turnRate);
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
            if (turnRate > 0.5 ){
                turnRate = 0.5;
                tfSpeed.setText("10.0");
            }

        } catch (Exception e) {
            turnRate = 0.05;
            tfSpeed.setText("1.0");
        }
        timeline.setRate(turnRate);

    }







}//controller
