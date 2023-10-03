package solver;

public class Node {
    public int value;
    public char[][] mapData;
    public char[][] itemData;
    public int[] playerCoordinates;
    public Node parentNode;
    public String direction;

    public Node (int value, char[][] mapData, char[][] itemData, int[] playerCoordinates, Node parentNode, String direction) {
        this.value = value;
        this.mapData = mapData;
        this.itemData = itemData;
        this.playerCoordinates = playerCoordinates;
        this.parentNode = parentNode;
        this.direction = direction;
    }
}
