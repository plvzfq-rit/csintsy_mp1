package solver;

import java.util.*;

public class NodeFunctions {

    public static List<Node> generateChildNodes (Node node, 
                                                 char[][] mapData, 
                                                 char[][] itemsData,
                                                 List<Coordinate> goalCoordinates) {
        List<Directions> possibleDirections = new ArrayList<Directions>();
        List<Node> childNodes = new ArrayList<Node>();

        // check possible directions
        for (Directions direction : Directions.values()) {
            if (NodeFunctions.canMoveInDirection(node.getPlayerCoordinate(), direction, mapData, itemsData)) {
                possibleDirections.add(direction);
            }
        }

        // create nodes in the specified directions
        for (Directions direction : possibleDirections) {
            childNodes.add(new Node(node, 
                                    new Coordinate(node.getPlayerCoordinate(), direction), 
                                    NodeFunctions.generateNewBoxCoordinates(node.getPlayerCoordinate(), 
                                                                            direction, 
                                                                            goalCoordinates), 
                                    goalCoordinates, 
                                    direction, 
                                    mapData,
                                    itemsData));
        }

        return childNodes;
    }

    public static boolean canMoveInDirection (Coordinate playerCoordinate, 
                                                Directions direction, 
                                                char[][] mapData, 
                                                char[][] itemsData) {
                                                    
        int[] directionAsArray = direction.asXYArray();
        int[] coordinateArray = {playerCoordinate.getX() + directionAsArray[0], 
                                 playerCoordinate.getY() + directionAsArray[1]};
        boolean canMove = true;

        if (mapData[coordinateArray[0]][coordinateArray[1]] == '#') {
            canMove = false;
        }

        else if (itemsData[coordinateArray[0]][coordinateArray[1]] == '$') {
            coordinateArray[0] += directionAsArray[0];
            coordinateArray[1] += directionAsArray[1];
            if (mapData[coordinateArray[0]][coordinateArray[1]] == '#' ||
                itemsData[coordinateArray[0]][coordinateArray[1]] == '$') {
                    canMove = false;
            }
        }

        return canMove;

    }

    public static List<Coordinate> generateNewBoxCoordinates (Coordinate playerCoordinate, 
                                                              Directions direction,
                                                              List<Coordinate> boxCoordinates) {
        List<Coordinate> updatedBoxCoordinates = new ArrayList<>();
        Coordinate newPlayerPosition = new Coordinate(playerCoordinate, direction);
        for (Coordinate coordinate : boxCoordinates) {
            if (coordinate.equals(newPlayerPosition)) {
                updatedBoxCoordinates.add(new Coordinate(newPlayerPosition, direction));
            }
            else {
                updatedBoxCoordinates.add(coordinate);
            }
        }
        return updatedBoxCoordinates;
    }

}
