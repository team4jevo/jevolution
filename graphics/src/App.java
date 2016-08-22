import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by LongJohn on 8/19/2016.
 */
public class App extends Application {
// this class is an entry point for application

    public static void main(String[] args) {
        launch (args);
    }


    @Override
    public void start (Stage stage ) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
        // root contains controller somewhere inside;

        Scene scene = new Scene(root, 800, 800);
        stage.setScene (scene);
        stage.show();
    }
}