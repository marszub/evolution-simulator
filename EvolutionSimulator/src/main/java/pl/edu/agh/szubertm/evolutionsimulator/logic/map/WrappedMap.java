package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

public class WrappedMap extends GrassField {

    public WrappedMap(Rectangle borders, String color) {
        super(borders, color);
    }

    @Override
    public boolean contains(Vector2d position) {
        if(position == null) throw new NullPointerException("Position can't be null.");
        return true;
    }

    @Override
    protected Vector2d getLocalMove(Vector2d position, Vector2d moveVector) {
        int x = (position.x + moveVector.x - borders.getBotLeft().x + borders.getTopRight().x - borders.getBotLeft().x + 1) %
                (borders.getTopRight().x - borders.getBotLeft().x + 1) + borders.getBotLeft().x;

        int y = (position.y + moveVector.y - borders.getBotLeft().y + borders.getTopRight().y - borders.getBotLeft().y + 1) %
                (borders.getTopRight().y - borders.getBotLeft().y + 1) + borders.getBotLeft().y;

        return new Vector2d(x,y);
    }
}
