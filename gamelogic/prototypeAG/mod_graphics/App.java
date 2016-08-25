package jevo;

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
        System.out.println("start");
      //  System.out.println(getClass().getResource("ui.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));

     //   FXMLLoader fxmlLoader = new FXMLLoader();
    //    Parent root = (Parent) fxmlLoader.load(getClass().getResource("ui.fxml"));
     //   Controller controller = (Controller) fxmlLoader.getController();
     //   System.out.print (" controller "+ controller);
        //controller.setStage(stage);


     //   Controller c = (Controller) fxmlLoader.g

        //root contains controller inside;
        System.out.println("root loaded");
        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().add("jevo/style.css");
        stage.setScene (scene);
        stage.show();
    }
}