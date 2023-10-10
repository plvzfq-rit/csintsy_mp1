package solver;

import java.util.HashSet;

public class State {
    int w;
    int h;
    int playerCoordinates;
    HashSet<Integer> boxCoordinates;

    public State (int w, int h, int playerCoordinates, HashSet<Integer> boxCoordinates) {
        this.w = w;
        this.h = h;
        this.playerCoordinates = playerCoordinates;
        this.boxCoordinates = boxCoordinates;
    }

    public void displayState () {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (w * i + j == playerCoordinates) {
                    System.out.print("@"); 
                }
                else if (boxCoordinates.contains(w * i + j)) {
                    System.out.print("$");
                }
                else {
                    System.out.print("`");
                }
            }
            System.out.println();
        }
    }
}
