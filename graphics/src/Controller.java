import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class Controller {
    // this class processes user events and calls needed methods of GameLogic

    GraphicsEngine ge = new GraphicsEngine();
    GameLogic gl = new GameLogic (ge);

    @FXML
    private Pane paneField;

    @FXML
    protected TextField tfTurns ;

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


    @FXML
    protected void handlePlay (ActionEvent event) {

    }

}
