package pl.edu.agh.szubertm.evolutionsimulator.logic;

public interface IPositionChangePublisher {
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
}
