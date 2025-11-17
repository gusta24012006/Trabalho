package game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class SnakeGame {

    private Screen screen;
    private Arena arena;
    private final int width = 40;
    private final int height = 20;

    public SnakeGame() throws Exception {
        screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(width, height))
                .createScreen();
        screen.startScreen();
        arena = new Arena(width, height);
    }

    public void run() throws Exception {
        boolean playing = true;

        while (playing) {
            try {
                screen.clear();
                arena.draw(screen.newTextGraphics());
                screen.refresh();

                Thread.sleep(100);

                var key = screen.pollInput();
                if (key != null) {
                    arena.processKey(key);
                }

                arena.update();
            } catch (Exception e) {
                // Snake died → show game over message
                screen.clear();
                screen.newTextGraphics().putString(1, height / 2, "Game Over! R = restart or Q = quit");
                screen.refresh();

                boolean waiting = true;
                while (waiting) {
                    var key = screen.pollInput();
                    if (key != null) {
                        switch (key.getKeyType()) {
                            case Character:
                                char c = key.getCharacter();
                                if (c == 'r' || c == 'R') {
                                    arena = new Arena(width, height); // restart game
                                    waiting = false;
                                } else if (c == 'q' || c == 'Q') {
                                    playing = false; // quit game
                                    waiting = false;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    Thread.sleep(50);
                }
            }
        }

        screen.stopScreen();
    }

}
