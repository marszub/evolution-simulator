package pl.edu.agh.szubertm.evolutionsimulator.logic;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

public interface IPositionChangeObserver {
    void positionChanged(IPositionChangePublisher source, MapElement movedElement, Vector2d oldPosition, Vector2d newPosition);
}
