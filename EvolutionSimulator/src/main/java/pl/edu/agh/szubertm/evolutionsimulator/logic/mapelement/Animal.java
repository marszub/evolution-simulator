package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangeObserver;
import pl.edu.agh.szubertm.evolutionsimulator.logic.IPositionChangePublisher;
import pl.edu.agh.szubertm.evolutionsimulator.logic.RandomGenerator;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.MapDirection;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.MapArea;

import java.util.LinkedList;
import java.util.List;

public class Animal extends MapElement implements IPositionChangePublisher {
    static{
        MapElement.elementsTypeOrder.put(Animal.class.toString(), 1);

    }
    private static int moveEnergy = 1;
    public static void setMoveEnergy(int moveEnergy){
        Animal.moveEnergy = moveEnergy;
    }
    private static int startEnergy = 10;
    public static void setStartEnergy(int startEnergy){Animal.startEnergy = startEnergy;}
    public static int getStartEnergy(){return Animal.startEnergy;}

    private final List<IPositionChangeObserver> observers = new LinkedList<>();
    private final MapArea map;
    private Vector2d position;
    private MapDirection facing;
    private final DNA dna;
    private Integer energy;

    public Animal(MapArea map, Vector2d position, MapDirection facing){
        this.map = map;
        this.position = position;
        this.facing = facing;
        energy = startEnergy;
        dna = new DNA();
        map.place(this);
    }

    Animal(Animal parent1, Animal parent2){
        if(!parent1.canBreed() || !parent2.canBreed())
            throw new IllegalArgumentException("In reproduction parents must have at least 1/2 of startEnergy. Parents energy: "
                    + parent1.energy + ", " + parent2.energy + ". startEnergy: "+ startEnergy);
        if(parent1 == parent2)
            throw new IllegalArgumentException("Animal has only one parent");
        if(!parent1.getPosition().equals(parent2.getPosition()))
            throw new IllegalArgumentException("Parents are on different positions");


        map = parent1.map;
        facing = RandomGenerator.getEnum(MapDirection.class);
        position = map.getMove(parent1.position, facing.toUnitVector());
        dna = new DNA(parent1.dna, parent2.dna);

        energy = parent1.energy/4 + parent2.energy/4;
        parent1.energy -= parent1.energy/4;
        parent2.energy -= parent2.energy/4;

        map.place(this);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public boolean isAlive(){
        return energy>=0;
    }

    public boolean canBreed(){
        return energy*2>=startEnergy;
    }

    public void move(){
        facing = facing.rotated(RandomGenerator.getFromList(dna.getStrand()));
        Vector2d newPos = map.getMove(position, facing.toUnitVector());
        changePosition(newPos);
        energy-=moveEnergy;
    }

    public void changePosition(Vector2d newPosition){
        if(position.equals(newPosition))
            return;

        Vector2d oldPosition = position;
        position = newPosition;

        List<IPositionChangeObserver> observersToCall = new LinkedList<>(observers);
        for(IPositionChangeObserver observer : observersToCall){
            observer.positionChanged(this, this, oldPosition, position);
        }
    }

    @Override
    public int compareTo(MapElement o) {
        int sCompare = super.compareTo(o);
        if(sCompare == 0) {
            return this.energy.compareTo(((Animal)o).energy);
        }
        return sCompare;
    }

    public Integer getEnergy(){
        return energy;
    }

    public void eat(int energy){
        this.energy += energy;
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }
}
