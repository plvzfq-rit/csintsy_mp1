package solver;
import java.util.ArrayList;
public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    ArrayList<BoxMove> bm = Solver.AStar(mapData, itemsData);

    return PAStar.aStar(mapData, itemsData, bm, bm.size() - 1, new StringBuilder());
  }
}
