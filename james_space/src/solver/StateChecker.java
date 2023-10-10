package solver;

import java.util.HashSet;

public class StateChecker {
    public boolean isStateSolved (HashSet<Integer> boxCoordinates, HashSet<Integer> goalCoordinates) {
        return boxCoordinates.equals(goalCoordinates);
    }
}
