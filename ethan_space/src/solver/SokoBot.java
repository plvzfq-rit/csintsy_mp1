package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SokoBot {
  private HashMap<String, Boolean> visitedStates = new HashMap<>();

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    return dfsSolve(width, height, mapData, itemsData);
  }

  public String dfsSolve(int width, int height, char[][] mapData, char[][] itemsData) {
    StringBuilder solString = new StringBuilder();
    if(isSolved(width, height, mapData, itemsData))
      return "";

    ArrayList<Character> possibleMoves = generatePossibleMoves(width, height, mapData, itemsData);

    for(Character move : possibleMoves) {
      System.out.println("MOVE: " + move + "\n");
      applyMove(width, height, mapData, itemsData, move);

      if(isValidState(width, height, mapData, itemsData) && !isVisited(mapData, itemsData)) {
        System.out.println("MARKED AS VISITED");
        markVisited(mapData, itemsData);

        String result = dfsSolve(width, height, mapData, itemsData);

        if(!result.isEmpty()) {
        //   System.out.println("SOLUTION MADE");
        //   solString.insert(0, move);
        //   solString.append(result);
        //   System.out.println(solString.toString());
          // return solString.toString();
          return move + result;
        }
      }
      undoMove(width, height, mapData, itemsData, move);
    }
    return "";
  }

  public boolean isSolved(int width, int height, char[][] mapData, char[][] itemsData) {
    // check if already solved
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        if(mapData[i][j] == '.' && itemsData[i][j] != '$') {
          return false;
        }
      }
    }
    return true;
  }

  public ArrayList<Character> generatePossibleMoves(int width, int height, char[][] mapData, char[][] itemsData) {
    ArrayList<Character> validMoves = new ArrayList<>();
    int x = -1; // player's x coordinate
    int y = -1; // player's y coordinate

    // find player's position and initialize valid moves
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (itemsData[i][j] == '@') {
                x = j;
                y = i;
                validMoves.addAll(Arrays.asList('u','d','l','r'));
            }
        }
    }

    if (x >= 0 && y >= 0 && x < width && y < height) {
      if (y - 1 >= 0 && mapData[y - 1][x] == '#') {
        validMoves.remove(Character.valueOf('u'));
      }
      if (y + 1 < height && mapData[y + 1][x] == '#') {
    validMoves.remove(Character.valueOf('d'));
      }
      if (x - 1 >= 0 && mapData[y][x - 1] == '#') {
        validMoves.remove(Character.valueOf('l'));
      }
      if (x + 1 < width && mapData[y][x + 1] == '#') {
        validMoves.remove(Character.valueOf('r'));
      }
    }
    for(Character c : validMoves) System.out.print(c);
    System.out.println("\n");
    return validMoves;
  }

  public void applyMove(int width, int height, char[][] mapData, char[][] itemsData, char move) {
    int x = -1; // player's x coordinate
    int y = -1; // player's y coordinate

    // find player's position and initialize valid moves
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (itemsData[i][j] == '@') {
          x = j;
          y = i;
        }
      }
    }
    if (x >= 0 && y >= 0 && x < width && y < height) {
      int newX = x;
      int newY = y;
      if (move == 'u') {
        newY--;
      } else if (move == 'd') {
        newY++;
      } else if (move == 'l') {
        newX--;
      } else if (move == 'r') {
        newX++;
      }
      if (newX >= 0 && newY >= 0 && newX < width && newY < height) {
        if (mapData[newY][newX] != '#') {
          if (itemsData[newY][newX] == '$') {
            // Move a box if there's one in front.
            int boxX = newX;
            int boxY = newY;
            if (move == 'u') {
              boxY--;
            } else if (move == 'd') {
              boxY++;
            } else if (move == 'l') {
              boxX--;
            } else if (move == 'r') {
              boxX++;
            }

            if (boxX >= 0 && boxY >= 0 && boxX < width && boxY < height && itemsData[boxY][boxX] == ' ') {
              itemsData[boxY][boxX] = '$';
              itemsData[newY][newX] = '@';
              itemsData[y][x] = ' ';
            }
          } else {
            // Move the player without pushing a box.
            itemsData[newY][newX] = '@';
            itemsData[y][x] = ' ';
          }
        }
      }
    }
  }

  public void undoMove(int width, int height, char[][] mapData, char[][] itemsData, char move) {
    int x = -1; // player's x coordinate
    int y = -1; // player's y coordinate

    // Find player's position
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (itemsData[i][j] == '@') {
                x = j;
                y = i;
            }
        }
    }

    // Check if the player's position is valid
    if (x >= 0 && y >= 0 && x < width && y < height) {
        int prevX = x;
        int prevY = y;

        // Calculate the previous position based on the move
        if (move == 'u') {
            prevY++;
        } else if (move == 'd') {
            prevY--;
        } else if (move == 'l') {
            prevX++;
        } else if (move == 'r') {
            prevX--;
        }

        // Ensure the previous position is within bounds
        if (prevX >= 0 && prevY >= 0 && prevX < width && prevY < height) {
            // Restore the previous state of the player
            itemsData[y][x] = '@';
            itemsData[prevY][prevX] = ' ';
        }
    }
  }

  public boolean isValidState(int width, int height, char[][] mapData, char[][] itemsData) {
    // for(int i = 0; i < height; i++) {
    //   for(int j = 0; j < width; j++) {
    //     return true;
    //   }
    // }
    return true;
  }

  public boolean isVisited(char[][] mapData, char[][] itemsData) {
    String gameData = gameToString(mapData) + gameToString(itemsData);
    return visitedStates.containsKey(gameData);
  }

  public void markVisited(char[][] mapData, char[][] itemsData) {
    String gameData = gameToString(mapData) + gameToString(itemsData);
    visitedStates.put(gameData, true);
  }

  public String gameToString(char[][] stateData) {
    StringBuilder stringGame = new StringBuilder();
    for (int i = 0; i < stateData.length; i++) {
      for (int j = 0; j < stateData[i].length; j++) {
          stringGame.append(stateData[i][j]);
      }
    }
    return stringGame.toString();
  }
}
