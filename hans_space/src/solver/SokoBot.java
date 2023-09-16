package solver;
import java.util.*;
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
    
    int[] Playerloc = findPLayerLoc(height, width, itemsData);
    System.out.println(Playerloc[0]);
    System.out.println(Playerloc[1]);

    ArrayList<int[]> boxes = findBoxLocations(height, width, itemsData);
    for(int k = 0; k < boxes.size(); k++){
      int[] items = boxes.get(k);
      System.out.println(items[0]+", "+ items[1]);
    }

    try {
      Thread.sleep(3000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "dududududud";
  }

  public static boolean isSolved(int height,int width,char[][] mapData, char[][] itemsData){
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        if(mapData[i][j] == '.' && itemsData[i][j] != '$'){
          return false;
        }
      }
    }
    return true;
  }

  public static int[] findPLayerLoc(int height,int width, char[][] itemsData){
    int i=-1,j=-1;
    int[] location = new int[2];
    for(i = 0; i < height; i++){
      for(j = 0; j < width; j++){
        if(itemsData[i][j] == '@'){
          location[0] = i;
          location[1] = j;
          return location;
        }
      }
      
    }
      return location;
  }

  public static ArrayList<int[]> findBoxLocations(int height,int width, char[][] itemsData){
    int i=-1,j=-1;
    ArrayList<int[]> returnMe = new ArrayList<int[]>();
    int[] location = new int[2];
    for(i = 0; i < height; i++){
      for(j = 0; j < width; j++){
        if(itemsData[i][j] == '$'){
          System.out.println("Box at: "+ i+ ", " + j);
          location[0] = i;
          location[1] = j;
          returnMe.add(location);
          location = new int[2];
        }
      }
    }
      return returnMe;
  }


}
