package solver;

import java.util.Comparator;
import java.lang.Math;

public class NodeComparator implements Comparator<Node> {
    public int compare (Node a, Node b) {
        return (int) Math.round(a.getTotalCost() - b.getTotalCost());
    }
}
