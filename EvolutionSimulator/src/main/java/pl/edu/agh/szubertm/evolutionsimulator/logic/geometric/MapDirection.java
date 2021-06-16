package pl.edu.agh.szubertm.evolutionsimulator.logic.geometric;

import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangeObserver;
import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangePublisher;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum MapDirection implements IPositionChangeObserver {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    private static final List<Vector2d> directionVectors;
    static {
        directionVectors = new ArrayList<>();
        Collections.addAll(directionVectors,
                new Vector2d(0, 1),
                new Vector2d(1, 1),
                new Vector2d(1, 0),
                new Vector2d(1, -1),
                new Vector2d(0, -1),
                new Vector2d(-1, -1),
                new Vector2d(-1, 0),
                new Vector2d(-1, 1));
    }

    private int toInt(){
        return switch (this){
            case NORTH      -> 0;
            case NORTH_EAST -> 1;
            case EAST       -> 2;
            case SOUTH_EAST -> 3;
            case SOUTH      -> 4;
            case SOUTH_WEST -> 5;
            case WEST       -> 6;
            case NORTH_WEST -> 7;
        };
    }

    public static MapDirection valueOf(int value){
        return switch (value) {
            case 0 -> NORTH;
            case 1 -> NORTH_EAST;
            case 2 -> EAST;
            case 3 -> SOUTH_EAST;
            case 4 -> SOUTH;
            case 5 -> SOUTH_WEST;
            case 6 -> WEST;
            case 7 -> NORTH_WEST;
            default -> throw new IllegalArgumentException("Integer: " + value + " does not suit MapDirection values. (0-7)");
        };
    }

    public Vector2d toUnitVector(){
        return directionVectors.get(toInt());
    }

    public MapDirection rotated(Rotation rotation){
        return valueOf((toInt() + rotation.toInt())%8);
    }

    @Override
    public void positionChanged(IPositionChangePublisher source, MapElement movedAnimal, Vector2d oldPosition, Vector2d newPosition){
        throw new UnsupportedOperationException("Not implemented: MapDirection.positionChanged");
    }
}
