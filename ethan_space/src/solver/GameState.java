package solver;

import java.util.List;

public class GameState {
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
