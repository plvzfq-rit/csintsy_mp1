package testing_ground;

public class Main {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] b = {0,5,-3,4,-9,-1};
        Annealer_Test at = new Annealer_Test();

        for (int i = 0; i < 5; i++) {
            System.out.println(at.anneal(6, a, b));
        }
    }
}
