package solver;

import java.util.*;
import java.lang.Math;

/*
 * C = Cint
 * for T = Tmax to Tmin
 * Ec = E(C)
 * N  = next 
 * En = E(N)
 * dE = EN - EC
 * if (dE > 0) C = N
 * else if (exp(dE/T)) C = N
 */


public class Calculator {
    public static int simulatedAnnealer (int numOfBoxes, List<Coordinate> boxCoordinates, List<Coordinate> goalCoordinates)  {
        int MAX_EPOCH = 101;
        int currentEpoch = 1;

        // loop through epochs
        while (currentEpoch < MAX_EPOCH) {
            // declare temperature
            int currentEnergy = Calculator.calculateEnergy(numOfBoxes, boxCoordinates, goalCoordinates);
            double temperature = 1 - (currentEpoch + 1) / MAX_EPOCH;

            // generate random state

        }

        return 
    }

    private static int calculateEnergy (int numOfBoxes, List<Coordinate> boxCoordinates, List<Coordinate> goalCoordinates) {
        int energy = 0;
        for (int i = 0; i < numOfBoxes; i++) {
            energy += manhattanDistance(boxCoordinates.get(i), goalCoordinates.get(i));
        }
        return energy;
    }

    public static int manhattanDistance (Coordinate c1, Coordinate c2) {
        return Math.abs(c1.getX() - c2.getX()) + Math.abs(c1.getY() - c2.getY());
    }
}
