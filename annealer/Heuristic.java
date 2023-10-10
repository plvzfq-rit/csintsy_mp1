import java.lang.Math;

public class Heuristic {
    public int calculateHeuristic () {
        return 0;
    }

    public int calculateHeuristicForTwo (Coord[] b, Coord[] g) {
        return Math.min(md(b[0], g[0]) + md(b[1], g[1]), md(b[1], g[0]) + md(b[0], g[1]));
    }

    public int

    private int md (Coord c1, Coord c2) {
        return Math.abs(c1.c - c2.c) + Math.abs(c1.r - c2.r);
    }
}
