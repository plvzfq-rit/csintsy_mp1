package solver;

import java.util.*;
import java.lang.*;

public class SokoBot {

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

  /*
   * Checks which moves are possible in which cardinal directions.
   * 
   * 
   */
  public String checkMove (int width, int height, char[][] mapData, char[][] itemsData, int[] coordinates) {
    String rString = "";
    int row = coordinates[0];
    int col = coordinates[1];

    // checks in the northern direction
    if (row >= 1) {
      if (mapData[row - 1][col] == ' ') {
        rString += "u";
      }
      else if (itemsData[row - 1][col] == '$' && row >= 2) {
        if (mapData[row - 2][col] == ' ') {
          rString += "u";
        }
      } 
    }

    // checks in the southern direction
    if (row < height - 1) {
      if (mapData[row + 1][col] == ' ') {
        rString += "d";
      }
      else if (itemsData[row + 1][col] == '$' && row < height - 2) {
        if (mapData[row + 2][col] == ' ') {
          rString += "d";
        }
      }
    }

    // checks in the western direction
    if (col >= 1) {
      if (mapData[row][col - 1] == ' ') {
        rString += "l";
      }
      else if (itemsData[row][col - 1] == '$' && col >= 2) {
        if (mapData[row][col - 2] == ' ') {
          rString += "l";
        }
      }
    }

    // checks in the eastern direction
    if (col < width - 1) {
      if (mapData[row][col + 1] == ' ') {
        rString += 'r';
      }
      else if (itemsData[row][col + 1] == '$' && col < width - 2) {
        if (mapData[row][col + 2] == ' ') {
          rString += "r";
        } 
      }
    }

    return rString;
  }

  /*
   * Models moving behaviour.
   */
  public char[][] move (int width, int height, char[][] ) {
    
  }
  /*
   * Models a naive "backtracking" solution.
   */
  public String naiveSolution () {

    return "";
  }
}
