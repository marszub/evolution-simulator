package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import org.junit.jupiter.api.Test;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WrappedMapTest extends MapAreaTest{
    @Override
    protected MapArea createArea(Rectangle borders, String color) {
        return new WrappedMap(borders, color);
    }

    @Test
    public void getMoveTeleport(){
        assertEquals(new Vector2d(0,0), area.getMove(new Vector2d(20, 20), new Vector2d(1, 1)));
    }
}