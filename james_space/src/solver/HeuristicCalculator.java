package solver;

import java.util.*;

public class HeuristicCalculator {
    // double[][] originalCostMatrix;
    // double[][] duplicateCostMatrix;

    // public HeuristicCalculator (ArrayList<Coordinate> boxes, ArrayList<Coordinate> goals) {
    //     int boxLength = boxes.size();
    //     int goalLength = goals.size();
    //     this.originalCostMatrix = new double[boxLength][goalLength];
    //     for (int i = 0; i < boxLength; i++) {
    //         for (int j = 0; j < goalLength; j++) {
    //             this.originalCostMatrix[i][j] = Coordinate.computeDistance(boxes.get(i), goals.get(j));
    //         }
    //     }
    //     this.duplicateCostMatrix = originalCostMatrix.clone();
    // }

    public double computeNaiveHeuristic (ArrayList<Coordinate> boxes, ArrayList<Coordinate> goals) {
        int boxSize = boxes.size();
        int goalSize = goals.size();
        double cost = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < goalSize; j++) {
                cost += Coordinate.computeDistance(boxes.get(i), goals.get(j));
            }
        }
        return cost / (boxSize * goalSize);
    }
    
}
