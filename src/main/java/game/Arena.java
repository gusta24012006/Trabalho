package game;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import game.elements.*;
import game.utils.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width, height;
    private Snake snake;
    private Food food;
    private List<Wall> walls;
    private Random random = new Random();

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new Snake(width / 2, height / 2);
        food = randomFood();
        walls = createWalls();
    }

    private Food randomFood() {
        int x = random.nextInt(width - 2) + 1;
        int y = random.nextInt(height - 2) + 1;
        return new Food(x, y);
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0));           // top
            walls.add(new Wall(x, height - 1));  // bottom
            x++;
        }
        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(0, y));           // left
            walls.add(new Wall(width - 1, y));   // right
        }

        return walls;
    }

    public void draw(TextGraphics g) {
        // Background
        g.setBackgroundColor(TextColor.Factory.fromString("#222233"));
        g.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        // Draw walls
        for (Wall wall : walls) {
            wall.draw(g);
        }

        // Draw snake and food
        snake.draw(g);
        food.draw(g);
    }

    public void processKey(KeyStroke key) throws IOException {
        if (key == null) return;

        switch (key.getKeyType()) {
            case ArrowUp: snake.setDirection(new Position(0, -1)); break;
            case ArrowDown: snake.setDirection(new Position(0, 1)); break;
            case ArrowLeft: snake.setDirection(new Position(-1, 0)); break;
            case ArrowRight: snake.setDirection(new Position(1, 0)); break;
            case Character:
                if (key.getCharacter() == 'q') throw new IOException("Quit");
        }
    }

    public void update() throws IOException {
        snake.move();
        Position head = snake.getBody().get(0);

        // Wall collision
        for (Wall wall : walls) {
            if (head.equals(wall.getPosition())) {
                throw new IOException("Game Over: Hit wall!");
            }
        }

        // Self collision
        if (snake.hitsSelf()) {
            throw new IOException("Game Over: Hit yourself!");
        }

        // Eat food
        if (head.equals(food.getPosition())) {
            snake.grow();
            food = randomFood();
        }
    }
}
