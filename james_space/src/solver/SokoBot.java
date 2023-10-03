package solver;

import java.util.*;

/*
 * LEGEND:
 * @ - player - in itemsData
 * # - wall - in mapData
 * $ - box - in itemsData
 * . - goal square - in mapData
 */

public class SokoBot {
    Coordinate initialPlayerCoordinates;
    List<Coordinate> initialBoxCoordinates = new ArrayList<Coordinate>();
    List<Coordinate> goalCoordinates = new ArrayList<Coordinate>();

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    this.initializeBot(width, height, mapData, itemsData);

    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  public void initializeBot (int width, int height, char[][] mapData, char[][] itemsData) {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (itemsData[i][j] == '@') {
                this.initialPlayerCoordinates = new Coordinate(i, j);
            }
            if (itemsData[i][j] == '$') {
                this.initialBoxCoordinates.add(new Coordinate(i, j));
            }
            if (mapData[i][j] == '.') {
                this.goalCoordinates.add(new Coordinate(i, j));
            }
        }
    }
  }

  public void aStarSearch (int width, int height, char[][] mapData, char[][] itemsData) {
    
  }

}