package game;

public class Application {
    public static void main(String[] args) {
        try {
            SnakeGame game = new SnakeGame();
            game.run();
        } catch (Exception e) {  // catch all Exceptions
            e.printStackTrace();
        }
    }
}
