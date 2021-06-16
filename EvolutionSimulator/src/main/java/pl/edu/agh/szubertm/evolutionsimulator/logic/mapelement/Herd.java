package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import pl.edu.agh.szubertm.evolutionsimulator.logic.RandomGenerator;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.map.MapArea;

import java.util.*;

public class Herd {
    private final List<Animal> animals;
    private final MapArea map;

    public Herd(MapArea map) {
        this.map = map;
        animals = new LinkedList<>();
    }

    public void add(Animal animal){
        animals.add(animal);
    }

    private void moveAnimals(){
        for(Animal animal : animals){
            animal.move();
        }
    }

    public void remove(Animal animal){
        map.remove(animal);
        animals.remove(animal);
    }

    private void removeDeadAnimals(){
        for(int i = 0; i<animals.size(); i++)
            if (!animals.get(i).isAlive()) {
                remove(animals.get(i));
                i--;
            }
    }

    private void eat(){
        for(Vector2d position : map.getOccupiedFields()){
            Optional<Grass> grassOptional = map.grassAt(position);
            if(grassOptional.isEmpty())
                continue;
            Grass grass = grassOptional.get();

            List<Animal> fightingAnimals = map.animalsAt(position);
            if(fightingAnimals.isEmpty())
                continue;

            int equalAnimals = 0;
            int maxEnergy = fightingAnimals.get(0).getEnergy();
            for(Animal fightingAnimal : fightingAnimals) {
                if(maxEnergy == fightingAnimal.getEnergy())
                    equalAnimals = equalAnimals + 1;
                else if(maxEnergy<fightingAnimal.getEnergy()){
                    equalAnimals = 1;
                    maxEnergy = fightingAnimal.getEnergy();
                }
            }
            int baseEnergy = grass.getEnergy()/equalAnimals;
            ListIterator<Integer> richerAnimalsIt = RandomGenerator.getListIndexes(equalAnimals, grass.getEnergy()%equalAnimals).listIterator();
            int richerAnimal = -1;
            if(richerAnimalsIt.hasNext())
                richerAnimal = richerAnimalsIt.next();
            ListIterator<Animal> fightingAnimalsIt = fightingAnimals.listIterator();
            for (int i = 0; i<equalAnimals;i++) {
                Animal fightingAnimal = fightingAnimalsIt.next();
                fightingAnimal.eat(baseEnergy);
                if (i == richerAnimal) {
                    fightingAnimal.eat(1);
                    if (richerAnimalsIt.hasNext())
                        richerAnimal = richerAnimalsIt.next();
                }
            }
            map.remove(grass);
        }
    }

    private void callStork(){
        Set<Vector2d> visitedByStork = new HashSet<>();
        List<Animal> currentAnimals = new LinkedList<>(animals);
        for(Animal animal : currentAnimals){
            Vector2d position = animal.getPosition();
            if(visitedByStork.contains(position))
                continue;
            visitedByStork.add(position);
            List<Animal> friendAnimals = map.animalsAt(animal.getPosition());
            if(friendAnimals.size()<=1)
                continue;

            //TODO: randomize parents
            Animal parent1 = friendAnimals.get(0);
            Animal parent2 = friendAnimals.get(1);
            if(parent1.canBreed() && parent2.canBreed())
                add(new Animal(parent1, parent2));
        }
    }

    public void nextDay(){
        removeDeadAnimals();
        moveAnimals();
        eat();
        callStork();
    }

    public int getAnimalNumber(){
        return animals.size();
    }
}