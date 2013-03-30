package gofish.console;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.console.config.ConfigSelector;
import java.util.Scanner;

public class ConsoleGame {
    
    final private static int OPTION_RESTART = 1;
    
    final private static int OPTION_RECONFIGURE = 2;
    
    final private static int OPTION_EXIT = 3;
    
    private GUIRenderer renderer;
    
    private Scanner input;
    
    private Config configCopy;

    public ConsoleGame(GUIRenderer renderer, Scanner input) {
        this.renderer = renderer;
        this.input = input;
    }
    
    public void run() {
        ConfigFactory factory = new ConfigSelector(input);
        Config config;
        
        // Create a new game
        config = factory.getConfig();
        startGame(config);
        
        do {
            // Choose what to do after game ends
            System.out.println("Choose an option");
            System.out.println("1. Restart game (using previous configuration)");
            System.out.println("2. New game (reconfigure)");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            switch (ConsoleUtils.nextInt(input, 1, 3)) {
                case OPTION_RESTART:
                    // Use config copy
                    config = configCopy;
                    break;
                case OPTION_RECONFIGURE:
                    // Create new config object
                    config = factory.getConfig();
                    break;
                case OPTION_EXIT:
                    config = null;
                    break;
            }
            
            startGame(config);
            
        } while (config != null);
    }
    
    private void startGame(Config config) {
        if (config != null) {
            configCopy = config.clone();
            Game game = new Game(renderer, config);
            game.start();
        }
    }

}
