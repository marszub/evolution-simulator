package pl.edu.agh.szubertm.evolutionsimulator.logic;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rectangle;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Vector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class RandomGenerator {
    private static final Random generator = new Random();

    static public List<Integer> getListIndexes(int listSize, int n){
        List<Integer> indexes = new LinkedList<>();
        for(int i = 0; i<n; i++) {
            Integer randomIndex = generator.nextInt(listSize - i);
            ListIterator<Integer> it = indexes.listIterator();
            while (it.hasNext()) {
                if (it.next() <= randomIndex)
                    randomIndex++;
            }
            it.add(randomIndex);
        }
        return indexes;
    }

    static public Vector2d getPosition(Rectangle area){
        if(area.getXsize()<=0 || area.getYsize() <= 0)
            throw new IllegalArgumentException("Area size must be positive");
        int x = generator.nextInt(area.getXsize()+1) + area.getBotLeft().x;
        int y = generator.nextInt(area.getYsize()+1)+area.getBotLeft().y;
        return new Vector2d(x, y);
    }

    static public <E extends Enum<E>> E getEnum(Class<E> enumType){
        E[] constants = enumType.getEnumConstants();
        return constants[generator.nextInt(constants.length)];
    }

    static public <T> T getFromList(List<T> list){
        return list.get(generator.nextInt(list.size()));
    }
}
