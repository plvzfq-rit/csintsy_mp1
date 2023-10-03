package solver;

import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.ArrayList;

public class SokobanDp {


    public static StateBuilder sb = new StateBuilder();
    public static StateChecker sc = new StateChecker();
    public static ArrayList<String> visited = new ArrayList<>();
    public static boolean found = false;

    // public static void main(String[] args) {
    //     char[][] mapData = {
    //         {' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', '#', '#', '#', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {'#', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#'},
    //         {'#', ' ', ' ', ' ', '#', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', ' ', '.', '.', '#'},
    //         {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '.', '.', '#'},
    //         {'#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', ' ', '.', '.', '#'},
    //         {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
    //         {' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    //     };
    //     char[][] itemsData= {
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', '$', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', '$', ' ', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '@', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
    //         {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    //     };
    //     SokobanDp sd = new SokobanDp();


    //     State state = sb.createState(mapData, itemsData);

    //     System.out.println(sd.SokobanDpSolver(state));
    // }

    public String SokobanDpSolver(State state) {
        if (found) {
            // System.out.println("NO WAY");
            return null;
        }

        if (sc.isStateSolved(state)) {
            found = true;
            return new String();
        }

        if (sc.isStateUnsolvable(state)) return null; 

        // System.out.println(sb.getBoxPositions(state));
        if (visited.contains(state.data)) return null;
        else visited.add(state.data);

        String solutions[] = {SokobanDpSolver(sb.moveState(state, 'u')), SokobanDpSolver(sb.moveState(state, 'd')), SokobanDpSolver(sb.moveState(state, 'l')), SokobanDpSolver(sb.moveState(state, 'r'))};
        String directions[] = {"u", "d", "l", "r"};

        int shortest = 0;
        for (int i = 0; i < 4; i++) {
            if (solutions[shortest] == null || (solutions[i] != null && solutions[i].length() < solutions[shortest].length()))
                shortest = i; 
        }
        
        if (solutions[shortest] != null)
            return directions[shortest] + solutions[shortest];
        
        return null;
    }
}

