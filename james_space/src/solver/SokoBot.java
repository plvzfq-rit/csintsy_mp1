package solver;

import java.util.*;

/*
 * LEGEND:
 * @ - player - in itemsData
 * # - wall - in mapData
 * $ - box - in itemsData
 * . - goal square - in mapData
 */

public class SokoBot {
    Coordinate initialPlayerCoordinates;
    List<Coordinate> initialBoxCoordinates = new ArrayList<Coordinate>();
    List<Coordinate> goalCoordinates = new ArrayList<Coordinate>();

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    this.initializeBot(width, height, mapData, itemsData);
    return aStarSearch(width, height, mapData, itemsData);
  }

  public void initializeBot (int width, int height, char[][] mapData, char[][] itemsData) {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (itemsData[i][j] == '@') {
                this.initialPlayerCoordinates = new Coordinate(i, j);
            }
            if (itemsData[i][j] == '$') {
                this.initialBoxCoordinates.add(new Coordinate(i, j));
            }
            if (mapData[i][j] == '.') {
                this.goalCoordinates.add(new Coordinate(i, j));
            }
        }
    }
  }

  public String aStarSearch (int width, int height, char[][] mapData, char[][] itemsData) {
    PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
    HashSet<NodeState> visitedMemory = new HashSet<NodeState>();
    HashMap<NodeState, Double> visitedNodeToCost = new HashMap<NodeState, Double>();
    HashSet<NodeState> frontierMemory = new HashSet<NodeState>();
    Node currentNode;
    // List<Node> viableNodes;
    String path = null;
    frontier.add(new Node (this.initialPlayerCoordinates, 
                           this.initialBoxCoordinates, 
                           this.goalCoordinates, 
                           mapData, 
                           itemsData));
                           
    while (!frontier.isEmpty() && path == null) {
      currentNode = frontier.poll();
      // frontierMemory.add(currentNode.getNodeState());
      if (isGoal(currentNode.getBoxCoordinates())) {
        path = currentNode.getPath();
      }
      else {
        visitedMemory.add(currentNode.getNodeState());
        // frontierMemory.remove(currentNode.getNodeState());
        double costToNext = currentNode.getCumulativeCost() + 1;
        List<Node> childNodes = NodeFunctions.generateChildNodes(currentNode, mapData, itemsData, goalCoordinates);
        // viableNodes = new ArrayList<Node>();
        for (Node childNode : childNodes) {
          // if (visitedMemory.contains(childNode.getNodeState()) && costToNext < )
          boolean nodeInFrontier = frontierMemory.contains(childNode.getNodeState());
          boolean nodeInVisited = visitedMemory.contains(childNode.getNodeState());

          if (nodeInFrontier) {
            if (visitedNodeToCost.get(childNode.getNodeState()) != null) {
              if (visitedNodeToCost.get(childNode.getNodeState()) > costToNext) {
                frontierMemory.remove(childNode.getNodeState());
                nodeInFrontier = !nodeInFrontier;
              }
            }
          }

          if (nodeInVisited) {
            if (visitedNodeToCost.get(childNode.getNodeState()) != null) {
              if (visitedNodeToCost.get(childNode.getNodeState()) > costToNext) {
                visitedMemory.remove(childNode.getNodeState());
                nodeInVisited = !nodeInVisited;
              }
            }
          }

          if (!nodeInFrontier && !nodeInVisited) {
            visitedNodeToCost.put(childNode.getNodeState(), costToNext);
            frontierMemory.add(childNode.getNodeState());
            frontier.add(childNode);
          }
        }
      }
    }
    
    return path;
  }

  public boolean isGoal (List<Coordinate> boxCoordinates) {
    return new HashSet<Coordinate>(boxCoordinates).equals(new HashSet<Coordinate>(this.goalCoordinates));
  }

}