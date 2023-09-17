package solver;

public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    try {
      Thread.sleep(3000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    StateBuilder sb = new StateBuilder();
    SokobanDp sd = new SokobanDp();
    State state = sb.createState(mapData, itemsData);



    return sd.SokobanDpSolver(state);
  }
}
