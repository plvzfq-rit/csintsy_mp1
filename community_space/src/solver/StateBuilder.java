package solver;

/*
    # - wall
    @ - player
    0 - player + target
    . - target
    * - box + target
 */

import java.lang.StringBuffer;
import java.lang.StringBuilder;
import java.util.ArrayList;

public class StateBuilder {
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


    //     String state = createState(mapData, itemsData);
        
    //     System.out.println("STARTING STATE: ");
    //     printState(state);
        
    //     System.out.println("NOW SOLVING:\n");

    //     int move = 1;
    //     for (char i : "rrrddlllrrruullldurrrddlludrruuldllur".toCharArray()) {
    //         // try {
    //         //     Thread.sleep(1000);
    //         //   } catch (Exception ex) {
    //         //     ex.printStackTrace();
    //         //   }

    //         state = moveState(state, i);
    //         System.out.printf("Move %d (%c)", move++, i);
    //         printState(state);
    //     }


    // }

    public State createState(char[][] mapData, char[][] itemsData) {
        StringBuffer sb = new StringBuffer();
        int w = mapData[0].length;
        int h = mapData.length;

        if (w == 0 || h == 0) 
            throw new NullPointerException("INVALID MAP OR ITEMS DATA");

        for (int i = 0; i < mapData.length; i++) {

            for (int j = 0; j < mapData[0].length; j++) {
                char md = mapData[i][j];
                char id = itemsData[i][j];

                if (md == '.' && id == '$') sb.append('*');
                else if (md == '.' && id == '@') sb.append('0');
                else if (md == ' ') sb.append(id);
                else sb.append(md);
            }

        }

        State state = new State(sb.toString(), h, w);
        return state;
    }

    public State moveState(State state, char direction) {
        int moveIndex = 0, movePlusIndex = 0;
        int playerIndex = Math.max(state.data.indexOf('0'), state.data.indexOf('@'));
        char variations[] = new char[3];
        int upper = state.h * state.w - 1;
        int lower = 0;
        StringBuilder sb = new StringBuilder(state.data);
        switch(direction) {
            case 'u':
                moveIndex = playerIndex - state.w;
                movePlusIndex = moveIndex - state.w;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex >= lower) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'l':
                moveIndex = playerIndex - 1;
                movePlusIndex = moveIndex - 1;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex >= lower) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'd':
                moveIndex = playerIndex + state.w;
                movePlusIndex = moveIndex + state.w;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex <= upper) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'r':
                moveIndex = playerIndex + 1;
                movePlusIndex = moveIndex + 1;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex <= upper) ? sb.charAt(movePlusIndex) : '#';
        }

        boolean pushed = false;

        if (variations[1] == '#'
        || ((variations[1] == '$' || variations[1] == '*') 
            && (variations[2] != ' ' && variations[2] != '.'))) {
            // move is illegal
            return state;
        }

        // change var[0] to correct char
        if (variations[0] == '0')
            variations[0] = '.';
        else variations[0] = ' ';

        // check if box was pushed
        if (variations[1] == '$' || variations[1] == '*') 
            pushed = true;

        // change var[1] to correct char
        if (variations[1] == '.' || variations[1] == '*')
            variations[1] = '0';
        else 
            variations[1] = '@';

        // change var[2] to correct char
        if (pushed && variations[2] == '.') 
            variations[2] = '*';
        else if (pushed && variations[2] == ' ')
            variations[2] = '$';

        // rebuild state
        sb.setCharAt(playerIndex, variations[0]);
        sb.setCharAt(moveIndex, variations[1]);
        if (movePlusIndex >= lower && movePlusIndex <= upper)
            sb.setCharAt(movePlusIndex, variations[2]);

        return new State(sb.toString(), state.h, state.w);
    }

    public void printState(State state) {
        int h = state.h;
        int w = state.w;
        for (int i = 2; i < h * w + 1; i++) {
            if ((i - 2) % w == 0) System.out.print("\n");

            System.out.print(state.data.charAt(i));
        }

        System.out.print("\n\n");
    } 

    public String getBoxPositions(State state) {
        String data = state.data;
        String pos = "";
        for (int i = 0; i < data.length(); i++) {
            if ("$*.@0".contains(Character.toString(data.charAt(i)))) {
                pos += String.format("%03d", i);
            }
        }
        
        return pos;
    }

    // public ArrayList<State> generateInvalidStates(State state) {
    //     String data = state.data;
    //     ArrayList<Integer> possibleInd = new ArrayList<>();
    //     for (int i = 0; i < data.length(); i++) {
    //         if (data.charAt(i) == ' ') {
    //             String c =  String.format("%c%c%c%c", data.charAt(i - state.w), data.charAt(i - 1), data.charAt(i + state.w), data.charAt(i + 1));

    //             if (c.contains("##") || (c.charAt(0) == '#' && c.charAt(3) == '#')) 
    //                 possibleInd.add(i);
    //         }
    //     }


    // }
}