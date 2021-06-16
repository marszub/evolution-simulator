package pl.edu.agh.szubertm.evolutionsimulator.graphics;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class Multiscene extends GridPane{
    public Multiscene(int scenesNumber){
        add(new Simulation("parameters.json"),0,0);
        getColumnConstraints().add(GridGenerator.column(100));
        if(scenesNumber == 1)
            getRowConstraints().add(GridGenerator.row(100));
        else if(scenesNumber == 2){
            this.add(new Simulation("parameters.json"),0,2);
            this.getRowConstraints().add(GridGenerator.row(49.5));
            this.getRowConstraints().add(GridGenerator.row(1));
            this.getRowConstraints().add(GridGenerator.row(49.5));
        }else{
            System.out.println("Wrong number of simulations.");
            Platform.exit();
        }
    }
}
