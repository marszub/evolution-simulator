package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Animal;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Grass;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.MapElement;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class MapField {
    protected final List<Animal> animals;
    protected Grass grass;

    public MapField() {
        animals = new LinkedList<>();
        grass = null;
    }

    public void place(Animal element){
        ListIterator<Animal> iterator = animals.listIterator();
        while(iterator.hasNext()) {
            MapElement e = iterator.next();
            if(MapElement.elementsTypeOrder.get(element.getClass().toString()) <= MapElement.elementsTypeOrder.get(e.getClass().toString()))
            {
                iterator.previous();
                break;
            }
        }
        iterator.add(element);
    }

    public void remove(Animal animal){
        ListIterator<Animal> iterator = animals.listIterator();
        while(true) {
            Animal e = iterator.next();
            if(animal == e)
                break;

            if(!iterator.hasNext())
                throw new IllegalArgumentException("There is no matching animal on field.");
        }
        iterator.remove();
    }

    public void place(Grass grass){
        if(this.grass != null)
            throw new IllegalArgumentException("More than 1 grass on field");
        this.grass = grass;
    }

    public void remove(Grass grass){
        if(grass != this.grass)
            throw new IllegalArgumentException("Grasses do not mach");
        this.grass = null;
    }

    public Optional<MapElement> top() {
        if(animals.isEmpty())
            return grass == null ? Optional.empty() : Optional.of(grass);
        return Optional.of(animals.get(0));
    }

    public List<Animal> getAnimals(){
        return new LinkedList<>(animals);
    }

    public Optional<Grass> getGrass(){
        return grass == null ? Optional.empty() : Optional.of(grass);
    }

    public boolean isEmpty(){
        return grass == null && animals.isEmpty();
    }
}

