package solver;

import java.util.*;
import java.lang.*;
import java.sql.Array;

public class SokoBot {
  public int[] initialCoordinates;
  public ArrayList<int[]> goalCoordinates = new ArrayList<int[]>();

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  /*
   * Generates goal data.
   * 
   * @param width   width of the map
   * @param height  height of the map
   * @param mapData 2D character array containing data about immovable objects in the map
   */
  public void generateGoalData (int width, int height, char[][] mapData) {
    int[] coords;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (mapData[i][j] == '.') {
          coords = new int[2];
          coords[0] = i;
          coords[1] = j;
          goalCoordinates.add(coords);
        }
      }
    }
  }

  /*
   * Gets the players coordinates.
   * 
   * @param width     width of the map
   * @param height    height of the map
   * @param itemsData 2D character array containing data about movable objects on the map
   */
  public void getPlayerCoordinates (int width, int height, char[][] itemsData) {
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
    initialCoordinates = coordinates;
  }

  /*
   * Checks which moves are possible in which cardinal directions.
   * 
   * @param width width of the map
   * @param height height of the map
   * @param mapData 2D character array containing data about mo 
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
   * Computes the Manhattan distance between two points.
   * 
   * @param pointA an integer array  
   */
  public int computeManhattanDistance (int[] pointA, int[] pointB) {
    return Math.abs(pointA[0] - pointB[0]) + Math.abs(pointA[1] - pointB[1]);
  }

  public int[] getDirection

  /*
   * Gives the new state after a move in a specified direction.
   * 
   * @param width       width of map
   * @param height      height of map
   * @param itemsData   2D character array of immovable objects
   * @param coordinates integer array containing the player's coordinates
   * @param dirChar     character indicating the intended direction of movement
   * 
   * @return new item map state
   */
  public char[][] createMove (int width, int height, char[][] itemsData, int[] coordinates, char dirChar) {
    int[][] directions = {{0,1}, {0,-1}, {-1, 0}, {1, 0}};
    char[][] newItemsData = itemsData;
    int[] direction;

    if (dirChar == 'u') {
      direction = directions[0];
    }
    else if (dirChar == 'd') {
      direction = directions[1];
    }
    else if (dirChar == 'l') {
      direction = directions[2];
    }
    else {
      direction = directions[3];
    }

    if (newItemsData[coordinates[0] + direction[0]][coordinates[1] + direction[1]] == '$') {
      newItemsData[coordinates[0] + direction[0] * 2][coordinates[1] + direction[1] * 2] = '$';
    }
    newItemsData[coordinates[0] + direction[0]][coordinates[1] + direction[1]] = '@';
    newItemsData[coordinates[0]][coordinates[1]] = ' ';

    return newItemsData;
  }

  public boolean isGoal (char[][] itemsData) {
    boolean isGoal = true;
    for (int i = 0; i < goalCoordinates.size() && isGoal; i++) {
      if (itemsData[goalCoordinates.get(i)[0]][goalCoordinates.get(i)[1]] != '$') {
        isGoal = false;
      }
    }
    return isGoal;
  }

  public String createSolution (Node node) {
    Node destinationNode = node;
    String returnString = "";
    while (destinationNode.direction != "") {
      returnString += destinationNode.direction;
      destinationNode = destinationNode.parentNode;
    }
    return returnString;
  }

  /*
   * Tries to implement an A* solution. 
   * 
   * @param width     width of the map
   * @param height    height of the map
   * @param mapData   2D character array of immovable objects
   * @param itemsData 2D character array of movable objects
   * 
   * @return a sequence of steps to solve a Sokoban puzzle
   */
  public String aStarSolution (int width, int height, char[][] mapData, char[][] itemsData) {
    // create a priority queue of nodes to Visit
    PriorityQueue<Node> toVisit = new PriorityQueue<Node>(new NodeComparator());

    // create a dynamic array of visited states 
    List<char[][]> visitedStates = new ArrayList<>();

    // add the initial state
    toVisit.add(new Node(0, mapData, itemsData, initialCoordinates, null, ""));

    // declare a space for a selected node
    Node selectedNode;
    
    // declare a space for a string containing possible moves
    String possibleMoves;

    // run while the priority queue is not empty
    while (!toVisit.isEmpty()) {

      // get the selected node
      selectedNode = toVisit.poll();

      // add the visited node to the list of states
      visitedStates.add(selectedNode.itemData);

      if (isGoal(selectedNode.itemData)) {
        return createSolution(selectedNode);
      }

      else {
        possibleMoves = checkMove(width, height, mapData, itemsData, initialCoordinates);
        for (int i = 0; i < possibleMoves.length(); i++) {
          toVisit.add(new Node())
        }
      }

    }
    

    return "";
  }
}
