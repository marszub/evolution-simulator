package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

public class Grass extends MapElement {
    static{
        MapElement.elementsTypeOrder.put(Grass.class.toString(), 2);
    }
    private final Vector2d position;
    private static int defaultEnergy = 1;
    public static void setDefaultEnergy(int energy){
        defaultEnergy = energy;
    }
    private final int energy;

    public Grass(Vector2d position) {
        this.position = position;
        energy = defaultEnergy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy(){
        return energy;
    }
}