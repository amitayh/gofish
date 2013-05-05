package gofish.console;

import gofish.config.Config;
import gofish.config.ConfigFactory;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.console.config.ConfigSelector;
import java.util.Scanner;

public class ConsoleGame {
    
    final private static int OPTION_RESTART = 1;
    
    final private static int OPTION_RECONFIGURE = 2;
    
    final private static int OPTION_EXIT = 3;
    
    private GUIRenderer renderer = new ConsoleRenderer();

    public GUIRenderer getRenderer() {
        return renderer;
    }
    
    public void run() {
        Scanner input = new Scanner(System.in);
        ConfigFactory factory = new ConfigSelector(input);
        Config config = factory.getConfig();
        
        do {
            // Start game
            Config previous = config.clone();
            Game game = new Game(renderer, config);
            game.start();
            
            // Choose what to do after game ends
            System.out.println("Choose an option");
            System.out.println("1. Restart game (using previous configuration)");
            System.out.println("2. New game (reconfigure)");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            switch (ConsoleUtils.nextInt(input, 1, 3)) {
                case OPTION_RESTART:
                    // Use config copy
                    config = previous;
                    break;
                case OPTION_RECONFIGURE:
                    // Create new config object
                    config = factory.getConfig();
                    break;
                case OPTION_EXIT:
                    // Exit game
                    config = null;
                    break;
            }
        } while (config != null);
    }

}
