package gofish;

import gofish.console.config.ConfigSelector;
import gofish.console.gui.Renderer;
import gofish.console.player.Computer;
import gofish.console.player.Human;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player;
import java.util.Scanner;
import java.util.Set;

public class GoFish {

    public static void main(String[] args) throws Exception {
        // Console renderer
        GuiRenderer renderer = new Renderer();
        try {
//            ConfigFactory configFactory = new ConfigSelector();
//            Game.Config config = configFactory.getConfig();
            Game.Config config = getConfig();
            Game game = new Game(renderer, config);
            // Start game
            game.start();
        } catch (Exception e) {
            renderer.error(e.getMessage());
            throw e; // TODO - remove this (debug only)
        }
    }
    
    private static Game.Config getConfig() {
        Game.Config config = new Game.Config();
            
        Deck deck = new Deck();
        deck.shuffle();

        Scanner input = new Scanner(System.in);
        Player[] players = {
//            new Human(input, "Amitay"),
//            new Human(input, "Bob"),
//            new Human(input, "Eve")
            new Computer("Alice"),
            new Computer("Bob"),
            new Computer("Eve")
        };
        
        int playerIndex = 0;
        while (deck.size() > 0) {
            players[playerIndex].addCard(deck.deal());
            playerIndex = (playerIndex + 1) % players.length;
        }
        
        for (Player player : players) {
            config.addPlayer(player);
        }

        return config;
    }
    
}
