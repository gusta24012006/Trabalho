package game.elements;


import com.googlecode.lanterna.graphics.TextGraphics;
import game.utils.Position;



public abstract class Element {
    protected Position position;

    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Every element must know how to draw itself
    public abstract void draw(TextGraphics graphics);
}
