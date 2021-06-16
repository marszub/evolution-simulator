package pl.edu.agh.szubertm.evolutionsimulator.logic;

import pl.edu.agh.szubertm.evolutionsimulator.graphics.JsonReader;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.MapDirection;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.Jungle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.MapArea;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.WrappedMap;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Animal;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Grass;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Herd;

public class World {
    private int day = 0;
    private final MapArea map;
    private final Herd herd;
    public World(String parametersFile){
        JsonReader parameters = new JsonReader(parametersFile);

        Animal.setStartEnergy(parameters.startEnergy);
        Animal.setMoveEnergy(parameters.moveEnergy);
        Grass.setDefaultEnergy(parameters.plantEnergy);

        map = new WrappedMap(new Rectangle(new Vector2d(0, 0), new Vector2d(parameters.width, parameters.height)), "#666633");
        double sqrtRatio = Math.sqrt(parameters.jungleRatio);
        int jungleWidth =(int) (parameters.width * sqrtRatio);
        int jungleHeight =(int) (parameters.height * sqrtRatio);
        Vector2d jungleBottomLeft = new Vector2d((parameters.width - jungleWidth)/2, (parameters.height - jungleHeight)/2);
        Vector2d jungleTopRight = jungleBottomLeft.add(new Vector2d(jungleWidth, jungleHeight));
        map.addSubarea(new Jungle(new Rectangle(jungleBottomLeft, jungleTopRight), "#006600"));

        herd = new Herd(map);
        populateMap(parameters.numberOfAnimals);


    }

    private void populateMap(int animalsNumber){
        for(int i = 0; i < animalsNumber; i++){
            Vector2d position = RandomGenerator.getPosition(map.getBorders());
            MapDirection direction = RandomGenerator.getEnum(MapDirection.class);
            herd.add(new Animal(map, position, direction));
        }
    }

    public void nextDay(){
        day++;
        herd.nextDay();
        map.nextDay();
    }

    public int getDay(){
        return day;
    }

    public int getAnimalNumber(){
        return herd.getAnimalNumber();
    }

    public int getGrassNumber(){
        return map.getGrassNumber();
    }

    public MapArea getMap(){
        return map;
    }
}
