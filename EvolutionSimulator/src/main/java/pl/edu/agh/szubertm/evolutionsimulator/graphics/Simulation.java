package pl.edu.agh.szubertm.evolutionsimulator.graphics;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pl.edu.agh.szubertm.evolutionsimulator.logic.World;

public class Simulation extends BorderPane {
    private final World world;
    private boolean isRunning;
    private final int deltaTime = 50;
    private final MapLayout mapLayout;
    private Text days;
    private Text animals;
    private Text grasses;

    Simulation(String parametersFile){
        isRunning = false;
        world = new World(parametersFile);
        setLeft(controls());
        mapLayout = new MapLayout(world.getMap());
        setCenter(mapLayout);


        Thread logicThread = logicThread();
        logicThread.setDaemon(true);
        logicThread.start();
    }

    private VBox controls(){
        days = new Text("Day: " + world.getDay());
        days.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        animals = new Text("Animals: " + world.getAnimalNumber());
        animals.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        grasses = new Text("Grasses: " + world.getGrassNumber());
        grasses.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Button playButton = new Button("Play");
        playButton.setOnAction(this::play);

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(this::pause);

        VBox vBox = new VBox();

        vBox.setSpacing(10);
        VBox.setMargin(days, new Insets(10, 10, 0, 10));
        VBox.setMargin(animals, new Insets(10, 10, 0, 10));
        VBox.setMargin(grasses, new Insets(10, 10, 0, 10));
        VBox.setMargin(playButton, new Insets(10, 10, 0, 10));
        VBox.setMargin(pauseButton, new Insets(10, 10, 0, 10));
        vBox.getChildren().addAll(days, animals, grasses, playButton, pauseButton);

        return vBox;
    }

    private void play(ActionEvent actionEvent) {
        isRunning = true;
    }

    private void pause(ActionEvent actionEvent) {
        isRunning = false;
    }

    private void nextDay(){
        world.nextDay();
        //setLeft(controls());
        days.setText("Day: " + world.getDay());
        animals.setText("Animals: " + world.getAnimalNumber());
        grasses.setText("Grasses: " + world.getGrassNumber());
        mapLayout.update();
    }

    private Thread logicThread(){
        return new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(deltaTime);
                } catch (InterruptedException ignore) {}
                if (isRunning) {
                    Platform.runLater(this::nextDay);
                }
            }
        });
    }
}
