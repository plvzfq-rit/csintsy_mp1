package testing_ground;

import java.lang.Math;

public class Annealer_Test {
    public int anneal (int len, int[] a, int[] b) {
        int[] map = createRange(len);
        int ce = calc(len,map, a, b);
        int max_epoch = 100;
        int curr_epoch = 1;

        while (curr_epoch < max_epoch) {
            // perturb

            int randomPointA = (int) Math.floor((Math.random() * len));
            int rPointB = (int) Math.floor((Math.random() * len));

            while (randomPointA == rPointB) {
                rPointB = (int) Math.floor((Math.random() * len));
            }

            int temp = map[randomPointA];
            map[randomPointA] = map[rPointB];
            map[rPointB] = temp;

            int newEnergy = calc(len, map, a, b);
            int deltaE = newEnergy - ce;
            double curr_temp = 1 - (curr_epoch + 1) / max_epoch;

            if (accept(curr_temp, deltaE)) {
                ce = newEnergy;
            }
            else {
                temp = map[randomPointA];
                map[randomPointA] = map[rPointB];
                map[rPointB] = temp;
            }
            curr_temp /= 0.1;
        }
        return ce;
    }

    private int[] createRange (int len) {
        int[] p = new int[len];
        for (int i = 0; i < len; i++) {
            p[i] = i;
        }
        return p;
    }

    private int calc (int len, int[] map, int[] a, int[] b) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += a[map[i]] * b[map[i]];
        }
        return sum;
    }

    private boolean accept (double t, int de) {
        if (de < 0) {
            return true;
        }
        else {
            double r = Math.random();
            if (r < Math.exp(-de/t)) {
                return true;
            }
            else {
                return false;
            }

        }
    }
}
