package solver;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c, Direction d) {
        if (d == Direction.u) {
            this.x = c.getX();
            this.y = c.getY() - 1;
        } else if (d == Direction.d) {
            this.x = c.getX();
            this.y = c.getY() + 1;
        } else if (d == Direction.l) {
            this.x = c.getX() - 1;
            this.y = c.getY();
        } else if (d == Direction.r) {
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

    public int toIntForm (int width) {
        return width * this.y + this.x;
    }

    public String toStringForm () {
        return "(" + this.x + "," + this.y + ")";
    }
}
