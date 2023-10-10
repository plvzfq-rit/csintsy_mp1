

import java.lang.Math;

public class Annealer {
    // step 1, figure out perturbation function
    public int anneal (int initialTemperature, int numOfNodes, int max_epochs, Coord[] boxes, Coord[] goals) {

        // double k = 0.00000000000000000000001380649;

        int[] state = new int[numOfNodes];
        for (int i = 0; i < numOfNodes; i++) {
            state[i] = i;
        }

        int minimumEnergy = calculateEnergy(numOfNodes, state, boxes, goals);
        int currentTemperature = initialTemperature;
        int prevIndexToSwap = numOfNodes;
        int epoch = 0;

        while (currentTemperature > 1 && max_epochs > epoch) {
            // perturbation
            int indexToSwap = (int) Math.round(Math.random() * (numOfNodes - 1));

            while (indexToSwap == prevIndexToSwap) {
                indexToSwap = (int) Math.round(Math.random() * (numOfNodes - 1));
            }

            prevIndexToSwap = indexToSwap;

            // swap
            int temp = state[indexToSwap];
            state[indexToSwap] = state[numOfNodes - 1];
            state[numOfNodes - 1] = temp;

            int newEnergy = calculateEnergy(numOfNodes, state, boxes, goals);

            if (newEnergy < minimumEnergy) {
                minimumEnergy = newEnergy;
            }

            int dE = newEnergy - minimumEnergy;
            double probability = Math.exp(dE / currentTemperature);
            
            if (!acceptQuo(dE, probability)) {
                temp = state[indexToSwap];
                state[indexToSwap] = state[numOfNodes - 1];
                state[numOfNodes - 1] = temp;
            }

            currentTemperature *= 0.95;
            epoch += 1;

        }

        return minimumEnergy;

    }

    private boolean acceptQuo (int dE, double probability) {
        if (dE < 0) {
            return true;
        }
        else {
            double r = Math.random();
            if (r < probability) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    private int calculateEnergy (int numOfNodes, int[] pi, Coord[] boxes, Coord[] goals) {
        int energy = 0;
        for (int i = 0; i < numOfNodes; i++) {
            energy += calculateManhattanDistance(boxes[pi[i]], goals[pi[i]]);
        }
        return energy;
    }

    private int calculateManhattanDistance (Coord c1, Coord c2) {
        return Math.abs(c1.c - c2.c) + Math.abs(c1.r - c2.r);
    }

}