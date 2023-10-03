package solver;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c, Directions d) {
        if (d == Directions.UP) {
            this.x = c.getX();
            this.y = c.getY() - 1;
        } else if (d == Directions.DOWN) {
            this.x = c.getX();
            this.y = c.getY() + 1;
        } else if (d == Directions.LEFT) {
            this.x = c.getX() - 1;
            this.y = c.getY();
        } else if (d == Directions.RIGHT) {
            this.x = c.getX() + 1;
            this.y = c.getY();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
