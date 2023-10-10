package solver;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    /*
     * YOU NEED TO REWRITE THE IMPLEMENTATION OF THIS METHOD TO MAKE THE BOT SMARTER
     */
    /*
     * Default stupid behavior: Think (sleep) for 3 seconds, and then return a
     * sequence
     * that just moves left and right repeatedly.
     */

        try {

            char[][] mapData1 = new char[mapData.length][];
            char[][] itemsData1  = new char[mapData.length][];

            for (int i = 0; i < mapData1.length; i++) {
                mapData1[i]=mapData[i].clone();
                itemsData1[i]=itemsData[i].clone();
            }

            ArrayList<BoxMove> boxMoves=BoxMoveAStar(mapData, itemsData);
            StringBuilder sb = new StringBuilder();
            interpretBoxMoves(mapData1, itemsData1, boxMoves, sb);
            //System.out.println(sb.toString());
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    return "";
  }

  public static void interpretBoxMoves(char[][] mapData, char[][] itemsData,ArrayList<BoxMove> boxMoves,StringBuilder sb){
        while(!boxMoves.isEmpty()){
            BoxMove bm=boxMoves.remove(boxMoves.size()-1);

            Coord player = new Coord(BoxMove.getPlayerLocation(itemsData));
            Coord goal = new Coord(bm.coord);
            playerAStar(mapData, itemsData, bm, player, goal, sb);
            sb.append(bm.dir);
            itemsData=newState(itemsData, bm);
        }
    }

    public static void playerAStar(char[][] mapData, char[][] itemsData, BoxMove bm, Coord player, Coord goal, StringBuilder sb){

        if(player.equals(goal)){
            return;
        }

        if(bm.dir=='u') goal.r++;
        if(bm.dir=='d') goal.r--;
        if(bm.dir=='l') goal.c++;
        if(bm.dir=='r') goal.c--;

        PriorityQueue<MoveNode> open = new PriorityQueue<>(new MoveNodeComparator());
        MoveNode root = new MoveNode(player, null, 0, Coord.manhattanDist(player, goal));
        open.add(root);
        HashSet<Coord> visited = new HashSet<>();

        boolean found = false;


        while(!open.isEmpty()&&found==false){
            MoveNode currentNode = open.poll();
            Coord current =currentNode.coords;
            visited.add(current);

            if(current.equals(goal)){
                found = true;

                Stack<Character> stack = new Stack<>();

                MoveNode prev = currentNode;
                MoveNode cur = currentNode.parent;
                while(cur!=null){

                  Coord curC = cur.coords;
                  Coord prevC = prev.coords;

                  if(curC.c<prevC.c){
                      stack.add('r');
                  }
                  if(curC.c>prevC.c){
                      stack.add('l');
                  }
                  if(curC.r<prevC.r){
                      stack.add('d');
                  }
                  if(curC.r>prevC.r){
                      stack.add('u');
                  }

                  prev=cur;
                  cur = cur.parent;
              }

              Coord curC = root.coords;
                  Coord prevC = prev.coords;

                  if(curC.c<prevC.c){
                      stack.add('r');
                  }
                  if(curC.c>prevC.c){
                      stack.add('l');
                  }
                  if(curC.r<prevC.r){
                      stack.add('d');
                  }
                  if(curC.r>prevC.r){
                      stack.add('u');
                  }

              while(!stack.isEmpty()){
                  sb.append(stack.pop());
              }
              continue;
          }

            Coord[] neighbours = current.getUDLRCoords();

            for (Coord c : neighbours) {
                if(visited.contains(c)) continue;
                int cost = currentNode.gCost +1;
                MoveNode child = new MoveNode(c,currentNode,cost,Coord.manhattanDist(c, goal));
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
    public static boolean isValid(Coord coords, char[][] map) {
        int cSize = map.length;
        int rSize = map[0].length;
        return (coords.c>=0 && coords.r>=0 &&coords.c<cSize&&coords.r<rSize);
    }


    public static ArrayList<BoxMove> BoxMoveAStar(char[][] mapData, char[][] itemsData) {
        // create root node ready to branch out
        Coord player = BoxMove.getPlayerLocation(itemsData);
        int nBoxes = BoxMove.getNumberOfBoxes(itemsData);
        Coord[] boxes = new Coord[nBoxes];
        Coord[] goals = new Coord[nBoxes];
        BoxMove.getAllItemsCoordinates(mapData, itemsData, boxes, goals);

        boolean[][] deadzone = generateDeadzone(mapData);

        Node root = new Node (
            boxes, null, new BoxMove(' ', player), 
            itemsData, 0, HFunction(boxes, goals)
        );

        // important colelctions and lists
        PriorityQueue<Node> open = new PriorityQueue<>(new NodeComparator());
        HashSet<Node> closed = new HashSet<>();
        ArrayList<BoxMove> backtrack = new ArrayList<>();

        open.add(root);

        while (!open.isEmpty()) {
            Node current = open.poll();
            closed.add(current);

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
            for (BoxMove move : BoxMove.generateBoxMoves(mapData, current.itemsData, current.move.coord, current.boxes, deadzone)) {
                Coord[] newBoxes = new Coord[boxes.length];
                Coord prevBox = move.coord;
                
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
                    }
                }

                Node child = new Node(newBoxes, current, move, newState(current.itemsData, move), current.gCost + 1, HFunction(newBoxes, goals));

                // see if child is open or closed already
                // ArrayList<Coord> visited = new ArrayList<>();
                if (open.contains(child) || closed.contains(child)) {
                    continue;
                }

                open.add(child);
            }
        }

        return backtrack;
    }

    public static int HFunction(Coord[] boxes, Coord[] goals) {
      //replace by passing list of items and list and maps

      int sum =0;
      int inGoal = 0;

      for (Coord box : boxes) {
          int lowest = Coord.manhattanDist(box, goals[0]);
          for (Coord goal : goals) {
              int cur = Coord.manhattanDist(box, goal);
              if(cur<lowest)lowest=cur;
          }
          if (lowest == 0) inGoal++;
          sum+=lowest;
      }

      int k = (boxes.length - inGoal) * boxes.length * boxes.length * 2;
      double goalRatio = (double) inGoal / boxes.length;
      if (goalRatio > 0.3) {
          k /= boxes.length * 2;
      }

      return sum + k;

    //   return anneal.anneal(1000, boxes.length, 100, boxes, goals) + k;
  }


    private static char[][] newState(char[][] previousState, BoxMove move) {
        char[][] newState = new char[previousState.length][previousState[0].length];

        for (int i = 0; i < previousState.length; i++) {
            for (int j = 0; j < previousState[0].length; j++) {
                if (previousState[i][j] == '@'){
                    newState[i][j] = ' ';
                }
                else newState[i][j] = previousState[i][j];
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

    public static boolean isFreezeDeadlock(char[][] mapData, char[][] itemsData, Coord box, ArrayList<Coord> visited) {
        // TODO if not busy
        return false;
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
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r - 1][cChild.c]) && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[1];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r + 1][cChild.c]) && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[2];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r][cChild.c - 1]) && !visited.contains(cChild))
                    queue.add(cChild);

                cChild = children[3];
                if (BoxMove.isOpen(mapData[cChild.r][cChild.c]) && BoxMove.isOpen(mapData[cChild.r][cChild.c + 1]) && !visited.contains(cChild))
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
            return 31 + coords.hashCode() ;
        }
    }

    public static class MoveNodeComparator implements Comparator<MoveNode> {
        public int compare(MoveNode n1, MoveNode n2) {
            return n1.getFCost() - n2.getFCost();
        }
    }

}

