package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    int x, y; // coordinates
    int g; // cost from start
    int h; // heuristic (estimated cost to goal)
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.parent = null;
    }

    // A* score (f = g + h)
    public int getF() {
        return g + h;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }
}

public class AStar {
    private static final char[][] GRID = {};

    private static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

    public static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        List<Node> closedSet = new ArrayList<>();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (GRID[current.x][current.y] == 'G') {
                // Reconstruct the path
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current);

            for (int[] dir : DIRECTIONS) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isValid(newX, newY) && GRID[newX][newY] != '1') {
                    Node neighbor = new Node(newX, newY);
                    int tentativeG = current.g + 1;

                    if (closedSet.contains(neighbor) && tentativeG >= neighbor.g) {
                        continue;
                    }

                    if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                        neighbor.parent = current;
                        neighbor.g = tentativeG;
                        neighbor.h = heuristic(neighbor, goal);

                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        // No path found
        return null;
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < GRID.length && y >= 0 && y < GRID[0].length;
    }

    private static int heuristic(Node a, Node b) {
        // Manhattan distance
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Node goal = new Node(3, 4);

        List<Node> path = findPath(start, goal);

        if (path != null) {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}