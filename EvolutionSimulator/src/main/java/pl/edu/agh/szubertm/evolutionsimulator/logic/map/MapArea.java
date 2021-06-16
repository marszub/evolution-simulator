package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangeObserver;
import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangePublisher;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Animal;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Grass;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

import java.util.*;

public abstract class MapArea implements IPositionChangeObserver {
    protected final List<MapArea> subareas;
    protected final Map<Vector2d, MapField> mapFields;
    protected final Rectangle borders;
    protected final String color;

    protected MapArea(Rectangle borders, String color){
        this.color = color;
        subareas = new LinkedList<>();
        mapFields = new HashMap<>();
        this.borders = borders;
    }

    public void addSubarea(MapArea area){
        subareas.add(area);
    }

    public abstract boolean contains(Vector2d position);

    protected MapArea areaAt(Vector2d position){
        for (MapArea area: subareas) {
            if(area.contains(position)){
                return area;
            }
        }
        return this;
    }

    protected abstract Vector2d getLocalMove(Vector2d position, Vector2d moveVector);

    public final Vector2d getMove(Vector2d position, Vector2d moveVector){
        MapArea prevArea = areaAt(position);
        Vector2d prevMove;

        if(prevArea == this){
            prevMove = getLocalMove(position, moveVector);
        }
        else{
            prevMove = prevArea.getMove(position, moveVector);
            if(prevArea.contains(prevMove)){
                return prevMove;
            }
        }

        MapArea nextArea = areaAt(prevMove);

        if(nextArea == this){
            return getLocalMove(position, moveVector);
        }
        else{
            return nextArea.getMove(position, moveVector);
        }
    }

    public List<Animal> animalsAt(Vector2d position){
        MapArea area = areaAt(position);
        if(area == this) {
            MapField mapField = mapFields.get(position);
            if (mapField == null)
                return new LinkedList<>();
            return mapField.getAnimals();
        }
        return area.animalsAt(position);
    }

    public Optional<Grass> grassAt(Vector2d position){
        MapArea area = areaAt(position);
        if(area == this) {
            if (!mapFields.containsKey(position))
                return Optional.empty();
            return mapFields.get(position).getGrass();
        }
        return area.grassAt(position);
    }

    public void place(Animal animal){
        Vector2d position = animal.getPosition();
        if(contains(position))
            animal.addObserver(this);
        else
            return;
        MapArea area = areaAt(position);

        if(area == this){
            if(!mapFields.containsKey(position))
                mapFields.put(position, new MapField());
            mapFields.get(position).place(animal);
            return;
        }
        area.place(animal);
    }

    public void place(Grass grass){
        Vector2d position = grass.getPosition();
        if(!contains(position))
            return;
        MapArea area = areaAt(position);

        if(area == this){
            if(!mapFields.containsKey(position))
                mapFields.put(position, new MapField());
            mapFields.get(position).place(grass);
            return;
        }
        area.place(grass);
    }

    public void remove(Animal animal){
        Vector2d position = animal.getPosition();
        if(contains(position))
            animal.removeObserver(this);
        else
            return;
        MapArea area = areaAt(position);

        if(area == this){
            mapFields.get(position).remove(animal);
            if(mapFields.get(position).top().isEmpty())
                mapFields.remove(position);

            return;
        }
        area.remove(animal);
    }

    public void remove(Grass grass){
        Vector2d position = grass.getPosition();
        MapArea area = areaAt(position);

        if(area == this){
            mapFields.get(position).remove(grass);
            if(mapFields.get(position).top().isEmpty())
                mapFields.remove(position);

            return;
        }
        area.remove(grass);
    }

    public Optional<MapElement> top(Vector2d position){
        MapArea area = areaAt(position);
        if(area != this)
            return area.top(position);
        if(!mapFields.containsKey(position))
            return Optional.empty();
        return mapFields.get(position).top();
    }

    public void nextDay(){
        for(MapArea subarea : subareas){
            subarea.nextDay();
        }
    }

    public Rectangle getBorders(){
        return borders;
    }

    public String getColor(Vector2d position){
        MapArea area = areaAt(position);
        if(area == this)
            return color;
        else
            return area.getColor(position);
    }

    public Set<Vector2d> getOccupiedFields(){
        Set<Vector2d> occupiedFields = new HashSet<>(mapFields.keySet());
        for(MapArea area:subareas){
            occupiedFields.addAll(area.getOccupiedFields());
        }
        return occupiedFields;
    }

    public int getGrassNumber(){
        Set<Vector2d> occupied = getOccupiedFields();
        int grasses = 0;
        for(Vector2d field : occupied){
            if(grassAt(field).isPresent())
                grasses++;
        }
        return grasses;
    }

    @Override
    public void positionChanged(IPositionChangePublisher source, MapElement movedElement, Vector2d oldPosition, Vector2d newPosition) {
        MapArea oldArea = areaAt(oldPosition);
        MapArea newArea = areaAt(newPosition);

        //common root
        if(contains(oldPosition) && contains(newPosition) &&
                oldArea !=this && areaAt(newPosition) != this)
            return;

        //old root
        if(contains(oldPosition)){
            source.removeObserver(this);
            if(oldArea == this){
                mapFields.get(oldPosition).remove((Animal) movedElement);
                if (mapFields.get(oldPosition).isEmpty())
                    mapFields.remove(oldPosition);
            }
        }

        //new root
        if(contains(newPosition)) {
            source.addObserver(this);
            if(newArea == this){
                if(!mapFields.containsKey(newPosition))
                    mapFields.put(newPosition, new MapField());
                mapFields.get(newPosition).place((Animal) movedElement);
            }else
                newArea.positionChanged(source, movedElement, oldPosition, newPosition);
        }
    }
}
