package solver;
import java.lang.reflect.Array;
import java.util.*;


public class SokoBot {
  public static final char WALL = '#', EMPTY = ' ', TARGET = '.', BOX = '$', PLAYER = '@';
  //Initialization
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    /*
     * YOU NEED TO REWRITE THE IMPLEMENTATION OF THIS METHOD TO MAKE THE BOT SMARTER
     */
    /*
     * Default stupid behavior: Think (sleep) for 3 seconds, and then return a
     * sequence
     * that just moves left and right repeatedly.
     */
    
    int[] playerloc = findPLayerLoc(height, width, itemsData);
    System.out.println(playerloc[0]);
    System.out.println(playerloc[1]);

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

    
    return solve(width,height,mapData,itemsData, playerloc[0],playerloc[1], boxes, "",0,0);
  }

  public static String solve(int width, int height, char[][] mapData, char[][] itemsData, int playerX, int playerY,ArrayList<int[]> boxes, String sol, int changeInX, int changeinY){

    return sol;
  }
  /*
   *  Sorts Arrays by Row then Column
   */
  public static ArrayList<int[]> sort(ArrayList<int[]> boxes){ 
    ArrayList<int[]> res = new ArrayList<>(); 
    res.add(boxes.get(0));
    for(int i = 1; i < boxes.size()-1; i++){
      int[] currEl = boxes.get(i);
      int[] nextEl = boxes.get(i+1);
      int[] temparr;
      if(currEl[0] > nextEl[0]){
        //swap(currEl and nextEl)
        temparr = currEl.clone();
        currEl = nextEl.clone();
        nextEl = temparr.clone();
        res.add(currEl);
        res.add(nextEl);
        i++;
      } else if (currEl[0] == nextEl[0] && currEl[1] > nextEl[1]){
        //swap(currEl and nextEl)
        temparr = currEl.clone();
        currEl = nextEl.clone();
        nextEl = temparr.clone();
        res.add(currEl);
        res.add(nextEl);
        i++;
      } else {
        res.add(currEl);
      }
    }
    return res;
  }
  //Make a key for the queue to ensure that there wont be repetittion
  public static String makeKey(String a){
    
    return a;
  }

  public static boolean isSolved(int height,int width,char[][] mapData, char[][] itemsData){
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        if(mapData[i][j] == TARGET && itemsData[i][j] != BOX){
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
        if(itemsData[i][j] == PLAYER){
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
        if(itemsData[i][j] == BOX){
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
