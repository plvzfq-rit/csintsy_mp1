package solver;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;

public class PAStar {
    
    public static String aStar(char[][] mapData, char[][] itemsData, ArrayList<BoxMove> moves, int moveInd, StringBuilder path) {

        if (moveInd == -1)
            return path.toString();

        // a star
        PriorityQueue<mNode> pq = new PriorityQueue<>(new mNodeComparator());
        HashSet<mNode> hs = new HashSet<>();
        Coord player = BoxMove.getPlayerLocation(itemsData);
        Coord goal = boxMoveParser(moves.get(moveInd));

        mNode root = new mNode(null, player, 'w', 0, HFunction(player, goal));

        pq.add(root);

        while (!pq.isEmpty()) {
            mNode current = pq.poll();
            hs.add(current);
            
            if (current.hCost == 0) {
                StringBuilder curPath = new StringBuilder();

                while (current.parent != null) {
                    curPath.append(current.dir);
                    current = current.parent;
                }

                curPath.reverse();
                curPath.append(moves.get(moveInd).dir);
                path.append(curPath);

                return aStar(mapData, Solver.newState(itemsData, moves.get(moveInd)), moves, moveInd - 1, path);
            }

            Coord[] UDLR = current.pos.getUDLRCoords();
            char dirs[] = {'u', 'd', 'l', 'r'};
            int i = 0;

            for (Coord c : UDLR) {

                if (!BoxMove.isOpen(mapData[c.r][c.c]) || !BoxMove.isOpen(itemsData[c.r][c.c])) {
                    i++;
                    continue;
                }

                mNode newNode = new mNode(current, c, dirs[i], current.gCost + 1, HFunction(c, goal));

                if (!hs.contains(newNode) && !pq.contains(newNode)) 
                    pq.add(newNode);
           
                i++;
            }
        }

        return null;
    }

    public static int HFunction(Coord player, Coord goal) {
        return Coord.manhattanDist(player, goal);
    }   

    public static Coord boxMoveParser(BoxMove bm) {
        Coord res = new Coord(bm.coord);
        switch(bm.dir) {
            case 'u':
            res.r++;
            break;
            case 'd':
            res.r--;
            break;
            case 'l':
            res.c++;
            break;
            case 'r':
            res.c--;
            break;
        }
        return res;
    }

    public static class mNode {
        public mNode parent;
        public Coord pos;
        public char dir;
        public int gCost;
        public int hCost;

        public mNode(mNode parent, Coord pos, char dir, int gCost, int hCost) {
            this.parent = parent;
            this.pos = pos;
            this.dir = dir;
            this.gCost = gCost;
            this.hCost = hCost;
        }

        public int getFCost() {
            return gCost + hCost;
        }

        @Override
        public boolean equals(Object e) {
            mNode n2 = (mNode) e;
            return pos.equals(n2.pos);
        }

        @Override
        public int hashCode() {
            return pos.hashCode();
        }
    }

    public static class mNodeComparator implements Comparator<mNode> {
        public int compare(mNode n1, mNode n2) {
            return n1.getFCost() - n2.getFCost();
        }
    }
}
