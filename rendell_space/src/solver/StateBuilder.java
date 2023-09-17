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

    public String createState(char[][] mapData, char[][] itemsData) {
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

        return String.format("%d%d%s", h, w, sb.toString());
    }

    public String moveState(String state, char direction) {
        int moveIndex = 0, movePlusIndex = 0;
        int playerIndex = Math.max(state.indexOf('0'), state.indexOf('@'));
        char variations[] = new char[3];
        int h = Character.getNumericValue(state.charAt(0));
        int w = Character.getNumericValue(state.charAt(1));
        int upper = h * w + 1;
        int lower = 2;
        StringBuilder sb = new StringBuilder(state);
        switch(direction) {
            case 'u':
                moveIndex = playerIndex - w;
                movePlusIndex = moveIndex - w;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex >= lower) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'l':
                moveIndex = playerIndex - 1;
                movePlusIndex = moveIndex - 1;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex >= lower && (moveIndex - lower) % w != 0) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'd':
                moveIndex = playerIndex + w;
                movePlusIndex = moveIndex + w;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex <= upper) ? sb.charAt(movePlusIndex) : '#';
                break;
            case 'r':
                moveIndex = playerIndex + 1;
                movePlusIndex = moveIndex + 1;

                variations[0] = sb.charAt(playerIndex);
                variations[1] = sb.charAt(moveIndex);
                variations[2] = (movePlusIndex <= upper && (moveIndex - 1) % w != 0) ? sb.charAt(movePlusIndex) : '#';
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

        return sb.toString();
    }

    public void printState(String state) {
        int h  = Character.getNumericValue(state.charAt(0));
        int w = Character.getNumericValue(state.charAt(1));

        for (int i = 2; i < h * w + 1; i++) {
            if ((i - 2) % w == 0) System.out.print("\n");

            System.out.print(state.charAt(i));
        }

        System.out.print("\n\n");
    } 
}
