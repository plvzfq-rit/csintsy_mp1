package solver;

import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.ArrayList;

public class SokobanDp {


    public static StateBuilder sb = new StateBuilder();
    public static StateChecker sc = new StateChecker();
    public static HashMap<String, StringBuilder> memo = new HashMap<>();
    public static ArrayList<String> visited = new ArrayList<>();

    // public static void main(String[] args) {
    //     char[][] mapData =  {{' ', '#', '#', '#', '#', '#', '#'},
    //     {' ', '#', ' ', ' ', '.', ' ', '#'},
    //     {'#', '#', ' ', ' ', ' ', ' ', '#'},
    //     {'#', '.', '.', ' ', '.', ' ', '#'},
    //     {'#', '#', '#', '#', '#', '#', '#'}};

    //     char[][] itemsData= {{' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //     {' ', ' ', '@', ' ', ' ', ' ', ' '},
    //     {' ', ' ', '$', '$', '$', ' ', ' '},
    //     {' ', ' ', ' ', ' ', '$', ' ', ' '},
    //     {' ', ' ', ' ', ' ', ' ', ' ', ' '}};


    //     String state = sb.createState(mapData, itemsData);

    //     System.out.println(SokobanDpSolver(state).toString());
    // }

    public String SokobanDpSolver(String state) {
        if (sc.isStateSolved(state)) return new String();
        if (sc.isStateUnsolvable(state)) return null;

        if (visited.contains(state)) return null;
        else visited.add(state);

        String u = SokobanDpSolver(sb.moveState(state, 'u'));
        String d = SokobanDpSolver(sb.moveState(state, 'd'));
        String l = SokobanDpSolver(sb.moveState(state, 'l'));
        String r = SokobanDpSolver(sb.moveState(state, 'r'));

        if (u != null) {
            return "u" + u;
        }
        if (d != null) {
            return "d" + d;
        }
        if (l != null) {
            return "l" + l;
        }
        if (r != null) {
            return "r" + r;
        }
        
        return null;
    }
}

