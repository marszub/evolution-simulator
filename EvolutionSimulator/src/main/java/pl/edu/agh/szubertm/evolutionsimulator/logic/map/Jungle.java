package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

public class Jungle extends GrassField {
    public Jungle(Rectangle borders, String color) {
        super(borders, color);
    }

    @Override
    public boolean contains(Vector2d point) {
        return borders.isInside(point);
    }

    @Override
    protected Vector2d getLocalMove(Vector2d position, Vector2d moveVector) {
        return position.add(moveVector);
    }
}