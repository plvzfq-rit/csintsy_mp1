package solver;

public class SokoBot {
  // Lets try implementing a naive solution
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  /*
   * Generates goal data.
   * 
   * @param width   width of the map
   * @param height  height of the map
   * @param mapData 2D character array containing data about immovable objects in the map
   * 
   * @return a 2D character array representing the goal state
   */
  public char[][] generateGoalData (int width, int height, char[][] mapData) {
    char[][] goalData = mapData;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        goalData[i][j] = (goalData[i][j] == '.')? '*' : goalData[i][j];
      }
    }
    return goalData;
  }

  /*
   * Gets the players coordinates.
   * 
   * @param width     width of the map
   * @param height    height of the map
   * @param itemsData 2D character array containing data about movable objects on the map
   * 
   * @return an integer array <coordinates> where <coordinates = {x-coordinate, y-coordinate}>
   */
  public int[] getPlayerCoordinates (int width, int height, char[][] itemsData) {
    int[] coordinates = {0, 0};
    while (itemsData[coordinates[0]][coordinates[1]] != '@' && coordinates[0] < height) {
      while(itemsData[coordinates[0]][coordinates[1]] != '@' && coordinates[1] < width) {
        coordinates[1]++;
      }
      if (itemsData[coordinates[0]][coordinates[1]] != '@') {
        coordinates[0]++;
        coordinates[1] = 0;
      }
    }
    return coordinates;
  }

}
