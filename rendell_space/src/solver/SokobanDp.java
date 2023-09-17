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
        if (sc.isStateUnsolvable(state)) {
            // memo.put(state, null);
            return null;
        }

        if (visited.contains(state)) return null;
        else visited.add(state);

        // if (memo.containsKey(state)) {
        //     return memo.get(state);
        // }

        String u = SokobanDpSolver(sb.moveState(state, 'u'));
        String d = SokobanDpSolver(sb.moveState(state, 'd'));
        String l = SokobanDpSolver(sb.moveState(state, 'l'));
        String r = SokobanDpSolver(sb.moveState(state, 'r'));

        if (u != null) {
            // memo.put(state, u.insert(0, 'u'));
            // return memo.get(state);
            return "u" + u;
        }
        if (d != null) {
            // memo.put(state, d.insert(0, 'u'));
            // return memo.get(state);
            return "d" + d;
        }
        if (l != null) {
            // memo.put(state, l.insert(0, 'u'));
            // return memo.get(state);
            return "l" + l;
        }
        if (r != null) {
            // memo.put(state, r.insert(0, 'u'));
            // return memo.get(state);
            return "r" + r;
        }

        // memo.put(state, null);
        return null;
    }
}
