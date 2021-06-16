package pl.edu.agh.szubertm.evolutionsimulator.logic.map;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;
import pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement.Grass;

import java.util.Random;

public abstract class GrassField extends MapArea {
    private static final int drawTries = 100;
    protected Random generator = new Random();


    //TODO: fix placing grasses
    //protected final Set<Vector2d> freeFields;

    protected GrassField(Rectangle borders, String color) {
        super(borders, color);
    }

    private boolean canPlaceGrass(Vector2d position){
        return areaAt(position) == this &&(!mapFields.containsKey(position));
    }

    @Override
    public void nextDay(){
        super.nextDay();
        Vector2d randPos;
        for(int i = 0; i < drawTries; i++){
            int x = generator.nextInt(borders.getXsize()+1) + borders.getBotLeft().x;
            int y = generator.nextInt(borders.getYsize()+1) + borders.getBotLeft().y;
            randPos = new Vector2d(x, y);
            if(canPlaceGrass(randPos)) {
                place(new Grass(randPos));
                return;
            }
        }
        for(int i = 0; i< borders.getXsize(); i++)
            for(int j = 0; j<borders.getYsize(); j++){
                Vector2d v = new Vector2d(i, j);
                if(canPlaceGrass(v)) {
                    place(new Grass(v));
                    return;
                }
            }
    }
}
