package solver;

import java.util.HashSet;

public class SokoBot {

  int playerCoordinates;
  HashSet<Integer> boxCoordinates = new HashSet<>();
  HashSet<Integer> goalCoordinates = new HashSet<>();
  

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    StateBuilder sb = new StateBuilder();
    
    HashSet<Integer> boxCoordinates = new HashSet<>();

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            if (itemsData[y][x] == '@') {
              playerCoordinates = sb.computeCoordinate(x, y, width);
            }
            if (itemsData[y][x] == '$') {
              boxCoordinates.add(sb.computeCoordinate(x, y, width));
            }
            if (mapData[y][x] == '.') {
              goalCoordinates.add(sb.computeCoordinate(x, y, width));
            }
        }
    }

    State initialState = new State (width, height, playerCoordinates, boxCoordinates);

    // SokobanDp sd = new SokobanDp();

    return "";
    // return sd.SokobanDpSolver(state);
  }
}