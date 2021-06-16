package pl.edu.agh.szubertm.evolutionsimulator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.edu.agh.szubertm.evolutionsimulator.graphics.JsonReader;
import pl.edu.agh.szubertm.evolutionsimulator.graphics.Multiscene;

import java.util.Arrays;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        int scenesNumber = 1;
        try{
            scenesNumber = (new JsonReader("parameters.json")).parallelSimulations;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Exception while reading json.");
            alert.setContentText(e.getMessage());
            alert.setOnHidden(a -> Platform.exit());
            alert.showAndWait();
        }

        if(scenesNumber != 1 && scenesNumber != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong scenes number in json file!");
            alert.setContentText("Expected: 1 or 2\nActual: " + scenesNumber);
            alert.setOnHidden(e -> Platform.exit());
            alert.showAndWait();
        }

        try {
            stage.setScene(new Scene(new Multiscene(scenesNumber), 900, 450 * (double) scenesNumber));
            stage.setTitle("Evolution simulator");
            stage.show();
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Exception while setting app.");
            alert.setContentText(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            alert.setOnHidden(a -> Platform.exit());
            alert.showAndWait();
        }
    }
}
