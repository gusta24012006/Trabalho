package game.elements;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Element {
    private List<Position> body;
    private Position direction;

    public Snake(int startX, int startY) {
        super(startX, startY);

        body = new ArrayList<>();
        body.add(new Position(startX, startY)); // head
        body.add(new Position(startX -1 , startY)); // body
        body.add(new Position(startX - 2, startY)); // body


        // Initial direction: moving right
        direction = new Position(1, 0);
    }

    @Override
    public void draw(TextGraphics g) {
        g.setForegroundColor(TextColor.Factory.fromString("#00FF00"));

        // Draw head
        g.putString(body.get(0).getX(), body.get(0).getY(), "O");

        // Draw body
        for (int i = 1; i < body.size(); i++) {
            g.putString(body.get(i).getX(), body.get(i).getY(), "o");

        }
    }

    public void setDirection(Position dir) {
        // Prevent reversing into itself
        if (!dir.isOpposite(direction)) {
            direction = dir;
        }
    }

    public List<Position> getBody() {
        return body;
    }

    public Position getNextHeadPosition() {
        return new Position(
                body.get(0).getX() + direction.getX(),
                body.get(0).getY() + direction.getY()
        );
    }

    public void grow() {
        body.add(body.get(body.size() - 1));
    }

    public void move() {
        Position newHead = getNextHeadPosition();

        // Move body
        for (int i = body.size() - 1; i > 0; i--) {
            body.set(i, body.get(i - 1));
        }

        // Move head
        body.set(0, newHead);
        this.position = newHead;
    }

    public boolean hitsSelf() {
        Position head = body.get(0);

        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }
}
