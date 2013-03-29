package gofish;

import gofish.console.config.ConfigSelector;
import gofish.console.ConsoleRenderer;

public class GoFish {

    public static void main(String[] args) throws Exception {
        // Console renderer
        GUIRenderer renderer = new ConsoleRenderer();
        try {
            ConfigFactory configFactory = new ConfigSelector();
            Config config = configFactory.getConfig();
            Game game = new Game(renderer, config);
            // Start game
            game.start();
        } catch (Exception e) {
            renderer.error(e.getMessage());
            throw e; // TODO - remove this (debug only)
        }
    }
    
}
