// package solver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;

public class SokoBot {

    public static int states = 0;
    public static int freeze = 0;
    public static int nondz = 0;
    public static int walkable = 0;

    public static void main(String[] args) {
        try {
            // map
            MapImporter mi = MapImporter.getDataFromFile(args[0]);
            char[][] mapData = mi.mapData;
            char[][] itemsData = mi.itemsData;
            int nBoxes = BoxMove.getNumberOfBoxes(itemsData);

            ArrayDeque<Coord> queue = new ArrayDeque<>();
            HashSet<Coord> visited = new HashSet<>();

            queue.add(BoxMove.getPlayerLocation(itemsData));

            while (!queue.isEmpty()) {
                Coord curr = queue.removeLast();

                visited.add(curr);

                Coord[] children = curr.getUDLRCoords();

                Coord cChild = children[0];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[1];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[2];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[3];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);
            }

            walkable = visited.size();
            
            // data
            System.out.println(args[0]);
            System.out.println("Boxes: " + nBoxes);

            long startTime = System.currentTimeMillis();

            // run program
            ArrayList<BoxMove> boxMoves = BoxMoveAStar(mapData, itemsData);
            int nbms = boxMoves.size();
            StringBuilder sb = new StringBuilder();
            interpretBoxMoves(mapData, itemsData, boxMoves, sb);
            //

            long endTime = System.currentTimeMillis();

            // data
            System.out.printf("** %d Player Moves, %d Box Moves\n", sb.length(), nbms);
            System.out.println("Unique States Evaluated: " + states);
            System.out.println("Total Squares: " + walkable);
            System.out.println("Open Squares: " + nondz);
            System.out.println("Deadlock Squares: " + (walkable - nondz));
            System.out.println("Pruned Frozen States: " + freeze);
            System.out.println("Runtime: " + ((double) (endTime - startTime) / 1000) + "s\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        // generate each box push until completed (a*)
        ArrayList<BoxMove> boxMoves = BoxMoveAStar(mapData, itemsData);

        StringBuilder sb = new StringBuilder();

        // generate player moves for each box move (a*)
        interpretBoxMoves(mapData, itemsData, boxMoves, sb);

        return sb.toString();
    }

    public static void interpretBoxMoves(char[][] mapData, char[][] itemsData, ArrayList<BoxMove> boxMoves,
            StringBuilder sb) {
        while (!boxMoves.isEmpty()) {
            BoxMove bm = boxMoves.remove(boxMoves.size() - 1);

            Coord player = new Coord(BoxMove.getPlayerLocation(itemsData));
            Coord goal = new Coord(bm.coord);
            playerAStar(mapData, itemsData, bm, player, goal, sb);
            sb.append(bm.dir);
            itemsData = newState(itemsData, bm);
        }
    }

    public static void playerAStar(char[][] mapData, char[][] itemsData, BoxMove bm, Coord player, Coord goal,
            StringBuilder sb) {

        if (player.equals(goal)) {
            return;
        }

        if (bm.dir == 'u')
            goal.r++;
        if (bm.dir == 'd')
            goal.r--;
        if (bm.dir == 'l')
            goal.c++;
        if (bm.dir == 'r')
            goal.c--;

        PriorityQueue<MoveNode> open = new PriorityQueue<>(new MoveNodeComparator());
        MoveNode root = new MoveNode(player, null, 0, Coord.manhattanDist(player, goal));
        open.add(root);
        HashSet<Coord> visited = new HashSet<>();

        boolean found = false;

        while (!open.isEmpty() && found == false) {
            MoveNode currentNode = open.poll();
            Coord current = currentNode.coords;
            visited.add(current);

            if (current.equals(goal)) {
                found = true;

                Stack<Character> stack = new Stack<>();

                MoveNode prev = currentNode;
                MoveNode cur = currentNode.parent;
                while (cur != null) {

                    Coord curC = cur.coords;
                    Coord prevC = prev.coords;

                    if (curC.c < prevC.c) {
                        stack.add('r');
                    }
                    if (curC.c > prevC.c) {
                        stack.add('l');
                    }
                    if (curC.r < prevC.r) {
                        stack.add('d');
                    }
                    if (curC.r > prevC.r) {
                        stack.add('u');
                    }

                    prev = cur;
                    cur = cur.parent;
                }

                Coord curC = root.coords;
                Coord prevC = prev.coords;

                if (curC.c < prevC.c) {
                    stack.add('r');
                }
                if (curC.c > prevC.c) {
                    stack.add('l');
                }
                if (curC.r < prevC.r) {
                    stack.add('d');
                }
                if (curC.r > prevC.r) {
                    stack.add('u');
                }

                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                continue;
            }

            Coord[] neighbours = current.getUDLRCoords();

            for (Coord c : neighbours) {
                if (visited.contains(c))
                    continue;
                int cost = currentNode.gCost + 1;
                MoveNode child = new MoveNode(c, currentNode, cost, Coord.manhattanDist(c, goal));
                if (open.contains(child)) {
                    continue;
                }

                if (isOpen(mapData[c.r][c.c]) && isOpen(itemsData[c.r][c.c]))
                    open.add(child);
            }
        }

    }

    public static boolean isOpen(char c) {
        return (c != '$' && c != '#');
    }

    public static ArrayList<BoxMove> BoxMoveAStar(char[][] mapData, char[][] itemsData) {
        // create root node ready to branch out
        Coord player = BoxMove.getPlayerLocation(itemsData);
        int nBoxes = BoxMove.getNumberOfBoxes(itemsData);
        Coord[] boxes = new Coord[nBoxes];
        Coord[] goals = new Coord[nBoxes];
        BoxMove.getAllItemsCoordinates(mapData, itemsData, boxes, goals);

        boolean[][] deadzone = generateDeadzone(mapData);

        for (int i = 0; i < deadzone.length; i++) {
            for (int j = 0; j < deadzone[0].length; j++) {
                if (deadzone[i][j] == false) nondz++;
            }
        }

        Node root = new Node(
                boxes, null, new BoxMove(' ', player),
                itemsData, 0, HFunction(boxes, goals));

                // important colelctions and lists
                PriorityQueue<Node> open = new PriorityQueue<>(new NodeComparator());
                HashSet<Node> closed = new HashSet<>();
                ArrayList<BoxMove> backtrack = new ArrayList<>();
                
                open.add(root);
                
        while (!open.isEmpty()) {
            Node current = open.poll();
            closed.add(current);

            char[][] check = current.itemsData;

            // for (int i = 0; i < check.length; i++) {
            //     for (int j = 0; j < check[0].length; j++) {
            //         if (mapData[i][j] == '#')
            //             System.out.print(mapData[i][j]);
            //         else if (mapData[i][j] == '.' && check[i][j] == '@')
            //             System.out.print('+');
            //         else if (mapData[i][j] == '.' && check[i][j] == '$')
            //             System.out.print('*');
            //         else if (mapData[i][j] == '.')
            //             System.out.print('.');
            //         else
            //             System.out.print(check[i][j]);
            //     }
            //     System.out.println();
            // }


            states++;
            
            // check if goal node? might not work
            // backtracks so sequence of boxmoves is in reverse order
            // current.hCost == 0 ??
            if (current.hCost == 0) {
                while (current.gCost != 0) {
                    backtrack.add(current.move);
                    current = current.parent;
                }

                

                return backtrack;
            }

            // generate children
            for (BoxMove move : BoxMove.generateBoxMoves(mapData, current.itemsData, current.move.coord, current.boxes,
                    deadzone)) {
                Coord[] newBoxes = new Coord[boxes.length];
                Coord prevBox = move.coord;
                Coord boxMoved = null;

                for (int i = 0; i < boxes.length; i++) {
                    newBoxes[i] = new Coord(current.boxes[i]);

                    if (newBoxes[i].equals(prevBox)) {
                        switch (move.dir) {
                            case 'u':
                                newBoxes[i].r -= 1;
                                break;
                            case 'd':
                                newBoxes[i].r += 1;
                                break;
                            case 'l':
                                newBoxes[i].c -= 1;
                                break;
                            case 'r':
                                newBoxes[i].c += 1;
                                break;
                        }
                        boxMoved = newBoxes[i];
                    }
                }

                char[][] newState = newState(current.itemsData, move);

                Node child = new Node(newBoxes, current, move, newState, current.gCost + 1,
                        HFunction(newBoxes, goals));

                // see if child is open or closed already
                // ArrayList<Coord> visited = new ArrayList<>();
                if (open.contains(child) || closed.contains(child)) {
                    continue;
                }

                if (FreezeLocks.isFreeze(mapData, newState, boxMoved, new ArrayList<Coord>(), deadzone)) {
                    closed.add(child);
                    freeze++;
                    continue;
                }

                open.add(child);
            }
        }

        return backtrack;
    }

    public static int HFunction(Coord[] boxes, Coord[] goals) {

        int sum = 0;
        int inGoal = 0;

        for (Coord box : boxes) {
            int lowest = Coord.manhattanDist(box, goals[0]);
            for (Coord goal : goals) {
                int cur = Coord.manhattanDist(box, goal);
                if (cur < lowest)
                    lowest = cur;
            }
            if (lowest == 0)
                inGoal++;
            sum += lowest;
        }

        return sum + (boxes.length - inGoal) * boxes.length * boxes.length;

        // return anneal.anneal(1000, boxes.length, 100, boxes, goals) + k;
    }

    private static char[][] newState(char[][] previousState, BoxMove move) {
        char[][] newState = new char[previousState.length][previousState[0].length];

        for (int i = 0; i < previousState.length; i++) {
            for (int j = 0; j < previousState[0].length; j++) {
                if (previousState[i][j] == '@') {
                    newState[i][j] = ' ';
                } else
                    newState[i][j] = previousState[i][j];
            }
        }

        newState[move.coord.r][move.coord.c] = '@';

        switch (move.dir) {
            case 'u':
                newState[move.coord.r - 1][move.coord.c] = '$';
                break;
            case 'd':
                newState[move.coord.r + 1][move.coord.c] = '$';
                break;
            case 'l':
                newState[move.coord.r][move.coord.c - 1] = '$';
                break;
            case 'r':
                newState[move.coord.r][move.coord.c + 1] = '$';
                break;
        }

        return newState;
    }

    public static boolean[][] generateDeadzone(char[][] mapData) {
        // delete all boxes, find player
        boolean[][] deadzone = new boolean[mapData.length][mapData[0].length];
        ArrayList<Coord> goals = new ArrayList<>();

        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                if (mapData[i][j] == '.') {
                    goals.add(new Coord(i, j));
                }
                deadzone[i][j] = true;
            }
        }

        for (Coord goal : goals) {
            ArrayDeque<Coord> queue = new ArrayDeque<>();
            HashSet<Coord> visited = new HashSet<>();

            queue.add(goal);

            while (!queue.isEmpty()) {
                Coord curr = queue.removeLast();

                visited.add(curr);
                deadzone[curr.r][curr.c] = false;

                Coord[] children = curr.getUDLRCoords();

                Coord cChild = children[0];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r - 1][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[1];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r + 1][cChild.c])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[2];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r][cChild.c - 1])
                        && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[3];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r][cChild.c + 1])
                        && !visited.contains(cChild))
                    queue.add(cChild);
            }
        }

        return deadzone;
    }

    public static class Node {
        public Coord[] boxes;
        public Node parent;
        public BoxMove move;
        public char[][] itemsData;
        public int gCost;
        public int hCost;

        public Node(Coord[] boxes, Node parent, BoxMove move, char[][] itemsData, int gCost, int hCost) {
            this.boxes = boxes;
            this.parent = parent;
            this.move = move;
            this.itemsData = itemsData;
            this.gCost = gCost;
            this.hCost = hCost;
        }

        public int getFCost() {
            return gCost + hCost;
        }

        @Override
        public boolean equals(Object e) {
            Node n2 = (Node) e;

            for (int i = 0; i < itemsData.length; i++) {
                if (!Arrays.equals(itemsData[i], n2.itemsData[i]) || move.dir != n2.move.dir)
                    return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return 31 + 7 * Arrays.deepHashCode(itemsData) + 11 * Character.hashCode(move.dir);
        }
    }

    public static class NodeComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.getFCost() - n2.getFCost();
        }
    }

    public static class MoveNode {
        public Coord coords;
        public MoveNode parent;
        public int gCost;
        public int hCost;

        public MoveNode(Coord coord, MoveNode parent, int gCost, int hCost) {
            this.coords = coord;
            this.parent = parent;

            this.gCost = gCost;
            this.hCost = hCost;
        }

        public int getFCost() {
            return gCost + hCost;
        }

        @Override
        public boolean equals(Object e) {
            MoveNode n2 = (MoveNode) e;

            return coords.equals(n2.coords);
        }

        @Override
        public int hashCode() {
            return 31 + coords.hashCode();
        }
    }

    public static class MoveNodeComparator implements Comparator<MoveNode> {
        public int compare(MoveNode n1, MoveNode n2) {
            return n1.getFCost() - n2.getFCost();
        }
    }

}
