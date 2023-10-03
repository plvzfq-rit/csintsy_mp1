package solver;

import java.lang.Math;

public class ManhattanCalculator {
    public static double computeDistance (Coordinate c1, Coordinate c2) {
        return Math.sqrt((c1.getX() + c2.getX()) * c1.getX() + c2.getX() + 
                         (c1.getY() + c2.getY()) * c1.getY() + c2.getY());
    }
}
