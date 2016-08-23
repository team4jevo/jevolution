import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class Controller {
    // this class processes user events and calls needed methods of GameLogic

    GraphicsEngine ge = new GraphicsEngine();


    GameLogic gl = new GameLogic (ge);


    Timeline timeline = new Timeline (new KeyFrame(
            Duration.millis(500),
            ae -> {
                System.out.println ("tick");
                gl.nextTurn ();
            }
    ));




    //when new game logic object is created graphic engine is passed to it in constructor
    @FXML
    private Pane paneField;

    @FXML
    private Button playBtn;

    @FXML
    protected TextField tfTurns ;

    @FXML
    protected TextField tfSpeed ;

    @FXML
    protected void handleNewGame (ActionEvent event) {
        System.out.println ("new game");
        ge.setPaneField(paneField);
        ge.reset();
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
            gl.nextTurn();
        }

        System.out.println ("next turn");

    }

    private boolean paused = true;




    @FXML
    protected void handlePlay (ActionEvent event) {
        System.out.println ("handle play TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        int turnDuration;
        try {
            turnDuration = Integer.valueOf(tfSpeed.getText());
        } catch (Exception e) {
            turnDuration = 500;
        }
        if (paused) {
            // change btn symbol
            paused = false;
            playBtn.setText("||");
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.setDelay(Duration.millis(turnDuration));
            timeline.play();


        } else {
            paused = true;
            timeline.stop();
            playBtn.setText(">");
        }

        // if poused

    }// end handle play



}
