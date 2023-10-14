package solver;
import java.util.ArrayList;

public class FreezeLocks {
    
    public static boolean isFreeze(char[][] mapData, char[][] itemsData, Coord boxMoved, ArrayList<Coord> walls,
            boolean[][] deadlock) {
        walls.add(boxMoved);

        boolean xAxis = true;
        boolean yAxis = true;
        Coord[] UDLR = boxMoved.getUDLRCoords();

        Coord u = UDLR[0];
        Coord d = UDLR[1];
        Coord l = UDLR[2];
        Coord r = UDLR[3];

        if (!BoxMove.isOpen(mapData[u.r][u.c]) || !BoxMove.isOpen(mapData[d.r][d.c])
                || !BoxMove.isOpen(itemsData[u.r][u.c]) || !BoxMove.isOpen(itemsData[d.r][d.c])
                || (deadlock[u.r][u.c] && deadlock[d.r][d.c])) {
            yAxis = false;
        }

        if (!BoxMove.isOpen(mapData[l.r][l.c]) || !BoxMove.isOpen(mapData[r.r][r.c])
                || !BoxMove.isOpen(itemsData[l.r][l.c]) || !BoxMove.isOpen(itemsData[r.r][r.c])
                || (deadlock[l.r][l.c] && deadlock[r.r][r.c])) {
            xAxis = false;
        }

        if (xAxis || yAxis) {
            return false;
        }

        boolean oneBoxNotFrozen = false;


        for (int i = 0; i < UDLR.length; i++) {
            if (itemsData[UDLR[i].r][UDLR[i].c] == '$' && !walls.contains(UDLR[i])) {
                if (isFreeze(mapData, itemsData, UDLR[i], walls, deadlock)) continue;
                else { oneBoxNotFrozen = true; break; }
            } else if (walls.contains(UDLR[i])) {
                if ((i == 0 || i == 1) && !xAxis) continue;
                if ((i == 2 || i == 3) && !yAxis) continue;
            }
        }

        boolean allGoals = true;
        for (Coord c : walls) {
            if (mapData[c.r][c.c] != '.') {
                allGoals = false;
                break;
            }
        }

        return !oneBoxNotFrozen && !allGoals;
    }

}
