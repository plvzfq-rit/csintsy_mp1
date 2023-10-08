package solver;

import java.util.*;

public class NodeState {
    Coordinate playerCoordinate;
    List<Coordinate> boxCoordinates;

    public NodeState (Coordinate playerCoordinate, List<Coordinate> boxCoordinates) {
        this.playerCoordinate = playerCoordinate;
        this.boxCoordinates = boxCoordinates;
    }

    public Coordinate getPlayerCoordinate () {
        return this.playerCoordinate;
    }

    public List<Coordinate> getBoxCoordinates () {
        return this.boxCoordinates;
    }
}
