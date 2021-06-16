package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rotation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DNATest {

    @Test
    void size() {
        List<Rotation> rotations = new LinkedList<>();
        rotations.add(Rotation.BACK);
        rotations.add(Rotation.BACK);
        rotations.add(Rotation.FORWARD);
        rotations.add(Rotation.LEFT);
        DNA dna = new DNA(rotations);
        assertEquals(4, dna.size());
    }

    @Test
    void get() {
        List<Rotation> rotations = new LinkedList<>();
        rotations.add(Rotation.BACK);
        rotations.add(Rotation.BACK);
        rotations.add(Rotation.FORWARD);
        rotations.add(Rotation.LEFT);
        DNA dna = new DNA(rotations);
        assertEquals(Rotation.FORWARD, dna.get(2));
    }

    @Test
    void getBordersSize(){
        List<Integer> borders = DNA.getSplitBorders(2);
        assertEquals(4, borders.size());
    }

    @Test
    void getBordersSides(){
        List<Integer> borders = DNA.getSplitBorders(2);
        assertEquals(new Pair<>(0, 32), new Pair<>(borders.get(0), borders.get(borders.size()-1)));
    }

    @Test
    void getBordersRising(){
        List<Integer> borders = DNA.getSplitBorders(4);
        for(int i = 0; i<5; i++){
            assertTrue(borders.get(i)<borders.get(i+1));
        }
    }

    @Test
    void getParentsSize(){
        List<Integer> parents = DNA.getParentsOrder(7);
        assertEquals(8, parents.size());
    }

    @Test
    void getParentsContainsAllParents(){
        int parentsNumber = 7;
        List<Integer> parents = DNA.getParentsOrder(parentsNumber);
        List<Integer> allParents = new ArrayList<>(parentsNumber);
        for(int i = 0; i<parentsNumber;i++){
            allParents.add(i);
        }

        assertTrue(parents.containsAll(allParents));
    }

    @Test
    void getParentsNoOtherIndexes(){
        int parentsNumber = 7;
        List<Integer> parents = DNA.getParentsOrder(parentsNumber);
        List<Integer> allParents = new ArrayList<>(parentsNumber);
        for(int i = 0; i<parentsNumber;i++){
            allParents.add(i);
        }

        assertTrue(allParents.containsAll(parents));
    }

    @Test
    void reproductionSameStrands(){
        DNA dna1 = new DNA();
        DNA dna2 = new DNA();
        assertEquals(dna1, new DNA(dna1, dna2));
    }
}