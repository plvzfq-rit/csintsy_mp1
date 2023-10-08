package solver;

import java.util.*;

public class SokoBot {
  private static List<int[]> walls = new ArrayList<>();
  private static List<int[]> goals = new ArrayList<>();
  private static String solution = "";
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    int[][] gameState = convertToGameState(width, height, mapData, itemsData);
    posOfWalls(gameState);
    posOfGoals(gameState);

    BFS(gameState);
    
    return solution;
  }

  public static int[][] convertToGameState(int width, int height, char[][] mapData, char[][] itemsData) {
    int[][] layout = new int[height][width];
    for (int irow = 0; irow < height; irow++) {
      for (int icol = 0; icol < width; icol++) {
        char char1 = mapData[irow][icol];
        char char2 = itemsData[irow][icol];

        if (char1 == ' ' && char2 == ' ') {
          layout[irow][icol] = 0; // free space
        } else if (char1 == '#') {
          layout[irow][icol] = 1; // wall
        } else if (char2 == '@') {
          layout[irow][icol] = 2; // player
        } else if (char2 == '$') {
          layout[irow][icol] = 3; // box
        } else if (char1 == '.') {
          layout[irow][icol] = 4; // goal
        } 
        if (char1 == '$' && char2 == '.') {
          layout[irow][icol] = 5; // box on goal
        }
      }
    }
    return layout;
  }

  public static void posOfWalls(int[][] gameState) {
    for (int i = 0; i < gameState.length; i++) {
      for (int j = 0; j < gameState[i].length; j++) {
        if (gameState[i][j] == 1) {
          walls.add(new int[]{i, j});
        }
      }
    }
  }

  public static List<int[]> posOfBoxes(int[][] gameState) {
    List<int[]> boxPositions = new ArrayList<>();

    for (int i = 0; i < gameState.length; i++) {
      for (int j = 0; j < gameState[i].length; j++) {
        if (gameState[i][j] == 3 || gameState[i][j] == 5) {
          boxPositions.add(new int[]{i, j});
        }
      }
    }

    return boxPositions;
  }

  public static void posOfGoals(int[][] gameState) {
    for (int i = 0; i < gameState.length; i++) {
      for (int j = 0; j < gameState[i].length; j++) {
        if (gameState[i][j] == 4) {
          goals.add(new int[]{i, j});
        }
      }
    }
  }

  public static int[] posOfPlayer(int[][] gameState) {
    for (int i = 0; i < gameState.length; i++) {
      for (int j = 0; j < gameState[i].length; j++) {
        if (gameState[i][j] == 2) {
          return new int[]{i, j};
        }
      }
    }
    
  // if the player is not found
  return new int[]{-1, -1};
  }

  public static boolean isEndState(List<int[]> box) {
    Collections.sort(box, Comparator.comparingInt(arr -> arr[0]));
    Collections.sort(goals, Comparator.comparingInt(arr -> arr[0]));
    return box.equals(goals);
  }

  public static List<List<Object>> legalActions(int[] posPlayer, List<int[]> posBox) {
      List<List<Object>> allActions = new ArrayList<>();
      allActions.add(new ArrayList<>(List.of(-1, 0, 'u', 'U')));
      allActions.add(new ArrayList<>(List.of(1, 0, 'd', 'D')));
      allActions.add(new ArrayList<>(List.of(0, -1, 'l', 'L')));
      allActions.add(new ArrayList<>(List.of(0, 1, 'r', 'R')));

      int xPlayer = posPlayer[0];
      int yPlayer = posPlayer[1];

      List<List<Object>> legalActions = new ArrayList<>();

      for (List<Object> action : allActions) {
        int x1 = xPlayer + (int) action.get(0);
        int y1 = yPlayer + (int) action.get(1);

        if (containsPosition(posBox, x1, y1)) { // the move was a push
          action.remove(2); // drop the little letter
        } else {
          action.remove(3); // drop the upper letter
        }

        if (isLegalAction(action, posPlayer, posBox)) {
          legalActions.add(new ArrayList<>(action));
        }
      }

      return legalActions;
  }

  public static boolean isLegalAction(List<Object> action, int[] posPlayer, List<int[]> posBox) {
    int xPlayer = posPlayer[0];
    int yPlayer = posPlayer[1];
    int x1, y1;
    if (Character.isUpperCase((char) action.get(2))) { // the move was a push
      x1 = xPlayer + 2 * (int) action.get(0);
      y1 = yPlayer + 2 * (int) action.get(1);
    } else {
      x1 = xPlayer + (int) action.get(0);
      y1 = yPlayer + (int) action.get(1);
    }
    return !containsPosition(posBox, x1, y1) && !containsPosition(walls, x1, y1);
  }

  public static boolean containsPosition(List<int[]> posWalls, int x, int y) {
    for (int[] position : posWalls) {
      if (position[0] == x && position[1] == y) {
        return true;
      }
    }
    return false;
  }

  public static boolean boxInWalls(int[] posBox) {
    for (int[] position : walls) {
      if (position[0] == posBox[0] && position[1] == posBox[1]) {
        return true;
      }
    }
    return false;
  }

  public static boolean boxInGoals(int[] posBox) {
    for (int[] position : goals) {
      if (position[0] == posBox[0] && position[1] == posBox[1]) {
        return true;
      }
    }
    return false;
  }

  public static boolean inBox(int[] pos, List<int[]> posBox) {
    for (int[] position : posBox) {
      if (position[0] == pos[0] && position[1] == pos[1]) {
        return true;
      }
    }
    return false;
  }

  public static GameState updateState(int[] posPlayer, List<int[]> posBox, List<Object> action) {
    int xPlayer = posPlayer[0];
    int yPlayer = posPlayer[1];
    int[] newPosPlayer = {xPlayer + (int) action.get(0), yPlayer + (int) action.get(1)};
    
    List<int[]> newPosBox = posBox;

    if(Character.isUpperCase((char) action.get(2))) {
      newPosBox.remove(newPosPlayer);
      int[] updatePlayer = {xPlayer + 2 * (int) action.get(0), yPlayer + 2 * (int) action.get(1)};
      newPosBox.add(updatePlayer);
    }

    return new GameState(newPosPlayer, newPosBox);
  }

  public static boolean isFailed(List<int[]> posBox) {
    List<List<Integer>> rotatePattern = Arrays.asList(
      Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8),
      Arrays.asList(2, 5, 8, 1, 4, 7, 0, 3, 6),
      Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1, 0),
      Arrays.asList(6, 3, 0, 7, 4, 1, 8, 5 ,2)
    );

    List<List<Integer>> flipPattern = Arrays.asList(
      Arrays.asList(2, 1, 0, 5, 4, 3, 8, 7, 6),
      Arrays.asList(0, 3, 6, 1, 4, 7, 2, 5, 8),
      Arrays.asList(6, 7, 8, 3, 4, 5, 0, 1, 2),
      Arrays.asList(8, 5, 2, 7, 4, 1, 6, 3, 0)
    );

    List<List<Integer>> allPattern = new ArrayList<>();
    allPattern.addAll(rotatePattern);
    allPattern.addAll(flipPattern);

    for (int[] box : posBox) {
      if (!boxInGoals(box)) {
        List<int[]> board = Arrays.asList(
          new int[]{box[0] - 1, box[1] - 1}, new int[]{box[0] - 1, box[1]}, new int[]{box[0] - 1, box[1] + 1},
          new int[]{box[0], box[1] - 1}, new int[]{box[0], box[1]}, new int[]{box[0], box[1] + 1},
          new int[]{box[0] + 1, box[1] - 1}, new int[]{box[0] + 1, box[1]}, new int[]{box[0] + 1, box[1] + 1}
        );

        for (List<Integer> pattern : allPattern) {
          List<int[]> newBoard = new ArrayList<>();
          for (int i : pattern) {
              newBoard.add(board.get(i));
          }

          if (boxInWalls(newBoard.get(1)) && boxInWalls(newBoard.get(5))) {
              return true;
          } else if (inBox(newBoard.get(1), posBox) && boxInWalls(newBoard.get(2)) && boxInWalls(newBoard.get(5))) {
              return true;
          } else if (inBox(newBoard.get(1), posBox) && boxInWalls(newBoard.get(2)) && inBox(newBoard.get(5), posBox)) {
              return true;
          } else if (inBox(newBoard.get(1), posBox) && inBox(newBoard.get(2), posBox) && inBox(newBoard.get(5), posBox)) {
              return true;
          } else if (inBox(newBoard.get(1), posBox) && inBox(newBoard.get(6), posBox) && boxInWalls(newBoard.get(2)) && 
                     boxInWalls(newBoard.get(3)) && boxInWalls(newBoard.get(8))) {
              return true;
          }
        }
      }
    }
    return false;
  }

  public static void printActions(List<Object> actions) {
    for(Object i : actions) {
      System.out.println(i.toString());
    }
  }

  public static void printFrontier(Deque<List<GameState>> f) {
    for(List<GameState> h : f) {
      for(GameState i : h) {
        System.out.printf("\nPlayer: %d, %d\n", i.getPlayer()[0], i.getPlayer()[1]);
        for(int[] j : i.getBoxes()) {
          System.out.printf("%d, %d\n", j[0], j[1]);
        }
      }
    }
  }

  public static void BFS(int[][] gameState) {
    List<int[]> startBox = posOfBoxes(gameState);
    int[] startPlayer = posOfPlayer(gameState);

    GameState startState = new GameState(startPlayer, startBox);

    Deque<List<GameState>> frontier = new LinkedList<>();
    Deque<List<Object>> actions = new LinkedList<>();

    frontier.offerLast(new ArrayList<>(Collections.singletonList(startState)));
    actions.offerLast(new ArrayList<>(Collections.singletonList(0)));

    Set<List<GameState>> exploredSet = new HashSet<>();

    System.out.println("Boxes: \n");
    for(int[] i : startBox) {
      System.out.printf("%d, %d\n", i[0], i[1]);
    }

    System.out.println("Goals: \n");
    for(int[] i : goals) {
      System.out.printf("%d, %d\n", i[0], i[1]);
    }

    System.out.printf("Player: %d, %d\n", startPlayer[0], startPlayer[1]);

    System.out.println("\n");
    System.out.println("\n");
    System.out.println("\n");
    System.out.println("\n");

    while (!frontier.isEmpty()) {
      List<GameState> node = frontier.pollFirst();
      List<Object> nodeAction = actions.pollFirst();

      if(isEndState(node.get(node.size() - 1).getBoxes())) {
        System.out.println("End state found!\n");
        printActions(nodeAction);
        break;
      }

      if(!exploredSet.contains(node)) {
        System.out.println("Currently exploring!\n");
        exploredSet.add(node);
        for (List<Object> action : legalActions(node.get(node.size() - 1).getPlayer(), node.get(node.size() - 1).getBoxes())) {
          GameState newStates = updateState(node.get(node.size() - 1).getPlayer(), node.get(node.size() - 1).getBoxes(), action);
          if (isFailed(newStates.getBoxes())) {
            System.out.println("Failed!\n"); 
            continue;
          }

          System.out.println("Success!\n"); 
          List<GameState> newNode = new ArrayList<>(node);
          newNode.add(newStates);
          frontier.offerLast(newNode);

          List<Object> newActions = new ArrayList<>(nodeAction);
          newActions.add(action);
          actions.offerLast(newActions);
        }
      }
      printFrontier(frontier);
    }
  }
}

class GameState {
    private int[] player;
    private List<int[]> boxes;

    public GameState(int[] player, List<int[]> boxes) {
        this.player = player;
        this.boxes = boxes;
    }

    public int[] getPlayer() {
        return player;
    }

    public List<int[]> getBoxes() {
        return boxes;
    }
}
