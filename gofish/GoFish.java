package gofish;

import gofish.console.ConsoleGame;

public class GoFish {

    public static void main(String[] args) {
        ConsoleGame game = new ConsoleGame();
        try {
            game.run();
        } catch (Exception e) {
            GUIRenderer renderer = game.getRenderer();
            renderer.error(e.getMessage());
        }
    }
    
}
