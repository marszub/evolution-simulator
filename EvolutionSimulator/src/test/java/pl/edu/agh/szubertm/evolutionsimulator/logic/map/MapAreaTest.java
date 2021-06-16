package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.MapDirection;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Animal;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class MapAreaTest {
    protected MapArea area;
    protected abstract MapArea createArea(Rectangle borders, String color);

    @BeforeEach
    private void beforeEach(){
        area = createArea(new Rectangle(new Vector2d(0, 0), new Vector2d(20, 20)), "#000000");
    }

    @Test
    public void areaAtCurrent(){
        assertEquals(area, area.areaAt(new Vector2d(5, 0)));
    }

    @Test
    public void areaAtInside(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(1,2), new Vector2d(2, 4)), "#000000");
        area.addSubarea(inside);
        assertEquals(inside, area.areaAt(new Vector2d(2, 3)));
    }

    @Test
    public void getMoveCurrent(){
        assertEquals(new Vector2d(1, 2), area.getMove(new Vector2d(2, 2), new Vector2d(-1, 0)));
    }

    @Test
    public void getMoveInto(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(0,0), new Vector2d(1, 4)), "#000000");
        area.addSubarea(inside);
        assertEquals(new Vector2d(1, 2), area.getMove(new Vector2d(2, 2), new Vector2d(-1, 0)));
    }

    @Test
    public void getMoveOut(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(0,0), new Vector2d(1, 4)), "#000000");
        area.addSubarea(inside);
        assertEquals(new Vector2d(2, 2), area.getMove(new Vector2d(1, 2), new Vector2d(1, 0)));
    }

    @Test
    public void positionChangedCleaning(){
        Animal animal = new Animal(area, new Vector2d(1, 1), MapDirection.NORTH);
        animal.changePosition(new Vector2d(1, 2));
        assertEquals(new LinkedList<MapElement>(), area.animalsAt(new Vector2d(1, 1)));
    }

    @Test
    public void positionChangedAdding(){
        Animal animal = new Animal(area, new Vector2d(1, 1), MapDirection.NORTH);
        animal.changePosition(new Vector2d(1, 2));
        List<MapElement> list = new LinkedList<>();
        list.add(animal);
        assertEquals(list, area.animalsAt(new Vector2d(1, 2)));
    }

    @Test
    public void positionChangedCleaningInto(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(2,0), new Vector2d(3, 4)), "#000000");
        area.addSubarea(inside);
        Animal animal = new Animal(area, new Vector2d(1, 1), MapDirection.NORTH);
        animal.changePosition(new Vector2d(2, 1));
        assertEquals(new LinkedList<MapElement>(), area.animalsAt(new Vector2d(1, 1)));
    }

    @Test
    public void positionChangedAddingInto(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(0,2), new Vector2d(1, 4)), "#000000");
        area.addSubarea(inside);
        Animal animal = new Animal(area, new Vector2d(1, 1), MapDirection.NORTH);
        animal.changePosition(new Vector2d(1, 2));
        List<MapElement> list = new LinkedList<>();
        list.add(animal);
        assertEquals(list, area.animalsAt(new Vector2d(1, 2)));
    }

    @Test
    public void positionChangedCleaningOutside(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(0,0), new Vector2d(1, 4)), "#000000");
        area.addSubarea(inside);
        Animal animal = new Animal(area, new Vector2d(1, 1), MapDirection.NORTH);
        animal.changePosition(new Vector2d(2, 1));
        assertEquals(new LinkedList<MapElement>(), area.animalsAt(new Vector2d(1, 1)));
    }

    @Test
    public void positionChangedAddingOutside(){
        MapArea inside = new Jungle(new Rectangle(new Vector2d(0,0), new Vector2d(1, 1)), "#000000");
        area.addSubarea(inside);
        Animal animal = new Animal(area, new Vector2d(0, 0), MapDirection.NORTH);
        animal.changePosition(new Vector2d(1, 2));
        List<MapElement> list = new LinkedList<>();
        list.add(animal);
        assertEquals(list, area.animalsAt(new Vector2d(1, 2)));
    }
}