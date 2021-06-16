package pl.edu.agh.szubertm.evolutionsimulator.graphics;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.MapArea;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Animal;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Grass;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MapLayout extends GridPane {
    MapArea map;
    List<Pane> mapElements = new LinkedList<>();
    MapLayout(MapArea map){
        this.map = map;
        updateSize();
        paintBackground();
        update();
    }

    private void updateSize(){
        int width = map.getBorders().getXsize()+2;
        for(int i = 0; i <  width; i++)
        {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/((double) width));
            this.getColumnConstraints().add(column);
        }
        int height = map.getBorders().getYsize()+2;
        for (int i = 0; i < height; i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/((double) height));
            this.getRowConstraints().add(row);
        }
    }

    private void paintBackground(){
        for(int i = 0; i< map.getBorders().getXsize()+1; i++){
            for(int j = 0; j<map.getBorders().getYsize()+1; j++){
                Pane field = new Pane();
                String color = map.getColor(new Vector2d(i, j));
                field.setStyle("-fx-background-color: " + color + ";");
                add(field, i, j);
            }
        }
    }

    public void update(){
        getChildren().removeAll(mapElements);
        mapElements.clear();
        Set<Vector2d> occupiedFields = map.getOccupiedFields();
        for(Vector2d field:occupiedFields){
            Optional<MapElement> mapElement = map.top(field);
            if(mapElement.isEmpty())
                //throw new RuntimeException("Field is occupied and empty at the same time.");
                continue;
            if(mapElement.get().getClass() == Animal.class){
                draw((Animal) mapElement.get());
            }else if(mapElement.get().getClass() == Grass.class){
                draw((Grass) mapElement.get());
            }
        }
    }

    private void draw(Animal animal){
        Pane pane = new Pane();
        pane.setShape(new Circle(0.05));
        if(animal.getEnergy() <= Animal.getStartEnergy()/2)
            pane.setStyle("-fx-background-color: #99ccff");
        else if(animal.getEnergy() <= Animal.getStartEnergy()*1.5)
            pane.setStyle("-fx-background-color: #3333ff");
        else
            pane.setStyle("-fx-background-color: #000066");

        add(pane, animal.getPosition().x, animal.getPosition().y);
        mapElements.add(pane);
    }

    private void draw(Grass grass){
        Pane pane = new Pane();
        Rectangle shape = new Rectangle(0.1, 0.1);
        shape.setArcHeight(0.02);
        shape.setArcWidth(0.02);
        pane.setShape(shape);

        pane.setStyle("-fx-background-color: #ff6600");

        add(pane, grass.getPosition().x, grass.getPosition().y);
        mapElements.add(pane);
    }
}
