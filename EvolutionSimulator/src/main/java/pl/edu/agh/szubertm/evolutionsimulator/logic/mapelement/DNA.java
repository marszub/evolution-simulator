package pl.edu.agh.szubertm.evolutionsimulator.logic.mapelement;

import pl.edu.agh.szubertm.evolutionsimulator.logic.geometric.Rotation;

import java.util.*;

public class DNA {
    private final static int strandLength = 32;
    private final List<Rotation> strand;
    private static Random generator = new Random();
    public static void setGenerator(Random generator){
        DNA.generator = generator;
    }

    public DNA(){
        strand = new ArrayList<>(strandLength);
        for(int i = 0; i<25; i++)
            strand.add(Rotation.FORWARD);
        for(int i = 1; i<8;i++)
            strand.add(Rotation.valueOf(i));
    }

    public DNA(List<Rotation> strand) {
        this.strand = strand;
    }

    public DNA(DNA parent1, DNA parent2){
        strand = new ArrayList<>(strandLength);

        List<DNA> parents = new ArrayList<>();
        parents.add(parent1);
        parents.add(parent2);

        createFromParents(parents);
    }

    private void createFromParents(List<DNA> parents){
        int size = parents.size();
        if(size >=strandLength-1)
            throw new IllegalArgumentException("Too many parents: " + size + "Max parents number is: " + (strandLength-1));

        List<Integer> borders = getSplitBorders(size);

        List<Integer> parentsOrder = getParentsOrder(size);

        for(int i = 0; i<size+1; i++){
            addPartFrom(parents.get(parentsOrder.get(i)), borders.get(i), borders.get(i+1));
        }

        repair();
    }

    public static List<Integer> getSplitBorders(int parentsNumber){
        List<Integer> indexes = new LinkedList<>();
        indexes.add(0);
        indexes.add(strandLength);
        for(int i = 0; i < parentsNumber; i++){
            int randIndex = generator.nextInt(strandLength - 1 - i);
            for(int j = 0; j < indexes.size(); j++){
                if(randIndex>=indexes.get(j)) {
                    randIndex++;
                }
                else{
                    indexes.add(j, randIndex);
                    break;
                }
            }
        }
        return indexes;
    }

    public static List<Integer> getParentsOrder(int parentsNumber){
        List<Integer> parentsParts = new LinkedList<>();
        int doubledParent = generator.nextInt(parentsNumber);

        List<Integer> toGenerate = new ArrayList<>(parentsNumber);
        for(int i = 0; i<parentsNumber;i++)
            toGenerate.add(1);

        toGenerate.set(doubledParent, 2);
        for(int i = 0; i< parentsNumber+1; i++) {
            int parentIndex = generator.nextInt(parentsNumber + 1 - i);
            int actParentIndex = 0;
            while(parentIndex>=0){
                parentIndex-=toGenerate.get(actParentIndex);
                actParentIndex++;
            }
            actParentIndex--;
            toGenerate.set(actParentIndex, toGenerate.get(actParentIndex)-1);
            parentsParts.add(actParentIndex);
        }
        return parentsParts;
    }

    private void addPartFrom(DNA parent, int start, int stop){
        for(int i = start; i < stop; i++){
            strand.add(parent.strand.get(i));
        }
    }

    private void replaceOneGene(Rotation oldGene, Rotation newGene){
        for(int i = 0; i< strandLength; i++){
            if(strand.get(i) == oldGene) {
                strand.set(i, newGene);
                break;
            }
        }
    }

    private void repair(){
        List<Integer> geneNumber = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            geneNumber.add(0);
        }

        for(int i = 0; i < strandLength; i++){
            geneNumber.set(strand.get(i).toInt(), geneNumber.get(strand.get(i).toInt()) + 1);
        }

        for(int i = 0; i<8;i++){
            if(geneNumber.get(i) == 0){
                int multipleGenes = 0;
                for(int j = 0; j<8;j++){
                    if(geneNumber.get(j)>=2)
                        multipleGenes++;
                }
                int geneToReplace = generator.nextInt(multipleGenes);
                for(int j = 0; j<8;j++){
                    if(geneNumber.get(j)>=2)
                        if(geneToReplace>0)
                            geneToReplace--;
                        else{
                            replaceOneGene(Rotation.valueOf(j), Rotation.valueOf(i));
                        }
                }
            }
        }



        strand.sort(Comparator.comparingInt(Rotation::toInt));
    }

    public int size(){
        return strand.size();
    }

    public Rotation get(int position){
        return strand.get(position);
    }

    public List<Rotation> getStrand(){
        return Collections.unmodifiableList(strand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNA dna = (DNA) o;
        return Objects.equals(strand, dna.strand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strand);
    }
}

