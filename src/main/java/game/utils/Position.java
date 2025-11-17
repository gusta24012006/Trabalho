package game.utils;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) { this.x = x; this.y = y; }

    public int getX() { return x; }
    public int getY() { return y; }

    public Position up() { return new Position(x, y - 1); }
    public Position down() { return new Position(x, y + 1); }
    public Position left() { return new Position(x - 1, y); }
    public Position right() { return new Position(x + 1, y); }

    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }

    public boolean isOpposite(Position p) {
        return this.x == -p.x && this.y == -p.y;
    }
}
