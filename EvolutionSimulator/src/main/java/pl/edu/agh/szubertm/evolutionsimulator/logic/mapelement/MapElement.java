package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class MapElement implements Comparable<MapElement> {
    public static Map<String, Integer> elementsTypeOrder = new HashMap<>();

    static Map<String, Integer> getElementsTypeOrder() {
        return Collections.unmodifiableMap(elementsTypeOrder);
    }

    public abstract Vector2d getPosition();

    public int compareTo(MapElement o) {
        if(!elementsTypeOrder.containsKey(this.getClass().toString()))
            throw new IllegalArgumentException("Class of /this/ must be defined in elementsTypeOrder map. Actual class is: " + this.getClass().toString() + "\nPossible values are: \n"+elementsTypeOrder);
        if(!elementsTypeOrder.containsKey(o.getClass().toString()))
            throw new IllegalArgumentException("Class of argument must be defined in elementsTypeOrder map. Actual class is: " + this.getClass().toString());
        return elementsTypeOrder.getOrDefault(this.getClass().toString(), 1000).compareTo(elementsTypeOrder.getOrDefault(o.getClass().toString(), 1000));
    }
}