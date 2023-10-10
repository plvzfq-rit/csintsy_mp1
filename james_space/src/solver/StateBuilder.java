package solver;

import java.util.*;

/*
 * Formula for coordinate: width * y coodinate + x coordinate
 */

public class StateBuilder {

    public int computeCoordinate (int x, int y, int width) {
        return width * y + x;
    }

    public int computeUp (int coord, int width) {
        return coord - width;
    }

    public int computeDown (int coord, int width) {
        return coord + width;
    }

    public int computeLeft (int coord) {
        return coord - 1;
    }

    public int computeRight (int coord) {
        return coord + 1;
    }

    public State createState (int width, int height, char[][] mapData, char[][] itemsData) {
        int playerCoordinates = 0;
        HashSet<Integer> boxCoordinates = new HashSet<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (itemsData[y][x] == '@') {
                    playerCoordinates = computeCoordinate(x, y, width);
                }
                if (itemsData[y][x] == '$') {
                    boxCoordinates.add(computeCoordinate(x, y, width));
                }
            }
        }
        return new State(width, height, playerCoordinates, boxCoordinates);
    }

    public State simulateMove (State state, char direction) {
        
    }

}
