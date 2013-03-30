package gofish;

import gofish.console.ConsoleGame;
import gofish.console.ConsoleRenderer;
import java.util.Scanner;

public class GoFish {

    public static void main(String[] args) {
        GUIRenderer renderer = new ConsoleRenderer();
        try {
            Scanner input = new Scanner(System.in);
            ConsoleGame game = new ConsoleGame(renderer, input);
            game.run();
        } catch (Exception e) {
            renderer.error(e.getMessage());
        }
    }
    
}
